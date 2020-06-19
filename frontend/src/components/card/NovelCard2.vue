<template>
    <v-card
        max-width="550px"
        max-height="275px"
        @click="gotoNovelDetail(novelPk)"
        outlined
        flat
        class="rounded-card"
    >
        <v-responsive :aspect-ratio="2.0">
            <v-row>
                <v-col cols="4" class="pa-0">
                    <v-img
                        aspect-ratio = "0.7"
                        :src="novelImage"
                    ></v-img>
                </v-col>
                <v-col cols="8" class="pb-0">
                    <!-- <v-list-item>
                        <v-list-item-content>
                            <v-list-item-subtitle class="total">총 {{episodeCount}} 화</v-list-item-subtitle>
                            <v-list-item-title class="title">{{novelName}}</v-list-item-title>
                            <v-list-item-subtitle>{{novelIntro}}</v-list-item-subtitle>
                        </v-list-item-content>
                    </v-list-item> -->
                    <!-- 총 N 화 -->
                    <div class="total">총 {{episodeCount}} 화</div>
                    <!-- 타이틀 -->
                    <v-card-title class="title2">
                        {{novelName}}
                    </v-card-title>
                    <!-- 인트로 -->
                    <v-card-text
                        class="desc"
                    >
                        {{novelIntro}}
                    </v-card-text>
                </v-col>
            </v-row>
        </v-responsive>
    </v-card>
</template>

<script>
export default {
    name: 'NovelCard',
    props: {
        novelImage: {type: String},
        novelName: {type: String},
        novelIntro: {type: String},
        episodeCount: {type: Number},
        novelPk: {type: Number}
    },
    methods: {
        gotoNovelDetail(novelPk) {
            this.$router.push(`/novel/detail/${novelPk}`)
        }
    },
}
</script>

<style lang="scss" scoped>
@import url("https://fonts.googleapis.com/css?family=Roboto:400,400i,700");
body {
  font-family: Roboto, sans-serif;
  margin: 0;
  height: 100vh;
  display: flex;
  align-items: center;
  justify-items: center;
  background-image: linear-gradient(to top, #96fbc4 0%, #f9f586 100%);
}
.total {
    padding: 1rem;
    font-size: 1rem;
    font-weight: bold;
}
.title2 {
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
  margin: 0;
  padding: 0 1rem;
  font-weight: bold !important;
}
.desc {
  padding: .5rem 1rem 0 1rem;
  font-size: 12px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 1;
  -webkit-box-orient: vertical;
}
.rounded-card {
    border-radius: 20px !important;
}
</style>