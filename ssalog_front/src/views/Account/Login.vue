<template>
  <v-container mt-16>
    <v-row no-gutters>
      <v-col cols="5">
        <v-card rounded="false" outlined class="pa-9" height="500px" align="center">
          <v-card-title class="pa-0 mb-7">
            <h1 class="ma-0" style="font-family: 'Do Hyeon', sans-serif;">
              쌀로그인
            </h1>
            <!-- <v-img
              :src="require('@/assets/images/logo.png')"
              min-width="10vh"
              max-width="10vh"
              max-height="10vh"
              alt="홈페이지 아이콘"
            /> -->
          </v-card-title>
          <v-form ref="form">
            <v-text-field
              class="my-3"
              v-model="loginData.username"
              label="아이디"
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
            </v-card-text>
            <v-card-actions class="px-0 mt-3">
              <v-btn block color="success" tile @click="onSubmit()">로그인</v-btn>
            </v-card-actions>

            <v-card-actions class="px-0 my-3">
              <v-btn block color="primary" tile @click="toRegister()">회원 가입</v-btn>
            </v-card-actions>

            <!-- <v-btn color="success" @click="toFindId()">아이디 찾기</v-btn>
            <v-btn color="success" @click="toFindPw()">비밀번호 찾기</v-btn> -->
            <v-menu v-model="showMenu" absolute offset-y style="max-width: 600px">
              <template v-slot:activator="{ on, attrs }">
                <small v-bind="attrs" v-on="on">아이디/비밀번호찾기</small>
              </template>
              <v-list>
                <v-list-item @click="$router.push({ name: 'FindId' })">
                  <v-list-item-title>아이디 찾기</v-list-item-title>
                </v-list-item>
                <v-divider></v-divider>
                <v-list-item @click="$router.push({ name: 'FindPass' })">
                  <v-list-item-title>비밀번호 찾기</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </v-form>
        </v-card>
      </v-col>
      <v-col cols="7">
        <v-img src="@/assets/images/login_main.jpg" height="500px"></v-img>
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
      showMenu: false
    };
  },
  methods: {
    async onSubmit() {
      if (!this.loginData.username || !this.loginData.password) {
        this.errorMsg = "아이디 또는 비밀번호를 입력해주세요";
      } else {
        try {
          await this.$store.dispatch("LOGIN", this.loginData);
          if (this.$store.state.formerLink) {
            this.$router.push(this.$store.state.formerLink);
          } else {
            this.$router.push({ name: "Home" });
          }
        } catch (e) {
          this.errorMsg = "아이디 또는 비밀번호를 확인해주세요";
          console.error(e);
        }
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
