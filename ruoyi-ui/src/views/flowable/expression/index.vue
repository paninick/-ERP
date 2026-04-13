<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="表达式名称" prop="expressionName">
        <el-input v-model="queryParams.expressionName" placeholder="请输入表达式名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="表达式类型" prop="expressionType">
        <el-select v-model="queryParams.expressionType" placeholder="请选择表达式类型" clearable style="width: 240px">
          <el-option label="变量表达式" value="variable" />
          <el-option label="条件表达式" value="condition" />
          <el-option label="脚本表达式" value="script" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['flowable:expression:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['flowable:expression:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['flowable:expression:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="expressionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="ID" align="center" prop="id" width="60" />
      <el-table-column label="表达式名称" align="center" prop="expressionName" :show-overflow-tooltip="true" />
      <el-table-column label="表达式KEY" align="center" prop="expressionKey" :show-overflow-tooltip="true" />
      <el-table-column label="表达式类型" align="center" prop="expressionType" width="140">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.expressionType === 'variable'">变量表达式</el-tag>
          <el-tag v-else-if="scope.row.expressionType === 'condition'" type="success">条件表达式</el-tag>
          <el-tag v-else type="warning">脚本表达式</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="表达式内容" align="center" prop="expressionContent" :show-overflow-tooltip="true" />
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
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['flowable:expression:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['flowable:expression:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改流程表达式对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="110px">
        <el-form-item label="表达式名称" prop="expressionName">
          <el-input v-model="form.expressionName" placeholder="请输入表达式名称" />
        </el-form-item>
        <el-form-item label="表达式KEY" prop="expressionKey">
          <el-input v-model="form.expressionKey" placeholder="请输入表达式KEY" />
        </el-form-item>
        <el-form-item label="表达式类型" prop="expressionType">
          <el-select v-model="form.expressionType" placeholder="请选择表达式类型">
            <el-option label="变量表达式" value="variable" />
            <el-option label="条件表达式" value="condition" />
            <el-option label="脚本表达式" value="script" />
          </el-select>
        </el-form-item>
        <el-form-item label="表达式内容" prop="expressionContent">
          <el-input v-model="form.expressionContent" type="textarea" :rows="4" placeholder="请输入表达式内容" />
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
import { listExpression, getExpression, delExpression, addExpression, updateExpression } from "@/api/flowable/expression"

export default {
  name: "Expression",
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
      // 流程表达式表格数据
      expressionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        expressionName: undefined,
        expressionType: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        expressionName: [
          { required: true, message: "表达式名称不能为空", trigger: "blur" }
        ],
        expressionKey: [
          { required: true, message: "表达式KEY不能为空", trigger: "blur" }
        ],
        expressionType: [
          { required: true, message: "表达式类型不能为空", trigger: "change" }
        ],
        expressionContent: [
          { required: true, message: "表达式内容不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询流程表达式列表 */
    getList() {
      this.loading = true
      listExpression(this.queryParams).then(response => {
        this.expressionList = response.rows
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
        expressionName: undefined,
        expressionKey: undefined,
        expressionType: undefined,
        expressionContent: undefined,
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
      this.title = "添加流程表达式"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getExpression(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改流程表达式"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != undefined) {
            updateExpression(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addExpression(this.form).then(() => {
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
      this.$modal.confirm('是否确认删除表达式ID为"' + ids + '"的数据项？').then(function() {
        return delExpression(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('flowable/expression/export', {
        ...this.queryParams
      }, `expression_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
