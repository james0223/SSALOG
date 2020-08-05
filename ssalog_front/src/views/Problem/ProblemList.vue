<template>
  <v-container>
    <SearchBar :SelectedCategoryIdx="categoryIdx" />
    <h2 class="text-center" v-if="problems.length === 0">검색결과가 없습니다 :(</h2>
    <h2 v-if="problems.length !== 0">{{ $route.query.q }}에 대한 검색결과입니다.</h2>
    <!-- <v-col v-for="(problem, i) in problems" :key="i" cols="4">{{ problem }}</v-col> -->
    <v-hover style="cursor:pointer" v-slot:default="{ hover }">
      <v-card @click="visitProblemDeatil(problem.id)" :elevation="hover ? 12 : 2">
        <v-card-title>{{ problem.id }} - {{ problem.name }}</v-card-title>
        <v-card-text>등록된 리뷰 : {{ problem.solutions }}개</v-card-text>
        <v-divider></v-divider>
        <v-card-text>
          <v-chip-group disabled>
            <v-chip v-for="tag in tags" :key="tag">
              {{ tag }}
            </v-chip>
          </v-chip-group>
        </v-card-text>
      </v-card>
    </v-hover>

    <div class="text-center">
      <v-pagination v-model="searchData.page" :length="4" circle></v-pagination>
    </div>
  </v-container>
</template>

<script>
import axios from "axios";
import qs from "qs";
import SearchBar from "@/components/SearchBar.vue";

export default {
  name: "ProblemList",
  components: {
    SearchBar
  },
  data() {
    return {
      isNoProblem: false,
      searchData: {
        direction: "ASC",
        problemid: this.$route.query.q,
        problemname: this.$route.query.q,
        page: 1,
        size: 24
      },
      categoryIdx: this.$route.query.categoryIdx,
      searchMethods: ["to_problemid", "to_problemname", "to_keyword"],
      problems: [],
      problem: {
        id: null,
        name: null,
        solutions: 4
      },
      tags: ["DFS", "BFS", "DP", "GREEDY"]
    };
  },
  methods: {
    visitProblemDeatil(id) {
      this.$router.push({
        name: "ProblemDetail",
        query: {
          id
        }
      });
    },
    async fetchProblems() {
      try {
        const { data } = await axios.get(
          `${this.$store.state.ServerURL}/newuser/search/${this.searchMethods[this.categoryIdx]}`,
          {
            params: {
              ...this.searchData,
              keyword: this.$route.query.keywords // route.query.keyword 가져와야함
            },
            paramsSerializer: params => {
              console.log(qs.stringify(params));
              return qs.stringify(params);
            }
          }
        );
        this.problems = data.content;
        this.problem.id = data.content[0].problemid;
        this.problem.name = data.content[0].problemname;
      } catch (e) {
        console.error(e);
        this.isNoProblem = true;
      }
    }
  },
  mounted() {
    if (this.$route.query.q || this.$route.query.keywords) {
      this.fetchProblems();
    }
  }
};
</script>

<style></style>
