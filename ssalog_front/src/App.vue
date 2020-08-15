<template>
  <v-app>
    <Header></Header>
    <!-- <v-container> -->
    <router-view :key="$route.fullPath"></router-view>
    <!-- </v-container> -->
    <v-snackbar :color="alertColor" :value="showAlert" centered multi-line>
      {{ AlertMessage }}
    </v-snackbar>
  </v-app>
</template>

<script>
import Axios from "axios";
import Header from "@/components/Header.vue";
import { mapState } from "vuex";

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
      this.$store.dispatch("autoRefresh");
      if (this.$store.state.accessToken) {
        // 갱신 후에도 토큰이 유효하다면
        Axios.defaults.headers.common.Authorization = `Bearer ${this.$store.state.accessToken}`;
      }
    }
  },
  computed: mapState(["showAlert", "AlertMessage", "alertColor"]),
  watch: {
    // eslint-disable-next-line
    AlertMessage: function(newVal) {
      if (newVal === "토큰이 만료되었습니다. 다시 로그인하세요") {
        setTimeout(() => {
          this.$router.push("/Login");
        }, 2000);
      }
    }
  }
};
</script>
