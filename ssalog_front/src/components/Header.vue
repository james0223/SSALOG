<template>
  <nav>
    <v-app-bar class="Header" color="transparent" max-height="65">
      <v-container fluid>
        <v-row class="align-center">
          <v-col cols="1">
            <v-avatar class="pointButton" color="lighten-5" size="60">
              <v-img
                contain
                max-height="90%"
                src="@/assets/images/logo.png"
                @click="changeRoute('Home')"
              ></v-img>
            </v-avatar>
          </v-col>
          <v-col cols="1" class="text-center d-none d-sm-flex">
            <v-btn
              class="pa-0"
              text
              @click="
                $router.push({
                  name: 'AccountList'
                })
              "
              >쌀로그</v-btn
            >
          </v-col>
          <v-col cols="1" class="text-center d-none d-sm-flex">
            <v-btn
              class="pa-0"
              text
              @click="
                $router.push({
                  name: 'ProblemList',
                  query: {
                    q: '',
                    categoryIdx: 1
                  }
                })
              "
              >문제</v-btn
            >
          </v-col>
          <v-col cols="1" class="text-center d-none d-sm-flex">
            <v-btn
              v-if="nickname"
              class="pa-0"
              text
              @click="
                $router.push({
                  name: 'GroupList',
                  params: {
                    nickname
                  }
                })
              "
              >그룹</v-btn
            >
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="3" class="text-center mt-7 d-none d-sm-flex">
            <v-text-field
              v-model="q"
              placeholder="문제번호를 입력하세요"
              @keypress.enter="goSearch"
            ></v-text-field>
            <v-icon @click="goSearch" class="mb-4">mdi-magnify</v-icon>
          </v-col>
          <v-spacer></v-spacer>
          <v-col cols="8" sm="3" lg="2" class="text-center py-0">
            <v-row no-gutters v-if="!$store.state.accessToken">
              <v-col cols="5">
                <v-btn text @click="changeRoute('Login')" class="">로그인</v-btn>
              </v-col>
              <v-col cols="1" class=""><v-divider vertical></v-divider> </v-col>
              <v-col cols="6">
                <v-btn text @click="changeRoute('SignUp')" class="">회원가입</v-btn>
              </v-col>
            </v-row>
            <v-menu open-on-hover offset-y v-if="$store.state.accessToken" no-gutters>
              <template v-slot:activator="{ on, attrs }">
                <v-card color="transparent" v-bind="attrs" v-on="on" flat>
                  <v-row no-gutters>
                    <v-col cols="4" class="d-nome d-md-flex">
                      <v-avatar>
                        <v-img
                          contain
                          max-height="100%"
                          :src="userThumbnail"
                          alt="유저썸네일"
                        ></v-img>
                      </v-avatar>
                    </v-col>
                    <v-col cols="8">
                      <div class="text-left subtitle">{{ nickname }} 님</div>
                      <div class="text-left caption">환영합니다</div>
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
          <v-spacer></v-spacer>

          <v-col cols="1" class="d-none d-md-flex mr-6">
            <v-btn text block @click="goSite('https://www.acmicpc.net/')"
              ><img
                title="백준으로 이동합니다."
                style="height:1.5rem;"
                src="https://d2gd6pc034wcta.cloudfront.net/images/logo@2x.png"
            /></v-btn>
          </v-col>
          <v-col cols="1" class="d-flex d-sm-none mr-3">
            <v-app-bar-nav-icon @click.stop="drawer = !drawer"></v-app-bar-nav-icon>
          </v-col>
        </v-row>
      </v-container>
    </v-app-bar>
    <!--    햄버거 버튼 넣기 위한 nav drawer-->
    <!--    <v-navigation-drawer v-model="drawer" app right>-->
    <!--      <v-list nav>-->
    <!--        <v-list-item v-for="(item, i) in items" :key="i" @click="userMenu(i)">-->
    <!--          <v-list-item-title v-text="item"></v-list-item-title>-->
    <!--        </v-list-item>-->
    <!--      </v-list>-->
    <!--    </v-navigation-drawer>-->
  </nav>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Header",
  data() {
    return {
      drawer: false,
      items: ["내 쌀로그", "계정설정", "로그아웃"],
      q: null
    };
  },
  computed: mapState(["userThumbnail", "nickname", "accessToken"]),
  methods: {
    userMenu(idx) {
      if (idx === 0) {
        this.$router.push({
          name: "SSalogMain",
          params: { nickname: this.nickname }
        });
      } else if (idx === 1) {
        this.$router.push(`/SSALOG/${this.$store.state.nickname}/UserSetting`);
      } else {
        this.$store.dispatch("LOGOUT");
      }
    },
    changeRoute(name) {
      // console.log(this.$route.name === name);
      if (this.$route.name === name) {
        this.$router.go({ name });
      } else {
        this.$router.push({ name });
      }
    },
    goSite(site) {
      const win = window.open(site, "_blank");
      win.focus();
    },
    goSearch() {
      this.$router
        .push({ name: "ProblemList", query: { q: this.q, categoryIdx: 0 } })
        .catch(() => {});
    }
  }
};
</script>

<style scoped>
.pointButton {
  cursor: pointer;
}
</style>
