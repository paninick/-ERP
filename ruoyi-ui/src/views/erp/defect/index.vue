<template>
  <div class="app-container">
    <!-- 1. 全局顶部操作与筛选区 -->
    <div class="biz-top-bar" style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px;">
      
      <!-- 左侧极简筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biz-search-form" style="margin-bottom: 0;">
        <el-form-item prop="jobId" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.jobId" placeholder="工票ID" clearable @keyup.enter.native="handleQuery" style="width: 140px;" />
        </el-form-item>
        <el-form-item prop="processName" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.processName" placeholder="工序名称" clearable @keyup.enter.native="handleQuery" style="width: 140px;" />
        </el-form-item>
        <el-form-item prop="employeeName" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.employeeName" placeholder="操作工" clearable @keyup.enter.native="handleQuery" style="width: 120px;" />
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">查询</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <!-- 右侧固定功能按钮区 -->
      <div class="biz-action-btn-group" style="display: flex; gap: 8px; flex-shrink: 0;">
        <el-button type="primary" size="small" @click="handleAdd" v-hasPermi="['erp:defect:add']">提报次品</el-button>
        <el-button type="default" size="small" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:defect:edit']">编辑</el-button>
        <el-button type="danger" plain size="small" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:defect:remove']">删除</el-button>
        <el-button type="default" size="small" @click="handleExport" v-hasPermi="['erp:defect:export']">导出</el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="margin-left: 8px;"></right-toolbar>
      </div>
    </div>

    <!-- 2. 数据表格区 (骨架屏包装) -->
    <el-skeleton :loading="loading" animated :rows="10">
      <template>
        <el-table class="biz-table" :data="defectList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="工票ID" align="center" prop="jobId" width="80" />
          <el-table-column label="工序名称" align="center" prop="processName" width="120" />
          <el-table-column label="操作工" align="center" prop="employeeName" width="100" />
          
          <el-table-column label="缺陷大类" align="center" prop="defectCategory" width="100">
            <template slot-scope="scope">
              <!-- 废除 Tag，使用极简文本色 -->
              <span :style="{color: ['WEAVE', 'DYE', 'SPLICE'].includes(scope.row.defectCategory) ? 'var(--app-danger-color)' : 'var(--app-warning-color)'}">
                {{ {WEAVE:'织造疵',DYE:'染色疵',SPLICE:'拼接疵',SEW:'缝制疵',NEEDLE:'残断针',PACK:'包装疵'}[scope.row.defectCategory] || scope.row.defectCategory }}
              </span>
            </template>
          </el-table-column>

          <el-table-column label="缺陷等级" align="center" prop="defectLevel" width="80">
            <template slot-scope="scope">
              <!-- 圆点指示器 -->
              <span :style="{color: scope.row.defectLevel === 'CRITICAL' ? 'var(--app-danger-color)' : (scope.row.defectLevel === 'MAJOR' ? 'var(--app-warning-color)' : 'var(--app-text-secondary)')}">
                {{ scope.row.defectLevel === 'CRITICAL' ? '● 致命' : (scope.row.defectLevel === 'MAJOR' ? '● 严重' : '○ 轻微') }}
              </span>
            </template>
          </el-table-column>

          <el-table-column label="次品数量" align="center" prop="defectQty" width="80" />
          <el-table-column label="残断针" align="center" prop="isBrokenNeedle" width="80">
            <template slot-scope="scope">
              <span v-if="scope.row.isBrokenNeedle === '1'" style="color: var(--app-danger-color); font-weight: bold;">⚠ 是</span>
              <span v-else style="color: var(--app-text-tip)">否</span>
            </template>
          </el-table-column>

          <el-table-column label="处理状态" align="center" width="140">
            <template slot-scope="scope">
              <span v-if="scope.row.isScrap === '1'" style="color: var(--app-danger-color)">已报废</span>
              <span v-else-if="scope.row.isRepair === '1'" style="color: var(--app-success-color)">已修复</span>
              <span v-else style="color: var(--app-text-tip)">待处理</span>
            </template>
          </el-table-column>

          <el-table-column label="发现时间" align="center" prop="findTime" width="160">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.findTime, '{y}-{m}-{d} {h}:{i}') }}</span>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['erp:defect:edit']">详情</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" style="margin-top: 16px; text-align: right;" />
      </template>
    </el-skeleton>

    <!-- 3. 添加或修改次品记录弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="750px" append-to-body :close-on-click-modal="false" custom-class="biz-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" class="biz-form">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工票ID" prop="jobId">
              <el-input-number v-model="form.jobId" :min="1" placeholder="工票ID" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="工序ID" prop="processId">
              <el-input-number v-model="form.processId" :min="1" placeholder="工序ID" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="工序名称" prop="processName">
              <el-input v-model="form.processName" placeholder="如: 缝纫" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="操作工姓名" prop="employeeName">
              <el-input v-model="form.employeeName" placeholder="操作工姓名" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="次品数量" prop="defectQty">
              <el-input-number v-model="form.defectQty" :min="1" placeholder="数量" style="width: 100%" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="缺陷大类" prop="defectCategory">
              <el-select v-model="form.defectCategory" placeholder="请选择" style="width: 100%">
                <el-option label="织造疵" value="WEAVE" />
                <el-option label="染色疵" value="DYE" />
                <el-option label="拼接疵" value="SPLICE" />
                <el-option label="缝制疵" value="SEW" />
                <el-option label="残断针" value="NEEDLE" />
                <el-option label="包装疵" value="PACK" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-divider content-position="left">缺陷定级与责任</el-divider>

        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="缺陷等级" prop="defectLevel">
              <el-select v-model="form.defectLevel" placeholder="请选择" style="width: 100%">
                <el-option label="致命" value="CRITICAL" />
                <el-option label="严重" value="MAJOR" />
                <el-option label="轻微" value="MINOR" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="责任归属" prop="responsibility">
              <el-select v-model="form.responsibility" placeholder="请选择" style="width: 100%">
                <el-option label="本厂" value="SELF" />
                <el-option label="外协" value="OUTSOURCE" />
                <el-option label="原料" value="MATERIAL" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item label="是否残断针" prop="isBrokenNeedle">
              <el-radio-group v-model="form.isBrokenNeedle">
                <el-radio label="0">否</el-radio>
                <el-radio label="1"><span style="color:var(--app-danger-color);font-weight:bold;">⚠ 零容忍项</span></el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
          <el-col :span="12">
             <el-form-item label="处理方式" prop="handleType">
              <el-select v-model="form.handleType" placeholder="请选择" style="width: 100%">
                <el-option label="返工修复" value="REPAIR" />
                <el-option label="降级处理" value="DOWNGRADE" />
                <el-option label="直接报废" value="SCRAP" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="24" v-if="form.handleType === 'SCRAP' || form.handleType === 'REPAIR'">
           <el-col :span="12" v-if="form.handleType === 'SCRAP'">
            <el-form-item label="已确认报废" prop="isScrap">
              <el-switch v-model="form.isScrap" active-value="1" inactive-value="0"></el-switch>
            </el-form-item>
          </el-col>
          <el-col :span="12" v-if="form.handleType === 'REPAIR'">
            <el-form-item label="已修复完成" prop="isRepair">
              <el-switch v-model="form.isRepair" active-value="1" inactive-value="0"></el-switch>
            </el-form-item>
          </el-col>
        </el-row>

        <el-form-item label="原因描述" prop="defectReasonDesc">
          <el-input v-model="form.defectReasonDesc" type="textarea" placeholder="详细描述缺陷特征与产生原因" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDefect, getDefect, addDefect, updateDefect, delDefect } from "@/api/erp/defect";

