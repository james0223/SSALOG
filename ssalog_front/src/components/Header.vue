<template>
  <v-container fluid class="pa-0">
    <v-app-bar flat color="white" height="95">
      <v-row class="align-center">
        <v-col cols="2">
          <v-avatar class="pointButton" color="lighten-5" size="70">
            <v-img
              contain
              max-height="100%"
              src="@/assets/images/logo.jpg"
              @click="changeRoute('Home')"
            ></v-img>
          </v-avatar>
        </v-col>
        <!-- <v-col cols="1.5" class="text-center">
          <v-btn :ripple="false" class="pa-0 no-background-hover" text @click="changeRoute(2)"
            >GROUP</v-btn
          >
        </v-col> -->
        <v-col cols="5" class="text-center">
          <SearchBar :SelectedCategoryIdx="0" class="mt-5" />
        </v-col>
        <!-- <v-col cols="1.5" class="text-center">
          <v-btn :ripple="false" class="pa-0 no-background-hover" text>Community</v-btn>
        </v-col> -->
        <v-col cols="2" class="text-center py-0">
          <v-row no-gutters v-if="!$store.state.accessToken">
            <v-col cols="5" @click="changeRoute('Login')">
              <small class="pointButton">로그인</small>
            </v-col>
            <v-col cols="1">|</v-col>
            <v-col cols="6" @click="changeRoute('SignUp')">
              <small class="pointButton">회원가입</small>
            </v-col>
          </v-row>
          <v-menu v-model="showMyMenu" offset-y v-if="$store.state.accessToken" no-gutters>
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
              <v-list-item-group v-model="items" color="primary">
                <v-list-item v-for="(item, index) in items" :key="index">
                  <v-list-item-title @click="userMenu(index)">{{ item.title }}</v-list-item-title>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-menu>
        </v-col>
        <v-col cols="3" class="text-center py-0 d-flex justify-end">
          <v-btn
            text
            tile
            height="100px"
            style="border : 1px solid rgba(0,0,0,.12);"
            @click="goSite('https://www.acmicpc.net/')"
            >BOJ</v-btn
          >
          <v-btn
            text
            tile
            height="100px"
            style="border : 1px solid rgba(0,0,0,.12);"
            @click="showAlert"
            >SW <br />EXPERT</v-btn
          >
          <v-btn
            text
            tile
            height="100px"
            style="border : 1px solid rgba(0,0,0,.12);"
            @click="showAlert"
          >
            Code
            <br />Force
          </v-btn>
        </v-col>
      </v-row>
    </v-app-bar>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
import SearchBar from "@/components/SearchBar.vue";

export default {
  name: "Header",
  components: {
    SearchBar
  },
  data() {
    return {
      showMyMenu: false,
      items: [{ title: "정보수정" }, { title: "내 쌀로그" }, { title: "로그아웃" }]
    };
  },
  computed: mapState(["userThumbnail", "username"]),
  methods: {
    userMenu(menu) {
      if (menu === 0) {
        this.$router.push({ name: "Account" });
      } else if (menu === 1) {
        this.$router.push({ name: "SSALOG/Main" });
      } else {
        this.$store.dispatch("LOGOUT");
      }
    },
    changeRoute(name) {
      this.$router.push({ name });
    },
    goSite(site) {
      const win = window.open(site, "_blank");
      win.focus();
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
