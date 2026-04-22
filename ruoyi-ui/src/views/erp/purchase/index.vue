<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="供应商" prop="supplierId">
        <el-select v-model="queryParams.supplierId" placeholder="请选择供应商" clearable
          filterable clearable remote :remote-method="filterSupplier" loading="supplierLoading">
          <el-option
            v-for="item in supplierOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="采购单号" prop="sn">
        <el-input
          v-model="queryParams.sn"
          placeholder="请输入采购单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="类型" prop="type">
        <el-input
          v-model="queryParams.type"
          placeholder="请输入类型"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-input
          v-model="queryParams.status"
          placeholder="请输入状态"
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
          v-hasPermi="['erp:purchase:add']"
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
          v-hasPermi="['erp:purchase:edit']"
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
          v-hasPermi="['erp:purchase:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:purchase:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="purchaseList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="类型" align="center" prop="type" width="100" />
      <el-table-column label="大货款号" align="center" prop="bulkOrderNo" width="180" show-overflow-tooltip />
      <el-table-column label="说明" align="center" prop="description" min-width="200" show-overflow-tooltip />
      <el-table-column label="预计到货日期" align="center" prop="expectedDeliveryDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.expectedDeliveryDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="采购员" align="center" prop="purchaseName" width="100" />
      <el-table-column label="确认时间" align="center" prop="confirmTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.confirmTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" prop="status" width="100" />
      <el-table-column label="采购单号" align="center" prop="sn" width="140" show-overflow-tooltip />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="150">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:purchase:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:purchase:remove']"
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

    <!-- 添加或修改采购单对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="供应商" prop="supplierId" required>
              <el-select v-model="form.supplierId" placeholder="请选择供应商" clearable
                filterable clearable remote :remote-method="filterSupplier" loading="supplierLoading">
                <el-option
                  v-for="item in supplierOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="采购单号" prop="sn" required>
              <el-input v-model="form.sn" placeholder="保存时自动生成（PO-yyyyMMdd-序号）" disabled />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="类型" prop="type" required>
              <el-input v-model="form.type" placeholder="请输入类型" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="大货款号" prop="bulkOrderNo">
              <el-input v-model="form.bulkOrderNo" placeholder="请输入大货款号" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="说明" prop="description">
          <el-input v-model="form.description" type="textarea" placeholder="请输入说明" />
        </el-form-item>
        <el-row>
          <el-col :span="12">
            <el-form-item label="预计到货日期" prop="expectedDeliveryDate" required>
              <el-date-picker clearable
                v-model="form.expectedDeliveryDate"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择预计到货日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="采购员" prop="purchaseUserId">
              <el-select v-model="form.purchaseUserId" placeholder="请选择采购员" clearable
                filterable clearable remote :remote-method="filterUser" loading="userLoading">
                <el-option
                  v-for="item in userOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="确认时间" prop="confirmTime">
              <el-date-picker clearable
                v-model="form.confirmTime"
                type="date"
                value-format="yyyy-MM-dd"
                placeholder="请选择确认时间">
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="状态" prop="status">
              <el-input v-model="form.status" placeholder="请输入状态" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item label="订单金额" prop="amount">
          <el-input-number v-model="form.amount" :precision="2" :min="0" placeholder="请输入订单金额" />
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
import { listPurchase, getPurchase, delPurchase, addPurchase, updatePurchase } from "@/api/erp/purchase"
import { listSupplier } from "@/api/erp/supplier"

export default {
  name: "Purchase",
  dicts: [],
  data() {
    return {
      loading: true,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      purchaseList: [],
      title: "",
      open: false,
      // 供应商选项
      supplierOptions: [],
      supplierLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        type: null,
        bulkOrderNo: null,
        purchaseName: null,
        status: null,
        sn: null,
        supplierId: null
      },
      form: {},
      // 表单校验
      rules: {
        sn: [
          { required: true, message: "采购单号不能为空", trigger: "blur" }
        ],
        type: [
          { required: true, message: "类型不能为空", trigger: "blur" }
        ],
        supplierId: [
          { required: true, message: "供应商不能为空", trigger: "change" }
        ],
        expectedDeliveryDate: [
          { required: true, message: "预计到货日期不能为空", trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 过滤供应商 */
    filterSupplier(query) {
      if (!query) {
        this.supplierOptions = []
        return
      }
      this.supplierLoading = true
      listSupplier({ pageNum: 1, pageSize: 20, supplierName: query }).then(response => {
        this.supplierOptions = response.rows.map(r => ({
          value: r.id,
          label: r.supplierName
        }))
        this.supplierLoading = false
      }).catch(() => {
        this.supplierLoading = false
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
    getList() {
      this.loading = true
      listPurchase(this.queryParams).then(response => {
        this.purchaseList = response.rows
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
        type: null,
        bulkOrderNo: null,
        description: null,
        expectedDeliveryDate: null,
        purchaseName: null,
        confirmTime: null,
        status: null,
        sn: null,
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
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    handleAdd() {
      this.reset()
      this.open = true
      this.title = "添加采购单"
    },
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getPurchase(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改采购单"
      })
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updatePurchase(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addPurchase(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除采购单编号为"' + ids + '"的数据项？').then(function() {
        return delPurchase(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    handleExport() {
      this.download('erp/purchase/export', {
        ...this.queryParams
      }, `purchase_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
