<template>
  <v-card class="mx-auto" max-width="500">
    <v-card-title class="title font-weight-regular justify-space-between">
      <span>{{ currentTitle }}</span>
      <v-avatar
        color="primary lighten-2"
        class="subheading white--text"
        size="24"
        v-text="step"
      ></v-avatar>
    </v-card-title>

    <v-window v-model="step">
      <v-window-item :value="1">
        <v-card-text>
          <v-text-field label="아이디" v-model="account.username"></v-text-field>
          <v-text-field label="이메일" v-model="account.email"></v-text-field>
          <span class="caption grey--text text--darken-1">
            이메일과 ID를 입력해주세요
          </span>
        </v-card-text>
      </v-window-item>

      <v-window-item :value="2">
        <v-card-text>
          질문 : {{ question }}
          <v-text-field label="답변" v-model="account.answer"></v-text-field>
          <span class="caption grey--text text--darken-1">
            비밀번호 질문에 대한 답을 작성해주세요
          </span>
        </v-card-text>
      </v-window-item>

      <v-window-item :value="3">
        <v-card-text>
          <v-text-field type="password" label="새 비밀번호" v-model="account.password">
          </v-text-field>
          <v-text-field type="password" label="비밀번호 확인" v-model="account.passwordCheck">
          </v-text-field>
          <span class="caption grey--text text--darken-1">
            새로운 비밀번호를 입력해주세요
          </span>
        </v-card-text>
      </v-window-item>
    </v-window>

    <v-divider></v-divider>

    <v-card-actions>
      <!-- <v-btn :disabled="step === 1" text @click="step--">
        뒤로가기
      </v-btn> -->
      <v-spacer></v-spacer>
      <v-btn v-if="step <= 2" color="primary" depressed @click="findPassReq()">
        다음
      </v-btn>
      <v-btn v-if="step === 3" color="primary" depressed @click="updatePasswordReq()">
        완료
      </v-btn>
    </v-card-actions>
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  name: "FindPass",
  data: () => ({
    step: 1,
    account: {
      username: "",
      email: "",
      passwordCheck: "",
      password: "",
      answer: ""
    },
    question: ""
  }),
  methods: {
    updatePasswordReq() {
      if (this.account.password === "" || this.account.passwordCheck === "") {
        alert("비밀번호와 확인문자를 입력해주세요");
      } else if (this.account.password !== this.account.passwordCheck) {
        alert("비밀번호 확인문자가 일치하지 않습니다.");
      } else {
        axios
          .post(`${this.$serverURL}/newuser/change_pw`, null, {
            params: {
              accessToken: this.$store.state.accessToken,
              password: this.account.password
            }
          })
          .then(({ data }) => {
            if (data) {
              alert("변경성공! 홈페이지로 이동합니다.");
              this.$router.push({ name: "Home" });
              // 메인페이지로 리다이렉트
            } else {
              alert("뭔가 잘못됬습니다...");
            }
          })
          .catch(err => {
            console.error(err);
          });
      }
    },
    findPassReq() {
      if (this.step === 1) {
        if (this.account.username === "" || this.account.email === "") {
          alert("아이디와 이메일을 입력해주세요");
        } else {
          axios
            .post(`${this.$serverURL}/newuser/findpw`, null, {
              params: {
                email: this.account.email,
                username: this.account.username
              }
            })
            .then(({ data }) => {
              if (data.result) {
                this.step = 2;
                this.question = data.question;
              } else {
                alert("아이디와 이메일을 확인해주세요");
              }
            })
            .catch(err => console.log(err));
        }
      } else if (this.step === 2) {
        if (this.account.answer === "") {
          alert("답변을 입력해주세요");
        } else {
          axios
            .post(`${this.$serverURL}/newuser/quiz`, null, {
              params: {
                answer: this.account.answer,
                username: this.account.username
              }
            })
            .then(({ data }) => {
              if (data.result === "true") {
                this.$store.commit("LOGIN", data);
                this.step = 3;
                // 토큰 받아서 로그인처리해야함
              } else {
                alert("질문에 대한 답이 일치하지 않습니다.");
              }
            })
            .catch(err => console.log(err));
        }
      }
    }
  },

  computed: {
    currentTitle() {
      switch (this.step) {
        default:
          return "비밀번호 찾기";
      }
    }
  }
};
</script>

<style></style>
