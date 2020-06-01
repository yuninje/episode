<template>
    <div class="viewer-container" v-if="data.novel">

        <!-- 소설 커버 이미지 -->
        <img class="novel-cover" :src="data.novel.novelImage"/>

        <!-- 소설 제목 -->
        <h2 class="novel-title">{{data.episodeTitle}}</h2>


        <!-- 윗쪽 버튼 -->
        <div class="button-container">
            <div class="previous">
                이전화
            </div>
            <div class="home">
            </div>
            <div class="next">
                다음화
            </div>
        </div>

        <!-- 소설 본문 -->
        <div class="novel-content">
            <div class="novel-text" v-html="data.episodeContent">
            </div>
        </div>

        <!-- 아래쪽 버튼 -->
        <div class="button-container">
            <div class="previous">
                이전화
            </div>
            <div class="home">
            </div>
            <div class="next">
                다음화
            </div>
        </div>

    </div>
</template>

<script>
import http from "../../http-common"

export default {
    data() {
        return {
            tab: null,
            items: [
                { tab: '전체', content: 'Tab 1 Content' },
                { tab: '판타지', content: 'Tab 2 Content' },
                { tab: '무협', content: 'Tab 3 Content' },
                { tab: '로맨스', content: 'Tab 4 Content' },
                { tab: '현판', content: 'Tab 5 Content' },
            ],
            data:{},
            errored: false,
            loading: true
        }
    },
    methods: {
        // f12, ctrl + shift + i(개발자 모드), 우클릭 방지
        preventCopy: function(){
            document.addEventListener("contextmenu", function (e) {
               e.preventDefault();
           }, false);

           document.addEventListener("keydown", function (e) {
               //document.onkeydown = function(e) {
               // "I" key
               if (e.ctrlKey && e.shiftKey && e.keyCode == 73) {
                   disabledEvent(e);
               }
               // "J" key
               if (e.ctrlKey && e.shiftKey && e.keyCode == 74) {
                   disabledEvent(e);
               }
               // "S" key + macOS
               if (e.keyCode == 83 && (navigator.platform.match("Mac") ? e.metaKey : e.ctrlKey)) {
                   disabledEvent(e);
               }
               // "U" key
               if (e.ctrlKey && e.keyCode == 85) {
                   disabledEvent(e);
               }
               // "F12" key
               if (event.keyCode == 123) {
                   disabledEvent(e);
               }
           }, false);
           function disabledEvent(e) {
               if (e.stopPropagation) {
                   e.stopPropagation();
               } else if (window.event) {
                   window.event.cancelBubble = true;
               }
               e.preventDefault();
               return false;
           }
        },
        getEpisode() {
            http
                .get(`/episodes/${this.$route.params.episodePk}`)
                .then(response => {
                    this.data = response.data.data;
                })
                .catch(() => {
                    this.errored = true;
                })
                .finally(() => {
                    this.loading = false;
                })
        }
    },
    mounted(){
        this.getEpisode();
        this.preventCopy();
    }
}
</script>

<style scoped>
.viewer-container {
    margin: 20px auto 0px auto;
    max-width: 700px;
    width: 100%;
}

.novel-cover {
    width: 100%;
}

.button-container {
    display:flex;
}

.previous{
    background-color: #BFBFBF;
    width: 45%;
    height: 50px;
    max-width: 300px;
    max-height: 50px;
    font-size: 1rem;
    text-align: center;
    color: white;
}

.home {
    background-color: rgba(255,83,83,1);
    width: 50px;
    height: 50px;
    margin-left: 2%;
    margin-right: 2%;
}

.next {
    background-color: rgba(255,83,83,1);
    width: 45%;
    height: 50px;
    max-width: 300px;
    max-height: 50px;
    font-size: 1rem;
    text-align: center;
    color: white;
}
.novel-content {
    margin-top: 20px;
    margin-bottom: 20px;
    height: 100%;
}
.novel-text {
    width: 100%;
    height: 100%;
}

.novel-title {
    margin-top: 20px;
    margin-bottom: 20px;
}
</style>