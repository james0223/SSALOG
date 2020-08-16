<template>
  <div>
    <h1>문제</h1>
    <SearchBar
      :SelectedCategoryIdx="$route.query.categoryIdx"
      :q="$route.query.q"
      :keywords="$route.query.keywords"
    />
    <v-card color="transparent" class="pa-10" v-if="isNoResult" flat>
      <v-row justify="center">
        <v-col cols="8">
          <v-card-title class="pb-1">
            <h1>저런!</h1>
          </v-card-title>
          <v-card-title>
            <h2>검색결과가 없네요</h2>
          </v-card-title>
          <v-card-text class="px-5">
            <h3>쌀로그의 문제는 여러분의 리뷰가 작성되면 자동적으로 생성됩니다.</h3>
            <h3>
              지금 바로 문제를 풀어서
              <span class="info--text">개척자</span>가 되어주세요!
            </h3>
          </v-card-text>
          <v-card-text class="text--secondary ml-1"
            >개척자란?
            <br />
            문제에 처음으로 리뷰를 등록하는 유저에게 부여되는 칭호입니다.</v-card-text
          >
        </v-col>
        <v-col cols="4">
          <v-card-text>
            <v-img
              class="mx-auto"
              src="@/assets/images/sorry.png"
              height="20vh"
              width="20vh"
              alt="Freepik from flaticon.com"
            ></v-img>
          </v-card-text>
          <v-card-actions class="justify-center">
            <a
              target="_blank"
              v-bind:href="
                `https://www.acmicpc.net/search#q=${
                  $route.query.categoryIdx === 2 ? '' : $route.query.q
                }`
              "
              style="text-decoration: none; "
            >
              <v-btn color="info" tile>BOJ에서 검색하기</v-btn>
            </a>
          </v-card-actions>
        </v-col>
      </v-row>
    </v-card>
    <h2>{{ entryMsg }}</h2>
    <v-row>
      <v-col v-for="(problem, i) in problems" :key="i" cols="12">
        <v-hover style="cursor:pointer" v-slot:default="{ hover }">
          <v-card @click="visitProblemDeatil(problem.problemid)" :elevation="hover ? 12 : 2">
            <v-card-title>{{ problem.problemid }} - {{ problem.name }}</v-card-title>
            <v-card-text>등록된 리뷰 : {{ problem.all_cnt }}개</v-card-text>
            <div v-if="JSON.stringify(problem.key) !== '{}'">
              <v-divider></v-divider>
              <v-card-text>
                <v-chip-group disabled>
                  <v-chip v-for="(keyword, i) in Object.keys(problem.key)" :key="i">
                    {{ keyword }}
                  </v-chip>
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
  </div>
</template>

<script>
import axios from "axios";
// 요청에 배열 담을 때 필요
import SearchBar from "@/components/SearchBar.vue";
import qs from "qs";
import InfiniteLoading from "vue-infinite-loading";

export default {
  name: "ProblemList",
  components: {
    InfiniteLoading,
    SearchBar
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
        const res = await axios.get(
          `${this.$store.state.ServerURL}/newuser/search/${this.searchMethods[this.categoryIdx]}`,
          {
            params: {
              param: this.$route.query.keywords,
              ...this.searchData
            },
            paramsSerializer: params => {
              return qs.stringify(params, { arrayFormat: "brackets" });
              // repeat : param param, brackets : param[], default : param[1] 이렇게 해라...
            }
          }
        );
        if (!res.data) {
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
    entryMsg() {
      if (this.isNoResult) {
        return "";
      }
      if (this.$route.query.categoryIdx === 2) {
        return `${this.$route.query.keywords.join(", ")} 의 검색결과입니다.`;
      }
      if (this.$route.query.q) {
        return `${this.$route.query.q} 의 검색결과입니다.`;
      }
      return "";
    }
  },
  beforeCreate() {
    if (this.$route.query.categoryIdx === 0 && !this.$route.query.q) {
      this.$route.query.categoryIdx = 1;
    }
  }
};
</script>

<style></style>
