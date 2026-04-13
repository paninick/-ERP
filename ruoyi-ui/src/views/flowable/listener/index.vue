<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="监听器名称" prop="listenerName">
        <el-input v-model="queryParams.listenerName" placeholder="请输入监听器名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="监听器类型" prop="listenerType">
        <el-select v-model="queryParams.listenerType" placeholder="请选择监听器类型" clearable style="width: 240px">
          <el-option label="任务监听器" value="task" />
          <el-option label="执行监听器" value="execution" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['flowable:listener:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['flowable:listener:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['flowable:listener:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="listenerList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="60" />
      <el-table-column label="监听器名称" align="center" prop="listenerName" :show-overflow-tooltip="true" />
      <el-table-column label="监听器类型" align="center" prop="listenerType" width="120">
        <template slot-scope="scope">
          <el-tag :type="scope.row.listenerType === 'task' ? '' : 'success'">{{ scope.row.listenerType === 'task' ? '任务监听器' : '执行监听器' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="事件类型" align="center" prop="eventType" width="120" />
      <el-table-column label="实现类" align="center" prop="implementation" :show-overflow-tooltip="true" />
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['flowable:listener:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['flowable:listener:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改流程监听对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="监听器名称" prop="listenerName">
          <el-input v-model="form.listenerName" placeholder="请输入监听器名称" />
        </el-form-item>
        <el-form-item label="监听器类型" prop="listenerType">
          <el-select v-model="form.listenerType" placeholder="请选择监听器类型">
            <el-option label="任务监听器" value="task" />
            <el-option label="执行监听器" value="execution" />
          </el-select>
        </el-form-item>
        <el-form-item label="事件类型" prop="eventType">
          <el-select v-model="form.eventType" placeholder="请选择事件类型">
            <el-option label="创建" value="create" />
            <el-option label="分配" value="assignment" />
            <el-option label="完成" value="complete" />
            <el-option label="删除" value="delete" />
          </el-select>
        </el-form-item>
        <el-form-item label="实现类" prop="implementation">
          <el-input v-model="form.implementation" placeholder="请输入实现类全限定名" />
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listListener, getListener, delListener, addListener, updateListener } from "@/api/flowable/listener"

export default {
  name: "Listener",
  dicts: ['sys_normal_disable'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 流程监听表格数据
      listenerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        listenerName: undefined,
        listenerType: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        listenerName: [
          { required: true, message: "监听器名称不能为空", trigger: "blur" }
        ],
        listenerType: [
          { required: true, message: "监听器类型不能为空", trigger: "change" }
        ],
        eventType: [
          { required: true, message: "事件类型不能为空", trigger: "change" }
        ],
        implementation: [
          { required: true, message: "实现类不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询流程监听列表 */
    getList() {
      this.loading = true
      listListener(this.queryParams).then(response => {
        this.listenerList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    // 取消按钮
    cancel() {
      this.open = false
      this.reset()
    },
    // 表单重置
    reset() {
      this.form = {
        id: undefined,
        listenerName: undefined,
        listenerType: undefined,
        eventType: undefined,
        implementation: undefined,
        status: "0",
        remark: undefined
      }
      this.resetForm("form")
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
      this.ids = selection.map(item => item.id)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加流程监听"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getListener(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改流程监听"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateListener(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addListener(this.form).then(() => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除监听器ID为"' + ids + '"的数据项？').then(function() {
        return delListener(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flowable/listener/export', {
        ...this.queryParams
      }, `listener_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
