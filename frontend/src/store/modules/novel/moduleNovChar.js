// Member 정보와 관련된 Vuex Store Module
import axios from "axios";

export default {
  namespaced: true,
  modules: {},
  state: {
    characterInfo: []
  },
  getters: {
    getCharacterInfo: state => {
      return state.characterInfo;
    }
  },
  mutations: {
    changeCharacterInfo(state, payload) {
      state.characterInfo = payload;
    }
  },
  actions: {
    FETCH_CHARACTER_INFO({ commit, rootGetters }, novelPk) {
      console.log("FETCH_CHARACTER_INFO() 들어옴")
      axios
        .get(`${rootGetters.getServer}/api/characters/novelPk=${novelPk}`)
        .then(res => {
          if (res.data.state == "ok") {
            commit("changeCharacterInfo", res.data.data);
            console.log(res.data.data)
          }
        })
        .catch(err => {
          console.error("FETCH_CHARACTER_INFO()", err);
        });
    }
  }
};
