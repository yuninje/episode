<template>
  <div class="main">
    <v-container>
      <img src="@/assets/images/iwillkillyou.jpg"/>
      <h1>
        aaa1234 너 감당할 수 있겠니? 테러 그만해라 찾아간다
      </h1>
    </v-container>
  </div>
</template>

<script>
import MainCarousel from "@/components/main/MainCarousel"
import AdCarousel from "@/components/main/AdCarousel"
import MainTop100 from "@/components/main/MainTop100"
import MainFeel from "@/components/main/MainFeel"
import KeywordRanking from "@/components/main/KeywordRanking"
import MainHotFanfic from "@/components/main/MainHotFanfic"
import MainHotCharacterSet from "@/components/main/MainHotCharacterSet"

export default {
  name: 'Main',
  components: {
    MainCarousel,
    AdCarousel,
    MainTop100,
    MainFeel,
    KeywordRanking,
    MainHotFanfic,
    MainHotCharacterSet
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
img {
  margin-left: auto;
  margin-right: auto;
}
</style>
