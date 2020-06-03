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
                @change="onChangeCharacter"
                @remove="onRemoveCharacter"
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
              rows="4"
              row-height="15"
            ></v-textarea>
            <p class="write-info"></p>
            <!-- <v-btn  rounded @click="clickUpdateNovel()" class="set-save-btn">저장</v-btn> -->
            <!-- <v-btn rounded @click="clickDeleteNovel()" class="set-save-btn">이 소설을 삭제하겠습니다.</v-btn> -->
          </v-col>
          <v-col cols="12">
            <br>
            <!-- <p class="tags" v-show="data.novel.hashTags.length !== 0"></p> -->
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12">
        <span>소설 설정 (아직 준비중인 페이지입니다.)</span>
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
              <p class="sub-title-white">{{button}}</p>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12">
        <v-row class="rectangle-outlined" v-show="checkButtons(0)">
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
        <v-row class="rectangle-outlined" v-show="checkButtons(1)">
          <v-col cols="12">
            <v-textarea
              auto-grow
              flat
              solo
            ></v-textarea>
          </v-col>
        </v-row>
        <v-row class="rectangle-outlined" v-show="checkButtons(2)">
          <v-col cols="12">
            <v-textarea
              auto-grow
              flat
              solo
            ></v-textarea>
          </v-col>
        </v-row>
        <v-row class="rectangle-outlined" v-show="checkButtons(3)">
          <v-col cols="12">
            <v-textarea
              auto-grow
              flat
              solo
            ></v-textarea>
          </v-col>
        </v-row>
        <v-row class="rectangle-outlined" v-show="checkButtons(4)">
          <v-col cols="12">
            <v-textarea
              auto-grow
              flat
              solo
            ></v-textarea>
          </v-col>
        </v-row>
      </v-col>
      <v-col style="margin-top: 30px; margin-bottom: 10px">
        <v-btn style="float:left" rounded @click="clickDeleteNovel()" class="set-save-btn">이 소설을 삭제하겠습니다.</v-btn>
        <v-btn rounded @click="clickUpdateNovel()" class="set-save-btn">저장</v-btn>
      </v-col>
    </v-row>

    <!-- 캐릭터 등록 페이지 -->
    <v-dialog v-model="dialog" persistent max-width="400px">
      <v-form action="" method="post" id="_createCharacterForm" name="createCharacterForm" @submit.prevent="createNewCharacter">
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
                  <!-- <v-img 
                    height="200"
                    src="@/assets/images/upload.png"
                    @click=""
                  ></v-img> -->
                  <picture-input
                    @change="onChange"
                    @remove="onRemove"
                    ref="inputFile"
                    button-class="btn"
                    buttonClass="pic-ch-btn"
                    removeButtonClass="pic-rem-btn"
                    accept="image/jpeg, image/png"
                    width="500"
                    height="500"
                    size="10"
                    radius="0"
                    :crop="true"
                    :removable="true"
                    :hideChangeButton="false"
                    :custom-strings="{
                      upload: '소설 이미지를 등록하세요 +',
                      drag: '캐릭터 이미지를 등록하세요 😺',
                      change: '이미지 수정  | ',
                      remove: '삭제'
                    }"
                  ></picture-input>
                </v-col>
                <v-col cols="12">
                  <v-text-field 
                    label="이름" 
                    required
                    v-model="newCharacter.name"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <ValidationProvider rules="required|numeric|checkAge" v-slot="{errors}">
                  <v-text-field 
                    v-model="newCharacter.age"
                    label="나이" 
                    required
                  ></v-text-field>
                  <span class="error-message">{{errors[0]}}</span>
                  </ValidationProvider>
                </v-col>
                <v-col cols="12" class="d-flex justify-center">
                  <!-- <v-text-field 
                    label="성별" 
                    required
                    v-model="newCharacter.gender"
                  ></v-text-field> -->
                  <v-radio-group v-model="newCharacter.gender" row>
                    <v-radio
                      label="남"
                      color="rgba(192,0,0,1)"
                      value="male"
                    ></v-radio>
                    <v-radio
                      label="여"
                      color="rgba(192,0,0,1)"
                      value="female"
                    ></v-radio>
                  </v-radio-group>
                </v-col>
                <v-col cols="12">
                  <v-text-field 
                    label="역할" 
                    required
                    v-model="newCharacter.role"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field 
                    label="직업" 
                    required
                    v-model="newCharacter.job"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field 
                    label="성격" 
                    required
                    v-model="newCharacter.personallity"
                  ></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field 
                    label="특이사항" 
                    required
                    v-model="newCharacter.significant"
                  ></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="rgba(192,0,0,1)" text @click="createNewCharacter(), dialog=false">create</v-btn>
            <v-btn color="rgba(192,0,0,1)" text @click="clearNewCharacter(), dialog=false">close</v-btn>
          </v-card-actions>
        </v-card>
      </v-form>
    </v-dialog>

  </v-container>
