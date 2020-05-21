import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store';

import Main from '../components/main/PageMain'
import Signup from '../components/member/PageSignUp'
import Signin from '../components/member/PageSignIn'
import Editor from '../components/editor/PageEditor'
import Mywork from '../components/work/PageMyWork'

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    name: 'Main',
    component: Main
  },
  {
    path: '/signup',
    name: 'Signup',
    component: Signup
  },
  {
    path: '/signin',
    name: 'Signin',
    component: Signin, 
    beforeEnter : rejectAuthUser

  },
  {
    path: '/editor',
    name: 'Editor',
    component: Editor
  },
  {
    path: '/mywork',
    name: 'mywork',
    component: Mywork
  },


]

function rejectAuthUser (to, from, next) {
  store.dispatch('checkSession');
  if(store.state.isLogin) {
    // 이미 로그인 한 유저 가드
    alert("이미 로그인 했습니다.")
    next("/")
  }else {
    next()
  }
}

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
