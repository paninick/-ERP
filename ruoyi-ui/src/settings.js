module.exports = {
  /**
   * 网页标题
   */
  title: process.env.VUE_APP_TITLE,

  /**
   * 侧边栏主题，深色主题 theme-dark，浅色主题 theme-light
   */
  sideTheme: 'theme-dark',

  /**
   * 系统布局配置
   */
  showSettings: true,

  /**
   * 菜单导航模式 1.左侧 2.混合 3.顶部
   */
  navType: 1,

  /**
   * 是否显示 tagsView
   */
  tagsView: true,

  /**
   * 是否持久化标签页
   */
  tagsViewPersist: false,

  /**
   * 是否显示标签图标
   */
  tagsIcon: false,

  /**
   * 是否固定顶部
   */
  fixedHeader: true,

  /**
   * 是否显示 logo
   */
  sidebarLogo: true,

  /**
   * 是否启用动态标题
   */
  dynamicTitle: false,

  /**
   * 是否显示底部版权
   */
  footerVisible: false,

  /**
   * 底部版权文本
   */
  footerContent: 'Copyright © 2018-2026 综合管理系统. All Rights Reserved.'
}
