<template>
  <div class="editor-page">
    <div class="editor-page-title"></div>

    <div class="editor-page-wrap">
      <div class="left-wrap">
        <!-- 오른쪽은 - 타임라인 Div -->
        <br>
      </div>

      <div class="middle-wrap">
        <div class="middle-top">
          <div id="top-ep-title">
            <label for="id">제목: </label>
            <input type="text" v-model="epiInfo.episodeTitle" placeholder="에피소드의 제목">
          </div>

          <div id="top-timer">
            ⏰마감까지 남은시간
            <div id="top-timer-time">{{ minutes }}분 {{ seconds }}초</div>
          </div>
          <div id="top-prg-bar">
            <k-progress
              id="pb"
              :percent="percent"
              :color="['#c00000', '#c00000']"
              :line-height="12"
              :show-text="false"
            ></k-progress>
          </div>
        </div>

        <div class="div-editor-wrap">
          <quill-editor
            class="editor"
            ref="myTextEditor"
            :value="epiInfo.episodeContent"
            :options="editorOption"
            @change="onEditorChange"
            @blur="onEditorBlur($event)"
            @focus="onEditorFocus($event)"
            @ready="onEditorReady($event)"
          />
          <div id="cnt-letter">{{ textCount | comma }} / 5000</div>


          <!-- 맞춤법 검사 시작 -->
          <button class="right-menu" v-on:click="hans">맞춤법 검사</button>
          <div class="spell-box" v-show="spellOpen">
            <!-- <ol>
              <li v-for="(item, idx) in list" v-bind:key="idx"> {{item}} </li>
            </ol> -->
      <v-tabs vertical>
        <v-tab class="overflow-text" v-for="(item, idx) in list" v-bind:key="idx">
          {{item.token}}
        </v-tab>
  
        <v-tab-item v-for="(item, idx) in list" v-bind:key="idx">
          <v-card flat>
            <v-card-text>
              <h3> 추천 맞춤법 </h3>
              <p>
                <ol>
                  <li v-for="(sug, idx) in item.suggestions" v-bind:key="idx"> {{sug}} </li>
                </ol>
              </p>
              <p class="mb-0">
                {{item.info}}
              </p>
            </v-card-text>
          </v-card>
        </v-tab-item>
      </v-tabs>

          </div>
          <!-- 맞춤법 검사 끝 -->
        </div>

        <div class="middle-btm">
          <!-- <div id="btm-hashtag">Episode 태그 | #사이다 #시작 #스승</div> -->
          <span
            v-show="this.getSaveTime.auto"
            id="btm-autosave"
          >(자동 저장됨) 마지막 저장 시간: {{ getSaveTime.auto }}</span>
          <span v-show="this.getSaveTime.default" id="btm-autosave">마지막 저장 시간: {{ getSaveTime.default }}</span>
          <span class="div-save-wrap">
            <button id="btn-save" v-on:click="save">저장하기</button>
          </span>
        </div>

      </div>

      <div class="right-wrap">
        <!-- <br>왼쪽은 - 등장인물, 주요사건, 연관에피소드, 맞춤법검사 Div -->
      </div>
    </div>
  </div>
</template>

<script>
import dedent from "dedent";
import hljs from "highlight.js";
import debounce from "lodash/debounce";
import { quillEditor } from "vue-quill-editor";

/* highlight.js style */
import "highlight.js/styles/tomorrow.css";
/* import theme style */
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";

import KProgress from "k-progress";
import http from "../../http-common";
import { mapActions, mapMutations, mapGetters, mapState } from "vuex";

const hanspell = require('./episode-spell');

