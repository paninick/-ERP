<template>
  <div class="app-container">
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
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('btn.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockList" border>
      <el-table-column label="ID" align="center" prop="id" width="70" />
      <el-table-column :label="$t('stock.materialId')" align="center" prop="materialId" width="90" />
      <el-table-column label="SKU ID" align="center" prop="skuId" width="80" />
      <el-table-column :label="$t('stock.warehouseId')" align="center" prop="warehouseId" width="90" />
      <el-table-column :label="$t('stock.invQty')" align="center" prop="invQty" width="110">
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
import { listStock } from '@/api/erp/stock'
import { listWarehouse } from '@/api/erp/warehouse'

export default {
  name: 'Stock',
  dicts: [],
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      stockList: [],
      warehouseList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialId: undefined,
        warehouseId: undefined
      }
    }
  },
  created() {
    this.getList()
    this.getWarehouses()
  },
  methods: {
    getList() {
      this.loading = true
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
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
    }
  }
}
</script>
