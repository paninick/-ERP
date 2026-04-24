<template>
  <div class="app-container">
    <!-- 工具栏 -->
    <el-form :inline="true" :model="queryParams" size="small" class="mb8">
      <el-form-item label="开始日期">
        <el-date-picker v-model="queryParams.startDate" type="date" value-format="yyyy-MM-dd" placeholder="开始日期" clearable />
      </el-form-item>
      <el-form-item label="截止日期">
        <el-date-picker v-model="queryParams.endDate" type="date" value-format="yyyy-MM-dd" placeholder="截止日期" clearable />
      </el-form-item>
      <el-form-item label="工序">
        <el-input v-model="queryParams.process" placeholder="工序名称" clearable @keyup.enter.native="loadData" />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="loadData">查询</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        <el-button type="warning" icon="el-icon-warning" size="mini" v-hasPermi="['erp:producegantt:detect']" @click="detectConflicts">检测冲突</el-button>
      </el-form-item>
    </el-form>

    <!-- 图例 -->
    <div class="legend mb8">
      <span class="legend-item"><span class="dot dot-normal"></span>正常</span>
      <span class="legend-item"><span class="dot dot-conflict-cap"></span>产能冲突</span>
      <span class="legend-item"><span class="dot dot-conflict-date"></span>日期冲突</span>
      <span class="legend-item"><span class="dot dot-done"></span>已完成</span>
    </div>

    <!-- 甘特图主体 -->
    <div v-loading="loading" class="gantt-wrap">
      <div v-if="tasks.length === 0 && !loading" class="empty-tip">暂无排程数据</div>
      <div v-else class="gantt-scroll">
        <!-- 表头：日期轴 -->
        <div class="gantt-header">
          <div class="gantt-label-col">工序 / 订单</div>
          <div class="gantt-timeline">
            <div
              v-for="d in dateAxis"
              :key="d"
              class="gantt-day-header"
              :class="{ today: d === today }"
            >{{ d.slice(5) }}</div>
          </div>
        </div>

        <!-- 每行 -->
        <div
          v-for="task in tasks"
          :key="task.id"
          class="gantt-row"
          :class="rowClass(task)"
        >
          <div class="gantt-label-col">
            <div class="task-process">{{ task.process }}</div>
            <div class="task-meta">
              <el-tag size="mini" :type="priorityType(task.priority)">P{{ task.priority }}</el-tag>
              <el-tag size="mini" :type="statusType(task.scheduleStatus)" style="margin-left:4px">{{ statusLabel(task.scheduleStatus) }}</el-tag>
            </div>
          </div>
          <div class="gantt-timeline">
            <div
              v-for="d in dateAxis"
              :key="d"
              class="gantt-day-cell"
              :class="{ today: d === today }"
            >
              <div
                v-if="isInRange(task, d)"
                class="gantt-bar"
                :class="barClass(task)"
                :title="barTitle(task)"
                @click="openReschedule(task)"
              >
                <span v-if="isStartDay(task, d)" class="bar-label">{{ task.process }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 重新排期弹窗 -->
    <el-dialog title="重新排期" :visible.sync="rescheduleVisible" width="400px">
      <el-form :model="rescheduleForm" label-width="80px" size="small">
        <el-form-item label="工序">
          <span>{{ rescheduleForm.process }}</span>
        </el-form-item>
        <el-form-item label="开始日期">
          <el-date-picker v-model="rescheduleForm.newStartDate" type="date" value-format="yyyy-MM-dd" style="width:100%" />
        </el-form-item>
        <el-form-item label="截止日期">
          <el-date-picker v-model="rescheduleForm.newDueDate" type="date" value-format="yyyy-MM-dd" style="width:100%" />
        </el-form-item>
      </el-form>
      <div slot="footer">
        <el-button size="small" @click="rescheduleVisible = false">取消</el-button>
        <el-button type="primary" size="small" :loading="submitLoading" v-hasPermi="['erp:producegantt:edit']" @click="submitReschedule">确定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getGanttData, updatePlanDate, detectConflicts } from "@/api/erp/producegantt";

