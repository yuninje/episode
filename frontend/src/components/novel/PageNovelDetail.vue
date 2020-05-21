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
                            :src = "data.novel.novelImage"
                            aspect-ratio=0.7
                        >
                        </v-img>
                    </v-col>
                    <v-col cols="12">
                        <p class="like"><v-icon color="rgba(192,0,0,1)">mdi mdi-heart-outline</v-icon>&nbsp;좋아요</p>
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
                        <p class="write-info">작가 | {{data.novel.member.memNick}}</p>
                        <p class="write-info">최근 업로드 날짜 | {{data.novel.novelUpdatedAt}}</p>
                    </v-col>
                    <v-col cols="12">
                        <p class = "sub-title">작품 소개</p>
                        <p class="write-info">{{data.novel.novelIntro}}</p>
                    </v-col>
                    <v-col cols="12">
                        <br/>
                        <p class="tags" v-show="data.novel.hashTags.length !== 0">{{data.novel.hashTags}}</p>
                    </v-col>
                </v-row>
            </v-col>
            <v-col cols="12">
                <v-simple-table>
                    <template v-slot:default>
                        <thead>
                            <tr>
                                <th class="text-left">회차</th>
                                <th class="text-left">작성일</th>
                                <th class="text-left">제목</th>
                                <th class="text-left"></th>
                            </tr>
                        </thead>
                        <tbody>
                            <tr v-for="(episode, index) in data.episodes.content" :key="index">
                                <td>{{index+1}}화</td>
                                <td>{{episode.episodeCreatedAt}}</td>
                                <td>{{episode.episodeTitle}}</td>
                                <td>
                                    <v-btn outlined color="rgba(192,0,0,1)" @click="gotoNovelViewer()">보기</v-btn>
                                    <v-btn outlined color="rgba(192,0,0,1)" @click="" v-show="checkRight()">수정</v-btn>
                                </td>                            
                            </tr>
                        </tbody>
                    </template>
                </v-simple-table>
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
            curNovelPk:'',
            item: {
                src : "https://comicthumb-phinf.pstatic.net/20181101_25/pocket_1541053325022bMb9z_JPEG/cover.jpg?type=m260",
                writer : "김소설",
                createdAt: "2020.05.01",
                novelIntro: "어느 날 지구의 시간이 멈추었고, 이를 리셋이라고 부르기 시작했다.",
                tags: "#리셋 #시스템 #헌터 #플레이어"
            },
            episodes: [
                {
                    createdAt: "2020.05.01",
                    title: "빌어먹을 스승(1)"
                },
                {
                    createdAt: "2020.05.02",
                    title: "빌어먹을 스승(2)"
                },
                {
                    createdAt: "2020.05.03",
                    title: "빌어먹을 스승(3)"
                },
                {
                    createdAt: "2020.05.04",
                    title: "빌어먹을 스승(4)"
                },
            ],
            errored: false,
            loading: true
        }
    },
    computed: {
        ...mapGetters(["getSession"])
    },
    mounted() {
        this.curNovelPk = this.$store.state.novelPk;
        this.getNovel();
        this.checkRight();
    },
    methods: {
        gotoNovelViewer() {
            this.$router.push('/viewer');
        },
        getNovel() {
            http
                .get(`/episodes/novel-pk=${this.$store.state.novelPk}`)
                .then(response => {
                    // console.log(response.data.data);
                    this.data = response.data.data;
                })
                .catch(() => {
                    this.errored = true;
                })
                .finally(() => {
                    this.loading = false;
                })
        },
        checkRight() {
            // console.log(this.getSession);
            if(this.getSession.memPk === this.data.novel.member.memPk) {
                return true;
            }else {
                return false;
            }
        }
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