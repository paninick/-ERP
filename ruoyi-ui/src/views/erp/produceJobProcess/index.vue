<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('produceJobProcess.jobId')" prop="jobId">
        <el-input
          v-model="queryParams.jobId"
          :placeholder="$t('validation.enter', [$t('produceJobProcess.jobId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('produceJobProcess.processId')" prop="processId">
        <el-input
          v-model="queryParams.processId"
          :placeholder="$t('validation.enter', [$t('produceJobProcess.processId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('produceJobProcess.employeeId')" prop="employeeId">
        <el-input
          v-model="queryParams.employeeId"
          :placeholder="$t('validation.enter', [$t('produceJobProcess.employeeId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('produceJobProcess.isOutsource')" prop="isOutsource">
        <el-select v-model="queryParams.isOutsource" clearable>
          <el-option :label="$t('produceJobProcess.selfProduction')" value="0" />
          <el-option :label="$t('produceJobProcess.outsourced')" value="1" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('btn.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
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
        >{{ $t('btn.add') }}</el-button>
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
        >{{ $t('btn.edit') }}</el-button>
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
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:produceJobProcess:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="produceJobProcessList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('produceJobProcess.jobId')" align="center" prop="jobId" width="80" />
      <el-table-column :label="$t('produceJobProcess.processId')" align="center" prop="processId" width="80" />
      <el-table-column :label="$t('produceJobProcess.processSeq')" align="center" prop="processSeq" width="60" />
      <el-table-column :label="$t('produceJobProcess.employeeName')" align="center" prop="employeeName" width="100" />
      <el-table-column :label="$t('produceJobProcess.inQty')" align="center" prop="inQty" width="80" />
      <el-table-column :label="$t('produceJobProcess.outQty')" align="center" prop="outQty" width="80" />
      <el-table-column :label="$t('produceJobProcess.defectQty')" align="center" prop="defectQty" width="80" />
      <el-table-column :label="$t('produceJobProcess.isOutsource')" align="center" prop="isOutsource" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isOutsource === '0' ? 'primary' : 'warning'">
            {{ scope.row.isOutsource === '0' ? $t('produceJobProcess.selfProduction') : $t('produceJobProcess.outsourced') }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('produceJobProcess.startTime')" align="center" prop="startTime" width="160">
        <template slot-scope="scope">
          <span v-if="scope.row.startTime">{{ parseTime(scope.row.startTime, '{y}-{m}-{d} {h}:{i}') }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('produceJobProcess.finishTime')" align="center" prop="finishTime" width="160">
        <template slot-scope="scope">
          <span v-if="scope.row.finishTime">{{ parseTime(scope.row.finishTime, '{y}-{m}-{d} {h}:{i}') }}</span>
          <span v-else>-</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:produceJobProcess:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:produceJobProcess:remove']"
          >{{ $t('btn.delete') }}</el-button>
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
        <el-form-item :label="$t('produceJobProcess.jobId')" prop="jobId">
          <el-input-number v-model="form.jobId" :min="1" :placeholder="$t('produceJobProcess.jobId')" />
        </el-form-item>
        <el-form-item :label="$t('produceJobProcess.processId')" prop="processId">
          <el-input-number v-model="form.processId" :min="1" :placeholder="$t('produceJobProcess.processId')" />
        </el-form-item>
        <el-form-item :label="$t('produceJobProcess.processSeq')" prop="processSeq">
          <el-input-number v-model="form.processSeq" :min="1" :placeholder="$t('produceJobProcess.processSeq')" />
        </el-form-item>
        <el-form-item :label="$t('produceJobProcess.employeeId')" prop="employeeId">
          <el-input-number v-model="form.employeeId" :min="0" :placeholder="$t('produceJobProcess.employeeId')" />
        </el-form-item>
        <el-form-item :label="$t('produceJobProcess.employeeName')" prop="employeeName">
          <el-input v-model="form.employeeName" :placeholder="$t('validation.enter', [$t('produceJobProcess.employeeName')])" />
        </el-form-item>
        <el-form-item :label="$t('produceJobProcess.inQty')" prop="inQty">
          <el-input-number v-model="form.inQty" :min="0" :placeholder="$t('produceJobProcess.inQty')" />
        </el-form-item>
        <el-form-item :label="$t('produceJobProcess.outQty')" prop="outQty">
          <el-input-number v-model="form.outQty" :min="0" :placeholder="$t('produceJobProcess.outQty')" />
        </el-form-item>
        <el-form-item :label="$t('produceJobProcess.defectQty')" prop="defectQty">
          <el-input-number v-model="form.defectQty" :min="0" :placeholder="$t('produceJobProcess.defectQty')" />
        </el-form-item>
        <el-form-item :label="$t('produceJobProcess.isOutsource')" prop="isOutsource">
          <el-radio-group v-model="form.isOutsource">
            <el-radio label="0">{{ $t('produceJobProcess.selfProduction') }}</el-radio>
            <el-radio label="1">{{ $t('produceJobProcess.outsourced') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('produceJobProcess.outsourceId')" prop="outsourceId">
          <el-input-number v-model="form.outsourceId" :min="0" :placeholder="$t('produceJobProcess.outsourceId')" />
        </el-form-item>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('validation.enter', [$t('system.remark')])" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
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
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      produceJobProcessList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobId: null,
        processId: null,
        employeeId: null,
        isOutsource: null,
        outsourceId: null
      },
      form: {}
    };
  },
  computed: {
    rules() {
      const t = this.$t;
      return {
        jobId: [
          { required: true, message: t('produceJobProcess.jobId') + t('validation.required'), trigger: "blur" }
        ],
        processId: [
          { required: true, message: t('produceJobProcess.processId') + t('validation.required'), trigger: "blur" }
        ],
        processSeq: [
          { required: true, message: t('produceJobProcess.processSeq') + t('validation.required'), trigger: "blur" }
        ],
        inQty: [
          { required: true, message: t('produceJobProcess.inQty') + t('validation.required'), trigger: "blur" }
        ]
      };
    }
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
      this.title = this.$t('produceJobProcess.addTitle');
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getProduceJobProcess(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('produceJobProcess.editTitle');
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          if (this.form.id != null) {
            updateProduceJobProcess(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          } else {
            addProduceJobProcess(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.addSuccess'));
              this.open = false;
              this.getList();
            }).finally(() => { this.submitLoading = false });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('msg.deleteConfirm', [ids])).then(function() {
        return delProduceJobProcess(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'));
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
