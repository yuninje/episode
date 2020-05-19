<template>
    <v-container>
      <div>
        <span class="sub-title-red">느낌별 소설 모음</span>
      </div>
        <v-row>
          <!-- 모바일은 괜찮은데, 태블릿 전용으로도 비율 줘야할듯 -->
          <v-col 
            cols="6"
            sm="4"
            md="2"
            v-for="(item, i) in items"
            :key=i
          >
            <v-img
              :src="require(`@/assets/images/${item.name}`)"
              aspect-ratio=1
              @click="gotoFeel()"
              class="feel-content"
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
          name: 'feel1.png',
        },
        {
          name: 'feel2.png',
        },
        {
          name: 'feel3.png',
        },
        {
          name: 'feel4.png',
        },
        {
          name: 'feel5.png',
        },
        {
          name: 'feel6.png',
        },
      ]
    }
  },
  methods: {
    gotoFeel() {
      this.$router.push('/feel');
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
.feel-content {
  cursor:pointer;
}
</style>

