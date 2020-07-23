<template>
  <v-img src="@/assets/images/intro-visual-bg01.jpg">
    <v-container>
      <v-row>
        <v-col cols="12" lg="2" offset-lg="3">
          <v-card class="mx-auto" height="40vh">
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
        username: null,
        password: null
      }
    };
  },
  methods: {
    async onSubmit() {
      // LOGIN action execute
      try {
        await this.$store.dispatch("LOGIN", this.loginData);
        this.redirect();
      } catch (e) {
        console.error(e);
      }
    },
    redirect() {}
  }
};
</script>
<style scoped>
.p_page {
  margin-top: "200vh";
}
</style>
