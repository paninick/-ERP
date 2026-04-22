import Vue from 'vue'
import VueI18n from 'vue-i18n'
import Cookies from 'js-cookie'

// 导入 Element-UI 的多语言包
import elementEnLocale from 'element-ui/lib/locale/lang/en'
import elementZhLocale from 'element-ui/lib/locale/lang/zh-CN'
import elementJaLocale from 'element-ui/lib/locale/lang/ja'

// 导入自定义的多语言词典
import enLocale from './en-US'
import zhLocale from './zh-CN'
import jaLocale from './ja-JP'

Vue.use(VueI18n)

const messages = {
  'en-US': {
    ...enLocale,
    ...elementEnLocale
  },
  'zh-CN': {
    ...zhLocale,
    ...elementZhLocale
  },
  'ja-JP': {
    ...jaLocale,
    ...elementJaLocale
  }
}

export function getLanguage() {
  const chooseLanguage = Cookies.get('language')
  if (chooseLanguage) return chooseLanguage

  // 如果没有选择语言，则根据浏览器语言自动设置
  const language = (navigator.language || navigator.browserLanguage).toLowerCase()
  const locales = Object.keys(messages)
  for (const locale of locales) {
    if (language.indexOf(locale.toLowerCase()) > -1) {
      return locale
    }
  }
  return 'zh-CN'
}

const i18n = new VueI18n({
  locale: getLanguage(),
  messages,
  silentTranslationWarn: true
})

export default i18n
