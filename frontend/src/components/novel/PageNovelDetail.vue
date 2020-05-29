<template>
    <v-container v-if="data.novel">
        <v-row>
            <v-col 
                cols="12"
                md="4"
                lg="3"
            >
                <v-row>
                    <v-col cols="12">
                        <v-img 
                        v-if="data.novel"
                            :src = "data.novel.novelImage"
                            aspect-ratio=0.7
                        >
                        </v-img>
                        <!-- <v-img
                        v-if="!data.novel.novelImage"
                            :src = "require(`@/assets/images/banner0.png`)"
                            aspect-ratio=0.7
                        >
                        </v-img> -->
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
                        <p class="write-info">최근 업로드 날짜 | {{data.novel.novelUpdatedAt.substr(0,10)}}</p>
                        <!-- <v-btn 
                            outlined color="rgba(192,0,0,1)" 
                            @click="gotoNovelSetting()" 
                            v-show="checkRight()">
                            수정하기
                        </v-btn> -->
                    </v-col>
                    <v-col cols="12">
                        <p class = "sub-title">작품 소개</p>
                        <p class="write-info">{{data.novel.novelIntro}}</p>
                    </v-col>
                    <v-col cols="12">
                        <br/>
                        <div 
                            class="tags" 
                            v-show="data.novel.hashTags.length !== 0" 
                            v-for="hashTag in data.novel.hashTags"
                            :key="hashTag.hashTagPk"
                        >
                            <span>#{{hashTag.hashTagName}}&nbsp;</span>
                        </div>
                    </v-col>
                </v-row>
            </v-col>
            <v-col cols="12" v-show="checkRight()">
                <div align="right">
                <v-btn outlined color="rgba(255,83,83,1)" @click="gotoNovelSet()"><v-icon color="rgba(255,83,83,1)">mdi mdi-settings</v-icon> 소설 설정</v-btn>
                &nbsp;
                <v-btn outlined color="rgba(255,83,83,1)" @click="createEpisode()"><v-icon color="rgba(255,83,83,1)">mdi mdi-pencil</v-icon> 글쓰기</v-btn>
                </div>
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
                                <td>{{(page-1)*10+index+1}}화</td>
                                <td>{{episode.episodeCreatedAt.substr(0,10)}}</td>
                                <td>{{episode.episodeTitle}}</td>
                                <td>
                                    <v-btn outlined color="rgba(192,0,0,1)" @click="gotoNovelViewer(episode.episodePk)">보기</v-btn>
                                    <v-btn outlined color="rgba(192,0,0,1)" @click="gotoNovelEditor(episode.episodePk, index+1)" v-show="checkRight()">수정</v-btn>
                                    <v-btn outlined color="rgba(192,0,0,1)" @click="deleteEpisode(episode.episodePk)" v-show="checkRight()">삭제</v-btn>
                                </td>                            
                            </tr>
                        </tbody>
                    </template>
                </v-simple-table>
            </v-col>
            <v-col cols="12">
                <v-pagination
                    v-model="page"
                    :length="pageLength"
                    color="rgba(255,83,83,1)"
                    @input="getNovel()"
                ></v-pagination>
            </v-col>
            <!-- <v-col cols="12">
                <div align="center">
                    <v-btn text color="rgba(192,0,0,1)" @click="createEpisode()" v-show="checkRight()">
                        <v-icon large>mdi mdi-plus</v-icon>
                    </v-btn>
                </div>
            </v-col> -->
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
            errored: false,
            loading: true,
            page:1,
            pageLength: 0
        }
    },
    computed: {
        ...mapGetters(["getSession"]),
    },
    created() {
        this.getNovel();
    },
    mounted() {
        this.page = 1;
    },
    methods: {
        ...mapActions("storeEditor", {
            storeEpisodePkLoc:"storeEpisodePkLoc",
            postEpisode: "postEpisode",
        }),
        gotoNovelSetting() {
            let novelPk = this.data.novel.novelPk
            let path = `/novel/setting/${novelPk}`
            this.$router.push({ path:path, param: novelPk })
        },
        gotoNovelViewer(episodePk) {
            this.$router.push(`/viewer/${episodePk}`);
        },
        gotoNovelEditor(episodePk, index) {
            this.storeEpisodePkLoc(episodePk)
            this.$router.push({name: 'Editor', param:"check"});
        },
        getNovel() {
            http
                .get(`/episodes/novel-pk=${this.$route.params.novelPk}`, {
                    params:{
                        page: this.page-1,
                        size: 10
                    }
                })
                .then(response => {
                    this.data = response.data.data;
                    if(this.pageLength === 0){
                        this.pageLength = this.data.episodes.totalPages;
                    }
                    this.checkRight();
                })
                .catch(() => {
                    this.errored = true;
                })
                .finally(() => {
                    this.loading = false;
                })
        },
        checkRight() {
            if(this.getSession.memPk === this.data.novel.member.memPk) {
                return true;
            }else {
                return false;
            }
        },
        deleteEpisode(episodePk) {
            var result = confirm("⚠️ 정말 에피소드를 삭제하시겠습니까? \n이 작업은 되돌릴 수 없습니다.")
            if(result) { // yes
                http
                    .delete(`/episodes/${episodePk}`)
                    .then(response => {
                        this.$router.go()
                    })
                    .catch(() => {
                        this.errored = true;
                    })
                    .finally(() => {
                        this.loading = false;
                    })
            }else { // no
                return;
            }
        },
        createEpisode() {
            if(this.checkRight()) {
                let data = {
                    novelPk         :this.data.novel.novelPk,
                    episodeWriter   :this.getSession.memPk ,
                    episodeTitle    :"",
                    episodeContent  :""
                };
                this.postEpisode(data)
            } else {
                alert("이 소설의 에피소드를 작성할 수 있는 권한이 없습니다.")
                return null;
            }
        },
        gotoNovelSet() {
            this.$router.push(`/novel/setting/${this.data.novel.novelPk}`)
        }

    },
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