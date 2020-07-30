<template>
  <v-container>
    <v-row no-gutters>
      <v-col cols="5">
        <v-card class="pa-9" height="70vh" align="center">
          <v-card-title class="pa-0 mb-7">
            <h1 class="ma-0">Hi, SSaLog</h1>
            <v-img
              :src="require('@/assets/images/header-logo.png')"
              max-width="5vw"
              max-height="10vh"
              alt="홈페이지 아이콘"
            />
          </v-card-title>
          <v-form ref="form">
            <v-text-field
              class="mb-3"
              v-model="loginData.username"
              label="아이디"
              required
            ></v-text-field>
            <v-text-field
              type="password"
              v-model="loginData.password"
              label="비밀번호"
              required
            ></v-text-field>
            <v-btn color="success" class="mr-4" @click="onSubmit()">로그인</v-btn>
            <v-btn color="success" class="mr-4" @click="toRegister()">회원 가입</v-btn>
            <v-btn color="success" class="mr-4" @click="toFindId()">아이디 찾기</v-btn>
            <v-btn color="success" class="mr-4" @click="toFindPw()">비밀번호 찾기</v-btn>
            로그인이 어려우신가요?
          </v-form>
        </v-card>
      </v-col>
      <v-col cols="7">
        <v-img src="@/assets/images/login_main.jpg" height="70vh"></v-img>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "Login",
  data() {
    return {
      loginData: {
        username: this.$route.query.username,
        password: null
      }
    };
  },
  methods: {
    async onSubmit() {
      try {
        await this.$store.dispatch("LOGIN", this.loginData);
        console.log(this.$store.state.formerLink);
        if (this.$store.state.formerLink) {
          this.$router.push(this.$store.state.formerLink);
        } else {
          this.$router.push({ name: "Home" });
        }
      } catch (e) {
        alert("아이디 또는 비밀번호를 확인해주세요");
        console.error(e);
      }
    },
    toRegister() {
      this.$router.push({ name: "SignUp" });
    },
    toFindId() {
      this.$router.push({ name: "FindId" });
    },
    toFindPw() {
      this.$router.push({ name: "FindPass" });
    }
  }
};
</script>
<style scoped>
.p_page {
  margin-top: "200vh";
}
</style>
