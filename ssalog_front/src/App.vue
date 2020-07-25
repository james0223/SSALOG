<template>
  <v-app>
    <Header
      v-if="$route.name === 'Home' || $route.name === 'Account' || $route.name === 'SSALOG'"
    ></Header>
    <v-divider></v-divider>
    <v-container align="center" fill-height>
      <router-view></router-view>
    </v-container>
  </v-app>
</template>

<script>
import Header from "@/components/Header.vue";

export default {
  name: "App",

  components: {
    Header
  },

  data: () => ({
    //
  })
};
</script>
