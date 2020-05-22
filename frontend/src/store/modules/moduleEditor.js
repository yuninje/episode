// Editor 페이지 정보와 관련된 Vuex Store Module
import axios from "axios";

export default {
  namespaced: true,
  state: {
    episodepK:"",
    episodeInfo:"",
    importTitle:"",
    importContent:"",

    savingTime:null,
    savingTimeAuto:null,
    savingContent: ""
  },
  getters: {
    getEpisodepK: state => { return state.episodepK; },
    getEpisodeInfo: state => { return state.episodeInfo; },

    getImportTitle: state => { return state.importTitle; },
    getImportContent: state => { return state.importContent; },
    getSavingTime: state => { return state.savingTime; },
    getSavingTimeAuto: state => { return state.savingTimeAuto; },
    getSavingContent: state => { return state.savingContent; }
  },
  mutations: {
    changeEpisodepK(state, payload, rootState) {
      state.episodepK = payload;
    },
    changeEpisodeInfo(state, payload, rootState) {
      state.episodeInfo = payload;
    },

    changeImportTitle(state, payload, rootState) {
      state.importTitle = payload;
    },
    changeImportContent(state, payload, rootState) {
      state.importContent = payload;
    },
    changeSavingTime(state, payload, rootState) {
      state.savingTime = payload;
    },
    changeSavingTimeAuto(state, payload, rootState) {
      state.savingTimeAuto = payload;
    },
    changeSavingContent(state, payload, rootState) {
      state.savingContent = payload;
    }
  },
  actions: {
    // 에피소드의 pk를 알아낸다
    EpisodePk({ state, dispatch, commit, getters, rootGetters }, data) {
      commit("changeEpisodepK", data)
      localStorage.setItem("episodePk", data);

    },
    // 에피소드 1개의 정보를 조회
    getEpisodeByPk({ state, dispatch, commit, getters, rootGetters }, data) {
      axios
        .get(`${rootGetters.getServer}/api/episodes/${data}`)
        .then( res => {
          dispatch("EpisodePk", data)
          commit("changeEpisodeInfo", res.data.data)

          commit("changeImportTitle", res.data.data.episodeTitle)
          commit("changeImportContent", res.data.data.episodeContent)
        })
        .catch(err => {
          console.error("putEpisode()", err);
        });
    },
    // session 자동저장
    fetchAutoSave({ state, dispatch, commit, getters, rootGetters }, data) {
      console.log("fetchAutoSave 들어옴");
      // commit("changeSavingContent", "fetchAutoSave Data");
      localStorage.setItem("autoSaved", data);
      setTimeout(() => commit('changeSavingContent', data), 1000);
    },
    // 서버에 저장 
    putEpisode({ state, dispatch, commit, getters, rootGetters }, data) {
      let url = getters.getEpisodepK
      // console.log("putEpisode - data", data)
      // console.log("putEpisode - url", url)
      axios
        .put(`${rootGetters.getServer}/api/episodes/`+url, data)
        .then( res => {
          let time = new Date().toLocaleString()
          commit("changeSavingTimeAuto", null)
          commit("changeSavingTime", time)
        })
        .catch(err => {
          console.error("putEpisode()", err);
        });
    },
    putEpisodeAuto({ state, dispatch, commit, getters, rootGetters }, data) {
      let url = getters.getEpisodepK
      axios
        .put(`${rootGetters.getServer}/api/episodes/`+url, data)
        .then( res => {
          let time = new Date().toLocaleString()
          commit("changeSavingTime", null)
          commit("changeSavingTimeAuto", time)
        })
        .catch(err => {
          console.error("putEpisode()", err);
        });
    },
    
  } 
};
