<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('planMaterial.planId')" prop="planId">
        <el-input
          v-model="queryParams.planId"
          :placeholder="$t('validation.enter', [$t('planMaterial.planId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planMaterial.salesItemId')" prop="salesItemId">
        <el-input
          v-model="queryParams.salesItemId"
          :placeholder="$t('validation.enter', [$t('planMaterial.salesItemId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planMaterial.materialType') + ' ' + $t('planMaterial.materialTypeHint')" prop="materialType">
        <el-select v-model="queryParams.materialType" :placeholder="$t('validation.select', [$t('planMaterial.materialType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_material_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('planMaterial.materialId')" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          :placeholder="$t('validation.enter', [$t('planMaterial.materialId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planMaterial.color')" prop="color">
        <el-input
          v-model="queryParams.color"
          :placeholder="$t('validation.enter', [$t('planMaterial.color')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planMaterial.unitConsumption')" prop="unitConsumption">
        <el-input
          v-model="queryParams.unitConsumption"
          :placeholder="$t('validation.enter', [$t('planMaterial.unitConsumption')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planMaterial.wastage')" prop="wastage">
        <el-input
          v-model="queryParams.wastage"
          :placeholder="$t('validation.enter', [$t('planMaterial.wastage')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planMaterial.planQuantity')" prop="planQuantity">
        <el-input
          v-model="queryParams.planQuantity"
          :placeholder="$t('validation.enter', [$t('planMaterial.planQuantity')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planMaterial.totalQuantity')" prop="totalQuantity">
        <el-input
          v-model="queryParams.totalQuantity"
          :placeholder="$t('validation.enter', [$t('planMaterial.totalQuantity')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('planMaterial.materialStatus')" prop="materialStatus">
        <el-select v-model="queryParams.materialStatus" :placeholder="$t('validation.select', [$t('planMaterial.materialStatus')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_plan_material_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('planMaterial.inventoryStatus')" prop="inventoryStatus">
        <el-select v-model="queryParams.inventoryStatus" :placeholder="$t('validation.select', [$t('planMaterial.inventoryStatus')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_plan_inventory_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
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
          v-hasPermi="['erp:planmaterial:add']"
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
          v-hasPermi="['erp:planmaterial:edit']"
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
          v-hasPermi="['erp:planmaterial:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:planmaterial:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="planmaterialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column :label="$t('planMaterial.planId')" align="center" prop="planId" />
      <el-table-column :label="$t('planMaterial.salesItemId')" align="center" prop="salesItemId" />
      <el-table-column :label="$t('planMaterial.materialType') + ' ' + $t('planMaterial.materialTypeHint')" align="center" prop="materialType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_material_type" :value="scope.row.materialType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('planMaterial.materialId')" align="center" prop="materialId" />
      <el-table-column :label="$t('planMaterial.color')" align="center" prop="color" />
      <el-table-column :label="$t('planMaterial.unitConsumption')" align="center" prop="unitConsumption" />
      <el-table-column :label="$t('planMaterial.lossType')" align="center" prop="lossType" />
      <el-table-column :label="$t('planMaterial.wastage')" align="center" prop="wastage" />
      <el-table-column :label="$t('planMaterial.planQuantity')" align="center" prop="planQuantity" />
      <el-table-column :label="$t('planMaterial.totalQuantity')" align="center" prop="totalQuantity" />
      <el-table-column :label="$t('planMaterial.materialStatus')" align="center" prop="materialStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_plan_material_status" :value="scope.row.materialStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('planMaterial.inventoryStatus')" align="center" prop="inventoryStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_plan_inventory_status" :value="scope.row.inventoryStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:planmaterial:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:planmaterial:remove']"
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

    <!-- 添加或修改生产计划物料明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('planMaterial.planId')" prop="planId">
          <el-input v-model="form.planId" :placeholder="$t('validation.enter', [$t('planMaterial.planId')])" />
        </el-form-item>
        <el-form-item :label="$t('planMaterial.salesItemId')" prop="salesItemId">
          <el-input v-model="form.salesItemId" :placeholder="$t('validation.enter', [$t('planMaterial.salesItemId')])" />
        </el-form-item>
        <el-form-item :label="$t('planMaterial.materialType') + ' ' + $t('planMaterial.materialTypeHint')" prop="materialType">
          <el-select v-model="form.materialType" :placeholder="$t('validation.select', [$t('planMaterial.materialType')])">
            <el-option
              v-for="dict in dict.type.erp_material_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('planMaterial.materialId')" prop="materialId">
          <el-input v-model="form.materialId" :placeholder="$t('validation.enter', [$t('planMaterial.materialId')])" />
        </el-form-item>
        <el-form-item :label="$t('planMaterial.color')" prop="color">
          <el-input v-model="form.color" :placeholder="$t('validation.enter', [$t('planMaterial.color')])" />
        </el-form-item>
        <el-form-item :label="$t('planMaterial.unitConsumption')" prop="unitConsumption">
          <el-input v-model="form.unitConsumption" :placeholder="$t('validation.enter', [$t('planMaterial.unitConsumption')])" />
        </el-form-item>
        <el-form-item :label="$t('planMaterial.wastage')" prop="wastage">
          <el-input v-model="form.wastage" :placeholder="$t('validation.enter', [$t('planMaterial.wastage')])" />
        </el-form-item>
        <el-form-item :label="$t('planMaterial.planQuantity')" prop="planQuantity">
          <el-input v-model="form.planQuantity" :placeholder="$t('validation.enter', [$t('planMaterial.planQuantity')])" />
        </el-form-item>
        <el-form-item :label="$t('planMaterial.totalQuantity')" prop="totalQuantity">
          <el-input v-model="form.totalQuantity" :placeholder="$t('validation.enter', [$t('planMaterial.totalQuantity')])" />
        </el-form-item>
        <el-form-item :label="$t('planMaterial.materialStatus')" prop="materialStatus">
          <el-select v-model="form.materialStatus" :placeholder="$t('validation.select', [$t('planMaterial.materialStatus')])">
            <el-option
              v-for="dict in dict.type.erp_plan_material_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('planMaterial.inventoryStatus')" prop="inventoryStatus">
          <el-select v-model="form.inventoryStatus" :placeholder="$t('validation.select', [$t('planMaterial.inventoryStatus')])">
            <el-option
              v-for="dict in dict.type.erp_plan_inventory_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
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
  </div>
