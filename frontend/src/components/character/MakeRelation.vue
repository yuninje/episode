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
            <v-img :aspect-ratio="1" :src="charImg1"/>
            <v-select
              id="mySelect"
              @change="changeChar1"
              :items="charInfo"
              item-text="characterName"
              item-value="characterPk"
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
                  v-model="rltName1"
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
                  v-model="rltName2"
                  id="relation2"
                  placeholder="어떤 관계인가요?"
                >
              </div>
            </v-row>
          </v-col>

          <!-- select character 2 -->
          <v-col sm="4">
            <v-img :aspect-ratio="1" :src="charImg2"/>
            <v-select
              @change="changeChar2"
              :items="charInfo"
              item-text="characterName"
              item-value="characterPk"
              placeholder="캐릭터 선택"
              color="#333"
            ></v-select>
          </v-col>
        </v-row>

        <!-- button create relation -->
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn @click="saveRlt()" color="rgba(255,83,83,1" text>확인</v-btn>
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
      char1:null,
      char2:null,

      charImg1: defaultImg,
      charImg2: defaultImg,

      rltName1: "",
      rltName2: "",

      relationInfo: {
        relationArrowKinds: "",
        relationrColor: "",
        relationrName: "",
        who: 0,
        whom: 0
      }
    };
  },
  computed: {
    ...mapGetters("storeNovChar", {
      charInfo: "getCharacterInfo"
    })
  },
  methods: {
    ...mapActions("storeNovChar", {
      postRelation: "postRelation",
    }),

    /** select한 캐릭릭터의 charInfo를 찾는다 */
    findCharInfo(select) {
      return this.charInfo.find(e => e.characterPk === select);
    },
    /** char선택 : 선택된 char의 charInfo를 찾고, charImg 이미지를 바꾼다 */
    changeChar1(select) {
      const char = this.findCharInfo(select)
      this.char1 = char.characterPk
      this.charImg1 = char.characterImage
    },
    changeChar2(select) {
      const char = this.findCharInfo(select)
      this.char2 = char.characterPk
      this.charImg2 = char.characterImage
    },
    /** api 요청할 데이터를 set한다 */
    setRelationData(name, from, to) {
      let { relationArrowKinds, relationrColor, relationrName, who, whom } = this.relationInfo;
      relationrName = name
      who = from
      whom = to
      return  { relationArrowKinds, relationrColor, relationrName, who, whom }
    },
    /** 관계 만들기 확인버튼 클릭시 postRelation 메서드를 호출한다 */
    saveRlt() {
      let char1 = this.char1
      let char2 = this.char2
      if(char1!=null && char2!=null) {
        if(char1!=char2) {

          if(this.rltName1!="") {
            const data = this.setRelationData(this.rltName1, char1, char2)
            this.postRelation(data)
          }
          if(this.rltName2!="") {
            const data = this.setRelationData(this.rltName2, char2, char1)
            this.postRelation(data)
          }
          
        }else {
          alert("\n동일한 캐릭터의 관계를 생성할 수 없습니다.\n서로 다른 캐릭터를 선택하세요")
        }

      } else {
        alert("\n캐릭터를 선택하지 않았습니다!\n캐릭터를 선택하세요")
      }
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


