<template>
  <div>
    <v-text-field label="이메일" v-model="email" @keypress.enter="findId"></v-text-field>
    <small v-if="faliMsg" class="deep-orange--text">{{ faliMsg }}</small>
    <small v-if="username">회원님의 아이디는 {{ username }} 입니다.</small>
    <div>
      <v-btn outlined color="indigo" @click="findId">아이디 찾기</v-btn>
      <v-btn outlined color="success" @click="login">로그인</v-btn>
      <v-btn outlined color="dark" @click="findPW">비밀번호 찾기</v-btn>
    </div>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "FindId",
  data() {
    return {
      email: null,
      username: null,
      faliMsg: null
    };
  },
  methods: {
    async findId() {
      if (this.email) {
        try {
          const { data } = await axios.post(`${this.$store.state.ServerURL}/newuser/findid`, null, {
            params: {
              email: this.email
            }
          });
          if (data.result) {
            this.faliMsg = null;
            this.username = data.username;
          } else {
            this.faliMsg = "존재하지 않는 이메일입니다.";
            this.username = null;
          }
        } catch (e) {
          console.error(e);
        }
      } else {
        this.faliMsg = "이메일을 입력해주세요";
        this.username = null;
      }
    },
    login() {
      this.$router.push({
        name: "Login",
        query: {
          username: this.username
        }
      });
    },
    findPW() {
      this.$router.push({
        name: "FindPass",
        query: { username: this.username, email: this.email }
      });
    }
  }
};
</script>

<style></style>
