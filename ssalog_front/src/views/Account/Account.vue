<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col lg="3" cols="12">
        <UserNav></UserNav>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col lg="6" cols="12">
        <v-card flat height="60vh" class="pa-8">
          <h2 class="font-weight-light mb-3">비밀번호 변경</h2>
          <v-divider></v-divider>
          <v-row>
            <body class="mt-3 ml-3">
              비밀번호를 변경하기 전에 다시 한번 확인합니다.
            </body>
          </v-row>
          <v-row>
            <v-dialog v-model="dialog" max-width="600px">
              <template v-slot:activator="{ on, attrs }">
                <v-btn class="mt-5 ml-3 mb-12" v-bind="attrs" v-on="on">비밀번호 변경</v-btn>
              </template>
              <v-card>
                <v-card-title>
                  <span class="headline">비밀번호 변경</span>
                </v-card-title>
                <v-card-text>
                  <v-container>
                    <v-row>
                      <v-col cols="12">
                        <v-text-field
                          label="새 비밀번호"
                          type="password"
                          v-model="changePw.password"
                          required
                        ></v-text-field>
                      </v-col>
                      <v-col cols="12">
                        <v-text-field
                          label="비밀번호 확인"
                          type="password"
                          v-model="changePw.passwordCheck"
                          required
                        ></v-text-field>
                      </v-col>
                    </v-row>
                    <small class="warn" v-if="changePw.passwordErrorMsg">
                      {{ changePw.passwordErrorMsg }}
                    </small>
                  </v-container>
                </v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="blue darken-1" text @click="dialog = false">닫기</v-btn>
                  <v-btn color="blue darken-1" text @click="changePassword">변경</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-row>
          <h2 class="font-weight-light mb-3 warn">계정 삭제</h2>
          <v-divider></v-divider>
          <v-row>
            <body class="mt-3 ml-3">
              계정을 삭제하시면
              <b>복구하실 수 없습니다</b>. 신중하게 결정해 주세요
            </body>
          </v-row>
          <v-row>
            <v-btn class="mt-5 ml-3">
              <body class="warn">
                계정 삭제
              </body>
            </v-btn>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import UserNav from "@/components/UserNav.vue";
import axios from "axios";

export default {
  name: "Account",
  data() {
    return {
      dialog: false,
      changePw: {
        password: null,
        passwordCheck: null,
        passwordErrorMsg: null
      }
    };
  },
  components: {
    UserNav
  },
  methods: {
    async changePassword() {
      if (!this.changePw.password || !this.changePw.passwordCheck) {
        this.changePw.passwordErrorMsg = "새 비밀번호, 확인문자를 입력해주세요";
      } else if (this.changePw.password !== this.changePw.passwordCheck) {
        this.changePw.passwordErrorMsg = "새 비밀번호와 확인문자가 일치하지 않습니다.";
      } else {
        try {
          const { data } = await axios.put(`${this.$store.state.ServerURL}/user/change_pw`, null, {
            params: {
              password: this.changePw.password
            }
          });
          if (data) {
            alert("비밀번호가 성공적으로 변경되었습니다!");
            this.dialog = false;
          }
        } catch (e) {
          this.changePw.passwordErrorMsg = "오류가 발생했습니다.";
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