export default {
  name: "ProduceDefect",
  data() {
    return {
      loading: true,
      submitLoading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      defectList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobId: null,
        processName: null,
        employeeName: null
      },
      form: {},
      rules: {
        jobId: [{ required: true, message: "不能为空", trigger: "blur" }],
        processId: [{ required: true, message: "不能为空", trigger: "blur" }],
        defectQty: [{ required: true, message: "不能为空", trigger: "blur" }]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    getList() {
      this.loading = true;
      listDefect(this.queryParams).then(res => {
        this.defectList = res.rows;
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
      this.form = { id: null, jobId: null, processId: null, processName: null, employeeId: null, employeeName: null, defectQty: 1, defectReasonCode: null, defectReasonDesc: null, isScrap: "0", isRepair: "0", findTime: null, outsourceId: null, isOutsource: "0", defectCategory: null, defectLevel: null, handleType: null, handleResult: null, responsibility: null, isBrokenNeedle: "0", needleConfirmBy: null, needleConfirmTime: null, remark: null };
      this.resetForm("form");
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "提报次品记录";
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getDefect(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = "次品记录详情";
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const req = this.form.id != null ? updateDefect(this.form) : addDefect(this.form);
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
      this.$confirm(`确定销毁记录 [${ids}] 吗？`, "危险操作", { type: 'warning' })
        .then(() => delDefect(ids))
        .then(() => {
          this.getList();
          this.$message.success("数据已销毁");
        }).catch(() => {});
    },
    handleExport() {
      this.download('erp/defect/export', { ...this.queryParams }, `defect_${new Date().getTime()}.xlsx`);
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
