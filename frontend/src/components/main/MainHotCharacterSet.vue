<template>
    <v-container>
        <div>
            <span class="sub-title-red">HOT 캐릭터셋</span>
        </div>
        <v-row>
            <v-col
                cols="4"
                sm="2"
                v-for="i in Math.min(limit, items.length)"
                :key="i"
            >
                <v-card
                    out-lined
                    dark
                    :img="items[i-1].src"
                    @click="gotoCharacterSet(items[i-1].novelPk)"
                    class="character-set-card"
                >
                    <v-responsive :aspect-ratio="0.7">
                        <v-card-title class="card-title">
                            {{items[i-1].title}}
                        </v-card-title>
                    </v-responsive>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    name: 'MainHotCharacterSet',
    data() {
        return {
            items: [
                {
                    title:"트와이스",
                    src:"https://pm1.narvii.com/6328/408293d39dde5d412ed4dffd382b3f0af8e3cfc7_hq.jpg",
                    novelPk: 126
                },
                {
                    title:"BTS",
                    src:"https://img.hankyung.com/photo/201811/01.18181797.1.jpg",
                    novelPk: 127
                },
                {
                    title:"EXO",
                    src:"https://i.pinimg.com/originals/19/1a/87/191a8792587a138d606d1c276f79d41f.jpg",
                    novelPk: 128
                },
                {
                    title:"세븐틴",
                    src:"https://i.imgur.com/J4uEU9a.jpg",
                    novelPk: 129
                },
                {
                    title:"레드벨벳",
                    src:"https://pbs.twimg.com/media/DizziB1W0AArvtO.jpg",
                    novelPk: 130
                },
                {
                    title:"강다니엘",
                    src:"https://img.hankyung.com/photo/201904/03.18861269.1.jpg",
                    novelPk: 131
                },
                {
                    title:"신세계",
                    src:"https://img.cgv.co.kr/Movie/Thumbnail/Poster/000076/76791/76791_1000.jpg",
                    novelPk: 132
                },
                {
                    title:"도깨비",
                    src:"https://upload.wikimedia.org/wikipedia/ko/6/63/%EB%93%9C%EB%9D%BC%EB%A7%88_%EB%8F%84%EA%B9%A8%EB%B9%84_%ED%8F%AC%EC%8A%A4%ED%84%B02.jpg",
                    novelPk: 134
                },
                {
                    title:"호텔 델루나",
                    src:"https://upload.wikimedia.org/wikipedia/commons/2/2b/%EB%8D%B8%EB%A3%A8%EB%82%98_%EB%8B%A8%EC%B2%B4.jpg",
                    novelPk: 137
                },
                {
                    title:"부산행",
                    src:"https://img.cgv.co.kr/Movie/Thumbnail/Poster/000078/78982/78982_1000.jpg",
                    novelPk: 138
                },
                {
                    title:"부부의 세계",
                    src:"https://upload.wikimedia.org/wikipedia/ko/thumb/2/23/%EB%B6%80%EB%B6%80%EC%9D%98_%EC%84%B8%EA%B3%84_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg/250px-%EB%B6%80%EB%B6%80%EC%9D%98_%EC%84%B8%EA%B3%84_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg",
                    novelPk: 139
                },
                {
                    title:"이태원클라쓰",
                    src:"https://upload.wikimedia.org/wikipedia/ko/f/f4/%EB%93%9C%EB%9D%BC%EB%A7%88_%EC%9D%B4%ED%83%9C%EC%9B%90_%ED%81%B4%EB%9D%BC%EC%93%B0_%ED%8F%AC%EC%8A%A4%ED%84%B0.jpg",
                    novelPk: 141
                },
            ],
            limit:12
        }
    },
    methods: {
        gotoCharacterSet(novelPk) {
            if(novelPk !== null && novelPk !== '') {
                this.$router.push(`/characterset/${novelPk}`);
            }
        }
    },
}
</script>

<style lang="scss" scoped>
.sub-title-red {
    color: rgb(192,0,0);
    font-weight: bold;
    font-size: 1.0rem;
}
.card-title {
    text-shadow: 2px 2px 2px gray;
}
.character-set-card {
    cursor:pointer;
}
</style>