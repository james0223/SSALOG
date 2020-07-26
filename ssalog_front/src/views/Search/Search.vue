<template>
  <div>
    <SearchBar />
  </div>
</template>

<script>
import SearchBar from "@/components/SearchBar.vue";

export default {
  name: "Search",
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
