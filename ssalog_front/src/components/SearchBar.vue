<template>
  <div>
    <v-row align="center">
      <v-col class="d-flex" cols="2">
        <v-select v-model="SelectedCategory" :items="category" label="검색유형"></v-select>
      </v-col>
      <v-col cols="8">
        <v-text-field
          autofocus
          v-if="SelectedCategory !== '문제유형'"
          v-model="q"
          label="검색어를 입력해주세요"
          append-icon="mdi-magnify"
          @keypress.enter="goSearch"
        ></v-text-field>
        <v-select
          v-if="SelectedCategory === '문제유형'"
          v-model="keywords"
          :items="
            $store.state.ProblemCategory.map(function(cat) {
              return cat.kor;
            })
          "
          multiple
          chips
          hint="3개까지 선택가능합니다."
          persistent-hint
        ></v-select>
      </v-col>
      <v-col cols="2">
        <v-btn tile block color="primary" @click="goSearch">검색</v-btn>
      </v-col>
    </v-row>
  </div>
</template>

<script>
export default {
  name: "SearchBar",
  data() {
    return {
      category: ["문제번호", "문제제목", "문제유형", "회원"],
      q: null,
      keywords: null,
      SelectedCategory: null
    };
  },
  methods: {
    goSearch() {
      if (this.category.indexOf(this.SelectedCategory) === 3) {
        this.$router.push({ name: "AccountList", query: { q: this.q } });
      } else {
        this.$router.push({
          name: "ProblemList",
          query: {
            q: this.q,
            keywords: this.keywords,
            categoryIdx: this.category.indexOf(this.SelectedCategory)
          }
        });
      }
    }
  },
  props: {
    SelectedCategoryIdx: Number
  },
  watch: {
    keywords(newVal) {
      if (newVal.length > 3) {
        this.keywords.pop();
      }
    }
  },
  mounted() {
    this.SelectedCategory = this.category[this.SelectedCategoryIdx];
  }
};
</script>

<style></style>
