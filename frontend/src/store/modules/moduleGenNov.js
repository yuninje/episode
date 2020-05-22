// 소설 생성 정보와 관련된 Vuex Store Module
import axios from "axios";

export default {
  namespaced: true,
  state: {},
  getters: {
  },
  mutations: {
  },
  actions: { 
    postNovel({ state, dispatch, commit, getters, rootGetters }, data) {
      axios
        .post(`${rootGetters.getServer}/api/novels`, data)
        .then(res => {
            console.log(res.data)
        })
        .catch(err => {
          console.error("postNovel()", err);
        });
    }
  }
};
