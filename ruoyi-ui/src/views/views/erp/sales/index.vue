<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="销售订单类型" prop="salesType">
        <el-select v-model="queryParams.salesType" placeholder="请选择销售订单类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_sales_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="打样通知id" prop="noticeId">
        <el-input
          v-model="queryParams.noticeId"
          placeholder="请输入打样通知id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="工艺书id" prop="techId">
        <el-input
          v-model="queryParams.techId"
          placeholder="请输入工艺书id"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="销售单号" prop="salesNo">
        <el-input
          v-model="queryParams.salesNo"
          placeholder="请输入销售单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salesList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="id" align="center" prop="id" />
      <el-table-column label="销售订单类型" align="center" prop="salesType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sales_type" :value="scope.row.salesType"/>
        </template>
      </el-table-column>
      <el-table-column label="打样通知id" align="center" prop="noticeId" />
      <el-table-column label="工艺书id" align="center" prop="techId" />
      <el-table-column label="销售单号" align="center" prop="salesNo" />
      <el-table-column label="销售日期" align="center" prop="salesDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.salesDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业务员id" align="center" prop="salesId" />
      <el-table-column label="客户id" align="center" prop="customerId" />
      <el-table-column label="交货日期" align="center" prop="dueDate" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="数量" align="center" prop="quantity" />
      <el-table-column label="订单金额" align="center" prop="amount" />
      <el-table-column label="付款金额" align="center" prop="payAmount" />
      <el-table-column label="订单状态" align="center" prop="orderStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sales_order_status" :value="scope.row.orderStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="审批状态" align="center" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_audit_status" :value="scope.row.auditStatus"/>
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
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="销售订单类型" prop="salesType">
          <el-select v-model="form.salesType" placeholder="请选择销售订单类型">
            <el-option
              v-for="dict in dict.type.erp_sales_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="打样通知id" prop="noticeId">
          <el-input v-model="form.noticeId" placeholder="请输入打样通知id" />
        </el-form-item>
        <el-form-item label="工艺书id" prop="techId">
          <el-input v-model="form.techId" placeholder="请输入工艺书id" />
        </el-form-item>
        <el-form-item label="销售单号" prop="salesNo">
          <el-input v-model="form.salesNo" placeholder="请输入销售单号" />
        </el-form-item>
        <el-form-item label="销售日期" prop="salesDate">
          <el-date-picker clearable
            v-model="form.salesDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择销售日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="业务员id" prop="salesId">
          <el-input v-model="form.salesId" placeholder="请输入业务员id" />
        </el-form-item>
        <el-form-item label="客户id" prop="customerId">
          <el-input v-model="form.customerId" placeholder="请输入客户id" />
        </el-form-item>
        <el-form-item label="交货日期" prop="dueDate">
          <el-date-picker clearable
            v-model="form.dueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择交货日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="数量" prop="quantity">
          <el-input v-model="form.quantity" placeholder="请输入数量" />
        </el-form-item>
        <el-form-item label="订单金额" prop="amount">
          <el-input v-model="form.amount" placeholder="请输入订单金额" />
        </el-form-item>
        <el-form-item label="付款金额" prop="payAmount">
          <el-input v-model="form.payAmount" placeholder="请输入付款金额" />
        </el-form-item>
        <el-form-item label="订单状态" prop="orderStatus">
          <el-select v-model="form.orderStatus" placeholder="请选择订单状态">
            <el-option
              v-for="dict in dict.type.sales_order_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批状态" prop="auditStatus">
          <el-select v-model="form.auditStatus" placeholder="请选择审批状态">
            <el-option
              v-for="dict in dict.type.erp_sample_audit_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="审批人" prop="auditBy">
          <el-input v-model="form.auditBy" placeholder="请输入审批人" />
        </el-form-item>
        <el-form-item label="审批时间" prop="auditTime">
          <el-date-picker clearable
            v-model="form.auditTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择审批时间">
          </el-date-picker>
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
import { listSales, getSales, delSales, addSales, updateSales } from "@/api/erp/sales"

export default {
  name: "Sales",
  dicts: ['erp_sales_type', 'sales_order_status', 'erp_sample_audit_status'],
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
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salesType: null,
        noticeId: null,
        techId: null,
        salesNo: null,
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
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
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
      this.single = selection.length!==1
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
      this.download('erp/sales/export', {
        ...this.queryParams
      }, `sales_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
