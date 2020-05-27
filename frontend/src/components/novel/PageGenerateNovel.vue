<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="4" lg="3">
        <v-row>
          <v-col cols="12">
            <div class="pic-uploader">
              <picture-input
                @change="onChange"
                ref="pictureInput"
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
            ></v-img> -->
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="12" md="8" lg="9">
        <v-row>
          <v-col cols="12">
            <p class="write-info">작가 | {{ getSession.memNick }}</p>
            <p class="write-info">생성일 | {{ today }}</p>
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
            <v-btn rounded @click="genNovel()" class="gennovel-btn">생성</v-btn>
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
import { mapActions, mapMutations, mapGetters } from "vuex";
import PictureInput from "vue-picture-input";

export default {
  data() {
    return {
      novelInfo: {
        memberPk: "", // o
        novelName: "", // o
        novelImage: "", // x  ==> url가져오기
        novelIntro: "", // o
        novelStatus: 0, // x
        novelLimit: true, // x
        novelOnly: true, // x
        novelOpen: true, // x
        genrePks: [3], //
        hashTagStrs: ["string"] //
      },
      today: new Date().toLocaleDateString(),
      pictureInput: ","
    };
  },
  components: {
    PictureInput
  },
  computed: {
    ...mapGetters(["getSession"])
  },
  created() {},
  mounted() {},
  methods: {
    ...mapActions("storeGenNov", {
      postNovel: "postNovel"
    }),
    onChange(image) {
      console.log("New picture selected!");
      if (image) {
        console.log("Picture loaded.");
        this.image = image;
        this.pictureInput = this.$refs.pictureInput.file;
        // console.log("onChange()",this.pictureInput)
      } else {
        console.log("Fail to load a picture💦");
      }
    },
    genNovel() {
      if (this.check(this.novelInfo.novelName)) {
        this.novelInfo.memberPk = this.getSession.memPk;
        let data = this.novelInfo;
        this.postNovel(data);
      }
    },
    check(novelName) {
      if (novelName != "") return true;
      if (novelName == "") {
        alert("소설 제목을 입력해주세요");
        return;
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
</style>