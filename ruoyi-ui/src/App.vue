<template>
  <div id="app">
    <router-view />
    <theme-picker />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker"

export default {
  name: "App",
  components: { ThemePicker },
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  },
  created() {
    const themeStyle = this.$store.state.settings.themeStyle;
    if (themeStyle && themeStyle !== 'default') {
      document.body.className = `theme-${themeStyle}`;
    }
  }
}
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
