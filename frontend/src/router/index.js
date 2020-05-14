import Vue from 'vue'
import VueRouter from 'vue-router'
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
    component: Signin
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

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
