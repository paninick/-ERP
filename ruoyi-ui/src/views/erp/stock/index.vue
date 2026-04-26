<template>
  <div class="app-container">
<<<<<<< Updated upstream
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="90px">
      <el-form-item :label="$t('stock.materialId')" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          :placeholder="$t('validation.enter', [$t('stock.materialId')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('stock.warehouse')" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" :placeholder="$t('stock.warehouse')" clearable filterable>
          <el-option v-for="w in warehouseList" :key="w.id" :label="w.warehouseName" :value="w.id" />
        </el-select>
=======
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="仓库" prop="warehouseId">
        <el-select v-model="queryParams.warehouseId" placeholder="请选择仓库" clearable style="width: 200px;">
          <el-option v-for="item in warehouseOptions" :key="item.id" :label="item.warehouseName" :value="item.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="物料ID" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          placeholder="请输入物料ID"
          clearable
          @keyup.enter.native="handleQuery"
        />
>>>>>>> Stashed changes
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('btn.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

<<<<<<< Updated upstream
    <el-table v-loading="loading" :data="stockList" border>
      <el-table-column label="ID" align="center" prop="id" width="70" />
      <el-table-column :label="$t('stock.materialId')" align="center" prop="materialId" width="90" />
      <el-table-column label="SKU ID" align="center" prop="skuId" width="80" />
      <el-table-column :label="$t('stock.warehouseId')" align="center" prop="warehouseId" width="90" />
      <el-table-column :label="$t('stock.invQty')" align="center" prop="invQty" width="110">
=======
    <el-table v-loading="loading" :data="inventoryList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="库存ID" align="center" prop="id" width="80" />
      <el-table-column label="仓库" align="center" prop="warehouseId" width="120">
        <template slot-scope="scope">
          {{ getWarehouseName(scope.row.warehouseId) }}
        </template>
      </el-table-column>
      <el-table-column label="物料ID" align="center" prop="materialId" width="100" />
      <el-table-column label="SKU ID" align="center" prop="skuId" width="100" />
      <el-table-column label="可用库存" align="center" prop="invQty" width="120" />
      <el-table-column label="锁定库存" align="center" prop="lockQty" width="120" />
      <el-table-column label="实际库存" align="center" width="120">
        <template slot-scope="scope">
          {{ (scope.row.invQty - scope.row.lockQty).toFixed(4) }}
        </template>
      </el-table-column>
      <el-table-column label="版本" align="center" prop="version" width="60" />
      <el-table-column label="更新时间" align="center" prop="updateTime" width="160" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
>>>>>>> Stashed changes
        <template slot-scope="scope">
          <span style="font-weight: 600; color: var(--app-primary-color)">{{ scope.row.invQty }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('stock.lockQty')" align="center" prop="lockQty" width="110">
        <template slot-scope="scope">
          <span style="color: var(--app-warning-color)">{{ scope.row.lockQty || 0 }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('stock.available')" align="center" width="110">
        <template slot-scope="scope">
          <span style="font-weight: 700; color: var(--app-success-color)">
            {{ (scope.row.invQty || 0) - (scope.row.lockQty || 0) }}
          </span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.createTime')" align="center" prop="createTime" width="160" />
      <el-table-column :label="$t('system.remark')" align="center" prop="remark" :show-overflow-tooltip="true" />
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
<<<<<<< Updated upstream
import { listStock } from '@/api/erp/stock'
import { listWarehouse } from '@/api/erp/warehouse'
=======
import { listInventory, getInventory, delInventory, addInventory, updateInventory } from "@/api/erp/inventory"
import { listWarehouse } from "@/api/erp/warehouse"
>>>>>>> Stashed changes

export default {
  name: 'Stock',
  dicts: [],
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
<<<<<<< Updated upstream
      stockList: [],
      warehouseList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialId: undefined,
        warehouseId: undefined
=======
      inventoryList: [],
      warehouseOptions: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        warehouseId: null,
        materialId: null,
        skuId: null
>>>>>>> Stashed changes
      }
    }
  },
  created() {
    this.getWarehouseList()
    this.getList()
    this.getWarehouses()
  },
  methods: {
    getWarehouseList() {
      listWarehouse({ pageNum: 1, pageSize: 100 }).then(response => {
        this.warehouseOptions = response.rows
      })
    },
    getWarehouseName(warehouseId) {
      const warehouse = this.warehouseOptions.find(w => w.id === warehouseId)
      return warehouse ? warehouse.warehouseName : warehouseId
    },
    getList() {
      this.loading = true
<<<<<<< Updated upstream
      listStock(this.queryParams).then(res => {
        this.stockList = res.rows || []
        this.total = res.total || 0
        this.loading = false
      }).catch(() => { this.loading = false })
    },
    getWarehouses() {
      listWarehouse({ pageSize: 999 }).then(res => {
        this.warehouseList = res.rows || []
      }).catch(() => {})
=======
      listInventory(this.queryParams).then(response => {
        this.inventoryList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
>>>>>>> Stashed changes
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
<<<<<<< Updated upstream
=======
    },
    handleView(row) {
      this.$modal.open({
        title: '库存详情',
        width: '600px',
        content: h => h('div', {
          style: { padding: '20px' }
        }, [
          h('p', `ID: ${row.id}`),
          h('p', `仓库ID: ${row.warehouseId}`),
          h('p', `SKU ID: ${row.skuId}`),
          h('p', `物料ID: ${row.materialId}`),
          h('p', `可用库存: ${row.invQty}`),
          h('p', `锁定库存: ${row.lockQty}`),
          h('p', `版本: ${row.version}`),
          h('p', `创建时间: ${row.createTime}`),
          h('p', `更新时间: ${row.updateTime}`)
        ])
      })
>>>>>>> Stashed changes
    }
  }
}
</script>
