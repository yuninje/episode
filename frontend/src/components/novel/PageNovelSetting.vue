<template>
  <v-container v-if="novelInfo" >
    <v-row v-if="checkAuth(this.novelInfo.member.memPk)"> 
      <v-col cols="12" md="4" lg="3"> 
        <!-- {{novelInfo.member}} -->
        <!-- {{getNovelInfo}} {{getNovelInfo.novelPk}} -->
        <v-row>
          <v-col cols="12">
            <div class="pic-uploader">
              <picture-input
                @change="onChange"
                @remove="onRemove"
                ref="inputFile"
                button-class="btn"
                buttonClass="pic-ch-btn"
                removeButtonClass="pic-rem-btn"
                width="700"
                height="1000"
                margin="16"
                accept="image/jpeg, image/png"
                size="10"
                radius="6"
                :prefill="onPrefill()"
                :crop="true"
                :removable="true"
                :hideChangeButton="false"
                :custom-strings="{
                  upload: '소설 이미지를 등록하세요 +',
                  drag: '소설 이미지를 등록하세요 📕',
                  change: '이미지 수정  | ',
                  remove: '삭제'
                }"
              ></picture-input>
            </div>
            <!-- <v-img
              :src = "require(`@/assets/images/banner0.png`)"
              aspect-ratio=0.7
            ></v-img>-->
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12" md="8" lg="9">
        <v-row>
          <v-col cols="12">
            <p class="write-info">작가 | {{ novelInfo.member.memId }}</p>
            <p class="write-info">생성일 | {{ novelInfo.novelUpdatedAt.substr(0,10) }}</p>
          </v-col>
          <v-col cols="12" sm="5">
            <p class="sub-title">제목</p>
            <v-text-field
              v-model="novelInfo.novelName"
              placeholder="소설의 제목를 입력하세요."
              solo
              flat
              clearable
              rows="1"
              row-height="15"
            ></v-text-field>
            <p class="write-info"></p>
          </v-col>
          <v-col cols="12">
            <p class="sub-title">작품 소개</p>
            <v-textarea
              v-model="novelInfo.novelIntro"
              placeholder="작품소개를 입력하세요."
              solo
              flat
              clearable
              rows="4"
              row-height="15"
            ></v-textarea>
            <p class="write-info"></p>
            <v-btn  rounded @click="clickUpdateNovel()" class="set-save-btn">저장</v-btn>
            <v-btn rounded @click="clickDeleteNovel()" class="set-save-btn">이 소설을 삭제하겠습니다.</v-btn>
          </v-col>
          <v-col cols="12">
            <br>
            <!-- <p class="tags" v-show="data.novel.hashTags.length !== 0"></p> -->
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import http from "../../http-common";
import PictureInput from "vue-picture-input";
import AWS from "aws-sdk";
import { mapActions, mapMutations, mapGetters } from "vuex";

