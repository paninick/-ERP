<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="产品名称" prop="productName">
        <el-input
          v-model="queryParams.productName"
          placeholder="请输入产品名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="产品编号" prop="productNo">
        <el-input
          v-model="queryParams.productNo"
          placeholder="产品编号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockList">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="类型" align="center" prop="type" />
      <el-table-column label="产品名称" align="center" prop="productName" />
      <el-table-column label="产品编号" align="center" prop="productNo" />
      <el-table-column label="主料成分" align="center" prop="mainComposition" />
      <el-table-column label="主料门幅" align="center" prop="mainWidth" />
      <el-table-column label="主料克重" align="center" prop="mainWeight" />
      <el-table-column label="辅料成分" align="center" prop="auxiliaryComposition" />
      <el-table-column label="辅料规格" align="center" prop="auxiliarySpec" />
      <el-table-column label="计量单位" align="center" prop="unit" />
      <el-table-column label="颜色" align="center" prop="color" />
      <el-table-column label="库存" align="center" prop="stock" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
          >查看</el-button>
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
  </div>
</template>

<script>
import { listStock, getStock, delStock, addStock, updateStock } from "@/api/erp/stock"

export default {
  name: "Stock",
  dicts: [],
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      stockList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        productName: null,
        productNo: null,
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      this.stockList = [
        {
          id: 1,
          type: '面料',
          productName: '摇粒绒',
          productNo: '',
          mainComposition: '',
          mainWidth: '150 cm',
          mainWeight: '300 g/m²',
          auxiliaryComposition: '',
          auxiliarySpec: '',
          unit: '米',
          color: '黑',
          stock: '506.88'
        },
        {
          id: 2,
          type: '面料',
          productName: '摇粒绒',
          productNo: '',
          mainComposition: '',
          mainWidth: '150 cm',
          mainWeight: '300 g/m²',
          auxiliaryComposition: '',
          auxiliarySpec: '',
          unit: '米',
          color: '米白',
          stock: '394.24'
        },
        {
          id: 3,
          type: '面料',
          productName: '摇粒绒',
          productNo: '',
          mainComposition: '',
          mainWidth: '150 cm',
          mainWeight: '300 g/m²',
          auxiliaryComposition: '',
          auxiliarySpec: '',
          unit: '米',
          color: '浅咖',
          stock: '506.88'
        },
        {
          id: 4,
          type: '纱线',
          productName: '全腈冰岛毛',
          productNo: '',
          mainComposition: '',
          mainWidth: '',
          mainWeight: '',
          auxiliaryComposition: '',
          auxiliarySpec: '',
          unit: '千克',
          color: '米',
          stock: '50.6'
        },
        {
          id: 5,
          type: '纱线',
          productName: '全腈冰岛毛',
          productNo: '',
          mainComposition: '',
          mainWidth: '',
          mainWeight: '',
          auxiliaryComposition: '',
          auxiliarySpec: '',
          unit: '千克',
          color: '摩卡',
          stock: '40.04'
        },
        {
          id: 6,
          type: '纱线',
          productName: '全腈冰岛毛',
          productNo: '',
          mainComposition: '',
          mainWidth: '',
          mainWeight: '',
          auxiliaryComposition: '',
          auxiliarySpec: '',
          unit: '千克',
          color: '蓝',
          stock: '50.6'
        },
        {
          id: 7,
          type: '纱线',
          productName: '全腈冰岛毛',
          productNo: '',
          mainComposition: '',
          mainWidth: '',
          mainWeight: '',
          auxiliaryComposition: '',
          auxiliarySpec: '',
          unit: '千克',
          color: '艾青',
          stock: '40.04'
        }
      ]
      this.total = 7
      this.loading = false
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleView(row) {
      this.$message.info('查看功能待实现')
    }
  }
}
</script>
