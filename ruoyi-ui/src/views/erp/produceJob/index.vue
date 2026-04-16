<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="工票编号" prop="jobNo">
        <el-input
          v-model="queryParams.jobNo"
          placeholder="请输入工票编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="生产计划ID" prop="producePlanId">
        <el-input
          v-model="queryParams.producePlanId"
          placeholder="请输入生产计划ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="颜色" prop="colorCode">
        <el-input
          v-model="queryParams.colorCode"
          placeholder="请输入颜色编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="尺码" prop="sizeCode">
        <el-input
          v-model="queryParams.sizeCode"
          placeholder="请输入尺码编码"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['erp:produceJob:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:produceJob:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:produceJob:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:produceJob:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="produceJobList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工票编号" align="center" prop="jobNo" width="140" />
      <el-table-column label="生产计划ID" align="center" prop="producePlanId" width="100" />
      <el-table-column label="订单ID" align="center" prop="orderId" width="80" />
      <el-table-column label="颜色" align="center" prop="colorCode" width="80" />
      <el-table-column label="尺码" align="center" prop="sizeCode" width="80" />
      <el-table-column label="计划数量" align="center" prop="planQty" width="80" />
      <el-table-column label="完成数量" align="center" prop="actualQty" width="80" />
      <el-table-column label="次品数量" align="center" prop="defectQty" width="80" />
      <el-table-column label="工票状态" align="center" prop="status">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '2' ? 'success' : scope.row.status === '1' ? 'warning' : 'info'">
            {{ scope.row.status === '0' ? '待生产' : scope.row.status === '1' ? '生产中' : '已完成' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d} {h}:{i}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:produceJob:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:produceJob:remove']"
          >删除</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-document"
            @click="handleViewProcess(scope.row)"
            v-hasPermi="['erp:produceJobProcess:list']"
          >工序记录</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改生产工票对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="工票编号" prop="jobNo">
          <el-input v-model="form.jobNo" placeholder="请输入工票编号" />
        </el-form-item>
        <el-form-item label="生产计划ID" prop="producePlanId">
          <el-input-number v-model="form.producePlanId" :min="1" placeholder="生产计划ID" />
        </el-form-item>
        <el-form-item label="销售订单ID" prop="orderId">
          <el-input-number v-model="form.orderId" :min="1" placeholder="销售订单ID" />
        </el-form-item>
        <el-form-item label="颜色编码" prop="colorCode">
          <el-input v-model="form.colorCode" placeholder="请输入颜色编码" />
        </el-form-item>
        <el-form-item label="尺码编码" prop="sizeCode">
          <el-input v-model="form.sizeCode" placeholder="请输入尺码编码" />
        </el-form-item>
        <el-form-item label="计划数量" prop="planQty">
          <el-input-number v-model="form.planQty" :min="1" placeholder="计划数量" />
        </el-form-item>
        <el-form-item label="当前工序ID" prop="currentProcessId">
          <el-input-number v-model="form.currentProcessId" :min="0" placeholder="当前工序ID" />
        </el-form-item>
        <el-form-item label="工票状态" prop="status">
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
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 工序记录对话框 -->
    <el-dialog title="工序流转记录" :visible.sync="processOpen" width="70%" append-to-body>
      <el-table v-loading="processLoading" :data="processList">
        <el-table-column label="工序ID" align="center" prop="processId" width="80" />
        <el-table-column label="工序顺序" align="center" prop="processSeq" width="80" />
        <el-table-column label="操作工" align="center" prop="employeeName" width="100" />
        <el-table-column label="接收数量" align="center" prop="inQty" width="80" />
        <el-table-column label="转出数量" align="center" prop="outQty" width="80" />
        <el-table-column label="次品数量" align="center" prop="defectQty" width="80" />
        <el-table-column label="是否外协" align="center" prop="isOutsource" width="80">
          <template slot-scope="scope">
            {{ scope.row.isOutsource === '0' ? '自产' : '外协' }}
          </template>
        </el-table-column>
        <el-table-column label="开始时间" align="center" prop="startTime" width="160">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}') }}</span>
          </template>
        </el-table-column>
        <el-table-column label="完成时间" align="center" prop="finishTime" width="160">
          <template slot-scope="scope">
            <span v-if="scope.row.finishTime">{{ parseTime(scope.row.finishTime, '{y}-{m}-{d} {h}:{i}') }}</span>
            <span v-else>-</span>
          </template>
        </el-table-column>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="processOpen = false">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProduceJob, getProduceJob, addProduceJob, updateProduceJob, delProduceJob, getProduceJobStatistics } from "@/api/erp/produceJob";
import { listProduceJobProcessByJob } from "@/api/erp/produceJobProcess";

export default {
  name: "ProduceJob",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 生产工票表格数据
      produceJobList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示工序记录对话框
      processOpen: false,
      // 工序记录加载
      processLoading: false,
      // 工序记录列表
      processList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobNo: null,
        producePlanId: null,
        colorCode: null,
        sizeCode: null,
        status: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jobNo: [
          { required: true, message: "工票编号不能为空", trigger: "blur" }
        ],
        producePlanId: [
          { required: true, message: "生产计划ID不能为空", trigger: "blur" }
        ],
        orderId: [
          { required: true, message: "销售订单ID不能为空", trigger: "blur" }
        ],
        planQty: [
          { required: true, message: "计划数量不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询生产工票列表 */
    getList() {
      this.loading = true;
      listProduceJob(this.queryParams).then(response => {
        this.produceJobList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        jobNo: null,
        producePlanId: null,
        orderId: null,
        colorCode: null,
        sizeCode: null,
        planQty: 0,
        actualQty: 0,
        defectQty: 0,
        currentProcessId: null,
        currentProcessStatus: "0",
        status: "0",
        remark: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加生产工票";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getProduceJob(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改生产工票";
      });
    },
    /** 查看工序记录 */
    handleViewProcess(row) {
      this.processLoading = true;
      this.processOpen = true;
      listProduceJobProcessByJob(row.id).then(response => {
        this.processList = response.data;
        this.processLoading = false;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProduceJob(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProduceJob(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除生产工票编号为"' + ids + '"的数据项？').then(function() {
        return delProduceJob(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/produceJob/export', {
        ...this.queryParams
      }, `produceJob_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
