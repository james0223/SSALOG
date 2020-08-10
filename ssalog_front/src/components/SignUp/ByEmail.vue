<template>
  <div>
    <v-card-title class="pt-0">
      <h5>쌀로그와 함께하기 위한 고객님의 소중한 정보를 입력해주세요 :)</h5>
    </v-card-title>
    <v-card-text>
      <v-form ref="form" v-model="valid" lazy-validation>
        <v-card-actions class="px-0">
          <v-text-field
            @keyup="checkEmail"
            type="email"
            :rules="emailRules"
            v-model="userData.username"
            label="이메일"
            required
          ></v-text-field>
          <v-btn class="ml-auto" rounded outlined @click="getCode()">{{ codeCheck.button }}</v-btn>
        </v-card-actions>
        <v-card-actions class="px-0" v-if="codeCheck.step === 1 || true">
          <v-text-field
            @keyup="checkEmail"
            type="text"
            v-model="codeCheck.code"
            label="인증코드"
            required
          ></v-text-field>
          <v-btn class="ml-auto" rounded outlined @click="checkCode()">
            확인
          </v-btn>
        </v-card-actions>
        <v-card-text class="text-right pa-0">
          <small class="deep-orange--text">{{ codeCheck.error }}</small>
          <small class="info--text" v-if="checkedFlags.code">인증완료</small>
        </v-card-text>
        <v-text-field
          v-model="userData.password"
          label="비밀번호"
          :rules="textRules"
          type="password"
          required
        ></v-text-field>
        <v-text-field
          v-model="passwdcheck"
          :rules="pwRules"
          label="비밀번호(확인)"
          type="password"
          required
        ></v-text-field>
        <v-text-field
          type="text"
          @keyup="checkNickname"
          :rules="nicknameRules"
          v-model="userData.nickname"
          label="닉네임"
          required
        ></v-text-field>
      </v-form>
    </v-card-text>
    <v-card-text class="py-0">
      <small class="deep-orange--text" v-show="errorMsg">{{ errorMsg }}</small>
    </v-card-text>
    <v-card-text>
      <v-btn class="px-0" tile block color="info" @click="signUp()">회원가입</v-btn>
    </v-card-text>
  </div>
</template>

