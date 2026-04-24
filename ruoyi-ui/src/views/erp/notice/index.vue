<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item :label="$t('notice.auditStatus')" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" :placeholder="$t('validation.select', [$t('notice.auditStatus')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('notice.customerName')" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          :placeholder="$t('validation.enter', [$t('notice.customerName')])"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item :label="$t('notice.sampleType')" prop="sampleType">
        <el-select v-model="queryParams.sampleType" :placeholder="$t('validation.select', [$t('notice.sampleType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('notice.styleType')" prop="styleType">
        <el-select v-model="queryParams.styleType" :placeholder="$t('validation.select', [$t('notice.styleType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_style"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('notice.sampleCategoryType')" prop="sampleCategoryType">
        <el-select v-model="queryParams.sampleCategoryType" :placeholder="$t('validation.select', [$t('notice.sampleCategoryType')])" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item :label="$t('notice.sampleNo')" prop="sampleNo">
        <el-input
          v-model="queryParams.sampleNo"
          :placeholder="$t('validation.enter', [$t('notice.sampleNo')])"
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
          v-hasPermi="['erp:notice:add']"
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
          v-hasPermi="['erp:notice:edit']"
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
          v-hasPermi="['erp:notice:remove']"
        >{{ $t('btn.delete') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:notice:export']"
        >{{ $t('btn.export') }}</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleImport"
          v-hasPermi="['erp:notice:import']"
        >{{ $t('btn.import') }}</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="noticeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column :label="$t('notice.auditStatus')" align="center" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.customerName')" align="center" prop="customerName" />
      <el-table-column :label="$t('notice.sampleType')" align="center" prop="sampleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_type" :value="scope.row.sampleType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.styleType')" align="center" prop="styleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_style" :value="scope.row.styleType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.sampleCategoryType')" align="center" prop="sampleCategoryType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_category" :value="scope.row.sampleCategoryType"/>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.styleCode')" align="center" prop="styleCode" />
      <el-table-column :label="$t('notice.bulkOrderNo')" align="center" prop="bulkOrderNo" />
      <el-table-column :label="$t('notice.dueDate')" align="center" prop="dueDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.salesName')" align="center" prop="salesName" />
      <el-table-column :label="$t('notice.applyTime')" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.auditBy')" align="center" prop="auditByNickName" />
      <el-table-column :label="$t('notice.auditTime')" align="center" prop="auditTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column :label="$t('notice.sampleNo')" align="center" prop="sampleNo" />
      <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:notice:edit']"
          >{{ $t('btn.edit') }}</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:notice:remove']"
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

    <!-- 添加或修改打样通知对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item :label="$t('notice.sampleType')" prop="sampleType">
          <el-select v-model="form.sampleType" :placeholder="$t('validation.select', [$t('notice.sampleType')])">
            <el-option
              v-for="dict in dict.type.erp_sample_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('notice.customerName')" prop="customerName">
          <el-input v-model="form.customerName" :placeholder="$t('validation.enter', [$t('notice.customerName')])" />
        </el-form-item>
        <el-form-item :label="$t('notice.styleType')" prop="styleType">
          <el-select v-model="form.styleType" :placeholder="$t('validation.select', [$t('notice.styleType')])">
            <el-option
              v-for="dict in dict.type.erp_sample_style"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('notice.sampleCategoryType')" prop="sampleCategoryType">
          <el-select v-model="form.sampleCategoryType" :placeholder="$t('validation.select', [$t('notice.sampleCategoryType')])">
            <el-option
              v-for="dict in dict.type.erp_sample_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('notice.sampleNo')" prop="sampleNo">
          <el-input v-model="form.sampleNo" :placeholder="$t('notice.sampleNoPlaceholder')" disabled />
        </el-form-item>
        <el-form-item :label="$t('notice.bulkOrderNo')" prop="bulkOrderNo">
          <el-input v-model="form.bulkOrderNo" :placeholder="$t('validation.enter', [$t('notice.bulkOrderNo')])" />
        </el-form-item>
        <el-form-item :label="$t('notice.dueDate')" prop="dueDate">
          <el-date-picker clearable
            v-model="form.dueDate"
            type="date"
            value-format="yyyy-MM-dd"
            :placeholder="$t('validation.select', [$t('notice.dueDate')])">
          </el-date-picker>
        </el-form-item>
        <el-form-item :label="$t('notice.salesName')" prop="salesName">
          <el-input v-model="form.salesName" :placeholder="$t('validation.enter', [$t('notice.salesName')])" />
        </el-form-item>
        <el-form-item :label="$t('notice.auditStatus')" prop="auditStatus">
          <el-select v-model="form.auditStatus" :placeholder="$t('validation.select', [$t('notice.auditStatus')])">
            <el-option
              v-for="dict in dict.type.erp_sample_audit_status"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('notice.auditBy')" prop="auditByNickName">
          <el-input v-model="form.auditByNickName" :placeholder="$t('validation.enter', [$t('notice.auditBy')])" />
        </el-form-item>
        <el-form-item :label="$t('notice.auditTime')" prop="auditTime">
          <el-date-picker clearable
            v-model="form.auditTime"
            type="date"
            value-format="yyyy-MM-dd"
            :placeholder="$t('validation.select', [$t('notice.auditTime')])">
          </el-date-picker>
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

    <!-- 打样通知导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading" :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">{{ $t('upload.dragText') }}，<em>{{ $t('upload.clickToUpload') }}</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>{{ $t('upload.allowedExcel') }}</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align:baseline" @click="importTemplate">{{ $t('btn.downloadTemplate') }}</el-link>
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
import { listNotice, getNotice, delNotice, addNotice, updateNotice } from "@/api/erp/notice"
import { getToken } from "@/utils/auth"

export default {
  name: "Notice",
  dicts: ['erp_sample_style', 'erp_sample_emergency', 'erp_sample_category', 'erp_sample_audit_status', 'erp_sample_type'],
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
      // 打样通知表格数据
      noticeList: [],
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
        customerName: null,
        sampleType: null,
        styleType: null,
        sampleCategoryType: null,
        sampleNo: null,
      },
      // 表单参数
      form: {},
      upload: {
        open: false,
        title: "",
        isUploading: false,
        updateSupport: 0,
        headers: { Authorization: "Bearer " + getToken() },
        url: process.env.VUE_APP_BASE_API + "/erp/notice/importData"
      }
    }
  },
  computed: {
    rules() {
      return {
        sampleType: [
          { required: true, message: this.$t('validation.required'), trigger: "change" }
        ],
        customerName: [
          { required: true, message: this.$t('validation.required'), trigger: "blur" }
        ],
        styleType: [
          { required: true, message: this.$t('validation.required'), trigger: "change" }
        ],
        dueDate: [
          { required: true, message: this.$t('validation.required'), trigger: "change" }
        ]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    /** 查询打样通知列表 */
    getList() {
      this.loading = true
      listNotice(this.queryParams).then(response => {
        this.noticeList = response.rows
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
        sampleNo: null,
        sampleType: null,
        customerId: null,
        customerName: null,
        styleType: null,
        sampleCategoryType: null,
        bulkOrderNo: null,
        dueDate: null,
        salesId: null,
        salesName: null,
        auditStatus: null,
        auditBy: null,
        auditByNickName: null,
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
      this.title = this.$t('notice.addTitle')
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getNotice(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = this.$t('notice.editTitle')
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true
          if (this.form.id != null) {
            updateNotice(this.form).then(response => {
              this.$modal.msgSuccess(this.$t('msg.editSuccess'))
              this.open = false
              this.getList()
            }).finally(() => { this.submitLoading = false })
          } else {
            addNotice(this.form).then(response => {
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
        return delNotice(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess(this.$t('msg.deleteSuccess'))
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/notice/export', {
        ...this.queryParams
      }, `notice_${new Date().getTime()}.xlsx`)
    },
    handleImport() {
      this.upload.title = this.$t('notice.importTitle')
      this.upload.open = true
    },
    importTemplate() {
      this.download('erp/notice/importTemplate', {}, `notice_template_${new Date().getTime()}.xlsx`)
    },
    handleFileUploadProgress() {
      this.upload.isUploading = true
    },
    handleFileSuccess(response) {
      this.upload.open = false
      this.upload.isUploading = false
      this.$refs.upload.clearFiles()
      this.$alert("<div style='overflow:auto;max-height:70vh;padding:10px 20px 0'>" + response.msg + "</div>", this.$t('upload.importResult'), { dangerouslyUseHTMLString: true })
      this.getList()
    },
    submitFileForm() {
      const file = this.$refs.upload.uploadFiles
      if (!file || file.length === 0 || (!file[0].name.toLowerCase().endsWith('.xls') && !file[0].name.toLowerCase().endsWith('.xlsx'))) {
        this.$modal.msgError(this.$t('upload.selectFile'))
        return
      }
      this.$refs.upload.submit()
    }
  }
}
</script>
