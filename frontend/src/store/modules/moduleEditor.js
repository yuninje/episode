// Editor 페이지 정보와 관련된 Vuex Store Module
import axios from "axios";

export default {
  namespaced: true,
  state: {
    savedContent: ""
  },
  getters: {
    getSavedContent: state => { return state.savedContent; }
  },
  mutations: {
    setSavedContent(state, payload, rootState) {
      console.log("setSavedContent 들어옴");
      state.savedContent = payload;
    }
  },
  actions: {
    // vuex에 자동저장
    fetchAutoSave({ state, dispatch, commit, getters, rootGetters }, data) {
      console.log("fetchAutoSave 들어옴");
      // commit("setSavedContent", "fetchAutoSave Data");
      localStorage.setItem("autoSaved", data);
      setTimeout(() => commit('setSavedContent', data), 1000);
    },
    // 서버에 저장
    fetchPostSave({ state, dispatch, commit, getters, rootGetters }, data) {
      axios
        .put(`http://localhost:8080/api/episodes` + 1, {
          "episodeContent": "수정되라 얍ㅜㅜ",
          "episodeTitle": "수정이 왜 안되니 ㅠㅠㅠ",
          "episodeWriter": "string"
        })
        .then( res => {
          console.log(red.data.data)
        })
        .catch(err => {
          console.error("fetchPostSave()", err);
        });
    }
  } 
};
