<template>
  <div id="app">
    <label for="username">아이디</label>
    <input type="email" name="username" id="username" v-model="username" />
    <br />
    <label for="pass1">비밀번호</label>
    <input type="password" name="password1" id="pass1" v-model="password" />
    <br />
    <label for="pass2">비밀번호 확인</label>
    <input type="password" name="password2" id="pass2" v-model="password2" />
    <br />
    <label for="nick">닉네임</label>
    <input type="text" name="nick" id="nick" v-model="nickname" />
    <br />
    <label for="email">이메일</label>
    <input type="email" name="email" id="email" v-model="email" />
    <br />
    <label for="birth">생일</label>
    <input type="date" name="birth" id="birth" v-model="birthDay" />
    <br />
    <label for="quest">질문</label>
    <select name="quest" id="quest" v-model="question">
      <option value="아버님 성함은?">아버님 성함은?</option>
      <option value="어머님 성함은?">어머님 성함은?</option>
      <option value="가장 좋아하는 음식은?">가장 좋아하는 음식은?</option>
      <option value="다녔던 초등학교 이름은?">다녔던 초등학교 이름은?</option>
    </select>
    <br />
    <label for="ans">답변</label>
    <input type="text" name="ans" id="ans" v-model="answer" />
    <br />
    <input type="submit" value="회원가입" id="login" @click="join" />

    <hr />
  </div>
</template>

<script>
import axios from "axios";
const storage = window.sessionStorage;

const ai = axios.create({
  baseURL: "http://localhost:8080"
});

export default {
  data() {
    return {
      username: "",
      password: "",
      password2: "",
      nickname: "",
      email: "",
      birthDay: "",
      question: "",
      answer:""
    };
  },
  methods: {
    join() {
      storage.setItem("accessToken", "");
      storage.setItem("login_user", "");
      ai.post("/newuser/add", {
        username : this.username,
        password : this.password,
        nickname : this.nickname,
        email : this.email,
        birthDay : this.birthDay,
        question : this. question,
        answer : this.answer
      })
        .then(res => {
          console.dir(res);
          //storage.setItem("accessToken", res.data.accessToken);
          alert("성공");
          //storage.setItem("login_user", this.email);
          
        })
        .catch(() => {
          alert("실패");
        });
    },

  }

};
</script>


<style scoped>
table {
  border-collapse: collapse;
  width: 100%;
  word-break: break-all;
}
th {
  width: 50px;
}
td,
th {
  border: 1px solid black;
}
</style>