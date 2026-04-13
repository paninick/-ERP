<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="出库单号" prop="stockOutNo">
        <el-input v-model="queryParams.stockOutNo" placeholder="请输入出库单号" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="目标单号" prop="targetNo">
        <el-input v-model="queryParams.targetNo" placeholder="请输入目标单号(销售单号等)" clearable style="width: 200px" @keyup.enter.native="handleQuery" />
      </el-form-item>
      <el-form-item label="出库类型" prop="stockOutType">
        <el-select v-model="queryParams.stockOutType" placeholder="请选择出库类型" clearable style="width: 200px">
          <el-option v-for="dict in dict.type.erp_stock_out_type" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择状态" clearable style="width: 200px">
          <el-option v-for="dict in dict.type.erp_stock_out_status" :key="dict.value" :label="dict.label" :value="dict.value" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary" plain icon="el-icon-plus" size="mini" @click="handleAdd" v-hasPermi="['erp:stockout:add']">新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="success" plain icon="el-icon-edit" size="mini" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:stockout:edit']">修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="danger" plain icon="el-icon-delete" size="mini" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:stockout:remove']">删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button type="warning" plain icon="el-icon-download" size="mini" @click="handleExport" v-hasPermi="['erp:stockout:export']">导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockOutList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="50" align="center" />
      <el-table-column label="出库ID" align="center" prop="stockOutId" width="80" />
      <el-table-column label="出库单号" align="center" prop="stockOutNo" width="160" />
      <el-table-column label="出库类型" align="center" prop="stockOutType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_stock_out_type" :value="scope.row.stockOutType"/>
        </template>
      </el-table-column>
      <el-table-column label="目标单号" align="center" prop="targetNo" width="160" />
      <el-table-column label="仓库" align="center" prop="warehouseName" :show-overflow-tooltip="true" />
      <el-table-column label="出库数量" align="center" prop="totalQty" width="90" />
      <el-table-column label="出库人" align="center" prop="operatorName" width="100" />
      <el-table-column label="出库时间" align="center" prop="stockOutTime" width="160">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.stockOutTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="80">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_stock_out_status" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="220">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)">查看</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" @click="handleUpdate(scope.row)" v-hasPermi="['erp:stockout:edit']">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-delete" @click="handleDelete(scope.row)" v-hasPermi="['erp:stockout:remove']">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination v-show="total > 0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" />

    <!-- 添加或修改出库单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="出库单号" prop="stockOutNo">
              <el-input v-model="form.stockOutNo" placeholder="系统自动生成" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="出库类型" prop="stockOutType">
              <el-select v-model="form.stockOutType" placeholder="请选择出库类型">
                <el-option v-for="dict in dict.type.erp_stock_out_type" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="目标单号" prop="targetNo">
              <el-input v-model="form.targetNo" placeholder="请输入目标单号" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="仓库" prop="warehouseId">
              <el-select v-model="form.warehouseId" placeholder="请选择仓库">
                <el-option v-for="item in warehouseOptions" :key="item.warehouseId" :label="item.warehouseName" :value="item.warehouseId" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="出库时间" prop="stockOutTime">
              <el-date-picker v-model="form.stockOutTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期时间"></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-radio-group v-model="form.status">
                <el-radio v-for="dict in dict.type.erp_stock_out_status" :key="dict.value" :label="dict.value">{{ dict.label }}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
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
import { listStockOut, getStockOut, delStockOut, addStockOut, updateStockOut } from "@/api/erp/stockout"
import { listWarehouse } from "@/api/erp/warehouse"

export default {
  name: "StockOut",
  dicts: ['erp_stock_out_type', 'erp_stock_out_status'],
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
      // 出库单表格数据
      stockOutList: [],
      // 仓库选项
      warehouseOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        stockOutNo: undefined,
        targetNo: undefined,
        stockOutType: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        stockOutType: [
          { required: true, message: "出库类型不能为空", trigger: "change" }
        ],
        warehouseId: [
          { required: true, message: "仓库不能为空", trigger: "change" }
        ],
        stockOutTime: [
          { required: true, message: "出库时间不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
    this.getWarehouseList()
  },
  methods: {
    /** 查询出库单列表 */
    getList() {
      this.loading = true
      listStockOut(this.queryParams).then(response => {
        this.stockOutList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    /** 获取仓库列表 */
    getWarehouseList() {
      listWarehouse({ pageNum: 1, pageSize: 9999 }).then(response => {
        this.warehouseOptions = response.rows
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
        stockOutId: undefined,
        stockOutNo: undefined,
        stockOutType: undefined,
        targetNo: undefined,
        warehouseId: undefined,
        warehouseName: undefined,
        totalQty: 0,
        operatorName: undefined,
        stockOutTime: undefined,
        status: "draft",
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
      this.ids = selection.map(item => item.stockOutId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加出库单"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const stockOutId = row.stockOutId || this.ids
      getStockOut(stockOutId).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改出库单"
      })
    },
    /** 查看详情 */
    handleView(row) {
      this.$message.info("查看出库单: " + row.stockOutNo)
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.stockOutId != undefined) {
            updateStockOut(this.form).then(() => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStockOut(this.form).then(() => {
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
      const stockOutIds = row.stockOutId || this.ids
      this.$modal.confirm('是否确认删除出库单号为"' + stockOutIds + '"的数据项？').then(function() {
        return delStockOut(stockOutIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/stockout/export', {
        ...this.queryParams
      }, `stockout_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
