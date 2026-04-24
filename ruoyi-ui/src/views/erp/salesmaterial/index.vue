<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('salesMaterial.salesOrderId')" prop="salesOrderId">
        <el-input
          v-model="queryParams.salesOrderId"
          :placeholder="$t('validation.enter', [$t('salesMaterial.salesOrderId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('salesMaterial.materialType')" prop="materialType">
        <el-select v-model="queryParams.materialType" :placeholder="$t('validation.select', [$t('salesMaterial.materialType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_material_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('salesMaterial.materialId')" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          :placeholder="$t('validation.enter', [$t('salesMaterial.materialId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('salesMaterial.color')" prop="color">
        <el-input
          v-model="queryParams.color"
          :placeholder="$t('validation.enter', [$t('salesMaterial.color')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('salesMaterial.unitConsumption')" prop="unitConsumption">
        <el-input
          v-model="queryParams.unitConsumption"
          :placeholder="$t('validation.enter', [$t('salesMaterial.unitConsumption')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('salesMaterial.lossType')" prop="lossType">
        <el-select v-model="queryParams.lossType" :placeholder="$t('validation.select', [$t('salesMaterial.lossType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_loss_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('salesMaterial.wastage')" prop="wastage">
        <el-input
          v-model="queryParams.wastage"
          :placeholder="$t('validation.enter', [$t('salesMaterial.wastage')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('salesMaterial.planQuantity')" prop="planQuantity">
        <el-input
          v-model="queryParams.planQuantity"
          :placeholder="$t('validation.enter', [$t('salesMaterial.planQuantity')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('salesMaterial.totalQuantity')" prop="totalQuantity">
        <el-input
          v-model="queryParams.totalQuantity"
          :placeholder="$t('validation.enter', [$t('salesMaterial.totalQuantity')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('salesMaterial.totalDesc')" prop="totalDesc">
        <el-input
          v-model="queryParams.totalDesc"
          :placeholder="$t('validation.enter', [$t('salesMaterial.totalDesc')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('salesMaterial.inboundQuantity')" prop="inboundQuantity">
        <el-input
          v-model="queryParams.inboundQuantity"
          :placeholder="$t('validation.enter', [$t('salesMaterial.inboundQuantity')])"
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
          v-hasPermi="['erp:salesmaterial:add']"
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
          v-hasPermi="['erp:salesmaterial:edit']"
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
          v-hasPermi="['erp:salesmaterial:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:salesmaterial:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salesmaterialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column :label="$t('salesMaterial.salesOrderId')" align="center" prop="salesOrderId" />
      <el-table-column :label="$t('salesMaterial.materialType')" align="center" prop="materialType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_material_type" :value="scope.row.materialType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('salesMaterial.materialId')" align="center" prop="materialId" />
      <el-table-column :label="$t('salesMaterial.color')" align="center" prop="color" />
      <el-table-column :label="$t('salesMaterial.unitConsumption')" align="center" prop="unitConsumption" />
      <el-table-column :label="$t('salesMaterial.lossType')" align="center" prop="lossType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_loss_type" :value="scope.row.lossType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('salesMaterial.wastage')" align="center" prop="wastage" />
      <el-table-column :label="$t('salesMaterial.planQuantity')" align="center" prop="planQuantity" />
      <el-table-column :label="$t('salesMaterial.totalQuantity')" align="center" prop="totalQuantity" />
      <el-table-column :label="$t('salesMaterial.totalDesc')" align="center" prop="totalDesc" />
      <el-table-column :label="$t('salesMaterial.inboundQuantity')" align="center" prop="inboundQuantity" />
      <el-table-column :label="$t('salesMaterial.materialStatus')" align="center" prop="materialStatus" />
      <el-table-column :label="$t('salesMaterial.inventoryStatus')" align="center" prop="inventoryStatus" />
      <el-table-column :label="$t('system.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:salesmaterial:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:salesmaterial:remove']"
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

    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('salesMaterial.salesOrder')" prop="salesOrderId" required>
          <el-select v-model="form.salesOrderId" :placeholder="$t('salesMaterial.selectSalesOrder')" clearable
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
            <el-form-item :label="$t('salesMaterial.materialType')" prop="materialType" required>
              <el-select v-model="form.materialType" :placeholder="$t('salesMaterial.selectMaterialType')">
                <el-option
                  v-for="dict in dict.type.erp_material_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('salesMaterial.material')" prop="materialId" required>
              <el-select v-model="form.materialId" :placeholder="$t('salesMaterial.selectMaterial')" clearable
                filterable clearable remote :remote-method="filterMaterial" loading="materialLoading">
                <el-option
                  v-for="item in materialOptions"
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
            <el-form-item :label="$t('salesMaterial.color')" prop="color" required>
              <el-input v-model="form.color" :placeholder="$t('validation.enter', [$t('salesMaterial.color')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('salesMaterial.unitConsumption')" prop="unitConsumption" required>
              <el-input-number v-model="form.unitConsumption" :precision="4" :min="0" :placeholder="$t('validation.enter', [$t('salesMaterial.unitConsumption')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('salesMaterial.lossType')" prop="lossType" required>
              <el-select v-model="form.lossType" :placeholder="$t('salesMaterial.selectLossType')">
                <el-option
                  v-for="dict in dict.type.erp_loss_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('salesMaterial.wastage')" prop="wastage">
              <el-input-number v-model="form.wastage" :precision="4" :min="0" :placeholder="$t('validation.enter', [$t('salesMaterial.wastage')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('salesMaterial.planQuantity')" prop="planQuantity" required>
              <el-input-number v-model="form.planQuantity" :precision="2" :min="0" :placeholder="$t('validation.enter', [$t('salesMaterial.planQuantity')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('salesMaterial.totalQuantity')" prop="totalQuantity">
              <el-input-number v-model="form.totalQuantity" :precision="2" :min="0" :placeholder="$t('validation.enter', [$t('salesMaterial.totalQuantity')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('salesMaterial.totalDesc')" prop="totalDesc">
          <el-input v-model="form.totalDesc" :placeholder="$t('validation.enter', [$t('salesMaterial.totalDesc')])" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('salesMaterial.inboundQuantity')" prop="inboundQuantity">
              <el-input-number v-model="form.inboundQuantity" :precision="2" :min="0" :placeholder="$t('validation.enter', [$t('salesMaterial.inboundQuantity')])" />
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
import { listSalesmaterial, getSalesmaterial, delSalesmaterial, addSalesmaterial, updateSalesmaterial } from "@/api/erp/salesmaterial"
import { listSales } from "@/api/erp/sales"
import { listMaterial as listMainMaterial } from "@/api/erp/material"

