<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('check.auditStatus')" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" :placeholder="$t('validation.select', [$t('check.auditStatus')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('check.progressStatus')" prop="progressStatus">
        <el-select v-model="queryParams.progressStatus" :placeholder="$t('validation.select', [$t('check.progressStatus')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_check_progress"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('check.customerName')" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          :placeholder="$t('validation.enter', [$t('check.customerName')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('check.sampleType')" prop="sampleType">
        <el-select v-model="queryParams.sampleType" :placeholder="$t('validation.select', [$t('check.sampleType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('check.styleType')" prop="styleType">
        <el-select v-model="queryParams.styleType" :placeholder="$t('validation.select', [$t('check.styleType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_style"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('check.sampleCategoryType')" prop="sampleCategoryType">
        <el-select v-model="queryParams.sampleCategoryType" :placeholder="$t('validation.select', [$t('check.sampleCategoryType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('check.styleCode')" prop="styleCode">
        <el-input
          v-model="queryParams.styleCode"
          :placeholder="$t('validation.enter', [$t('check.styleCode')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('check.bulkOrderNo')" prop="bulkOrderNo">
        <el-input
          v-model="queryParams.bulkOrderNo"
          :placeholder="$t('validation.enter', [$t('check.bulkOrderNo')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('check.salesName')" prop="salesName">
        <el-input
          v-model="queryParams.salesName"
          :placeholder="$t('validation.enter', [$t('check.salesName')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('check.techNo')" prop="techNo">
        <el-input
          v-model="queryParams.techNo"
          :placeholder="$t('validation.enter', [$t('check.techNo')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('check.sampleNo')" prop="sampleNo">
        <el-input
          v-model="queryParams.sampleNo"
          :placeholder="$t('validation.enter', [$t('check.sampleNo')])"
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
          v-hasPermi="['erp:check:add']"
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
          v-hasPermi="['erp:check:edit']"
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
          v-hasPermi="['erp:check:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:check:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          v-hasPermi="['erp:check:import']"
        >{{ $t('btn.import') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="checkList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('check.auditStatus')" align="center" prop="auditStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('check.progressStatus')" align="center" prop="progressStatus" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_check_progress" :value="scope.row.progressStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('check.customerName')" align="center" prop="customerName" />
      <el-table-column :label="$t('check.sampleType')" align="center" prop="sampleType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_type" :value="scope.row.sampleType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('check.styleType')" align="center" prop="styleType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_style" :value="scope.row.styleType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('check.sampleCategoryType')" align="center" prop="sampleCategoryType" width="100">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_category" :value="scope.row.sampleCategoryType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('check.styleCode')" align="center" prop="styleCode" width="120" />
      <el-table-column :label="$t('check.bulkOrderNo')" align="center" prop="bulkOrderNo" width="120" />
      <el-table-column :label="$t('check.salesName')" align="center" prop="salesName" width="100" />
      <el-table-column :label="$t('check.dueDate')" align="center" prop="dueDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('check.techNo')" align="center" prop="techNo" width="140" />
      <el-table-column :label="$t('check.sampleNo')" align="center" prop="sampleNo" width="140" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width" width="200">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:check:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:check:remove']"
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

    <!-- 添加或修改大货核版对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="70%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('check.auditStatus')" prop="auditStatus">
              <el-select v-model="form.auditStatus" :placeholder="$t('validation.select', [$t('check.auditStatus')])">
                <el-option
                  v-for="dict in dict.type.erp_sample_audit_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('check.progressStatus')" prop="progressStatus">
              <el-select v-model="form.progressStatus" :placeholder="$t('validation.select', [$t('check.progressStatus')])">
                <el-option
                  v-for="dict in dict.type.erp_check_progress"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('check.sampleType')" prop="sampleType">
              <el-select v-model="form.sampleType" :placeholder="$t('validation.select', [$t('check.sampleType')])">
                <el-option
                  v-for="dict in dict.type.erp_sample_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('check.customerName')" prop="customerName">
              <el-input v-model="form.customerName" :placeholder="$t('validation.enter', [$t('check.customerName')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('check.styleType')" prop="styleType">
              <el-select v-model="form.styleType" :placeholder="$t('validation.select', [$t('check.styleType')])">
                <el-option
                  v-for="dict in dict.type.erp_sample_style"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('check.sampleCategoryType')" prop="sampleCategoryType">
              <el-select v-model="form.sampleCategoryType" :placeholder="$t('validation.select', [$t('check.sampleCategoryType')])">
                <el-option
                  v-for="dict in dict.type.erp_sample_category"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('check.styleCode')" prop="styleCode">
              <el-input v-model="form.styleCode" :placeholder="$t('validation.enter', [$t('check.styleCode')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('check.bulkOrderNo')" prop="bulkOrderNo">
              <el-input v-model="form.bulkOrderNo" :placeholder="$t('validation.enter', [$t('check.bulkOrderNo')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('check.salesName')" prop="salesName">
              <el-input v-model="form.salesName" :placeholder="$t('validation.enter', [$t('check.salesName')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('check.dueDate')" prop="dueDate">
              <el-date-picker clearable
                v-model="form.dueDate"
                type="date"
                value-format="yyyy-MM-dd"
                :placeholder="$t('validation.select', [$t('check.dueDate')])">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item :label="$t('check.techNo')" prop="techNo">
              <el-input v-model="form.techNo" :placeholder="$t('validation.enter', [$t('check.techNo')])" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('check.sampleNo')" prop="sampleNo">
              <el-input v-model="form.sampleNo" :placeholder="$t('validation.enter', [$t('check.sampleNo')])" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('check.checkDescription')" prop="checkDescription">
          <el-input v-model="form.checkDescription" type="textarea" :placeholder="$t('validation.enter', [$t('check.checkDescription')])" />
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
          {{ $t('upload.dragText') }}，
          <em>{{ $t('btn.upload') }}</em>
        </div>
        <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" /> {{ $t('upload.updateExisting') }}
          <el-link type="info" style="font-size:12px" @click="importTemplate">{{ $t('btn.downloadTemplate') }}</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="upload.open = false">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCheck, getCheck, delCheck, addCheck, updateCheck } from "@/api/erp/check"
import { getToken } from "@/utils/auth"

export default {
  name: "Check",
  dicts: ['erp_sample_type', 'erp_sample_style', 'erp_sample_category', 'erp_sample_audit_status', 'erp_check_progress'],
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
      // 大货核版表格数据
      checkList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      submitLoading: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        auditStatus: null,
        progressStatus: null,
        customerName: null,
        sampleType: null,
        styleType: null,
        sampleCategoryType: null,
        styleCode: null,
        bulkOrderNo: null,
        salesName: null,
        techNo: null,
        sampleNo: null,
      },
      // 表单参数
      form: {},
      // 上传参数
      upload: {
        open: false,
        title: "",
        isUploading: false,
        updateSupport: 0,
        headers: { Authorization: "Bearer " + getToken() },
        url: process.env.VUE_APP_BASE_API + "/erp/check/importData"
      }
    }
  },
  computed: {
    rules() {
      return {
        sampleType: [
          { required: true, message: this.$t('validation.required', [this.$t('check.sampleType')]), trigger: "change" }
        ],
        customerName: [
          { required: true, message: this.$t('validation.required', [this.$t('check.customerName')]), trigger: "blur" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询大货核版列表 */
    getList() {
      this.loading = true
      listCheck(this.queryParams).then(response => {
        this.checkList = response.rows
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
        checkId: null,
        checkNo: null,
        sampleNo: null,
        techNo: null,
        sampleType: null,
        styleType: null,
        sampleCategoryType: null,
        styleCode: null,
        bulkOrderNo: null,
        customerId: null,
        customerName: null,
        salesId: null,
        salesName: null,
        dueDate: null,
        progressStatus: null,
        auditStatus: null,
        auditBy: null,
        auditByName: null,
        auditTime: null,
        quantity: null,
        checkDescription: null,
        checkResult: null,
        status: null,
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
      this.ids = selection.map(item => item.checkId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset()
      this.open = true
      this.title = this.$t('check.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const checkId = row.checkId || this.ids
      getCheck(checkId).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('check.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.checkId != null) {
            updateCheck(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addCheck(this.form).then(response => {
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
      const checkIds = row.checkId || this.ids
      this.$modal.confirm(this.$t('msg.deleteConfirm', [checkIds])).then(function() {
        return delCheck(checkIds)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/check/export', {
        ...this.queryParams
      }, `check_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = this.$t('check.importTitle')
      this.upload.open = true
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download('erp/check/importTemplate', {}, 'check_template.xlsx')
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
