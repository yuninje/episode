<template>
    <v-container>
      <div>
        <span class="sub-title-red">느낌별 소설 모음</span>
      </div>
        <v-row>
          <!-- 모바일은 괜찮은데, 태블릿 전용으로도 비율 줘야할듯 -->
          <v-col 
            cols="12"
            md="2"
            v-for="(item, i) in items"
            :key=i
            class="feel-content"
          >
            <v-img
              :src="item.src"
              aspect-ratio=1
            >
            </v-img>
          </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
  data() {
    return {
      items: [
        {
          src:"https://i.pinimg.com/originals/17/a6/6c/17a66c5622530241fa91bf9e15bb66d1.jpg",
        },
        {
          src:"https://s3.ap-northeast-2.amazonaws.com/elasticbeanstalk-ap-northeast-2-176213403491/media/magazine_img/magazine_261/%EC%8D%B8%EB%84%A4%EC%9D%BC.jpg",
        },
        {
          src:"https://t3.daumcdn.net/thumb/R720x0/?fname=http://t1.daumcdn.net/brunch/service/user/49mq/image/kso33wIKKknRlP-N2gpaK_HjOrs",
        },
        {
          src:"https://i.pinimg.com/originals/50/f3/00/50f300a6980d8e7f3117181c3e18658e.jpg",
        },
        {
          src:"https://i.pinimg.com/originals/17/a6/6c/17a66c5622530241fa91bf9e15bb66d1.jpg",
        }
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

@media(min-width: 960px) {
  .feel-content:first-child{
    margin-left:0%;
  }
  .feel-content {
    margin-left:4.1666667%;
  }
}

</style>

