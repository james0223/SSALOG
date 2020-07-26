<template>
  <div>
    사용자 조회 list
    <SearchBar SelectedCategoryIdx="3" />
    {{ q }}
  </div>
</template>

<script>
import SearchBar from "@/components/SearchBar.vue";

export default {
  name: "AccountList",
  components: {
    SearchBar
  },
  data() {
    return {
      page: 0,
      q: this.$route.query.q,
      users: []
    };
  },
  mounted() {
    if (!this.q) {
      // 전체 user 조회
      this.q = "검색어가 없으므로 걍 전체리스트한다 ㅇㅋ?";
    } else {
      // 닉 기반으로 조회
    }
  }
};
</script>

<style></style>
