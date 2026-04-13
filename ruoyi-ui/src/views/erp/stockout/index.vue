<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="大货款号" prop="bulkOrderNo">
        <el-input
          v-model="queryParams.bulkOrderNo"
          placeholder="请输入款号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="出库单号" prop="sn">
        <el-input
          v-model="queryParams.sn"
          placeholder="请输入出库单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="确认状态" prop="confirmStatus">
        <el-select v-model="queryParams.confirmStatus" placeholder="请选择确认状态" clearable>
          <el-option
            v-for="dict in dict.type.erp_confirm_status"
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
          v-hasPermi="['erp:stock:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:stock:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:stock:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="stockoutList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="类型" align="center" prop="outType" width="100">
        <template slot-scope="scope">
          <span>{{ getOutTypeLabel(scope.row.outType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="大货款号" align="center" prop="bulkOrderNo" width="180" :show-overflow-tooltip="true" />
      <el-table-column label="出库简介" align="center" prop="outDescription" width="200" :show-overflow-tooltip="true" />
      <el-table-column label="申请人" align="center" prop="applicant" width="100" />
      <el-table-column label="申请日期" align="center" prop="applyDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.applyDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="确认时间" align="center" prop="confirmTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.confirmTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="确认人" align="center" prop="confirmBy" width="100" />
      <el-table-column label="出库确认" align="center" prop="confirmStatus" width="100">
        <template slot-scope="scope">
          <span>{{ getConfirmStatusLabel(scope.row.confirmStatus) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="出库单号" align="center" prop="sn" width="140" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:stock:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-check"
            @click="handleConfirm(scope.row)"
            v-hasPermi="['erp:stock:edit']"
          >确认</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:stock:remove']"
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

    <!-- 添加或修改出库单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="出库单号" prop="sn" required>
          <el-input v-model="form.sn" placeholder="请输入出库单号" />
        </el-form-item>
        <el-form-item label="类型" prop="outType" required>
          <el-select v-model="form.outType" placeholder="请选择类型">
            <el-option label="面料" :value="1" />
            <el-option label="纱线" :value="2" />
            <el-option label="辅料" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item label="大货款号" prop="bulkOrderNo">
          <el-input v-model="form.bulkOrderNo" placeholder="请输入大货款号" />
        </el-form-item>
        <el-form-item label="出库简介" prop="outDescription">
          <el-input v-model="form.outDescription" type="textarea" placeholder="请输入出库简介" />
        </el-form-item>
        <el-form-item label="申请人" prop="applicant">
          <el-input v-model="form.applicant" placeholder="请输入申请人" />
        </el-form-item>
        <el-form-item label="申请日期" prop="applyDate">
          <el-date-picker clearable
            v-model="form.applyDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择申请日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="确认人" prop="confirmBy">
          <el-input v-model="form.confirmBy" placeholder="请输入确认人" />
        </el-form-item>
        <el-form-item label="确认时间" prop="confirmTime">
          <el-date-picker clearable
            v-model="form.confirmTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择确认时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="确认状态" prop="confirmStatus">
          <el-select v-model="form.confirmStatus" placeholder="请选择确认状态">
            <el-option
              v-for="dict in dict.type.erp_confirm_status"
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
  </div>
</template>

<script>
import { listStockout, getStockout, delStockout, addStockout, updateStockout } from "@/api/erp/stockout"

export default {
  name: "StockOut",
  dicts: ['erp_confirm_status'],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      stockoutList: [],
      title: "",
      open: false,
      outTypeOptions: [
        { value: '1', label: '面料' },
        { value: '2', label: '纱线' },
        { value: '3', label: '辅料' }
      ],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        sn: null,
        bulkOrderNo: null,
        confirmStatus: null
      },
      form: {},
      rules: {
        sn: [
          { required: true, message: "出库单号不能为空", trigger: "blur" }
        ],
        outType: [
          { required: true, message: "类型不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getOutTypeLabel(value) {
      const typeMap = {'1': '面料', '2': '纱线', '3': '辅料'}
      return typeMap[value] || value
    },
    getConfirmStatusLabel(value) {
      const statusMap = {'0': '待确认', '1': '已确认'}
      return statusMap[value] || value
    },
    getList() {
      this.loading = true
      listStockout(this.queryParams).then(response => {
        this.stockoutList = response.rows
        this.total = response.total
        this.loading = false
      })
    },
    cancel() {
      this.open = false
      this.reset()
    },
    reset() {
      this.form = {
        id: null,
        sn: null,
        outDate: null,
        outType: null,
        chargeUserId: null,
        confirmStatus: null,
        applicant: null,
        applyDate: null,
        confirmBy: null,
        confirmTime: null,
        purchaseId: null,
        purchaseSn: null,
        planId: null,
        bulkOrderNo: null,
        outDescription: null,
        remark: null
      }
      this.resetForm("form")
    },
    handleQuery() {
      this.queryParams.pageNum = 1
      this.getList()
    },
    resetQuery() {
      this.resetForm("queryForm")
      this.handleQuery()
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加出库单"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getStockout(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改出库单"
      })
    },
    handleConfirm(row) {
      this.$modal.confirm('是否确认出库单编号为"' + row.sn + '"的数据？').then(() => {
        row.confirmStatus = '1'
        updateStockout(row).then(() => {
          this.$modal.msgSuccess("确认成功")
          this.getList()
        })
      }).catch(() => {})
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateStockout(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addStockout(this.form).then(response => {
              this.$modal.msgSuccess("新增成功")
              this.open = false
              this.getList()
            })
          }
        }
      })
    },
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm('是否确认删除出库单编号为"' + ids + '"的数据项？').then(function() {
        return delStockout(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/stockOut/export', {
        ...this.queryParams
      }, `stock_out_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
