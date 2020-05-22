<template>
    <v-container>
        <v-row>
            <v-col 
                cols="12"
                md="4"
                lg="3"
            >
                <v-row>
                    <v-col cols="12">
                        <v-img
                            :src = "require(`@/assets/images/banner0.png`)"
                            aspect-ratio=0.7
                        >
                        </v-img>
                    </v-col>
                </v-row>
            </v-col>
            <v-col 
                cols="12"
                md="8"
                lg="9"
            >
                <v-row>
                    <v-col cols="12">
                        <p class="write-info">작가 | {{ getSession.memNick }}</p>
                        <p class="write-info">생성일 | {{ today }}</p>
                    </v-col>
                    <v-col cols="12" sm="5">
                        <p class = "sub-title">제목</p>
                            <v-text-field 
                                v-model="novelName"
                                placeholder="소설의 제목를 입력하세요."
                                solo
                                flat
                                clearable
                                rows="1"
                                row-height="15"
                            ></v-text-field>
                        <p class="write-info"></p>
                    </v-col>
                    <v-col cols="12" >
                        <p class = "sub-title">작품 소개</p>
                            <v-textarea 
                                v-model="novelIntro"
                                placeholder="작품소개를 입력하세요."
                                solo
                                flat
                                clearable
                                rows="5"
                                row-height="15"
                            ></v-textarea>
                        <p class="write-info"></p>
                        <v-btn rounded @click="genNovel()" class="gennovel-btn">생성</v-btn>
                    </v-col>
                    <v-col cols="12">
                        <br/>
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

export default {
    data() {
        return {
            data: {},
            item: {
                src : "https://comicthumb-phinf.pstatic.net/20181101_25/pocket_1541053325022bMb9z_JPEG/cover.jpg?type=m260",
                writer : "김소설",
                createdAt: "2020.05.01",
                novelIntro: "어느 날 지구의 시간이 멈추었고, 이를 리셋이라고 부르기 시작했다.",
                tags: "#리셋 #시스템 #헌터 #플레이어"
            },
            today : new Date().toLocaleDateString(),
            
            "novelName": "",  // o
            "novelImage": "", // x
            "novelIntro": "", // o
            "novelStatus": 0,
            "novelLimit": true,
            "novelOnly": true,
            "novelOpen": true,
            "genrePks": [3],    // 임의값
            "hashTagStrs": ["string"],  // 임의값
            
            errored: false,
            loading: true
        }
    },
    computed: {
        ...mapGetters(["getSession"]),
    },
    created() {
    },
    mounted() {
    },
    methods: {
        ...mapActions("storeGenNov", {
            postNovel: "postNovel",
        }),
        genNovel() {
            let memberPk = this.getSession.memPk
            let { 
                novelName, 
                novelImage, 
                novelIntro, 
                novelStatus, 
                novelLimit , 
                novelOnly, 
                novelOpen,
                genrePks,
                hashTagStrs } = this;
            if (this.check(novelName)) {
                let data = {
                memberPk,
                novelName, 
                novelImage, 
                novelIntro, 
                novelStatus, 
                novelLimit , 
                novelOnly, 
                novelOpen,
                genrePks,
                hashTagStrs
            }

            this.postNovel(data);
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
}
</script>

<style lang="scss" scoped>
.tags {
    color: rgba(192,0,0,1);
}
.sub-title {
    font-size : 1.5rem;
    font-weight: bold;
}
.like {
    text-align: right;
}
.container {
    max-width: 1300px;
}
.write-info {
    font-size : 1.2rem;
    font-weight: 500;
}
</style>