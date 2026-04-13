<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="流程名称" prop="name">
        <el-input v-model="queryParams.name" placeholder="请输入流程名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="流程KEY" prop="key">
        <el-input v-model="queryParams.key" placeholder="请输入流程KEY" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 240px">
          <el-option label="激活" value="active" />
          <el-option label="挂起" value="suspended" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['flowable:definition:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['flowable:definition:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['flowable:definition:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="definitionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="流程ID" align="center" prop="id" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="流程名称" align="center" prop="name" :show-overflow-tooltip="true" />
      <el-table-column label="流程KEY" align="center" prop="key" :show-overflow-tooltip="true" />
      <el-table-column label="版本" align="center" prop="version" width="80" />
      <el-table-column label="状态" align="center" prop="suspended" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.suspended ? 'info' : 'success'">{{ scope.row.suspended ? '挂起' : '激活' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="部署时间" align="center" prop="deploymentTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.deploymentTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="250">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['flowable:definition:edit']">编辑</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['flowable:definition:remove']">删除</el-button>
          <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)">
            <el-button size="mini" type="text" icon="el-icon-d-arrow-right">更多</el-button>
            <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleActivate" icon="el-icon-video-play" v-if="scope.row.suspended">激活</el-dropdown-item>
              <el-dropdown-item command="handleSuspend" icon="el-icon-video-pause" v-if="!scope.row.suspended">挂起</el-dropdown-item>
              <el-dropdown-item command="handleConvertToModel" icon="el-icon-document">转模型</el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改流程定义对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="流程名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入流程名称" />
        </el-form-item>
        <el-form-item label="流程KEY" prop="key">
          <el-input v-model="form.key" placeholder="请输入流程KEY" />
        </el-form-item>
        <el-form-item label="描述" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入描述" />
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
import { listDefinition, getDefinition, delDefinition, addDefinition, updateDefinition, activateDefinition, suspendDefinition } from "@/api/flowable/definition"

export default {
  name: "Definition",
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
      // 流程定义表格数据
      definitionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        key: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        name: [
          { required: true, message: "流程名称不能为空", trigger: "blur" }
        ],
        key: [
          { required: true, message: "流程KEY不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询流程定义列表 */
    getList() {
      this.loading = true
      listDefinition(this.queryParams).then(response => {
        this.definitionList = response.rows
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
        name: undefined,
        key: undefined,
        description: undefined
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
    // 更多操作触发
    handleCommand(command, row) {
      switch (command) {
        case "handleActivate":
          this.handleActivate(row)
          break
        case "handleSuspend":
          this.handleSuspend(row)
          break
        case "handleConvertToModel":
          this.handleConvertToModel(row)
          break
        default:
          break
      }
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加流程定义"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getDefinition(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改流程定义"
      })
    },
    /** 查看详情 */
    handleView(row) {
      this.$message.info("查看流程定义: " + row.name)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateDefinition(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addDefinition(this.form).then(() => {
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
      this.$modal.confirm('是否确认删除流程定义ID为"' + ids + '"的数据项？').then(function() {
        return delDefinition(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flowable/definition/export', {
        ...this.queryParams
      }, `definition_${new Date().getTime()}.xlsx`)
    },
    /** 激活流程 */
    handleActivate(row) {
      this.$modal.confirm('确认要激活"' + row.name + '"流程吗？').then(() => {
        return activateDefinition(row.id)
      }).then(() => {
        this.$modal.msgSuccess("激活成功")
        this.getList()
      }).catch(() => {})
    },
    /** 挂起流程 */
    handleSuspend(row) {
      this.$modal.confirm('确认要挂起"' + row.name + '"流程吗？').then(() => {
        return suspendDefinition(row.id)
      }).then(() => {
        this.$modal.msgSuccess("挂起成功")
        this.getList()
      }).catch(() => {})
    },
    /** 转换为模型 */
    handleConvertToModel(row) {
      this.$modal.confirm('确认要将"' + row.name + '"转换为模型吗？').then(() => {
        this.$modal.msgSuccess("转换成功")
      }).catch(() => {})
    }
  }
}
</script>
