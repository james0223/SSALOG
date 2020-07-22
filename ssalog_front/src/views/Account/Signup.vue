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
        <v-text-field v-model="userData.id" label="아이디"></v-text-field>
        <v-btn @click="duplicateCheck" text small color="primary">중복검사</v-btn>
        <v-text-field v-model="userData.email" label="이메일"></v-text-field>
        <v-text-field v-model="userData.nickName" label="닉네임"></v-text-field>
        <v-text-field v-model="userData.password" label="비밀번호" type="password"></v-text-field>
        <v-text-field
          v-model="userData.password_check"
          label="비밀번호(확인)"
          type="password"
        ></v-text-field>
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
              v-model="userData.birthDay"
              label="생일"
              readonly
              v-bind="attrs"
              v-on="on"
            ></v-text-field>
          </template>
          <v-date-picker
            ref="picker"
            v-model="userData.birthDay"
            :max="new Date().toISOString().substr(0, 10)"
            min="1920-01-01"
            @change="save"
            locale="ko-kr"
          ></v-date-picker>
        </v-menu>
      </v-card-text>
    </v-card>

    <v-divider></v-divider>

    <v-card-actions>
      <v-btn color="primary" text @click="submit">회원가입</v-btn>
      <v-spacer></v-spacer>
    </v-card-actions>
  </v-card>
</template>

<script>
/* eslint-disable no-unused-expressions */
export default {
  name: "SignUp",
  data: () => ({
    userData: {
      id: "",
      email: "",
      nickName: "",
      password: "",
      password_check: "",
      birthDay: ""
    },
    step: 1,
    isDuplicateChecked: false,
    menu: false
  }),
  watch: {
    menu(val) {
      val &&
        setTimeout(() => {
          this.$refs.picker.activePicker = "YEAR";
        });
    }
  },
  methods: {
    submit() {
      if (this.isDuplicateChecked) {
        console.log("회원가입 성공");
      } else {
        console.log("아이디 중복여부 체크해주세요");
      }
    },
    duplicateCheck() {
      this.isDuplicateChecked = true;
    },
    save(date) {
      this.$refs.menu.save(date);
    }
  }
};
</script>

<style></style>
