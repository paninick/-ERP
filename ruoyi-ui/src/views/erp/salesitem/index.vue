<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('salesItem.salesOrderId')" prop="salesOrderId">
        <el-select v-model="queryParams.salesOrderId" :placeholder="$t('salesItem.selectSalesOrder')" clearable
          filterable clearable remote :remote-method="filterSalesOrder" loading="salesOrderLoading">
          <el-option
            v-for="item in salesOrderOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('salesItem.color')" prop="color">
        <el-input
          v-model="queryParams.color"
          :placeholder="$t('validation.enter', [$t('salesItem.color')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('salesItem.size')" prop="size">
        <el-input
          v-model="queryParams.size"
          :placeholder="$t('validation.enter', [$t('salesItem.size')])"
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
          v-hasPermi="['erp:salesitem:add']"
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
          v-hasPermi="['erp:salesitem:edit']"
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
          v-hasPermi="['erp:salesitem:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:salesitem:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salesitemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('salesItem.seq')" align="center" prop="id" width="60" />
      <el-table-column :label="$t('salesItem.salesOrder')" align="center" prop="salesOrderNo" />
      <el-table-column :label="$t('salesItem.color')" align="center" prop="color" width="100" />
      <el-table-column :label="$t('salesItem.size')" align="center" prop="size" width="80" />
      <el-table-column :label="$t('salesItem.orderQuantity')" align="center" prop="orderQuantity" width="100" />
      <el-table-column :label="$t('salesItem.planQuantity')" align="center" prop="planQuantity" width="100" />
      <el-table-column :label="$t('salesItem.inboundAmount')" align="center" prop="inboundAmount" width="100" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:salesitem:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:salesitem:remove']"
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

    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('salesItem.salesOrder')" prop="salesOrderId" required>
          <el-select v-model="form.salesOrderId" :placeholder="$t('salesItem.selectSalesOrder')" clearable
            filterable clearable remote :remote-method="filterSalesOrder" loading="salesOrderLoading">
            <el-option
              v-for="item in salesOrderOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('salesItem.color')" prop="color" required>
              <el-input v-model="form.color" :placeholder="$t('validation.enter', [$t('salesItem.color')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('salesItem.size')" prop="size" required>
              <el-input v-model="form.size" :placeholder="$t('validation.enter', [$t('salesItem.size')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('salesItem.orderQuantity')" prop="orderQuantity" required>
              <el-input-number v-model="form.orderQuantity" :precision="2" :min="0" :placeholder="$t('validation.enter', [$t('salesItem.orderQuantity')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('salesItem.planQuantity')" prop="planQuantity">
              <el-input-number v-model="form.planQuantity" :precision="2" :min="0" :placeholder="$t('validation.enter', [$t('salesItem.planQuantity')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('salesItem.inboundAmount')" prop="inboundAmount">
          <el-input-number v-model="form.inboundAmount" :precision="2" :min="0" :placeholder="$t('validation.enter', [$t('salesItem.inboundAmount')])" />
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
import { listSalesitem, getSalesitem, delSalesitem, addSalesitem, updateSalesitem } from "@/api/erp/salesitem"
import { listSales } from "@/api/erp/sales"

export default {
  name: "Salesitem",
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      salesitemList: [],
      title: "",
      open: false,
      submitLoading: false,
      salesOrderOptions: [],
      salesOrderLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salesOrderId: null,
        color: null,
        size: null
      },
      form: {}
    }
  },
  computed: {
    rules() {
      return {
        salesOrderId: [
          { required: true, message: () => this.$t('validation.enter', [this.$t('salesItem.salesOrder')]), trigger: "change" }
        ],
        color: [
          { required: true, message: () => this.$t('validation.enter', [this.$t('salesItem.color')]), trigger: "blur" }
        ],
        size: [
          { required: true, message: () => this.$t('validation.enter', [this.$t('salesItem.size')]), trigger: "blur" }
        ],
        orderQuantity: [
          { required: true, message: () => this.$t('validation.enter', [this.$t('salesItem.orderQuantity')]), trigger: "blur" }
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
    getList() {
      this.loading = true
      listSalesitem(this.queryParams).then(response => {
        this.salesitemList = response.rows
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
        salesOrderId: null,
        noticeId: null,
        techId: null,
        color: null,
        size: null,
        orderQuantity: null,
        planQuantity: null,
        inboundAmount: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = this.$t('salesItem.addTitle')
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSalesitem(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('salesItem.editTitle')
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateSalesitem(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addSalesitem(this.form).then(response => {
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
        return delSalesitem(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/salesitem/export', {
        ...this.queryParams
      }, `salesitem_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
