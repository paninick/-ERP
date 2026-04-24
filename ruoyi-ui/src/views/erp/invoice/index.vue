<template>
  <div class="app-container">
    <!-- 1. 全局顶部操作与筛选区 -->
    <div class="biz-top-bar" style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px;">

      <!-- 左侧极简筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biz-search-form" style="margin-bottom: 0;">
        <el-form-item prop="corpType" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.corpType" :placeholder="$t('invoice.corpType')" clearable style="width: 140px;">
            <el-option v-for="dict in dict.type.erp_corp_type" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item prop="socialNumber" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.socialNumber" :placeholder="$t('invoice.socialNumber')" clearable @keyup.enter.native="handleQuery" style="width: 160px;" />
        </el-form-item>
        <el-form-item prop="corpName" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.corpName" :placeholder="$t('invoice.corpName')" clearable @keyup.enter.native="handleQuery" style="width: 160px;" />
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">{{ $t('btn.query') }}</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
        </el-form-item>
      </el-form>

      <!-- 右侧固定功能按钮区 -->
      <div class="biz-action-btn-group" style="display: flex; gap: 8px; flex-shrink: 0;">
        <el-button type="primary" size="small" @click="handleAdd" v-hasPermi="['erp:invoice:add']">{{ $t('invoice.addTitle') }}</el-button>
        <el-button type="default" size="small" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:invoice:edit']">{{ $t('btn.edit') }}</el-button>
        <el-button type="danger" plain size="small" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:invoice:remove']">{{ $t('btn.delete') }}</el-button>
        <el-button type="default" size="small" @click="handleExport" v-hasPermi="['erp:invoice:export']">{{ $t('btn.export') }}</el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="margin-left: 8px;"></right-toolbar>
      </div>
    </div>

    <!-- 2. 数据表格区 (骨架屏包装) -->
    <el-skeleton :loading="loading" animated :rows="10">
      <template>
        <el-table class="biz-table" :data="invoiceList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column :label="$t('invoice.corpName')" align="center" prop="corpName" width="220" />
          <el-table-column :label="$t('invoice.corpType')" align="center" prop="corpType" width="100">
            <template slot-scope="scope">
              <span style="color: var(--app-text-secondary)">
                {{ (dict.type.erp_corp_type.find(d => d.value === scope.row.corpType) || {}).label || scope.row.corpType }}
              </span>
            </template>
          </el-table-column>
          <el-table-column :label="$t('invoice.socialNumber')" align="center" prop="socialNumber" width="180" />
          <el-table-column :label="$t('invoice.corpBank')" align="center" prop="corpBank" width="150" />
          <el-table-column :label="$t('invoice.corpBankNo')" align="center" prop="corpBankNo" width="180" />
          <el-table-column :label="$t('invoice.corpPhone')" align="center" prop="corpPhone" width="140" />
          <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['erp:invoice:edit']">{{ $t('btn.detail') }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" style="margin-top: 16px; text-align: right;" />
      </template>
    </el-skeleton>

    <!-- 3. 添加或修改弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false" custom-class="biz-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" class="biz-form">
        <el-form-item :label="$t('invoice.corpType')" prop="corpType" required>
          <el-select v-model="form.corpType" :placeholder="$t('invoice.selectCorpType')" style="width: 100%">
            <el-option v-for="dict in dict.type.erp_corp_type" :key="dict.value" :label="dict.label" :value="dict.value"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item :label="$t('invoice.corpName')" prop="corpName" required>
          <el-input v-model="form.corpName" :placeholder="$t('invoice.enterCorpName')" />
        </el-form-item>
        <el-form-item :label="$t('invoice.socialNumber')" prop="socialNumber" required>
          <el-input v-model="form.socialNumber" :placeholder="$t('invoice.enterSocialNumber')" />
        </el-form-item>

        <el-divider content-position="left">{{ $t('invoice.accountInfo') }}</el-divider>

        <el-form-item :label="$t('invoice.corpBank')" prop="corpBank">
          <el-input v-model="form.corpBank" :placeholder="$t('invoice.enterCorpBank')" />
        </el-form-item>
        <el-form-item :label="$t('invoice.corpBankNo')" prop="corpBankNo">
          <el-input v-model="form.corpBankNo" :placeholder="$t('invoice.enterCorpBankNo')" />
        </el-form-item>
        <el-form-item :label="$t('invoice.corpAddress')" prop="corpAddress">
          <el-input v-model="form.corpAddress" :placeholder="$t('invoice.enterCorpAddress')" />
        </el-form-item>
        <el-form-item :label="$t('invoice.corpPhone')" prop="corpPhone">
          <el-input v-model="form.corpPhone" :placeholder="$t('invoice.enterCorpPhone')" />
        </el-form-item>
        <el-form-item :label="$t('invoice.remark')" prop="remark">
          <el-input v-model="form.remark" type="textarea" :placeholder="$t('invoice.enterRemark')" />
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
import { listInvoice, getInvoice, delInvoice, addInvoice, updateInvoice } from "@/api/erp/invoice"

export default {
  name: "Invoice",
  dicts: ['erp_corp_type'],
  data() {
    return {
      loading: true,
      submitLoading: false,
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      invoiceList: [],
      title: "",
      open: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        corpType: null,
        socialNumber: null,
        corpName: null,
      },
      form: {}
    }
  },
  computed: {
    rules() {
      return {
        corpType: [{ required: true, message: () => this.$t('validation.required'), trigger: "change" }],
        corpName: [{ required: true, message: () => this.$t('validation.required'), trigger: "blur" }],
        socialNumber: [{ required: true, message: () => this.$t('validation.required'), trigger: "blur" }]
      }
    }
  },
  created() {
    this.getList()
  },
  methods: {
    getList() {
      this.loading = true;
      listInvoice(this.queryParams).then(res => {
        this.invoiceList = res.rows;
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
      this.form = { id: null, corpType: null, corpId: null, socialNumber: null, corpName: null, corpAddress: null, corpPhone: null, corpBank: null, corpBankNo: null, remark: null };
      this.resetForm("form");
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('invoice.addTitle');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getInvoice(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = this.$t('invoice.editTitle');
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const req = this.form.id != null ? updateInvoice(this.form) : addInvoice(this.form);
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
      this.$confirm(this.$t('invoice.destroyConfirm', [ids]), this.$t('invoice.destroyWarning'), { type: 'warning' })
        .then(() => delInvoice(ids))
        .then(() => {
          this.getList();
          this.$message.success(this.$t('invoice.destroySuccess'));
        }).catch(() => {});
    },
    handleExport() {
      this.download('erp/invoice/export', { ...this.queryParams }, `invoice_${new Date().getTime()}.xlsx`);
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
