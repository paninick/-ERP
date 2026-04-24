<template>
  <div class="app-container">
    <!-- 1. 全局顶部操作与筛选区 -->
    <div class="biz-top-bar" style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px;">

      <!-- 左侧极简筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biz-search-form" style="margin-bottom: 0;">
        <el-form-item prop="purchaseId" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.purchaseId" :placeholder="$t('stockin.purchasePlaceholder')" clearable filterable remote :remote-method="filterPurchase" :loading="purchaseLoading" style="width: 160px;">
            <el-option v-for="item in purchaseOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item prop="inType" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.inType" :placeholder="$t('stockin.inTypePlaceholder')" clearable style="width: 120px;">
            <el-option :label="$t('stockin.fabric')" :value="1" />
            <el-option :label="$t('stockin.yarn')" :value="2" />
            <el-option :label="$t('stockin.auxiliary')" :value="3" />
          </el-select>
        </el-form-item>
        <el-form-item prop="bulkOrderNo" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.bulkOrderNo" :placeholder="$t('stockin.bulkOrderNo')" clearable @keyup.enter.native="handleQuery" style="width: 140px;" />
        </el-form-item>
        <el-form-item prop="confirmStatus" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.confirmStatus" :placeholder="$t('stockin.confirmStatusPlaceholder')" clearable style="width: 120px;">
            <el-option v-for="dict in dict.type.erp_confirm_status" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">{{ $t('btn.query') }}</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
        </el-form-item>
      </el-form>

      <!-- 右侧固定功能按钮区 -->
      <div class="biz-action-btn-group" style="display: flex; gap: 8px; flex-shrink: 0;">
        <el-button type="primary" size="small" @click="handleAdd" v-hasPermi="['erp:stockin:add']">{{ $t('stockin.addTitle') }}</el-button>
        <el-button type="default" size="small" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:stockin:edit']">{{ $t('btn.edit') }}</el-button>
        <el-button type="danger" plain size="small" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:stockin:remove']">{{ $t('btn.delete') }}</el-button>
        <el-button type="default" size="small" @click="handleExport" v-hasPermi="['erp:stockin:export']">{{ $t('btn.export') }}</el-button>
        <el-button type="success" plain size="small" icon="el-icon-upload" @click="handleImport" v-hasPermi="['erp:stockin:import']">{{ $t('btn.import') }}</el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="margin-left: 8px;"></right-toolbar>
      </div>
    </div>

    <!-- 2. 数据表格区 (骨架屏包装) -->
    <el-skeleton :loading="loading" animated :rows="10">
      <template>
        <!-- 双击表格行进行快速状态确认 -->
        <el-table class="biz-table" :data="stockinList" @selection-change="handleSelectionChange" @row-dblclick="handleRowDblclick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column :label="$t('stockin.sn')" align="center" prop="sn" width="140" />
          <el-table-column :label="$t('stockin.purchaseSn')" align="center" prop="purchaseSn" width="140" />
          <el-table-column :label="$t('stockin.bulkOrderNo')" align="center" prop="bulkOrderNo" width="160" :show-overflow-tooltip="true" />
          <el-table-column :label="$t('stockin.type')" align="center" prop="inType" width="80">
            <template slot-scope="scope">
              <span>{{ getInTypeLabel(scope.row.inType) }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('stockin.confirmStatus')" align="center" prop="confirmStatus" width="120">
            <template slot-scope="scope">
              <!-- 极简圆点指示器 -->
              <span :style="{color: scope.row.confirmStatus === '1' ? 'var(--app-success-color)' : 'var(--app-warning-color)', cursor: scope.row.confirmStatus === '0' ? 'pointer' : 'default'}" :title="$t('stockin.dblClickConfirm')">
                {{ scope.row.confirmStatus === '1' ? '● ' + $t('stockin.confirmed') : '○ ' + $t('stockin.pending') }}
              </span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('stockin.confirmBy')" align="center" prop="confirmBy" width="100" />

          <el-table-column :label="$t('stockin.inDate')" align="center" prop="inDate" width="120">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.inDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width" width="150">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['erp:stockin:edit']">{{ $t('btn.detail') }}</el-button>
              <el-button v-if="scope.row.confirmStatus === '0'" size="mini" type="text" @click="handleConfirm(scope.row)" v-hasPermi="['erp:stockin:edit']">{{ $t('stockin.confirmBtn') }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" style="margin-top: 16px; text-align: right;" />
      </template>
    </el-skeleton>

    <!-- 3. 添加或修改侧边抽屉或轻量弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false" custom-class="biz-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" class="biz-form">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('stockin.sn')" prop="sn">
              <el-input v-model="form.sn" :placeholder="$t('stockin.snAuto')" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('stockin.purchaseSn')" prop="purchaseId">
              <el-select v-model="form.purchaseId" :placeholder="$t('stockin.searchPurchase')" clearable filterable remote :remote-method="filterPurchase" :loading="purchaseLoading" style="width: 100%">
                <el-option v-for="item in purchaseOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('stockin.type')" prop="inType" required>
              <el-select v-model="form.inType" :placeholder="$t('stockin.selectType')" style="width: 100%">
                <el-option :label="$t('stockin.fabric')" :value="1" />
                <el-option :label="$t('stockin.yarn')" :value="2" />
                <el-option :label="$t('stockin.auxiliary')" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('stockin.inDate')" prop="inDate" required>
              <el-date-picker clearable v-model="form.inDate" type="date" value-format="yyyy-MM-dd" :placeholder="$t('stockin.selectDate')" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('stockin.bulkOrderNo')" prop="bulkOrderNo">
              <el-input v-model="form.bulkOrderNo" :placeholder="$t('stockin.enterBulkNo')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('stockin.totalPrice')" prop="totalPrice">
              <el-input-number v-model="form.totalPrice" :precision="2" :min="0" placeholder="0.00" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('stockin.chargeUser')" prop="chargeUserId">
          <el-select v-model="form.chargeUserId" :placeholder="$t('stockin.selectChargeUser')" clearable filterable remote :remote-method="filterUser" :loading="userLoading" style="width: 100%">
            <el-option v-for="item in userOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('stockin.description')" prop="inDescription">
          <el-input v-model="form.inDescription" type="textarea" :placeholder="$t('stockin.descriptionPlaceholder')" />
        </el-form-item>
        <el-form-item :label="$t('system.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('system.remark')" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>

    <!-- 入库单导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload ref="upload" :limit="1" accept=".xlsx, .xls" :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
        :disabled="upload.isUploading" :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess" :auto-upload="false" drag>
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">{{ $t('upload.dragText') }}</div>
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
import { listStockin, getStockin, delStockin, addStockin, updateStockin } from "@/api/erp/stockin"
import { listPurchase } from "@/api/erp/purchase"
import { listUser } from "@/api/system/user"
import { getToken } from "@/utils/auth"

export default {
  name: "StockIn",
  dicts: ['erp_confirm_status'],
  data() {
    return {
      loading: true,
      submitLoading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      stockinList: [],
      title: "",
      open: false,
      purchaseOptions: [],
      purchaseLoading: false,
      userOptions: [],
      userLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        purchaseId: null,
        sn: null,
        bulkOrderNo: null,
        inDate: null,
        inType: null,
        confirmStatus: null
      },
      form: {},
      upload: {
        open: false,
        title: "",
        isUploading: false,
        updateSupport: 0,
        headers: { Authorization: "Bearer " + getToken() },
        url: process.env.VUE_APP_BASE_API + "/erp/stockIn/importData"
      }
    }
  },
  computed: {
    rules() {
      return {
        inDate: [{ required: true, message: this.$t('validation.required'), trigger: "change" }],
        inType: [{ required: true, message: this.$t('validation.required'), trigger: "change" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    filterPurchase(query) {
      if (!query) { this.purchaseOptions = []; return; }
      this.purchaseLoading = true;
      listPurchase({ pageNum: 1, pageSize: 20, sn: query }).then(res => {
        this.purchaseOptions = res.rows.map(r => ({ value: r.id, label: r.sn }));
        this.purchaseLoading = false;
      }).catch(() => { this.purchaseLoading = false });
    },
    filterUser(query) {
      if (!query) { this.userOptions = []; return; }
      this.userLoading = true;
      listUser({ pageNum: 1, pageSize: 50 }).then(res => {
        const users = (res.rows || []).filter(u =>
          (u.nickName && u.nickName.includes(query)) ||
          (u.userName && u.userName.includes(query))
        );
        this.userOptions = users.map(u => ({ value: u.userId, label: u.nickName + '(' + u.userName + ')' }));
      }).finally(() => {
        this.userLoading = false;
      });
    },
    getInTypeLabel(value) {
      const typeMap = {
        '1': this.$t('stockin.fabric'),
        '2': this.$t('stockin.yarn'),
        '3': this.$t('stockin.auxiliary')
      }
      return typeMap[value] || value
    },
    getList() {
      this.loading = true;
      listStockin(this.queryParams).then(res => {
        this.stockinList = res.rows;
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
      this.form = { id: null, sn: null, inDate: null, inType: null, chargeUserId: null, totalPrice: null, confirmStatus: null, purchaseId: null, bulkOrderNo: null };
      this.resetForm("form");
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('stockin.addTitle');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getStockin(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = this.$t('stockin.editTitle');
      });
    },
    // 双击快速确认交互
    handleRowDblclick(row, column, event) {
      if (column.property === 'confirmStatus' && row.confirmStatus === '0') {
        this.handleConfirm(row);
      }
    },
    handleConfirm(row) {
      this.$confirm(this.$t('stockin.confirmMsg', [row.sn]), this.$t('stockin.quickConfirm'), { type: 'warning' }).then(() => {
        row.confirmStatus = '1';
        updateStockin(row).then(() => {
          this.$message.success(this.$t('stockin.confirmedMsg'));
          this.getList();
        });
      }).catch(() => {});
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const req = this.form.id != null ? updateStockin(this.form) : addStockin(this.form);
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
      this.$confirm(this.$t('stockin.deleteConfirm', [ids]), this.$t('msg.deleteWarning'), { type: 'warning' })
        .then(() => delStockin(ids))
        .then(() => {
          this.getList();
          this.$message.success(this.$t('msg.deleteSuccess'));
        }).catch(() => {});
    },
    handleExport() {
      this.download('erp/stockIn/export', { ...this.queryParams }, `stock_in_${new Date().getTime()}.xlsx`);
    },
    handleImport() {
      this.upload.title = this.$t('stockin.importTitle')
      this.upload.open = true
    },
    importTemplate() {
      this.download('erp/stockIn/importTemplate', {}, `stock_in_template_${new Date().getTime()}.xlsx`)
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

<style scoped>
.biz-table >>> .el-table__row {
  cursor: pointer;
}
.biz-search-form >>> .el-form-item__content {
  line-height: 32px;
}
</style>
