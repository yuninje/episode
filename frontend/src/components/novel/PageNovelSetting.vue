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
      <v-col cols="12">
        <span>소설 설정</span>
        <hr/>
      </v-col>
      <v-col cols="12">
        <v-row>
          <v-col cols="2"
            v-for="(button, i) in buttons"
            :key = i
          >
            <v-card
              color="rgba(255,83,83,1)"
              class="d-flex justify-center align-center"
              height="150px"
              outlined
              @click="selectButtons(i)"
            >
              <p class="sub-title">{{button}}</p>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12">
        <v-row class="rectangle-outlined">
          <!-- 기존 등록된 캐릭터 카드 -->
          <v-col 
            cols="3" 
            v-for="(card, i) in cards"
            :key=i
          >
            <v-card
              :color="card.color"
            >
              <v-row>
                <v-col cols="8">
                  <v-list-item
                    color="rgba(0,0,0,0.4)"
                    dark
                  >
                    <v-list-item-content>
                      <v-list-item-title class="title">{{card.name}}</v-list-item-title>
                      <v-list-item-subtitle>나이 : {{card.age}}세</v-list-item-subtitle>
                      <v-list-item-subtitle>직업 : {{card.job}}</v-list-item-subtitle>
                      <v-list-item-subtitle>역할 : {{card.role}}</v-list-item-subtitle>
                      <v-list-item-subtitle>특이사항 : {{card.special}}</v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </v-col>
                <v-col cols="4">
                  <v-avatar tile size="100%" class="px-2">
                    <v-img
                      :src="card.img"
                    ></v-img>
                  </v-avatar>
                </v-col>
              </v-row>
            </v-card>
          </v-col>
          <!-- 새로운 캐릭터 생성 카드 -->
          <v-col cols="3">
            <v-card
              color="rgba(242, 242, 242, 1)"
              class="d-flex justify-center align-center"
              height="152"
              @click="createCharacter()"
            >
              <v-card-title><v-icon>mdi mdi-plus</v-icon></v-card-title>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>

    <!-- 캐릭터 등록 페이지 -->
    <v-dialog v-model="dialog" persistent max-width="400px">
      <v-card>
        <v-card-title class="text-center">
          <v-spacer></v-spacer>
          캐릭터 등록
          <v-spacer></v-spacer>
        </v-card-title>
        <v-card-text>
          <v-container>
            <v-row>
              <v-col cols="12">
                <v-img 
                  height="200" 
                  src="@/assets/images/upload.png"
                  @click=""
                ></v-img>
              </v-col>
              <v-col cols="12">
                <v-text-field label="이름" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="직업" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="역할" required></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field label="특이사항" required></v-text-field>
              </v-col>
            </v-row>
          </v-container>
        </v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="rgba(192,0,0,1)" text @click="dialog=false">create</v-btn>
          <v-btn color="rgba(192,0,0,1)" text @click="dialog=false">close</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

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
      inputFile: null,
      buttons: [
        "캐릭터",
        "세계관",
        "인물관계",
        "사건",
        "배경지식",
        "+"
      ],
      dialog: false,
      cards: [
        {
          img: "https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F2345743655F2DD2A08",
          name:"치즈",
          age:1,
          job:"주인",
          role:"놀고 먹는 것",
          special: "잠이 많음",
          color:"pink"
        },
        {
          img:"https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcRvXvViz3ZxXneCFTsSC6bnkIgu-ZT29fIGl1C-nqx07E14SPZk&usqp=CAU",
          name:"비글",
          age:1,
          job:"경비",
          role:"집 지키는 것",
          special: "3대 악마견",
          color:"green"
        },
        {
          img:"https://img1.daumcdn.net/thumb/R800x0/?scode=mtistory2&fname=https%3A%2F%2Ft1.daumcdn.net%2Fcfile%2Ftistory%2F27683E3B526DDFD620",
          name:"리트리버",
          age:2,
          job:"경비",
          role:"집 지키는 것",
          special: "사람 친화적",
          color:"grey"
        },
        {
          img:"https://i.pinimg.com/736x/67/7e/db/677edbdb9e16f4f95adb302fa508fa3b.jpg",
          name:"고등어",
          age:2,
          job:"주인",
          role:"뛰어 다니는 것",
          special: "말 안들음",
          color:"indigo"
        },
        {
          img:"https://scontent-gmp1-1.cdninstagram.com/v/t51.2885-15/e35/16584031_1846523242287591_6149872609245790208_n.jpg?_nc_ht=scontent-gmp1-1.cdninstagram.com&_nc_cat=103&_nc_ohc=JuXCIvXTWVsAX-iNib7&oh=96aaa7045ddb7a650773af098133def5&oe=5EF81923",
          name:"순무",
          age:4,
          job:"분노고양이",
          role:"화내는 것",
          special: "순무처럼 생김",
          color:"blue"
        },
      ],
      selectedButton: '0'
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
    selectButtons(index) {
      this.selectedButton = index;
      // console.log("선택된 버튼은 " + this.selectedButton +"번째 버튼입니다!!!");
    },
    createCharacter() {
      this.dialog = true;
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
.rectangle-outlined {
  border: 1px solid;
  border-color: rgba(255,83,83,1);
}
</style>