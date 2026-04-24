<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('purchase.supplier')" prop="supplierId">
        <el-select v-model="queryParams.supplierId" :placeholder="$t('purchase.selectSupplier')" clearable
          filterable clearable remote :remote-method="filterSupplier" :loading="supplierLoading">
          <el-option
            v-for="item in supplierOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('purchase.sn')" prop="sn">
        <el-input
          v-model="queryParams.sn"
          :placeholder="$t('purchase.enterNo')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('purchase.type')" prop="type">
        <el-select v-model="queryParams.type" :placeholder="$t('purchase.selectType')" clearable>
          <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value" />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('purchase.purchaseStatus')" prop="status">
        <el-select v-model="queryParams.status" :placeholder="$t('purchase.selectStatus')" clearable>
          <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
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
          v-hasPermi="['erp:purchase:add']"
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
          v-hasPermi="['erp:purchase:edit']"
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
          v-hasPermi="['erp:purchase:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:purchase:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('purchase.type')" align="center" prop="type" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.type === '原材料' ? 'primary' : scope.row.type === '辅料' ? 'warning' : 'info'" size="small">{{ scope.row.type }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('purchase.bulkOrderNo')" align="center" prop="bulkOrderNo" width="180" show-overflow-tooltip />
      <el-table-column :label="$t('purchase.description')" align="center" prop="description" min-width="200" show-overflow-tooltip />
      <el-table-column :label="$t('purchase.expectedDeliveryDate')" align="center" prop="expectedDeliveryDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expectedDeliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('purchase.purchaseName')" align="center" prop="purchaseName" width="100" />
      <el-table-column :label="$t('purchase.confirmTime')" align="center" prop="confirmTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.confirmTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('purchase.purchaseStatus')" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status === '已完成' ? 'success' : scope.row.status === '待确认' ? 'warning' : scope.row.status === '已取消' ? 'danger' : 'info'" size="small">{{ scope.row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column :label="$t('purchase.sn')" align="center" prop="sn" width="140" show-overflow-tooltip />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:purchase:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:purchase:remove']"
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

    <!-- 添加或修改采购单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('purchase.supplier')" prop="supplierId" required>
              <el-select v-model="form.supplierId" :placeholder="$t('purchase.selectSupplier')" clearable
                filterable clearable remote :remote-method="filterSupplier" :loading="supplierLoading">
                <el-option
                  v-for="item in supplierOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('purchase.sn')" prop="sn">
              <el-input v-model="form.sn" :placeholder="$t('purchase.autoGenerateHint')" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('purchase.type')" prop="type" required>
              <el-select v-model="form.type" :placeholder="$t('purchase.selectType')" clearable>
                <el-option v-for="item in typeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('purchase.bulkOrderNo')" prop="bulkOrderNo">
              <el-input v-model="form.bulkOrderNo" :placeholder="$t('purchase.enterBulkOrderNo')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('purchase.description')" prop="description">
          <el-input v-model="form.description" type="textarea" :placeholder="$t('purchase.enterDescription')" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('purchase.expectedDeliveryDate')" prop="expectedDeliveryDate" required>
              <el-date-picker clearable
                v-model="form.expectedDeliveryDate"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('purchase.selectDeliveryDate')">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('purchase.purchaseName')" prop="purchaseUserId">
              <el-select v-model="form.purchaseUserId" :placeholder="$t('purchase.selectPurchaseName')" clearable
                filterable clearable remote :remote-method="filterUser" :loading="userLoading">
                <el-option
                  v-for="item in userOptions"
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
            <el-form-item :label="$t('purchase.confirmTime')" prop="confirmTime">
              <el-date-picker clearable
                v-model="form.confirmTime"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('purchase.selectConfirmTime')">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('purchase.purchaseStatus')" prop="status">
              <el-select v-model="form.status" :placeholder="$t('purchase.selectStatus')" clearable>
                <el-option v-for="item in statusOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('purchase.amount')" prop="amount">
          <el-input-number v-model="form.amount" :precision="2" :min="0" :placeholder="$t('purchase.enterAmount')" />
        </el-form-item>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('purchase.enterRemark')" />
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
import { listPurchase, getPurchase, delPurchase, addPurchase, updatePurchase } from "@/api/erp/purchase"
import { listSupplier } from "@/api/erp/supplier"
import { listUser } from "@/api/system/user"

export default {
  name: "Purchase",
  dicts: [],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      purchaseList: [],
      submitLoading: false,
      title: "",
      open: false,
      // 供应商选项
      supplierOptions: [],
      supplierLoading: false,
      userOptions: [],
      userLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        bulkOrderNo: null,
        purchaseName: null,
        status: null,
        sn: null,
        supplierId: null
      },
      form: {}
    }
  },
  computed: {
    typeOptions() {
      return [
        { label: this.$t('purchase.rawMaterial'), value: '原材料' },
        { label: this.$t('purchase.auxiliary'), value: '辅料' },
        { label: this.$t('purchase.finished'), value: '成品' },
        { label: this.$t('purchase.packaging'), value: '包材' }
      ]
    },
    statusOptions() {
      return [
        { label: this.$t('status.pendingConfirm'), value: '待确认' },
        { label: this.$t('status.confirmed'), value: '已确认' },
        { label: this.$t('status.completed'), value: '已完成' },
        { label: this.$t('status.cancelled'), value: '已取消' }
      ]
    },
    rules() {
      return {
        type: [
          { required: true, message: this.$t('validation.required'), trigger: "change" }
        ],
        supplierId: [
          { required: true, message: this.$t('validation.required'), trigger: "change" }
        ],
        expectedDeliveryDate: [
          { required: true, message: this.$t('validation.required'), trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 过滤供应商 */
    filterSupplier(query) {
      if (!query) {
        this.supplierOptions = []
        return
      }
      this.supplierLoading = true
      listSupplier({ pageNum: 1, pageSize: 20, supplierName: query }).then(response => {
        this.supplierOptions = response.rows.map(r => ({
          value: r.id,
          label: r.supplierName
        }))
        this.supplierLoading = false
      }).catch(() => {
        this.supplierLoading = false
      })
    },
    /** 过滤用户 */
    filterUser(query) {
      if (!query) {
        this.userOptions = []
        return
      }
      this.userLoading = true
      listUser({ pageNum: 1, pageSize: 50 }).then(response => {
        const users = (response.rows || []).filter(u =>
          (u.nickName && u.nickName.includes(query)) ||
          (u.userName && u.userName.includes(query))
        )
        this.userOptions = users.map(u => ({
          value: u.userId,
          label: u.nickName + '(' + u.userName + ')'
        }))
      }).finally(() => {
        this.userLoading = false
      })
    },
    getList() {
      this.loading = true
      listPurchase(this.queryParams).then(response => {
        this.purchaseList = response.rows
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
        type: null,
        bulkOrderNo: null,
        description: null,
        expectedDeliveryDate: null,
        purchaseName: null,
        confirmTime: null,
        status: null,
        sn: null,
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
      this.title = this.$t('purchase.addTitle')
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPurchase(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('purchase.editTitle')
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updatePurchase(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addPurchase(this.form).then(response => {
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
        return delPurchase(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/purchase/export', {
        ...this.queryParams
      }, `purchase_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
