<template>
  <v-card flat color="transparent" height="60vh" class="pa-8">
    <v-card-title>
      <h3 class="font-weight-light mb-3">비밀번호 변경</h3>
      <v-spacer></v-spacer>
      <v-btn fab color="secondary" dark @click="showPwChange = !showPwChange">
        <v-icon>{{ showPwChange ? "mdi-chevron-up" : "mdi-chevron-down" }}</v-icon>
      </v-btn>
    </v-card-title>
    <v-card-text>
      <v-expand-transition>
        <div v-show="showPwChange">
          <v-card-text>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="새 비밀번호"
                  type="password"
                  v-model="changePw.data"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="비밀번호 확인"
                  type="password"
                  v-model="changePw.check"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <small class="warn">{{ changePw.error }}</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="changePassword">변경</v-btn>
          </v-card-actions>
        </div>
      </v-expand-transition>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-title>
      <h3 class="font-weight-light mb-3">닉네임 변경</h3>
      <v-spacer></v-spacer>
      <v-btn fab color="secondary" dark @click="showNickChange = !showNickChange">
        <v-icon>{{ showNickChange ? "mdi-chevron-up" : "mdi-chevron-down" }}</v-icon>
      </v-btn>
    </v-card-title>
    <v-card-text>
      <v-expand-transition>
        <div v-show="showNickChange">
          <v-card-text>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="새 닉네임"
                  type="text"
                  v-model="changeNick.data"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <small class="warn">{{ changeNick.error }}</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="checkNickname">중복확인</v-btn>
            <v-dialog v-model="changeNick.dialog" max-width="60vh">
              <v-card>
                <v-card-title>
                  <h5>{{ changeNick.data }} (은)는 사용가능한 닉네임입니다!</h5>
                </v-card-title>
                <v-card-text>닉네임을 변경하시겠어요?</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="green darken-1" text @click="changeNickname">변경</v-btn>
                  <v-btn color="grey" text @click="changeNick.dialog = false">취소</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-card-actions>
        </div>
      </v-expand-transition>
    </v-card-text>
    <v-divider></v-divider>
  </v-card>
</template>

<script>
export default {
  name: "Setting",
  data() {
    return {
      showPwChange: false,
      showNickChange: false,
      changePw: {
        data: null,
        check: null,
        error: null
      },
      changeNick: {
        data: null,
        error: null,
        dialog: false
      }
    };
  },
  methods: {
    async checkNickname() {
      this.changeNick.error = null;
      if (!this.changeNick.data) {
        this.changeNick.error = "변경할 닉네임을 입력해주세요";
      } else {
        try {
          const { data } = await this.$http.get(
            `${this.$store.state.ServerURL}/newuser/checkNickname`,
            {
              params: {
                nickname: this.changeNick.data
              }
            }
          );
          if (data) {
            this.changeNick.dialog = true;
          } else {
            this.changeNick.error = "해당 닉네임은 이미 사용중입니다.";
          }
        } catch (e) {
          console.error(e);
          this.changeNick.error = "Server error";
        }
      }
    },
    async changeNickname() {
      this.changeNick.error = null;
      try {
        const { data } = await this.$http.put(
          `${this.$store.state.ServerURL}/user/change_nickname`,
          null,
          {
            params: {
              nickname: this.changeNick.data
            }
          }
        );
        this.changeNick.dialog = false;
        if (data === "sucess") {
          this.$store.commit("NicknameChange", this.changeNick.data);
          this.$store.commit("ShowAlert", {
            flag: true,
            msg: "닉네임이 성공적으로 변경되었습니다!",
            color: "info"
          });
          this.$router.push({
            name: "UserSetting",
            params: { nickname: this.changeNick.data }
          });
          setTimeout(() => {
            this.$store.commit("ShowAlert", { flag: false, msg: "" });
          }, 2000);
        } else {
          this.changeNick.error = "해당 닉네임은 이미 사용중입니다.";
        }
      } catch (e) {
        console.error(e);
        this.changeNick.error = "Server error";
      }
    },
    async changePassword() {
      this.changePw.error = null;
      if (!this.changePw.data || !this.changePw.check) {
        this.changePw.error = "새 비밀번호, 확인문자를 입력해주세요";
      } else if (this.changePw.data !== this.changePw.check) {
        this.changePw.error = "새 비밀번호와 확인문자가 일치하지 않습니다.";
      } else {
        try {
          const { data } = await this.$http.put(
            `${this.$store.state.ServerURL}/user/change_pw`,
            null,
            {
              params: {
                password: this.changePw.data
              }
            }
          );
          if (data) {
            this.$store.commit("ShowAlert", {
              flag: true,
              msg: "비밀번호가 성공적으로 변경되었습니다!",
              color: "info"
            });
            setTimeout(() => {
              this.$store.commit("ShowAlert", { flag: false, msg: "" });
            }, 2000);
          }
        } catch (e) {
          this.changePw.error = "server error.";
          console.error(e);
        }
      }
    }
  }
};
</script>

<style scoped>
.warn {
  color: red;
}
</style>
