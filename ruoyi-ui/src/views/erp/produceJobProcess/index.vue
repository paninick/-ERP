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

    <!-- 查询条件补充：工序状态 -->
    <el-row style="margin-bottom:8px">
      <el-form :model="queryParams" :inline="true" size="small">
        <el-form-item label="工序状态" prop="processStatus">
          <el-select v-model="queryParams.processStatus" clearable placeholder="全部" @change="handleQuery">
            <el-option v-for="s in statusOptions" :key="s.value" :label="s.label" :value="s.value" />
          </el-select>
        </el-form-item>
        <el-form-item label="工票号" prop="jobNo">
          <el-input v-model="queryParams.jobNo" clearable placeholder="工票号" @keyup.enter.native="handleQuery" />
        </el-form-item>
      </el-form>
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
      <el-table-column label="状态" align="center" prop="processStatus" width="110">
        <template slot-scope="scope">
          <el-tag :type="statusTagType(scope.row.processStatus)" size="small">
            {{ statusLabel(scope.row.processStatus) }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('produceJobProcess.isOutsource')" align="center" prop="isOutsource" width="80">
        <template slot-scope="scope">
          <el-tag :type="scope.row.isOutsource === '0' ? 'primary' : 'warning'" size="small">
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
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width" width="220">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['erp:produceJobProcess:edit']">{{ $t('btn.edit') }}</el-button>
          <el-button size="mini" type="text" icon="el-icon-video-pause" @click="handleSkip(scope.row)" v-hasPermi="['erp:produceJobProcess:edit']" :disabled="scope.row.processStatus === 'PASS' || scope.row.processStatus === 'SKIP'">跳过</el-button>
          <el-button size="mini" type="text" icon="el-icon-refresh-right" @click="handleRework(scope.row)" v-hasPermi="['erp:produceJobProcess:add']" :disabled="scope.row.processStatus !== 'FAIL'">返修</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['erp:produceJobProcess:remove']">{{ $t('btn.delete') }}</el-button>
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
        <el-form-item label="工序状态" prop="processStatus">
          <el-select v-model="form.processStatus" clearable>
            <el-option v-for="s in statusOptions" :key="s.value" :label="s.label" :value="s.value" />
          </el-select>
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

    <!-- 跳过工序对话框 -->
    <el-dialog title="跳过工序" :visible.sync="skipOpen" width="40%" append-to-body>
      <el-form ref="skipForm" :model="skipForm" label-width="100px">
        <el-form-item label="跳过原因" prop="skipReason" :rules="[{required:true,message:'请填写跳过原因',trigger:'blur'}]">
          <el-input v-model="skipForm.skipReason" type="textarea" rows="3" placeholder="请填写跳过原因" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" :loading="submitLoading" @click="submitSkip">确认跳过</el-button>
        <el-button @click="skipOpen = false">取消</el-button>
      </div>
    </el-dialog>

    <!-- 插入返修工序对话框 -->
    <el-dialog title="插入返修工序" :visible.sync="reworkOpen" width="40%" append-to-body>
      <el-form ref="reworkForm" :model="reworkForm" label-width="100px">
        <el-form-item label="工序ID" prop="processId" :rules="[{required:true,message:'请填写工序ID',trigger:'blur'}]">
          <el-input-number v-model="reworkForm.processId" :min="1" />
        </el-form-item>
        <el-form-item label="返修原因" prop="insertReason">
          <el-input v-model="reworkForm.insertReason" type="textarea" rows="3" placeholder="返修原因（可选）" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" :loading="submitLoading" @click="submitRework">确认插入</el-button>
        <el-button @click="reworkOpen = false">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listProduceJobProcess, getProduceJobProcess, addProduceJobProcess,
  updateProduceJobProcess, delProduceJobProcess, skipProcess, insertReworkProcess
} from "@/api/erp/produceJobProcess";

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
      skipOpen: false,
      reworkOpen: false,
      submitLoading: false,
      skipForm: { id: null, skipReason: null },
      reworkForm: { jobId: null, processId: null, reworkSourceProcessId: null, insertReason: null },
      statusOptions: [
        { value: 'PENDING',    label: '待开工' },
        { value: 'RUNNING',    label: '进行中' },
        { value: 'WAIT_CHECK', label: '完工待检' },
        { value: 'PASS',       label: '检验通过' },
        { value: 'FAIL',       label: '检验不合格' },
        { value: 'OUTSOURCE',  label: '外发中' }
      ],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        jobId: null,
        processId: null,
        employeeId: null,
        isOutsource: null,
        processStatus: null,
        jobNo: null
      },
      form: {}
    };
  },
  computed: {
    rules() {
      const t = this.$t;
      return {
        jobId:     [{ required: true, message: t('produceJobProcess.jobId') + t('validation.required'), trigger: "blur" }],
        processId: [{ required: true, message: t('produceJobProcess.processId') + t('validation.required'), trigger: "blur" }],
        processSeq:[{ required: true, message: t('produceJobProcess.processSeq') + t('validation.required'), trigger: "blur" }],
        inQty:     [{ required: true, message: t('produceJobProcess.inQty') + t('validation.required'), trigger: "blur" }]
      };
    }
  },
  created() {
    this.getList();
  },
  methods: {
    statusTagType(status) {
      const map = { PENDING: 'info', RUNNING: 'primary', WAIT_CHECK: 'warning', PASS: 'success', FAIL: 'danger', OUTSOURCE: '' };
      return map[status] || 'info';
    },
    statusLabel(status) {
      const opt = this.statusOptions.find(s => s.value === status);
      return opt ? opt.label : (status || '-');
    },
    getList() {
      this.loading = true;
      listProduceJobProcess(this.queryParams).then(response => {
        this.produceJobProcessList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    reset() {
      this.form = {
        id: null, jobId: null, processId: null, processSeq: null,
        employeeId: null, employeeName: null, inQty: 0, outQty: 0,
        defectQty: 0, startTime: null, finishTime: null,
        isOutsource: "0", outsourceId: null, processStatus: null, remark: null
      };
      this.resetForm("form");
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
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('produceJobProcess.addTitle');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getProduceJobProcess(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = this.$t('produceJobProcess.editTitle');
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) return;
        this.submitLoading = true;
        const action = this.form.id != null ? updateProduceJobProcess : addProduceJobProcess;
        const msg = this.form.id != null ? this.$t('msg.editSuccess') : this.$t('msg.addSuccess');
        action(this.form).then(() => {
          this.$modal.msgSuccess(msg);
          this.open = false;
          this.getList();
        }).finally(() => { this.submitLoading = false; });
      });
    },
    handleSkip(row) {
      this.skipForm = { id: row.id, skipReason: null };
      this.skipOpen = true;
    },
    submitSkip() {
      this.$refs["skipForm"].validate(valid => {
        if (!valid) return;
        this.submitLoading = true;
        skipProcess(this.skipForm).then(() => {
          this.$modal.msgSuccess('已跳过');
          this.skipOpen = false;
          this.getList();
        }).finally(() => { this.submitLoading = false; });
      });
    },
    handleRework(row) {
      this.reworkForm = { jobId: row.jobId, processId: null, reworkSourceProcessId: row.id, insertReason: null };
      this.reworkOpen = true;
    },
    submitRework() {
      this.$refs["reworkForm"].validate(valid => {
        if (!valid) return;
        this.submitLoading = true;
        insertReworkProcess(this.reworkForm).then(() => {
          this.$modal.msgSuccess('返修工序已插入');
          this.reworkOpen = false;
          this.getList();
        }).finally(() => { this.submitLoading = false; });
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm(this.$t('msg.deleteConfirm', [ids])).then(() => {
        return delProduceJobProcess(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'));
      }).catch(() => {});
    },
    handleExport() {
      this.download('erp/produceJobProcess/export', { ...this.queryParams }, `produceJobProcess_${new Date().getTime()}.xlsx`);
    }
  }
};
</script>