export default {
  name: "quill-example-snow",
  title: "Theme: snow",
  components: {
    quillEditor,
    KProgress
  },
  data() {
    return {
      /* 에디터 옵션 */
      editorOption: {
        modules: {
          toolbar: [
            [{ size: ["small", false, "large", "huge"] }],
            ["bold", "italic", "underline"],
            ["image"]
          ],
          syntax: {
            highlight: text => hljs.highlightAuto(text).value
          }
        },
        placeholder: "여기에 입력하세요..."
      },

      /* 에피소드 정보 */
      epiInfo:{},

      /* 에디터 글자수 프로그레스바 */
      // content: dedent``,
      htmlContent: "",
      textLength: 0,  // 입력중인 글자 포함
      textCount: 0,   //  표시되는 글자 길이
      percent: 0,     // 프로그레스바 퍼센트

      /* 타이머 */
      timer: null,        // 타이머 함수
      timerSetTime: 1800, // 시간 설정 : 30분 ( 30 * 60 = 1800 )
      totalTime: 1800,    // 남은 시간

      /* 저장 */
      autoSave: null,     // 자동 저장 함수
      episodeContent: "",
      episodeWriter: "",
      setTitle:"",
      setWriter:"",

      // 맞춤법 테스트
      list: [],

      // 맞춤법 창 오픈 여부
      spellOpen: false
    };
  },
  created() {
    this.episodePk = this.getEpisodePk
    this.getEpisode();
  },
  filters: {
    /* 숫자 단위당 쉼표 */
    comma(val) {
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
  },
  watch: {  // 데이터가 변경되어 API를 호출해야 할 때
    textLength: function() {
      /* 글자수 */
      this.textCount = this.textLength - 1;

      /* 프로그레스바 */
      this.percent = this.textCount / 50;

      /* 글자 수 제한 */
      if (this.textCount > 5000) {
        alert(
          "최대 5000자까지 입력 가능합니다. \n 5000자가 넘어가는 글은 저장되지 않습니다."
        );
      }
    },
    /* html태그제거 */
    model: function () {
      console.log((this.model).replace(/(<([^>]+)>)/ig,""))
    }
  },
  methods: {
    ...mapActions("storeEditor", {
      putEpisodeAuto: "putEpisodeAuto",
      putEpisode: "putEpisode",
      resetSaveTime : "resetSaveTime"
    }),

    /* 해당하는 에피소드를 가져온다 */
    getEpisode() {
      http
        .get(`episodes/${this.episodePk}`)
        .then(response => {
          this.epiInfo = response.data.data;
          // this.checkRight();
          })
        .catch((err) => {
          console.log(err)
        })
    },

    /* 타이머 */
    resetTimer: function() {
      this.totalTime = this.timerSetTime;
      clearInterval(this.timer);
      this.timer = null;
    },
    startTimer: function() {
      this.totalTime = this.timerSetTime;
      this.timer = setInterval(() => {
        this.countdown();
      }, 1000); // 1000 = 1초
    },
    countdown: function() {
      if (this.totalTime > 0) {
        this.totalTime--;
      } else {
        this.totalTime = 0;
        clearInterval(this.timer);
      }
    },
    padTime: function(time) {
      return (time < 10 ? "0" : "") + time;
    },

    // 자동 저장
    startAutoSave() {
      this.autoSave = setInterval(() => this.saveAuto(), 300000); // 1000 = 1초 -> (5분 * 60초 * 1000 = 300000)
    },
    /* 저장 */
    saveAuto() {
      this.episodeContent = this.htmlContent;

      let { episodeTitle } = this.epiInfo;
      let { episodeContent, episodeWriter } = this;
      let data = {
        episodeContent,
        episodeTitle,
        episodeWriter
      };
      this.putEpisodeAuto(data);
    },
    save() {
      this.episodeContent = this.htmlContent;

      let { episodeTitle } = this.epiInfo;
      let { episodeContent, episodeWriter } = this;
      let data = {
        episodeContent,
        episodeTitle,
        episodeWriter
      };
      console.log("save() data: ",data)
      this.putEpisode(data);
    },

    /* 에디터 */
    onEditorChange(value) {
      // console.log(value);
      this.textContent = value.text;
      console.log(this.textContent)
      this.textLength = value.text.length;
      this.htmlContent = value.html;
      // 자동
    },
    // onEditorChange: debounce(function(value) { // 시간을 두고 함수 실행
    //   this.content = value.html;
    // }, 300),

    onEditorFocus(editor) {
      // console.log("editor focus", editor);
      
      // 타이머 시작, 0분 남으면 타이머 스탑
      if (!this.timer) {
        this.startTimer();
        this.startAutoSave();
      }
    },
    onEditorBlur(editor) {
      // console.log("editor blur", editor);
    },
    onEditorReady(editor) {
      // console.log("editor ready!", editor);
    },
    hans: function(){
      this.openSpellBox(); //  spell-box 오픈
      console.log("스펠 이벤트 발생")
      console.log(this.textContent)
      // console.log(this.list)
      this.list = hanspell(this.textContent, 6000, this.checker, function () {
          console.log("// check ends");
      }, function (err) {
          console.error("HTTP status code: " + err);
      });
    },
    checker: function(result){
      this.list = result
    },
    openSpellBox() {
      if(this.spellOpen === false) {
        this.spellOpen = true;
      } else {
        this.spellOpen = false;
      }
    }
  },
  computed: {
    ...mapGetters("storeEditor", {
      getSaveTime: "getSaveTime",
      getEpisodePk: "getEpisodePk"
    }),

    // 타이머
    minutes: function() {
      const minutes = Math.floor(this.totalTime / 60);
      return this.padTime(minutes);
    },
    seconds: function() {
      const seconds = this.totalTime - this.minutes * 60;
      return this.padTime(seconds);
    },

    editor() {
      return this.$refs.myTextEditor.quill;
    },
    contentCode() {
      return hljs.highlightAuto(this.content).value;
    }
  },
  mounted() {
    // console.log("this is Quill instance:", this.editor);
  },
  beforeDestroy() {
    /* 페이지 나가기 전 확인
    var result = confirm("⚠️ 정말 이 페이지를 나가시겠습니까? \n 저장하지 않은 데이터가 손실됩니다.")
    if(result) {
      console.log("확인")
      return null;
    }else {
      return
    }
    */
    
  },
  destroyed() {
    this.resetSaveTime();
  },
};

</script>

<style lang="scss" scoped>
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap');

.editor-page {
  margin-left: auto;
  margin-right: auto;
  margin-top: 50px;
}
.editor-page-title {
  margin-top: 50px;
  margin-bottom: 50px;
}
.middle-wrap {
  flex-direction: column;
  display: table;
  margin-left: -200px;
  margin-right: auto;
  width: 60vw;
}
.div-editor-wrap {
  margin-bottom: 16px;
  position: relative;
  .editor {
    // width: 50rem;
    // height: 36rem;
    height: 30vw;
    padding-bottom: 1em;
  }

  #cnt-letter {
    padding-right: 10px;
    text-align: right;
    color: rgb(192, 0, 1);
  }
}
.middle-top {
  margin: 0 auto;
  margin-bottom: 16px;

  #top-ep-title {
    float: left;
    height: 59px;

    label {
      display: inline-block;
      line-height: 49px;
      max-width: 110%;
    }
    input,
    span {
      border: none;
      border-right: 0px;
      border-top: 0px;
      width: 80%;
      height: 49px;
      line-height: 49px;
      float: right;
      background: 0 0;
      box-sizing: border-box;
      padding: 7px 0px 7px;
      outline-style: none;
    }
    input::placeholder {
      color: #424b536e;
    }
  }

  #top-timer {
    text-align: right;
    font-size: 15px;
  }
  #top-timer-time {
    font-weight: 500;
    font-size: 18px;
    padding-bottom: 5px;
    border-bottom: solid 1px #8590996e;
  }

  #pb {
    display: block;
    width: calc(100% + 50px);
  }
}

