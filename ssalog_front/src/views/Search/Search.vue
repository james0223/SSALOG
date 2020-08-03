<template>
  <v-container>
    <SearchBar SelectedCategoryIdx="0" />
    최근에 많이 검색된 내용
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
