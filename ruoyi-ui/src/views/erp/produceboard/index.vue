<template>
  <div class="app-container">
    <!-- 总体统计卡片 -->
    <el-row :gutter="20">
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }">
          <div class="stats-number">{{ stats.totalPlans }}</div>
          <div class="stats-label">总生产计划</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }">
          <div class="stats-number text-warning">{{ stats.inProgressPlans }}</div>
          <div class="stats-label">进行中</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }">
          <div class="stats-number text-success">{{ stats.completedPlans }}</div>
          <div class="stats-label">已完成</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }">
          <div class="stats-number text-primary">{{ stats.totalWipJobs }}</div>
          <div class="stats-label">WIP工票数</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }" v-if="stats.pendingApprovalCount > 0">
          <div class="stats-number text-danger">{{ stats.pendingApprovalCount }}</div>
          <div class="stats-label">待审批超领</div>
        </el-card>
        <el-card class="stats-card" :body-style="{ padding: '20px' }" v-else>
          <div class="stats-number text-success">{{ stats.pendingApprovalCount }}</div>
          <div class="stats-label">待审批超领</div>
        </el-card>
      </el-col>
      <el-col :span="4">
        <el-card class="stats-card" :body-style="{ padding: '20px' }" v-if="stats.unhandledAbnormalCount > 0">
          <div class="stats-number text-danger">{{ stats.unhandledAbnormalCount }}</div>
          <div class="stats-label">未处理异常</div>
        </el-card>
        <el-card class="stats-card" :body-style="{ padding: '20px' }" v-else>
          <div class="stats-number text-success">{{ stats.unhandledAbnormalCount }}</div>
          <div class="stats-label">未处理异常</div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 率 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <div slot="header">
            <span>效率指标</span>
          </div>
          <div class="rate-row">
            <div class="rate-item">
              <div class="rate-label">准时交付率</div>
              <div class="rate-value">
                <el-progress :percentage="stats.onTimeDeliveryRate || 0" :color="getProgressColor(stats.onTimeDeliveryRate)"></el-progress>
              </div>
            </div>
            <div class="rate-item">
              <div class="rate-label">平均产能利用率</div>
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
            <span>今日/本周完成</span>
          </div>
          <div class="rate-row">
            <div class="rate-item">
              <div class="rate-label">今日完工</div>
              <div class="daily-number">{{ stats.todayCompleted }}</div>
            </div>
            <div class="rate-item">
              <div class="rate-label">本周完工</div>
              <div class="daily-number">{{ stats.weekCompleted }}</div>
            </div>
            <div class="rate-item">
              <div class="rate-label">今日新增工票</div>
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
          title="瓶颈工序预警"
          type="danger"
          description="以下工序WIP积压超过3天预计完成时间，需要关注产能调配"
          show-icon
          :closable="false">
        </el-alert>
      </el-col>
      <el-col :span="24" style="margin-top: 10px;">
        <el-table :data="bottleneckWarnings" border style="width: 100%">
          <el-table-column prop="processName" label="工序名称" width="150" />
          <el-table-column prop="wipJobCount" label="WIP扎数" width="100" align="center" />
          <el-table-column prop="wipQuantity" label="WIP总件数" width="100" align="center" />
          <el-table-column prop="dailyCapacity" label="日均产能" width="100" align="center" />
          <el-table-column prop="estimatedDays" label="预计完成天数" width="120" align="center" />
          <el-table-column prop="utilizationRate" label="利用率%" width="120" align="center">
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
            <span>工序WIP统计</span>
          </div>
          <el-table :data="wipStats" v-loading="loading" border style="width: 100%">
            <el-table-column prop="processName" label="工序名称" width="150" />
            <el-table-column prop="wipJobCount" label="WIP扎数" width="100" align="center" />
            <el-table-column prop="wipQuantity" label="WIP总件数" width="100" align="center" />
            <el-table-column prop="dailyCapacity" label="日均产能" width="100" align="center" />
            <el-table-column prop="estimatedDays" label="预计完成天数" width="120" align="center">
              <template slot-scope="scope">
                <el-tag :type="scope.row.isBottleneck ? 'danger' : 'info'">
                  {{ scope.row.estimatedDays.toFixed(1) }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="utilizationRate" label="利用率%" width="120" align="center">
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
            <span>本月员工产量排名（Top 10）</span>
          </div>
          <el-table :data="employeeRank" v-loading="loading" border style="width: 100%">
            <el-table-column prop="rank" label="排名" width="60" align="center">
              <template slot-scope="scope">
                <el-tag :type="scope.row.rank === 1 ? 'danger' : scope.row.rank === 2 ? 'warning' : scope.row.rank === 3 ? 'primary' : 'info'">
                  {{ scope.row.rank }}
                </el-tag>
              </template>
            </el-table-column>
            <el-table-column prop="employeeName" label="员工姓名" width="120" />
            <el-table-column prop="monthlyQuantity" label="本月产量（件）" width="120" align="center" />
            <el-table-column prop="dailyAverage" label="日均产量" width="100" align="center" />
            <el-table-column prop="monthlyWage" label="预计计件工资" width="120" align="center" />
          </el-table>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getBoardStats, getWipStats, getEmployeeRank, getBottleneckWarnings } from "@/api/erp/produceboard";
import SockJS from "sockjs-client";
import Stomp from "@stomp/stompjs";

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
        this.stats = resStats.data;
        this.wipStats = resWip.data;
        this.employeeRank = resRank.data;
        this.bottleneckWarnings = resBottle.data;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    connectWs() {
      try {
        const baseUrl = process.env.VUE_APP_BASE_API || '';
        const wsUrl = baseUrl.replace(/^http/, 'http') + '/ws/erp';
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
            this.$notify({ type: data.level === 'ERROR' ? 'error' : 'warning', title: '生产报警', message: data.message });
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