export default {
  name: "ProduceGantt",
  data() {
    return {
      loading: false,
      tasks: [],
      dateAxis: [],
      today: new Date().toISOString().slice(0, 10),
      queryParams: { startDate: "", endDate: "", process: "" },
      submitLoading: false,
      rescheduleVisible: false,
      rescheduleForm: { id: null, process: "", newStartDate: "", newDueDate: "" }
    };
  },
  mounted() {
    this.initDefaultRange();
    this.loadData();
  },
  methods: {
    initDefaultRange() {
      const now = new Date();
      const start = new Date(now);
      start.setDate(1);
      const end = new Date(now);
      end.setMonth(end.getMonth() + 2);
      end.setDate(0);
      this.queryParams.startDate = start.toISOString().slice(0, 10);
      this.queryParams.endDate = end.toISOString().slice(0, 10);
    },
    loadData() {
      this.loading = true;
      getGanttData(this.queryParams).then(res => {
        this.tasks = res.data || [];
        this.buildDateAxis();
      }).finally(() => { this.loading = false; });
    },
    buildDateAxis() {
      const start = this.queryParams.startDate || this.today;
      const end = this.queryParams.endDate || this.today;
      const axis = [];
      let cur = new Date(start);
      const endD = new Date(end);
      while (cur <= endD) {
        axis.push(cur.toISOString().slice(0, 10));
        cur.setDate(cur.getDate() + 1);
      }
      this.dateAxis = axis;
    },
    resetQuery() {
      this.queryParams = { startDate: "", endDate: "", process: "" };
      this.initDefaultRange();
      this.loadData();
    },
    isInRange(task, d) {
      if (!task.startDate || !task.dueDate) return false;
      return d >= task.startDate && d <= task.dueDate;
    },
    isStartDay(task, d) {
      return d === task.startDate;
    },
    rowClass(task) {
      if (task.conflictFlag === "1") return "row-conflict-cap";
      if (task.conflictFlag === "2") return "row-conflict-date";
      if (task.scheduleStatus === "3") return "row-done";
      return "";
    },
    barClass(task) {
      if (task.conflictFlag === "1") return "bar-conflict-cap";
      if (task.conflictFlag === "2") return "bar-conflict-date";
      if (task.scheduleStatus === "3") return "bar-done";
      return "bar-normal";
    },
    barTitle(task) {
      return `${task.process} | 负载:${task.load || 0}% | ${task.startDate}~${task.dueDate}`;
    },
    priorityType(p) {
      if (p <= 2) return "danger";
      if (p <= 4) return "warning";
      if (p <= 6) return "primary";
      return "info";
    },
    statusType(s) {
      const map = { "0": "info", "1": "primary", "2": "warning", "3": "success" };
      return map[s] || "info";
    },
    statusLabel(s) {
      const map = { "0": "待排", "1": "已排", "2": "进行中", "3": "已完成" };
      return map[s] || s;
    },
    detectConflicts() {
      detectConflicts().then(res => {
        this.$modal.msgSuccess(`检测完成，发现冲突 ${res.data} 条`);
        this.loadData();
      });
    },
    openReschedule(task) {
      this.rescheduleForm = {
        id: task.id,
        process: task.process,
        newStartDate: task.startDate,
        newDueDate: task.dueDate
      };
      this.rescheduleVisible = true;
    },
    submitReschedule() {
      const { id, newStartDate, newDueDate } = this.rescheduleForm;
      this.submitLoading = true;
      updatePlanDate(id, newStartDate, newDueDate).then(() => {
        this.$modal.msgSuccess("排期已更新");
        this.rescheduleVisible = false;
        this.loadData();
      }).finally(() => { this.submitLoading = false; });
    }
  }
};
</script>

<style scoped>
.gantt-wrap { overflow-x: auto; }
.gantt-scroll { min-width: 900px; }
.gantt-header, .gantt-row { display: flex; align-items: stretch; border-bottom: 1px solid #ebeef5; }
.gantt-header { background: #f5f7fa; font-weight: bold; font-size: 12px; }
.gantt-label-col { width: 160px; min-width: 160px; padding: 6px 8px; border-right: 1px solid #ebeef5; }
.gantt-timeline { display: flex; flex: 1; }
.gantt-day-header { width: 32px; min-width: 32px; text-align: center; padding: 4px 0; font-size: 11px; border-right: 1px solid #ebeef5; }
.gantt-day-cell { width: 32px; min-width: 32px; height: 40px; border-right: 1px solid #f0f0f0; position: relative; }
.gantt-day-header.today, .gantt-day-cell.today { background: #ecf5ff; }
.gantt-bar { position: absolute; top: 8px; left: 0; right: 0; height: 24px; border-radius: 3px; cursor: pointer; overflow: hidden; }
.bar-normal { background: #409EFF; }
.bar-conflict-cap { background: #E6A23C; }
.bar-conflict-date { background: #F56C6C; }
.bar-done { background: #67C23A; }
.bar-label { color: #fff; font-size: 11px; padding-left: 4px; white-space: nowrap; }
.task-process { font-size: 13px; font-weight: 500; }
.task-meta { margin-top: 2px; }
.row-conflict-cap { background: #fdf6ec; }
.row-conflict-date { background: #fef0f0; }
.row-done { background: #f0f9eb; }
.legend { display: flex; gap: 16px; font-size: 12px; color: #606266; }
.legend-item { display: flex; align-items: center; gap: 4px; }
.dot { display: inline-block; width: 12px; height: 12px; border-radius: 2px; }
.dot-normal { background: #409EFF; }
.dot-conflict-cap { background: #E6A23C; }
.dot-conflict-date { background: #F56C6C; }
.dot-done { background: #67C23A; }
.empty-tip { text-align: center; color: #909399; padding: 40px; }
</style>