</template>

<script>
import { listPlanmaterial, getPlanmaterial, delPlanmaterial, addPlanmaterial, updatePlanmaterial } from "@/api/erp/planmaterial"

export default {
  name: "Planmaterial",
  dicts: ['erp_plan_inventory_status', 'erp_material_type', 'erp_plan_material_status'],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      planmaterialList: [],
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        planId: null,
        salesItemId: null,
        materialType: null,
        materialId: null,
        color: null,
        unitConsumption: null,
        lossType: null,
        wastage: null,
        planQuantity: null,
        totalQuantity: null,
        materialStatus: null,
        inventoryStatus: null,
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
      listPlanmaterial(this.queryParams).then(response => {
        this.planmaterialList = response.rows
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
        planId: null,
        salesItemId: null,
        materialType: null,
        materialId: null,
        color: null,
        unitConsumption: null,
        lossType: null,
        wastage: null,
        planQuantity: null,
        totalQuantity: null,
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
      this.title = this.$t('planMaterial.addTitle')
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPlanmaterial(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('planMaterial.editTitle')
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updatePlanmaterial(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addPlanmaterial(this.form).then(response => {
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
      this.$modal.confirm(this.$t('planMaterial.destroyConfirm', [ids])).then(function() {
        return delPlanmaterial(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/planmaterial/export', {
        ...this.queryParams
      }, `planmaterial_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
