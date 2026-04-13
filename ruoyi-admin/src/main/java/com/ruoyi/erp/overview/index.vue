<template>
  <div class="app-container">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="mb20">
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card">
          <div class="stat-content">
            <div class="stat-title">总打样数</div>
            <div class="stat-value">{{ statistics.totalCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card stat-primary">
          <div class="stat-content">
            <div class="stat-title">进行中</div>
            <div class="stat-value">{{ statistics.inProgressCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card stat-success">
          <div class="stat-content">
            <div class="stat-title">已完成</div>
            <div class="stat-value">{{ statistics.completedCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="6">
        <el-card shadow="hover" class="stat-card stat-warning">
          <div class="stat-content">
            <div class="stat-title">待处理</div>
            <div class="stat-value">{{ statistics.pendingCount || 0 }}</div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="通知编号" prop="noticeNo">
        <el-input v-model="queryParams.noticeNo" placeholder="请输入通知编号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input v-model="queryParams.customerName" placeholder="请输入客户名称" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="款号" prop="styleNo">
        <el-input v-model="queryParams.styleNo" placeholder="请输入款号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">
          <el-option v-for="dict in dict.type.erp_notice_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['erp:overview:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="overviewList">
      <el-table-column label="通知编号" align="center" prop="noticeNo" width="150" />
      <el-table-column label="客户名称" align="center" prop="customerName" :show-overflow-tooltip="true" />
      <el-table-column label="款号" align="center" prop="styleNo" width="130" />
      <el-table-column label="打样数量" align="center" prop="quantity" width="90" />
      <el-table-column label="要求交期" align="center" prop="deliveryDate" width="110">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="BOM状态" align="center" prop="bomStatus" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.bomStatus)" size="mini">{{ getStatusLabel(scope.row.bomStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="核版状态" align="center" prop="checkStatus" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.checkStatus)" size="mini">{{ getStatusLabel(scope.row.checkStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="工艺状态" align="center" prop="techStatus" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusType(scope.row.techStatus)" size="mini">{{ getStatusLabel(scope.row.techStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="整体进度" align="center" prop="progress" width="120">
        <template slot-scope="scope">
          <el-progress :percentage="scope.row.progress || 0" :stroke-width="8" :color="getProgressColor(scope.row.progress)"></el-progress>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-position" @click="handleTrack(scope.row)">追踪</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listOverview, getOverviewStatistics } from "@/api/erp/overview"

export default {
  name: "Overview",
  dicts: ['erp_notice_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 打样总览表格数据
      overviewList: [],
      // 统计信息
      statistics: {},
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        noticeNo: undefined,
        customerName: undefined,
        styleNo: undefined,
        status: undefined
      }
    }
  },
  created() {
    this.getList()
    this.getStatistics()
  },
  methods: {
    /** 查询打样总览列表 */
    getList() {
      this.loading = true
      listOverview(this.queryParams).then(response => {
        this.overviewList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 获取统计信息 */
    getStatistics() {
      getOverviewStatistics().then(response => {
        this.statistics = response.data
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 查看详情 */
    handleView(row) {
      this.$message.info("查看打样详情: " + row.noticeNo)
    },
    /** 追踪流程 */
    handleTrack(row) {
      this.$message.info("追踪打样流程: " + row.noticeNo)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/overview/export', {
        ...this.queryParams
      }, `overview_${new Date().getTime()}.xlsx`)
    },
    /** 获取状态类型 */
    getStatusType(status) {
      const statusMap = { 'pending': 'info', 'in_progress': 'warning', 'completed': 'success' }
      return statusMap[status] || ''
    },
    /** 获取状态标签 */
    getStatusLabel(status) {
      const statusMap = { 'pending': '待处理', 'in_progress': '进行中', 'completed': '已完成' }
      return statusMap[status] || status
    },
    /** 获取进度条颜色 */
    getProgressColor(percentage) {
      if (percentage >= 100) return '#67C23A'
      if (percentage >= 70) return '#E6A23C'
      if (percentage >= 30) return '#409EFF'
      return '#F56C6C'
    }
  }
}
</script>

<style scoped>
.stat-card {
  margin-bottom: 20px;
}
.stat-content {
  text-align: center;
  padding: 15px 0;
}
.stat-title {
  font-size: 14px;
  color: #909399;
  margin-bottom: 10px;
}
.stat-value {
  font-size: 32px;
  font-weight: bold;
  color: #303133;
}
.stat-primary .stat-value {
  color: #409EFF;
}
.stat-success .stat-value {
  color: #67C23A;
}
.stat-warning .stat-value {
  color: #E6A23C;
}
.mb20 {
  margin-bottom: 20px;
}
</style>
