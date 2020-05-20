<template>
    <v-container>
        <v-row>
            <v-col cols="12">
                 <p class="search-result">현재 페이지는 작업중인 페이지입니다.</p>
            </v-col>
            <v-col cols="12">
                <p class="no-result">이런 소설은 어떤가요?</p>
            </v-col>
            <v-col cols="12" class="d-flex justify-center">
                <v-card
                    flat
                    width="75%"
                >
                    <v-tabs
                        v-model="tab"
                        background-color="white"
                        color="rgba(255,83,83,1)"
                        class="tabs"
                    >
                        <v-tabs-slider color="transparent"></v-tabs-slider>
                        <v-tab
                            v-for="item in items"
                            :key="item.tab"
                            class="tab"
                        >
                            {{item.tab}}
                        </v-tab>
                    </v-tabs>
                    <v-tabs-items v-model="tab">
                        <v-tab-item
                            v-for="item in items"
                            :key="item.tab"
                        >
                            {{item.content}}
                        </v-tab-item>
                    </v-tabs-items>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
export default {
    data() {
        return {
            tab: null,
            items: [
                { tab: '전체', content: 'Tab 1 Content' },
                { tab: '판타지', content: 'Tab 2 Content' },
                { tab: '무협', content: 'Tab 3 Content' },
                { tab: '로맨스', content: 'Tab 4 Content' },
                { tab: '현판', content: 'Tab 5 Content' },
            ],
        }
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
  max-width: 400px;
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
  color: green;
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
  background-image: linear-gradient(-60deg, #16a085 0%, #f4d03f 100%);
  top: 15px;
  left: 85px;
}

.card-text {
  display: grid;
  grid-template-columns: 1fr 2fr;
}

.title-total {
  padding: 2.5em 1.5em 1.5em 1.5em;
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
  background-image: url("https://m.media-amazon.com/images/S/aplus-media/vc/cab6b08a-dd8f-4534-b845-e33489e91240._CR75,0,300,300_PT0_SX300__.jpg");
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
    border-left: 1px solid;
    border-right: 1px solid;
    border-color:rgba(255,83,83,1);
    color: black !important;
}
.v-tab--active {
    background-color: rgba(255,83,83,1) !important;
    color: white !important;
}
</style>