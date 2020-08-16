<template>
  <v-row align="center" class="align-baseline">
    <v-col class="d-flex" cols="4">
      <v-select v-model="SelectedCategory" :items="category" label="검색유형"></v-select>
    </v-col>
    <v-col cols="6">
      <v-text-field
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
      ></v-select>
    </v-col>
    <v-col cols="2">
      <v-btn rounded block outlined color="primary" @click="goSearch">검색</v-btn>
    </v-col>
  </v-row>
</template>

<script>
import { mapMutations } from "vuex";

export default {
  name: "SearchBar",
  data() {
    return {
      category: ["문제번호", "문제제목", "문제유형", "회원"],
      SelectedCategory: null
    };
  },
  methods: {
    ...mapMutations(["ShowAlert"]),
    goSearch() {
      if (this.category.indexOf(this.SelectedCategory) === 2) {
        if (!this.keywords || this.keywords.length === 0) {
          this.SelectedCategory = "문제제목";
          this.q = null;
        }
      }
      // if (this.category.indexOf(this.SelectedCategory) === 1) {
      //   if (!this.q) {
      //     this.SelectedCategory = "문제제목";
      //     this.q = null;
      //   }
      // }
      if (this.category.indexOf(this.SelectedCategory) === 3) {
        this.$router.push({ name: "AccountList", query: { q: this.q } }).catch(() => {});
      } else {
        this.$router
          .push({
            name: "ProblemList",
            query: {
              q: !this.q ? null : this.q,
              keywords: this.keywords,
              categoryIdx: this.category.indexOf(this.SelectedCategory)
            }
          })
          .catch(() => {});
      }
    }
  },
  props: {
    SelectedCategoryIdx: Number,
    keywords: Array,
    q: String
  },
  watch: {
    keywords(newVal) {
      if (newVal.length > 3) {
        this.keywords.pop();
        this.ShowAlert({ flag: true, msg: "3개까지 선택가능합니다.", color: "primary" });
        setTimeout(() => {
          this.ShowAlert({ flag: false, msg: "" });
        }, 2000);
      }
    }
  },
  mounted() {
    this.SelectedCategory = this.category[this.SelectedCategoryIdx];
  }
};
</script>

<style></style>
