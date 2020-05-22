import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueSession from 'vue-session'
import vuetify from './plugins/vuetify'

Vue.config.productionTip = false
Vue.use(VueSession, {persist: true})

// Vue.component('editor', require('./components/editor/Editor'));

new Vue({
  router,
  store,
  vuetify,
  
  beforeCreate(){ // 렌더링 직전
    this.$store.dispatch('checkSession');
  },
  render: h => h(App)
}).$mount('#app')
