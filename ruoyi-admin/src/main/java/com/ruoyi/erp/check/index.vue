<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="核版编号" prop="checkNo">
        <el-input v-model="queryParams.checkNo" placeholder="请输入核版编号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="款号" prop="styleNo">
        <el-input v-model="queryParams.styleNo" placeholder="请输入款号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input v-model="queryParams.customerName" placeholder="请输入客户名称" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">
          <el-option v-for="dict in dict.type.erp_check_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['erp:check:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:check:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:check:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['erp:check:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="核版ID" align="center" prop="checkId" width="80" />
      <el-table-column label="核版编号" align="center" prop="checkNo" width="150" />
      <el-table-column label="款号" align="center" prop="styleNo" width="130" />
      <el-table-column label="客户名称" align="center" prop="customerName" :show-overflow-tooltip="true" />
      <el-table-column label="数量" align="center" prop="quantity" width="80" />
      <el-table-column label="核版结果" align="center" prop="checkResult" width="100">
        <template slot-scope="scope">
          <el-tag :type="scope.row.checkResult === 'pass' ? 'success' : 'danger'" size="mini">{{ scope.row.checkResult === 'pass' ? '通过' : '不通过' }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_check_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="250">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['erp:check:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-check" @click="handleApprove(scope.row)" v-if="scope.row.status === 'pending'" v-hasPermi="['erp:check:approve']">审批</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['erp:check:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改大货核版对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="核版编号" prop="checkNo">
              <el-input v-model="form.checkNo" placeholder="系统自动生成" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="款号" prop="styleNo">
              <el-input v-model="form.styleNo" placeholder="请输入款号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="客户名称" prop="customerId">
              <el-select v-model="form.customerId" placeholder="请选择客户" filterable>
                <el-option v-for="item in customerOptions" :key="item.customerId" :label="item.customerName" :value="item.customerId" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数量" prop="quantity">
              <el-input-number v-model="form.quantity" :min="1" placeholder="请输入数量" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="核版说明" prop="checkDescription">
          <el-input v-model="form.checkDescription" type="textarea" placeholder="请输入核版说明" />
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
import { listCheck, getCheck, delCheck, addCheck, updateCheck, approveCheck } from "@/api/erp/check"
import { listCustomer } from "@/api/erp/customer"

export default {
  name: "Check",
  dicts: ['erp_check_status'],
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
      // 大货核版表格数据
      checkList: [],
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
        checkNo: undefined,
        styleNo: undefined,
        customerName: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        styleNo: [
          { required: true, message: "款号不能为空", trigger: "blur" }
        ],
        customerId: [
          { required: true, message: "客户不能为空", trigger: "change" }
        ],
        quantity: [
          { required: true, message: "数量不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getCustomerList()
  },
  methods: {
    /** 查询大货核版列表 */
    getList() {
      this.loading = true
      listCheck(this.queryParams).then(response => {
        this.checkList = response.rows
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
        checkId: undefined,
        checkNo: undefined,
        styleNo: undefined,
        customerId: undefined,
        customerName: undefined,
        quantity: undefined,
        checkDescription: undefined,
        status: "pending",
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
      this.ids = selection.map(item => item.checkId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加大货核版"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const checkId = row.checkId || this.ids
      getCheck(checkId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改大货核版"
      })
    },
    /** 查看详情 */
    handleView(row) {
      this.$message.info("查看核版详情: " + row.checkNo)
    },
    /** 审批操作 */
    handleApprove(row) {
      this.$prompt('请输入审批意见', "核版审批", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        inputPlaceholder: "请输入审批意见"
      }).then(({ value }) => {
        approveCheck({ checkId: row.checkId, comment: value }).then(() => {
          this.$modal.msgSuccess("审批成功")
          this.getList()
        })
      }).catch(() => {})
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.checkId != undefined) {
            updateCheck(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addCheck(this.form).then(() => {
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
      const checkIds = row.checkId || this.ids
      this.$modal.confirm('是否确认删除核版编号为"' + checkIds + '"的数据项？').then(function() {
        return delCheck(checkIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/check/export', {
        ...this.queryParams
      }, `check_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
