<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('processPrice.processId')" prop="processId">
        <el-input
          v-model="queryParams.processId"
          :placeholder="$t('processPrice.enterProcessId')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('processPrice.employeeId')" prop="employeeId">
        <el-input
          v-model="queryParams.employeeId"
          :placeholder="$t('processPrice.enterEmployeeId')"
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
          v-hasPermi="['erp:processPrice:add']"
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
          v-hasPermi="['erp:processPrice:edit']"
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
          v-hasPermi="['erp:processPrice:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:processPrice:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processPriceList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('processPrice.processId')" align="center" prop="processId" />
      <el-table-column :label="$t('processPrice.employeeId')" align="center" prop="employeeId" />
      <el-table-column :label="$t('processPrice.price')" align="center" prop="price" />
      <el-table-column :label="$t('processPrice.effectiveDate')" align="center" prop="effectiveDate" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.effectiveDate }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('processPrice.expireDate')" align="center" prop="expireDate" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.expireDate }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('processPrice.status')" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:processPrice:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:processPrice:remove']"
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

    <!-- 添加或修改工序工价对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('processPrice.processId')" prop="processId">
          <el-input v-model="form.processId" :placeholder="$t('processPrice.enterProcessId')" />
        </el-form-item>
        <el-form-item :label="$t('processPrice.employeeId')" prop="employeeId">
          <el-input v-model="form.employeeId" :placeholder="$t('processPrice.enterEmployeeId')" />
          <span class="el-form-item__label-hint">{{ $t('processPrice.employeeHint') }}</span>
        </el-form-item>
        <el-form-item :label="$t('processPrice.price')" prop="price">
          <el-input-number v-model="form.price" :precision="4" :min="0" :placeholder="$t('processPrice.enterPrice')" />
        </el-form-item>
        <el-form-item :label="$t('processPrice.effectiveDate')" prop="effectiveDate">
          <el-date-picker
            v-model="form.effectiveDate"
            type="date"
            :placeholder="$t('processPrice.selectEffectiveDate')"
            value-format="yyyy-MM-dd"
            align="right"
          />
        </el-form-item>
        <el-form-item :label="$t('processPrice.expireDate')" prop="expireDate">
          <el-date-picker
            v-model="form.expireDate"
            type="date"
            :placeholder="$t('processPrice.selectExpireDate')"
            value-format="yyyy-MM-dd"
            align="right"
          />
        </el-form-item>
        <el-form-item :label="$t('processPrice.status')" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="0">{{ $t('processPrice.effective') }}</el-radio>
            <el-radio label="1">{{ $t('processPrice.expired') }}</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('processPrice.enterContent')" />
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
import { listProcessPrice, getProcessPrice, delProcessPrice, addProcessPrice, updateProcessPrice } from "@/api/erp/processPrice"

export default {
  name: "ProcessPrice",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      processPriceList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processId: null,
        employeeId: null,
        status: null
      },
      form: {}
    }
  },
  computed: {
    rules() {
      return {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listProcessPrice(this.queryParams).then(response => {
        this.processPriceList = response.rows
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
        processId: null,
        employeeId: null,
        price: null,
        effectiveDate: null,
        expireDate: null,
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
      this.title = this.$t('processPrice.addTitle')
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProcessPrice(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('processPrice.editTitle')
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateProcessPrice(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addProcessPrice(this.form).then(response => {
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
      this.$modal.confirm(this.$t('processPrice.deleteConfirm', [ids])).then(function() {
        return delProcessPrice(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/processPrice/export', {
        ...this.queryParams
      }, `processPrice_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
