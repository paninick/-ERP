<template>
  <div class="app-container">
    <!-- 1. 全局顶部操作与筛选区（两区物理隔离） -->
    <div class="biz-top-bar" style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px;">

      <!-- 左侧极简筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biz-search-form" style="margin-bottom: 0;">
        <el-form-item prop="customerId" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.customerId" :placeholder="$t('bom.selectCustomer')" clearable filterable remote :remote-method="filterCustomer" :loading="customerLoading" style="width: 160px;">
            <el-option v-for="item in customerOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item prop="salesOrderId" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.salesOrderId" :placeholder="$t('bom.salesOrder')" clearable filterable remote :remote-method="filterSalesOrder" :loading="salesOrderLoading" style="width: 160px;">
            <el-option v-for="item in salesOrderOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item prop="progressStatus" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.progressStatus" :placeholder="$t('bom.progressStatus')" clearable style="width: 140px;">
            <el-option v-for="dict in dict.type.erp_progress_status" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">{{ $t('btn.query') }}</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
        </el-form-item>
      </el-form>

      <!-- 右侧固定功能按钮区 -->
      <div class="biz-action-btn-group" style="display: flex; gap: 8px; flex-shrink: 0;">
        <el-button type="primary" size="small" @click="handleAdd" v-hasPermi="['erp:bom:add']">{{ $t('bom.addTitle') }}</el-button>
        <el-button type="default" size="small" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:bom:edit']">{{ $t('btn.edit') }}</el-button>
        <el-button type="danger" plain size="small" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:bom:remove']">{{ $t('btn.delete') }}</el-button>
        <el-button type="default" size="small" @click="handleExport" v-hasPermi="['erp:bom:export']">{{ $t('btn.export') }}</el-button>
        <el-button type="success" plain size="small" icon="el-icon-upload" @click="handleImport" v-hasPermi="['erp:bom:import']">{{ $t('btn.import') }}</el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="margin-left: 8px;"></right-toolbar>
      </div>
    </div>

    <!-- 2. 数据表格区 (引入骨架屏缓解加载焦虑) -->
    <el-skeleton :loading="loading" animated :rows="10">
      <template>
        <!-- 移除边框，双击唤起极速编辑 -->
        <el-table class="biz-table" :data="bomList" @selection-change="handleSelectionChange" @row-dblclick="handleRowDblclick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column :label="$t('bom.sampleNo')" align="center" prop="sampleNo" />
          <el-table-column :label="$t('bom.customer')" align="center" prop="customerName" />
          <el-table-column :label="$t('bom.styleCode')" align="center" prop="styleCode" width="140" />
          <el-table-column :label="$t('bom.bulkOrderNo')" align="center" prop="bulkOrderNo" />
          <el-table-column :label="$t('bom.styleType')" align="center" prop="styleType">
            <template slot-scope="scope">
              <span>{{ (dict.type.erp_sample_style.find(d => d.value === scope.row.styleType) || {}).label || scope.row.styleType }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('bom.progressStatus')" align="center" prop="progressStatus">
            <template slot-scope="scope">
              <!-- 双击状态时内联编辑 -->
              <div v-if="scope.row.isEditing">
                <el-select v-model="scope.row.progressStatus" size="mini" @change="saveInline(scope.row)">
                  <el-option v-for="dict in dict.type.erp_progress_status" :key="dict.value" :label="dict.label" :value="dict.value" />
                </el-select>
              </div>
              <span v-else>{{ (dict.type.erp_progress_status.find(d => d.value === scope.row.progressStatus) || {}).label || scope.row.progressStatus }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('bom.auditStatus')" align="center" prop="auditStatus" width="100">
            <template slot-scope="scope">
              <span :style="{color: scope.row.auditStatus === '1' ? 'var(--app-success-color)' : (scope.row.auditStatus === '2' ? 'var(--app-danger-color)' : 'var(--app-text-tip)')}">
                {{ scope.row.auditStatus === '1' ? ('● ' + $t('bom.auditStatusText.audited')) : scope.row.auditStatus === '2' ? ('● ' + $t('bom.auditStatusText.rejected')) : ('○ ' + $t('bom.auditStatusText.unreviewed')) }}
              </span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('bom.dueDate')" align="center" prop="dueDate" width="120">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('bom.salesName')" align="center" prop="salesName" />

          <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['erp:bom:edit']">{{ $t('btn.detail') }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" style="margin-top: 16px; text-align: right;" />
      </template>
    </el-skeleton>

    <!-- 3. 添加或修改侧边抽屉或轻量弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false" custom-class="biz-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" class="biz-form">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('bom.customer')" prop="customerId" required>
              <el-select v-model="form.customerId" :placeholder="$t('bom.selectCustomer')" clearable filterable remote :remote-method="filterCustomer" :loading="customerLoading" style="width: 100%">
                <el-option v-for="item in customerOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('bom.salesOrder')" prop="salesOrderId">
              <el-select v-model="form.salesOrderId" :placeholder="$t('bom.selectSalesOrder')" clearable filterable remote :remote-method="filterSalesOrder" :loading="salesOrderLoading" style="width: 100%">
                <el-option v-for="item in salesOrderOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('bom.sampleType')" prop="sampleType" required>
              <el-select v-model="form.sampleType" :placeholder="$t('btn.pleaseSelect')" style="width: 100%">
                <el-option v-for="dict in dict.type.erp_sample_type" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('bom.styleType')" prop="styleType" required>
              <el-select v-model="form.styleType" :placeholder="$t('btn.pleaseSelect')" style="width: 100%">
                <el-option v-for="dict in dict.type.erp_sample_style" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('bom.sampleCategoryType')" prop="sampleCategoryType" required>
              <el-select v-model="form.sampleCategoryType" :placeholder="$t('btn.pleaseSelect')" style="width: 100%">
                <el-option v-for="dict in dict.type.erp_sample_category" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('bom.dueDate')" prop="dueDate" required>
              <el-date-picker clearable v-model="form.dueDate" type="date" value-format="yyyy-MM-dd" :placeholder="$t('bom.selectDueDate')" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('bom.bulkOrderNo')" prop="bulkOrderNo">
              <el-input v-model="form.bulkOrderNo" :placeholder="$t('btn.pleaseInput')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('bom.sampleNo')" prop="sampleNo">
              <el-input v-model="form.sampleNo" :placeholder="$t('btn.pleaseInput')" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('bom.auditStatus')" prop="auditStatus">
              <el-select v-model="form.auditStatus" :placeholder="$t('btn.pleaseSelect')" style="width: 100%">
                <el-option v-for="dict in dict.type.erp_sample_audit_status" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('bom.progressStatus')" prop="progressStatus">
              <el-select v-model="form.progressStatus" :placeholder="$t('btn.pleaseSelect')" style="width: 100%">
                <el-option v-for="dict in dict.type.erp_progress_status" :key="dict.value" :label="dict.label" :value="dict.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('btn.pleaseInput')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 样衣 BOM 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading" :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">{{ $t('upload.dragText') }}，或<em>{{ $t('btn.upload') }}</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>{{ $t('upload.allowedExcel') }}（{{ $t('bom.uploadNote') }}）。</span>
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
import { listBom, getBom, delBom, addBom, updateBom } from "@/api/erp/bom"
import { listCustomer } from "@/api/erp/customer"
import { listSales } from "@/api/erp/sales"
import { getToken } from "@/utils/auth"

export default {
  name: "Bom",
  dicts: ['erp_sample_style', 'erp_sample_category', 'erp_sample_audit_status', 'erp_sample_type', 'erp_progress_status'],
  data() {
    return {
      loading: true,
      submitLoading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      bomList: [],
      title: "",
      open: false,
      customerOptions: [],
      customerLoading: false,
      salesOrderOptions: [],
      salesOrderLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        customerId: null,
        salesOrderId: null,
        auditStatus: null,
        progressStatus: null,
      },
      form: {},
      upload: {
        open: false,
        title: "",
        isUploading: false,
        updateSupport: 0,
        headers: { Authorization: "Bearer " + getToken() },
        url: process.env.VUE_APP_BASE_API + "/erp/bom/importData"
      }
    }
  },
  computed: {
    rules() {
      const msg = this.$t('validation.required')
      return {
        customerId: [{ required: true, message: msg, trigger: "change" }],
        sampleType: [{ required: true, message: msg, trigger: "change" }],
        styleType: [{ required: true, message: msg, trigger: "change" }],
        sampleCategoryType: [{ required: true, message: msg, trigger: "change" }],
        dueDate: [{ required: true, message: msg, trigger: "change" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    filterCustomer(query) {
      if (!query) { this.customerOptions = []; return; }
      this.customerLoading = true;
      listCustomer({ pageNum: 1, pageSize: 20, customerName: query }).then(res => {
        this.customerOptions = res.rows.map(r => ({ value: r.id, label: r.customerName }));
        this.customerLoading = false;
      }).catch(() => { this.customerLoading = false });
    },
    filterSalesOrder(query) {
      if (!query) { this.salesOrderOptions = []; return; }
      this.salesOrderLoading = true;
      listSales({ pageNum: 1, pageSize: 20, salesNo: query }).then(res => {
        this.salesOrderOptions = res.rows.map(r => ({ value: r.id, label: r.salesNo }));
        this.salesOrderLoading = false;
      }).catch(() => { this.salesOrderLoading = false });
    },
    getList() {
      this.loading = true;
      listBom(this.queryParams).then(res => {
        this.bomList = res.rows.map(item => ({ ...item, isEditing: false }));
        this.total = res.total;
        this.loading = false;
      });
    },
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    reset() {
      this.form = { id: null, sampleNo: null, sampleType: null, customerId: null, styleType: null, sampleCategoryType: null, dueDate: null };
      this.resetForm("form");
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('bom.addTitle');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getBom(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = this.$t('bom.detailTitle');
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const req = this.form.id != null ? updateBom(this.form) : addBom(this.form);
          req.then(() => {
            this.$message.success(this.$t('msg.operationSuccess'));
            this.open = false;
            this.getList();
          }).finally(() => {
            this.submitLoading = false;
          });
        }
      });
    },
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$confirm(this.$t('bom.destroyConfirm', [ids]), this.$t('msg.deleteWarning'), { type: 'warning' })
        .then(() => delBom(ids))
        .then(() => {
          this.getList();
          this.$message.success(this.$t('msg.deleteSuccess'));
        }).catch(() => {});
    },
    handleExport() {
      this.download('erp/bom/export', { ...this.queryParams }, `bom_${new Date().getTime()}.xlsx`);
    },
    handleImport() {
      this.upload.title = this.$t('bom.importTitle')
      this.upload.open = true
    },
    importTemplate() {
      this.download('erp/bom/importTemplate', {}, `bom_template_${new Date().getTime()}.xlsx`)
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
    },
    // 双击内联编辑
    handleRowDblclick(row, column, event) {
      if (column.property === 'progressStatus') {
        row.isEditing = true;
      }
    },
    // 保存内联
    saveInline(row) {
      row.isEditing = false;
      updateBom(row).then(() => {
        this.$message.success(this.$t('msg.statusUpdated'));
      }).catch(() => {
        row.isEditing = true;
      });
    }
  }
}
</script>

<style scoped>
.biz-table >>> .el-table__row {
  cursor: pointer;
}
.biz-search-form >>> .el-form-item__content {
  line-height: 32px;
}
</style>
