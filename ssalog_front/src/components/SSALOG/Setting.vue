<template>
  <v-card flat height="60vh" class="pa-8">
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
            <v-btn color="blue darken-1" text @click="changeNickname">변경</v-btn>
          </v-card-actions>
        </div>
      </v-expand-transition>
    </v-card-text>
    <v-divider></v-divider>
  </v-card>
</template>

<script>
import axios from "axios";

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
        error: null
      }
    };
  },
  methods: {
    async changeNickname() {
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
          if (data === true) {
            // 요청 보내기
            alert("닉네임이 변경되기까지 api 만드는게 필요");
          } else {
            this.changeNick.error = "해당 닉네임은 이미 사용중입니다.";
          }
        } catch (e) {
          console.error(e);
          this.changeNick.error = "Server error";
        }
      }
    },
    async changePassword() {
      if (!this.changePw.data || !this.changePw.check) {
        this.changePw.error = "새 비밀번호, 확인문자를 입력해주세요";
      } else if (this.changePw.data !== this.changePw.check) {
        this.changePw.error = "새 비밀번호와 확인문자가 일치하지 않습니다.";
      } else {
        try {
          const { data } = await axios.put(`${this.$store.state.ServerURL}/user/change_pw`, null, {
            params: {
              password: this.changePw.data
            }
          });
          if (data) {
            alert("비밀번호가 성공적으로 변경되었습니다!");
            this.dialog = false;
          }
        } catch (e) {
          this.changePw.error = "오류가 발생했습니다.";
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
