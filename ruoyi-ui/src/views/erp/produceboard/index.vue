<template>
  <div class="app-container">
    <!-- 总体统计卡片 -->
    <el-row :gutter="20">
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }">
          <div class="stats-number">{{ stats.totalPlans }}</div>
          <div class="stats-label">{{ $t('produceboard.totalPlans') }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }">
          <div class="stats-number text-warning">{{ stats.inProgressPlans }}</div>
          <div class="stats-label">{{ $t('produceboard.inProgress') }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }">
          <div class="stats-number text-success">{{ stats.completedPlans }}</div>
          <div class="stats-label">{{ $t('produceboard.completed') }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }">
          <div class="stats-number text-primary">{{ stats.totalWipJobs }}</div>
          <div class="stats-label">{{ $t('produceboard.wipJobs') }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }" v-if="stats.pendingApprovalCount > 0">
          <div class="stats-number text-danger">{{ stats.pendingApprovalCount }}</div>
          <div class="stats-label">{{ $t('produceboard.pendingApproval') }}</div>
        </el-card>
        <el-card class="stats-card" :body-style="{ padding: '20px' }" v-else>
          <div class="stats-number text-success">{{ stats.pendingApprovalCount }}</div>
          <div class="stats-label">{{ $t('produceboard.pendingApproval') }}</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }" v-if="stats.unhandledAbnormalCount > 0">
          <div class="stats-number text-danger">{{ stats.unhandledAbnormalCount }}</div>
          <div class="stats-label">{{ $t('produceboard.unhandledAbnormal') }}</div>
        </el-card>
        <el-card class="stats-card" :body-style="{ padding: '20px' }" v-else>
          <div class="stats-number text-success">{{ stats.unhandledAbnormalCount }}</div>
          <div class="stats-label">{{ $t('produceboard.unhandledAbnormal') }}</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 率 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>{{ $t('produceboard.efficiency') }}</span>
          </div>
          <div class="rate-row">
            <div class="rate-item">
              <div class="rate-label">{{ $t('produceboard.onTimeDeliveryRate') }}</div>
              <div class="rate-value">
                <el-progress :percentage="stats.onTimeDeliveryRate || 0" :color="getProgressColor(stats.onTimeDeliveryRate)"></el-progress>
              </div>
            </div>
            <div class="rate-item">
              <div class="rate-label">{{ $t('produceboard.capacityUtilization') }}</div>
              <div class="rate-value">
                <el-progress :percentage="stats.capacityUtilization || 0" :color="getProgressColor(stats.capacityUtilization)"></el-progress>
              </div>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>{{ $t('produceboard.todayWeekCompleted') }}</span>
          </div>
          <div class="rate-row">
            <div class="rate-item">
              <div class="rate-label">{{ $t('produceboard.todayCompleted') }}</div>
              <div class="daily-number">{{ stats.todayCompleted }}</div>
            </div>
            <div class="rate-item">
              <div class="rate-label">{{ $t('produceboard.weekCompleted') }}</div>
              <div class="daily-number">{{ stats.weekCompleted }}</div>
            </div>
            <div class="rate-item">
              <div class="rate-label">{{ $t('produceboard.todayNewJobs') }}</div>
              <div class="daily-number">{{ stats.todayNewJobs }}</div>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 瓶颈预警 -->
    <el-row :gutter="20" style="margin-top: 20px;" v-if="bottleneckWarnings.length > 0">
      <el-col :span="24">
        <el-alert
          :title="$t('produceboard.bottleneckWarning')"
          type="danger"
          :description="$t('produceboard.bottleneckDesc')"
          show-icon
          :closable="false">
        </el-alert>
      </el-col>
      <el-col :span="24" style="margin-top: 10px;">
        <el-table :data="bottleneckWarnings" border style="width: 100%">
          <el-table-column prop="processName" :label="$t('produceboard.column.processName')" width="150" />
          <el-table-column prop="wipJobCount" :label="$t('produceboard.column.wipJobCount')" width="100" align="center" />
          <el-table-column prop="wipQuantity" :label="$t('produceboard.column.wipQuantity')" width="100" align="center" />
          <el-table-column prop="dailyCapacity" :label="$t('produceboard.column.dailyCapacity')" width="100" align="center" />
          <el-table-column prop="estimatedDays" :label="$t('produceboard.column.estimatedDays')" width="120" align="center" />
          <el-table-column prop="utilizationRate" :label="$t('produceboard.column.utilizationRate')" width="120" align="center">
            <template slot-scope="scope">
              <el-tag :type="scope.row.utilizationRate > 100 ? 'danger' : scope.row.utilizationRate > 80 ? 'warning' : 'success'">
                {{ scope.row.utilizationRate.toFixed(1) }}%
              </el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>

    <!-- 工序WIP统计 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <div slot="header">
            <span>{{ $t('produceboard.wipStats') }}</span>
          </div>
          <el-table :data="wipStats" v-loading="loading" border style="width: 100%">
            <el-table-column prop="processName" :label="$t('produceboard.column.processName')" width="150" />
            <el-table-column prop="wipJobCount" :label="$t('produceboard.column.wipJobCount')" width="100" align="center" />
            <el-table-column prop="wipQuantity" :label="$t('produceboard.column.wipQuantity')" width="100" align="center" />
            <el-table-column prop="dailyCapacity" :label="$t('produceboard.column.dailyCapacity')" width="100" align="center" />
            <el-table-column prop="estimatedDays" :label="$t('produceboard.column.estimatedDays')" width="120" align="center">
              <template slot-scope="scope">
                <el-tag :type="scope.row.isBottleneck ? 'danger' : 'info'">
                  {{ scope.row.estimatedDays.toFixed(1) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="utilizationRate" :label="$t('produceboard.column.utilizationRate')" width="120" align="center">
              <template slot-scope="scope">
                <el-progress :percentage="Math.min(scope.row.utilizationRate, 100)" :color="getProgressColor(scope.row.utilizationRate)"></el-progress>
              </template>
            </el-table-column>
          </el-table>
        </el-card>
      </el-col>
    </el-row>

    <!-- 员工产量排名 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <div slot="header">
            <span>{{ $t('produceboard.employeeRank') }}（Top 10）</span>
          </div>
          <el-table :data="employeeRank" v-loading="loading" border style="width: 100%">
            <el-table-column prop="rank" :label="$t('produceboard.column.rank')" width="60" align="center">
              <template slot-scope="scope">
                <el-tag :type="scope.row.rank === 1 ? 'danger' : scope.row.rank === 2 ? 'warning' : scope.row.rank === 3 ? 'primary' : 'info'">
                  {{ scope.row.rank }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="employeeName" :label="$t('produceboard.column.employeeName')" width="120" />
            <el-table-column prop="monthlyQuantity" :label="$t('produceboard.column.monthlyQuantity')" width="120" align="center" />
            <el-table-column prop="dailyAverage" :label="$t('produceboard.column.dailyAverage')" width="100" align="center" />
            <el-table-column prop="monthlyWage" :label="$t('produceboard.column.monthlyWage')" width="120" align="center" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getBoardStats, getWipStats, getEmployeeRank, getBottleneckWarnings } from "@/api/erp/produceboard";
import SockJS from "sockjs-client";
import { Stomp } from "@stomp/stompjs";

export default {
  name: "ProduceBoard",
  data() {
    return {
      loading: false,
      stats: {
        totalPlans: 0,
        inProgressPlans: 0,
        completedPlans: 0,
        todayCompleted: 0,
        weekCompleted: 0,
        totalWipJobs: 0,
        todayNewJobs: 0,
        pendingApprovalCount: 0,
        unhandledAbnormalCount: 0,
        onTimeDeliveryRate: 0,
        capacityUtilization: 0
      },
      wipStats: [],
      employeeRank: [],
      bottleneckWarnings: [],
      wsClient: null,
      wsConnected: false
    };
  },
  created() {
    this.loadData();
    this.connectWs();
  },
  beforeDestroy() {
    this.disconnectWs();
  },
  methods: {
    loadData() {
      this.loading = true;
      Promise.all([
        getBoardStats(),
        getWipStats(),
        getEmployeeRank(),
        getBottleneckWarnings()
      ]).then(([resStats, resWip, resRank, resBottle]) => {
        this.stats = {
          ...this.stats,
          ...(resStats.data || {})
        };
        this.wipStats = resWip.data || [];
        this.employeeRank = resRank.data || [];
        this.bottleneckWarnings = resBottle.data || [];
      }).catch(() => {
        this.wipStats = [];
        this.employeeRank = [];
        this.bottleneckWarnings = [];
        this.$modal.msgError(this.$t('produceboard.boardLoadFailed'));
      }).finally(() => {
        this.loading = false;
      });
    },
    connectWs() {
      // Dev proxy on localhost:80 is unstable for SockJS/STOMP and can crash vue-cli-service.
      // Keep realtime push enabled in non-dev environments; local smoke relies on manual refresh.
      if (process.env.NODE_ENV === 'development') {
        this.wsConnected = false;
        return;
      }
      try {
        const baseUrl = (process.env.VUE_APP_BASE_API || '').replace(/\/$/, '');
        const wsUrl = `${window.location.origin}${baseUrl}/ws/erp`;
        const socket = new SockJS(wsUrl);
        const client = Stomp.over(socket);
        client.debug = () => {}; // 静默 STOMP debug 日志
        client.connect({}, () => {
          this.wsConnected = true;
          // 订阅生产看板刷新信号
          client.subscribe('/topic/erp/produce', (msg) => {
            const data = JSON.parse(msg.body);
            if (data.type === 'REFRESH' || data.type === 'PROCESS_COMPLETE') {
              this.loadData(); // 收到推送后自动刷新看板数据
            }
          });
          // 订阅报警
          client.subscribe('/topic/erp/alert', (msg) => {
            const data = JSON.parse(msg.body);
            this.$notify({ type: data.level === 'ERROR' ? 'error' : 'warning', title: this.$t('produceboard.alertTitle'), message: data.message });
          });
        }, () => { this.wsConnected = false; });
        this.wsClient = client;
      } catch (e) {
        console.warn('WebSocket 连接失败（非致命，看板仍可手动刷新）:', e.message);
      }
    },
    disconnectWs() {
      if (this.wsClient && this.wsConnected) {
        try { this.wsClient.disconnect(); } catch (e) {}
        this.wsConnected = false;
      }
    },
    getProgressColor(percentage) {
      if (percentage < 50) {
        return '#67C23A';
      } else if (percentage < 80) {
        return '#E6A23C';
      } else {
        return '#F56C6C';
      }
    }
  }
};
</script>

<style scoped>
.stats-card {
  text-align: center;
}
.stats-number {
  font-size: 28px;
  font-weight: bold;
  line-height: 40px;
}
.stats-label {
  color: #909399;
  font-size: 14px;
}
.text-primary {
  color: #409EFF;
}
.text-success {
  color: #67C23A;
}
.text-warning {
  color: #E6A23C;
}
.text-danger {
  color: #F56C6C;
}
.rate-row {
  display: flex;
  flex-wrap: wrap;
}
.rate-item {
  width: 50%;
  margin-bottom: 15px;
}
.rate-label {
  font-size: 14px;
  color: #606266;
  margin-bottom: 5px;
}
.daily-number {
  font-size: 26px;
  font-weight: bold;
  color: #409EFF;
}
</style>
