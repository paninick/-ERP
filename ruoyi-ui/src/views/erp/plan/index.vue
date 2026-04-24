<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="80px">
      <el-form-item :label="$t('plan.salesOrderId')" prop="salesOrderId">
        <el-select v-model="queryParams.salesOrderId" :placeholder="$t('plan.selectSalesOrder')" clearable
          filterable clearable remote :remote-method="filterSalesOrder" loading="salesOrderLoading">
          <el-option
            v-for="item in salesOrderOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('plan.customerId')" prop="customerId">
        <el-select v-model="queryParams.customerId" :placeholder="$t('plan.selectCustomer')" clearable
          filterable clearable remote :remote-method="filterCustomer" loading="customerLoading">
          <el-option
            v-for="item in customerOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('plan.bulkOrderNo')" prop="bulkOrderNo">
        <el-input
          v-model="queryParams.bulkOrderNo"
          :placeholder="$t('validation.enter', [$t('plan.bulkOrderNo')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('plan.sampleStyleNo')" prop="sampleStyleNo">
        <el-input
          v-model="queryParams.sampleStyleNo"
          :placeholder="$t('validation.enter', [$t('plan.sampleStyleNo')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('plan.styleCode')" prop="styleCode">
        <el-input
          v-model="queryParams.styleCode"
          :placeholder="$t('validation.enter', [$t('plan.styleCode')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('plan.produceStatus')" prop="produceStatus">
        <el-input
          v-model="queryParams.produceStatus"
          :placeholder="$t('validation.enter', [$t('plan.produceStatus')])"
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
          v-hasPermi="['erp:plan:add']"
        >{{ $t('plan.addTitle') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:plan:edit']"
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
          v-hasPermi="['erp:plan:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:plan:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
        >{{ $t('plan.importTitle') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('plan.produceStatus')" align="center" prop="produceStatus" width="100" />
      <el-table-column :label="$t('plan.type')" align="center" prop="type" width="80" />
      <el-table-column :label="$t('plan.planNo')" align="center" prop="planNo" width="160" show-overflow-tooltip />
      <el-table-column :label="$t('plan.bulkOrderNo')" align="center" prop="bulkOrderNo" width="140" show-overflow-tooltip />
      <el-table-column :label="$t('plan.sampleStyleNo')" align="center" prop="sampleStyleNo" width="120" show-overflow-tooltip />
      <el-table-column :label="$t('plan.styleCode')" align="center" prop="styleCode" width="140" show-overflow-tooltip />
      <el-table-column :label="$t('plan.customerName')" align="center" prop="customerName" width="120" show-overflow-tooltip />
      <el-table-column :label="$t('plan.styleCategory')" align="center" prop="styleCategory" width="100" show-overflow-tooltip />
      <el-table-column :label="$t('plan.salesName')" align="center" prop="salesName" width="80" />
      <el-table-column :label="$t('plan.materialArrivalDate')" align="center" prop="materialArrivalDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.materialArrivalDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('plan.preProcessDate')" align="center" prop="preProcessDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.preProcessDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('plan.inspectionDate')" align="center" prop="inspectionDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inspectionDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('plan.inBoundDate')" align="center" prop="inBoundDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.inBoundDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('plan.shippingDate')" align="center" prop="shippingDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.shippingDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('plan.postProcessDate')" align="center" prop="postProcessDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.postProcessDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('plan.dueDate')" align="center" prop="dueDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('plan.auditStatus')" align="center" prop="auditStatus" width="100" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:plan:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:plan:remove']"
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

    <!-- 添加或修改生产计划对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="900px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('plan.salesOrderId')" prop="salesOrderId">
              <el-select v-model="form.salesOrderId" :placeholder="$t('plan.selectSalesOrder')" clearable
                filterable clearable remote :remote-method="filterSalesOrder" loading="salesOrderLoading">
                <el-option
                  v-for="item in salesOrderOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('plan.customerId')" prop="customerId">
              <el-select v-model="form.customerId" :placeholder="$t('plan.selectCustomer')" clearable
                filterable clearable remote :remote-method="filterCustomer" loading="customerLoading">
                <el-option
                  v-for="item in customerOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('plan.planNo')" prop="planNo">
              <el-input v-model="form.planNo" :placeholder="$t('plan.planNoAuto')" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('plan.bulkOrderNo')" prop="bulkOrderNo" required>
              <el-input v-model="form.bulkOrderNo" :placeholder="$t('validation.enter', [$t('plan.bulkOrderNo')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('plan.sampleStyleNo')" prop="sampleStyleNo">
              <el-input v-model="form.sampleStyleNo" :placeholder="$t('validation.enter', [$t('plan.sampleStyleNo')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('plan.styleCode')" prop="styleCode">
              <el-input v-model="form.styleCode" :placeholder="$t('plan.styleCodeAuto')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('plan.customerName')" prop="customerName">
              <el-input v-model="form.customerName" :placeholder="$t('validation.enter', [$t('plan.customerName')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('plan.styleCategory')" prop="styleCategory">
              <el-input v-model="form.styleCategory" :placeholder="$t('validation.enter', [$t('plan.styleCategory')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('plan.produceStatus')" prop="produceStatus">
              <el-input v-model="form.produceStatus" :placeholder="$t('validation.enter', [$t('plan.produceStatus')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('plan.materialArrivalDate')" prop="materialArrivalDate">
              <el-date-picker clearable
                v-model="form.materialArrivalDate"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('validation.select', [$t('plan.materialArrivalDate')])">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('plan.preProcessDate')" prop="preProcessDate">
              <el-date-picker clearable
                v-model="form.preProcessDate"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('validation.select', [$t('plan.preProcessDate')])">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('plan.inspectionDate')" prop="inspectionDate">
              <el-date-picker clearable
                v-model="form.inspectionDate"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('validation.select', [$t('plan.inspectionDate')])">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('plan.inBoundDate')" prop="inBoundDate">
              <el-date-picker clearable
                v-model="form.inBoundDate"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('validation.select', [$t('plan.inBoundDate')])">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('plan.shippingDate')" prop="shippingDate">
              <el-date-picker clearable
                v-model="form.shippingDate"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('validation.select', [$t('plan.shippingDate')])">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('plan.postProcessDate')" prop="postProcessDate">
              <el-date-picker clearable
                v-model="form.postProcessDate"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('validation.select', [$t('plan.postProcessDate')])">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('plan.dueDate')" prop="dueDate">
              <el-date-picker clearable
                v-model="form.dueDate"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('validation.select', [$t('plan.dueDate')])">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('plan.auditStatus')" prop="auditStatus">
              <el-input v-model="form.auditStatus" :placeholder="$t('validation.enter', [$t('plan.auditStatus')])" />
            </el-form-item>
          </el-col>
        </el-row>
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
import { listPlan, getPlan, delPlan, addPlan, updatePlan } from "@/api/erp/plan"
import { listSales } from "@/api/erp/sales"
import { listCustomer } from "@/api/erp/customer"

export default {
  name: "ProducePlan",
  dicts: [],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      planList: [],
      title: "",
      open: false,
      submitLoading: false,
      salesOrderOptions: [],
      salesOrderLoading: false,
      customerOptions: [],
      customerLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salesOrderId: null,
        customerId: null,
        bulkOrderNo: null,
        sampleStyleNo: null,
        styleCode: null,
        customerName: null,
        produceStatus: null,
        salesName: null
      },
      form: {}
    }
  },
  computed: {
    rules() {
      return {
        planNo: [
          { required: true, message: () => this.$t('plan.planNo') + this.$t('validation.required'), trigger: "blur" }
        ],
        bulkOrderNo: [
          { required: true, message: () => this.$t('plan.bulkOrderNo') + this.$t('validation.required'), trigger: "blur" }
        ],
        customerName: [
          { required: true, message: () => this.$t('plan.customerName') + this.$t('validation.required'), trigger: "blur" }
        ],
        dueDate: [
          { required: true, message: () => this.$t('plan.dueDate') + this.$t('validation.required'), trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    filterSalesOrder(query) {
      if (!query) {
        this.salesOrderOptions = []
        return
      }
      this.salesOrderLoading = true
      listSales({ pageNum: 1, pageSize: 20, salesNo: query }).then(response => {
        this.salesOrderOptions = response.rows.map(r => ({
          value: r.id,
          label: r.salesNo
        }))
        this.salesOrderLoading = false
      }).catch(() => {
        this.salesOrderLoading = false
      })
    },
    filterCustomer(query) {
      if (!query) {
        this.customerOptions = []
        return
      }
      this.customerLoading = true
      listCustomer({ pageNum: 1, pageSize: 20, customerName: query }).then(response => {
        this.customerOptions = response.rows.map(r => ({
          value: r.id,
          label: r.customerName
        }))
        this.customerLoading = false
      }).catch(() => {
        this.customerLoading = false
      })
    },
    getList() {
      this.loading = true
      listPlan(this.queryParams).then(response => {
        this.planList = response.rows
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
        produceStatus: null,
        type: null,
        planNo: null,
        bulkOrderNo: null,
        sampleStyleNo: null,
        styleCode: null,
        customerName: null,
        styleCategory: null,
        salesName: null,
        materialArrivalDate: null,
        preProcessDate: null,
        inspectionDate: null,
        inBoundDate: null,
        shippingDate: null,
        postProcessDate: null,
        dueDate: null,
        auditStatus: null,
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
      this.title = this.$t('plan.addTitle')
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPlan(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('plan.editTitle')
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updatePlan(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addPlan(this.form).then(response => {
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
      this.$modal.confirm(this.$t('plan.destroyConfirm', [ids])).then(function() {
        return delPlan(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/plan/export', {
        ...this.queryParams
      }, `plan_${new Date().getTime()}.xlsx`)
    },
    handleImport() {
      this.$message.info(this.$t('plan.importWip'))
    }
  }
}
</script>
