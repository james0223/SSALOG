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
      search: this.$route.params.search
    };
  }
};
</script>

<style></style>
