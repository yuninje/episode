<template>
  <v-app id="app">
    <div id="nav">
      <NavigationBar/>
    </div>
    <router-view/>
  </v-app>
</template>

<script>
import NavigationBar from "@/components/main/NavigationBar"
import TestLogin from "@/components/member/TestLoginC"
export default {
  components: {
    TestLogin,
    NavigationBar
  },

  
}
</script>

<style lang="scss" scoped>

</style>