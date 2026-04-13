<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="打样类型" prop="sampleType">
        <el-select v-model="queryParams.sampleType" placeholder="请选择打样类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="样品款式" prop="styleType">
        <el-select v-model="queryParams.styleType" placeholder="请选择样品款式" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_style"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="样品类型" prop="sampleCategoryType">
        <el-select v-model="queryParams.sampleCategoryType" placeholder="请选择样品类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="打样款号" prop="styleCode">
        <el-input
          v-model="queryParams.styleCode"
          placeholder="请输入款号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审批状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="申请时间" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          size="small"
        />
      </el-form-item>
      <el-form-item label="业务员" prop="salesName">
        <el-select v-model="queryParams.salesName" placeholder="请选择业务员" clearable>
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
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:notice:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="overviewList">
      <el-table-column label="审批状态" align="center" prop="auditStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="customerName" :show-overflow-tooltip="true" />
      <el-table-column label="打样类型" align="center" prop="sampleType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_type" :value="scope.row.sampleType"/>
        </template>
      </el-table-column>
      <el-table-column label="样品款式" align="center" prop="styleType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_style" :value="scope.row.styleType"/>
        </template>
      </el-table-column>
      <el-table-column label="样品类型" align="center" prop="sampleCategoryType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_category" :value="scope.row.sampleCategoryType"/>
        </template>
      </el-table-column>
      <el-table-column label="打样款号" align="center" prop="styleCode" width="120" />
      <el-table-column label="大货款号" align="center" prop="bulkOrderNo" width="120" />
      <el-table-column label="要求交期" align="center" prop="dueDate" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="制版人" align="center" prop="auditByNickName" />
      <el-table-column label="打样人" align="center" prop="salesName" />
      <el-table-column label="打样耗时" align="center" prop="remark" />
      <el-table-column label="业务员" align="center" prop="salesName" />
      <el-table-column label="申请时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="打样编号" align="center" prop="sampleNo" width="130" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="100">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listNotice } from "@/api/erp/notice"

export default {
  name: "Overview",
  dicts: ['erp_sample_style', 'erp_sample_category', 'erp_sample_audit_status', 'erp_sample_type'],
  data() {
    return {
      loading: true,
      total: 0,
      overviewList: [],
      showSearch: true,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        auditStatus: null,
        customerName: null,
        sampleType: null,
        styleType: null,
        sampleCategoryType: null,
        styleCode: null,
        salesName: null
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listNotice(this.queryParams).then(response => {
        this.overviewList = response.rows
        this.total = response.total
        this.loading = false
      })
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
      this.$message.info("查看打样详情: " + row.sampleNo)
    },
    handleExport() {
      this.download('erp/notice/export', {
        ...this.queryParams
      }, `overview_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
