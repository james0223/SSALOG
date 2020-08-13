<template>
  <v-container>
    <row v-if="isNoResult">
      <h2 class="text-center">검색결과가 없습니다.</h2>
      <h3 class="text-center">쌀로그의 문제는 여러분의 쌀로그가 모여 생성됩니다.</h3>
      <h4 class="text-center">그러니까 문제를 풀어서 개척자가 되어주세요!</h4>
      <a class="text-center" href="https://www.acmicpc.net/">문제풀러가기</a>
    </row>
    <h2 v-if="!isNoResult">{{ resultMsg }}에 대한 검색결과입니다.</h2>
    <v-row>
      <v-col v-for="(problem, i) in problems" :key="i" cols="12">
        <v-hover style="cursor:pointer" v-slot:default="{ hover }">
          <v-card @click="visitProblemDeatil(problem.problemid)" :elevation="hover ? 12 : 2">
            <v-card-title>{{ problem.problemid }} - {{ problem.problemname }}</v-card-title>
            <v-card-text>등록된 리뷰 : n개</v-card-text>
            <div v-if="!!problem.keyword && problem.keyword.length !== 0">
              <v-divider></v-divider>
              <v-card-text>
                <v-chip-group disabled>
                  <v-chip v-for="(keyword, i) in problem.keyword" :key="i">{{ keyword }}</v-chip>
                </v-chip-group>
              </v-card-text>
            </div>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>

    <infinite-loading
      v-if="limit >= searchData.page && !isError && !isNoResult"
      @infinite="infiniteHandler"
      spinner="waveDots"
    ></infinite-loading>
  </v-container>
</template>

<script>
import axios from "axios";
// import qs from "qs";
import InfiniteLoading from "vue-infinite-loading";

export default {
  name: "ProblemList",
  components: {
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
      is_fetching: false,
      isNoResult: false,
      isError: false
    };
  },
  methods: {
    infiniteHandler($state) {
      setTimeout(() => {
        if (!this.is_fetching && !this.isNoResult && !this.isError) {
          this.fetchProblems();
        }
        $state.loaded();
      }, 1000);
    },
    visitProblemDeatil(id) {
      this.$router.push({
        name: "ProblemDetail",
        params: {
          id
        }
      });
    },
    async fetchProblems() {
      this.is_fetching = true;
      try {
        // let keywordString = `/?`;
        // this.$route.query.keywords.forEach(function(keyword) {
        //   keywordString += `keyword=${keyword}&`;
        // });
        const res = await axios.get(
          `${this.$store.state.ServerURL}/newuser/search/${this.searchMethods[this.categoryIdx]}`,
          {
            params: {
              param: this.$route.query.keywords,
              ...this.searchData
            }
            // 배열을 보내기 위한 방법, npm i qs 필요
            // paramsSerializer: params => {
            //   console.log(qs.stringify(params));
            //   return qs.stringify(params);
            // }
          }
        );
        console.log(res);
        if (!res.data) {
          console.log("아무것도 없다.");
          this.isError = true;
          return;
        }
        if (res.data.totalElements === 0) {
          this.isNoResult = true;
        }
        this.searchData.page += 1;
        this.problems = [...this.problems, ...res.data.content];
        this.limit = Number(res.data.totalPages);
      } catch (e) {
        this.isError = true;
        console.error(e);
      }
      this.is_fetching = false;
    }
  },
  computed: {
    resultMsg() {
      return this.$route.query.categoryIdx === 2
        ? this.$route.query.keywords.join(", ")
        : this.$route.query.q;
    }
  }
};
</script>

<style></style>
