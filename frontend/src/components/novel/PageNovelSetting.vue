<template>
  <v-container>
    <v-row v-if="novelInfo">
      <v-col cols="12" md="4" lg="3"> 
        <!-- {{novelInfo.member}} -->
        <!-- {{getNovelInfo}} {{getNovelInfo.novelPk}} -->
        <v-row>
          <v-col cols="12">
            <div class="pic-uploader">
              <picture-input
                @change="onChange"
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
                :crop="true"
                :removable="true"
                :hideChangeButton="false"
                :custom-strings="{
                  upload: '소설 이미지를 등록하세요 +',
                  drag: '소설 이미지 등록 서비스 준비 중입니다 📕',
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
            <p class="write-info">생성일 | {{ novelInfo.novelUpdatedAt }}</p>
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
            <v-btn rounded @click="" class="gennovel-btn">저장</v-btn>
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

      bucketInfo: {
        albumBucketName: "episode-image",
        bucketRegion: "ap-northeast-2",
        IdentityPoolId: "ap-northeast-2:591d201c-0c7d-45ce-a2cf-987fcb38f9e2"
      },
      today: new Date().toLocaleDateString(),
      inputFile: null
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
    })
  },
  mounted() {},
  methods: {
    ...mapActions("storeGenNov", {
      postNovel: "postNovel",
      FETCH_FILE: "FETCH_FILE"
    }),
    onChange(image) { //이미지가 선택됨
      if (image) {  // 이미지가 로드됨
        this.image = image;
        this.inputFile = this.$refs.inputFile.file;
        this.FETCH_FILE(this.inputFile)
      } else {
        console.log("Fail to load picture💦");
      }
    },

    /* AWS서버 업로드 */
    uploadNovelImage(photoKey) {
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

      let path = 'novel/'
      let file = this.inputFile
      // let photoKey = memPk+'_'+time

      s3.upload(
        {
          Key: path + photoKey, // 파일경로와 파일명 지정
          Body: file,           // 업로드할 파일
          ACL: "public-read"
        },(err, data) => {
          if (err) {
            console.log(err)
            return;
          }
          // console.log("사진 업로드 성공", data)
        }
      );
    },
    genNovel() {
      if (this.check(this.novelInfo.novelName)) {
        let memPk = this.getSession.memPk
        this.novelInfo.memberPk = memPk
        
        if(this.inputFile==null) {
          this.novelInfo.novelImage = 'https://i.imgur.com/37mPPf6.png'
        }else {
          let time = new Date()
          let photoKey = memPk+'_'+time.getTime()
          this.uploadNovelImage(photoKey)
          this.novelInfo.novelImage = 'https://episode-image.s3.ap-northeast-2.amazonaws.com/novel/' + photoKey 
        }
        
        let data = this.novelInfo;
        this.postNovel(data)
      }
    },
    check(novelName) {
      if (novelName != "") return true;
      if (novelName == "") {
        alert("소설 제목을 입력해주세요");
        return;
      }
    },

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
.gennovel-btn {
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