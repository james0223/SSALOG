<template>
  <v-container class="pa-0">
    <v-app-bar app color="white" height="95">
      <v-row class="align-center">
        <v-avatar class="mr-3" color="lighten-5" size="70">
          <v-img
            contain
            max-height="100%"
            src="@/assets/images/logo.jpg"
            @click="changeRoute(0)"
          ></v-img>
        </v-avatar>

        <v-toolbar-title class="font-weight-black headline"> </v-toolbar-title>
        <!-- <v-col cols="1.5" class="text-center">
          <v-btn :ripple="false" class="pa-0 no-background-hover" text @click="changeRoute(2)"
            >GROUP</v-btn
          >
        </v-col> -->
        <v-col cols="1.5" class="text-center">
          <v-btn :ripple="false" class="pa-0 no-background-hover" text @click="changeRoute(3)">
            <b>검색</b>
          </v-btn>
        </v-col>
        <!-- <v-col cols="1.5" class="text-center">
          <v-btn :ripple="false" class="pa-0 no-background-hover" text>Community</v-btn>
        </v-col> -->
        <v-col cols="2" class="text-center py-0">
          <v-row no-gutters v-if="!$store.state.accessToken">
            <v-col cols="5" @click="$router.push({ name: 'Login' })">
              <small class="pointButton">로그인</small>
            </v-col>
            <v-col cols="1">|</v-col>
            <v-col cols="6" @click="$router.push({ name: 'SignUp' })">
              <small class="pointButton">회원가입</small>
            </v-col>
          </v-row>
          <v-menu v-model="showMenu" offset-y v-if="$store.state.accessToken" no-gutters>
            <template v-slot:activator="{ on, attrs }">
              <v-card v-bind="attrs" v-on="on" flat>
                <v-row no-gutters>
                  <v-col cols="4">
                    <v-avatar>
                      <img :src="userThumbnail" alt="유저썸네일" />
                    </v-avatar>
                  </v-col>
                  <v-col cols="8">
                    <div class="justify-space-between subtitle">{{ username }} 님</div>
                    <div class="justify-space-between caption">환영합니다</div>
                  </v-col>
                </v-row>
              </v-card>
            </template>
            <v-list>
              <v-list-item-group v-model="item" color="primary">
                <v-list-item v-for="(item, index) in items" :key="index">
                  <v-list-item-title @click="doFunc(index)">{{ item.title }}</v-list-item-title>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-menu>
        </v-col>
        <v-col cols="3" class="text-center py-0 d-flex justify-space-between">
          <v-btn class="flex-grow-1" text tile height="100px" small>SWEA</v-btn>
          <v-btn
            text
            class="flex-grow-1"
            small
            tile
            height="100px"
            style="border-left: 1px solid; border-right: 1px solid; border-color: rgba(0,0,0,.12);"
            >BOJ</v-btn
          >
          <v-btn class="flex-grow-1" text tile height="100px" small>
            Code
            <br />Force
          </v-btn>
        </v-col>
      </v-row>
    </v-app-bar>
  </v-container>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";

export default {
  name: "Header",
  data: () => ({
    showMenu: false,
    items: [{ title: "정보수정" }, { title: "내 쌀로그" }, { title: "로그아웃" }]
  }),
  computed: mapState(["userThumbnail", "username"]),
  methods: {
    doFunc(i) {
      if (i === 0) {
        this.$router.push({ name: "Account" });
      } else {
        axios
          .post(`${this.$store.state.serverURL}/user/out`)
          .then(() => {
            alert("로그아웃 되었다.");
          })
          .catch(err => console.error(err));
        this.$store.commit("LOGOUT");
      }
    },
    changeRoute(i) {
      if (i === 0) {
        this.$router.push({ name: "Home" });
      } else if (i === 1) {
        this.$router.push({ name: "SSALOG" });
      } else if (i === 3) {
        this.$router.push({ name: "Search" });
      }
    }
  }
};
</script>

<style scoped>
.pointButton {
  cursor: pointer;
}
.no-background-hover::before {
  background-color: transparent !important;
}
</style>
