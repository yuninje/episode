<template>
  <div class="test-login">
    <span>테스트 라인입니다.</span>
    <span id="greeting" v-if="getIsLogin">{{ getSession.memId }} 님 환영합니다.</span>
    <span>
      <button v-if="!getIsLogin">
        <router-link to="/signin">로그인</router-link>
      </button>
      <button v-if="getIsLogin" v-on:click="signout">로그아웃</button>
    </span>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapGetters } from "vuex";

export default {
  name: "TestLogin",
  data: () => {
    return {};
  },

  computed: {
    ...mapGetters(["getIsLogin"]),
    ...mapGetters(["getSession"])
  },
  methods: {
    signout() {
      this.$store.dispatch("signout");
    },
    refresh() {}
  }
};
</script>

<style lang="scss" scoped>
.test-login {
  float: right;
}
</style>
