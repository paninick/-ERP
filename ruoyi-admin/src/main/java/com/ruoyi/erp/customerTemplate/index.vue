<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="模板名称" prop="templateName">
        <el-input v-model="queryParams.templateName" placeholder="请输入模板名称" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="模板编码" prop="templateCode">
        <el-input v-model="queryParams.templateCode" placeholder="请输入模板编码" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="关联客户" prop="customerId">
        <el-input v-model="queryParams.customerId" placeholder="请输入客户ID" clearable style="width: 240px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['erp:customerTemplate:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:customerTemplate:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:customerTemplate:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['erp:customerTemplate:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="templateList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="模板ID" align="center" prop="templateId" width="80" />
      <el-table-column label="模板编码" align="center" prop="templateCode" width="140" />
      <el-table-column label="模板名称" align="center" prop="templateName" :show-overflow-tooltip="true" />
      <el-table-column label="关联客户" align="center" prop="customerName" :show-overflow-tooltip="true" />
      <el-table-column label="版本号" align="center" prop="version" width="80" />
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['erp:customerTemplate:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['erp:customerTemplate:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改客户模板对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="650px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="模板编码" prop="templateCode">
          <el-input v-model="form.templateCode" placeholder="请输入模板编码" />
        </el-form-item>
        <el-form-item label="模板名称" prop="templateName">
          <el-input v-model="form.templateName" placeholder="请输入模板名称" />
        </el-form-item>
        <el-form-item label="关联客户" prop="customerId">
          <el-select v-model="form.customerId" placeholder="请选择关联客户" filterable>
            <el-option v-for="item in customerOptions" :key="item.customerId" :label="item.customerName" :value="item.customerId" />
          </el-select>
        </el-form-item>
        <el-form-item label="版本号" prop="version">
          <el-input-number v-model="form.version" :precision="1" :step="0.1" :min="1" style="width: 100%" />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-radio-group v-model="form.status">
            <el-radio v-for="dict in dict.type.sys_normal_disable" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
          </el-radio-group>
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
import { listCustomerTemplate, getCustomerTemplate, delCustomerTemplate, addCustomerTemplate, updateCustomerTemplate } from "@/api/erp/customerTemplate"
import { listCustomer } from "@/api/erp/customer"

export default {
  name: "CustomerTemplate",
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
      // 客户模板表格数据
      templateList: [],
      // 客户选项
      customerOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        templateName: undefined,
        templateCode: undefined,
        customerId: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        templateCode: [
          { required: true, message: "模板编码不能为空", trigger: "blur" }
        ],
        templateName: [
          { required: true, message: "模板名称不能为空", trigger: "blur" }
        ],
        customerId: [
          { required: true, message: "关联客户不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getCustomerList()
  },
  methods: {
    /** 查询客户模板列表 */
    getList() {
      this.loading = true
      listCustomerTemplate(this.queryParams).then(response => {
        this.templateList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 获取客户列表 */
    getCustomerList() {
      listCustomer({ pageNum: 1, pageSize: 9999 }).then(response => {
        this.customerOptions = response.rows
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
        templateId: undefined,
        templateCode: undefined,
        templateName: undefined,
        customerId: undefined,
        version: 1,
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
      this.ids = selection.map(item => item.templateId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加客户模板"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const templateId = row.templateId || this.ids
      getCustomerTemplate(templateId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改客户模板"
      })
    },
    /** 查看详情 */
    handleView(row) {
      this.$message.info("查看客户模板: " + row.templateName)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.templateId != undefined) {
            updateCustomerTemplate(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addCustomerTemplate(this.form).then(() => {
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
      const templateIds = row.templateId || this.ids
      this.$modal.confirm('是否确认删除模板编号为"' + templateIds + '"的数据项？').then(function() {
        return delCustomerTemplate(templateIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/customerTemplate/export', {
        ...this.queryParams
      }, `customerTemplate_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
