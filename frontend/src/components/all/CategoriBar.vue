<template>
    <v-tabs
        v-model="tab"
        background-color="white"
        color="rgba(255,83,83,1)"
        class="tabs"
        show-arrows
    >
        <v-tabs-slider color="transparent"></v-tabs-slider>
        <!-- v-tab을 페이지 이동을 위한 방법으로 사용 가능 -->
        <v-tab
            v-for="genre in genres"
            :key="genre.genrePk"
            class="tab"
            @click="gotoNovelListPage(genre.genrePk)"
        >
            {{genre.genreName}}
        </v-tab>
    </v-tabs>
</template>

<script>
import http from '../../http-common'

export default {
    name: 'CategoriBar',
    props: {
        // PageNovelList.vue에서 현재 주소값을 바탕으로 props에 넣어줄것임
        // PageNovelList.vue 주소 규칙 - '/novel/list/:genrePk/:pageNum'
        selectedTab: {type: String}
    },
    data() {
        return {
            genres: [
                {genrePk:0, genreName: "전체"},
            ],
            tab: null,
            errored: false,
            loading: true
        }
    },
    created() {
        this.getGenres();
    },
    mounted() {
        // this.tab = Number(this.selectedTab);
        this.tab = Number(this.$route.params.genrePk);
    },
    methods: {
        getGenres() {
            http
                .get('/genres')
                .then(response => {
                    if(this.genres.length === 1) {
                        // console.log("추가 전");
                        for(var i in response.data.data) {
                            this.genres.push(response.data.data[i]);
                        }
                        // console.log(this.genres)
                        this.tab = Number(this.$route.params.genrePk);
                    }
                })
                .catch(() => {
                    this.errored = true;
                })
                .finally(()=> {
                    this.loading = false;
                })
        },
        gotoNovelListPage(genrePk) {
            this.$router.push(`/novel/list/${genrePk}/1`);
        }
    },
}
</script>

<style lang="scss" scoped>
.tabs {
    border-bottom: 2px solid;
    border-bottom-color: rgba(255,83,83,1);
}
.tab:first-child {
    border-left-width: 2px;
}
.tab:last-child {
    border-right-width: 2px;
}
.tab {
    border-top: 2px solid;
    border-left: 2px solid;
    border-right: 1px solid;
    border-color:rgba(255,83,83,1);
    color: rgba(255,83,83,1) !important;
    font-weight: bold;
    margin-right: -1px;
}
.v-tab--active {
    background-color: rgba(255,83,83,1) !important;
    color: white !important;
}
</style>