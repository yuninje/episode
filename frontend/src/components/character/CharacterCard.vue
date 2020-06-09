<template>
  <v-card color="blue" >
    <v-row>
      <v-col cols="6">
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
      <v-col cols="6">
        <v-avatar tile size="100%" class="px-2">
          <v-img :src="`${src}`" aspect-ratio="1"></v-img>
        </v-avatar>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
export default {
  // name: CharCard,
  props: ["name", "age", "job", "role", "significant", "src"]
};
</script>

