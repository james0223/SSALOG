<template>
  <v-row align="center" class="align-baseline">
    <v-col class="d-flex" cols="3">
      <v-menu offset-y>
        <template v-slot:activator="{ on, attrs }">
          <v-btn
            color="blue"
            block
            :outlined="isDark"
            tile
            :dark="!isDark"
            v-bind="attrs"
            v-on="on"
          >
            {{ SelectedCategory }}
            <v-icon>mdi-chevron-down</v-icon>
          </v-btn>
        </template>
        <v-list :dark="isDark">
          <v-list-item v-for="(cat, idx) in category" :key="idx" @click="changeCat(idx)">
            <v-list-item-title>{{ cat }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-menu>
    </v-col>
    <v-col cols="7">
      <v-text-field
        :dark="isDark"
        v-if="SelectedCategory !== '문제유형'"
        v-model="q"
        label="검색어를 입력해주세요"
        append-icon="mdi-magnify"
        @keypress.enter="goSearch"
      ></v-text-field>
      <v-select
        :dark="isDark"
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
      <v-btn color="blue" rounded block :dark="isDark" outlined @click="goSearch">검색</v-btn>
    </v-col>
  </v-row>
</template>

<script>
import { mapState, mapMutations } from "vuex";

export default {
  name: "SearchBar",
  data() {
    return {
      category: ["문제번호", "문제제목", "문제유형", "회원"],
      SelectedCategory: null,
      q: null,
      keywords: []
    };
  },
  methods: {
    ...mapMutations(["ShowAlert"]),
    changeCat(idx) {
      this.SelectedCategory = this.category[idx];
    },
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
    Keywords: [String, Array],
    Q: String
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
  computed: mapState(["isDark", "ColorSet"]),
  mounted() {
    this.q = this.Q;
    if (typeof this.Keywords === "string") {
      this.keywords = [this.Keywords];
    } else {
      this.keywords = this.Keywords ? this.Keywords : [];
    }

    this.SelectedCategory = this.category[this.SelectedCategoryIdx];
  }
};
</script>

<style></style>
