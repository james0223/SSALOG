<template>
  <v-container>
    <v-card :dark="$store.state.isDark" class="mt-15" color="transparent" flat>
      <v-card-title>404 Page Not Found :(</v-card-title>
      <v-card-text>페이지를 찾을 수 없습니다.</v-card-text>
      <v-card-actions>
        <v-btn class="ml-2" @click="$router.push({ name: 'Naruto' })">두구두구두구</v-btn>
      </v-card-actions>
    </v-card>
  </v-container>
</template>

<script>
export default {
  name: "Error"
};
</script>

<style></style>
