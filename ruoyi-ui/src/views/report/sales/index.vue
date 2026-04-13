<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
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
    </el-row>

    <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

    <el-table v-loading="loading" :data="salesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" width="60" />
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
      <el-table-column label="金额合计" align="center" prop="amount" width="120">
        <template slot-scope="scope">
          <span>{{ scope.row.amount }} 元</span>
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
      :page-num.sync="queryParams.pageNum"
      :page-limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import { listSales, getSales, delSales, addSales, updateSales } from "@/api/erp/sales"
import { listCustomer } from "@/api/erp/customer"

export default {
  name: "SalesSummary",
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
        salesDate: []
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
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
      this.resetForm("queryParams")
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
      }, `sales_summary_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
