<template>
  <div class="signup">
    <div class="title-signup">회원가입</div>
    <br>
    <div class="signup-wrap">
      <div class="input-wrap">
        <!-- 입력받을 정보: 아이디, 닉네임, 비밀번호, 비밀번호확인, 이메일, 휴대전화, 생년월일, 성별, 동의 -->
        <!-- mem_id, mem_nick, mem_pw, mem_email, mem_phone, mem_birth, mem_gender -->
        <div class="input-row">
          <label for="id">아이디</label>
          <input type="text" id="id" v-model="mem_id" placeholder="5글자 이상의 영문 및 숫자를 입력해주세요">
        </div>
        <div class="input-row">
          <label for="id">닉네임</label>
          <input type="text" id="nick" v-model="mem_nick" placeholder="2글자 이상 입력해주세요">
        </div>
        <div class="input-row">
          <label for="password">비밀번호</label>
          <input type="password" id="pw" v-model="mem_pw" placeholder="영문, 숫자 포함 10자리 이상 입력해주세요">
        </div>
        <div class="input-row">
          <label for="password">비밀번호확인</label>
          <input type="password" id="pw" v-model="mem_pw_check" placeholder="영문, 숫자 포함 10자리 이상 입력해주세요">
        </div>
        <div class="input-row">
          <label for="id">이메일</label>
          <input type="text" id="email" v-model="mem_email" placeholder="사용중인 이메일 주소를 입력해주세요">
        </div>
        <div class="input-row">
          <label for="id">휴대전화</label>
          <input type="text" id="phone" v-model="mem_phone" placeholder="'-'를 제외한 숫자를 입력해주세요">
        </div>
        <div class="input-row">
          <label for="id">생년월일</label>
          <input
            type="text"
            id="birth"
            v-model="mem_birth"
            placeholder="'-'를 제외한 숫자를 입력해주세요 예) 19991231"
          >
        </div>

        <!-- 성별 input 고치기~ -->
        <div class="input-row">
          <label for="gender">성별</label>
          <input type="text" id="gender" v-model="mem_gender" placeholder="성별을 선택하세요">
        </div>
      </div>

      <!-- 이용약관 추가~ -->
      <div class="check-wrap">
        <div class="check-row">
          <input type="checkbox" id="terms1" name="terms1" value="terms1" v-model="terms1">
          <label for="terms1">(필수) EPISODE 서비스약관, 유료이용약관, 개인정보 취급방침에 동의합니다.</label>
        </div>
        <div class="check-row">
          <input type="checkbox" id="terms2" name="terms2" value="terms2" v-model="terms2">
          <label for="terms2">(선택) APP PUSH 알림</label>
        </div>
        <div class="check-row">
          <input type="checkbox" id="terms3" name="terms3" value="terms3" v-model="terms3">
          <label for="terms3">(선택) 뉴스레터, 문자안내 수신</label>
        </div>
        <div class="check-notice">*수신 동의 시 다양한 이벤트 정보와 무료 쿠폰 등의 혜택을 받으실 수 있습니다.</div>
      </div>
    </div>

    <div class="btn-wrap">
      <button class="btn-signup" v-on:click="signup">가입완료</button>
    </div>
  </div>
</template>

<script>
import http from '../../http-common'
export default {
  name: 'signup',
  data () {
    return {
      mem_id: '',
      mem_nick: '',
      mem_pw: '',
      mem_pw_check: '',
      mem_email: '',
      mem_phone: '',
      mem_birth: '',
      mem_gender: '',
      terms1: false,
      terms2: false,
      terms3: false,
      loading: true,
      errored: false
    }
  },
  methods: {
    signup () {
      if (this.mem_id && this.mem_nick && this.mem_pw && this.mem_pw_check && this.mem_email && this.mem_phone && this.mem_birth && this.mem_gender) {
        if (this.terms1) {
          if (this.mem_pw === this.mem_pw_check) {
            // 여기에서 axios post 함수 시작
            http
              .post('/singup', {
                mem_id: this.mem_id,
                mem_email: this.mem_email,
                mem_pw: this.mem_pw,
                mem_nick: this.mem_nick,
                mem_phone: this.mem_phone,
                mem_birth: this.mem_birth,
                mem_gender: this.mem_gender
                // 일단 terms1, 2, 3은 따로 저장하지 않음
              })
              .then(response => {
                // 일단 이 부분은 백엔드에서 어떤걸 보내줄지에 따라 함수를 구현해야함.
                if (response.data.success === true) {
                  alert('회원 가입 완료!')
                  console.log(response.data)
                  this.$router.push('/')
                } else {
                  alert(response.data.message)
                }
              })
              .catch(() => {
                alert('아이디나 닉네임이 중복됩니다.')
                this.errored = true
              })
              .finally(() => {
                this.loading = false
              })
          } else {
            alert('비밀번호와 비밀번호확인이 일치하지 않습니다.')
          }
        } else {
          alert('필수 항목을 동의하셔야 합니다.')
        }
      } else {
        alert('필수 항목들을 입력하지 않으셨습니다.')
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.signup {
  position: absolute;
  width: 75vw;
  margin-left: 10vw;
  margin-right: 15vw;
  margin-top: 10px;
  margin-bottom: 60px;
  padding-top: 10px;
  padding-bottom: 125px;
}
.signup .title-signup {
  margin-top: 60px;
  margin-bottom: 40px;
  font-weight: 500;
  font-size: 22px;
}
.input-wrap {
  padding-bottom: 16px;
}

.input-row {
  width: 100%;
  height: 56px;

  label {
    width: 100%;
  }
  input {
    border: none;
    border-right: 0px;
    border-top: 0px;
    width: 100%;
    border-bottom: solid 1px #8590996e;
    float: right;
    background: 0 0; //? 이게 무슨 속성일까!
    box-sizing: border-box;
    padding: 7px 8px 7px;
    outline-style: none; /* 포커스시 발생하는 효과 제거 */

    -webkit-appearance: none;
    -moz-appearance: none;
    appearance: none;
  }
  input::placeholder {
    color: #424b536e;
  }
}
.btn-wrap {
  text-align: center;
  margin-top: 30px;
}
.btn-signup {
  width: 80%;
  height: 42px;
  border: 0;
  outline: 0;
  border-radius: 21px;
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
    box-shadow: 71vw 0 0 0 rgba(230, 12, 12, 0.493) inset;
  }
}

@media (min-width: 600px) {
  .signup {
    width: 450px;
    margin-left: calc((100vw - 450px) / 2);
    margin-right: calc((100vw - 450px) / 2);
  }
}
</style>
