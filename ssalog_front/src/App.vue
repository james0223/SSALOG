<template>
  <v-app>
    <Header
      v-if="
        $route.name === 'Home' ||
          $route.name === 'Account' ||
          $route.name === 'SSALOG' ||
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
