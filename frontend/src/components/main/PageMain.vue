<template>
  <div class="main">
    <MainCarousel/>
    <v-container>
      <v-row>
        <v-col cols="10">
          <v-row>
            <!-- 느낌별 소설 모음 -->
            <v-col cols="12">
              <MainFeel/>
            </v-col>
            <!-- Top 100 -->
            <v-col cols="12">
              <MainTop100/>
            </v-col>
          </v-row>
        </v-col>
        <v-col cols="2" class="pa-5">
          <v-row>
            <!-- 검색 순위 들어갈 곳 -->
            <v-col cols="12">
              <KeywordRanking/>
            </v-col>
            <!-- 광고 캐러셀 -->
            <v-col cols="12">
              <AdCarousel/>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
import MainCarousel from "@/components/main/MainCarousel"
import AdCarousel from "@/components/main/AdCarousel"
import MainTop100 from "@/components/main/MainTop100"
import MainFeel from "@/components/main/MainFeel"
import KeywordRanking from "@/components/main/KeywordRanking"

export default {
  name: 'Main',
  components: {
    MainCarousel,
    AdCarousel,
    MainTop100,
    MainFeel,
    KeywordRanking
  },
  data () {
    return {

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
</style>
