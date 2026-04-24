<template>
  <div class="app-container report-hub">
    <div class="hero">
      <div>
        <h2>{{ $t('reportHub.title') }}</h2>
        <p>{{ $t('reportHub.desc') }}</p>
      </div>
    </div>

    <el-row :gutter="20">
      <el-col :xs="24" :sm="12" :lg="8" v-for="item in reportCards" :key="item.titleKey">
        <el-card class="report-card" shadow="hover" @click.native="openRoute(item.path)">
          <div class="report-card__title">{{ $t(item.titleKey) }}</div>
          <div class="report-card__desc">{{ $t(item.descKey) }}</div>
          <div class="report-card__action">{{ $t('reportHub.enter') }}</div>
        </el-card>
      </el-col>
    </el-row>

    <el-card class="report-tip" shadow="never">
      <div slot="header">{{ $t('reportHub.tipTitle') }}</div>
      <p>{{ $t('reportHub.tipContent1') }}</p>
      <p>{{ $t('reportHub.tipContent2') }}</p>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "ReportHub",
  data() {
    return {
      reportCards: [
        { titleKey: 'reportHub.cardOverview', descKey: 'reportHub.cardOverviewDesc', path: '/erp/overview' },
        { titleKey: 'reportHub.cardProduceboard', descKey: 'reportHub.cardProduceboardDesc', path: '/erp/produceboard' },
        { titleKey: 'reportHub.cardPiecewage', descKey: 'reportHub.cardPiecewageDesc', path: '/erp/piecewage' },
        { titleKey: 'reportHub.cardPiecewagedetail', descKey: 'reportHub.cardPiecewagedetailDesc', path: '/erp/piecewagedetail' }
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
