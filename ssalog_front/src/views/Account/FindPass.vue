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
          <v-text-field label="id" v-model="userData.id"></v-text-field>
          <v-text-field label="Email" v-model="userData.email"></v-text-field>
          <span class="caption grey--text text--darken-1">
            이메일과 ID를 입력해주세요
          </span>
        </v-card-text>
      </v-window-item>

      <v-window-item :value="2">
        <v-card-text>
          질문 : 별명은?
          <v-text-field></v-text-field>
          <span class="caption grey--text text--darken-1">
            비밀번호 질문에 대한 답을 작성해주세요
          </span>
        </v-card-text>
      </v-window-item>

      <v-window-item :value="3">
        <div class="pa-4 text-center">
          <v-img
            class="mb-4"
            contain
            height="128"
            src="https://cdn.vuetifyjs.com/images/logos/v.svg"
          ></v-img>
          <h3 class="title font-weight-light mb-2">Welcome to Vuetify</h3>
          <span class="caption grey--text">Thanks for signing up!</span>
        </div>
      </v-window-item>
    </v-window>

    <v-divider></v-divider>

    <v-card-actions>
      <v-btn :disabled="step === 1" text @click="step--">
        뒤로가기
      </v-btn>
      <v-spacer></v-spacer>
      <v-btn v-if="step === 1" color="primary" depressed @click="findPassReq(1)">
        다음
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
    userData: {
      id: "",
      email: ""
    }
  }),
  methods: {
    findPassReq(step) {
      if (step === 1) {
        axios
          .get("url", this.userData)
          .then(res => {
            console.log(res.data);
          })
          .catch(err => console.error(err));
      } else if (step === 2) {
        console.log("으으음");
      }
    }
  },

  computed: {
    currentTitle() {
      switch (this.step) {
        case 1:
          return "비밀번호 찾기";
        case 2:
          return "Create a password";
        default:
          return "Account created";
      }
    }
  }
};
</script>

<style></style>
