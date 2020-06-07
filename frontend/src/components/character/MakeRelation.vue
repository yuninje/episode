<template>
  <v-card>
    <!-- {{ charInfo }} -->

    <!-- 타이틀 -->
    <v-card-actions>
      <div class="make-rlt-title">관계 만들기</div>
      <v-spacer></v-spacer>
      <v-btn icon @click="show = !show">
        <v-icon>{{ show ? 'mdi-chevron-up' : 'mdi-chevron-down' }}</v-icon>
      </v-btn>
    </v-card-actions>


    <!-- 접기 -->
    <v-expand-transition>
      <div v-show="show">
        <v-row align="center" justify="center" style="padding: 8px 16px; margin-bottom: -16px;">

          <!-- select character 1 -->
          <v-col sm="4">
            <v-img :aspect-ratio="1" :src="char1Img"/>
            <v-select
              id="mySelect"
              @change="changeChar1"
              v-model="char1Img"
              :items="charInfo"
              item-text="characterName"
              item-value="characterImage"
              placeholder="캐릭터 선택"
              color="#333"
            ></v-select>
          </v-col>

          <v-col sm="4">
            <!-- relationship (char1 -> char2) -->
            <v-row justify="center">
              <div class="input-row">
                <input
                  type="text"
                  name="relation1"
                  :v-model="node_sid"
                  id="relation1"
                  placeholder="어떤 관계인가요?"
                >
              </div>
              <hr class="arrow-line" align="left">
              <div class="arrow-right"></div>
              <!-- <v-img :aspect-ratio="16/9" src="@/assets/images/arrow-R.png"></v-img> -->
            </v-row>

            <!-- relationship (char1 <- char2) -->
            <v-row justify="center">
              <div class="arrow-left"></div>
              <hr class="arrow-line" align="right">
              <div class="input-row">
                <input
                  type="text"
                  name="relation2"
                  :v-model="node_tid"
                  id="relation2"
                  placeholder="어떤 관계인가요?"
                >
              </div>
            </v-row>
          </v-col>

          <!-- select character 2 -->
          <v-col sm="4">
            <v-img :aspect-ratio="1" :src="char2Img"/>
            <v-select
              @change="changeChar2"
              v-model="char2Img"
              :items="charInfo"
              item-text="characterName"
              item-value="characterImage"
              placeholder="캐릭터 선택"
              color="#333"
            ></v-select>
          </v-col>
        </v-row>

        <!-- button create relation -->
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="rgba(255,83,83,1" text>확인</v-btn>
        </v-card-actions>
        
      </div>
    </v-expand-transition>

  </v-card>
</template>

<script>
import { mapActions, mapMutations, mapGetters } from "vuex";

const defaultImg = require(`@/assets/images/selectCharCover.png`);

export default {
  // props: ["who", "whom"],
  data() {
    return {
      show: false,
      char1Img: defaultImg,
      char2Img: defaultImg,
      node_sid: "",
      node_tid: "",
    };
  },
  computed: {
    ...mapGetters("storeNovChar", {
      charInfo: "getCharacterInfo"
    })
  },
  methods: {
    changeChar1(select) {
      // console.log(select);
    },
    changeChar2(select) {
      // console.log(select);
    }
  }
};
</script>

<style lang="scss" scoped>
input {
  text-align: center;
}
.input-row {
  input,
  span {
    border: none;
    border-right: 0px;
    border-top: 0px;
    background: 0 0; //? 이게 무슨 속성일까!
    box-sizing: border-box;
    outline-style: none;
  }
  input::placeholder {
    color: #424b536e;
  }
}
.arrow-line {
  margin: auto 0;
  width: 90%;
}
.arrow-right {
  width: 0;
  height: 0;
  border-top: 6px solid transparent;
  border-bottom: 6px solid transparent;
  border-left: 7px solid black;
  vertical-align: middle;
}
.arrow-left {
  width: 0;
  height: 0;
  border-top: 6px solid transparent;
  border-bottom: 6px solid transparent;
  border-right: 7px solid black;
  vertical-align: middle;
}
.make-rlt-title {
  margin-left: 10px;
}
</style>


