<template>
    <v-card
        max-width="550px"
        max-height="275px"
    >
        <v-responsive :aspect-ratio="2">
            <v-row>
                <v-col cols="4">
                    <v-img
                        :src="novelImage"
                    ></v-img>
                </v-col>
                <v-col cols="8">
                    <!-- 총 N 화 -->
                    <!-- 타이틀 -->
                    <!-- 인트로 -->
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
        episodeCount: {type: String},
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

</style>