<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="processName">
        <el-input v-model="queryParams.processName" placeholder="请输入流程名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="发起人" prop="startUser">
        <el-input v-model="queryParams.startUser" placeholder="请输入发起人" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="处理结果" prop="result">
        <el-select v-model="queryParams.result" placeholder="请选择处理结果" clearable style="width: 240px">
          <el-option label="通过" value="approved" />
          <el-option label="驳回" value="rejected" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['flowable:finished:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="finishedList">
      <el-table-column label="任务ID" align="center" prop="taskId" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="流程名称" align="center" prop="processName" :show-overflow-tooltip="true" />
      <el-table-column label="任务名称" align="center" prop="taskName" :show-overflow-tooltip="true" />
      <el-table-column label="发起人" align="center" prop="startUserName" width="100" />
      <el-table-column label="处理结果" align="center" prop="result" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.result === 'approved' ? 'success' : 'danger'" size="mini">{{ scope.row.result === 'approved' ? '通过' : '驳回' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="处理意见" align="center" prop="comment" :show-overflow-tooltip="true" />
      <el-table-column label="开始时间" align="center" prop="startTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.startTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="完成时间" align="center" prop="endTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.endTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="耗时" align="center" prop="duration" width="100" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
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
import { listFinished } from "@/api/flowable/task/finished"

export default {
  name: "Finished",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 已办任务表格数据
      finishedList: [],
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processName: undefined,
        startUser: undefined,
        result: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询已办任务列表 */
    getList() {
      this.loading = true
      listFinished(this.queryParams).then(response => {
        this.finishedList = response.rows
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
      this.resetForm("queryForm")
      this.handleQuery()
    },
    /** 查看详情 */
    handleView(row) {
      this.$message.info("查看已办任务: " + row.taskName)
    },
    /** 流程追踪 */
    handleTrack(row) {
      this.$message.info("追踪流程: " + row.processInstanceId)
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flowable/finished/export', {
        ...this.queryParams
      }, `finished_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
