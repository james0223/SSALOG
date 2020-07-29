<template>
  <v-img src="@/assets/images/intro-visual-bg01.jpg">
    <v-container>
      <v-row>
        <v-col cols="12" lg="2" offset-lg="3" class="pt-16">
          <v-card class="pa-5 mt-16" height="40vh" align="center">
            <v-form ref="form" lazy-validation>
              <v-text-field
                v-model="loginData.username"
                :counter="10"
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
            </v-form>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-img>
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
      // LOGIN action execute
      try {
        await this.$store.dispatch("LOGIN", this.loginData);
        this.$router.push({ name: "Home" });
      } catch (e) {
        console.error(e);
      }
    },
    toRegister() {
      this.$router.push({ name: "SignUp" });
    }
  }
};
</script>
<style scoped>
.p_page {
  margin-top: "200vh";
}
</style>
