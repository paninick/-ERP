<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="工资单ID" prop="wageId">
        <el-input v-model="queryParams.wageId" placeholder="请输入工资单ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="员工ID" prop="employeeId">
        <el-input v-model="queryParams.employeeId" placeholder="请输入员工ID" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="工序名称" prop="processName">
        <el-input v-model="queryParams.processName" placeholder="请输入工序名称" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="工票ID" prop="jobId">
        <el-input v-model="queryParams.jobId" placeholder="请输入工票ID" clearable @keyup.enter.native="handleQuery" />
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
          v-hasPermi="['erp:piecewagedetail:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="piecewagedetailList">
      <el-table-column label="明细ID" align="center" prop="id" width="90" />
      <el-table-column label="工资单ID" align="center" prop="wageId" width="100" />
      <el-table-column label="员工ID" align="center" prop="employeeId" width="90" />
      <el-table-column label="工序名称" align="center" prop="processName" min-width="140" />
      <el-table-column label="工票ID" align="center" prop="jobId" width="90" />
      <el-table-column label="合格数量" align="center" prop="okQty" width="100" />
      <el-table-column label="次品数量" align="center" prop="defectQty" width="100" />
      <el-table-column label="工序单价" align="center" prop="processPrice" width="100" />
      <el-table-column label="应得工资" align="center" prop="shouldWage" width="100" />
      <el-table-column label="扣款" align="center" prop="deductWage" width="100" />
      <el-table-column label="实际工资" align="center" prop="actualWage" width="100" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
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
  </div>
</template>

<script>
import { listPiecewagedetail, exportPiecewagedetail } from "@/api/erp/piecewagedetail"

export default {
  name: "PieceWageDetail",
  data() {
    return {
      loading: true,
      showSearch: true,
      total: 0,
      piecewagedetailList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        wageId: undefined,
        employeeId: undefined,
        processName: undefined,
        jobId: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true
      listPiecewagedetail(this.queryParams).then(response => {
        this.piecewagedetailList = response.rows || []
        this.total = response.total || 0
      }).finally(() => {
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
    handleExport() {
      exportPiecewagedetail(this.queryParams).then(() => {
        this.download("erp/piecewagedetail/export", {
          ...this.queryParams
        }, `piecewagedetail_${new Date().getTime()}.xlsx`)
      }).catch(() => {})
    }
  }
}
</script>
