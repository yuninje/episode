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
          <div id="top-ep-title">Episode {{ index }}. <input type="text"  :value="title" ></div>
          
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
            :value="content"
            :options="editorOption"
            @change="onEditorChange"
            @blur="onEditorBlur($event)"
            @focus="onEditorFocus($event)"
            @ready="onEditorReady($event)"
          />
          <div id="cnt-letter">{{ textCount | comma }} / 5000</div>
        </div>

        <div class="middle-btm">
          <!-- <div id="btm-hashtag">Episode 태그 | #사이다 #시작 #스승</div> -->
          <span v-show="this.getSavingTimeAuto" id="btm-autosave">(자동 저장됨) 마지막 저장 시간: {{ getSavingTimeAuto }}</span>
          <span v-show="this.getSavingTime" id="btm-autosave">마지막 저장 시간: {{ getSavingTime }}</span>
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
// highlight.js style
import "highlight.js/styles/tomorrow.css";
// import theme style
import "quill/dist/quill.core.css";
import "quill/dist/quill.snow.css";

import KProgress from "k-progress";

import { mapActions, mapMutations, mapGetters, mapState } from "vuex";

export default {
  name: "quill-example-snow",
  title: "Theme: snow",
  components: {
    quillEditor,
    KProgress
  },
  data() {
    return {
      // 에디터 옵션
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

      // 에디터 글자수 프로그레스바
      htmlContent: "",
      textContent: "",
      // content: "",
      // content: dedent``,
      textLength: 0, // 입력중인 글자 포함
      textCount: 0, // 화면에 표시되는 글자 길이
      percent: 0, // 프로그레스바의 퍼센트

      // 타이머
      timer: null, // 타이머 함수
      timerSetTime: 1800, // 타이머 시간 설정 : 30분 ( 30 * 60 = 1800 )
      totalTime: 1800, // 남은 시간

      // 저장
      autoSave: null, // 자동 저장 함수
      episodeContent: "",
      episodeTitle: "",
      episodeWriter: "",
      setTitle:"",
      setWriter:"",
    };
  },
  created() {
    this.index = this.$route.params.index
    this.episodePk = this.$route.params.episodePk
    this.$store.dispatch("storeEditor/getEpisodeByPk", this.episodePk);
  },
  filters: {
    // 숫자 단위당 쉼표 필터
    comma(val) {
      return String(val).replace(/\B(?=(\d{3})+(?!\d))/g, ",");
    }
  },
  watch: {
    // 데이터가 변경되어 API를 호출해야 할 때
    textLength: function() {
      // 글자수
      this.textCount = this.textLength - 1;

      // 프로그레스바
      this.percent = this.textCount / 50;
      // 글자 수 제한
      if (this.textCount > 5000) {
        // const tt =this.model.getByte
        alert(
          "최대 5000자까지 입력 가능합니다. \n 5000자가 넘어가는 글은 저장되지 않습니다."
        );
        // 입력 안되게 끊기
      }
    }

    // model: function () {
    //   // html 태그 제거하기
    //   console.log((this.model).replace(/(<([^>]+)>)/ig,""))
    // }
  },
  methods: {
    ...mapActions("storeEditor", {
      fetchAutoSave: "fetchAutoSave",
      putEpisodeAuto: "putEpisodeAuto",
      putEpisode: "putEpisode",
    }),

    // 타이머
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
      // console.log(this.totalTime);
    },
    padTime: function(time) {
      return (time < 10 ? "0" : "") + time;
    },

    // 자동 저장
    startAutoSave() {
      this.autoSave = setInterval(() => this.saveAuto(), 10000); // 1000 = 1초 -> (5분 * 60초 * 1000 = 300000)
    },
    // 임시 저장
    saveTmp() {
      this.episodeContent = this.htmlContent;
      localStorage.setItem("autoSaved", this.episodeContent);
      // this.fetchAutoSave(this.episodeContent)
      this.save();
    },
    // 저장 : 자동 저장
    saveAuto() {
      // 데이터 처리
      this.episodeContent = this.htmlContent;
      this.episodeTitle = this.title;
      // this.episodeWriter = this.episode.episodeWriter

      let { episodeContent, episodeTitle, episodeWriter } = this;
      let data = {
        episodeContent,
        episodeTitle,
        episodeWriter
      };
      this.putEpisodeAuto(data);
    },
    // 저장 : 버튼 이벤트
    save() {
      // 데이터 처리
      this.episodeContent = this.htmlContent;
      this.episodeTitle = this.title;
      // this.episodeWriter = this.episode.episodeWritera

      let { episodeContent, episodeTitle, episodeWriter } = this;
      let data = {
        episodeContent,
        episodeTitle,
        episodeWriter
      };
      this.putEpisode(data);
    },
    
    // 에디터
    onEditorChange(value) {
      // console.log(value);
      // this.textContent = value.text;
      this.textLength = value.text.length;
      this.htmlContent = value.html;

      // 자동
    },

    // onEditorChange: debounce(function(value) { // 시간을 두고 함수 실행
    //   this.content = value.html;
    // }, 300),

    onEditorFocus(editor) {
      // console.log("editor focus!", editor);
      // 타이머 시작, 0분 남으면 타이머 스탑
      if (!this.timer) {
        this.startTimer();
        this.startAutoSave();
      }
    },
    onEditorBlur(editor) {
      // console.log("editor blur!", editor);
    },
    onEditorReady(editor) {
      // console.log("editor ready!", editor);
    }
  },
  computed: {
    ...mapGetters("storeEditor", {
      title: "getImportTitle",
      content: "getImportContent",
      getSavingTime: "getSavingTime",
      getSavingTimeAuto: "getSavingTimeAuto"
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

    // 자동저장

    editor() {
      return this.$refs.myTextEditor.quill;
    },
    contentCode() {
      return hljs.highlightAuto(this.content).value;
    }
  },
  mounted() {
    // console.log("this is Quill instance:", this.editor);
  }
};
</script>

<style lang="scss" scoped>
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
  margin-left: auto;
  margin-right: auto;
  width: 60vw;
}
.div-editor-wrap {
  margin-bottom: 16px;

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
  }
  #top-timer {
    text-align: right;
    font-size: 15px;
  }
  #top-timer-time {
    font-weight: 500;
    font-size: 18px;
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
</style>