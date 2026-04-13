<template>
  <div class="app-container">
    <!-- 操作按钮 -->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="任务名称" prop="taskName">
        <el-input
          v-model="queryParams.taskName"
          placeholder="请输入任务名称"
          clearable
          size="small"
          style="width: 200px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="任务状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="请选择任务状态"
          clearable
          size="small"
          style="width: 150px"
        >
          <el-option label="全部" value="" />
          <el-option label="运行中" value="running" />
          <el-option label="已完成" value="completed" />
          <el-option label="失败" value="failed" />
          <el-option label="暂停" value="paused" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="small" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <!-- 工具栏 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-plus"
          size="small"
          @click="handleAdd"
          v-hasPermi="['erp:automation:add']"
        >
          新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          icon="el-icon-edit"
          size="small"
          @click="handleEdit"
          :disabled="single"
          v-hasPermi="['erp:automation:edit']"
        >
          修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="small"
          @click="handleDelete"
          :disabled="multiple"
          v-hasPermi="['erp:automation:remove']"
        >
          删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-download"
          size="small"
          @click="handleExport"
          v-hasPermi="['erp:automation:export']"
        >
          导出
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          icon="el-icon-refresh"
          size="small"
          @click="refresh"
        >
          刷新
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          icon="el-icon-video-play"
          size="small"
          @click="handleRun"
          :disabled="multiple"
          v-hasPermi="['erp:automation:run']"
        >
          运行
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          icon="el-icon-video-pause"
          size="small"
          @click="handlePause"
          :disabled="multiple"
          v-hasPermi="['erp:automation:pause']"
        >
          暂停
        </el-button>
      </el-col>
    </el-row>

    <!-- 任务列表 -->
    <el-table
      v-loading="loading"
      :data="automationList"
      @selection-change="handleSelectionChange"
      style="width: 100%"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="任务ID" align="center" prop="taskId" width="100" />
      <el-table-column label="任务名称" align="center" prop="taskName" />
      <el-table-column label="任务类型" align="center" prop="taskType" width="120">
        <template slot-scope="scope">
          <el-tag :type="getTaskTypeTag(scope.row.taskType)">{{ getTaskTypeText(scope.row.taskType) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="任务状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <el-tag :type="getStatusTag(scope.row.status)">{{ getStatusText(scope.row.status) }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="执行时间" align="center" prop="executeTime" width="180" />
      <el-table-column label="创建时间" align="center" prop="createTime" width="180" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleEdit(scope.row)"
            v-hasPermi="['erp:automation:edit']"
          >
            修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:automation:remove']"
          >
            删除
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-play"
            @click="handleRun(scope.row)"
            v-hasPermi="['erp:automation:run']"
          >
            运行
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-video-pause"
            @click="handlePause(scope.row)"
            v-hasPermi="['erp:automation:pause']"
          >
            暂停
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 分页 -->
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 新增/修改弹窗 -->
    <el-dialog
      :title="title"
      :visible.sync="open"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="任务名称" prop="taskName">
          <el-input v-model="form.taskName" placeholder="请输入任务名称" />
        </el-form-item>
        <el-form-item label="任务类型" prop="taskType">
          <el-select v-model="form.taskType" placeholder="请选择任务类型">
            <el-option label="数据库备份" value="backup" />
            <el-option label="系统检查" value="check" />
            <el-option label="数据清理" value="clean" />
            <el-option label="系统更新" value="update" />
          </el-select>
        </el-form-item>
        <el-form-item label="执行周期" prop="cronExpression">
          <el-input v-model="form.cronExpression" placeholder="请输入Cron表达式" />
          <el-button type="text" size="small" @click="showCronHelp">Cron帮助</el-button>
        </el-form-item>
        <el-form-item label="任务描述" prop="description">
          <el-input type="textarea" v-model="form.description" placeholder="请输入任务描述" :rows="3" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio label="running">运行中</el-radio>
            <el-radio label="paused">暂停</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确定</el-button>
        <el-button @click="cancel">取消</el-button>
      </div>
    </el-dialog>

    <!-- Cron帮助弹窗 -->
    <el-dialog
      title="Cron表达式帮助"
      :visible.sync="cronHelpVisible"
      width="600px"
      append-to-body
    >
      <div class="cron-help">
        <h4>Cron表达式格式：</h4>
        <p>秒 分 时 日 月 周 年（可选）</p>
        <h4>示例：</h4>
        <ul>
          <li>0 0 12 * * ? - 每天中午12点执行</li>
          <li>0 15 10 ? * * - 每天上午10:15执行</li>
          <li>0 15 10 * * ? - 每天上午10:15执行</li>
          <li>0 15 10 * * ? * - 每天上午10:15执行</li>
          <li>0 15 10 * * ? 2026 - 2026年每天上午10:15执行</li>
          <li>0 * 14 * * ? - 每天14点到14:59期间的每1分钟执行</li>
          <li>0 0/5 14 * * ? - 每天14点到14:55期间的每5分钟执行</li>
          <li>0 0/5 14,18 * * ? - 每天14点到14:55期间和18点到18:55期间的每5分钟执行</li>
          <li>0 0-5 14 * * ? - 每天14:00到14:05期间的每1分钟执行</li>
          <li>0 10,44 14 ? 3 WED - 每年3月的星期三的14:10和14:44执行</li>
          <li>0 15 10 ? * MON-FRI - 周一至周五的10:15执行</li>
          <li>0 15 10 15 * ? - 每月15日的10:15执行</li>
          <li>0 15 10 L * ? - 每月最后一日的10:15执行</li>
          <li>0 15 10 ? * 6L - 每月最后一个星期五的10:15执行</li>
          <li>0 15 10 ? * 6L 2025-2026 - 2025年至2026年每月最后一个星期五的10:15执行</li>
          <li>0 15 10 ? * 6#3 - 每月第三个星期五的10:15执行</li>
        </ul>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cronHelpVisible = false">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listAutomation, getAutomation, delAutomation, addAutomation, updateAutomation, runAutomation, pauseAutomation, exportAutomation } from '@/api/erp/automation'

export default {
  name: 'Automation',
  data() {
    return {
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 自动化任务列表
      automationList: [],
      // 弹出层标题
      title: '',
      // 是否显示弹出层
      open: false,
      // 是否显示Cron帮助
      cronHelpVisible: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        taskName: [
          { required: true, message: '请输入任务名称', trigger: 'blur' }
        ],
        taskType: [
          { required: true, message: '请选择任务类型', trigger: 'change' }
        ],
        cronExpression: [
          { required: true, message: '请输入执行周期', trigger: 'blur' }
        ]
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        taskName: '',
        status: ''
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询自动化任务列表 */
    getList() {
      this.loading = true
      listAutomation(this.queryParams).then(response => {
        this.automationList = response.rows
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
      this.resetForm('queryForm')
      this.handleQuery()
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.taskId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = '新增自动化任务'
    },
    /** 修改按钮操作 */
    handleEdit(row) {
      this.reset()
      const taskId = row.taskId || this.ids
      getAutomation(taskId).then(response => {
        this.form = response.data
        this.open = true
        this.title = '修改自动化任务'
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.taskId != null) {
            updateAutomation(this.form).then(response => {
              this.$modal.msgSuccess('修改成功')
              this.open = false
              this.getList()
            })
          } else {
            addAutomation(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 取消按钮 */
    cancel() {
      this.open = false
      this.reset()
    },
    /** 重置表单 */
    reset() {
      this.form = {
        taskId: null,
        taskName: '',
        taskType: '',
        cronExpression: '',
        description: '',
        status: 'running'
      }
      this.resetForm('form')
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const taskIds = row.taskId || this.ids
      this.$modal.confirm('是否确认删除自动化任务编号为"' + taskIds + '"的数据项？').then(function() {
        return delAutomation(taskIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('删除成功')
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/automation/export', { ...this.queryParams }, `automation_${new Date().getTime()}.xlsx`)
    },
    /** 运行按钮操作 */
    handleRun(row) {
      const taskId = row.taskId || this.ids
      this.$modal.confirm('是否确认运行自动化任务编号为"' + taskId + '"的数据项？').then(function() {
        return runAutomation(taskId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('运行成功')
      })
    },
    /** 暂停按钮操作 */
    handlePause(row) {
      const taskId = row.taskId || this.ids
      this.$modal.confirm('是否确认暂停自动化任务编号为"' + taskId + '"的数据项？').then(function() {
        return pauseAutomation(taskId)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess('暂停成功')
      })
    },
    /** 显示Cron帮助 */
    showCronHelp() {
      this.cronHelpVisible = true
    },
    /** 刷新按钮操作 */
    refresh() {
      this.getList()
    },
    /** 获取任务类型标签 */
    getTaskTypeTag(type) {
      switch (type) {
        case 'backup':
          return 'primary'
        case 'check':
          return 'success'
        case 'clean':
          return 'warning'
        case 'update':
          return 'info'
        default:
          return ''
      }
    },
    /** 获取任务类型文本 */
    getTaskTypeText(type) {
      switch (type) {
        case 'backup':
          return '数据库备份'
        case 'check':
          return '系统检查'
        case 'clean':
          return '数据清理'
        case 'update':
          return '系统更新'
        default:
          return type
      }
    },
    /** 获取状态标签 */
    getStatusTag(status) {
      switch (status) {
        case 'running':
          return 'success'
        case 'completed':
          return 'info'
        case 'failed':
          return 'danger'
        case 'paused':
          return 'warning'
        default:
          return ''
      }
    },
    /** 获取状态文本 */
    getStatusText(status) {
      switch (status) {
        case 'running':
          return '运行中'
        case 'completed':
          return '已完成'
        case 'failed':
          return '失败'
        case 'paused':
          return '暂停'
        default:
          return status
      }
    }
  }
}
</script>

<style scoped>
.cron-help {
  max-height: 400px;
  overflow-y: auto;
}

.cron-help h4 {
  margin-top: 16px;
  margin-bottom: 8px;
}

.cron-help ul {
  list-style-type: disc;
  padding-left: 20px;
}

.cron-help li {
  margin-bottom: 4px;
}
</style>
