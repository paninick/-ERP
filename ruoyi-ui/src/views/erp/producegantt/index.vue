<template>
  <div class="app-container">
    <div class="gantt-container" ref="ganttContainer">
      <div id="gantt-here" style="width: 100%; height: 600px;"></div>
    </div>
  </div>
</template>

<script>
import { getGanttData, updatePlanDate } from "@/api/erp/producegantt";

export default {
  name: "ProduceGantt",
  data() {
    return {
      loading: false,
      tasks: []
    };
  },
  mounted() {
    this.initGantt();
  },
  methods: {
    initGantt() {
      this.loading = true;
      getGanttData().then(response => {
        this.tasks = response.data;
        this.renderGantt();
        this.loading = false;
      });
    },
    renderGantt() {
      // Simple gantt display using HTML table
      // For production, integrate dhtmlx-gantt or similar library
      const container = document.getElementById('gantt-here');
      if (!container) return;

      let html = `
      <table style="width:100%;border-collapse:collapse;border:1px solid #ccc;">
        <thead>
          <tr style="background:#f5f5f5;">
            <th style="border:1px solid #ccc;padding:8px;text-align:left;width:200px;">生产计划</th>
            <th style="border:1px solid #ccc;padding:8px;text-align:left;width:100px;">客户</th>
            <th style="border:1px solid #ccc;padding:8px;text-align:left;width:80px;">进度</th>
            <th style="border:1px solid #ccc;padding:8px;text-align:left;width:120px;">开始日期</th>
            <th style="border:1px solid #ccc;padding:8px;text-align:left;width:120px;">截止日期</th>
          </tr>
        </thead>
        <tbody>
      `;

      this.tasks.forEach(task => {
        const progressColor = task.progress > 80 ? '#67C23A' : task.progress > 30 ? '#E6A23C' : '#909399';
        html += `
          <tr>
            <td style="border:1px solid #ccc;padding:8px;">${task.name}</td>
            <td style="border:1px solid #ccc;padding:8px;">${task.customerName || '-'}</td>
            <td style="border:1px solid #ccc;padding:8px;">
              <div style="background:#eee;height:20px;border-radius:10px;overflow:hidden;">
                <div style="background:${progressColor};height:100%;width:${task.progress}%;text-align:center;color:white;font-size:12px;line-height:20px;">${task.progress}%</div>
              </div>
            </td>
            <td style="border:1px solid #ccc;padding:8px;">${this.formatDate(task.start)}</td>
            <td style="border:1px solid #ccc;padding:8px;">${this.formatDate(task.end)}</td>
          </tr>
        `;
      });

      html += `
        </tbody>
        </table>
      `;

      container.innerHTML = html;
    },
    formatDate(date) {
      if (!date) return '-';
      const d = new Date(date);
      return d.toISOString().split('T')[0];
    }
  }
};
</script>

<style scoped>
.gantt-container {
  background: #fff;
  padding: 10px;
  border-radius: 4px;
}
</style>
