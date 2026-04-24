<template>
  <div class="invoice-board">
    <!-- 统计带 -->
    <div class="stat-strip">
      <div class="stat-card">
        <div class="stat-value">
          <count-to :startVal="0" :endVal="boardStats.invoicedAmount || 0" :duration="1400" prefix="¥" separator="," />
        </div>
        <div class="stat-label">{{ $t('invoiceBoard.invoicedAmount') }}</div>
      </div>
      <div class="stat-card">
        <div class="stat-value" style="color: var(--app-warning-color)">
          <count-to :startVal="0" :endVal="boardStats.pendingAmount || 0" :duration="1400" prefix="¥" separator="," />
        </div>
        <div class="stat-label">{{ $t('invoiceBoard.pendingAmount') }}</div>
      </div>
      <div class="stat-card">
        <div class="stat-value" style="color: var(--app-success-color)">
          <count-to :startVal="0" :endVal="boardStats.reconcileRate || 0" :duration="1600" :decimals="1" suffix="%" />
        </div>
        <div class="stat-label">{{ $t('invoiceBoard.reconcileRate') }}</div>
      </div>
    </div>

    <!-- 筛选栏 -->
    <div class="filter-bar">
      <el-date-picker v-model="dateRange" type="daterange" range-separator="—"
        :start-placeholder="$t('invoiceBoard.startDate')" :end-placeholder="$t('invoiceBoard.endDate')" value-format="yyyy-MM-dd"
        size="small" style="width: 260px" @change="loadCards" />
      <el-select v-model="statusFilter" :placeholder="$t('invoiceBoard.invoiceStatus')" clearable size="small"
        style="width: 140px; margin-left: 12px" @change="loadCards">
        <el-option :label="$t('invoiceBoard.statusVerified')" value="verified" />
        <el-option :label="$t('invoiceBoard.statusPartial')" value="partial" />
        <el-option :label="$t('invoiceBoard.statusPending')" value="pending" />
        <el-option :label="$t('invoiceBoard.statusReversed')" value="reversed" />
      </el-select>
      <el-input v-model="customerSearch" :placeholder="$t('invoiceBoard.searchCustomer')" clearable size="small"
        style="width: 180px; margin-left: 12px" @clear="loadCards" @keyup.enter.native="loadCards" />
    </div>

    <!-- 卡片流 -->
    <div class="card-grid" v-loading="loading">
      <div v-for="inv in invoices" :key="inv.id" class="inv-card" :class="{ 'inv-card--reversed': inv.status === 'reversed' }">
        <div class="inv-card-header">
          <span class="inv-no">{{ inv.invoiceNo || inv.sn || '—' }}</span>
          <span class="inv-status" :class="'status-' + (inv.status || 'pending')">
            {{ statusMap[inv.status] || $t('invoiceBoard.statusPending') }}
          </span>
        </div>
        <div class="inv-card-body">
          <div class="inv-customer">{{ inv.customerName || inv.companyName || '—' }}</div>
          <div class="inv-amount" :class="{ 'amount-reversed': inv.status === 'reversed' }">
            ¥{{ formatMoney(inv.amount || inv.invoiceAmount || 0) }}
          </div>
        </div>
        <div class="inv-card-footer">
          <span class="inv-date">{{ inv.invoiceDate || inv.createTime || '' }}</span>
        </div>
      </div>
      <div v-if="invoices.length === 0 && !loading" class="empty-state">
        <div class="empty-icon">📄</div>
        <div class="empty-text">{{ $t('invoiceBoard.noInvoiceData') }}</div>
      </div>
    </div>
  </div>
</template>

<script>
import countTo from 'vue-count-to'
import { listInvoiceCards } from '@/api/erp/invoiceboard'

