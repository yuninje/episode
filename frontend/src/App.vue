<template>
  <div id="app">
    <div id="nav">
      <TestLogin />
    </div>
    <router-view/>
  </div>
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

