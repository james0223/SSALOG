<template>
  <v-form ref="form" lazy-validation>
    <v-text-field v-model="loginData.username" :counter="10" label="아이디" required></v-text-field>

    <v-text-field
      type="password"
      v-model="loginData.password"
      label="비밀번호"
      required
    ></v-text-field>

    <v-btn color="success" class="mr-4" @click="onSubmit()">로그인</v-btn>
  </v-form>
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
<style></style>