export default {
  name: "Salesmaterial",
  dicts: ['erp_loss_type', 'erp_material_type'],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      salesmaterialList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salesOrderId: null,
        materialType: null,
        materialId: null,
        color: null,
        unitConsumption: null,
        lossType: null,
        wastage: null,
        planQuantity: null,
        totalQuantity: null,
        totalDesc: null,
        inboundQuantity: null,
        materialStatus: null,
        inventoryStatus: null,
      },
      salesOrderOptions: [],
      salesOrderLoading: false,
      materialOptions: [],
      materialLoading: false,
      form: {}
    }
  },
  computed: {
    rules() {
      return {
        salesOrderId: [
          { required: true, message: () => this.$t('validation.enter', [this.$t('salesMaterial.salesOrder')]), trigger: "change" }
        ],
        materialType: [
          { required: true, message: () => this.$t('validation.select', [this.$t('salesMaterial.materialType')]), trigger: "change" }
        ],
        materialId: [
          { required: true, message: () => this.$t('validation.select', [this.$t('salesMaterial.material')]), trigger: "change" }
        ],
        unitConsumption: [
          { required: true, message: () => this.$t('validation.enter', [this.$t('salesMaterial.unitConsumption')]), trigger: "blur" }
        ],
        lossType: [
          { required: true, message: () => this.$t('validation.select', [this.$t('salesMaterial.lossType')]), trigger: "change" }
        ],
        planQuantity: [
          { required: true, message: () => this.$t('validation.enter', [this.$t('salesMaterial.planQuantity')]), trigger: "blur" }
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
    filterMaterial(query) {
      if (!query) {
        this.materialOptions = []
        return
      }
      this.materialLoading = true
      listMainMaterial({ pageNum: 1, pageSize: 20, materialName: query }).then(response => {
        this.materialOptions = response.rows.map(r => ({
          value: r.id,
          label: r.materialName
        }))
        this.materialLoading = false
      }).catch(() => {
        this.materialLoading = false
      })
    },
    getList() {
      this.loading = true
      listSalesmaterial(this.queryParams).then(response => {
        this.salesmaterialList = response.rows
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
        materialType: null,
        materialId: null,
        color: null,
        unitConsumption: null,
        lossType: null,
        wastage: null,
        planQuantity: null,
        totalQuantity: null,
        totalDesc: null,
        inboundQuantity: null,
        materialStatus: null,
        inventoryStatus: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
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
      this.title = this.$t('salesMaterial.addTitle')
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSalesmaterial(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('salesMaterial.editTitle')
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateSalesmaterial(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addSalesmaterial(this.form).then(response => {
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
        return delSalesmaterial(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/salesmaterial/export', {
        ...this.queryParams
      }, `salesmaterial_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
