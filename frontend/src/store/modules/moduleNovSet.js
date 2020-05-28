// NovelSetting 정보와 관련된 Vuex Store Module
import axios from "axios";
import router from "@/router/index";

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
    /** 소설 한 개의 정보를 조회 */
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
    },

    /** 소설 정보 업데이트 */
    putNovel({ state, dispatch, commit, getters, rootGetters }, data) {
      let novelPk = state.novelInfo.novelPk
      axios
        .put(`${rootGetters.getServer}/api/novels/${novelPk}`, data)
        .then(res => {
            if(res.data.state=="ok") {
              router.push({ name: "Mywork"})
            }
        })
        .catch(err => {
          console.error("postNovel()", err);
        });
    },

    /** 소설 삭제 */
    deleteNovel({ state, dispatch, commit, getters, rootGetters }, novelPk) {
      axios
        .delete(`${rootGetters.getServer}/api/novels/${novelPk}`)
        .then(res => {
            if(res.data.state=="ok") {
              router.replace({ name: "Mywork"})
            }
        })
        .catch(err => {
          console.error("postNovel()", err);
        });
    },
  }
};
