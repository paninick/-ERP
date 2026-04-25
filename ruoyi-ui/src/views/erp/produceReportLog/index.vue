<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="88px">
      <el-form-item label="工票ID" prop="jobId">
        <el-input v-model="queryParams.jobId" placeholder="工票ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="工序ID" prop="processId">
        <el-input v-model="queryParams.processId" placeholder="工序ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="员工ID" prop="employeeId">
        <el-input v-model="queryParams.employeeId" placeholder="员工ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="报工类型" prop="reportType">
        <el-select v-model="queryParams.reportType" clearable placeholder="全部">
          <el-option label="正常报工" value="NORMAL" />
          <el-option label="返修报工" value="REWORK" />
          <el-option label="外协报工" value="OUTSOURCE" />
        </el-select>
      </el-form-item>
      <el-form-item label="校验状态" prop="validationStatus">
        <el-select v-model="queryParams.validationStatus" clearable placeholder="全部">
          <el-option label="待校验" value="PENDING" />
          <el-option label="通过" value="PASS" />
          <el-option label="失败" value="FAIL" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['erp:produceReportLog:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['erp:produceReportLog:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="70" />
      <el-table-column label="工票ID" align="center" prop="jobId" width="90" />
      <el-table-column label="工序流转ID" align="center" prop="jobProcessId" width="100" />
      <el-table-column label="工序顺序" align="center" prop="processSeq" width="80" />
      <el-table-column label="员工姓名" align="center" prop="employeeName" />
      <el-table-column label="操作人" align="center" prop="operatorName" />
      <el-table-column label="报工数量" align="center" prop="reportQty" width="90" />
      <el-table-column label="完成数量" align="center" prop="completedQty" width="90" />
      <el-table-column label="次品数量" align="center" prop="defectQty" width="90" />
      <el-table-column label="损耗数量" align="center" prop="lossQty" width="90" />
      <el-table-column label="报工类型" align="center" prop="reportType" width="100">
        <template slot-scope="scope">
          <el-tag :type="reportTypeTag(scope.row.reportType)" size="small">{{ reportTypeLabel(scope.row.reportType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="校验状态" align="center" prop="validationStatus" width="100">
        <template slot-scope="scope">
          <el-tag :type="validationTag(scope.row.validationStatus)" size="small">{{ validationLabel(scope.row.validationStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="事件时间" align="center" prop="eventTime" width="160">
        <template slot-scope="scope"><span>{{ parseTime(scope.row.eventTime) }}</span></template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="160" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['erp:produceReportLog:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['erp:produceReportLog:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="工票ID" prop="jobId">
          <el-input-number v-model="form.jobId" :min="1" />
        </el-form-item>
        <el-form-item label="工序流转ID" prop="jobProcessId">
          <el-input-number v-model="form.jobProcessId" :min="1" />
        </el-form-item>
        <el-form-item label="员工ID" prop="employeeId">
          <el-input-number v-model="form.employeeId" :min="1" />
        </el-form-item>
        <el-form-item label="员工姓名" prop="employeeName">
          <el-input v-model="form.employeeName" />
        </el-form-item>
        <el-form-item label="报工数量" prop="reportQty">
          <el-input-number v-model="form.reportQty" :min="0" />
        </el-form-item>
        <el-form-item label="完成数量" prop="completedQty">
          <el-input-number v-model="form.completedQty" :min="0" />
        </el-form-item>
        <el-form-item label="次品数量" prop="defectQty">
          <el-input-number v-model="form.defectQty" :min="0" />
        </el-form-item>
        <el-form-item label="损耗数量" prop="lossQty">
          <el-input-number v-model="form.lossQty" :min="0" />
        </el-form-item>
        <el-form-item label="报工类型" prop="reportType">
          <el-select v-model="form.reportType" clearable>
            <el-option label="正常报工" value="NORMAL" />
            <el-option label="返修报工" value="REWORK" />
            <el-option label="外协报工" value="OUTSOURCE" />
          </el-select>
        </el-form-item>
        <el-form-item label="事件时间" prop="eventTime">
          <el-date-picker v-model="form.eventTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listProduceReportLog, getProduceReportLog, addProduceReportLog, updateProduceReportLog, delProduceReportLog } from '@/api/erp/produceReportLog'

export default {
  name: 'ProduceReportLog',
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      list: [],
      title: '',
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1, pageSize: 10,
        jobId: null, processId: null, employeeId: null,
        reportType: null, validationStatus: null
      },
      form: {},
      rules: {
        jobId:     [{ required: true, message: '工票ID不能为空', trigger: 'blur' }],
        reportQty: [{ required: true, message: '报工数量不能为空', trigger: 'blur' }]
      }
    }
  },
  created() { this.getList() },
  methods: {
    reportTypeTag(v) { return { NORMAL: 'success', REWORK: 'warning', OUTSOURCE: '' }[v] || 'info' },
    reportTypeLabel(v) { return { NORMAL: '正常报工', REWORK: '返修报工', OUTSOURCE: '外协报工' }[v] || v },
    validationTag(v) { return { PENDING: 'info', PASS: 'success', FAIL: 'danger' }[v] || 'info' },
    validationLabel(v) { return { PENDING: '待校验', PASS: '通过', FAIL: '失败' }[v] || v },
    getList() {
      this.loading = true
      listProduceReportLog(this.queryParams).then(r => {
        this.list = r.rows; this.total = r.total; this.loading = false
      })
    },
    cancel() { this.open = false; this.reset() },
    reset() {
      this.form = { id: null, jobId: null, jobProcessId: null, processId: null, processSeq: null,
        employeeId: null, employeeName: null, reportQty: 0, completedQty: 0, defectQty: 0,
        lossQty: 0, reportType: 'NORMAL', eventTime: null, remark: null }
      this.resetForm('form')
    },
    handleQuery() { this.queryParams.pageNum = 1; this.getList() },
    resetQuery() { this.resetForm('queryForm'); this.handleQuery() },
    handleSelectionChange(s) { this.ids = s.map(i => i.id); this.single = s.length !== 1; this.multiple = !s.length },
    handleAdd() { this.reset(); this.open = true; this.title = '新增报工流水' },
    handleUpdate(row) {
      this.reset()
      getProduceReportLog(row.id || this.ids[0]).then(r => { this.form = r.data; this.open = true; this.title = '修改报工流水' })
    },
    submitForm() {
      this.$refs.form.validate(valid => {
        if (!valid) return
        this.submitLoading = true
        const action = this.form.id ? updateProduceReportLog : addProduceReportLog
        action(this.form).then(() => {
          this.$modal.msgSuccess(this.form.id ? '修改成功' : '新增成功')
          this.open = false; this.getList()
        }).finally(() => { this.submitLoading = false })
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('确认删除？').then(() => delProduceReportLog(ids)).then(() => {
        this.getList(); this.$modal.msgSuccess('删除成功')
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/produceReportLog/export', { ...this.queryParams }, `produceReportLog_${Date.now()}.xlsx`)
    }
  }
}
</script>
