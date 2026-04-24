<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('supplier.type')" prop="supplierType">
        <el-select v-model="queryParams.supplierType" :placeholder="$t('validation.select', [$t('supplier.type')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_supplier_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('supplier.no')" prop="supplierNo">
        <el-input
          v-model="queryParams.supplierNo"
          :placeholder="$t('validation.enter', [$t('supplier.no')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('supplier.name')" prop="supplierName">
        <el-input
          v-model="queryParams.supplierName"
          :placeholder="$t('validation.enter', [$t('supplier.name')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('supplier.brief')" prop="supplierBrief">
        <el-input
          v-model="queryParams.supplierBrief"
          :placeholder="$t('validation.enter', [$t('supplier.brief')])"
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
          v-hasPermi="['erp:supplier:add']"
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
          v-hasPermi="['erp:supplier:edit']"
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
          v-hasPermi="['erp:supplier:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:supplier:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['erp:supplier:import']"
        >{{ $t('btn.import') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="supplierList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('supplier.type')" align="center" prop="supplierType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_supplier_type" :value="scope.row.supplierType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('supplier.no')" align="center" prop="supplierNo" />
      <el-table-column :label="$t('supplier.name')" align="center" prop="supplierName" />
      <el-table-column :label="$t('supplier.brief')" align="center" prop="supplierBrief" />
      <el-table-column :label="$t('system.createTime')" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.updateTime')" align="center" prop="updateTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:supplier:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:supplier:remove']"
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

    <!-- 添加或修改供应商对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('supplier.type')" prop="supplierType">
          <el-select v-model="form.supplierType" :placeholder="$t('validation.select', [$t('supplier.type')])">
            <el-option
              v-for="dict in dict.type.erp_supplier_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('supplier.no')" prop="supplierNo">
          <el-input v-model="form.supplierNo" :placeholder="$t('validation.enter', [$t('supplier.no')])" />
        </el-form-item>
        <el-form-item :label="$t('supplier.name')" prop="supplierName">
          <el-input v-model="form.supplierName" :placeholder="$t('validation.enter', [$t('supplier.name')])" />
        </el-form-item>
        <el-form-item :label="$t('supplier.brief')" prop="supplierBrief">
          <el-input v-model="form.supplierBrief" :placeholder="$t('validation.enter', [$t('supplier.brief')])" />
        </el-form-item>
        <el-form-item :label="$t('supplier.businessScope')" prop="businessScope">
          <el-input v-model="form.businessScope" :placeholder="$t('validation.enter', [$t('supplier.businessScope')])" />
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

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        :action="upload.url"
        :headers="upload.headers"
        :data="upload.data"
        :on-change="upload.handleChange"
        :before-upload="upload.beforeUpload"
        :on-success="upload.onSuccess"
        :on-error="upload.onError"
        :limit="1"
        accept=".xlsx,.xls"
        drag
        auto-upload
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">{{ $t('upload.dragText') }}<em>{{ $t('upload.clickToUpload') }}</em></div>
        <div class="el-upload__tip" slot="tip">{{ $t('upload.allowedExcelShort') }}</div>
      </el-upload>
    </el-dialog>
  </div>
</template>

<script>
import { listSupplier, getSupplier, delSupplier, addSupplier, updateSupplier } from "@/api/erp/supplier"
import { getToken } from "@/utils/auth"

export default {
  name: "Supplier",
  dicts: ['erp_supplier_type'],
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
      // 供应商表格数据
      supplierList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        supplierType: null,
        supplierNo: null,
        supplierName: null,
        supplierBrief: null,
      },
      // 表单参数
      form: {},
      // 上传参数
      upload: {
        open: false,
        title: "",
        url: process.env.VUE_APP_BASE_API + "/erp/supplier/importData",
        headers: { Authorization: "Bearer " + getToken() },
        data: {
          updateSupport: false
        }
      }
    }
  },
  computed: {
    rules() {
      return {
        supplierName: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询供应商列表 */
    getList() {
      this.loading = true
      listSupplier(this.queryParams).then(response => {
        this.supplierList = response.rows
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
        supplierType: null,
        supplierNo: null,
        supplierName: null,
        supplierBrief: null,
        businessScope: null,
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
      this.title = this.$t('supplier.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getSupplier(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('supplier.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateSupplier(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.updateSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addSupplier(this.form).then(response => {
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
        return delSupplier(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/supplier/export', {
        ...this.queryParams
      }, `supplier_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = this.$t('supplier.importTitle')
      this.upload.open = true
    },
    /** 上传成功回调 */
    'upload.onSuccess'(response, file, fileList) {
      if (response.code === 200) {
        this.$modal.msgSuccess(response.msg)
        this.upload.open = false
        this.getList()
      } else {
        this.$modal.msgError(response.msg)
      }
    },
    /** 上传失败回调 */
    'upload.onError'(err, file, fileList) {
      this.$modal.msgError(this.$t('upload.uploadFailed'))
    },
    /** 上传前校验 */
    'upload.beforeUpload'(file) {
      const isExcel = /\.(xlsx|xls)$/i.test(file.name)
      if (!isExcel) {
        this.$modal.msgError(this.$t('upload.onlyExcel'))
        return false
      }
      const isLt10M = file.size / 1024 / 1024 < 10
      if (!isLt10M) {
        this.$modal.msgError(this.$t('upload.sizeLimit'))
        return false
      }
      return true
    },
    /** 文件改变 */
    'upload.handleChange'(file, fileList) {
    }
  }
}
</script>
