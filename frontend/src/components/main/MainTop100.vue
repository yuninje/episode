<template>
    <v-container>
      <div>
        <span class="sub-title-red">TOP 100</span>
        &nbsp;
        <!-- 더 보기 버튼을 누를 경우 Top100 페이지로 이동시킬 것 -->
        <v-btn x-small color="rgba(192,0,0,1)" rounded class="more-btn" @click="gotoTop100()">더 보기</v-btn>
      </div>
        <v-row>
          <v-col 
            cols="4"
            sm="2" 
            md="1" 
            v-for="(item, i) in items"
            :key=i
          >
            <v-img
              :src="item.src"
              aspect-ratio=0.7
              @click="gotoNovelDetail()"
              class="novel-image"
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
          src:"https://comicthumb-phinf.pstatic.net/20181101_25/pocket_1541053325022bMb9z_JPEG/cover.jpg?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20200225_42/pocket_1582603851691bSfLI_JPEG/__800x1250.jpg?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20200108_287/pocket_1578467146851FQFms_JPEG/cover.jpg?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20200427_80/pocket_15879785758941KJwJ_JPEG/__.jpg?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20200507_106/pocket_1588843190984O86vo_JPEG/___.jpg?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20200224_50/pocket_1582541916201UOo68_PNG/__A.png?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20190605_135/pocket_1559708037360WpJ2N_JPEG/_2.jpg?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20200422_94/pocket_1587521992086XqSzO_JPEG/image.JPEG?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20200423_107/pocket_1587627223414yerlx_JPEG/-_.jpg?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20200422_13/pocket_1587534928875CQVSb_JPEG/_%28%29200228.jpg?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20200506_200/pocket_15887646322893zupf_JPEG/___.jpg?type=m260",
        },
        {
          src:"https://comicthumb-phinf.pstatic.net/20200428_133/pocket_1588044467650zDp5T_JPEG/packseller.jpg?type=m260",
        }
      ]
    }
  },
  methods: {
    gotoTop100() {
      this.$router.push('/top100');
    },
    gotoNovelDetail() {
      this.$router.push('/noveldetail');
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
.more-btn {
  color:white !important;
  font-size: 0.7rem;
  font-weight: bold;
}
.novel-image {
  cursor: pointer;
}
</style>