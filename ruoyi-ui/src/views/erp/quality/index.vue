<template>
  <div class="quality-workbench">
    <!-- KPI 带 -->
    <div class="kpi-strip">
      <div class="kpi-card">
        <div class="kpi-value">{{ stats.pendingCount || 0 }}</div>
        <div class="kpi-label">待检批次</div>
      </div>
      <div class="kpi-card">
        <div class="kpi-value" style="color: var(--app-primary-color)">
          <count-to :startVal="0" :endVal="stats.todayChecked || 0" :duration="1200" />
        </div>
        <div class="kpi-label">今日已检</div>
      </div>
      <div class="kpi-card">
        <div class="kpi-value" style="color: var(--app-success-color)">
          <count-to :startVal="0" :endVal="stats.passRate || 0" :duration="1600" :decimals="1" suffix="%" />
        </div>
        <div class="kpi-label">合格率</div>
      </div>
      <div class="kpi-card" :class="{ 'kpi-card--pulse': stats.criticalCount > 0 }">
        <div class="kpi-value" style="color: var(--app-danger-color)">{{ stats.criticalCount || 0 }}</div>
        <div class="kpi-label">致命缺陷</div>
      </div>
    </div>

    <!-- 图表双栏 -->
    <div class="chart-row">
      <div class="chart-panel">
        <div class="panel-title">AQL 合格率</div>
        <div ref="ringChart" class="chart-canvas" />
      </div>
      <div class="chart-panel">
        <div class="panel-title">不合格原因 TOP 5</div>
        <div ref="barChart" class="chart-canvas" />
      </div>
    </div>

    <!-- 扫码 + 记录 -->
    <div class="action-section">
      <div class="scan-bar">
        <input
          ref="scanInput"
          v-model="barcode"
          class="scan-input"
          placeholder="扫码或输入批次号，回车查询..."
          @keyup.enter="handleScan"
        />
      </div>
<!-- PLACEHOLDER_TABLE -->
      <div class="record-table">
        <table>
          <thead>
            <tr>
              <th style="width: 4px; padding: 0"></th>
              <th>批次号</th>
              <th>款号</th>
              <th>检验结果</th>
              <th>检验员</th>
              <th>时间</th>
              <th style="width: 80px">操作</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in records" :key="item.id"
                :class="{ 'row-critical': item.resultLevel === 'CRITICAL', 'row-flash': item._flash }">
              <td class="row-indicator" :class="{ 'indicator-critical': item.resultLevel === 'CRITICAL' }"></td>
              <td>{{ item.batchNo || item.bulkOrderNo }}</td>
              <td>{{ item.styleCode }}</td>
              <td>
                <span class="result-tag" :class="'result-' + (item.resultLevel || 'PASS').toLowerCase()">
                  {{ item.resultLevel === 'CRITICAL' ? '致命' : item.resultLevel === 'MAJOR' ? '严重' : item.resultLevel === 'MINOR' ? '轻微' : '合格' }}
                </span>
              </td>
              <td>{{ item.createBy }}</td>
              <td>{{ item.createTime }}</td>
              <td>
                <button v-if="item.resultLevel === 'CRITICAL'" class="action-reject" @click="openReject(item)">打回</button>
              </td>
            </tr>
            <tr v-if="records.length === 0">
              <td colspan="7" style="text-align: center; color: var(--app-text-tip); padding: 40px 0">暂无检验记录</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>

    <!-- 打回抽屉 -->
    <el-drawer :visible.sync="drawerVisible" direction="rtl" size="380px" :show-close="false">
      <template #title>
        <span style="font-size: 16px; font-weight: 600; color: var(--app-text-title)">确认打回</span>
      </template>
      <div class="drawer-body">
        <div class="drawer-info">
          <div class="drawer-info-row"><span class="drawer-info-label">批次号</span><span>{{ rejectItem.batchNo }}</span></div>
          <div class="drawer-info-row"><span class="drawer-info-label">款号</span><span>{{ rejectItem.styleCode }}</span></div>
          <div class="drawer-info-row"><span class="drawer-info-label">缺陷类型</span><el-tag type="danger" size="small">致命缺陷</el-tag></div>
        </div>
        <el-input v-model="rejectReason" type="textarea" :rows="3" placeholder="打回原因（选填）" style="margin-top: 20px" />
        <button class="reject-confirm-btn" @click="confirmReject">确认打回</button>
      </div>
    </el-drawer>
  </div>
</template>

<script>
import * as echarts from 'echarts'
import countTo from 'vue-count-to'
import { listRecentChecks, rejectCheck } from '@/api/erp/quality'