export default {
  name: 'InvoiceBoard',
  components: { countTo },
  data() {
    return {
      loading: false,
      boardStats: { invoicedAmount: 128600, pendingAmount: 34200, reconcileRate: 79.0 },
      invoices: [],
      dateRange: [],
      statusFilter: '',
      customerSearch: ''
    }
  },
  computed: {
    statusMap() {
      return {
        verified: this.$t('invoiceBoard.statusVerified'),
        partial: this.$t('invoiceBoard.statusPartial'),
        pending: this.$t('invoiceBoard.statusPending'),
        reversed: this.$t('invoiceBoard.statusReversed')
      }
    }
  },
  created() { this.loadCards() },
  methods: {
    loadCards() {
      this.loading = true
      const params = { pageNum: 1, pageSize: 50 }
      if (this.dateRange && this.dateRange.length === 2) {
        params.startDate = this.dateRange[0]
        params.endDate = this.dateRange[1]
      }
      if (this.statusFilter) params.status = this.statusFilter
      if (this.customerSearch) params.companyName = this.customerSearch
      listInvoiceCards(params).then(res => {
        this.invoices = res.rows || res.data || []
        this.loading = false
      }).catch(() => { this.loading = false })
    },
    formatMoney(val) {
      return Number(val).toLocaleString('zh-CN', { minimumFractionDigits: 2, maximumFractionDigits: 2 })
    }
  }
}
</script>

<style scoped>
.invoice-board { padding: 24px; max-width: 1400px; margin: 0 auto; }

.stat-strip { display: grid; grid-template-columns: repeat(3, 1fr); gap: 20px; margin-bottom: 24px; }
.stat-card {
  background: var(--app-card-bg); border-radius: var(--app-border-radius);
  box-shadow: var(--app-box-shadow); padding: 28px 24px; text-align: center; border: none;
  backdrop-filter: var(--app-blur); -webkit-backdrop-filter: var(--app-blur);
  transition: var(--app-transition);
}
.stat-card:hover { transform: translateY(-2px); }
.stat-value { font-size: 32px; font-weight: 700; color: var(--app-text-title); letter-spacing: -0.5px; }
.stat-label { font-size: 13px; color: var(--app-text-tip); margin-top: 8px; }

.filter-bar {
  display: flex; align-items: center; margin-bottom: 20px; padding: 16px 20px;
  background: var(--app-card-bg); border-radius: var(--app-border-radius);
  box-shadow: var(--app-box-shadow); border: none;
}

.card-grid {
  display: grid; grid-template-columns: repeat(3, 1fr); gap: 16px; min-height: 200px;
}
@media (max-width: 1024px) { .card-grid { grid-template-columns: repeat(2, 1fr); } }
@media (max-width: 640px) { .card-grid { grid-template-columns: 1fr; } }

.inv-card {
  background: var(--app-card-bg); border-radius: var(--app-border-radius);
  box-shadow: var(--app-box-shadow); padding: 20px; border: none; cursor: default;
  backdrop-filter: var(--app-blur); -webkit-backdrop-filter: var(--app-blur);
  transition: var(--app-transition);
}
.inv-card:hover { transform: translateY(-2px); box-shadow: 0 6px 20px rgba(0,0,0,0.08); }
.inv-card--reversed { opacity: 0.7; }

.inv-card-header { display: flex; justify-content: space-between; align-items: center; margin-bottom: 14px; }
.inv-no { font-size: 14px; font-weight: 600; color: var(--app-text-title); }
.inv-status {
  display: inline-block; padding: 2px 10px; border-radius: 20px; font-size: 11px; font-weight: 600;
}
.status-verified { background: rgba(52,199,89,0.1); color: var(--app-success-color); }
.status-partial { background: rgba(255,159,10,0.1); color: var(--app-warning-color); }
.status-pending { background: rgba(142,142,147,0.1); color: var(--app-text-tip); }
.status-reversed { background: rgba(255,59,48,0.1); color: var(--app-danger-color); }

.inv-card-body { margin-bottom: 12px; }
.inv-customer { font-size: 13px; color: var(--app-text-tip); margin-bottom: 6px; }
.inv-amount { font-size: 24px; font-weight: 700; color: var(--app-text-title); letter-spacing: -0.3px; }
.amount-reversed { text-decoration: line-through; color: var(--app-danger-color); }

.inv-card-footer { font-size: 12px; color: var(--app-text-tip); }

.empty-state { grid-column: 1 / -1; text-align: center; padding: 60px 0; }
.empty-icon { font-size: 48px; margin-bottom: 12px; }
.empty-text { font-size: 14px; color: var(--app-text-tip); }
</style>
