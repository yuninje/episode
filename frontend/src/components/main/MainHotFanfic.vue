<template>
    <v-container>
        <div>
            <span class="sub-title-red" @click="gotoTop100()">HOT FANFIC</span>
        </div>
        <v-row>
            <v-col
                cols="12"
                sm="6"
                md="3"
                v-for="i in Math.min(topNovels.length, limit)"
                :key="i"
            >
                <!-- 메인용 카드 사용 -->
                <NovelCard2
                    :novelImage="topNovels[i-1].novelImage"
                    :novelName="topNovels[i-1].novelName"
                    :novelIntro="topNovels[i-1].novelIntro"
                    :novelPk="topNovels[i-1].novelPk"
                    :episodeCount="topNovels[i-1].episodeCount"
                ></NovelCard2>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import http from "../../http-common"
import NovelCard2 from "../card/NovelCard2"

export default {
    name: 'MainHotFanfic',
    data() {
        return {
            topNovels:[],
            limit: 16,
            errored: false,
            loading: true
        }
    },
    mounted() {
        this.getTop100();
    },
    methods: {
        gotoTop100() {
            this.$router.push('/top100');
        },
        getTop100() {
            http
                .get('/novels/top100')
                .then(response => {
                    // console.log(response.data.data);
                    this.topNovels = response.data.data;
                })
                .catch(() => {
                    this.errored = true;
                })
                .finally(() => {
                    this.loading = false;
                })
        }
    },
    components: {
        NovelCard2
    }
}
</script>

<style lang="scss" scoped>
.sub-title-red {
    color: rgb(192,0,0);
    font-weight: bold;
    font-size: 1.0rem;
    cursor: pointer;
}
</style>