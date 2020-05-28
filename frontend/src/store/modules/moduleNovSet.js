// NovelSetting 정보와 관련된 Vuex Store Module
import axios from "axios";

export default {
  namespaced: true,
  state: {
    novelInfo: ""
  },
  getters: {
    getNovelInfo: state => {
      return state.novelInfo;
    }
  },
  mutations: {
    changeNovelInfo(state, payload) {
        state.novelInfo = payload;
      },
  },
  actions: {
    /** 소설 업로드 */
    getNovelInfo({ state, dispatch, commit, getters, rootGetters }, novelPk) {
      axios
        .get(`${rootGetters.getServer}/api/novels/${novelPk}`)
        .then(res => {
          if (res.data.state == "ok") {
            console.log(res.data.data);
            commit('changeNovelInfo', res.data.data);
          }
        })
        .catch(err => {
          console.error("getNovelInfo()", err);
        });
    }
  }
};
