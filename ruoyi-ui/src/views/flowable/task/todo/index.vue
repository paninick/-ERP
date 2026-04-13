<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="processName">
        <el-input v-model="queryParams.processName" placeholder="请输入流程名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="发起人" prop="startUser">
        <el-input v-model="queryParams.startUser" placeholder="请输入发起人" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="优先级" prop="priority">
        <el-select v-model="queryParams.priority" placeholder="请选择优先级" clearable style="width: 240px">
          <el-option label="高" value="high" />
          <el-option label="中" value="medium" />
          <el-option label="低" value="low" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-check" size="mini" :disabled="single" @click="handleApprove" v-hasPermi="['flowable:todo:approve']">审批通过</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-close" size="mini" :disabled="single" @click="handleReject" v-hasPermi="['flowable:todo:reject']">驳回</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['flowable:todo:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="todoList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="任务ID" align="center" prop="taskId" width="150" :show-overflow-tooltip="true" />
      <el-table-column label="流程名称" align="center" prop="processName" :show-overflow-tooltip="true" />
      <el-table-column label="当前节点" align="center" prop="taskName" :show-overflow-tooltip="true" />
      <el-table-column label="发起人" align="center" prop="startUserName" width="100" />
      <el-table-column label="优先级" align="center" prop="priority" width="80">
        <template slot-scope="scope">
          <el-tag :type="getPriorityType(scope.row.priority)" size="mini">{{ getPriorityLabel(scope.row.priority) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="280">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-check" @click="handleApproveOne(scope.row)" v-hasPermi="['flowable:todo:approve']">通过</el-button>
          <el-button size="mini" type="text" icon="el-icon-close" @click="handleRejectOne(scope.row)" v-hasPermi="['flowable:todo:reject']">驳回</el-button>
          <el-button size="mini" type="text" icon="el-icon-user" @click="handleDelegate(scope.row)">委派</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 审批对话框 -->
    <el-dialog :title="approveTitle" :visible.sync="approveOpen" width="500px" append-to-body>
      <el-form ref="approveForm" :model="approveForm" :rules="approveRules" label-width="80px">
        <el-form-item label="审批意见" prop="comment">
          <el-input v-model="approveForm.comment" type="textarea" :rows="4" placeholder="请输入审批意见" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitApprove">确 定</el-button>
        <el-button @click="approveOpen = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listTodo, approveTask, rejectTask } from "@/api/flowable/task/todo"

export default {
  name: "Todo",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 总条数
      total: 0,
      // 待办任务表格数据
      todoList: [],
      // 显示搜索条件
      showSearch: true,
      // 审批对话框标题
      approveTitle: "",
      // 是否显示审批对话框
      approveOpen: false,
      // 当前操作类型
      currentAction: "",
      // 审批表单
      approveForm: {
        taskId: undefined,
        comment: ""
      },
      // 审批表单校验
      approveRules: {
        comment: [
          { required: true, message: "审批意见不能为空", trigger: "blur" }
        ]
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        processName: undefined,
        startUser: undefined,
        priority: undefined
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询待办任务列表 */
    getList() {
      this.loading = true
      listTodo(this.queryParams).then(response => {
        this.todoList = response.rows
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.taskId)
      this.single = selection.length != 1
    },
    /** 查看详情 */
    handleView(row) {
      this.$message.info("查看任务: " + row.taskName)
    },
    /** 批量审批通过 */
    handleApprove() {
      this.currentAction = "approve"
      this.approveTitle = "批量审批通过"
      this.approveForm.taskId = this.ids.join(",")
      this.approveForm.comment = ""
      this.approveOpen = true
    },
    /** 单个审批通过 */
    handleApproveOne(row) {
      this.currentAction = "approve"
      this.approveTitle = "审批通过 - " + row.taskName
      this.approveForm.taskId = row.taskId
      this.approveForm.comment = ""
      this.approveOpen = true
    },
    /** 批量驳回 */
    handleReject() {
      this.currentAction = "reject"
      this.approveTitle = "批量驳回"
      this.approveForm.taskId = this.ids.join(",")
      this.approveForm.comment = ""
      this.approveOpen = true
    },
    /** 单个驳回 */
    handleRejectOne(row) {
      this.currentAction = "reject"
      this.approveTitle = "驳回 - " + row.taskName
      this.approveForm.taskId = row.taskId
      this.approveForm.comment = ""
      this.approveOpen = true
    },
    /** 委派任务 */
    handleDelegate(row) {
      this.$message.info("委派任务: " + row.taskName)
    },
    /** 提交审批 */
    submitApprove() {
      this.$refs["approveForm"].validate(valid => {
        if (valid) {
          if (this.currentAction === "approve") {
            approveTask(this.approveForm).then(() => {
              this.$modal.msgSuccess("审批通过")
              this.approveOpen = false
              this.getList()
            })
          } else {
            rejectTask(this.approveForm).then(() => {
              this.$modal.msgSuccess("已驳回")
              this.approveOpen = false
              this.getList()
            })
          }
        }
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flowable/todo/export', {
        ...this.queryParams
      }, `todo_${new Date().getTime()}.xlsx`)
    },
    /** 获取优先级类型 */
    getPriorityType(priority) {
      const priorityMap = {
        'high': 'danger',
        'medium': 'warning',
        'low': 'info'
      }
      return priorityMap[priority] || ''
    },
    /** 获取优先级标签 */
    getPriorityLabel(priority) {
      const priorityMap = {
        'high': '高',
        'medium': '中',
        'low': '低'
      }
      return priorityMap[priority] || priority
    }
  }
}
</script>
