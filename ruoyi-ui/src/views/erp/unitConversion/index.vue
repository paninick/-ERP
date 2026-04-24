<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('unitConversion.materialId')" prop="materialId">
        <el-input
          v-model="queryParams.materialId"
          :placeholder="$t('unitConversion.enterMaterialId')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">{{ $t('btn.search') }}</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
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
          v-hasPermi="['erp:unitConversion:add']"
        >{{ $t('btn.add') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['erp:unitConversion:edit']"
        >{{ $t('btn.edit') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['erp:unitConversion:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:unitConversion:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="unitConversionList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('unitConversion.materialId')" align="center" prop="materialId" />
      <el-table-column :label="$t('unitConversion.purchaseUnit')" align="center" prop="purchaseUnit" />
      <el-table-column :label="$t('unitConversion.stockUnit')" align="center" prop="stockUnit" />
      <el-table-column :label="$t('unitConversion.produceUnit')" align="center" prop="produceUnit" />
      <el-table-column :label="$t('unitConversion.purchaseToStock')" align="center" prop="purchaseToStock" />
      <el-table-column :label="$t('unitConversion.stockToProduce')" align="center" prop="stockToProduce" />
      <el-table-column :label="$t('system.createTime')" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:unitConversion:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:unitConversion:remove']"
          >{{ $t('btn.delete') }}</el-button>
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

    <!-- 添加或修改单位换算对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('unitConversion.materialId')" prop="materialId">
          <el-input v-model="form.materialId" :placeholder="$t('unitConversion.enterMaterialId')" />
        </el-form-item>
        <el-form-item :label="$t('unitConversion.purchaseUnit')" prop="purchaseUnit">
          <el-input v-model="form.purchaseUnit" :placeholder="$t('unitConversion.enterPurchaseUnit')" />
        </el-form-item>
        <el-form-item :label="$t('unitConversion.stockUnit')" prop="stockUnit">
          <el-input v-model="form.stockUnit" :placeholder="$t('unitConversion.enterStockUnit')" />
        </el-form-item>
        <el-form-item :label="$t('unitConversion.produceUnit')" prop="produceUnit">
          <el-input v-model="form.produceUnit" :placeholder="$t('unitConversion.enterProduceUnit')" />
        </el-form-item>
        <el-form-item :label="$t('unitConversion.purchaseToStockLabel')" prop="purchaseToStock">
          <el-input-number v-model="form.purchaseToStock" :precision="6" :min="0.000001" :placeholder="$t('unitConversion.purchaseToStockHint')" />
          <span class="el-form-item__label-hint">{{ $t('unitConversion.purchaseToStockHint') }}</span>
        </el-form-item>
        <el-form-item :label="$t('unitConversion.stockToProduceLabel')" prop="stockToProduce">
          <el-input-number v-model="form.stockToProduce" :precision="6" :min="0.000001" :placeholder="$t('unitConversion.stockToProduceHint')" />
          <span class="el-form-item__label-hint">{{ $t('unitConversion.stockToProduceHint') }}</span>
        </el-form-item>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('validation.enter', [$t('system.remark')])" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listUnitConversion, getUnitConversion, delUnitConversion, addUnitConversion, updateUnitConversion } from "@/api/erp/unitConversion"

export default {
  name: "UnitConversion",
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
      // 单位换算表格数据
      unitConversionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialId: null
      },
      // 表单参数
      form: {}
    }
  },
  computed: {
    rules() {
      return {}
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询单位换算列表 */
    getList() {
      this.loading = true
      listUnitConversion(this.queryParams).then(response => {
        this.unitConversionList = response.rows
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
        materialId: null,
        purchaseUnit: null,
        stockUnit: null,
        produceUnit: null,
        purchaseToStock: null,
        stockToProduce: null,
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
      this.title = this.$t('unitConversion.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getUnitConversion(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('unitConversion.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateUnitConversion(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addUnitConversion(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.addSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          }
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids
      this.$modal.confirm(this.$t('msg.deleteConfirm', [ids])).then(function() {
        return delUnitConversion(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/unitConversion/export', {
        ...this.queryParams
      }, `unitConversion_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
