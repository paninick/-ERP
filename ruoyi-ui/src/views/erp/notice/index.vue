<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="审批状态" prop="auditStatus">
        <el-select v-model="queryParams.auditStatus" placeholder="请选择审批状态" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_audit_status"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="客户名称" prop="customerName">
        <el-input
          v-model="queryParams.customerName"
          placeholder="请输入客户名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="打样类型" prop="sampleType">
        <el-select v-model="queryParams.sampleType" placeholder="请选择打样类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="样品款式" prop="styleType">
        <el-select v-model="queryParams.styleType" placeholder="请选择样品款式" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_style"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="样品类型" prop="sampleCategoryType">
        <el-select v-model="queryParams.sampleCategoryType" placeholder="请选择样品类型" clearable>
          <el-option
            v-for="dict in dict.type.erp_sample_category"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="打样编号" prop="sampleNo">
        <el-input
          v-model="queryParams.sampleNo"
          placeholder="请输入打样编号"
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
          v-hasPermi="['erp:notice:add']"
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
          v-hasPermi="['erp:notice:edit']"
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
          v-hasPermi="['erp:notice:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"
          v-hasPermi="['erp:notice:export']"
        >导出</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-upload"
          size="mini"
          @click="handleImport"
          v-hasPermi="['erp:notice:import']"
        >导入</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="noticeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="审批状态" align="center" prop="auditStatus">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_audit_status" :value="scope.row.auditStatus"/>
        </template>
      </el-table-column>
      <el-table-column label="客户名称" align="center" prop="customerName" />
      <el-table-column label="打样类型" align="center" prop="sampleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_type" :value="scope.row.sampleType"/>
        </template>
      </el-table-column>
      <el-table-column label="样品款式" align="center" prop="styleType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_style" :value="scope.row.styleType"/>
        </template>
      </el-table-column>
      <el-table-column label="样品类型" align="center" prop="sampleCategoryType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.erp_sample_category" :value="scope.row.sampleCategoryType"/>
        </template>
      </el-table-column>
      <el-table-column label="打样款号" align="center" prop="styleCode" />
      <el-table-column label="大货款号" align="center" prop="bulkOrderNo" />
      <el-table-column label="要求交期" align="center" prop="dueDate" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="业务员" align="center" prop="salesName" />
      <el-table-column label="申请时间" align="center" prop="createTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审批人" align="center" prop="auditByNickName" />
      <el-table-column label="审批时间" align="center" prop="auditTime" width="120">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.auditTime, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="打样编号" align="center" prop="sampleNo" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['erp:notice:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['erp:notice:remove']"
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

    <!-- 添加或修改打样通知对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body :close-on-click-modal="false" :close-on-press-escape="false">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="打样类型" prop="sampleType">
          <el-select v-model="form.sampleType" placeholder="请选择打样类型">
            <el-option
              v-for="dict in dict.type.erp_sample_type"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="客户名称" prop="customerName">
          <el-input v-model="form.customerName" placeholder="请输入客户名称" />
        </el-form-item>
        <el-form-item label="样品款式" prop="styleType">
          <el-select v-model="form.styleType" placeholder="请选择样品款式">
            <el-option
              v-for="dict in dict.type.erp_sample_style"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="样品类型" prop="sampleCategoryType">
          <el-select v-model="form.sampleCategoryType" placeholder="请选择样品类型">
            <el-option
              v-for="dict in dict.type.erp_sample_category"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="打样编号" prop="sampleNo">
          <el-input v-model="form.sampleNo" placeholder="保存时自动生成（SN-yyyyMMdd-序号）" disabled />
        </el-form-item>
        <el-form-item label="大货款号" prop="bulkOrderNo">
          <el-input v-model="form.bulkOrderNo" placeholder="请输入大货款号" />
        </el-form-item>
        <el-form-item label="要求交期" prop="dueDate">
          <el-date-picker clearable
            v-model="form.dueDate"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="请选择要求交期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="业务员" prop="salesName">
          <el-input v-model="form.salesName" placeholder="请输入业务员" />
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
        <el-form-item label="审批人" prop="auditByNickName">
          <el-input v-model="form.auditByNickName" placeholder="请输入审批人" />
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

    <!-- 打样通知导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading" :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入 xls、xlsx 格式文件。</span>
          <el-link type="primary" :underline="false" style="font-size:12px;vertical-align:baseline" @click="importTemplate">下载模板</el-link>
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
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
      // 表单校验
      rules: {
      },
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
      this.title = "添加打样通知"
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset()
      const id = row.id || this.ids
      getNotice(id).then(response => {
        this.form = response.data
        this.open = true
        this.title = "修改打样通知"
      })
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateNotice(this.form).then(response => {
              this.$modal.msgSuccess("修改成功")
              this.open = false
              this.getList()
            })
          } else {
            addNotice(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除打样通知编号为"' + ids + '"的数据项？').then(function() {
        return delNotice(ids)
      }).then(() => {
        this.getList()
        this.$modal.msgSuccess("删除成功")
      }).catch(() => {})
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('erp/notice/export', {
        ...this.queryParams
      }, `notice_${new Date().getTime()}.xlsx`)
    },
    handleImport() {
      this.upload.title = "打样通知导入"
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
      this.$alert("<div style='overflow:auto;max-height:70vh;padding:10px 20px 0'>" + response.msg + "</div>", "导入结果", { dangerouslyUseHTMLString: true })
      this.getList()
    },
    submitFileForm() {
      const file = this.$refs.upload.uploadFiles
      if (!file || file.length === 0 || (!file[0].name.toLowerCase().endsWith('.xls') && !file[0].name.toLowerCase().endsWith('.xlsx'))) {
        this.$modal.msgError('请选择后缀为 .xls 或 .xlsx 的文件。')
        return
      }
      this.$refs.upload.submit()
    }
  }
}
</script>
