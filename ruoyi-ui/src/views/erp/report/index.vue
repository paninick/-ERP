<template>
  <div class="app-container report-hub">
    <div class="hero">
      <div>
        <h2>报表中心</h2>
        <p>先把常用入口和核对路径收拢在一起，方便业务侧快速查看样衣、生产和工资数据。</p>
      </div>
    </div>

    <el-row :gutter="20">
      <el-col :xs="24" :sm="12" :lg="8" v-for="item in reportCards" :key="item.title">
        <el-card class="report-card" shadow="hover" @click.native="openRoute(item.path)">
          <div class="report-card__title">{{ item.title }}</div>
          <div class="report-card__desc">{{ item.desc }}</div>
          <div class="report-card__action">进入</div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="report-tip" shadow="never">
      <div slot="header">当前说明</div>
      <p>报表中心原先只有目录节点，没有任何子页面，所以用户侧看到的是空目录。</p>
      <p>这里先提供统一入口页，后续可以继续拆成经营总览、生产日报、工资月报等正式报表。</p>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "ReportHub",
  data() {
    return {
      reportCards: [
        { title: "打样总览", desc: "查看打样通知与样衣进度汇总。", path: "/erp/overview" },
        { title: "生产看板", desc: "查看生产计划、WIP 与员工排名。", path: "/erp/produceboard" },
        { title: "计件工资", desc: "查看月度工资汇总与工资明细。", path: "/erp/piecewage" },
        { title: "工资明细", desc: "直接进入明细表，便于对账与导出。", path: "/erp/piecewagedetail" }
      ]
    }
  },
  methods: {
    openRoute(path) {
      this.$router.push(path)
    }
  }
}
</script>

<style lang="scss" scoped>
.report-hub {
  .hero {
    margin-bottom: 20px;
    padding: 24px 28px;
    border-radius: 16px;
    background: linear-gradient(135deg, #f4f0e8 0%, #e6f1ea 100%);
  }

  h2 {
    margin: 0 0 8px;
    font-size: 28px;
    color: #20302b;
  }

  p {
    margin: 0;
    color: #52605b;
    line-height: 1.7;
  }
}

.report-card {
  margin-bottom: 20px;
  cursor: pointer;
  border-radius: 14px;

  &__title {
    margin-bottom: 10px;
    font-size: 18px;
    font-weight: 600;
    color: #22352f;
  }

  &__desc {
    min-height: 44px;
    color: #62706b;
    line-height: 1.6;
  }

  &__action {
    margin-top: 14px;
    color: #1e7a5a;
    font-weight: 600;
  }
}

.report-tip {
  margin-top: 8px;
}
</style>
