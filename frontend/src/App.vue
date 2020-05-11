<template>
  <v-app id="app">
    <div id="nav">
      <TestLogin />
    </div>
    <router-view/>
  </v-app>
</template>

<script>
import TestLogin from "@/components/member/TestLoginC"
export default {
  components: {
    TestLogin
  },

  
}
</script>

<style lang="scss" scoped>

</style>