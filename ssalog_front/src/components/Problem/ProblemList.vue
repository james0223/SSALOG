<template>
  <div>
    <h1>문제</h1>
    <SearchBar
      :SelectedCategoryIdx="Number($route.query.categoryIdx)"
      :Q="$route.query.q"
      :Keywords="$route.query.keywords"
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
          <v-card-text class="text--secondary ml-1">
            개척자란?
            <br />문제에 처음으로 리뷰를 등록하는 유저에게 부여되는 칭호입니다.
          </v-card-text>
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
      <v-col v-if="!username && showCard && !isNoResult" cols="12">
        <v-hover v-slot:default="{ hover }">
          <v-card min-height="11rem" class="toNewbee" dark elevation="8">
            <v-row align="center">
              <v-col cols="7"
                ><v-card-title>
                  <h3 class="ml-3 mt-5">
                    쌀로그와 함께<br />
                    문제를 풀어주세요!😃
                  </h3></v-card-title
                ></v-col
              >
              <v-col cols="5"
                ><v-card-title>
                  <h1 class="mt-9"><v-icon x-large>mdi-login-variant</v-icon>Go SSaLog</h1>
                </v-card-title></v-col
              >
            </v-row>

            <v-card
              v-if="hover"
              class="d-flex flex-column black v-card--reveal white--text pa-4"
              elevation="12"
            >
              <v-card-actions
                ><v-btn color="black" large rounded @click="$router.push({ name: 'Login' })"
                  ><v-icon>mdi-login</v-icon>로그인</v-btn
                ></v-card-actions
              >
              <v-card-actions>
                <v-btn color="black" large rounded @click="$router.push({ name: 'SignUp' })">
                  <v-icon>mdi-checkbox-marked-circle-outline</v-icon>회원가입</v-btn
                ></v-card-actions
              >
            </v-card>
          </v-card>
        </v-hover>
      </v-col>
      <v-col v-for="(problem, i) in problems" :key="i" cols="12">
        <v-hover style="cursor:pointer" v-slot:default="{ hover }">
          <v-card @click="visitProblemDeatil(problem.problemid)" :elevation="hover ? 12 : 2">
            <img
              v-if="problem.starter === username"
              class="corner"
              src="@/assets/images/corner/corner_pioneer.png"
              title="이 문제의 개척자이십니다!"
              alt="you are pioneer"
            />
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
      showCard: false,
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
              param:
                typeof this.$route.query.keywords === "string"
                  ? [this.$route.query.keywords]
                  : this.$route.query.keywords,
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
      this.showCard = true;
    }
  },
  computed: {
    username() {
      return this.$store.state.username;
    },
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

<style scoped>
.corner {
  position: absolute;
  top: 0px;
  right: 0px;
  height: 4rem;
}
.toNewbee {
  background: rgb(122, 6, 118);
  background: linear-gradient(145deg, rgba(122, 6, 118, 1) 16%, rgba(0, 220, 255, 1) 92%);
}
.v-card--reveal {
  bottom: 0;
  opacity: 0.85;
  justify-content: center;
  position: absolute;
  width: 100%;
  height: 100%;
}
</style>
