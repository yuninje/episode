// 소설 생성 정보와 관련된 Vuex Store Module
import axios from "axios";
import router from "@/router/index";

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
            if(res.data.state=="ok") {
              router.push("/mywork");
            }
        })
        .catch(err => {
          console.error("postNovel()", err);
        });
    }
  }
};
