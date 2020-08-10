<template>
  <v-container my-16>
    <v-card flat class="mx-auto" max-width="50vw">
      <v-card-title class="title font-weight-regular justify-space-between">
        <h1>비밀번호 찾기</h1>
      </v-card-title>

      <v-window v-model="step">
        <v-window-item :value="1">
          <v-card-text class="pb-0">
            <span class="caption grey--text text--darken-1"
              >가입시 등록한 이메일을 입력해주세요</span
            >
            <v-text-field label="이메일" v-model="account.username"></v-text-field>
          </v-card-text>
        </v-window-item>
        <v-window-item :value="2">
          <v-card-text class="pb-0">
            {{ account.username }} 으로 임시 비밀번호가 전송되었습니다. 다시 로그인해주세요
          </v-card-text>
        </v-window-item>
        <!-- <v-window-item :value="2">
          <v-card-text class="pb-0">
            <span class="caption grey--text text--darken-1"
              >{{ account.username }} 으로 발송된 인증코드를 입력해주세요</span
            >
            <v-text-field label="인증코드" v-model="account.code"></v-text-field>
          </v-card-text>
        </v-window-item>

        <v-window-item :value="3">
          <v-card-text class="pb-0">
            <span class="caption grey--text text--darken-1">새로운 비밀번호를 입력해주세요</span>
            <v-text-field
              type="password"
              label="새 비밀번호"
              v-model="account.password"
            ></v-text-field>
            <v-text-field
              type="password"
              label="비밀번호 확인"
              v-model="account.passwordCheck"
            ></v-text-field>
          </v-card-text>
        </v-window-item> -->
      </v-window>
      <v-card-text class="py-0">
        <small class="red--text">{{ errorMsg }}</small>
      </v-card-text>
      <v-card-actions class="px-4">
        <v-spacer></v-spacer>
        <v-btn tile v-if="step === 1" color="info" depressed @click="findPassReq()">다음</v-btn>
        <v-btn
          tile
          v-if="step === 2"
          color="primary"
          depressed
          @click="$router.push({ name: 'Home' })"
          >완료</v-btn
        >
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
import axios from "axios";

export default {
  name: "FindPass",
  data() {
    return {
      step: 1,
      account: {
        username: this.$route.query.username,
        passwordCheck: null,
        password: null,
        code: null
      },
      answer: null,
      errorMsg: null
    };
  },
  methods: {
    updatePasswordReq() {
      this.errorMsg = null;
      if (!this.account.password || !this.account.passwordCheck) {
        this.errorMsg = "비밀번호와 확인문자를 입력해주세요";
      } else if (this.account.password !== this.account.passwordCheck) {
        this.errorMsg = "비밀번호 확인문자가 일치하지 않습니다.";
      } else {
        axios
          .put(`${this.$store.state.ServerURL}/user/change_pw`, null, {
            params: {
              password: this.account.password
            }
          })
          .then(({ data }) => {
            if (data) {
              alert("비밀번호 변경성공! 다시 로그인해주세요");
              this.$router.push({ name: "Home" });
            } else {
              this.errorMsg = "서버에러";
            }
          })
          .catch(err => {
            console.error(err);
            this.errorMsg = "서버에러";
          });
      }
    },
    findPassReq() {
      this.errorMsg = null;
      if (this.step === 1) {
        if (!this.account.username) {
          this.errorMsg = "이메일을 입력해주세요";
        } else {
          // 수정필요
          axios
            .get(`${this.$store.state.ServerURL}/newuser/수정필요`, {
              params: {
                username: this.account.username
              }
            })
            .then(({ data }) => {
              if (data.result) {
                this.step = 2;
                this.answer = data.정답;
              } else {
                this.errorMsg = "유효하지 않은 이메일입니다.";
              }
            })
            .catch(err => console.error(err));
        }
      } else if (this.step === 2) {
        if (this.account.code !== this.answer) {
          this.errorMsg = "인증코드가 일치하지 않습니다.";
        } else {
          this.step = 3;
        }
      }
    }
  }
};
</script>

<style></style>
