<template>
  <v-col col="3">
    <v-card color="blue" >
      <v-row>
        <v-col cols="8">
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
        <v-col cols="4">
          <v-avatar tile size="100%" max-width="100" max-height="100" class="px-2">
            <v-img :src="`${src}`"></v-img>
          </v-avatar>
        </v-col>
      </v-row>
    </v-card>
  </v-col>
</template>

<script>
export default {
  // name: CharCard,
  props: ["name", "age", "job", "role", "significant", "src"]
};
</script>

