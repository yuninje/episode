import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
import router from "@/router/index";

import moduleMember from "./modules/moduleMember"
import moduleEditor from "./modules/moduleEditor"
import moduleMywork from "./modules/moduleMywork"
import moduleNovGen from "./modules/moduleNovGen"
import moduleNovSet from "./modules/moduleNovSet"
import moduleNovChar from "./modules/novel/moduleNovChar"
import moduleNovel from "./modules/novel/moduleNovel"

Vue.use(Vuex);

export default new Vuex.Store({
  modules: {
    storeMember: moduleMember,
    storeEditor: moduleEditor,
    storeMywork: moduleMywork,
    storeNovGen: moduleNovGen,
    storeNovSet: moduleNovSet,
    storeNovChar : moduleNovChar,
    storeNovel : moduleNovel
  },
  state: {
    server: "http://k02a2061.p.ssafy.io:8080",
    isLogin: false,
    session: ""
  },
  getters: {
    getServer: state => {
      return state.server;
    },
    getIsLogin: state => {
      return state.isLogin;
    },
    getSession: state => {
      return state.session;
    }
  },
  mutations: {
    changeSession(state, payload) {
      state.session = payload;
      state.isLogin = true;
    },
    changeLogout(state) {
      state.isLogin = false;
      state.session = "";
      localStorage.removeItem('savedSession');
    },
  },
  actions: {
    // 로그아웃
    signout({ state, dispatch, commit, getters, rootGetters }) {
      // dispatch("deleteSession");
      commit("changeLogout");
    },

    // 로그인
    postSignin({ state, dispatch, commit, getters, rootGetters }, data) {
      axios
        .post(`${getters.getServer}/api/members/login`, data)
        .then(res => {
          let session = res.data.data;
          
          // 세션 정보 저장
          // this._vm.$session.set("login_info", { id: session.mem_id });

          // 로컬 스토리지에 저장..
          localStorage.setItem("savedSession", JSON.stringify(session));

          dispatch("checkSession")

          if (res.data.state == "ok") {
            router.replace("/");
          } else {
            alert("아이디와 비밀번호가 일치하지 않습니다.");
          }
        })
        .catch(err => {
          console.error("postSignin()", err);
        });
    },

    checkSession({ state, dispatch, commit, getters, rootGetters }, callBack) {
      let session = localStorage.getItem('savedSession');
      if(session!=null) {
        commit('changeSession', JSON.parse(session));
      }else {
        dispatch('signout')
      }
      // // 세션 값이 있을 경우
      // if(this._vm.$session.exists()) {
      //   // 세션이 유효한지 체크
      //   // console.log(this._vm.$session.id())
        
      //   // 시간이 지났을 경우
      //   // dispatch("deleteSession")
      //   // alert('로그인 세션이 만료되었습니다.\n다시 로그인해주세요')
      //   // router.push("/signin");
      //   return false;
      // }else { // 세션 값이 없을 경우
      //   // alert('로그인해야 이용 가능합니다.')
      //   // router.push("/signin");
      //   return false;
      // }
    },
    refreshSession({ state, dispatch, commit, getters, rootGetters }, callBack) {
      // 세션이 만료되기 전에 세션을 refresh함
    },
    deleteSession({ state, dispatch, commit, getters, rootGetters }, callBack) {
      // 시간이 지났거나 로그아웃하면 세션 만료
      this._vm.$session.destroy()
    }
  },
});
