import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueSession from 'vue-session'
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false
Vue.use(VueSession, {persist: true})

new Vue({
  router,
  store,
  beforeCreate(){
    this.$store.dispatch('checkSession');
  },
  vuetify,
  render: h => h(App)
}).$mount('#app')
