<template>
  <v-container my-16>
    <v-card :dark="isDark" color="transparent" flat class="mx-auto" max-width="50vw">
      <v-card-text class="text-center">
        <v-avatar class="elevation-12 mb-12" size="200">
          <v-img src="@/assets/images/logo.jpg"></v-img>
        </v-avatar>
      </v-card-text>
      <v-card-title class="py-0 align-baseline">
        <h1 class="mb-0" style="font-family: 'Do Hyeon', sans-serif;">쌀로하~</h1>
        <v-btn class="ml-auto" rounded outlined @click="goBack()">뒤로가기</v-btn>
      </v-card-title>
      <div v-if="step === 'main'">
        <v-card-text>
          <v-btn tile block color="info" @click="step = 'e'">이메일로 회원가입</v-btn>
        </v-card-text>
      </div>
      <ByEmail v-if="step === 'e'" />
    </v-card>
  </v-container>
</template>

<script>
import ByEmail from "@/components/SignUp/ByEmail.vue";
import { mapState } from "vuex";

export default {
  name: "SignUp",
  components: {
    ByEmail
  },
  data() {
    return {
      step: "main"
    };
  },
  computed: mapState(["isDark"]),
  methods: {
    goBack() {
      if (this.step === "main") {
        this.$router.go(-1);
      } else {
        this.step = "main";
      }
    }
  }
};
</script>

<style>
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
</style>
