<template>
    <v-container>
        <v-row>
            <v-col cols="12">
                 <p class="search-result">'{{searchKeyword}}'에 대한 검색결과 입니다.</p>
            </v-col>
            <v-col cols="12">
                <p class="no-result">이런 소설은 어떤가요?</p>
            </v-col>
            <v-col cols="12" class="d-flex justify-center">
                <v-card
                    flat
                    width="75%"
                >
                  <v-row>
                    <!-- 검색 옵션 바 -->
                    <v-col cols="12">
                      <SearchOptionBar></SearchOptionBar>
                    </v-col>
                    <v-col cols="12">
                      <v-row>
                        <v-col 
                          v-for="(novel, index) in novels"
                          :key="index"
                          cols="12"
                          sm="6"
                        >
                          <!-- 태블릿, 데스크탑 화면에선 기존 카드 -->
                          <NovelCard
                            :novelImage= "novel.novelImage"
                            :novelName= "novel.novelName"
                            :novelIntro= "novel.novelIntro"
                            :novelPk= "novel.novelPk"
                            :episodeCount= "novel.episodeCount"
                            class="hidden-xs-only"
                          ></NovelCard>
                          <!-- 모바일 화면에선 한줄 요약 카드 -->
                          <NovelCard2
                            :novelImage= "novel.novelImage"
                            :novelName= "novel.novelName"
                            :novelIntro= "novel.novelIntro"
                            :novelPk= "novel.novelPk"
                            :episodeCount= "novel.episodeCount"
                            class="hidden-sm-and-up"
                          ></NovelCard2>
                        </v-col>
                      </v-row>
                    </v-col>
                    <v-col cols="12">
                      <v-pagination
                        v-model="page"
                        :length="pageLength"
                        color="rgba(255,83,83,1)"
                        @input="gotoSearch(searchKeyword, typeNum, page)"
                      ></v-pagination>
                    </v-col>
                  </v-row>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import http from "../../http-common"
import { mapActions, mapMutations, mapGetters } from "vuex";
import NovelCard from '../card/NovelCard';
import NovelCard2 from '../card/NovelCard2';
import SearchOptionBar from '../search/SearchOptionBar';

export default {
    data() {
      return {
        data:[],
        novels:[],
        searchKeyword:'',
        sort:'updated',
        type:'all', // search 옵션을 type으로 두었고
        typeNum: 0, // 현재 searchPk를 typeNum에 저장
        page: 1,
        pageLength: 0,
        errored: false,
        loading: true
      }
    },
    computed: {
      ...mapGetters(["getIsLogin"]),
      ...mapGetters(["getSession"])
    },
    created() {
      this.getType();
    },
    mounted() {
      // console.log("여긴 마운티드입니다!");
      this.getSearchResult();
      this.searchKeyword = this.$route.params.searchKeyword;
      // console.log("여기에서 마운티드가 끝납니다!");
    },
    methods: {
      getType() {
        if(this.$route.params.searchPk === '0' || this.$route.params.searchPk === 0) {
          this.type = 'all';
          this.typeNum = 0;
        } else if(this.$route.params.searchPk === '1' || this.$route.params.searchPk === 1) {
          this.type = 'author_name';
          this.typeNum = 1;
        } else if(this.$route.params.searchPk === '2' || this.$route.params.searchPk === 2) {
          this.type = 'novel_name';
          this.typeNum = 2;
        } else if(this.$route.params.searchPk === '3' || this.$route.params.searchPk === 3) {
          this.type = 'hashtag';
          this.typeNum = 3;
        } else {
          this.type = 'all';
          this.typeNum = 0;
        }
      },
      getSearchResult() {
        // console.log("함수 실행");
        http
          .get(`/search/${this.type}`, {
            params:{
              genrePk: 0,
              memPk: this.getSession.memPk,
              page: Number(this.$route.params.pageNum),
              size: 10,
              sort: this.sort,
              word: this.$route.params.searchKeyword,
            }
          })
          .then(response => {
            // console.log(response.data.data.content);
            this.novels = response.data.data.content;
            this.pageLength = response.data.data.totalPages;
            this.page = Number(this.$route.params.pageNum);
          })
          .catch((e) => {
            console.log(e);
            this.errored = true;
          })
          .finally(() => {
            this.loading = false;
          })
      },
      gotoSearch(searchKeyword, searchPk, pageNum) {
        this.$router.push(`/search/${searchKeyword}/${searchPk}/${pageNum}`)
      }
    },
    components: {
      NovelCard,
      NovelCard2,
      SearchOptionBar
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
  cursor:pointer;
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
  width: 100%;
  height: 100%;
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
  white-space: nowrap;
  width: 100px;
}
</style>