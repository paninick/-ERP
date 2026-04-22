<template>
  <div class="app-container">
    <!-- 1. 全局顶部操作与筛选区 -->
    <div class="biz-top-bar" style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px;">
      
      <!-- 左侧极简筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biz-search-form" style="margin-bottom: 0;">
        <el-form-item prop="jobNo" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.jobNo" placeholder="工票编号" clearable @keyup.enter.native="handleQuery" style="width: 160px;" />
        </el-form-item>
        <el-form-item prop="producePlanId" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.producePlanId" placeholder="生产计划ID" clearable @keyup.enter.native="handleQuery" style="width: 140px;" />
        </el-form-item>
        <el-form-item prop="colorCode" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.colorCode" placeholder="颜色" clearable @keyup.enter.native="handleQuery" style="width: 120px;" />
        </el-form-item>
        <el-form-item prop="status" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.status" placeholder="状态" clearable style="width: 120px;">
            <el-option label="待生产" value="0" />
            <el-option label="生产中" value="1" />
            <el-option label="已完成" value="2" />
          </el-select>
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">查询</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 右侧固定功能按钮区 -->
      <div class="biz-action-btn-group" style="display: flex; gap: 8px; flex-shrink: 0;">
        <el-button type="primary" size="small" @click="handleAdd" v-hasPermi="['erp:produceJob:add']">开立工票</el-button>
        <el-button type="default" size="small" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:produceJob:edit']">编辑</el-button>
        <el-button type="danger" plain size="small" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:produceJob:remove']">作废</el-button>
        <el-button type="default" size="small" @click="handleExport" v-hasPermi="['erp:produceJob:export']">导出</el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="margin-left: 8px;"></right-toolbar>
      </div>
    </div>

    <!-- 2. 数据表格区 (骨架屏包装) -->
    <el-skeleton :loading="loading" animated :rows="10">
      <template>
        <!-- 移除边框，双击唤起极速编辑 -->
        <el-table class="biz-table" :data="produceJobList" @selection-change="handleSelectionChange" @row-dblclick="handleRowDblclick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="工票编号" align="center" prop="jobNo" width="140" />
          <el-table-column label="计划ID" align="center" prop="producePlanId" width="80" />
          <el-table-column label="颜色" align="center" prop="colorCode" width="80" />
          <el-table-column label="尺码" align="center" prop="sizeCode" width="80" />
          <el-table-column label="计划数量" align="center" prop="planQty" width="80" />
          <el-table-column label="完成数量" align="center" prop="actualQty" width="80" />
          <el-table-column label="次品数量" align="center" prop="defectQty" width="80" />
          
          <el-table-column label="工票状态" align="center" prop="status">
            <template slot-scope="scope">
              <!-- 双击状态时内联编辑 -->
              <div v-if="scope.row.isEditing">
                <el-select v-model="scope.row.status" size="mini" @change="saveInline(scope.row)">
                  <el-option label="待生产" value="0" />
                  <el-option label="生产中" value="1" />
                  <el-option label="已完成" value="2" />
                </el-select>
              </div>
              <span v-else :style="{color: scope.row.status === '2' ? 'var(--app-success-color)' : (scope.row.status === '1' ? 'var(--app-warning-color)' : 'var(--app-text-tip)')}">
                {{ scope.row.status === '0' ? '○ 待生产' : scope.row.status === '1' ? '● 生产中' : '● 已完成' }}
              </span>
            </template>
          </el-table-column>

          <el-table-column label="创建时间" align="center" prop="createTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['erp:produceJob:edit']">详情</el-button>
              <el-button size="mini" type="text" @click="handleViewProcess(scope.row)" v-hasPermi="['erp:produceJobProcess:list']">流转记录</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" style="margin-top: 16px; text-align: right;" />
      </template>
    </el-skeleton>

    <!-- 3. 添加或修改生产工票对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false" custom-class="biz-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" class="biz-form">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工票编号" prop="jobNo">
              <el-input v-model="form.jobNo" placeholder="保存时自动生成（PJ-yyyyMMdd-序号）" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="生产计划ID" prop="producePlanId">
              <el-input-number v-model="form.producePlanId" :min="1" placeholder="计划ID" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="颜色编码" prop="colorCode">
              <el-input v-model="form.colorCode" placeholder="请输入" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="尺码编码" prop="sizeCode">
              <el-input v-model="form.sizeCode" placeholder="请输入" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="计划数量" prop="planQty">
              <el-input-number v-model="form.planQty" :min="1" placeholder="数量" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="销售订单ID" prop="orderId">
              <el-input-number v-model="form.orderId" :min="1" placeholder="订单ID" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="当前状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">待生产</el-radio>
            <el-radio label="1">生产中</el-radio>
            <el-radio label="2">已完成</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 工序记录抽屉 (替代笨重全屏 Dialog) -->
    <el-drawer title="工序流转记录" :visible.sync="processOpen" size="50%" append-to-body custom-class="biz-drawer">
      <div style="padding: 0 20px;">
        <el-table v-loading="processLoading" :data="processList" class="biz-table">
          <el-table-column label="顺序" align="center" prop="processSeq" width="60" />
          <el-table-column label="操作工" align="center" prop="employeeName" width="100" />
          <el-table-column label="接收数量" align="center" prop="inQty" width="80" />
          <el-table-column label="转出数量" align="center" prop="outQty" width="80" />
          <el-table-column label="次品数量" align="center" prop="defectQty" width="80" />
          <el-table-column label="是否外协" align="center" prop="isOutsource" width="80">
            <template slot-scope="scope">
              {{ scope.row.isOutsource === '0' ? '自产' : '外协' }}
            </template>
          </el-table-column>
          <el-table-column label="完成时间" align="center" prop="finishTime" width="160">
            <template slot-scope="scope">
              <span v-if="scope.row.finishTime">{{ parseTime(scope.row.finishTime, '{y}-{m}-{d} {h}:{i}') }}</span>
              <span v-else>-</span>
            </template>
          </el-table-column>
        </el-table>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import { listProduceJob, getProduceJob, addProduceJob, updateProduceJob, delProduceJob } from "@/api/erp/produceJob";
