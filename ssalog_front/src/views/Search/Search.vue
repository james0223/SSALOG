<template>
  <div>
    <SearchBar :category="category" />
  </div>
</template>

<script>
import SearchBar from "@/components/SearchBar.vue";

export default {
  name: "Search",
  data() {
    return {
      category: ["문제번호", "문제제목", "문제유형", "회원"]
    };
  },
  components: {
    SearchBar
  },
  methods: {
    goSearch() {
      if (this.selectedCatagory === "회원") {
        this.$router.push({ name: "AccountList", query: { search: this.search } });
      }
    }
  }
};
</script>

<style></style>
