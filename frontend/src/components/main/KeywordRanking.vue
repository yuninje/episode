<template>
    <div>
        <span class="sub-title-black">검색 순위</span>
        <span class="sub-sub-black">(2020.05.12 오후 7시 50분 기준)</span>
        <br/>
        <!-- <p class="sub-item-black">1. 로맨스 소설</p> -->
        <v-carousel
            cycle
            height="30"
            hide-delimiters
            :show-arrows="false"
            vertical
            class="keyword-view"
        >
            <v-carousel-item
                v-for="(keyword, i) in keywords"
                :key = "i"
            >
                <v-sheet
                    color="rgba(255,255,255,0)"
                    height="100%"
                    tile
                >
                    <v-row
                        class="fill-height ml-1"
                        align="center"
                    >
                        <div class="keyword">{{i+1}}. {{keyword}}</div>
                    </v-row>
                </v-sheet>
            </v-carousel-item>
        </v-carousel>
    </div>
</template>

<script>
export default {
    data() {
        return {
            slides: [1,2,3,4,5,6,7,8,9,10],
            keywords:[
                '로맨스 소설',
                '판타지 소설',
                '무협 소설',
                '미스터리 소설',
                '현대 판타지 소설',
                '로맨스 판타지 소설',
                '추리 소설',
                '식당 밑에 던전',
                '재혼 황후',
                '100년 묶은 탑셰프'
            ]
        }
    }
}
</script>

<style lang="scss" scoped>
.sub-title-red {
  color: rgb(192,0,0);
  font-weight: bold;
  font-size: 1.0rem;
}
.sub-title-black {
  color: black;
  font-weight: bold;
  font-size: 1.0rem;
}
.sub-sub-black {
  color: black;
  font-size: 0.8rem;
}
.sub-item-black {
  color: black;
  font-size: 1.0rem;
}
.keyword {
  color: black;
}
.keyword-view {
  border-bottom: solid;
  border-bottom-width: thin;
}
</style>