import { listProduceJobProcessByJob } from "@/api/erp/produceJobProcess";

export default {
  name: "ProduceJob",
  data() {
    return {
      loading: true,
      submitLoading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      produceJobList: [],
      title: "",
      open: false,
      processOpen: false,
      processLoading: false,
      processList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobNo: null,
        producePlanId: null,
        colorCode: null,
        sizeCode: null,
        status: null
      },
      form: {},
      rules: {
        jobNo: [{ required: true, message: "不能为空", trigger: "blur" }],
        producePlanId: [{ required: true, message: "不能为空", trigger: "blur" }],
        orderId: [{ required: true, message: "不能为空", trigger: "blur" }],
        planQty: [{ required: true, message: "不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listProduceJob(this.queryParams).then(res => {
        this.produceJobList = res.rows.map(item => ({ ...item, isEditing: false }));
        this.total = res.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    reset() {
      this.form = { id: null, jobNo: null, producePlanId: null, orderId: null, colorCode: null, sizeCode: null, planQty: 0, actualQty: 0, defectQty: 0, status: "0" };
      this.resetForm("form");
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "开立工票";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getProduceJob(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = "工票详情";
      });
    },
    handleViewProcess(row) {
      this.processLoading = true;
      this.processOpen = true;
      listProduceJobProcessByJob(row.id).then(res => {
        this.processList = res.data;
        this.processLoading = false;
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const req = this.form.id != null ? updateProduceJob(this.form) : addProduceJob(this.form);
          req.then(() => {
            this.$message.success("保存成功");
            this.open = false;
            this.getList();
          }).finally(() => {
            this.submitLoading = false;
          });
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(`确定作废工票 [${ids}] 吗？`, "危险操作", { type: 'warning' })
        .then(() => delProduceJob(ids))
        .then(() => {
          this.getList();
          this.$message.success("工票已作废");
        }).catch(() => {});
    },
    handleExport() {
      this.download('erp/produceJob/export', { ...this.queryParams }, `produceJob_${new Date().getTime()}.xlsx`);
    },
    // 双击内联编辑
    handleRowDblclick(row, column, event) {
      if (column.property === 'status') {
        row.isEditing = true;
      }
    },
    // 保存内联
    saveInline(row) {
      row.isEditing = false;
      updateProduceJob(row).then(() => {
        this.$message.success('流转状态已更新');
      }).catch(() => {
        row.isEditing = true;
      });
    }
  }
};
</script>

<style scoped>
.biz-table >>> .el-table__row {
  cursor: pointer;
}
.biz-search-form >>> .el-form-item__content {
  line-height: 32px;
}
</style>
