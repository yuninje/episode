<template>
    <v-container>
        <v-row>
            <v-col cols="12" v-show="false">
                 <p class="search-result">소설 전체보기 페이지입니다.</p>
            </v-col>
            <v-col cols="12">
                <p class="no-result">소설 전체보기</p>
            </v-col>
            <v-col cols="12" class="d-flex justify-center">
                <v-card
                    flat
                    width="75%"
                >
                  <v-row>
                    <v-col cols="12">
                      <!-- 여기에 카테고리바를 집어넣는다. props를 주면서 -->
                      <CategoriBar
                        :tab = tab
                      ></CategoriBar>
                    </v-col>
                    <!-- 여기에서 소설 데이터 받은 걸 반복문으로 돌린다. -->
                    <v-col cols="12">
                      <v-row>
                        <v-col 
                          v-for="(novel, index) in novels"
                          :key="index"
                          cols="6"
                          md="6"
                        >
                          <NovelCard
                            :novelImage= "novel.novelImage"
                            :novelName= "novel.novelName"
                            :novelIntro= "novel.novelIntro"
                            :novelPk= "novel.novelPk"
                            :episodeCount= "novel.episodeCount"
                          ></NovelCard>
                        </v-col>
                      </v-row>
                    </v-col>
                    <v-col cols="12">
                      <v-pagination
                        v-model="page"
                        :length="pageLength"
                        color="rgba(255,83,83,1)"
                        @input="gotoNovelListPage(tab, page)"
                      ></v-pagination>
                    </v-col>
                  </v-row>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import http from '../../http-common'
import NovelCard from '../card/NovelCard'
import CategoriBar from '../all/CategoriBar'

export default {
  data() {
    return {
      page:1,
      pageLength:0,
      genres:[
          {genrePk:0, genreName: "전체"},
      ],
      novels:[],
      tab: null,
      errored: false,
      loading: true
    }
  },
  created() {

  },
  mounted() {
    this.tab = this.$route.params.genrePk;
    this.getNovels(Number(this.$route.params.genrePk), this.$route.params.pageNum);
  },
  methods: {
    getNovels(genrePk, pageNum) {
      if(genrePk === '0' || genrePk === 0) {
        http
          .get(`/novels`, {
            params: {
              page: pageNum,
              size: 10,
              sort: "updated"
            }
          })
          .then(response => {
            this.novels = response.data.data.content;
            this.pageLength = response.data.data.totalPages;
            this.page = Number(this.$route.params.pageNum);
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => {
            this.loading = false;
          })
      } else {
        http
          .get(`/novels/genre-pk=${genrePk}`, {
            params: {
              page: pageNum,
              size: 10,
              sort: "updated"
            }
          })
          .then(response => {
            this.novels = response.data.data.content;
            this.pageLength = response.data.data.totalPages;
            this.page = Number(this.$route.params.pageNum);
          })
          .catch(() => {
            this.errored = true;
          })
          .finally(() => {
            this.loading = false;
          })
      }
    },
    gotoNovelListPage(genrePk, pageNum) {
      this.$router.push(`/novel/list/${genrePk}/${pageNum}`);
    }
  },
  components: {
    NovelCard,
    CategoriBar
  }
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
.card {
  background: #fff;
  border-radius: 4px;
  box-shadow: 0px 14px 80px rgba(34, 35, 58, 0.5);
  max-width: 550px;
  display: flex;
  flex-direction: row;
  border-radius: 25px;
  position: relative;
}
.card h2 {
  margin: 0;
  padding: 0 1rem;
}
.card .title {
  padding: 1rem;
  text-align: right;
  color: black;
  font-weight: bold;
  font-size: 12px;
}
.card .desc {
  padding: 0.5rem 1rem;
  font-size: 12px;
  overflow: hidden;
  display: -webkit-box;
  -webkit-line-clamp: 5;
  -webkit-box-orient: vertical;
}
.card .actions {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  align-items: center;
  padding: 0.5rem 1rem;
}
.card svg {
  width: 85px;
  height: 85px;
  margin: 0 auto;
}

.img-avatar {
  width: 80px;
  height: 80px;
  position: absolute;
  border-radius: 50%;
  border: 6px solid white;
  // background-image: linear-gradient(-60deg, #16a085 0%, #f4d03f 100%);
  top: 15px;
  left: 135px;
  background-color:  rgba(255,83,83,1);
}

.card-text {
  display: grid;
  grid-template-columns: 1fr 2fr;
}

.title-total {
  padding: 2.5em 1.5em 1.5em 1.5em;
}

.total {
  padding: 1rem;
  font-size: 1rem;
  font-weight: bold;;
}

path {
  fill: white;
}

.img-portada {
  width: 100%;
}

.portada {
  width: 196px;
  height: 280px;
  border-top-left-radius: 20px;
  border-bottom-left-radius: 20px;
  // background-image: url("https://comicthumb-phinf.pstatic.net/20190325_108/pocket_1553525187132gW0BF_JPEG/untitled.jpg");
  background-position: bottom center;
  background-size: cover;
}

button {
  border: none;
  background: none;
  font-size: 24px;
  color: #8bc34a;
  cursor: pointer;
  transition:.5s;
  &:hover{
    color: #4CAF50  ;
    transform: rotate(22deg)
  }
}


.no-result {
    color: rgba(255,83,83,1);
    font-size: 3.5rem;
    font-weight: bold;
    text-align: center;
}
.search-result {
    color: black;
    font-size: 1.1rem;
    font-weight: bold;
    text-align: center;
}
.tabs {
    border-bottom: 2px solid;
    border-bottom-color: rgba(255,83,83,1);
}
.tab:first-child {
    border-left-width: 2px;
}
.tab:last-child {
    border-right-width: 2px;
}
.tab {
    border-top: 2px solid;
    border-left: 2px solid;
    border-right: 1px solid;
    border-color:rgba(255,83,83,1);
    color: rgba(255,83,83,1) !important;
    font-weight: bold;
    margin-right: -1px;
}
.v-tab--active {
    background-color: rgba(255,83,83,1) !important;
    color: white !important;
}

.overflow-text {
  overflow:hidden;
  text-overflow: ellipsis;
  white-space: pre-line;
  max-height: 60px;
}
</style>