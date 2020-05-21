// Editor 페이지 정보와 관련된 Vuex Store Module
import axios from "axios";

export default {
  namespaced: true,
  state: {
    savedTime:null,
    savedContent: ""
  },
  getters: {
    getSavedTime: state => { return state.savedTime; },
    getSavedContent: state => { return state.savedContent; }
  },
  mutations: {
    changeSavedTime(state, payload, rootState) {
      state.savedTime = payload;
    },
    changeSavedContent(state, payload, rootState) {
      console.log("changeSavedContent 들어옴");
      state.savedContent = payload;
    }
  },
  actions: {
    // session 자동저장
    fetchAutoSave({ state, dispatch, commit, getters, rootGetters }, data) {
      console.log("fetchAutoSave 들어옴");
      // commit("changeSavedContent", "fetchAutoSave Data");
      localStorage.setItem("autoSaved", data);
      setTimeout(() => commit('changeSavedContent', data), 1000);
    },
    // 서버에 저장
    putEpisode({ state, dispatch, commit, getters, rootGetters }, data) {
      console.log(data)
      axios
        .put(`${rootGetters.getServer}/api/episodes/3`, data)
        .then( res => {
          console.log(res.data.data)

          let time = new Date().toLocaleString()
          commit("changeSavedTime", time)
        })
        .catch(err => {
          console.error("putEpisode()", err);
        });
    }
  } 
};
