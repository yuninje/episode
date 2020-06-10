<template>
  <v-container v-if="novelInfo">
    <v-row v-if="checkAuth(this.novelInfo.member.memPk)">
      <v-col cols="12" md="4" lg="3">
        <v-row>
          <v-col cols="12">
            <div class="canvas-wrap">
              <canvas
                id="previewCanvas"
                width="700"
                height="1000"
                :style="{ 'background-image': `url(${this.novelInfo.novelImage})` }"
                style="background-repeat:no-repeat;background-position: center; background-size : cover;"
              ></canvas>
            </div>

            <div class="file-input-div">
              <p>
                <input type="button" value="소설 이미지 수정 📕" class="file-input-button">
                <input
                  class="file-input-hidden"
                  @change="fileSelect"
                  ref="inputFile"
                  type="file"
                  accept="image/*"
                  name="photo"
                  required="required"
                >
              </p>
            </div>
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12" md="8" lg="9">
        <v-row>
          <v-col cols="12">
            <p class="write-info">작가 | {{ novelInfo.member.memNick }}</p>
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
          </v-col>
          <v-col cols="12">
            <br>
            <!-- <p class="tags" v-show="data.novel.hashTags.length !== 0"></p> -->
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12">
        <span>소설 설정</span>
        <hr>
      </v-col>
      <v-col cols="12">
        <v-row>
          <v-col cols="2" v-for="(button, i) in buttons" :key="i">
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
            cols="12"
            sm="3"
            v-for="(char, i) in charInfo"
            :key="`character-${i}`"
            @click="getCharacter(char.characterPk)"
          >
            <char-card
              :name="char.characterName"
              :age="char.characterAge"
              :job="char.characterJob"
              :role="char.characterRole"
              :significant="char.characterSignificant"
              :src="char.characterImage"
              :color="getRandomRgb(char.characterName)"
            />
          </v-col>
          <!-- <v-col cols="3" v-for="(character, i) in characters" :key="i">
            <v-card color="blue">
              <v-row>
                <v-col cols="8">
                  <v-list-item color="rgba(0,0,0,0.4)" dark>
                    <v-list-item-content>
                      <v-list-item-title class="title">{{character.characterName}}</v-list-item-title>
                      <v-list-item-subtitle>나이 : {{character.characterAge}}세</v-list-item-subtitle>
                      <v-list-item-subtitle>직업 : {{character.characterJob}}</v-list-item-subtitle>
                      <v-list-item-subtitle>역할 : {{character.characterRole}}</v-list-item-subtitle>
                      <v-list-item-subtitle>특이사항 : {{character.characterSignificant}}</v-list-item-subtitle>
                    </v-list-item-content>
                  </v-list-item>
                </v-col>
                <v-col cols="4">
                  <v-avatar tile size="100%" class="px-2">
                    <v-img :src="character.characterImage"></v-img>
                  </v-avatar>
                </v-col>
              </v-row>
            </v-card>
          </v-col>-->
          <!-- 새로운 캐릭터 생성 카드 -->
          <v-col cols="12" sm="3">
            <v-card
              color="rgba(242, 242, 242, 1)"
              class="d-flex justify-center align-center"
              height="152"
              @click="createCharacter()"
            >
              <v-card-title>
                <v-icon>mdi mdi-plus</v-icon>
              </v-card-title>
            </v-card>
          </v-col>
        </v-row>
        <v-row class="rectangle-outlined" v-show="checkButtons(1)">
          <v-col cols="12">
            <v-textarea auto-grow flat solo></v-textarea>
          </v-col>
        </v-row>
        <v-row v-show="checkButtons(2)">
          <v-col cols="9" class="rectangle-outlined rlt-wrap" >
            <relation-diagram class="rlt-diagram"/>
          </v-col>
          <v-col cols="3">
            <make-relation class="rlt-card"/>
            <h3>인물 리스트</h3>
            <char-list-sero/>
          </v-col>
        </v-row>
        <v-row class="rectangle-outlined" v-show="checkButtons(3)">
          <v-col cols="12">
            <v-textarea auto-grow flat solo></v-textarea>
          </v-col>
        </v-row>
        <v-row class="rectangle-outlined" v-show="checkButtons(4)">
          <v-col cols="12">
            <v-textarea auto-grow flat solo></v-textarea>
          </v-col>
        </v-row>
      </v-col>
      <v-col style="margin-top: 30px; margin-bottom: 10px">
        <v-btn
          @click="clickDeleteNovel()"
          outlined
          color="rgba(255,83,83,1)"
          class="set-del-btn"
        >
          <v-icon color="rgba(255,83,83,1)">mdi mdi-pencil</v-icon>이 소설을 삭제하겠습니다.
        </v-btn>
        <v-btn
          @click="clickUpdateNovel()"
          outlined
          color="rgba(255,83,83,1)"
          style="float:right"
        >저장</v-btn>
      </v-col>
    </v-row>

    <!-- 캐릭터 등록 페이지 -->
    <v-dialog v-model="dialog" persistent max-width="400px" @keydown.esc="dialog=false">
      <v-form
        action
        method="post"
        id="_createCharacterForm"
        name="createCharacterForm"
        @submit.prevent="createNewCharacter"
      >
        <v-card>
          <v-card-title class="text-center">
            <v-spacer></v-spacer><v-spacer></v-spacer>캐릭터 등록<v-spacer></v-spacer>
            <v-btn style="margin:0 0" color="rgba(192,0,0,1)" text @click="clearNewCharacter(), dialog=false">닫기</v-btn>
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <!-- <v-img 
                    height="200"
                    src="@/assets/images/upload.png"
                    @click=""
                  ></v-img>-->
                  <picture-input
                    @change="onChangeCharacter"
                    @remove="onRemoveCharacter"
                    ref="inputFileCha"
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
                      upload: '캐릭터이미지를 등록하세요 +',
                      drag: '캐릭터 이미지를 등록하세요 😺',
                      change: '이미지 수정  | ',
                      remove: '삭제'
                    }"
                  ></picture-input>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="이름" required v-model="newCharacter.name"></v-text-field>
                </v-col>
                <v-col cols="12">
                  <ValidationProvider rules="required|numeric|checkAge" v-slot="{errors}">
                    <v-text-field v-model="newCharacter.age" label="나이" required></v-text-field>
                    <span class="error-message">{{errors[0]}}</span>
                  </ValidationProvider>
                </v-col>
                <v-col cols="12" class="d-flex justify-center">
                  <!-- <v-text-field 
                    label="성별" 
                    required
                    v-model="newCharacter.gender"
                  ></v-text-field>-->
                  <v-radio-group v-model="newCharacter.gender" row>
                    <v-radio label="남" color="rgba(192,0,0,1)" value="true"></v-radio>
                    <v-radio label="여" color="rgba(192,0,0,1)" value="false"></v-radio>
                  </v-radio-group>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="역할" required v-model="newCharacter.role"></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="직업" required v-model="newCharacter.job"></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="성격" required v-model="newCharacter.personallity"></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="특이사항" required v-model="newCharacter.significant"></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-col>
            <v-btn color="rgba(192,0,0,1)" text @click="createNewCharacter()" style="float:right">만들기</v-btn>
            </v-col>
          </v-card-actions>
        </v-card>
      </v-form>
    </v-dialog>

    <!-- 캐릭터 수정 페이지 -->
    <v-dialog v-model="dialog2" persistent max-width="400px" @keydown.esc="dialog2=false">
      <v-form
        action
        method="put"
        id="_updateCharacterForm"
        name="updateCharacterForm"
        @submit.prevent="updateCharacter"
      >
        <v-card>
          <v-card-title class="text-center">
            <v-spacer></v-spacer><v-spacer></v-spacer>캐릭터 수정<v-spacer></v-spacer>
            <v-btn color="rgba(192,0,0,1)" text @click="clearCharacter(), dialog2=false">닫기</v-btn> 
          </v-card-title>
          <v-card-text>
            <v-container>
              <v-row>
                <v-col cols="12">
                  <v-img aspect-ratio="1" :src="character.characterImage"></v-img>
                  <!-- <picture-input
                    @change="onChangeCharacter"
                    @remove="onRemoveCharacter"
                    ref="inputFileCha"
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
                      upload: '캐릭터이미지를 등록하세요 +',
                      drag: '캐릭터 이미지를 등록하세요 😺',
                      change: '이미지 수정  | ',
                      remove: '삭제'
                    }"
                  ></picture-input>-->
                </v-col>
                <v-col cols="12">
                  <v-text-field label="이름" required v-model="character.characterName"></v-text-field>
                </v-col>
                <v-col cols="12">
                  <ValidationProvider rules="required|numeric|checkAge" v-slot="{errors}">
                    <v-text-field v-model="character.characterAge" label="나이" required></v-text-field>
                    <span class="error-message">{{errors[0]}}</span>
                  </ValidationProvider>
                </v-col>
                <v-col cols="12" class="d-flex justify-center">
                  <!-- <v-text-field 
                    label="성별" 
                    required
                    v-model="newCharacter.gender"
                  ></v-text-field>-->
                  <v-radio-group v-model="character.characterGender" row>
                    <v-radio label="남" color="rgba(192,0,0,1)" value="true"></v-radio>
                    <v-radio label="여" color="rgba(192,0,0,1)" value="false"></v-radio>
                  </v-radio-group>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="역할" required v-model="character.characterRole"></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="직업" required v-model="character.characterJob"></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="성격" required v-model="character.characterPersonallity"></v-text-field>
                </v-col>
                <v-col cols="12">
                  <v-text-field label="특이사항" required v-model="character.characterSignificant"></v-text-field>
                </v-col>
              </v-row>
            </v-container>
          </v-card-text>
          <v-card-actions>
            <v-col>
            <v-btn
              color="rgba(192,0,0,1)"
              text
              @click="deleteCharacter(character.characterPk)"
              style="float:left"
            >캐릭터 삭제</v-btn>
            <v-btn
              color="rgba(192,0,0,1)"
              text
              @click="updateCharacter(character.characterPk)"
              style="float:right"
            >수정</v-btn>
            </v-col>
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
import { ValidationProvider, extend } from "vee-validate";
import { required, numeric } from "vee-validate/dist/rules";
import relationDiagram from "../character/RelationDiagram";
import MakeRelation from "../character/MakeRelation";
import CharCard from "../character/CharacterCard";
import CharListSero from "../character/CharacterCardVerticalList";

