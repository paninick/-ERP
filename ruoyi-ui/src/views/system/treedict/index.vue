<template>
  <div class="app-container treedict-placeholder">
    <el-card shadow="never">
      <div slot="header" class="treedict-placeholder__header">
        <span>级联字典管理</span>
      </div>
      <el-empty description="该页面尚未完成接入，当前先保留菜单入口，避免动态路由加载失败。">
        <el-button type="primary" size="mini" @click="$router.push('/system/dict')">前往字典管理</el-button>
      </el-empty>
      <div class="treedict-placeholder__tip">
        如需正式启用级联字典，可在后续补充对应页面与接口实现。
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "TreeDict"
}
</script>

<style scoped>
.treedict-placeholder__header {
  font-weight: 600;
}

.treedict-placeholder__tip {
  margin-top: 16px;
  text-align: center;
  color: #909399;
  font-size: 13px;
}
</style>
