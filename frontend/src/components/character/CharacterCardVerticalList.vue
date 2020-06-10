<template>
  <div class="scroll-height">
    <v-list>
      <char-card
        v-for="(char, i) in charInfo"
        :key="`character-${i}`"
        :name="char.characterName"
        :age="char.characterAge"
        :job="char.characterJob"
        :role="char.characterRole"
        :significant="char.characterSignificant"
        :src="char.characterImage"
        :color="getRandomRgb(char.characterName)"
      />
    </v-list>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapGetters } from "vuex";
import CharCard from "../character/CharacterCardVertical";

export default {
  props: ["name", "age", "job", "role", "significant", "src"],
  data() {
    return {
      window: {
        height: 0
      },
    };
  },
  components: {
    CharCard
  },
  created() {
    window.addEventListener("resize", this.handleResize);
    this.handleResize();
  },
  destroyed() {
    window.removeEventListener("resize", this.handleResize);
  },
  computed: {
    ...mapGetters("storeNovChar", {
      charInfo: "getCharacterInfo"
    })
  },
  methods: {
    handleResize() {
      this.window.height = window.innerHeight;
    },
    djb2(str){
      let hash = 5381;
      for (let i = 0; i < str.length; i++) {
        hash = ((hash << 5) + hash) + str.charCodeAt(i); /* hash * 33 + c */
      }
      return hash;
    }
    ,getRandomRgb(str) {
      let hash = this.djb2(str);
      let r = (hash & 0xFF0000) >> 16;
      let g = (hash & 0x00FF00) >> 8;
      let b = hash & 0x0000FF;
      return "#" + ("0" + r.toString(16)).substr(-2) + ("0" + g.toString(16)).substr(-2) + ("0" + b.toString(16)).substr(-2);
    }
  }
};
</script>
<style lang="scss" scoped>
.scroll-height {
  height: 680px; 
  // height: "`(${this.window.height}-300)px`";
  overflow-x: hidden;
  overflow-y: auto;
}
</style>


