<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('notice.sampleType')" prop="sampleType">
        <el-select v-model="queryParams.sampleType" :placeholder="$t('validation.select', [$t('notice.sampleType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('notice.customerName')" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          :placeholder="$t('validation.enter', [$t('notice.customerName')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('notice.styleType')" prop="styleType">
        <el-select v-model="queryParams.styleType" :placeholder="$t('validation.select', [$t('notice.styleType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_style"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('notice.sampleCategoryType')" prop="sampleCategoryType">
        <el-select v-model="queryParams.sampleCategoryType" :placeholder="$t('validation.select', [$t('notice.sampleCategoryType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('notice.styleCode')" prop="styleCode">
        <el-input
          v-model="queryParams.styleCode"
          :placeholder="$t('validation.enter', [$t('notice.styleCode')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('notice.auditStatus')" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" :placeholder="$t('validation.select', [$t('notice.auditStatus')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('notice.applyTime')" prop="createTime">
        <el-date-picker
          v-model="queryParams.createTime"
          type="daterange"
          range-separator="-"
          :start-placeholder="$t('gantt.startDate')"
          :end-placeholder="$t('gantt.endDate')"
          value-format="yyyy-MM-dd"
          size="small"
        />
      </el-form-item>
      <el-form-item :label="$t('notice.salesName')" prop="salesName">
        <el-select v-model="queryParams.salesName" :placeholder="$t('validation.select', [$t('notice.salesName')])" clearable>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('btn.query') }}</el-button>
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
          v-hasPermi="['erp:notice:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="overviewList">
      <el-table-column :label="$t('notice.auditStatus')" align="center" prop="auditStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.customerName')" align="center" prop="customerName" :show-overflow-tooltip="true" />
      <el-table-column :label="$t('notice.sampleType')" align="center" prop="sampleType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_type" :value="scope.row.sampleType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.styleType')" align="center" prop="styleType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_style" :value="scope.row.styleType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.sampleCategoryType')" align="center" prop="sampleCategoryType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_category" :value="scope.row.sampleCategoryType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.styleCode')" align="center" prop="styleCode" width="120" />
      <el-table-column :label="$t('notice.bulkOrderNo')" align="center" prop="bulkOrderNo" width="120" />
      <el-table-column :label="$t('notice.dueDate')" align="center" prop="dueDate" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.auditBy')" align="center" prop="auditByNickName" />
      <el-table-column :label="$t('bom.salesName')" align="center" prop="salesName" />
      <el-table-column :label="$t('system.remark')" align="center" prop="remark" />
      <el-table-column :label="$t('notice.salesName')" align="center" prop="salesName" />
      <el-table-column :label="$t('notice.applyTime')" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.sampleNo')" align="center" prop="sampleNo" width="130" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width" width="100">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">{{ $t('btn.view') }}</el-button>
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
      this.$message.info(this.$t('overview.viewSampleDetail', [row.sampleNo]))
    },
    handleExport() {
      this.download('erp/notice/export', {
        ...this.queryParams
      }, `overview_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
