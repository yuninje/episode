<<template>
    <div class="signup">
        <v-form action="" method="post" id="_signupForm" name="signupForm" @submit.prevent="signup">
            <v-row justify="center">
                <v-card max-width="100%" style="margin-bottom:50px" outlined>
                    <v-card-title>
                        <span style="margin: 10px;">회원가입</span>
                    </v-card-title>
                    <v-card-text>
                        <v-container>
                            <v-row>
                                <v-col cols="12">
                                    <p class="item-title">아이디</p>
                                    <ValidationProvider rules="required|alpha_num|min:5|max:20" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_id"
                                        placeholder="5글자 이상 20글자 이하의 영문 및 숫자를 입력해주세요"
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">닉네임</p>
                                    <ValidationProvider rules="required|min:2" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_nick"
                                        placeholder="2글자 이상 입력해주세요"
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">비밀번호</p>
                                    <ValidationProvider rules="required|min:10|max:20" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_pw"
                                        type="password"
                                        placeholder="영문, 숫자 포함 10자리 이상 20자리 이하로 입력해주세요"
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">비밀번호 확인</p>
                                    <ValidationProvider rules="required|min:10|max:20" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_pw_check"
                                        type="password"
                                        placeholder="비밀번호를 다시 입력해주세요"
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">이메일</p>
                                    <ValidationProvider rules="required|email" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_email"
                                        placeholder="사용중인 이메일 주소를 입력해주세요"
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">휴대전화</p>
                                    <ValidationProvider rules="required|numeric" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_phone"
                                        placeholder="'-'를 제외한 숫자를 입력해주세요."
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">생년월일</p>
                                    <ValidationProvider rules="required|numeric|length:8" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_birth"
                                        placeholder="'-'를 제외한 숫자를 입력해주세요 예) 19991231"
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">성별</p>
                                    <v-radio-group v-model="mem_gender" row>
                                        <v-radio
                                            label="남성"
                                            value="남성"
                                        >
                                        </v-radio>
                                        <v-radio
                                            label="여성"
                                            value="여성"
                                        >
                                        </v-radio>
                                    </v-radio-group>
                                </v-col>
                                <v-col cols="12">
                                    <v-checkbox v-model="terms1" class="mx-0 px-0" label="(필수) EPISODE 서비스약관, 유료이용약관, 개인정보 취급방침에 동의합니다."></v-checkbox>
                                    <v-checkbox v-model="terms2" class="mx-0 px-0" label="(선택) APP PUSH 알림"></v-checkbox>
                                    <v-checkbox v-model="terms3" class="mx-0 px-0" label="(선택) 뉴스레터, 문자안내 수신"></v-checkbox>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card-text>
                </v-card>
            </v-row>
        </v-form>
    </div>
</template>

<script>
import http from '../../http-common'
import { ValidationProvider, extend } from 'vee-validate';
import {required, email, alpha_num, numeric, min, max, length} from 'vee-validate/dist/rules';

// extend('min', {
//   getMessage(field, args) {
//     const length = args.length
//     return `해당 영역은 ${length}글자 이상으로 입력해야합니다.`
//   },
//   validate(value, args) {
//     return value.length >= args.length;
//   },
//   params: ['length']
// });

extend('min', {
    ...min,
    message: (field, args) => `${args.length}글자 이상 입력해야 합니다.`
});

extend('max', {
    ...max,
    message: (field, args) => `${args.length}글자 이하로 입력해야 합니다.`
})

extend('required', {
    ...required,
    message: (field, value) => "필수입력 항목입니다."
});

extend('email', {
    ...email,
    message: "올바른 이메일 형식이 아닙니다."
});

extend('alpha_num', {
    ...alpha_num,
    message: "영어와 숫자만으로 구성해야합니다."
});

extend('numeric', {
    ...numeric,
    message: "숫자만 입력해야합니다."
});

extend('length', {
    ...length,
    message: (field, args) => `'-'를 제외한 ${args.length}자리의 숫자를 입력해주세요 예) 19991231`
})

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
  },
  components: {
    ValidationProvider
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

.item-title {
    font-size: 1.2rem;
    font-weight: bold;
}
.error-message {
    color: red;
}
</style>