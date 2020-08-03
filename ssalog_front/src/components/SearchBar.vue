<template>
  <v-container>
    <v-row align="center">
      <v-col class="d-flex" cols="2">
        <v-select v-model="SelectedCategory" :items="category" label="검색유형"></v-select>
      </v-col>
      <v-col class="d-flex" cols="9">
        <v-text-field
          v-model="q"
          label="검색어를 입력해주세요"
          append-icon="mdi-magnify"
          @keypress.enter="goSearch"
        ></v-text-field>
      </v-col>
      <v-col clos="1">
        <v-btn tile block color="primary" @click="goSearch">검색</v-btn>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: "SearchBar",
  data() {
    return {
      category: ["문제번호", "문제제목", "문제유형", "회원"],
      q: "",
      SelectedCategory: ""
    };
  },
  methods: {
    goSearch() {
      if (this.category.indexOf(this.SelectedCategory) === 3) {
        this.$router.push({ name: "AccountList", query: { q: this.q } });
      } else {
        this.$router.push({
          name: "ProblemList",
          query: { q: this.q, categoryIdx: this.category.indexOf(this.SelectedCategory) }
        });
      }
    }
  },
  props: {
    SelectedCategoryIdx: Number
  },
  mounted() {
    this.SelectedCategory = this.category[this.SelectedCategoryIdx];
  }
};
</script>

<style></style>
