<template>
  <v-container ref="toc" class="TOC" />
</template>

<script>
/* eslint no-use-before-define: 0 */ // --> OFF
export default {
  name: "TOC",
  mounted() {
    const mainContents = document.querySelector(".main_content");
    const hTags = mainContents.querySelectorAll("h1, h2, h3");
    console.log(hTags);
    /* h1, h2, h3 태그가 있는지 확인한다 */
    const checkExistenceOfHTags = function() {
      if (mainContents === undefined) {
        return false;
      }
      return hTags.length !== 0;
    };
    checkExistenceOfHTags();
  }
};
</script>

<style scoped></style>
