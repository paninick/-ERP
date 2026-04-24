<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('processLossMatrix.materialAType')" prop="materialAType">
        <el-input
          v-model="queryParams.materialAType"
          :placeholder="$t('processLossMatrix.enterMaterialAType')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('processLossMatrix.materialBType')" prop="materialBType">
        <el-input
          v-model="queryParams.materialBType"
          :placeholder="$t('processLossMatrix.enterMaterialBType')"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('processLossMatrix.processCode')" prop="processCode">
        <el-input
          v-model="queryParams.processCode"
          :placeholder="$t('processLossMatrix.enterProcessCode')"
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
          v-hasPermi="['erp:processLossMatrix:add']"
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
          v-hasPermi="['erp:processLossMatrix:edit']"
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
          v-hasPermi="['erp:processLossMatrix:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:processLossMatrix:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="processLossMatrixList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('processLossMatrix.materialAType')" align="center" prop="materialAType" />
      <el-table-column :label="$t('processLossMatrix.materialBType')" align="center" prop="materialBType" />
      <el-table-column :label="$t('processLossMatrix.processCode')" align="center" prop="processCode" />
      <el-table-column :label="$t('processLossMatrix.standardLossRate')" align="center" prop="standardLossRate" />
      <el-table-column :label="$t('processLossMatrix.actualAvgLoss')" align="center" prop="actualAvgLoss" />
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
            v-hasPermi="['erp:processLossMatrix:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:processLossMatrix:remove']"
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

    <!-- 添加或修改工序损耗矩阵对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('processLossMatrix.materialAType')" prop="materialAType">
          <el-input v-model="form.materialAType" :placeholder="$t('processLossMatrix.enterMaterialAType')" />
        </el-form-item>
        <el-form-item :label="$t('processLossMatrix.materialBType')" prop="materialBType">
          <el-input v-model="form.materialBType" :placeholder="$t('processLossMatrix.enterMaterialBTypeOptional')" />
        </el-form-item>
        <el-form-item :label="$t('processLossMatrix.processCode')" prop="processCode">
          <el-input v-model="form.processCode" :placeholder="$t('processLossMatrix.enterProcessCode')" />
        </el-form-item>
        <el-form-item :label="$t('processLossMatrix.standardLossRate')" prop="standardLossRate">
          <el-input-number v-model="form.standardLossRate" :precision="4" :min="0" :max="100" :placeholder="$t('processLossMatrix.standardLossRateHint')" />
        </el-form-item>
        <el-form-item :label="$t('processLossMatrix.actualAvgLoss')" prop="actualAvgLoss">
          <el-input-number v-model="form.actualAvgLoss" :precision="4" :min="0" :max="100" :placeholder="$t('processLossMatrix.actualAvgLossHint')" />
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
import { listProcessLossMatrix, getProcessLossMatrix, delProcessLossMatrix, addProcessLossMatrix, updateProcessLossMatrix } from "@/api/erp/processLossMatrix"

export default {
  name: "ProcessLossMatrix",
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
      // 工序损耗矩阵表格数据
      processLossMatrixList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        materialAType: null,
        materialBType: null,
        processCode: null
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
    /** 查询工序损耗矩阵列表 */
    getList() {
      this.loading = true
      listProcessLossMatrix(this.queryParams).then(response => {
        this.processLossMatrixList = response.rows
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
        materialAType: null,
        materialBType: null,
        processCode: null,
        standardLossRate: null,
        actualAvgLoss: null,
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
      this.title = this.$t('processLossMatrix.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getProcessLossMatrix(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('processLossMatrix.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateProcessLossMatrix(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addProcessLossMatrix(this.form).then(response => {
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
        return delProcessLossMatrix(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/processLossMatrix/export', {
        ...this.queryParams
      }, `processLossMatrix_${new Date().getTime()}.xlsx`)
    }
  }
}
</script>
