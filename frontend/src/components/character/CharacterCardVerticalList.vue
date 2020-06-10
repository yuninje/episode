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