.middle-btm {
  #btm-autosave {
    vertical-align: middle;
    width: 70%;
  }
  #btn-save {
    float: right;
    padding: 5px 20px;
    border: solid 1px rgb(204, 197, 197);
    outline: 0;
    border-radius: 2px;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
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
    }
  }
}

.right-menu {
    position: absolute;
    right: -71px;
    top: 38px;
    padding: 5px 20px;
    border: solid 1px rgb(204, 197, 197);
    outline: 0;
    border-radius: 2px;
    font-size: 14px;
    font-weight: bold;
    text-align: center;
    transition: all 0.1s;
    transition: all 0.1s;

    writing-mode:tb-rl;
    -webkit-transform:rotate(90deg);
    -moz-transform:rotate(90deg);
    -o-transform: rotate(90deg);
    white-space:nowrap;

    font-family: 'Noto Sans KR', sans-serif;

    &:hover {
    color: #fff;
    box-shadow: 300px 0 0 0 rgb(192, 0, 0) inset;
    border: 0;
    outline: 0;
  }
}

.spell-box {
  width: 400px;
  height: 100%;
  overflow: scroll;
  white-space: pre-line;
  overflow-x:hidden;
  position: absolute;
  right: -433px;
  top: 0px;
  border: solid 1px rgb(204,197,197);
}

.overflow-text {
  overflow:hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  width: 100px;
}
</style>