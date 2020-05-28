// 소설 생성 정보와 관련된 Vuex Store Module
import axios from "axios";
import router from "@/router/index";

export default {
  namespaced: true,
  state: {
    novelPk:"",
    novelImgfile:null,
  },
  getters: {
    getNovelPk: state => {
      return state.novelPk;
    },
    getNovelImgFile: state => {
      return state.novelImgfile;
    },
  },
  mutations: {
    changeNovelPk(state, payload) {
      state.novelPk = payload;
    },
    changeNovelImgFile(state, payload) {
      state.novelImgfile = payload;
    },
  },
  actions: { 
    /** 업로드할 파일 fetch */
    FETCH_FILE({ commit },data) {
      commit('changeNovelImgFile', data)
    },
    /** 소설 업로드 */
    postNovel({ state, dispatch, commit, getters, rootGetters }, data) {
      axios
        .post(`${rootGetters.getServer}/api/novels`, data)
        .then(res => {
            if(res.data.state=="ok") {
              let novelPk = res.data.data.novelPk
              // dispatch("uploadNovelImage", novelPk)
              router.push("/mywork");
            }
        })
        .catch(err => {
          console.error("postNovel()", err);
        });
    },
    /** 이미지업로드(by novelPk) */
    uploadNovelImage({ state, dispatch, commit, getters, rootGetters }, novelPk) {
      let bucketInfo= {
        albumBucketName: "episode-image",
        bucketRegion: "ap-northeast-2",
        IdentityPoolId: "ap-northeast-2:591d201c-0c7d-45ce-a2cf-987fcb38f9e2"
      }

      AWS.config.update({
        region: bucketInfo.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: bucketInfo.IdentityPoolId
        })
      });

      const s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: { Bucket: bucketInfo.albumBucketName }
      });

      let path = "novel/"
      let photoKey = novelPk
      let file = getters.getNovelImgFile

      s3.upload(
        {
          Key: path + photoKey,    // 파일경로와 파일명 지정
          Body: file,           // 업로드할 파일
          ACL: "public-read"
        },(err, data) => {
          if (err) {
            return console.log(err)
          }
          console.log(data)
        }
      );

      // https://episode-image.s3.ap-northeast-2.amazonaws.com/novel/${novelPk}
    }
  }
};
