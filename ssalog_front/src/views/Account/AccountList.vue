<template>
  <div>
    {{ search }}
  </div>
</template>

<script>
export default {
  name: "AccountList",
  data() {
    return {
      page: 0,
      search: this.$route.query.search,
      users: []
    };
  },
  mounted() {
    if (!this.search) {
      // 전체 user 조회
      this.search = "검색어가 없으므로 걍 전체리스트한다 ㅇㅋ?";
    } else {
      // 닉 기반으로 조회
    }
  }
};
</script>

<style></style>
