<template>
  <div class="app-container">
    <!-- 1. 全局顶部操作与筛选区 -->
    <div class="biz-top-bar" style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px;">

      <!-- 左侧极简筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biz-search-form" style="margin-bottom: 0;">
        <el-form-item prop="purchaseId" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.purchaseId" :placeholder="$t('stockout.purchasePlaceholder')" clearable filterable remote :remote-method="filterPurchase" :loading="purchaseLoading" style="width: 160px;">
            <el-option v-for="item in purchaseOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item prop="bulkOrderNo" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.bulkOrderNo" :placeholder="$t('stockout.bulkOrderNo')" clearable @keyup.enter.native="handleQuery" style="width: 140px;" />
        </el-form-item>
        <el-form-item prop="sn" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.sn" :placeholder="$t('stockout.sn')" clearable @keyup.enter.native="handleQuery" style="width: 140px;" />
        </el-form-item>
        <el-form-item prop="confirmStatus" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.confirmStatus" :placeholder="$t('stockout.confirmStatusPlaceholder')" clearable style="width: 120px;">
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
        <el-button type="primary" size="small" @click="handleAdd" v-hasPermi="['erp:stockout:add']">{{ $t('stockout.addTitle') }}</el-button>
        <el-button type="default" size="small" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:stockout:edit']">{{ $t('btn.edit') }}</el-button>
        <el-button type="danger" plain size="small" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:stockout:remove']">{{ $t('btn.delete') }}</el-button>
        <el-button type="default" size="small" @click="handleExport" v-hasPermi="['erp:stockout:export']">{{ $t('btn.export') }}</el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="margin-left: 8px;"></right-toolbar>
      </div>
    </div>

    <!-- 2. 数据表格区 (骨架屏包装) -->
    <el-skeleton :loading="loading" animated :rows="10">
      <template>
        <!-- 双击表格行进行快速状态确认 -->
        <el-table class="biz-table" :data="stockoutList" @selection-change="handleSelectionChange" @row-dblclick="handleRowDblclick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column :label="$t('stockout.sn')" align="center" prop="sn" width="140" />
          <el-table-column :label="$t('stockout.bulkOrderNo')" align="center" prop="bulkOrderNo" width="160" :show-overflow-tooltip="true" />
          <el-table-column :label="$t('stockout.type')" align="center" prop="outType" width="80">
            <template slot-scope="scope">
              <span>{{ getOutTypeLabel(scope.row.outType) }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('stockout.confirmStatus')" align="center" prop="confirmStatus" width="120">
            <template slot-scope="scope">
              <!-- 极简圆点指示器 -->
              <span :style="{color: scope.row.confirmStatus === '1' ? 'var(--app-success-color)' : 'var(--app-warning-color)', cursor: scope.row.confirmStatus === '0' ? 'pointer' : 'default'}" :title="$t('stockout.dblClickConfirm')">
                {{ scope.row.confirmStatus === '1' ? '● ' + $t('stockout.confirmed') : '○ ' + $t('stockout.pending') }}
              </span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('stockout.applicant')" align="center" prop="applicant" width="100" />

          <el-table-column :label="$t('stockout.applyDate')" align="center" prop="applyDate" width="120">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.applyDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width" width="150">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['erp:stockout:edit']">{{ $t('btn.detail') }}</el-button>
              <el-button v-if="scope.row.confirmStatus === '0'" size="mini" type="text" @click="handleConfirm(scope.row)" v-hasPermi="['erp:stockout:edit']">{{ $t('stockout.confirmBtn') }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" style="margin-top: 16px; text-align: right;" />
      </template>
    </el-skeleton>

    <!-- 3. 添加或修改轻量弹窗 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body :close-on-click-modal="false" custom-class="biz-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="100px" class="biz-form">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('stockout.sn')" prop="sn">
              <el-input v-model="form.sn" :placeholder="$t('stockout.snAuto')" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('stockout.purchaseSn')" prop="purchaseId">
              <el-select v-model="form.purchaseId" :placeholder="$t('stockout.searchPurchase')" clearable filterable remote :remote-method="filterPurchase" :loading="purchaseLoading" style="width: 100%">
                <el-option v-for="item in purchaseOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('stockout.type')" prop="outType" required>
              <el-select v-model="form.outType" :placeholder="$t('stockout.selectType')" style="width: 100%">
                <el-option :label="$t('stockout.fabric')" :value="1" />
                <el-option :label="$t('stockout.yarn')" :value="2" />
                <el-option :label="$t('stockout.auxiliary')" :value="3" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('stockout.applyDate')" prop="applyDate" required>
              <el-date-picker clearable v-model="form.applyDate" type="date" value-format="yyyy-MM-dd" :placeholder="$t('stockout.selectDate')" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('stockout.bulkOrderNo')" prop="bulkOrderNo">
              <el-input v-model="form.bulkOrderNo" :placeholder="$t('stockout.enterBulkNo')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('stockout.chargeUser')" prop="chargeUserId">
              <el-select v-model="form.chargeUserId" :placeholder="$t('stockout.selectChargeUser')" clearable filterable remote :remote-method="filterUser" :loading="userLoading" style="width: 100%">
                <el-option v-for="item in userOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-form-item :label="$t('stockout.description')" prop="outDescription">
          <el-input v-model="form.outDescription" type="textarea" :placeholder="$t('stockout.descriptionPlaceholder')" />
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
  </div>
</template>

<script>
import { listStockout, getStockout, delStockout, addStockout, updateStockout } from "@/api/erp/stockout"
import { listPurchase } from "@/api/erp/purchase"
import { listUser } from "@/api/system/user"

export default {
  name: "StockOut",
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
      stockoutList: [],
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
        confirmStatus: null
      },
      form: {}
    }
  },
  computed: {
    rules() {
      return {
        outType: [{ required: true, message: this.$t('validation.required'), trigger: "change" }],
        applyDate: [{ required: true, message: this.$t('validation.required'), trigger: "change" }]
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
    getOutTypeLabel(value) {
      const typeMap = {
        '1': this.$t('stockout.fabric'),
        '2': this.$t('stockout.yarn'),
        '3': this.$t('stockout.auxiliary')
      }
      return typeMap[value] || value
    },
    getList() {
      this.loading = true;
      listStockout(this.queryParams).then(res => {
        this.stockoutList = res.rows;
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
      this.form = { id: null, sn: null, applyDate: null, outType: null, chargeUserId: null, confirmStatus: null, purchaseId: null, bulkOrderNo: null };
      this.resetForm("form");
    },
    cancel() {
      this.open = false;
      this.reset();
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('stockout.addTitle');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids[0];
      getStockout(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = this.$t('stockout.editTitle');
      });
    },
    // 双击快速确认交互
    handleRowDblclick(row, column, event) {
      if (column.property === 'confirmStatus' && row.confirmStatus === '0') {
        this.handleConfirm(row);
      }
    },
    handleConfirm(row) {
      this.$confirm(this.$t('stockout.confirmMsg', [row.sn]), this.$t('stockout.quickConfirm'), { type: 'warning' }).then(() => {
        row.confirmStatus = '1';
        updateStockout(row).then(() => {
          this.$message.success(this.$t('stockout.confirmedMsg'));
          this.getList();
        });
      }).catch(() => {});
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const req = this.form.id != null ? updateStockout(this.form) : addStockout(this.form);
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
      this.$confirm(this.$t('stockout.deleteConfirm', [ids]), this.$t('msg.deleteWarning'), { type: 'warning' })
        .then(() => delStockout(ids))
        .then(() => {
          this.getList();
          this.$message.success(this.$t('msg.deleteSuccess'));
        }).catch(() => {});
    },
    handleExport() {
      this.download('erp/stockOut/export', { ...this.queryParams }, `stock_out_${new Date().getTime()}.xlsx`);
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
