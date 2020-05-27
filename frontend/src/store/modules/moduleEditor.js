// Editor 페이지 정보와 관련된 Vuex Store Module
import axios from "axios";
import router from "@/router/index";

export default {
  namespaced: true,
  state: {
    episodePk:"",
    episodeInfo:"",
    saveTime:{
      default: null,
      auto: null,
    },
    savingContent: "",
  },
  getters: {
    getEpisodePk: state => { return state.episodePk; },
    getEpisodeInfo: state => { return state.episodeInfo; },
    getSaveTime: state => { return state.saveTime; },
    
    getSavingContent: state => { return state.savingContent; },
  },
  mutations: {
    changeEpisodePk(state, payload, rootState) {
      state.episodePk = payload;
    },
    changeEpisodeInfo(state, payload, rootState) {
      state.episodeInfo = payload;
    },

    changeSaveTime(state, payload, rootState) {
      state.saveTime = payload;
    },
    changeSavingContent(state, payload, rootState) {
      state.savingContent = payload;
    },

  },
  actions: {

    // 에피소드의 pk를 로컬스토리지에 저장한다.
    storeEpisodePkLoc({ state, dispatch, commit, getters, rootGetters }, data) {
      localStorage.setItem("episodePk", data);
      commit("changeEpisodePk", data)
    },

    // 로컬 스토리지에 저장된 EpisodePk를 가져온다.
    fetchEpisodePk({ state, dispatch, commit, getters, rootGetters }) {
      let epk = localStorage.getItem("episodePk");
      commit("changeEpisodePk", epk)
    },

    // saveTime을 초기화한다.
    resetSaveTime({ state, dispatch, commit, getters, rootGetters }) {
      commit("changeSaveTime", { auto: null, default: null })
    },

    // 에피소드 1개의 정보를 조회
    getEpisodeByPk({ state, dispatch, commit, getters, rootGetters }, data) {
      axios
        .get(`${rootGetters.getServer}/api/episodes/${data}`)
        .then( res => {
          dispatch("fetchEpisodePk")
          commit("changeEpisodeInfo", res.data.data)
        })
        .catch(err => {
          console.error("getEpisodeByPk()", err);
        });
    },

    // session 자동저장
    fetchAutoSave({ state, dispatch, commit, getters, rootGetters }, data) {
      console.log("fetchAutoSave 들어옴");
      // commit("changeSavingContent", "fetchAutoSave Data");
      localStorage.setItem("autoSaved", data);
      setTimeout(() => commit('changeSavingContent', data), 1000);
    },

    // 에피소드 저장 (서버) 
    putEpisode({ state, dispatch, commit, getters, rootGetters }, data) {
      let epk = localStorage.getItem("episodePk");
      // console.log("putEpisode - data", data)
      // console.log("putEpisode - url", epk)
      axios
        .put(`${rootGetters.getServer}/api/episodes/`+epk, data)
        .then( res => {
          
          let time = new Date().toLocaleString()
          commit("changeSaveTime", null)
          commit("changeSaveTime", { auto: null, default : time })
        })
        .catch(err => {
          console.error("putEpisode()", err);
        });
    },
    putEpisodeAuto({ state, dispatch, commit, getters, rootGetters }, data) {
      let epk = localStorage.getItem("episodePk");
      axios
        .put(`${rootGetters.getServer}/api/episodes/`+epk, data)
        .then( res => {
          let time = new Date().toLocaleString()
          commit("changeSaveTime", { auto: time, default: null })
        })
        .catch(err => {
          console.error("putEpisode()", err);
        });
    },

    // 에피소드 생성
    postEpisode({ state, dispatch, commit, getters, rootGetters }, data) {
       axios
        .post(`${rootGetters.getServer}/api/episodes/`, data)
        .then( res => {
          if(res.data.state =="ok") {
            // 새로운 에피소드 생성 후 에디터 페이지로 이동
            let episodePk = res.data.data.episodePk
            dispatch("storeEpisodePkLoc", episodePk)
            router.push({name: 'Editor'});
          }
        })
        .catch(err => {
          console.error("postEpisode()", err);
        });
    },
    
  } 
};
