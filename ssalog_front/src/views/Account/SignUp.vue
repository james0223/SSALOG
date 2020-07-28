<template>
  <v-card class="mx-auto" max-width="50vw">
    <v-card-title class="title font-weight-regular justify-space-between">
      <span>회원가입</span>
    </v-card-title>

    <v-card width="50vw">
      <v-card-text>
        <v-text-field
          @keyup="checkId"
          type="text"
          v-model="userData.username"
          :rules="idRules"
          label="아이디"
        ></v-text-field>
        <v-text-field v-model="userData.password" label="비밀번호" type="password"></v-text-field>
        <v-text-field
          v-model="passwdcheck"
          :rules="pwRules"
          label="비밀번호(확인)"
          type="password"
        ></v-text-field>
        <v-text-field
          @keyup="checkEmail"
          type="email"
          :rules="emailRules"
          v-model="userData.email"
          label="이메일"
        ></v-text-field>
        <v-text-field
          type="text"
          @keyup="checkNickname"
          :rules="nicknameRules"
          v-model="userData.nickname"
          label="닉네임"
        ></v-text-field>
        <v-menu
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
              readonly
              v-bind="attrs"
              v-on="on"
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
        </v-menu>
        <v-select
          :items="questions"
          v-model="userData.question"
          label="비밀번호 찾기 힌트"
        ></v-select>
        <v-text-field
          v-model="userData.answer"
          label="비밀번호 찾기 답변"
          type="text"
        ></v-text-field>
      </v-card-text>
    </v-card>

    <v-divider></v-divider>

    <v-card-actions>
      <v-btn color="primary" text @click="signUp()">회원가입</v-btn>
      <v-spacer></v-spacer>
    </v-card-actions>
  </v-card>
</template>

<script>
import axios from "axios";
/* eslint-disable no-unused-expressions */
export default {
  name: "SignUp",
  data() {
    return {
      userData: {
        username: "",
        password: "",
        nickname: "",
        email: "",
        birthday: "",
        question: "",
        answer: ""
      },
      passwdcheck: "",
      menu: false,
      isCheckingId: false,
      isCheckedId: false,
      isCheckingEmail: false,
      isCheckedEmail: false,
      isCheckingNickname: false,
      isCheckedNickname: false,
      pwRules: [v => this.userData.password === v || "비밀번호 확인이 일치하지 않습니다."],
      questions: ["나의 어렸을 적 별명은?", "내가 살았던 동네 이름은?", "졸업한 초등학교 이름은?"]
    };
  },
  methods: {
    checkEmail() {
      if (!this.isCheckingEmail) {
        this.isCheckingEmail = true;
        setTimeout(() => {
          axios
            .post(`${this.$serverURL}/newuser/checkemail`, null, {
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
            .post(`${this.$serverURL}/newuser/checkid`, null, {
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
            .post(`${this.$serverURL}/newuser/checkNickname`, null, {
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
      if (this.userData.password === this.passwdcheck) {
        try {
          console.log(this.userData);
          await this.$store.dispatch("SIGNUP", this.userData);
          this.$router.push({ name: "Home" });
        } catch (e) {
          console.error(e);
        }
      }
    }
  },
  computed: {
    nicknameRules() {
      const rules = [];
      const rule2 = v => !!v || "닉네임을 입력해주세요";
      rules.push(rule2);
      const rule = this.isCheckedNickname || "중복된 닉네임입니다.";
      rules.push(rule);
      return rules;
    },
    emailRules() {
      const rules = [];
      const rule2 = v => !!v || "메일주소를 입력해주세요";
      rules.push(rule2);
      const rule = this.isCheckedEmail || "중복된 메일주소입니다.";
      rules.push(rule);
      return rules;
    },
    idRules() {
      const rules = [];
      const rule2 = v => !!v || "아이디를 입력해주세요";
      rules.push(rule2);
      const rule = this.isCheckedId || "중복된 아이디 입니다.";
      rules.push(rule);
      return rules;
    }
  }
};
</script>

<style></style>
