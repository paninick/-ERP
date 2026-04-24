<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('employee.code')" prop="employeeCode">
        <el-input
          v-model="queryParams.employeeCode"
          :placeholder="$t('validation.enter', [$t('employee.code')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('employee.name')" prop="employeeName">
        <el-input
          v-model="queryParams.employeeName"
          :placeholder="$t('validation.enter', [$t('employee.name')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('employee.department')" prop="department">
        <el-input
          v-model="queryParams.department"
          :placeholder="$t('validation.enter', [$t('employee.department')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          v-hasPermi="['erp:employee:add']"
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
          v-hasPermi="['erp:employee:edit']"
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
          v-hasPermi="['erp:employee:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:employee:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="employeeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('employee.code')" align="center" prop="employeeCode" />
      <el-table-column :label="$t('employee.name')" align="center" prop="employeeName" />
      <el-table-column :label="$t('employee.phone')" align="center" prop="phone" />
      <el-table-column :label="$t('employee.department')" align="center" prop="department" />
      <el-table-column :label="$t('employee.station')" align="center" prop="station" />
      <el-table-column :label="$t('employee.entryDate')" align="center" prop="entryDate" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.entryDate }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('employee.status')" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_user_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.createTime')" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:employee:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:employee:remove']"
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

    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('employee.code')" prop="employeeCode">
          <el-input v-model="form.employeeCode" :placeholder="$t('validation.enter', [$t('employee.code')])" />
        </el-form-item>
        <el-form-item :label="$t('employee.name')" prop="employeeName">
          <el-input v-model="form.employeeName" :placeholder="$t('validation.enter', [$t('employee.name')])" />
        </el-form-item>
        <el-form-item :label="$t('employee.phone')" prop="phone">
          <el-input v-model="form.phone" :placeholder="$t('validation.enter', [$t('employee.phone')])" />
        </el-form-item>
        <el-form-item :label="$t('employee.idCard')" prop="idCard">
          <el-input v-model="form.idCard" :placeholder="$t('validation.enter', [$t('employee.idCard')])" />
        </el-form-item>
        <el-form-item :label="$t('employee.department')" prop="department">
          <el-input v-model="form.department" :placeholder="$t('validation.enter', [$t('employee.department')])" />
        </el-form-item>
        <el-form-item :label="$t('employee.station')" prop="station">
          <el-input v-model="form.station" :placeholder="$t('validation.enter', [$t('employee.station')])" />
        </el-form-item>
        <el-form-item :label="$t('employee.entryDate')" prop="entryDate">
          <el-date-picker
            v-model="form.entryDate"
            type="date"
            :placeholder="$t('employee.entryDate')"
            value-format="yyyy-MM-dd"
            align="right"
          />
        </el-form-item>
        <el-form-item :label="$t('employee.leaveDate')" prop="leaveDate">
          <el-date-picker
            v-model="form.leaveDate"
            type="date"
            :placeholder="$t('employee.leaveDate')"
            value-format="yyyy-MM-dd"
            align="right"
          />
        </el-form-item>
        <el-form-item :label="$t('employee.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">{{ $t('employee.active') }}</el-radio>
            <el-radio label="1">{{ $t('employee.inactive') }}</el-radio>
          </el-radio-group>
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
import { listEmployee, getEmployee, delEmployee, addEmployee, updateEmployee } from "@/api/erp/employee"

export default {
  name: "Employee",
  dicts: ['sys_user_status'],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      employeeList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        employeeCode: null,
        employeeName: null,
        department: null,
        status: null
      },
      form: {}
    }
  },
  computed: {
    rules() {
      return {
        employeeName: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ],
        employeeCode: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listEmployee(this.queryParams).then(response => {
        this.employeeList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: null,
        employeeCode: null,
        employeeName: null,
        phone: null,
        idCard: null,
        department: null,
        station: null,
        entryDate: null,
        leaveDate: null,
        status: '0',
        remark: null
      }
      this.resetForm("form")
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = this.$t('employee.addTitle')
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getEmployee(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('employee.editTitle')
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateEmployee(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.updateSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addEmployee(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.addSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          }
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm(this.$t('msg.deleteConfirm', [ids])).then(function() {
        return delEmployee(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/employee/export', {
        ...this.queryParams
      }, `employee_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
