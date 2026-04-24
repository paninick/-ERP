<template>
  <div class="app-container">
    <el-form ref="queryForm" :model="queryParams" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('piecewageDetail.wageId')" prop="wageId">
        <el-input v-model="queryParams.wageId" :placeholder="$t('validation.enter', [$t('piecewageDetail.wageId')])" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('piecewageDetail.employeeId')" prop="employeeId">
        <el-input v-model="queryParams.employeeId" :placeholder="$t('validation.enter', [$t('piecewageDetail.employeeId')])" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('piecewageDetail.processName')" prop="processName">
        <el-input v-model="queryParams.processName" :placeholder="$t('validation.enter', [$t('piecewageDetail.processName')])" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item :label="$t('piecewageDetail.jobId')" prop="jobId">
        <el-input v-model="queryParams.jobId" :placeholder="$t('validation.enter', [$t('piecewageDetail.jobId')])" clearable @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('btn.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
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
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" />
    </el-row>

    <el-table v-loading="loading" :data="piecewagedetailList">
      <el-table-column :label="$t('piecewageDetail.id')" align="center" prop="id" width="90" />
      <el-table-column :label="$t('piecewageDetail.wageId')" align="center" prop="wageId" width="100" />
      <el-table-column :label="$t('piecewageDetail.employeeId')" align="center" prop="employeeId" width="90" />
      <el-table-column :label="$t('piecewageDetail.processName')" align="center" prop="processName" min-width="140" />
      <el-table-column :label="$t('piecewageDetail.jobId')" align="center" prop="jobId" width="90" />
      <el-table-column :label="$t('piecewageDetail.okQty')" align="center" prop="okQty" width="100" />
      <el-table-column :label="$t('piecewageDetail.defectQty')" align="center" prop="defectQty" width="100" />
      <el-table-column :label="$t('piecewageDetail.processPrice')" align="center" prop="processPrice" width="100" />
      <el-table-column :label="$t('piecewageDetail.shouldWage')" align="center" prop="shouldWage" width="100" />
      <el-table-column :label="$t('piecewageDetail.deductWage')" align="center" prop="deductWage" width="100" />
      <el-table-column :label="$t('piecewageDetail.actualWage')" align="center" prop="actualWage" width="100" />
      <el-table-column :label="$t('piecewageDetail.createTime')" align="center" prop="createTime" width="180">
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
