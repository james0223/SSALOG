<template>
  <v-container mt-16>
    <v-row no-gutters>
      <v-col cols="5">
        <v-card rounded="false" outlined class="pa-9" height="600px" align="center">
          <v-card-title class="pa-0 mb-7 justify-space-between">
            <h1 class="ma-0" style="font-family: 'Do Hyeon', sans-serif;">쌀로그인</h1>
            <v-btn @click="$router.push({ name: 'Home' })" color="secondary" fab dark>
              <v-icon>mdi-home-outline</v-icon>
            </v-btn>
            <!-- <v-img
              :src="require('@/assets/images/logo.png')"
              min-width="10vh"
              max-width="10vh"
              max-height="10vh"
              alt="홈페이지 아이콘"
            />-->
          </v-card-title>
          <v-form ref="form">
            <v-text-field
              class="my-3"
              v-model="loginData.username"
              placeholder="hello@ssalog.com"
              label="이메일"
              required
              @keypress.enter="onSubmit()"
            ></v-text-field>
            <v-text-field
              type="password"
              v-model="loginData.password"
              label="비밀번호"
              required
              @keypress.enter="onSubmit()"
            ></v-text-field>
            <v-card-text style="height: 16px;">
              <small class="red--text">{{ errorMsg }}</small>
              <v-progress-circular indeterminate v-if="loading"></v-progress-circular>
            </v-card-text>
            <v-card-actions class="px-0 mt-4">
              <v-btn block color="success" tile @click="onSubmit()">로그인</v-btn>
            </v-card-actions>

            <v-card-actions class="px-0 my-3">
              <v-btn block color="primary" tile @click="toRegister()">회원 가입</v-btn>
            </v-card-actions>
            <small style="cursor:pointer" @click="$router.push({ name: 'FindPass' })"
              >비밀번호찾기</small
            >
          </v-form>
        </v-card>
      </v-col>
      <v-col cols="7">
        <v-img src="@/assets/images/login_main.jpg" height="600px"></v-img>
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
      },
      errorMsg: null,
      showMenu: false,
      loading: false
    };
  },
  methods: {
    async onSubmit() {
      if (!this.loginData.username || !this.loginData.password) {
        this.errorMsg = "이메일 또는 비밀번호를 입력해주세요";
      } else {
        this.errorMsg = null;
        this.loading = true;
        try {
          await this.$store.dispatch("LOGIN", this.loginData);
          if (this.$store.state.formerLink) {
            this.$router.push(this.$store.state.formerLink);
          } else {
            this.$router.push({ name: "Home" });
          }
        } catch (e) {
          this.errorMsg = "이메일 또는 비밀번호를 확인해주세요";
          console.error(e);
        }
        this.loading = false;
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
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
.p_page {
  margin-top: "200vh";
}
</style>
