<template>
  <div class="app-container">
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
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('btn.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

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
        <template slot-scope="scope">
          <span style="font-weight: 600; color: var(--app-primary-color)">{{ scope.row.invQty }}</span>
        </template>
      </el-table-column>
      <el-table-column label="锁定库存" align="center" prop="lockQty" width="110">
        <template slot-scope="scope">
          <span style="color: var(--app-warning-color)">{{ scope.row.lockQty || 0 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="可用" align="center" width="110">
        <template slot-scope="scope">
          <span style="font-weight: 700; color: var(--app-success-color)">
            {{ (scope.row.invQty || 0) - (scope.row.lockQty || 0) }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160" />
      <el-table-column label="备注" align="center" prop="remark" :show-overflow-tooltip="true" />
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
import { listInventory, getInventory, delInventory, addInventory, updateInventory } from "@/api/erp/inventory"
import { listWarehouse } from "@/api/erp/warehouse"

export default {
  name: 'Stock',
  dicts: [],
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      inventoryList: [],
      warehouseOptions: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        warehouseId: null,
        materialId: null,
        skuId: null
      }
    }
  },
  created() {
    this.getWarehouseList()
    this.getList()
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
      listInventory(this.queryParams).then(response => {
        this.inventoryList = response.rows
        this.total = response.total
        this.loading = false
      }).catch(() => {
        this.loading = false
      })
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm('queryForm')
      this.handleQuery()
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
    }
  }
}
</script>