export default {
  name: 'QualityWorkbench',
  components: { countTo },
  data() {
    return {
      stats: { pendingCount: 12, todayChecked: 47, passRate: 96.8, criticalCount: 2 },
      records: [],
      barcode: '',
      drawerVisible: false,
      rejectItem: {},
      rejectReason: '',
      ringChart: null,
      barChartInst: null
    }
  },
  mounted() {
    this.$nextTick(() => {
      this.$refs.scanInput && this.$refs.scanInput.focus()
      this.initRingChart()
      this.initBarChart()
      this.loadRecords()
    })
  },
  beforeDestroy() {
    this.ringChart && this.ringChart.dispose()
    this.barChartInst && this.barChartInst.dispose()
  },
  methods: {
    loadRecords() {
      listRecentChecks({ pageNum: 1, pageSize: 20 }).then(res => {
        this.records = (res.rows || res.data || []).map(r => ({ ...r, _flash: false }))
      }).catch(() => {})
    },
    handleScan() {
      if (!this.barcode.trim()) return
      listRecentChecks({ bulkOrderNo: this.barcode.trim(), pageNum: 1, pageSize: 20 }).then(res => {
        this.records = (res.rows || res.data || []).map(r => ({ ...r, _flash: false }))
      })
      this.barcode = ''
      this.$refs.scanInput && this.$refs.scanInput.focus()
    },
    openReject(item) {
      this.rejectItem = item
      this.rejectReason = ''
      this.drawerVisible = true
    },
    confirmReject() {
      rejectCheck(this.rejectItem.id, { reason: this.rejectReason }).then(() => {
        this.drawerVisible = false
        const idx = this.records.findIndex(r => r.id === this.rejectItem.id)
        if (idx > -1) {
          this.$set(this.records[idx], '_flash', true)
          setTimeout(() => { this.records.splice(idx, 1) }, 600)
        }
        this.$message.success('已打回')
      }).catch(() => { this.$message.error('打回失败') })
    },
    initRingChart() {
      this.ringChart = echarts.init(this.$refs.ringChart)
      const rate = this.stats.passRate
      this.ringChart.setOption({
        series: [{
          type: 'pie', radius: ['62%', '80%'], center: ['50%', '50%'],
          silent: true, label: { show: false },
          data: [
            { value: rate, itemStyle: { color: 'var(--app-success-color, #34C759)' } },
            { value: 100 - rate, itemStyle: { color: 'var(--app-ring-track, #f0f0f0)' } }
          ]
        }],
        graphic: [{
          type: 'text', left: 'center', top: '42%',
          style: { text: rate.toFixed(1) + '%', fontSize: 32, fontWeight: 700, fill: '#1D1D1F', textAlign: 'center' }
        }, {
          type: 'text', left: 'center', top: '58%',
          style: { text: 'AQL 合格率', fontSize: 13, fill: '#86868B', textAlign: 'center' }
        }]
      })
    },
    initBarChart() {
      this.barChartInst = echarts.init(this.$refs.barChart)
      const reasons = ['色差超标', '尺寸偏差', '织疵', '缝制不良', '污渍']
      const values = [18, 12, 9, 7, 3]
      this.barChartInst.setOption({
        grid: { left: 90, right: 30, top: 10, bottom: 10 },
        xAxis: { type: 'value', show: false },
        yAxis: { type: 'category', data: reasons.reverse(), axisTick: { show: false }, axisLine: { show: false },
          axisLabel: { color: '#6E6E73', fontSize: 13 } },
        series: [{ type: 'bar', data: values.reverse(), barWidth: 18, itemStyle: { borderRadius: 4, color: new echarts.graphic.LinearGradient(0, 0, 1, 0, [
          { offset: 0, color: 'rgba(255,59,48,0.7)' }, { offset: 1, color: 'rgba(255,149,0,0.7)' }
        ]) }, label: { show: true, position: 'right', color: '#6E6E73', fontSize: 12 } }]
      })
    }
  }
}
</script>

<style scoped>
.quality-workbench { padding: 24px; max-width: 1400px; margin: 0 auto; }

