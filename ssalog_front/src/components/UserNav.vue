<template>
  <v-card flat height="60vh">
    <v-row justify="center">
      <v-avatar color="orange" size="260" class="mt-8">
        <span class="white--text headline">62</span>
      </v-avatar>
    </v-row>
    <v-row justify="center">
      <h3>uutaein</h3>
    </v-row>
    <v-row justify="center">
      <v-btn outlined large class="mt-6 mb-5">Edit Profile</v-btn>
    </v-row>
    <v-row justify="center">
      <v-btn class="ma-2" text><v-icon left>mdi-account-heart</v-icon>following</v-btn>
      <v-btn class="ma-2" text><v-icon left>mdi-account-arrow-left</v-icon>follower</v-btn>
    </v-row>
    <v-row justify="center">
      <v-btn class="ma-2" text><v-icon left>mdi-star</v-icon>star</v-btn>
    </v-row>
  </v-card>
</template>

<script>
export default {};
</script>

<style></style>
