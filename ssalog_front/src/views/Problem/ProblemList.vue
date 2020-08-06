<template>
  <v-container>
    <SearchBar :SelectedCategoryIdx="categoryIdx" />
    <div v-if="isNoResult">
      <h2 class="text-center">
        검색결과가 없습니다.
      </h2>
      <h3 class="text-center">쌀로그의 문제는 여러분의 쌀로그가 모여 생성됩니다.</h3>
      <h4 class="text-center">그러니까 문제를 풀러 가지 않으실래요?!</h4>
      <a class="text-center" href="https://www.acmicpc.net/">문제풀러가기</a>
    </div>
    <h2 v-if="!isNoResult">{{ resultMsg }}에 대한 검색결과입니다.</h2>
    <v-row>
      <v-col v-for="(problem, i) in problems" :key="i" cols="12">
        문제가 들어올 자리, 아래에는 샘플
      </v-col>
      <v-col cols="12">
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
      </v-col>
    </v-row>

    <infinite-loading
      v-if="limit >= searchData.page && !isError"
      @infinite="infiniteHandler"
      spinner="waveDots"
    ></infinite-loading>
  </v-container>
</template>

<script>
import axios from "axios";
import qs from "qs";
import SearchBar from "@/components/SearchBar.vue";
import InfiniteLoading from "vue-infinite-loading";

export default {
  name: "ProblemList",
  components: {
    SearchBar,
    InfiniteLoading
  },
  data() {
    return {
      searchData: {
        direction: "ASC",
        problemid: this.$route.query.q,
        problemname: this.$route.query.q,
        page: 1,
        size: 12
      },
      limit: 9999,
      categoryIdx: this.$route.query.categoryIdx,
      searchMethods: ["to_problemid", "to_problemname", "to_keyword"],
      problems: [],
      // 샘플용임
      problem: {
        id: 9999,
        name: "샘플용 문제",
        solutions: 4
      },
      tags: ["샘플", "BFS", "DP", "GREEDY"],
      isNoResult: false,
      isError: false
    };
  },
  methods: {
    infiniteHandler($state) {
      setTimeout(() => {
        this.fetchProblems();
        $state.loaded();
      }, 2000);
    },
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
              keyword: this.$route.query.keywords
            },
            // 배열을 보내기 위한 방법, npm i qs 필요
            paramsSerializer: params => {
              console.log(params);
              console.log(qs.stringify(params));
              return qs.stringify(params);
            }
          }
        );
        if (data.totalElements === 0) {
          this.isNoResult = true;
        }
        this.searchData.page += 1;
        this.problems = [...this.problems, ...data.content];
        this.limit = Number(data.totalPages);
        // 임시로 처리
        this.problem.id = data.content[0].problemid;
        this.problem.name = data.content[0].problemname;
      } catch (e) {
        this.isError = true;
        console.error(e);
      }
    }
  },
  computed: {
    resultMsg() {
      return this.$route.query.keywords
        ? this.$route.query.keywords.join(", ")
        : this.$route.query.q;
    }
  },
  mounted() {
    // if (this.$route.query.q || this.$route.query.keywords) {
    //   this.fetchProblems();
    // }
  }
};
</script>

<style></style>
