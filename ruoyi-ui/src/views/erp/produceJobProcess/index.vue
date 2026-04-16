<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="工票ID" prop="jobId">
        <el-input
          v-model="queryParams.jobId"
          placeholder="请输入工票ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工序ID" prop="processId">
        <el-input
          v-model="queryParams.processId"
          placeholder="请输入工序ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作工ID" prop="employeeId">
        <el-input
          v-model="queryParams.employeeId"
          placeholder="请输入操作工ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="是否外协" prop="isOutsource">
        <el-select v-model="queryParams.isOutsource" placeholder="全部" clearable>
          <el-option label="自产" value="0" />
          <el-option label="外协" value="1" />
        </el-select>
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
          v-hasPermi="['erp:produceJobProcess:add']"
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
          v-hasPermi="['erp:produceJobProcess:edit']"
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
          v-hasPermi="['erp:produceJobProcess:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:produceJobProcess:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="produceJobProcessList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="工票ID" align="center" prop="jobId" width="80" />
      <el-table-column label="工序ID" align="center" prop="processId" width="80" />
      <el-table-column label="顺序" align="center" prop="processSeq" width="60" />
      <el-table-column label="操作工" align="center" prop="employeeName" width="100" />
      <el-table-column label="接收数量" align="center" prop="inQty" width="80" />
      <el-table-column label="转出数量" align="center" prop="outQty" width="80" />
      <el-table-column label="次品数量" align="center" prop="defectQty" width="80" />
      <el-table-column label="是否外协" align="center" prop="isOutsource" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isOutsource === '0' ? 'primary' : 'warning'">
            {{ scope.row.isOutsource === '0' ? '自产' : '外协' }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" width="160">
        <template slot-scope="scope">
          <span v-if="scope.row.startTime">{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}') }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" prop="finishTime" width="160">
        <template slot-scope="scope">
          <span v-if="scope.row.finishTime">{{ parseTime(scope.row.finishTime, '{y}-{m}-{d} {h}:{i}') }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:produceJobProcess:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:produceJobProcess:remove']"
          >删除</el-button>
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

    <!-- 添加或修改工序流转记录对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="工票ID" prop="jobId">
          <el-input-number v-model="form.jobId" :min="1" placeholder="工票ID" />
        </el-form-item>
        <el-form-item label="工序ID" prop="processId">
          <el-input-number v-model="form.processId" :min="1" placeholder="工序ID" />
        </el-form-item>
        <el-form-item label="工序顺序" prop="processSeq">
          <el-input-number v-model="form.processSeq" :min="1" placeholder="工序顺序" />
        </el-form-item>
        <el-form-item label="操作工ID" prop="employeeId">
          <el-input-number v-model="form.employeeId" :min="0" placeholder="操作工ID" />
        </el-form-item>
        <el-form-item label="操作工姓名" prop="employeeName">
          <el-input v-model="form.employeeName" placeholder="请输入操作工姓名" />
        </el-form-item>
        <el-form-item label="接收数量" prop="inQty">
          <el-input-number v-model="form.inQty" :min="0" placeholder="接收数量" />
        </el-form-item>
        <el-form-item label="转出数量" prop="outQty">
          <el-input-number v-model="form.outQty" :min="0" placeholder="转出数量" />
        </el-form-item>
        <el-form-item label="次品数量" prop="defectQty">
          <el-input-number v-model="form.defectQty" :min="0" placeholder="次品数量" />
        </el-form-item>
        <el-form-item label="是否外协" prop="isOutsource">
          <el-radio-group v-model="form.isOutsource">
            <el-radio label="0">自产</el-radio>
            <el-radio label="1">外协</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="外协ID" prop="outsourceId">
          <el-input-number v-model="form.outsourceId" :min="0" placeholder="外协ID" />
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
  </div>
</template>

<script>
import { listProduceJobProcess, getProduceJobProcess, addProduceJobProcess, updateProduceJobProcess, delProduceJobProcess } from "@/api/erp/produceJobProcess";

export default {
  name: "ProduceJobProcess",
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
      // 工序流转记录表格数据
      produceJobProcessList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobId: null,
        processId: null,
        employeeId: null,
        isOutsource: null,
        outsourceId: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        jobId: [
        { required: true, message: "工票ID不能为空", trigger: "blur" }
      ],
        processId: [
          { required: true, message: "工序ID不能为空", trigger: "blur" }
        ],
        processSeq: [
          { required: true, message: "工序顺序不能为空", trigger: "blur" }
        ],
        inQty: [
          { required: true, message: "接收数量不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询工序流转记录列表 */
    getList() {
      this.loading = true;
      listProduceJobProcess(this.queryParams).then(response => {
        this.produceJobProcessList = response.rows;
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
        jobId: null,
        processId: null,
        processSeq: null,
        employeeId: null,
        employeeName: null,
        inQty: 0,
        outQty: 0,
        defectQty: 0,
        startTime: null,
        finishTime: null,
        isOutsource: "0",
        outsourceId: null,
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
      this.title = "添加工序流转记录";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getProduceJobProcess(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改工序流转记录";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateProduceJobProcess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addProduceJobProcess(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除工序流转记录编号为"' + ids + '"的数据项？').then(function() {
        return delProduceJobProcess(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/produceJobProcess/export', {
        ...this.queryParams
      }, `produceJobProcess_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