export default {
  data() {
    return {
      updateInfo: {
        novelName: "", // o
        novelImage: "", // o
        novelIntro: "", // o
        novelStatus: 0, // x
        novelLimit: true, // x
        novelOnly: true, // x
        novelOpen: true, // x
        genrePks: [3], //
        hashTagStrs: [] //
      },

      bucketInfo: {
        albumBucketName: "episode-image",
        bucketRegion: "ap-northeast-2",
        IdentityPoolId: "ap-northeast-2:591d201c-0c7d-45ce-a2cf-987fcb38f9e2"
      },
      today: new Date().toLocaleDateString(),
      inputFile: null,
      inputStatus:0,  // -1: 삭제, 1: 새로운 사진, 0 변화 없음
    };
  },
  components: {
    PictureInput
  },
  beforeCreate() {
    this.novelPk = this.$route.params.novelPk
    this.$store.dispatch(`storeNovSet/getNovelInfo`, this.novelPk );
  },
  created() {
    
  },
  computed: {
    ...mapGetters(["getSession"]),
    ...mapGetters("storeNovSet", {
      novelInfo: "getNovelInfo",
    }),
  },
  mounted() {},
  methods: {
    ...mapActions("storeGenNov", {
      postNovel: "postNovel",
    }),
    ...mapActions("storeNovSet", {
      putNovel: "putNovel",
      deleteNovel: "deleteNovel",
    }),
    onChange(image) { //이미지가 선택됨
      if (image) {  // 이미지가 로드됨
        this.image = image;
        this.inputFile = this.$refs.inputFile.file;
        this.inputStatus = 1
      }else {
        console.log("Fail to load picture💦");
      }
    },
    onRemove() {
      this.image = ''
      this.inputStatus = -1
    },
    onPrefill() {
      if(this.novelInfo.novelImage) {
        return this.novelInfo.novelImage
      }
    },

    /** S3 이미지 업로드 */
    uploadNovelImage(path, photoKey, ext) {
      AWS.config.update({
        region: this.bucketInfo.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.bucketInfo.IdentityPoolId
        })
      });

      const s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: { Bucket: this.bucketInfo.albumBucketName }
      });

      let file = this.inputFile

      s3.upload(
        {
          Key: path + photoKey + ext, // 파일경로와 파일명 지정
          Body: file,           // 업로드할 파일
          ACL: "public-read"
        },(err, data) => {
          if (err) {
            console.log(err)
            return;
          }
          console.log("사진 업로드 성공", data)
        }
      );
    },
    /** S3 이미지 삭제 */
    deleteNovelImage() {
      let path = 'novel/'
      const fileName = this.getFileName()

      AWS.config.update({
        region: this.bucketInfo.bucketRegion,
        credentials: new AWS.CognitoIdentityCredentials({
          IdentityPoolId: this.bucketInfo.IdentityPoolId
        })
      });

      const s3 = new AWS.S3({
        apiVersion: "2006-03-01",
        params: { Bucket: this.bucketInfo.albumBucketName }
      });

      s3.deleteObject({ 
        Key: path + fileName 
        },(err, data) => {
        if (err) {
          console.log(err)
          return alert("There was an error deleting your photo: ", err.message);
        }
      });

    },
    getFileName() {
      let imagePath = this.novelInfo.novelImage
      let lastSlash = imagePath.lastIndexOf('/')
      let fileName = imagePath.substr(lastSlash + 1); 
      return fileName
    },

    /** 소설 정보 업데이트 */
    clickUpdateNovel() {
      // 권한 확인
      const author = this.novelInfo.member.memPk;
      if (!this.checkAuth(author)) return;

      if (this.check(this.novelInfo.novelName)) {
        this.imagehandler(this.inputStatus)
        let novelImage = this.updateInfo.novelImage
        let {
          novelName,
          novelIntro,
          novelStatus,
          novelLimit,
          novelOnly,
          novelOpen,
        } = this.novelInfo
        let genrePks = [ this.novelInfo.genres[0].genrePk  ]
        let hashTagStrs = []
        let data = {
          novelName,
          novelImage,
          novelIntro,
          novelStatus,
          novelLimit,
          novelOnly,
          novelOpen,
          genrePks,
          hashTagStrs
        };
        this.putNovel(data)
      }
    },
    /** 소설 삭제 */
    clickDeleteNovel() {
      // 권한 확인
      const author = this.novelInfo.member.memPk;
      if (!this.checkAuth(author)) return;

      const result = confirm("⚠️ 정말 이 소설을 삭제하시겠습니까? \n이 작업은 되돌릴 수 없습니다.")
      if(result) {
        const novelPk = this.novelInfo.novelPk
        this.deleteNovel(novelPk)
        this.deleteNovelImage()
      }else {
        return;
      }
    },
    imagehandler(status) {    // status: 0: 변경 없음, -1: 이미지 삭제, 1: 이미지 변경
      if( status == 0 ) {
        this.updateInfo.novelImage = this.novelInfo.novelImage
      }else{
        // 기존 이미지 삭제
        this.deleteNovelImage()

        if( status < 0 ) {
          this.updateInfo.novelImage = 'https://i.imgur.com/37mPPf6.png'
        }else if( status > 0) { 
          // 새로운 이미지 저장
          let path = 'novel/'
          let time = new Date()
          let memPk = this.novelInfo.member.memPk
          let photoKey = memPk+'_'+time.getTime()
          let ext='.jpg'

          this.uploadNovelImage(path, photoKey, ext)
          this.updateInfo.novelImage = 'https://episode-image.s3.ap-northeast-2.amazonaws.com/' + path + photoKey + ext
        }
        this.inputStatus=0;
      }
    },
    check(novelName) {
      if (novelName != "") return true;
      if (novelName == "") {
        alert("소설 제목을 입력해주세요");
        return;
      }
    },
    /** 소설 수정 및 삭제 권한 확인 */
    checkAuth(author) {
      if(author == this.getSession.memPk) {
        return true;
      }else {
        this.$router.replace("/")
        alert("접근 권한이 없습니다.")
        return false;
      }
    }

  }
};
</script>

<style lang="scss" scoped>
.tags {
  color: rgba(192, 0, 0, 1);
}
.sub-title {
  font-size: 1.5rem;
  font-weight: bold;
}
.like {
  text-align: right;
}
.container {
  max-width: 1300px;
}
.write-info {
  font-size: 1.2rem;
  font-weight: 500;
}
.set-save-btn {
  float: right;
  margin-right:12px;
  -webkit-transition: all 0.1s;
  -moz-transition: all 0.1s;
  -o-transition: all 0.1s;
  transition: all 0.1s;
  transition: all 0.1s;
  &:hover {
      color: #fff;
      box-shadow: 300px 0 0 0 rgb(192, 0, 0) inset;
      border: 0;
      outline: 0;
      font-weight: bold;
    }
}
</style>