<script>
export default {
  name: "ByEmail",
  data() {
    return {
      step: "main",
      userData: {
        password: null,
        nickname: null,
        username: null
      },
      passwdcheck: null,
      // 검사하고있는지를 판단
      chekcingFlags: {
        email: false,
        nickname: false
      },
      // 검사완료가 되었는지를 판단
      checkedFlags: {
        email: false,
        nickname: false,
        code: false
      },
      pwRules: [
        v => !!v || "입력이 필요합니다.",
        v => this.userData.password === v || "비밀번호 확인이 일치하지 않습니다."
      ],

      textRules: [v => !!v || "입력이 필요합니다."],
      errorMsg: null,
      codeCheck: {
        code: null,
        originCode: null,
        error: null,
        button: "인증번호 발송"
      }
    };
  },
  methods: {
    goBack() {
      if (this.step === "main") {
        this.$router.go(-1);
      } else {
        this.step = "main";
      }
    },
    checkCode() {
      const { code, originCode } = this.codeCheck;
      if (code !== originCode || !originCode || !code) {
        this.codeCheck.error = "인증코드가 일치하지 않습니다.";
      } else {
        this.checkedFlags.code = true;
        this.codeCheck.error = null;
      }
    },
    async getCode() {
      if (!this.checkedFlags.email || !this.userData.username) {
        this.codeCheck.error = "이메일을 확인해주세요";
      } else {
        this.codeCheck.error = null;
        this.codeCheck.button = "인증번호 재발송";
        try {
          const { data } = this.$http.get(`${this.$store.state.ServerURL}/서버야돌아와`, {
            params: {
              username: this.userData.username
            }
          });
          this.codeCheck.originCode = data;
        } catch (e) {
          console.error(e);
        }
      }
    },
    checkEmail() {
      if (!this.isCheckingEmail) {
        this.isCheckingEmail = true;
        setTimeout(() => {
          this.$http
            // 되나 확인해야함
            .get(`${this.$store.state.ServerURL}/newuser/checkemail`, {
              params: {
                username: this.userData.username
              }
            })
            .then(({ data }) => {
              if (data) {
                this.isCheckedEmail = true;
              } else {
                this.isCheckedEmail = false;
              }
            })
            .catch(err => {
              console.error(err);
            });
          this.isCheckingEmail = false;
        }, 500);
      }
    },
    checkId() {
      if (!this.isCheckingId) {
        this.isCheckingId = true;
        setTimeout(() => {
          this.$http
            .get(`${this.$store.state.ServerURL}/newuser/checkid`, {
              params: {
                username: this.userData.username
              }
            })
            .then(({ data }) => {
              if (data) {
                this.isCheckedId = true;
              } else {
                this.isCheckedId = false;
              }
            })
            .catch(err => {
              console.error(err);
            });
          this.isCheckingId = false;
        }, 500);
      }
    },
    checkNickname() {
      if (!this.isCheckingNickname) {
        this.isCheckingNickname = true;
        setTimeout(() => {
          this.$http
            .get(`${this.$store.state.ServerURL}/newuser/checkNickname`, {
              params: {
                nickname: this.userData.nickname
              }
            })
            .then(({ data }) => {
              console.log(data);
              if (data) {
                this.isCheckedNickname = true;
              } else {
                this.isCheckedNickname = false;
              }
            })
            .catch(err => {
              console.error(err);
            });
          this.isCheckingNickname = false;
        }, 500);
      }
    },
    async signUp() {
      this.$refs.form.validate();
      if (!this.userData.username) {
        this.errorMsg = "이메일을 입력해주세요";
      } else if (!this.isCheckedId) {
        this.errorMsg = "해당 이메일은 이미 사용중입니다.";
      } else if (!this.userData.password || !this.passwdcheck) {
        this.errorMsg = "비밀번호와 확인문자를 입력해주세요";
      } else if (this.userData.password !== this.passwdcheck) {
        this.errorMsg = "비밀번호와 확인문자가 일치하지 않습니다.";
      } else if (!this.userData.nickname) {
        this.errorMsg = "닉네임을 입력해주세요";
      } else if (!this.isCheckedNickname) {
        this.errorMsg = "해당 닉네임은 이미 사용중입니다.";
      } else if (!this.userData.question) {
        this.errorMsg = "비밀번호 찾기 힌트를 선택해주세요";
      } else if (!this.userData.answer) {
        this.errorMsg = "비밀번호 찾기 힌트의 답을 입력해주세요";
      } else {
        try {
          console.log(this.userData);
          await this.$store.dispatch("SIGNUP", this.userData);
          this.$router.push({ name: "Home" });
        } catch (e) {
          alert("오류가 발생했습니다. 다시 접근해주세요 :(");
          console.error(e);
        }
      }
    }
  },
  computed: {
    nicknameRules() {
      const rules = [];
      const rule = v => !!v || "닉네임을 입력해주세요";
      rules.push(rule);
      const rule2 = this.isCheckedNickname || "중복된 닉네임입니다.";
      rules.push(rule2);
      return rules;
    },
    emailRules() {
      const rules = [];
      const rule = v => !!v || "메일주소를 입력해주세요";
      rules.push(rule);
      const rule2 = this.isCheckedEmail || "중복된 메일주소입니다.";
      rules.push(rule2);
      return rules;
    },
    idRules() {
      const rules = [];
      const rule = v => !!v || "아이디를 입력해주세요";
      rules.push(rule);
      const rule2 = this.isCheckedId || "중복된 아이디 입니다.";
      rules.push(rule2);
      return rules;
    }
  }
};
</script>

<style></style>
