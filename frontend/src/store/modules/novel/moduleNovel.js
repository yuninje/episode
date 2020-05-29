// Member 정보와 관련된 Vuex Store Module
import axios from "axios";

export default {
  namespaced: true,
  modules: {

  },
  state: {
    novelPk: ""
  },
  getters: {
    getNovelPk: state => {
        return state.novelPk;
    },
  },
  mutations: {
    changeNovelPk(state, payload) {
        state.novelPk = payload;
      },
  },
  actions: {
    FETCH_NOVELPK({ commit }, data) {
        commit('changeNovelPk', data)
    }
  }
};
