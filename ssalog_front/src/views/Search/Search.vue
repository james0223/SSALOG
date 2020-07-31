<template>
  <div>
    검색
    <SearchBar SelectedCategoryIdx="0" />
    최근에 많이 검색된 내용
  </div>
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
