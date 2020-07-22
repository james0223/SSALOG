<template>
  <v-form ref="form" v-model="valid" lazy-validation>
    <v-text-field
      v-model="loginData.id"
      :counter="10"
      :rules="nameRules"
      label="아이디"
      required
    ></v-text-field>

    <v-text-field
      type="password"
      v-model="loginData.password"
      :rules="nameRules"
      label="비밀번호"
      required
    ></v-text-field>

    <v-checkbox v-model="checkbox" label="아이디 기억하기" required></v-checkbox>

    <v-btn :disabled="!valid" color="success" class="mr-4" @click="login">로그인</v-btn>
  </v-form>
</template>

<script>
import axios from "axios";

export default {
  name: "Login",
  data: () => ({
    loginData: {
      id: "",
      password: ""
    },
    valid: true,
    idRules: [
      v => !!v || "아이디를 입력해주세요"
      // v => (v && v.length <= 10) || "Name must be less than 10 characters"
    ],
    passwordRules: [
      v => !!v || "비밀번호를 입력해주세요"
      // v => (v && v.length <= 10) || "Name must be less than 10 characters"
    ],
    select: null,
    checkbox: false
  }),

  methods: {
    login() {
      axios
        .post("server", this.loginData)
        .then(res => {
          console.log(res.data);
        })
        .catch(err => console.error(err));
    }
  }
};
</script>

<style></style>
