<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="销售订单ID" prop="salesOrderId">
        <el-select v-model="queryParams.salesOrderId" placeholder="请选择销售订单" clearable
          filterable clearable remote :remote-method="filterSalesOrder" loading="salesOrderLoading">
          <el-option
            v-for="item in salesOrderOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="颜色" prop="color">
        <el-input
          v-model="queryParams.color"
          placeholder="请输入颜色"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="尺码" prop="size">
        <el-input
          v-model="queryParams.size"
          placeholder="请输入尺码"
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
          v-hasPermi="['erp:salesitem:add']"
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
          v-hasPermi="['erp:salesitem:edit']"
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
          v-hasPermi="['erp:salesitem:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:salesitem:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="salesitemList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" align="center" prop="id" width="60" />
      <el-table-column label="销售订单" align="center" prop="salesOrderNo" />
      <el-table-column label="颜色" align="center" prop="color" width="100" />
      <el-table-column label="尺码" align="center" prop="size" width="80" />
      <el-table-column label="订单数量" align="center" prop="orderQuantity" width="100" />
      <el-table-column label="排产数量" align="center" prop="planQuantity" width="100" />
      <el-table-column label="入库数量" align="center" prop="inboundAmount" width="100" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:salesitem:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:salesitem:remove']"
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

    <!-- 添加或修改销售订单明细对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="销售订单" prop="salesOrderId" required>
          <el-select v-model="form.salesOrderId" placeholder="请选择销售订单" clearable
            filterable clearable remote :remote-method="filterSalesOrder" loading="salesOrderLoading">
            <el-option
              v-for="item in salesOrderOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="颜色" prop="color" required>
              <el-input v-model="form.color" placeholder="请输入颜色" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="尺码" prop="size" required>
              <el-input v-model="form.size" placeholder="请输入尺码" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="订单数量" prop="orderQuantity" required>
              <el-input-number v-model="form.orderQuantity" :precision="2" :min="0" placeholder="请输入订单数量" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="排产数量" prop="planQuantity">
              <el-input-number v-model="form.planQuantity" :precision="2" :min="0" placeholder="请输入排产数量" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="入库数量" prop="inboundAmount">
          <el-input-number v-model="form.inboundAmount" :precision="2" :min="0" placeholder="请输入入库数量" />
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
  </div>
</template>

<script>
import { listSalesitem, getSalesitem, delSalesitem, addSalesitem, updateSalesitem } from "@/api/erp/salesitem"
import { listSales } from "@/api/erp/sales"

export default {
  name: "Salesitem",
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
      // 销售订单明细表格数据
      salesitemList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 销售订单选项
      salesOrderOptions: [],
      salesOrderLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salesOrderId: null,
        color: null,
        size: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        salesOrderId: [
          { required: true, message: "销售订单不能为空", trigger: "change" }
        ],
        color: [
          { required: true, message: "颜色不能为空", trigger: "blur" }
        ],
        size: [
          { required: true, message: "尺码不能为空", trigger: "blur" }
        ],
        orderQuantity: [
          { required: true, message: "订单数量不能为空", trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 过滤销售订单 */
    filterSalesOrder(query) {
      if (!query) {
        this.salesOrderOptions = []
        return
      }
      this.salesOrderLoading = true
      listSales({ pageNum: 1, pageSize: 20, salesNo: query }).then(response => {
        this.salesOrderOptions = response.rows.map(r => ({
          value: r.id,
          label: r.salesNo
        }))
        this.salesOrderLoading = false
      }).catch(() => {
        this.salesOrderLoading = false
      })
    },
    /** 查询销售订单明细列表 */
    getList() {
      this.loading = true
      listSalesitem(this.queryParams).then(response => {
        this.salesitemList = response.rows
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
        salesOrderId: null,
        noticeId: null,
        techId: null,
        color: null,
        size: null,
        orderQuantity: null,
        planQuantity: null,
        inboundAmount: null,
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
      this.title = "添加销售订单明细"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSalesitem(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改销售订单明细"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateSalesitem(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addSalesitem(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除销售订单明细编号为"' + ids + '"的数据项？').then(function() {
        return delSalesitem(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/salesitem/export', {
        ...this.queryParams
      }, `salesitem_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
