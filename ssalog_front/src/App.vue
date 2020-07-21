<template>
  <v-app>
    <router-link :to="{ name: 'SignUp' }">회원가입</router-link>
    <router-view />
  </v-app>
</template>

<script>
export default {
  name: "App",

  components: {},

  data: () => ({})
};
</script>