</template>

<script>
import http from "../../http-common";
import PictureInput from "vue-picture-input";
import AWS from "aws-sdk";
import { mapActions, mapMutations, mapGetters } from "vuex";
import { ValidationProvider, extend } from 'vee-validate';
import { required, numeric } from 'vee-validate/dist/rules';

extend('numeric', {
  ...numeric,
  message:"숫자만 입력해야합니다."
});

extend('required', {
  ...required,
  message: (field, value) => "필수 입력 항목입니다."
});

extend('checkAge', (value) => {
  if(parseInt(value).toString() === value) {
    return true;
  } else{
    return '올바르지 않은 형식입니다. 0 이상의 정수를 입력해주세요.'
  }
});

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
      inputFileCha:null,
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
      newCharacter: {
        image:'',
        name:'',
        age:'',
        gender:'',
        role:'',
        job:'',
        personallity:'',
        significant:''
        // 일단 more는 안함
      },
      newCharacterImage:'',
      selectedButton: 0,
      inputStatus:0,  // -1: 삭제, 1: 새로운 사진, 0 변화 없음
      errored: false,
      loading: true
    };
  },
  components: {
    PictureInput,
    ValidationProvider
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
    onChangeCharacter(image) { //이미지가 선택됨
      if (image) {  // 이미지가 로드됨
        this.image = image;
        this.inputFileCha = this.$refs.inputFile.file;
      }else {
        console.log("캐릭터 이미지를 로드하는데 실패했습니다.");
      }
    },
    onRemoveCharacter() {
      this.image = ''
    },
    onPrefill() {
      if(this.novelInfo.novelImage) {
        return this.novelInfo.novelImage
      }
    },

    /** S3 이미지 업로드 */
    uploadNovelImage(path, photoKey, ext, file) {
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
          let file = this.inputFile

          this.uploadNovelImage(path, photoKey, ext, file)
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
    selectButtons(index) {
      this.selectedButton = index;
      // console.log("선택된 버튼은 " + this.selectedButton +"번째 버튼입니다!!!");
    },
    createCharacter() {
      this.dialog = true;
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
    },
    checkButtons(index) {
      if(this.selectedButton === index) {
        return true;
      } else {
        return false;
      }
    },
    createNewCharacter() {
      if(this.newCharacter.gender === '' || this.newCharacter.gender === null) {
        alert('성별을 선택하지 않았습니다!');
      }
      else{ //  성별 선택 완료
        let gender;
        if(this.newCharacter.gender === "male") {
          gender = true;
        } else if(this.newCharacter.gender === "female") {
          gender = false;
        }

        if(this.isNum(this.newCharacter.age) && this.newCharacter.age !== '') {
          http
            .post('/characters', {
              characterImage : "https://www.mstoday.co.kr/news/photo/202004/_3_1018454_448598_1539.jpg",
              characterName : this.newCharacter.name,
              characterAge : this.newCharacter.age,
              characterGender : gender,
              characterRole : this.newCharacter.role,
              characterJob : this.newCharacter.job,
              characterPersonallity : this.newCharacter.personallity,
              characterSignificant : this.newCharacter.significant,
              novelPk: this.$route.params.novelPk
            })
            .then(response => {
              if(response.data.state === "ok") {
                alert(`새로운 캐릭터가 등록되었습니다.`);
              }
              this.clearNewCharacter();
            })
            .catch(() => {
              this.errored = true;
            })
            .finally(() => {
              this.loading = false;
            })
        } else {  //  나이 형식 에러
          alert('올바르지 않은 형식입니다. 나이는 0 이상의 정수를 입력해주세요.');
        }
      }
    },
    clearNewCharacter() {
      this.newCharacter.image='';
      this.newCharacter.name='';
      this.newCharacter.age='';
      this.newCharacter.gender='';
      this.newCharacter.role='';
      this.newCharacter.job='';
      this.newCharacter.personallity='';
      this.newCharacter.significant='';
    },
    isNum(str) {
      if(parseInt(str).toString() === str) {
        return true;
      } else {
        console.log(parseInt(str).toString());
        console.log(str)
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
.sub-title-white {
  font-size: 1.5rem;
  color:white;
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
.rectangle-outlined {
  border: 1px solid;
  border-color: rgba(255,83,83,1);
}
.error-message {
    color: rgb(192, 0, 0);
}
</style>