.kpi-strip { display: grid; grid-template-columns: repeat(4, 1fr); gap: 20px; margin-bottom: 28px; }
.kpi-card {
  background: var(--app-card-bg); border-radius: var(--app-border-radius);
  box-shadow: var(--app-box-shadow); padding: 28px 24px; text-align: center;
  border: none; transition: var(--app-transition);
  backdrop-filter: var(--app-blur); -webkit-backdrop-filter: var(--app-blur);
}
.kpi-card:hover { transform: translateY(-2px); }
.kpi-value { font-size: 36px; font-weight: 700; color: var(--app-text-title); line-height: 1.2; letter-spacing: -0.5px; }
.kpi-label { font-size: 13px; color: var(--app-text-tip); margin-top: 8px; letter-spacing: 0.5px; }
.kpi-card--pulse { animation: pulse-glow 2s ease-in-out infinite; }
@keyframes pulse-glow {
  0%, 100% { box-shadow: var(--app-box-shadow); }
  50% { box-shadow: 0 0 0 6px var(--app-critical-glow), var(--app-box-shadow); }
}

.chart-row { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 28px; }
.chart-panel {
  background: var(--app-card-bg); border-radius: var(--app-border-radius);
  box-shadow: var(--app-box-shadow); padding: 24px; border: none;
  backdrop-filter: var(--app-blur); -webkit-backdrop-filter: var(--app-blur);
}
.panel-title { font-size: 15px; font-weight: 600; color: var(--app-text-title); margin-bottom: 16px; }
.chart-canvas { height: 240px; }

.action-section {
  background: var(--app-card-bg); border-radius: var(--app-border-radius);
  box-shadow: var(--app-box-shadow); padding: 24px; border: none;
  backdrop-filter: var(--app-blur); -webkit-backdrop-filter: var(--app-blur);
}
.scan-bar { margin-bottom: 20px; }
.scan-input {
  width: 100%; padding: 14px 20px; font-size: 16px; border: 2px solid var(--app-border-color);
  border-radius: var(--app-border-radius); outline: none; background: var(--app-card-bg-secondary);
  color: var(--app-text-title); transition: var(--app-transition);
}
.scan-input:focus { border-color: var(--app-primary-color); background: var(--app-card-bg); }
.scan-input::placeholder { color: var(--app-text-tip); }

.record-table { overflow-x: auto; }
.record-table table { width: 100%; border-collapse: collapse; }
.record-table th {
  text-align: left; padding: 10px 14px; font-size: 12px; font-weight: 600;
  color: var(--app-text-tip); text-transform: uppercase; letter-spacing: 0.5px;
  border-bottom: 1px solid var(--app-border-color);
}
.record-table td { padding: 12px 14px; font-size: 14px; color: var(--app-text-main); border-bottom: 1px solid rgba(0,0,0,0.04); }
.record-table tbody tr { transition: var(--app-transition); }
.record-table tbody tr:hover { background: var(--app-card-bg-secondary); }

.row-indicator { width: 4px; padding: 0 !important; }
.indicator-critical { background: var(--app-danger-color); }
.row-critical { background: var(--app-critical-glow); }
.row-flash { animation: flash-out 0.6s ease forwards; }
@keyframes flash-out {
  0% { opacity: 1; background: rgba(255,59,48,0.15); }
  50% { opacity: 0.4; }
  100% { opacity: 0; height: 0; padding: 0; overflow: hidden; }
}

.result-tag {
  display: inline-block; padding: 2px 10px; border-radius: 20px; font-size: 12px; font-weight: 500;
}
.result-pass { background: rgba(52,199,89,0.1); color: var(--app-success-color); }
.result-minor { background: rgba(255,159,10,0.1); color: var(--app-warning-color); }
.result-major { background: rgba(255,149,0,0.15); color: #E65100; }
.result-critical { background: rgba(255,59,48,0.12); color: var(--app-danger-color); font-weight: 600; }

.action-reject {
  padding: 4px 14px; border: none; border-radius: 6px; font-size: 12px; font-weight: 600;
  color: #fff; background: linear-gradient(135deg, #FF3B30, #FF6B6B); cursor: pointer;
  transition: var(--app-transition);
}
.action-reject:hover { transform: scale(1.05); box-shadow: 0 2px 8px rgba(255,59,48,0.3); }

.drawer-body { padding: 0 20px 20px; }
.drawer-info { background: var(--app-card-bg-secondary); border-radius: 8px; padding: 16px; }
.drawer-info-row { display: flex; justify-content: space-between; padding: 6px 0; font-size: 14px; color: var(--app-text-main); }
.drawer-info-label { color: var(--app-text-tip); }
.reject-confirm-btn {
  width: 100%; margin-top: 24px; padding: 14px; border: none; border-radius: 12px;
  font-size: 16px; font-weight: 600; color: #fff; cursor: pointer;
  background: linear-gradient(135deg, #FF3B30 0%, #FF6259 100%);
  transition: var(--app-transition);
}
.reject-confirm-btn:hover { opacity: 0.9; transform: scale(0.98); }
</style>
