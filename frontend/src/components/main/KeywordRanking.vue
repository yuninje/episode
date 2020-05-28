<template>
    <div>
        <span class="sub-title-black">검색 순위</span>
        <span class="sub-sub-black">({{year}}.{{month}}.{{date}} {{hours}}시 {{minutes}}분 기준)</span>
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
                v-for="(keyword, i) in searchKeywords"
                :key = "i"
                @click="gotoSearch(keyword.searchWord)"
                class="clickable"
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
                        <div class="keyword">{{i+1}}. {{keyword.searchWord}}</div>
                    </v-row>
                </v-sheet>
            </v-carousel-item>
        </v-carousel>
    </div>
</template>

<script>
import http from '../../http-common'

export default {
    data() {
        return {
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
            ],
            searchKeywords:[],
            today:'',
            year:null,
            month:null,
            date:null,
            hours:null,
            minutes:null,
            loading: true,
            errored: false
        }
    },
    mounted() {
        this.getKeywords();
        this.getDate();
    },
    methods: {
        getKeywords() {
            http
                .get('/search/real-time-search')
                .then(response => {
                    this.searchKeywords = response.data.data;
                })
                .catch(() => {
                    this.errored = true;
                })
                .finally(() => {
                    this.loading = false;
                })
        },
        getDate() {
            this.today = new Date();
            this.year = this.today.getFullYear();
            this.month = this.today.getMonth() + 1;
            this.date = this.today.getDate();
            this.hours = this.today.getHours();
            this.minutes = this.today.getMinutes();
            if(this.hours < 12) {
                this.hours = '오전 ' + this.hours;
            }
            else if(this.hours === 12){
                this.hours = '오후 ' + this.hours;
            }else {
                this.hours = '오후 ' + (this.hours - 12);
            }
        },
        gotoSearch(keyword) {
            this.$router.push(`/search/${keyword}`)
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
.clickable {
    cursor: pointer;
}
</style>
