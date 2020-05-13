<template>
  <div class="main">
    <MainCarousel/>
    <v-container>
      <v-row>
        <v-col cols="10">
          <v-row>
            <!-- 느낌별 소설 모음 나중에 이 부분을 컴포넌트로 빼자-->
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
              <div>
                <span class="sub-title-black">검색 순위</span>
                <span class="sub-sub-black">(2020.05.12 오후 7시 50분 기준)</span>
                <br/>
                <p class="sub-item-black">1. 로맨스 소설</p>
              </div>
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

export default {
  name: 'Main',
  components: {
    MainCarousel,
    AdCarousel,
    MainTop100,
    MainFeel
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
