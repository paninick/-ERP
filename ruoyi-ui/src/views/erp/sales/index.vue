<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="销售类型" prop="salesType">
        <el-select v-model="queryParams.salesType" placeholder="请选择销售类型" clearable>
          <el-option
            v-for="item in salesTypeOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户" prop="customerId">
        <el-select v-model="queryParams.customerId" placeholder="请选择客户" clearable
          filterable clearable remote :remote-method="filterCustomer" loading="customerLoading">
          <el-option
            v-for="item in customerOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="销售单号" prop="salesNo">
        <el-input
          v-model="queryParams.salesNo"
          placeholder="请输入销售单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="销售日期" prop="salesDate">
        <el-date-picker
          v-model="queryParams.salesDate"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          value-format="yyyy-MM-dd"
          size="small">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="订单状态" prop="orderStatus">
        <el-select v-model="queryParams.orderStatus" placeholder="请选择订单状态" clearable>
          <el-option
            v-for="dict in dict.type.erp_order_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="审批状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in dict.type.erp_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['erp:sales:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:sales:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:sales:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:sales:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['erp:sales:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" width="60" />
      <el-table-column label="销售类型" align="center" prop="salesType">
        <template slot-scope="scope">
          <span>{{ getSalesTypeLabel(scope.row.salesType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="销售单号" align="center" prop="salesNo" />
      <el-table-column label="客户名称" align="center" prop="customerName" />
      <el-table-column label="大货款号" align="center" prop="bulkOrderNo" />
      <el-table-column label="打样款号" align="center" prop="sampleStyleNo" />
      <el-table-column label="款式/品类" align="center" prop="styleCategory" />
      <el-table-column label="打样编号" align="center" prop="sampleNo" />
      <el-table-column label="销售日期" align="center" prop="salesDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.salesDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="交货日期" align="center" prop="dueDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业务员" align="center" prop="salesName" />
      <el-table-column label="大货意见" align="center" prop="bulkOpinion" />
      <el-table-column label="审批状态" align="center" prop="auditStatus" />
      <el-table-column label="订单状态" align="center" prop="orderStatus" />
      <el-table-column label="排产需求是否超出" align="center" prop="productionExceed" />
      <el-table-column label="审批时间" align="center" prop="auditTime" width="140">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" width="140">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:sales:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:sales:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改销售订单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="销售类型" prop="salesType" required>
          <el-select v-model="form.salesType" placeholder="请选择销售类型">
            <el-option
              v-for="item in salesTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户" prop="customerId" required>
          <el-select v-model="form.customerId" placeholder="请选择客户" clearable
            filterable clearable remote :remote-method="filterCustomer" loading="customerLoading">
            <el-option
              v-for="item in customerOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="销售单号" prop="salesNo" required>
          <el-input v-model="form.salesNo" placeholder="请输入销售单号" />
        </el-form-item>
        <el-form-item label="销售日期" prop="salesDate" required>
          <el-date-picker clearable
            v-model="form.salesDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择销售日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="业务员" prop="salesId">
          <el-select v-model="form.salesId" placeholder="请选择业务员" clearable
            filterable clearable remote :remote-method="filterUser" loading="userLoading">
            <el-option
              v-for="item in userOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="数量" prop="quantity" required>
          <el-input-number v-model="form.quantity" :precision="2" :min="0" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="订单金额" prop="amount" required>
          <el-input-number v-model="form.amount" :precision="2" :min="0" placeholder="请输入订单金额" />
        </el-form-item>
        <el-form-item label="交货日期" prop="dueDate">
          <el-date-picker clearable
            v-model="form.dueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择交货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="订单状态" prop="orderStatus">
          <el-select v-model="form.orderStatus" placeholder="请选择订单状态">
            <el-option
              v-for="dict in dict.type.erp_order_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批状态" prop="auditStatus">
          <el-select v-model="form.auditStatus" placeholder="请选择审批状态">
            <el-option
              v-for="dict in dict.type.erp_audit_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="备注" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入备注" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">
          将文件拖到此处，或
          <em>点击上传</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" /> 是否更新已经存在的数据
          <el-link type="info" style="font-size:12px" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSales, getSales, delSales, addSales, updateSales } from "@/api/erp/sales"
import { listCustomer } from "@/api/erp/customer"
import { getToken } from "@/utils/auth"

export default {
  name: "SalesOrder",
  dicts: ['erp_order_status', 'erp_audit_status'],
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
      // 销售订单表格数据
      salesList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 销售类型选项
      salesTypeOptions: [
        { value: 'sample', label: '打样单' },
        { value: 'bulk', label: '大货单' }
      ],
      // 客户选项
      customerOptions: [],
      customerLoading: false,
      // 用户选项
      userOptions: [],
      userLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salesType: null,
        customerId: null,
        salesNo: null,
        salesDate: null,
        orderStatus: null,
        auditStatus: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        salesType: [
          { required: true, message: "销售类型不能为空", trigger: "change" }
        ],
        customerId: [
          { required: true, message: "客户不能为空", trigger: "change" }
        ],
        salesNo: [
          { required: true, message: "销售单号不能为空", trigger: "blur" }
        ],
        salesDate: [
          { required: true, message: "销售日期不能为空", trigger: "change" }
        ],
        quantity: [
          { required: true, message: "数量不能为空", trigger: "blur" }
        ],
        amount: [
          { required: true, message: "订单金额不能为空", trigger: "blur" }
        ]
      },
      // 上传参数
      upload: {
        open: false,
        title: "",
        isUploading: false,
        updateSupport: 0,
        headers: { Authorization: "Bearer " + getToken() },
        url: process.env.VUE_APP_BASE_API + "/erp/sales/order/importData"
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 获取销售类型标签 */
    getSalesTypeLabel(value) {
      const item = this.salesTypeOptions.find(o => o.value === value)
      return item ? item.label : value
    },
    /** 过滤客户 */
    filterCustomer(query) {
      if (!query) {
        this.customerOptions = []
        return
      }
      this.customerLoading = true
      listCustomer({ pageNum: 1, pageSize: 20, customerName: query }).then(response => {
        this.customerOptions = response.rows.map(r => ({
          value: r.id,
          label: r.customerName
        }))
        this.customerLoading = false
      }).catch(() => {
        this.customerLoading = false
      })
    },
    /** 过滤用户 */
    filterUser(query) {
      if (!query) {
        this.userOptions = []
        return
      }
      this.userLoading = true
      const users = this.$store.getters.userList.filter(u => u.nickName.includes(query) || u.userName.includes(query))
      this.userOptions = users.map(u => ({
        value: u.userId,
        label: u.nickName + '(' + u.userName + ')'
      }))
      this.userLoading = false
    },
    /** 查询销售订单列表 */
    getList() {
      this.loading = true
      listSales(this.queryParams).then(response => {
        this.salesList = response.rows
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
        id: null,
        salesType: null,
        noticeId: null,
        techId: null,
        salesNo: null,
        salesDate: null,
        salesId: null,
        customerId: null,
        dueDate: null,
        quantity: null,
        amount: null,
        payAmount: null,
        orderStatus: null,
        auditStatus: null,
        auditBy: null,
        auditTime: null,
        remark: null
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加销售订单"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSales(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改销售订单"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSales(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSales(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除销售订单编号为"' + ids + '"的数据项？').then(function() {
        return delSales(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/sales/order/export', {
        ...this.queryParams
      }, `sales_order_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "销售订单导入"
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('erp/sales/order/importTemplate', {}, 'sales_order_template.xlsx')
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$modal.msgSuccess(response.msg)
      this.getList()
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit()
    }
  }
}
</script>