extend("numeric", {
  ...numeric,
  message: "숫자만 입력해야합니다."
});

extend("required", {
  ...required,
  message: (field, value) => "필수 입력 항목입니다."
});

extend("checkAge", value => {
  if (parseInt(value).toString() === value) {
    return true;
  } else {
    return "올바르지 않은 형식입니다. 0 이상의 정수를 입력해주세요.";
  }
});

export default {
  components: {
    PictureInput,
    ValidationProvider,
    relationDiagram,
    MakeRelation,
    CharCard,
    CharListSero
  },
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
      inputFileCha: null,
      buttons: ["캐릭터", "세계관", "인물관계", "사건", "배경지식", "+"],
      dialog: false,
      dialog2: false,
      characters: [],
      character: {},
      newCharacter: {
        image: "",
        name: "",
        age: "",
        gender: null,
        role: "",
        job: "",
        personallity: "",
        significant: ""
        // 일단 more는 안함
      },
      newCharacterImage: "",
      selectedButton: 0,
      inputStatus: 0, // -1: 삭제, 1: 새로운 사진, 0 변화 없음
      errored: false,
      loading: true
    };
  },
  beforeCreate() {
    this.novelPk = this.$route.params.novelPk;
    this.$store.dispatch(`storeNovSet/getNovelInfo`, this.novelPk);
    this.$store.dispatch(`storeNovChar/FETCH_CHARACTER_INFO`, this.novelPk);
    this.$store.dispatch(`storeNovChar/FETCH_RELATION_INFO`, this.novelPk);
  },
  created() {},
  computed: {
    ...mapGetters(["getSession"]),
    ...mapGetters("storeNovSet", {
      novelInfo: "getNovelInfo"
    }),
    ...mapGetters("storeNovChar", {
      charInfo: "getCharacterInfo"
    })
  },
  mounted() {
    this.getCharacters();
  },
  destroyed() {
    this.$store.dispatch(`storeNovSet/destroyNovelInfo`);
  },
  methods: {
    ...mapActions("storeGenNov", {
      postNovel: "postNovel"
    }),
    ...mapActions("storeNovSet", {
      putNovel: "putNovel",
      deleteNovel: "deleteNovel"
    }),

    fileSelect(event) {
      var canvas = document.getElementById("previewCanvas");
      var ctx = canvas.getContext("2d");

      var reader = new FileReader();
      reader.onload = function(event) {
        var img = new Image();

        img.onload = function() {
          canvas.width = 700;
          canvas.height = 1000;
          ctx.drawImage(img, 0, 0, 700, 1000);
        };

        img.src = event.target.result;
      };

      reader.readAsDataURL(event.target.files[0]);

      this.inputFile = this.$refs.inputFile.files[0];
      this.inputStatus = 1;
    },
    onChange(image) {
      //이미지가 선택됨
      if (image) {
        // 이미지가 로드됨
        this.image = image;
        this.inputFile = this.$refs.inputFile.file;
        this.inputStatus = 1;
      } else {
        console.log("Fail to load picture💦");
      }
    },
    onRemove() {
      this.image = "";
      this.inputFile = null;
      this.inputStatus = -1;
    },
    onChangeCharacter(image) {
      //이미지가 선택됨
      if (image) {
        // 이미지가 로드됨
        this.image = image;
        this.inputFileCha = this.$refs.inputFileCha.file;
      } else {
        console.log("캐릭터 이미지를 로드하는데 실패했습니다.");
      }
    },
    onRemoveCharacter() {
      this.image = "";
      this.$refs.inputFileCha.file = null;
      this.$refs.inputFileCha.image = "";
      this.$refs.inputFileCha.imageObject = null;
      this.inputFileCha = null;
    },

    /** S3에서 이미지 가져오기 */
    getNovelImage() {
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

      s3.listObjects({ Delimiter: "/" }, function(err, data) {
        if (err) {
          return alert(
            "There was an error listing your albums: " + err.message
          );
        } else {
          console.log(data);
          var albums = data.CommonPrefixes.map(function(commonPrefix) {
            var prefix = commonPrefix.Prefix;
            var albumName = decodeURIComponent(prefix.replace("/", ""));
            console.log(albumName);
          });
        }
      });

      var albumPhotosKey = encodeURIComponent("novel") + "//";
      s3.listObjects({ Delimiter: "albumPhotosKey" }, (err, data) => {
        if (err) {
          return alert(
            "There was an error listing your albums: " + err.message
          );
        } else {
          console.log(data);
          var href = "https://episode-image.s3.ap-northeast-2.amazonaws.com/";
          var bucketUrl =
            "https://episode-image.s3.ap-northeast-2.amazonaws.com/";
          var photos = data.Contents.map(function(photo) {
            var photoKey = photo.Key;
            var photoUrl = bucketUrl + encodeURIComponent(photoKey);
            if (photoKey == "novel/2_1591389724472.jpg") {
              console.log(photoUrl);
              return photoUrl;
            }
          });
        }
      });
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
          Body: file, // 업로드할 파일
          ACL: "public-read",
          ContentType: "image/jpeg"
        },
        (err, data) => {
          if (err) {
            console.log(err);
            return;
          }
          // console.log("사진 업로드 성공", data);
          // this.$router.push({ name: "Mywork"})
        }
      );
    },
    /** S3 이미지 삭제 */
    deleteNovelImage() {
      let path = "novel/";
      const fileName = this.getFileName();

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

      s3.deleteObject(
        {
          Key: path + fileName
        },
        (err, data) => {
          if (err) {
            console.log(err);
            return alert(
              "There was an error deleting your photo: ",
              err.message
            );
          }
        }
      );
    },
    getFileName() {
      let imagePath = this.novelInfo.novelImage;
      let lastSlash = imagePath.lastIndexOf("/");
      let fileName = imagePath.substr(lastSlash + 1);
      return fileName;
    },

    /** 소설 정보 업데이트 */
    clickUpdateNovel() {
      // 권한 확인
      const author = this.novelInfo.member.memPk;
      if (!this.checkAuth(author)) return;

      if (this.check(this.novelInfo.novelName)) {
        this.imagehandler(this.inputStatus);
        let novelImage = this.updateInfo.novelImage;
        let {
          novelName,
          novelIntro,
          novelStatus,
          novelLimit,
          novelOnly,
          novelOpen
        } = this.novelInfo;
        let genrePks = [this.novelInfo.genres[0].genrePk];
        let hashTagStrs = [];
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
        this.putNovel(data);
      }
    },
    /** 소설 삭제 */
    clickDeleteNovel() {
      // 권한 확인
      const author = this.novelInfo.member.memPk;
      if (!this.checkAuth(author)) return;

      const result = confirm(
        "⚠️ 정말 이 소설을 삭제하시겠습니까? \n이 작업은 되돌릴 수 없습니다."
      );
      if (result) {
        const novelPk = this.novelInfo.novelPk;
        this.deleteNovel(novelPk);
        this.deleteNovelImage();
      } else {
        return;
      }
    },
    imagehandler(status) {
      // status: 0: 변경 없음, -1: 이미지 삭제, 1: 이미지 변경
      if (status == 0) {
        this.updateInfo.novelImage = this.novelInfo.novelImage;
      } else {
        // 기존 이미지 삭제
        this.deleteNovelImage();

        if (status < 0) {
          this.updateInfo.novelImage = "https://i.imgur.com/37mPPf6.png";
        } else if (status > 0) {
          // 새로운 이미지 저장
          let path = "novel/";
          let time = new Date();
          let memPk = this.novelInfo.member.memPk;
          let photoKey = memPk + "_" + time.getTime();
          let ext = ".jpg";
          let file = this.inputFile;

          this.uploadNovelImage(path, photoKey, ext, file);
          this.updateInfo.novelImage =
            "https://episode-image.s3.ap-northeast-2.amazonaws.com/" +
            path +
            photoKey +
            ext;
        }
        this.inputStatus = 0;
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
      if (author == this.getSession.memPk) {
        return true;
      } else {
        this.$router.replace("/");
        alert("접근 권한이 없습니다.");
        return false;
      }
    },
    checkButtons(index) {
      if (this.selectedButton === index) {
        return true;
      } else {
        return false;
      }
    },
    getCharacter(characterPk) {
      http
        .get(`/characters/${characterPk}`)
        .then(response => {
          this.character = response.data.data;
          console.log(this.character);
          this.dialog2 = true;
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => {
          this.loading = false;
        });
    },
    getCharacters() {
      http
        .get(`/characters/novelPk=${this.$route.params.novelPk}`)
        .then(response => {
          this.characters = response.data.data;
        })
        .catch(() => {
          this.errored = true;
        })
        .finally(() => {
          this.loading = false;
        });
    },
    createNewCharacter() {
      if (
        this.newCharacter.gender === "" ||
        this.newCharacter.gender === null
      ) {
        alert("성별을 선택하지 않았습니다!");
      } else {
        //  성별 선택 완료
        // let gender;
        // if (this.newCharacter.gender === "male") {
        //   gender = true;
        // } else if (this.newCharacter.gender === "female") {
        //   gender = false;
        // }

        if (this.isNum(this.newCharacter.age) && this.newCharacter.age !== "") {
          if (this.inputFileCha != null) {
            let path = "character/" + this.novelPk + "/";
            let time = new Date();
            let photoKey = this.novelPk + "_" + time.getTime();
            let ext = ".jpg";
            const file = this.inputFileCha;
            this.uploadNovelImage(path, photoKey, ext, file);
            this.newCharacter.image =
              "https://episode-image.s3.ap-northeast-2.amazonaws.com/" +
              path +
              photoKey +
              ext;
          } else {
            // 기본 이미지로 저장
            this.newCharacter.image =
              "https://www.mstoday.co.kr/news/photo/202004/_3_1018454_448598_1539.jpg";
          }

          http
            .post("/characters", {
              characterImage: this.newCharacter.image,
              characterName: this.newCharacter.name,
              characterAge: this.newCharacter.age,
              characterGender: this.newCharacter.gender,
              characterRole: this.newCharacter.role,
              characterJob: this.newCharacter.job,
              characterPersonallity: this.newCharacter.personallity,
              characterSignificant: this.newCharacter.significant,
              novelPk: this.$route.params.novelPk
            })
            .then(response => {
              if (response.data.state === "ok") {
                alert(`새로운 캐릭터가 등록되었습니다.`);
              }
              this.clearNewCharacter();
              this.$refs.inputFileCha.removeImage();
              this.dialog = false;
            })
            .catch(() => {
              this.errored = true;
            })
            .finally(() => {
              this.loading = false;
            });
        } else {
          //  나이 형식 에러
          alert(
            "올바르지 않은 형식입니다. 나이는 0 이상의 정수를 입력해주세요."
          );
        }
      }
    },
    clearNewCharacter() {
      this.newCharacter.image = "";
      this.newCharacter.name = "";
      this.newCharacter.age = "";
      this.newCharacter.gender = null;
      this.newCharacter.role = "";
      this.newCharacter.job = "";
      this.newCharacter.personallity = "";
      this.newCharacter.significant = "";
    },
    clearCharacter() {
      this.character = {};
    },
    updateCharacter(characterPk) {
      http
        .put(`/characters/${characterPk}`, {
          characterPk: characterPk,
          characterAge: this.character.characterAge,
          characterGender: this.character.characterGender,
          characterImage: this.character.characterImage,
          characterJob: this.character.characterJob,
          characterName: this.character.characterName,
          characterPersonallity: this.character.characterPersonallity,
          characterRole: this.character.characterRole,
          characterSignificant: this.character.characterSignificant
        })
        .then(response => {
          if (response.data.state === "ok") {
            alert("수정되었습니다!");
            this.dialog2 = false;
          }
        })
        .catch(() => {
          alert("캐릭터 정보 수정중 에러발생!!");
        });
    },
    deleteCharacter(characterPk) {
      let confirmflag = confirm("진짜로 삭제하시겠습니까?");
      if (confirmflag) {
        http
          .delete(`/characters/${characterPk}`)
          .then(response => {
            if (response.data.state === "ok") {
              alert("삭제되었습니다.");
              this.dialog2 = false;
            }
          })
          .catch(() => {
            alert("캐릭터 삭제 중 에러 발생");
          });
      } else {
        // 취소
      }
    },
    isNum(str) {
      if (parseInt(str).toString() === str) {
        return true;
      } else {
        console.log(parseInt(str).toString());
        console.log(str);
        return false;
      }
    },
    djb2(str){
      let hash = 5381;
      for (let i = 0; i < str.length; i++) {
        hash = ((hash << 5) + hash) + str.charCodeAt(i); /* hash * 33 + c */
      }
      return hash;
    }
    ,getRandomRgb(str) {
      let hash = this.djb2(str);
      let r = (hash & 0xFF0000) >> 16;
      let g = (hash & 0x00FF00) >> 8;
      let b = hash & 0x0000FF;
      return "#" + ("0" + r.toString(16)).substr(-2) + ("0" + g.toString(16)).substr(-2) + ("0" + b.toString(16)).substr(-2);
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
  color: white;
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
.set-del-btn {
  float:left;
  margin-right: 12px;
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
  border-color: rgba(255, 83, 83, 1);
}
.error-message {
  color: rgb(192, 0, 0);
}

#previewCanvas {
  width: 100%;
}
.canvas-wrap {
  position: relative;
  margin: -5.8px auto;

  &::after {
    content: "";
    display: block;
  }
}
.file-input-div {
  // margin: 0 auto;
  position: relative;
  width: 100%;
  height: 56px;
  overflow: hidden;
}
.file-input-button {
  width: 100%;
  height: 56px;
  position: absolute;
  top: 0px;
  color: rgba(255,83,83,1);
  border: 1px solid;
}
.file-input-hidden {
  font-size: 25px;
  position: absolute;
  right: 0px;
  top: 0px;
  opacity: 0;
  width: 100%;

  filter: alpha(opacity=0);
  -ms-filter: "alpha(opacity=0)";
  -khtml-opacity: 0;
  -moz-opacity: 0;
}
.rlt-wrap {
  position: relative;;
  overflow: scroll;
  resize: both;
  text-align: center;

  .rlt-diagram {
    position: relative;
    display: inline-block;
    height: 100%;
    width: 100%;
  }
  .rlt-card {
    position: absolute;
    // left:65.5%;
    // width: 33%;
    z-index: 500;
  }
}
</style>