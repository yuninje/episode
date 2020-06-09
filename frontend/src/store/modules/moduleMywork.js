// Mywork 정보와 관련된 Vuex Store Module
import axios from "axios";

export default {
  namespaced: true,
  state: {
    novAllList: "", // 전체 작품
    novPubList: "", // 연재 중인 작품
    novWriList: ""  // 작성 중인 작품
  },
  getters: {
    // (state, getters, rootState, rootGetters)
    getNovAllList: state => {
      return state.novAllList;
    },
    getNovPubList: state => {
      return state.novPubList;
    },
    getNovWriList: state => {
      return state.novWriList;
    },
  },
  mutations: {
    // (state, payload, rootState?)
    changeNovAllList(state, payload) {
      state.novAllList = payload;
    },
    changeNovPubList(state, payload) {
      state.novPubList = payload;
    },
    changeNovWriList(state, payload) {
      state.novWriList = payload;
    },
  },
  actions: { // ({ dispatch, commit, getters, rootGetters }, data, ...)
    // 전체 소설 목록
    getNovelByMemPk({ state, dispatch, commit, getters, rootGetters }) {
      axios
        .get(`${rootGetters.getServer}/api/novels/member-pk=${rootGetters.getSession.memPk}?size=30&sort=updated` )
        .then( res => {
          let content = res.data.data.content
          let pubList = []
          let wriList = []
          
          // // novelStatus [1: 완결, 0: 연재중, -1: 휴재]
          for(let i=0; i<content.length; ++i) {
            if ( content[i].novelStatus == 1 )      continue;                   // 완결  : 전체 작품
            else if( content[i].novelStatus == 0 )  pubList.push(content[i])    // 연재중 : 연재 중 작품
            else if( content[i].novelStatus == -1 ) wriList.push(content[i])    // 휴재  :  작성 중 작품  임의
          }
          
          commit('changeNovAllList', content);
          commit('changeNovPubList', pubList);
          commit('changeNovWriList', wriList);
          
        })
        .catch(err => {
          console.error("getNovelByMemPk()", err);
        })
    },

    // 연재 중 작품
    getNovelPublish({ state, dispatch, commit, getters, rootGetters }) {
      axios
        .get(`${rootGetters.getServer}/api/novels/member-pk=${rootGetters.getSession.memPk}?sort=updated` )
        .then( res => {
          let content = res.data.data.content
          commit('changeNovPubList', content);
        })
        .catch(err => {
          console.error("getNovelPublish()", err);
        })
    },
    // 작성 중 작품
    getNovelPublish({ state, dispatch, commit, getters, rootGetters }) {
      axios
        .get(`${rootGetters.getServer}/api/novels/member-pk=${rootGetters.getSession.memPk}?sort=updated` )
        .then( res => {
          let content = res.data.data.content
          commit('changeNovWriList', content);
        })
        .catch(err => {
          console.error("getNovelPublish()", err);
        })
    },

    
  }
};
