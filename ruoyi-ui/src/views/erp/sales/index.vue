<template>
  <div class="app-container">
    <!-- 1. 全局顶部操作与筛选区（两区物理隔离） -->
    <div class="biz-top-bar" style="display: flex; justify-content: space-between; align-items: flex-end; margin-bottom: 24px;">

      <!-- 左侧极简筛选 -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" class="biz-search-form" style="margin-bottom: 0;">
        <el-form-item prop="salesNo" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.salesNo" :placeholder="$t('sales.searchNo')" clearable @keyup.enter.native="handleQuery" style="width: 200px;" />
        </el-form-item>
        <el-form-item prop="styleCode" style="margin-bottom: 0; margin-right: 16px;">
          <el-input v-model="queryParams.styleCode" :placeholder="$t('sales.searchStyle')" clearable @keyup.enter.native="handleQuery" style="width: 160px;" />
        </el-form-item>
        <el-form-item prop="customerId" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.customerId" :placeholder="$t('sales.selectCustomer')" clearable filterable remote :remote-method="filterCustomer" :loading="customerLoading" style="width: 160px;">
            <el-option v-for="item in customerOptions" :key="item.value" :label="item.label" :value="item.value" />
          </el-select>
        </el-form-item>
        <el-form-item prop="orderStatus" style="margin-bottom: 0; margin-right: 16px;">
          <el-select v-model="queryParams.orderStatus" :placeholder="$t('sales.orderStatus')" clearable style="width: 140px;">
            <el-option v-for="dict in dict.type.erp_order_status" :key="dict.value" :label="dict.label" :value="dict.value" />
          </el-select>
        </el-form-item>
        <el-form-item style="margin-bottom: 0;">
          <el-button type="primary" icon="el-icon-search" size="small" @click="handleQuery">{{ $t('btn.query') }}</el-button>
          <el-button icon="el-icon-refresh" size="small" @click="resetQuery">{{ $t('btn.reset') }}</el-button>
        </el-form-item>
      </el-form>

      <!-- 右侧固定功能按钮区 (统一放置右上角，最高频功能外露) -->
      <div class="biz-action-btn-group" style="display: flex; gap: 8px; flex-shrink: 0;">
        <el-button type="primary" size="small" @click="handleAdd" v-hasPermi="['erp:sales:add']">{{ $t('sales.addTitle') }}</el-button>
        <el-button type="default" size="small" :disabled="single" @click="handleUpdate" v-hasPermi="['erp:sales:edit']">{{ $t('btn.edit') }}</el-button>
        <el-button type="danger" plain size="small" :disabled="multiple" @click="handleDelete" v-hasPermi="['erp:sales:remove']">{{ $t('btn.delete') }}</el-button>
        <el-button type="default" size="small" @click="handleExport" v-hasPermi="['erp:sales:export']">{{ $t('btn.export') }}</el-button>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList" style="margin-left: 8px;"></right-toolbar>
      </div>
    </div>

    <!-- 2. 数据表格区 (引入骨架屏缓解加载焦虑) -->
    <el-skeleton :loading="loading" animated :rows="10">
      <template>
        <!-- 移除边框，双击唤起极速编辑 -->
        <el-table class="biz-table" :data="salesList" @selection-change="handleSelectionChange" @row-dblclick="handleRowDblclick">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column :label="$t('sales.salesNo')" align="center" prop="salesNo" />
          <el-table-column :label="$t('sales.styleCode')" align="center" prop="styleCode" width="140" />
          <el-table-column :label="$t('sales.customer')" align="center" prop="customerName" />
          <el-table-column :label="$t('sales.bulkOrderNo')" align="center" prop="bulkOrderNo" />
          <el-table-column :label="$t('sales.styleCategory')" align="center" prop="styleCategory" />

          <el-table-column :label="$t('sales.orderStatus')" align="center" prop="orderStatus">
            <template slot-scope="scope">
              <!-- 双击状态时内联编辑 (示例实现) -->
              <div v-if="scope.row.isEditing">
                <el-select v-model="scope.row.orderStatus" size="mini" @change="saveInline(scope.row)">
                  <el-option v-for="dict in dict.type.erp_order_status" :key="dict.value" :label="dict.label" :value="dict.value" />
                </el-select>
              </div>
              <span v-else>{{ (dict.type.erp_order_status.find(d => d.value === scope.row.orderStatus) || {}).label || scope.row.orderStatus }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('sales.dueDate')" align="center" prop="dueDate" width="120">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.dueDate, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('sales.jisLabelStatus')" align="center" prop="jisLabelStatus" width="100">
            <template slot-scope="scope">
              <!-- MUJI极简状态反馈，不用刺眼的强对比Tag -->
              <span :style="{color: scope.row.jisLabelStatus === '1' ? 'var(--app-success-color)' : (scope.row.jisLabelStatus === '2' ? 'var(--app-danger-color)' : 'var(--app-text-tip)')}">
                {{ scope.row.jisLabelStatus === '1' ? ('● ' + $t('sales.jisCompliant')) : scope.row.jisLabelStatus === '2' ? ('● ' + $t('sales.jisNonCompliant')) : ('○ ' + $t('sales.jisUnconfirmed')) }}
              </span>
            </template>
          </el-table-column>

          <el-table-column :label="$t('system.operation')" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleUpdate(scope.row)" v-hasPermi="['erp:sales:edit']">{{ $t('btn.detail') }}</el-button>
            </template>
          </el-table-column>
        </el-table>

        <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getList" style="margin-top: 16px; text-align: right;" />
      </template>
    </el-skeleton>

    <!-- 3. 添加或修改侧边抽屉或轻量弹窗 -->
    <!-- 遵循轻量化反馈原则，不再使用巨大模态框霸屏 -->
    <el-dialog :title="title" :visible.sync="open" width="700px" append-to-body :close-on-click-modal="false" custom-class="biz-dialog">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px" class="biz-form">
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('sales.salesType')" prop="salesType" required>
              <el-select v-model="form.salesType" :placeholder="$t('btn.select')" class="w-full" style="width: 100%">
                <el-option v-for="item in salesTypeOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('sales.customer')" prop="customerId" required>
              <el-select v-model="form.customerId" :placeholder="$t('sales.selectCustomer')" clearable filterable remote :remote-method="filterCustomer" :loading="customerLoading" style="width: 100%">
                <el-option v-for="item in customerOptions" :key="item.value" :label="item.label" :value="item.value" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="24">
          <el-col :span="12">
            <el-form-item :label="$t('sales.salesNo')" prop="salesNo">
              <el-input v-model="form.salesNo" :placeholder="$t('sales.salesNameAuto')" disabled />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('sales.styleCode')" prop="styleCode">
              <el-input v-model="form.styleCode" :placeholder="$t('sales.styleCodeAuto')" />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item :label="$t('sales.salesDate')" prop="salesDate" required>
              <el-date-picker clearable v-model="form.salesDate" type="date" value-format="yyyy-MM-dd" :placeholder="$t('sales.selectDate')" style="width: 100%" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <!-- 按钮 Loading 反馈，非遮罩 -->
        <el-button type="primary" :loading="submitLoading" @click="submitForm">{{ $t('btn.confirm') }}</el-button>
        <el-button @click="cancel">{{ $t('btn.cancel') }}</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listSales, getSales, delSales, addSales, updateSales } from "@/api/erp/sales"
