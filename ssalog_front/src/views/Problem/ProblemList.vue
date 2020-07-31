<template>
  <div>
    문제 조회 list
    <SearchBar :SelectedCategoryIdx="categoryIdx" />
    {{ q }}
    {{ categoryIdx }}
  </div>
</template>

<script>
import SearchBar from "@/components/SearchBar.vue";

export default {
  name: "ProblemList",
  components: {
    SearchBar
  },
  data() {
    return {
      page: 0,
      q: this.$route.query.q,
      categoryIdx: this.$route.query.categoryIdx,
      problems: []
    };
  },
  mounted() {
    if (!this.q) {
      // 전체 problem 조회
      this.q = "검색어가 없으므로 걍 전체리스트한다.";
    } else {
      // 닉 기반으로 조회
    }
  }
};
</script>

<style></style>
