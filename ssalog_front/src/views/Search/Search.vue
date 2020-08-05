<template>
  <v-container>
    <SearchBar SelectedCategoryIdx="0" />
  </v-container>
</template>

<script>
import SearchBar from "@/components/SearchBar.vue";

export default {
  name: "Search",
  components: {
    SearchBar
  }
};
</script>

<style></style>