import { listCustomer } from "@/api/erp/customer"
import { getToken } from "@/utils/auth"

export default {
  name: "SalesOrder",
  dicts: ['erp_order_status', 'erp_audit_status'],
  data() {
    return {
      loading: true,
      submitLoading: false, // 按钮内联 Loading
      ids: [],
      single: true,
      multiple: true,
      showSearch: true,
      total: 0,
      salesList: [],
      title: "",
      open: false,
      customerOptions: [],
      customerLoading: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        salesType: null,
        customerId: null,
        salesNo: null,
        styleCode: null,
        orderStatus: null
      },
      form: {},
      rules: {
        salesType: [{ required: true, message: () => this.$t('validation.required'), trigger: "change" }],
        customerId: [{ required: true, message: () => this.$t('validation.required'), trigger: "change" }],
        salesDate: [{ required: true, message: () => this.$t('validation.required'), trigger: "change" }]
      }
    }
  },
  computed: {
    salesTypeOptions() {
      return [
        { value: 'sample', label: this.$t('sales.sample') },
        { value: 'bulk', label: this.$t('sales.bulk') }
      ]
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
    getList() {
      this.loading = true;
      listSales(this.queryParams).then(res => {
        this.salesList = res.rows.map(item => ({ ...item, isEditing: false }));
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
      this.form = { id: null, salesType: null, salesNo: null, styleCode: null, salesDate: null, customerId: null };
      this.resetForm("form");
    },
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = this.$t('sales.addTitle');
    },
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getSales(id).then(res => {
        this.form = res.data;
        this.open = true;
        this.title = this.$t('sales.editTitle');
      });
    },
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.submitLoading = true;
          const req = this.form.id != null ? updateSales(this.form) : addSales(this.form);
          req.then(() => {
            this.$message.success(this.$t('msg.operationSuccess')); // 使用极简 Toast 替代全屏确认
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
      // 高危操作保留确认弹窗
      this.$confirm(this.$t('sales.destroyConfirm', [ids]), this.$t('msg.deleteWarning'), {
        type: 'warning'
      }).then(() => delSales(ids)).then(() => {
        this.getList();
        this.$message.success(this.$t('msg.dataDestroyed'));
      }).catch(() => {});
    },
    handleExport() {
      this.download('erp/sales/order/export', { ...this.queryParams }, `sales_${new Date().getTime()}.xlsx`);
    },
    // 双击单元格极速编辑交互
    handleRowDblclick(row, column, event) {
      if (column.property === 'orderStatus') {
        row.isEditing = true;
      }
    },
    // 极速保存内联更改
    saveInline(row) {
      row.isEditing = false;
      updateSales(row).then(() => {
        this.$message.success(this.$t('msg.statusUpdated'));
      }).catch(() => {
        row.isEditing = true; // 回退
      });
    }
  }
}
</script>

<style scoped>
/* 局部样式微调，配合全局引擎 */
.biz-table >>> .el-table__row {
  cursor: pointer;
}
.biz-search-form >>> .el-form-item__content {
  line-height: 32px;
}
</style>
