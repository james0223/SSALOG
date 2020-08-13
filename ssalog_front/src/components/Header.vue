<template>
  <v-app-bar color="white" max-height="65">
    <v-container fluid>
      <v-row class="align-center">
        <v-col cols="1" md="2">
          <v-avatar class="pointButton" color="lighten-5" size="65">
            <v-img
              contain
              max-height="90%"
              src="@/assets/images/logo.jpg"
              @click="changeRoute('Home')"
            ></v-img>
          </v-avatar>
        </v-col>
        <!-- <v-col cols="1.5" class="text-center">
          <v-btn :ripple="false" class="pa-0 no-background-hover" text @click="changeRoute(2)"
            >GROUP</v-btn
          >
        </v-col>-->
        <v-col cols="8" md="6" class="text-center">
          <SearchBar :SelectedCategoryIdx="0" class="mt-7" />
        </v-col>
        <!-- <v-col cols="1.5" class="text-center">
          <v-btn :ripple="false" class="pa-0 no-background-hover" text>Community</v-btn>
        </v-col>-->
        <v-col cols="3" class="text-center py-0">
          <v-row no-gutters v-if="!$store.state.accessToken">
            <v-col cols="5" @click="changeRoute('Login')">
              <small class="pointButton">로그인</small>
            </v-col>
            <v-col cols="1">|</v-col>
            <v-col cols="6" @click="changeRoute('SignUp')">
              <small class="pointButton">회원가입</small>
            </v-col>
          </v-row>
          <v-menu offset-y v-if="$store.state.accessToken" no-gutters>
            <template v-slot:activator="{ on, attrs }">
              <v-card v-bind="attrs" v-on="on" flat>
                <v-row no-gutters>
                  <v-col cols="4">
                    <v-avatar>
                      <v-img contain max-height="80%" :src="userThumbnail" alt="유저썸네일"></v-img>
                    </v-avatar>
                  </v-col>
                  <v-col cols="6">
                    <div class="text-left subtitle">{{ nickname }} 님</div>
                    <div class="text-left caption">환영합니다</div>
                  </v-col>
                  <v-col cols="2" class="d-flex align-center">
                    <v-icon>mdi-chevron-down</v-icon>
                  </v-col>
                </v-row>
              </v-card>
            </template>
            <v-list>
              <v-list-item-group color="primary">
                <v-list-item v-for="(item, i) in items" :key="i" @click="userMenu(i)">
                  <v-list-item-title v-text="item"></v-list-item-title>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-menu>
        </v-col>
        <v-col cols="1" class="d-none d-md-flex">
          <v-btn text block @click="goSite('https://www.acmicpc.net/')">백준</v-btn>
        </v-col>
      </v-row>
    </v-container>
  </v-app-bar>
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
      items: ["계정설정", "내 쌀로그", "로그아웃"]
    };
  },
  computed: mapState(["userThumbnail", "nickname", "accessToken"]),
  methods: {
    userMenu(idx) {
      if (idx === 0) {
        this.$router.push({ name: "UserSetting", params: { nickname: this.nickname } });
      } else if (idx === 1) {
        this.$router.push({ name: "SSalogMain", params: { nickname: this.nickname } });
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
