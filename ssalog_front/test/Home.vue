<template>
  <div id="app">
    <label for="email">아이디</label>
    <input type="email" name="email" id="email" v-model="email" />
    <label for="pass">비밀번호</label>
    <input type="password" name="password" id="pass" v-model="password" />
    <input type="submit" value="로그인" id="login" @click="login" />
    <button value="로그아웃" id="logout" @click="logout" />
    <hr />
    <span>
      사용자 정보:
      <span>{{ message }}</span>
    </span>
    <hr />
    <table>
      <tr>
        <th>상태</th>
        <td id="status">{{ status }}</td>
      </tr>
      <tr>
        <th>토큰</th>
        <td id="token">{{ token }}</td>
      </tr>
      <tr>
        <th>정보</th>
        <td id="info">{{ info }}</td>
      </tr>
    </table>
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
      email: "",
      password: "",
      message: "login please",
      status: "",
      token: "",
      info: ""
    };
  },
  methods: {
    setInfo(status, token, info) {
      this.status = status;
      this.token = token;
      this.info = info;
    },
    logout() {
      storage.setItem("accessToken", "");
      storage.setItem("login_user", "");
      this.message = "로그인해주세요";
      this.setInfo("로그아웃 성공", "", "");
    },
    getinfo() {
      ai.post(
        "/info",
        {
          email: "some@email.com",
          password: "some password"
        },
        {
          headers: {
            Authorization: `Bearer ${storage.getItem("accessToken")}`
          }
        }
      )
        .then(res => {
          this.setInfo("정보 조회 성공", res.headers.auth_token, JSON.stringify(res.data));
        })
        .catch(e => {
          this.setInfo("정보 조회 실패", "", e.response.data.msg);
        });
    },
    login() {
      storage.setItem("accessToken", "");
      storage.setItem("login_user", "");
      ai.post("/newuser/login", {
        username: this.email,
        password: this.password
      })
        .then(res => {
          console.dir(res.data.accessToken);
          storage.setItem("accessToken", res.data.accessToken);
          this.setInfo("성공", res.data.accessToken, "데이터");
          storage.setItem("login_user", this.email);
          this.message = `${this.email}로 로그인 되었습니다.`;

          // if (res.data.status) {
          //   this.setInfo(
          //     "성공",
          //     res.headers["accessToken"],
          //     JSON.stringify(res.data.data)
          //   );
          // } else {
          //   this.setInfo("", "", "");
          //   this.message = "로그인 해주세요";
          //   alert("입력 정보를 확인하세요");
          // }
        })
        .catch(() => {
          this.setInfo("로그인실패1", "", "로그인실패2");
        });
    },

    init() {
      if (storage.getItem("accessToken")) {
        this.message = `${storage.getItem("login_user")}로 로그인 되었습니다.`;
      } else {
        storage.setItem("accessToken", "");
      }
    }
  },
  mounted() {
    this.init();
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
