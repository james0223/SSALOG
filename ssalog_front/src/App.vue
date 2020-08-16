<template>
  <v-app class="Background">
    <Header></Header>
    <!-- <v-container> -->
    <router-view :key="$route.fullPath"></router-view>
    <!-- </v-container> -->
    <v-snackbar :color="alertColor" :value="showAlert" centered multi-line>
      {{ AlertMessage }}
    </v-snackbar>
    <v-footer class="font-weight-medium" height="110" color="#37474F">
      <v-col class="text-center white--text" cols="12 ">
        2020-&copy;SSALOG
      </v-col>
      <v-col class="text-center white--text" cols="12">
        ssalogofficial@gmail.com
      </v-col>
    </v-footer>
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
<style scoped>
.Background {
  background: rgb(255, 255, 255);
  background: radial-gradient(
    circle,
    rgba(255, 255, 255, 1) 0%,
    rgba(255, 255, 255, 1) 66%,
    rgba(237, 237, 237, 1) 100%
  ) !important;
}
</style>
