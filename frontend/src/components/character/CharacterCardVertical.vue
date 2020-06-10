<template>
  <v-card :color="`${color}`" class="vert-card-space">
    <v-row>
      <v-col sm="6">
        <v-list-item color="rgba(0,0,0,0.4)" dark>
          <v-list-item-content>
            <v-list-item-title class="title">{{ name }}</v-list-item-title>
            <v-list-item-subtitle>나이 : {{ age }}세</v-list-item-subtitle>
            <v-list-item-subtitle>직업 : {{ job }}</v-list-item-subtitle>
            <v-list-item-subtitle>역할 : {{ role }}</v-list-item-subtitle>
            <v-list-item-subtitle>특이사항 : {{ significant }}</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
      </v-col>
      <v-col sm="6" class="px-2">
          <v-img :src="`${src}`" aspect-ratio="1" width="100"></v-img>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
export default {
  props: ["name", "age", "job", "role", "significant", "src", "color"]
};
</script>
<style lang="scss" scoped>
.vert-card-space {
  margin: 12px 0;
  width: 300px;
}
</style>

