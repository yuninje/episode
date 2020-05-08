<template>
  <div class="signin">
    <div class="title-signin">EPISODE</div>
    <div class="input-wrap">
      <div class="input-row">
        <label for="id">아이디</label>
        <input type="text" v-model="mem_id" v-if="!idSaveCheck" id="id" placeholder="아이디(이메일)">
        <input type="text" v-model="mem_id" v-if="idSaveCheck">
      </div>
      <div class="input-row">
        <label for="password">비밀번호</label>
        <input
          type="password"
          id="pw"
          v-model="mem_pw"
          @keyup.enter="signin"
          placeholder="비밀번호를 입력하세요."
        >
      </div>
    </div>
    <div class="check-row">
      <input type="checkbox" id="idSaveCheck" v-model="idSaveCheck">
      <label for="idSaveCheck">아이디 저장</label>
    </div>
    <div class="btn-wrap">
      <button class="btn-signin" v-on:click="signin">로그인</button>
      <br>
      <div class="btn-push-wrap">
        <router-link class="btn-push" :to="'/signup'">회원가입</router-link>|
        <router-link class="btn-push" :to="'/signup'">아이디찾기</router-link>|
        <router-link class="btn-push" :to="'/signup'">비밀번호찾기</router-link>
      </div>
    </div>
  </div>
</template>

<script>
import { mapActions, mapMutations, mapGetters } from "vuex";

export default {
  data() {
    return {
      mem_id: "",
      mem_pw: "",
      idSaveCheck: false,
    };
  },
  computed: {
    if(checked) {
      console.log("checked")
      this.mem_id="test2"
    }
  },
  methods: {
    ...mapActions(["postSignin"]),

    signin() {
      let { mem_id, mem_pw } = this;
      if (this.check(mem_id, mem_pw)) {
        let data = {
          mem_id,
          mem_pw
        };
        this.postSignin(data);
      }
    },
    check(id, pw) {
      if (id != "" && pw != "") return true;
      if (id == "") {
        alert("아이디를 입력해주세요");
        return;
      } else if (pw == "") {
        alert("비밀번호를 입력해주세요");
        return;
      }
    }
  }
};
</script>

<style lang="scss" scoped>
.signin {
  position: absolute;
  width: 75vw;
  margin-left: 10vw;
  margin-right: 15vw;
  margin-top: 10px;
  margin-bottom: 60px;
  padding-top: 10px;
  padding-bottom: 125px;
}
.title-signin {
  margin-top: 60px;
  margin-bottom: 40px;
  font-weight: 800;
  font-size: 2em;
}

.input-wrap {
  padding-bottom: 16px;
}
.input-row {
  width: 100%;
  height: 56px;
  border-bottom: solid 1px #8590996e;

  label {
    display: inline-block;
    line-height: 56px;
    max-width: 100%;
  }
  input {
    border: none;
    border-right: 0px;
    border-top: 0px;
    width: 78%;
    height: 56px;
    line-height: 56px;
    float: right;
    background: 0 0; //? 이게 무슨 속성일까!
    box-sizing: border-box;
    padding: 7px 8px 7px;
    outline-style: none;
  }
  input::placeholder {
    color: #424b536e;
  }
}
.check-row {
  float: right;
}
.btn-wrap {
  text-align: center;
  margin-top: 42px;
}
.btn-push-wrap {
  color: rgba(0, 0, 0, 0.8);
  font-size: 14px;
  margin-top: 24px;
  display: flex;

  a:link {
    text-decoration: none;
  }
  a:hover {
    text-decoration: underline;
  }
  a:visited {
    color: rgba(0, 0, 0, 0.8);
  }
}
.btn-push {
  color: rgba(0, 0, 0, 0.8);
  flex: 1;
  text-align: center;
}
.btn-signin {
  width: 100%;
  height: 50px;
  border: 0;
  outline: 0;
  border-radius: 25px;
  background: rgb(196, 40, 40);
  color: white;
  font-size: 16px;
  font-weight: bold;
  text-align: center;
  -webkit-transition: all 0.1s;
  -moz-transition: all 0.1s;
  -o-transition: all 0.1s;
  transition: all 0.1s;
  transition: all 0.1s;

  &:hover {
    color: #fff;
    box-shadow: 90vw 0 0 0 rgba(230, 12, 12, 0.493) inset;
  }
}

@media (min-width: 600px) {
  .signin {
    width: 400px;
    margin-left: calc((100vw - 400px) / 2);
    margin-right: calc((100vw - 400px) / 2);
  }
}
</style>
