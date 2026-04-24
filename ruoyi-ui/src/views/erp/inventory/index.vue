<template>
  <div class="app-container">
    <el-form
      ref="queryForm"
      :model="queryParams"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="90px"
    >
      <el-form-item label="库存ID" prop="id">
        <el-input
          v-model="queryParams.id"
          placeholder="请输入库存ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="仓库" prop="warehouseId">
        <el-select
          v-model="queryParams.warehouseId"
          placeholder="请选择仓库"
          filterable
          clearable
        >
          <el-option
            v-for="item in warehouseOptions"
            :key="item.id"
            :label="formatWarehouseOption(item)"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="物料" prop="materialId">
        <el-select
          v-model="queryParams.materialId"
          placeholder="请选择物料"
          filterable
          clearable
          @change="handleQueryMaterialChange"
        >
          <el-option
            v-for="item in materialOptions"
            :key="item.id"
            :label="formatMaterialOption(item)"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="SKU" prop="skuId">
        <el-select
          v-model="queryParams.skuId"
          placeholder="请先选择物料"
          filterable
          clearable
          :loading="skuLoading"
          :disabled="!queryParams.materialId"
        >
          <el-option
            v-for="item in querySkuOptions"
            :key="item.id"
            :label="formatSkuOption(item)"
            :value="item.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
          v-hasPermi="['erp:inventory:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:inventory:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:inventory:remove']"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="inventoryList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="库存ID" align="center" prop="id" width="90" />
      <el-table-column label="仓库" align="center" min-width="160">
        <template slot-scope="scope">
          <span>{{ getWarehouseLabel(scope.row.warehouseId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="物料" align="center" min-width="220" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ getMaterialLabel(scope.row.materialId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="SKU" align="center" min-width="220" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ getSkuLabel(scope.row.skuId) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="可用库存" align="center" prop="invQty" min-width="120" />
      <el-table-column label="锁定库存" align="center" prop="lockQty" min-width="120" />
      <el-table-column label="版本号" align="center" prop="version" width="90" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="备注" align="center" prop="remark" min-width="180" :show-overflow-tooltip="true" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="140">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:inventory:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:inventory:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <el-dialog :title="title" :visible.sync="open" width="680px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-row :gutter="20">
          <el-col :span="12">
            <el-form-item label="仓库" prop="warehouseId">
              <el-select v-model="form.warehouseId" placeholder="请选择仓库" filterable style="width: 100%">
                <el-option
                  v-for="item in warehouseOptions"
                  :key="item.id"
                  :label="formatWarehouseOption(item)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="物料" prop="materialId">
              <el-select
                v-model="form.materialId"
                placeholder="请选择物料"
                filterable
                style="width: 100%"
                @change="handleFormMaterialChange"
              >
                <el-option
                  v-for="item in materialOptions"
                  :key="item.id"
                  :label="formatMaterialOption(item)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="SKU" prop="skuId">
              <el-select
                v-model="form.skuId"
                placeholder="请先选择物料"
                filterable
                clearable
                style="width: 100%"
                :loading="skuLoading"
                :disabled="!form.materialId"
              >
                <el-option
                  v-for="item in formSkuOptions"
                  :key="item.id"
                  :label="formatSkuOption(item)"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="版本号" prop="version">
              <el-input-number v-model="form.version" :min="0" :precision="0" controls-position="right" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="可用库存" prop="invQty">
              <el-input-number
                v-model="form.invQty"
                :min="0"
                :precision="6"
                :step="1"
                controls-position="right"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="锁定库存" prop="lockQty">
              <el-input-number
                v-model="form.lockQty"
                :min="0"
                :precision="6"
                :step="1"
                controls-position="right"
              />
            </el-form-item>
          </el-col>
          <el-col :span="24">
            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" :rows="3" placeholder="请输入备注" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { addInventory, delInventory, getInventory, listInventory, updateInventory } from "@/api/erp/inventory"
import { listWarehouse } from "@/api/erp/warehouse"
import { listMaterial } from "@/api/erp/material"
import { listMaterialSkuOptions } from "@/api/erp/materialSku"

export default {
  name: "Inventory",
  data() {
    return {
      loading: true,
      skuLoading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      inventoryList: [],
      warehouseOptions: [],
      materialOptions: [],
      querySkuOptions: [],
      formSkuOptions: [],
      skuCache: {},
      skuLabelMap: {},
      title: "",
      open: false,
      submitLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        id: undefined,
        warehouseId: undefined,
        skuId: undefined,
        materialId: undefined
      },
      form: {},
      rules: {
        warehouseId: [{ required: true, message: "请选择仓库", trigger: "change" }],
        materialId: [{ required: true, message: "请选择物料", trigger: "change" }],
        skuId: [{ required: true, message: "请选择SKU", trigger: "change" }],
        invQty: [{ required: true, message: "可用库存不能为空", trigger: "change" }],
        lockQty: [{ required: true, message: "锁定库存不能为空", trigger: "change" }],
        version: [{ required: true, message: "版本号不能为空", trigger: "change" }]
      }
    }
  },
  created() {
    this.reset()
    this.loadOptions()
    this.getList()
  },
  methods: {
    loadOptions() {
      listWarehouse({ pageNum: 1, pageSize: 9999 }).then(response => {
        this.warehouseOptions = response.rows || []
      })
      listMaterial({ pageNum: 1, pageSize: 9999 }).then(response => {
        this.materialOptions = response.rows || []
      })
    },
    rememberSkuOptions(items) {
      ;(items || []).forEach(item => {
        this.$set(this.skuLabelMap, Number(item.id), item)
      })
    },
    syncSkuOptions(materialId, target) {
      if (!materialId) {
        if (target === "query") {
          this.querySkuOptions = []
          this.queryParams.skuId = undefined
        }
        if (target === "form") {
          this.formSkuOptions = []
          this.form.skuId = undefined
        }
        return Promise.resolve([])
      }

      const cacheKey = Number(materialId)
      if (this.skuCache[cacheKey]) {
        const items = this.skuCache[cacheKey]
        this.rememberSkuOptions(items)
        if (target === "query") {
          this.querySkuOptions = items
        }
        if (target === "form") {
          this.formSkuOptions = items
        }
        return Promise.resolve(items)
      }

      this.skuLoading = true
      return listMaterialSkuOptions({ materialId: cacheKey }).then(response => {
        const items = response.data || []
        this.$set(this.skuCache, cacheKey, items)
        this.rememberSkuOptions(items)
        if (target === "query") {
          this.querySkuOptions = items
        }
        if (target === "form") {
          this.formSkuOptions = items
        }
        return items
      }).finally(() => {
        this.skuLoading = false
      })
    },
    ensureSkuLabels(rows) {
      const materialIds = [...new Set((rows || []).map(item => item.materialId).filter(Boolean))]
      const missingMaterialIds = materialIds.filter(id => !this.skuCache[Number(id)])
      if (!missingMaterialIds.length) {
        return Promise.resolve()
      }
      return Promise.all(missingMaterialIds.map(id => this.syncSkuOptions(id))).then(() => undefined)
    },
    getList() {
      this.loading = true
      const query = { ...this.queryParams }
      if (query.id) {
        query.warehouseId = undefined
        query.materialId = undefined
        query.skuId = undefined
      }
      listInventory(query).then(response => {
        let rows = response.rows || []
        if (query.id) {
          const id = Number(query.id)
          rows = rows.filter(item => item.id === id)
        }
        this.inventoryList = rows
        this.total = query.id ? rows.length : response.total
        return this.ensureSkuLabels(rows)
      }).finally(() => {
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: undefined,
        warehouseId: undefined,
        skuId: undefined,
        materialId: undefined,
        invQty: 0,
        lockQty: 0,
        version: 0,
        remark: undefined
      }
      this.formSkuOptions = []
      this.resetForm("form")
    },
    formatWarehouseOption(item) {
      if (!item) {
        return "-"
      }
      return item.code ? `${item.name} (${item.code})` : item.name
    },
    formatMaterialOption(item) {
      if (!item) {
        return "-"
      }
      return item.mainMaterialNo ? `${item.name} (${item.mainMaterialNo})` : item.name
    },
    formatSkuOption(item) {
      if (!item) {
        return "-"
      }
      if (item.skuName && item.skuCode) {
        return `${item.skuName} (${item.skuCode})`
      }
      return item.skuName || item.skuCode || `SKU #${item.id}`
    },
    getWarehouseLabel(id) {
      const item = this.warehouseOptions.find(option => Number(option.id) === Number(id))
      return item ? this.formatWarehouseOption(item) : id
    },
    getMaterialLabel(id) {
      const item = this.materialOptions.find(option => Number(option.id) === Number(id))
      return item ? this.formatMaterialOption(item) : id
    },
    getSkuLabel(id) {
      const item = this.skuLabelMap[Number(id)]
      return item ? this.formatSkuOption(item) : id
    },
    handleQueryMaterialChange(value) {
      this.queryParams.skuId = undefined
      this.syncSkuOptions(value, "query")
    },
    handleFormMaterialChange(value) {
      const currentSkuId = this.form.skuId
      this.syncSkuOptions(value, "form").then(items => {
        const exists = (items || []).some(item => Number(item.id) === Number(currentSkuId))
        if (!exists) {
          this.form.skuId = undefined
        }
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.querySkuOptions = []
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
      this.title = "新增库存盘点"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids[0]
      getInventory(id).then(response => {
        this.form = {
          ...this.form,
          ...response.data
        }
        return this.syncSkuOptions(this.form.materialId, "form")
      }).then(() => {
        this.open = true
        this.title = "修改库存盘点"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (!valid) {
          return
        }
        this.submitLoading = true
        const payload = {
          ...this.form,
          warehouseId: Number(this.form.warehouseId),
          skuId: Number(this.form.skuId),
          materialId: Number(this.form.materialId)
        }
        const request = payload.id ? updateInventory(payload) : addInventory(payload)
        request.then(() => {
          this.$modal.msgSuccess(payload.id ? "修改成功" : "新增成功")
          this.open = false
          this.getList()
        }).finally(() => { this.submitLoading = false })
      })
    },
    handleDelete(row) {
      const ids = row.id ? [row.id] : this.ids
      this.$modal.confirm('是否确认删除库存记录编号为"' + ids.join(",") + '"的数据项？').then(() => {
        return delInventory(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    }
  }
}
</script>

<style lang="scss" scoped>
.el-input-number {
  width: 100%;
}
</style>
