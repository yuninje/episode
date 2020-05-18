import Vue from 'vue'
import VueRouter from 'vue-router'
import store from '../store';

import Main from '../components/main/PageMain'
import Signup from '../components/member/PageSignUp'
import Signin from '../components/member/PageSignIn'
import Editor from '../components/editor/PageEditor'
import TestEditor1 from '../components/editor/TestEditor1'
import TestTest from '../components/editor/TestTest'

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
    path: '/testeditor1',
    name: 'TestEditor1',
    component: TestEditor1
  },
  {
    path: '/testtest',
    name: 'TestTest',
    component: TestTest
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
