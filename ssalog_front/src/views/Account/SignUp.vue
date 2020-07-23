<template>
  <v-card class="mx-auto" max-width="50vw">
    <v-card-title class="title font-weight-regular justify-space-between">
      <span>회원가입</span>
      <v-avatar
        color="primary lighten-2"
        class="subheading white--text"
        size="24"
        v-text="step"
      ></v-avatar>
    </v-card-title>

    <v-card v-model="step" width="50vw">
      <v-card-text>
        <v-text-field type="text" v-model="userData.username" label="아이디"></v-text-field>
        <v-text-field v-model="userData.password" label="비밀번호" type="password"></v-text-field>
        <v-text-field v-model="passwdcheck" label="비밀번호(확인)" type="password"></v-text-field>
        <v-btn @click="duplicateCheck" text small color="primary">중복검사</v-btn>
        <v-text-field type="email" v-model="userData.email" label="이메일"></v-text-field>
        <v-text-field type="text" v-model="userData.nickname" label="닉네임"></v-text-field>
        <v-menu
          ref="menu"
          v-model="menu"
          :close-on-content-click="false"
          transition="scale-transition"
          offset-y
          min-width="290px"
        >
          <template v-slot:activator="{ on, attrs }">
            <v-text-field
              v-model="userData.birthday"
              label="생일"
              readonly
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            ref="picker"
            v-model="userData.birthday"
            :max="new Date().toISOString().substr(0, 10)"
            min="1920-01-01"
            @change="save"
            locale="ko-kr"
          ></v-date-picker>
        </v-menu>
        <v-text-field v-model="userData.question" label="질문" type="text"></v-text-field>
        <v-text-field v-model="userData.answer" label="답변" type="text"></v-text-field>
      </v-card-text>
    </v-card>

    <v-divider></v-divider>

    <v-card-actions>
      <v-btn color="primary" text @click="signUp()">회원가입</v-btn>
      <v-spacer></v-spacer>
    </v-card-actions>
  </v-card>
</template>

<script>
/* eslint-disable no-unused-expressions */
export default {
  name: "SignUp",
  data() {
    return {
      userData: {
        username: "",
        password: "",
        nickname: "",
        email: "",
        birthday: "",
        question: "",
        answer: ""
      },
      passwdcheck: "",
      step: 1,
      isDuplicateChecked: false,
      menu: false
    };
  },
  methods: {
    async signUp() {
      if (this.userData.password === this.passwdcheck) {
        try {
          console.log(this.userData);
          await this.$store.dispatch("SIGNUP", this.userData);
          this.$router.push({ name: "Home" });
        } catch (e) {
          console.error(e);
        }
      }
    }
  }
};
</script>

<style></style>
