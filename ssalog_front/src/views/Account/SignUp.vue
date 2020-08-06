<template>
  <v-container my-16>
    <v-card flat class="mx-auto" max-width="50vw">
      <v-card-text class="text-center">
        <v-avatar class="elevation-12 mb-12" size="200">
          <v-img src="@/assets/images/logo.jpg"></v-img>
        </v-avatar>
      </v-card-text>
      <v-card-title class="py-0 align-baseline">
        <h1 class="mb-0" style="font-family: 'Do Hyeon', sans-serif;">쌀로하~</h1>
        <v-btn class="ml-auto" rounded outlined @click="$router.go(-1)">뒤로가기</v-btn>
      </v-card-title>
      <v-card-title class="pt-0">
        <h5>쌀로그와 함께하기 위한 고객님의 소중한 정보를 입력해주세요 :)</h5>
      </v-card-title>

      <v-card-text>
        <v-form ref="form" v-model="valid" lazy-validation>
          <v-text-field
            @keyup="checkId"
            type="text"
            v-model="userData.username"
            :rules="idRules"
            label="아이디"
            required
          ></v-text-field>
          <v-text-field
            v-model="userData.password"
            label="비밀번호"
            :rules="textRules"
            type="password"
            required
          ></v-text-field>
          <v-text-field
            v-model="passwdcheck"
            :rules="pwRules"
            label="비밀번호(확인)"
            type="password"
            required
          ></v-text-field>
          <v-text-field
            @keyup="checkEmail"
            type="email"
            :rules="emailRules"
            v-model="userData.email"
            label="이메일"
            required
          ></v-text-field>
          <v-text-field
            type="text"
            @keyup="checkNickname"
            :rules="nicknameRules"
            v-model="userData.nickname"
            label="닉네임"
            required
          ></v-text-field>
          <!-- <v-menu
            ref="menu"
            v-model="menu"
            :close-on-content-click="false"
            transition="scale-transition"
            offset-y
            min-width="290px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                v-model="userData.birthday"
                label="생일"
                :rules="textRules"
                readonly
                v-bind="attrs"
                v-on="on"
                required
              ></v-text-field>
            </template>
            <v-date-picker
              ref="picker"
              v-model="userData.birthday"
              :max="new Date().toISOString().substr(0, 10)"
              min="1920-01-01"
              @change="save"
              locale="ko-kr"
            ></v-date-picker>
          </v-menu>-->
          <v-select
            :items="questions"
            v-model="userData.question"
            :rules="textRules"
            label="비밀번호 찾기 힌트"
            required
          ></v-select>
          <v-text-field
            v-model="userData.answer"
            label="비밀번호 찾기 답변"
            :rules="textRules"
            type="text"
            required
          ></v-text-field>
        </v-form>
      </v-card-text>
      <v-card-text class="py-0">
        <small class="deep-orange--text" v-show="errorMsg">{{ errorMsg }}</small>
      </v-card-text>
      <v-card-text>
        <v-btn class="px-0" tile block color="info" @click="signUp()">회원가입</v-btn>
      </v-card-text>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "SignUp",
  data() {
    return {
      userData: {
        username: null,
        password: null,
        nickname: null,
        email: null,
        question: null,
        answer: null
      },
      passwdcheck: null,
      // menu: false, 생일 입력때문에 있던 것
      isCheckingId: false,
      isCheckedId: false,
      isCheckingEmail: false,
      isCheckedEmail: false,
      isCheckingNickname: false,
      isCheckedNickname: false,
      pwRules: [
        v => !!v || "입력이 필요합니다.",
        v => this.userData.password === v || "비밀번호 확인이 일치하지 않습니다."
      ],

      textRules: [v => !!v || "입력이 필요합니다."],
      errorMsg: null,
      questions: [
        "나의 어렸을 적 별명은?",
        "내가 살았던 동네 이름은?",
        "졸업한 초등학교 이름은?",
        "나의 인생영화는?"
      ]
    };
  },
  methods: {
    checkEmail() {
      if (!this.isCheckingEmail) {
        this.isCheckingEmail = true;
        setTimeout(() => {
          axios
            .get(`${this.$store.state.ServerURL}/newuser/checkemail`, {
              params: {
                email: this.userData.email
              }
            })
            .then(({ data }) => {
              if (data) {
                this.isCheckedEmail = true;
              } else {
                this.isCheckedEmail = false;
              }
            })
            .catch(err => {
              console.error(err);
            });
          this.isCheckingEmail = false;
        }, 500);
      }
    },
    checkId() {
      if (!this.isCheckingId) {
        this.isCheckingId = true;
        setTimeout(() => {
          axios
            .get(`${this.$store.state.ServerURL}/newuser/checkid`, {
              params: {
                username: this.userData.username
              }
            })
            .then(({ data }) => {
              if (data) {
                this.isCheckedId = true;
              } else {
                this.isCheckedId = false;
              }
            })
            .catch(err => {
              console.error(err);
            });
          this.isCheckingId = false;
        }, 500);
      }
    },
    checkNickname() {
      if (!this.isCheckingNickname) {
        this.isCheckingNickname = true;
        setTimeout(() => {
          axios
            .get(`${this.$store.state.ServerURL}/newuser/checkNickname`, {
              params: {
                nickname: this.userData.nickname
              }
            })
            .then(({ data }) => {
              console.log(data);
              if (data) {
                this.isCheckedNickname = true;
              } else {
                this.isCheckedNickname = false;
              }
            })
            .catch(err => {
              console.error(err);
            });
          this.isCheckingNickname = false;
        }, 500);
      }
    },
    async signUp() {
      this.$refs.form.validate();
      if (!this.userData.username) {
        this.errorMsg = "아이디를 입력해주세요";
      } else if (!this.isCheckedId) {
        this.errorMsg = "해당 아이디는 이미 사용중입니다.";
      } else if (!this.userData.password || !this.passwdcheck) {
        this.errorMsg = "비밀번호와 확인문자를 입력해주세요";
      } else if (this.userData.password !== this.passwdcheck) {
        this.errorMsg = "비밀번호와 확인문자가 일치하지 않습니다.";
      } else if (!this.userData.email) {
        this.errorMsg = "이메일을 입력해주세요";
      } else if (!this.isCheckedEmail) {
        this.errorMsg = "해당 이메일은 이미 사용중입니다.";
      } else if (!this.userData.nickname) {
        this.errorMsg = "닉네임을 입력해주세요";
      } else if (!this.isCheckedNickname) {
        this.errorMsg = "해당 닉네임은 이미 사용중입니다.";
      } else if (!this.userData.question) {
        this.errorMsg = "비밀번호 찾기 힌트를 선택해주세요";
      } else if (!this.userData.answer) {
        this.errorMsg = "비밀번호 찾기 힌트의 답을 입력해주세요";
      } else {
        try {
          console.log(this.userData);
          await this.$store.dispatch("SIGNUP", this.userData);
          this.$router.push({ name: "Home" });
        } catch (e) {
          alert("오류가 발생했습니다. 다시 접근해주세요 :(");
          console.error(e);
        }
      }
    }
  },
  computed: {
    nicknameRules() {
      const rules = [];
      const rule = v => !!v || "닉네임을 입력해주세요";
      rules.push(rule);
      const rule2 = this.isCheckedNickname || "중복된 닉네임입니다.";
      rules.push(rule2);
      return rules;
    },
    emailRules() {
      const rules = [];
      const rule = v => !!v || "메일주소를 입력해주세요";
      rules.push(rule);
      const rule2 = this.isCheckedEmail || "중복된 메일주소입니다.";
      rules.push(rule2);
      return rules;
    },
    idRules() {
      const rules = [];
      const rule = v => !!v || "아이디를 입력해주세요";
      rules.push(rule);
      const rule2 = this.isCheckedId || "중복된 아이디 입니다.";
      rules.push(rule2);
      return rules;
    }
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
</style>
