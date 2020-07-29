<template>
  <v-app>
    <Header
      v-if="
        $route.name === 'Home' ||
          $route.name === 'Account' ||
          $route.name === 'SSALOG' ||
          $route.name === 'LogDetail' ||
          $route.name === 'ProblemDetail'
      "
    ></Header>
    <v-divider></v-divider>
    <v-container fill-height>
      <router-view :key="$route.fullPath"></router-view>
    </v-container>
  </v-app>
</template>

<script>
import Axios from "axios";
import Header from "@/components/Header.vue";

export default {
  name: "App",

  components: {
    Header
  },

  data: () => ({
    //
  }),
  created() {
    // 이거 안해놓고 새로고침하면 헤더 설정 다 날아감 ^^ㅗ
    if (this.$store.state.accessToken) {
      Axios.defaults.headers.common.Authorization = `Bearer ${this.$store.state.accessToken}`;
    }
  }
};
</script>
