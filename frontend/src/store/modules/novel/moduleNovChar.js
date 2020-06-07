// Member 정보와 관련된 Vuex Store Module
import axios from "axios";

export default {
  namespaced: true,
  modules: {},
  state: {
    novelPk:"",
    characterInfo: [],
    relationInfo : [],
  },
  getters: {
    getCharacterInfo: state => {
      return state.characterInfo;
    },
    getRelationInfo: state => {
      return state.relationInfo;
    },
  },
  mutations: {
    changeNovelPk(state, payload) {
      state.novelPk = payload;
    },
    changeCharacterInfo(state, payload) {
      state.characterInfo = payload;
    },
    changeRelationInfo(state, payload) {
      state.relationInfo = payload;
    },
  },
  actions: {
    /** novelPk로 해당 소설의 캐릭터 정보를 가져온다 */
    FETCH_CHARACTER_INFO({ commit, rootGetters }, novelPk) {
      commit("changeNovelPk",novelPk)
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
    },
    /** novelPk로 해당 소설의 relation 정보를 가져온다 */
    FETCH_RELATION_INFO({ commit, rootGetters }, novelPk) {
      console.log("FETCH_RELATION_INFO() 들어옴")
      axios
        .get(`${rootGetters.getServer}/api/relations/novelPk=${novelPk}`)
        .then(res => {
          if (res.data.state == "ok") {
            commit("changeRelationInfo", res.data.data);
          }
        })
        .catch(err => {
          console.error("FETCH_CHARACTER_INFO()", err);
        });
    },
    /** postRelation api를 호출하는 메서드 */
    postRelation({ state, dispatch, commit, getters, rootGetters }, data) {
      axios
        .post(`${rootGetters.getServer}/api/relations`, data)
        .then(res => {
            if(res.data.state=="ok") {
              // 관계 업데이트
              dispatch("FETCH_RELATION_INFO", state.novelPk)
            }
        })
        .catch(err => {
          console.error("postNovel()", err);
        });
    },
  }
};
