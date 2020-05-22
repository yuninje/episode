<<template>
    <div class="signup">
        <v-form action="" method="post" id="_signupForm" name="signupForm" @submit.prevent="signup">
            <v-row justify="center">
                <v-card class="signup-card" max-width="100%" style="margin-bottom:50px" outlined >
                    <v-card-title>
                        <span style="margin: 10px;">회원가입</span>
                    </v-card-title>
                    <v-card-text>
                        <v-container>
                            <v-row>
                                <v-col cols="12">
                                    <p class="item-title">아이디<span class="item-required">*</span></p>
                                    <ValidationProvider rules="required|alpha_num|min:5|max:20|checkInput:1" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_id"
                                        placeholder="5글자 이상 20글자 이하의 영문 및 숫자를 입력해주세요"
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">닉네임<span class="item-required">*</span></p>
                                    <ValidationProvider rules="required|min:2|checkInput:2" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_nick"
                                        placeholder="2글자 이상 입력해주세요"
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">비밀번호<span class="item-required">*</span></p>
                                    <ValidationProvider rules="required|min:10|max:20|checkInput:3" v-slot="{errors}" vid="pw">
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
                                    <p class="item-title">비밀번호 확인<span class="item-required">*</span></p>
                                    <ValidationProvider rules="required|min:10|max:20|confirmed:pw" v-slot="{errors}">
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
                                    <p class="item-title">이메일<span class="item-required">*</span></p>
                                    <ValidationProvider rules="required|email|checkInput:4" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_email"
                                        placeholder="사용중인 이메일 주소를 입력해주세요"
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">휴대전화<span class="item-required">*</span></p>
                                    <ValidationProvider rules="required|numeric|checkInput:5" v-slot="{errors}">
                                    <v-text-field
                                        v-model="mem_phone"
                                        placeholder="'-'를 제외한 숫자를 입력해주세요."
                                        required
                                    ></v-text-field>
                                    <span class="error-message">{{errors[0]}}</span>
                                    </ValidationProvider>
                                </v-col>
                                <v-col cols="12">
                                    <p class="item-title">생년월일<span class="item-required">*</span></p>
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
                                    <p class="item-title">성별<span class="item-required">*</span></p>
                                    <v-radio-group v-model="mem_gender" row>
                                        <v-radio
                                            label="남성"
                                            color="rgba(192,0,0,1)"
                                            value="male"
                                        >
                                        </v-radio>
                                        <v-radio
                                            label="여성"
                                            color="rgba(192,0,0,1)"
                                            value="female"
                                        >
                                        </v-radio>
                                    </v-radio-group>
                                </v-col>
                                <v-col cols="12" class="pa-0">
                                    <v-checkbox color="rgba(192,0,0,1)" v-model="terms1" class="signup-checkbox" label="(필수) EPISODE 서비스약관, 유료이용약관, 개인정보 취급방침에 동의합니다."></v-checkbox>
                                    <v-checkbox color="rgba(192,0,0,1)" v-model="terms2" class="signup-checkbox" label="(선택) APP PUSH 알림"></v-checkbox>
                                    <v-checkbox color="rgba(192,0,0,1)" v-model="terms3" class="signup-checkbox" label="(선택) 뉴스레터, 문자안내 수신"></v-checkbox>
                                </v-col>
                                <v-col cols="12" class="pa-0">
                                    <span class="check-notice">* 수신 동의 시 다양한 이벤트 정보와 무료 쿠폰 등의 혜택을 받으실 수 있습니다.</span>
                                </v-col>
                            </v-row>
                        </v-container>
                    </v-card-text>
                    <v-card-actions >
                        <v-row justify="center">
                          <v-btn rounded color="rgba(192,0,0,1)" width="80%" @click="signup()" class="signup-btn">가입완료</v-btn>
                        </v-row>
                    </v-card-actions>
                    <br/>
                </v-card>
            </v-row>
        </v-form>
    </div>
</template>

<script>
import http from '../../http-common'
import { ValidationProvider, extend } from 'vee-validate';
import {required, email, alpha_num, numeric, min, max, length, confirmed} from 'vee-validate/dist/rules';

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

extend('checkInput', async (value, args) => {
  let response = await http.post(`/members/check/${args}`, value);
  if(response.data.state == "ok"){
    return true;
  }
  else {
    if(args == 1){
      return "중복된 아이디입니다.";
    }
    else if(args == 2){
      return "중복된 닉네임입니다.";
    }
    else if(args == 3){
      return "올바르지 않은 암호입니다. 영어와 숫자가 각각 최소 1개씩 반드시 포함되어야 합니다.";
    }
    else if(args == 4){
      return "중복된 이메일입니다.";
    }
    else if(args == 5){
      return "중복된 전화번호입니다.";
    }
  }
});

extend('confirmed', {
  ...confirmed,
  message: "비밀번호가 일치하지 않습니다."
});

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
});

export default {
  name: 'signup',
  data () {
    return {
      error_msg: ['', '', '', '', '', ''],
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
        // console.log("mem_id : " + this.mem_id)
        // console.log("mem_nick : " + this.mem_nick)
        // console.log("mem_pw : " + this.mem_pw)
        // console.log("mem_email : " + this.mem_email)
        // console.log("mem_phone : " + this.mem_phone)
        // console.log("mem_birth : " + this.mem_birth)
        // console.log("mem_gender : " + this.mem_gender)
        let gender = false;
        if(this.mem_gender === "male") gender = true;
        else if(this.mem_gender == "female") gender = false;
        if (this.terms1) {
          if (this.mem_pw === this.mem_pw_check) {
            // 여기에서 axios post 함수 시작
            http
              .post('/members', {
                memId: this.mem_id,
                memEmail: this.mem_email,
                memPw: this.mem_pw,
                memNick: this.mem_nick,
                memPhone: this.mem_phone,
                memBirth: this.mem_birth,
                memGender: gender
                // 일단 terms1, 2, 3은 따로 저장하지 않음
              })
              .then(response => {
                // 일단 이 부분은 백엔드에서 어떤걸 보내줄지에 따라 함수를 구현해야함.
                if (response.data.state === "ok") {
                  alert('회원 가입 완료!')
                  console.log(response.data)
                  this.$router.push('/')
                } else {
                  alert("이거 뜨면 뭔가 문제")
                }
              })
              .catch(() => {
                alert("중복된 아이디, 닉네임, 전화번호, 이메일 혹은 잘못된 비밀번호입니다.")
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
  position: relative;
  width: 75vw;
  margin-left: 10vw;
  margin-right: 15vw;
  margin-top: 10px;
  margin-bottom: 60px;
  padding-top: 10px;
  padding-bottom: 125px;
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
.item-required {
    color: rgb(192, 0, 0);
}
.error-message {
    color: rgb(192, 0, 0);
}
.check-notice {
    color: rgb(196, 196, 196);
}
.signup-checkbox {
    margin-top : 0px !important;
    padding-top: 0px !important;
}
.signup-btn {
  color:white !important;
}
.signup-card {
  border-color: rgb(192, 0, 0) !important;
}
</style>