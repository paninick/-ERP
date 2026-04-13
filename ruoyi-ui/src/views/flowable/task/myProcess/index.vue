<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="processName">
        <el-input v-model="queryParams.processName" placeholder="请输入流程名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="流程KEY" prop="processKey">
        <el-input v-model="queryParams.processKey" placeholder="请输入流程KEY" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="发起时间">
        <el-date-picker v-model="dateRange" style="width: 240px" value-format="yyyy-MM-dd" type="daterange" range-separator="-" start-placeholder="开始日期" end-placeholder="结束日期"></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['flowable:myProcess:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="myProcessList">
      <el-table-column label="实例ID" align="center" prop="processInstanceId" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="流程名称" align="center" prop="processName" :show-overflow-tooltip="true" />
      <el-table-column label="流程KEY" align="center" prop="processKey" :show-overflow-tooltip="true" />
      <el-table-column label="业务KEY" align="center" prop="businessKey" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="当前节点" align="center" prop="currentNode" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="processStatus" width="100">
        <template slot-scope="scope">
          <el-tag :type="getProcessStatusType(scope.row.processStatus)">{{ getProcessStatusLabel(scope.row.processStatus) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="发起时间" align="center" prop="startTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" prop="endTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-position" @click="handleTrack(scope.row)">追踪</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleCancel(scope.row)" v-if="!scope.row.endTime">撤销</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />
  </div>
</template>

<script>
import { listMyProcess } from "@/api/flowable/task/myProcess"

export default {
  name: "MyProcess",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 已发任务表格数据
      myProcessList: [],
      // 显示搜索条件
      showSearch: true,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processName: undefined,
        processKey: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询已发任务列表 */
    getList() {
      this.loading = true
      listMyProcess(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.myProcessList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 查看详情 */
    handleView(row) {
      this.$message.info("查看流程实例: " + row.processInstanceId)
    },
    /** 流程追踪 */
    handleTrack(row) {
      this.$message.info("追踪流程: " + row.processInstanceId)
    },
    /** 撤销流程 */
    handleCancel(row) {
      this.$modal.confirm('确认要撤销该流程吗？').then(() => {
        this.$modal.msgSuccess("撤销成功")
        this.getList()
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flowable/myProcess/export', {
        ...this.queryParams
      }, `myProcess_${new Date().getTime()}.xlsx`)
    },
    /** 获取流程状态类型 */
    getProcessStatusType(status) {
      const statusMap = {
        'running': 'primary',
        'completed': 'success',
        'cancelled': 'info',
        'terminated': 'danger'
      }
      return statusMap[status] || ''
    },
    /** 获取流程状态标签 */
    getProcessStatusLabel(status) {
      const statusMap = {
        'running': '运行中',
        'completed': '已完成',
        'cancelled': '已取消',
        'terminated': '已终止'
      }
      return statusMap[status] || status
    }
  }
}
</script>
