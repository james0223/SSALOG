<template>
  <v-container class="mt-15">
    <v-row justify="center">
      <v-col cols="12">
        <v-card class="mb-4 pt-7 px-7" tile>
          <img
            v-if="isSolved"
            class="corner"
            src="@/assets/images/corner/corner_success.png"
            title="이 문제를 푸셨습니다!"
            alt="success"
          />
          <v-card-title class="mb-7">
            <h1>{{ problemNumber }} - {{ problemTitle }}</h1>
          </v-card-title>
          <v-row>
            <v-col cols="4">
              <v-card-text>
                <h3><v-icon>mdi-shovel</v-icon>개척자</h3>
                <h2>
                  <v-avatar>
                    <v-img src="https://cdn.vuetifyjs.com/images/john.png"></v-img>
                  </v-avatar>
                  arduinho
                </h2>
              </v-card-text>
            </v-col>
            <v-col cols="4">
              <v-card-text>
                <h3><v-icon>mdi-clipboard-check-outline</v-icon>등록된 리뷰</h3>
                <h2>{{ numberOfSolutions }} 개</h2>
              </v-card-text>
            </v-col>
            <v-col cols="4" class="d-flex align-end justify-end">
              <v-card-actions>
                <a
                  target="_blank"
                  v-bind:href="`https://www.acmicpc.net/problem/${problemNumber}`"
                  style="text-decoration: none; "
                  title="클릭시 문제로 이동합니다."
                  >BOJ 로 열기</a
                >
                <v-icon>mdi-launch</v-icon>
              </v-card-actions>
            </v-col>
          </v-row>

          <v-divider class="mx-3"></v-divider>

          <v-row justify="center">
            <v-col cols="5">
              <v-card flat>
                <v-card-title>
                  <h5>언어별 평균스펙</h5>
                </v-card-title>
                <v-card-actions>
                  <v-row no-gutters class="align-center">
                    <v-col cols="2" class="d-flex justify-center">
                      <v-icon x-large @click="fetchAvgData(false)">mdi-chevron-left</v-icon></v-col
                    >
                    <v-col cols="8">
                      <v-carousel hide-delimiters :show-arrows="false" v-model="langIdx">
                        <v-carousel-item v-for="(language, i) in languages" :key="i">
                          <v-sheet height="100%" tile light>
                            <v-row class="fill-height" align="center" justify="center">
                              <v-card-title>
                                <v-icon x-large>{{ selectedIco }}</v-icon>
                                <h3 class="my-0">{{ selectedLang }}</h3>
                              </v-card-title>
                              <v-card-title>
                                <v-icon x-large>mdi-alarm</v-icon>
                                <h2 class="my-0">{{ userAvgData.time }} ms</h2>
                              </v-card-title>
                              <v-card-title>
                                <v-icon x-large>mdi-memory</v-icon>
                                <h3 class="my-0">{{ userAvgData.memory }} kb</h3>
                              </v-card-title>
                            </v-row>
                          </v-sheet>
                        </v-carousel-item>
                      </v-carousel>
                    </v-col>
                    <v-col cols="2" class="d-flex justify-center">
                      <v-icon x-large @click="fetchAvgData(true)">mdi-chevron-right</v-icon></v-col
                    >
                  </v-row></v-card-actions
                >
              </v-card>
            </v-col>
            <v-col cols="2"></v-col>
            <v-col cols="4">
              <v-card flat class="chart-container">
                <v-card-title>
                  <h5>주요 풀이기법</h5>
                </v-card-title>
                <v-card-text class="mt-15">
                  <DoughNutChart
                    v-if="doughnutLoaded"
                    v-bind:chart-data="problemData"
                    v-bind:chart-options="chartOptions"
                  />
                </v-card-text>
              </v-card>
            </v-col>
            <v-col cols="1"></v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-card tile>
          <v-subheader>
            <h3>리뷰 목록</h3>
          </v-subheader>
          <v-data-table
            :loading="SolutionLoading"
            loading-text="풀이를 가져오는 중입니다..."
            :headers="headers"
            :page.sync="SolutionPage"
            hide-default-footer
            :items-per-page="SolutionPerPage"
            :items="solvedLists"
          >
            <template v-slot:item.exectime="{ item }">
              <v-chip :color="getColor(item.exectime)">{{ item.exectime }}</v-chip>
            </template>
            <template v-slot:item.memory="{ item }">
              <v-chip :color="getColor(item.memory)">{{ item.memory }}</v-chip>
            </template>
          </v-data-table>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
import DoughNutChart from "../../components/DoughnutChart.vue";

export default {
  name: "ProblemDetail",
  components: {
    DoughNutChart
  },
  data() {
    return {
      languages: [
        { lang: "Python3", ico: "mdi-language-python" },
        { lang: "PyPy3", ico: "mdi-language-python" },
        { lang: "Java", ico: "mdi-language-java" },
        { lang: "C++14", ico: "mdi-language-cpp" },
        { lang: "C++", ico: "mdi-language-cpp" },
        { lang: "C11", ico: "mdi-language-c" },
        { lang: "C", ico: "mdi-language-c" }
      ],
      langIdx: 0,
      isSolved: true, // 유저가 문제를 풀었는지 아닌지 파악
      isPioneer: false, // 개척자라면
      problemTitle: null,
      problemNumber: this.$route.params.id,
      numberOfSolutions: null,
      selectedLang: "PyPy3",
      selectedIco: "mdi-language-python",
      userAvgData: {
        time: null,
        memory: null
      },
      // doughnutchart 관련 옵션
      doughnutLoaded: false,
      problemData: {
        labels: [],
        datasets: [
          {
            label: "문제 키워드",
            backgroundColor: ["#377DF0", "#30C7BE", "#40AD58", "#92C43F", "#BDAA3D"],
            data: []
          }
        ]
      },
      chartOptions: {
        scales: {
          yAxes: [
            {
              ticks: {
                beginAtZero: true
              }
            }
          ]
        },
        responsive: true,
        maintainAspectRatio: false
      },
      // table 관련 data
      timeAvg: "", // 나중에 평균 시간내서 색깔입힐때
      memoryAvg: "", // 마찬가지
      SolutionLoading: true,
      SolutionPage: 1,
      SolutionPageCount: 1,
      SolutionCount: null,
      SolutionPerPage: 10,
      headers: [
        {
          text: "작성자",
          align: "start",
          sortable: false,
          filterable: false,
          value: "nickname"
        },
        { text: "제목", value: "title" },
        { text: "추천", value: "like" },
        { text: "시간", value: "time" },
        { text: "메모리", value: "memory" },
        { text: "언어", value: "language" }
      ],
      solvedLists: []
    };
  },
  methods: {
    async fetchSolutionNumb() {
      try {
        const { data } = await this.$http.get(
          `${this.$store.state.ServerURL}/newuser/search/detail_page_cnt`,
          {
            params: { problemid: this.problemNumber }
          }
        );
        this.numberOfSolutions = data;
      } catch (e) {
        console.error(e);
      }
    },
    async fetchAvgData(flag) {
      if (flag) {
        if (this.langIdx === 6) {
          return;
        }
        this.langIdx += 1;
      } else {
        if (this.langIdx === 0) {
          return;
        }
        this.langIdx -= 1;
      }
      try {
        const { data } = await this.$http.get(
          `${this.$store.state.ServerURL}/newuser/search/detail_avg`,
          {
            params: {
              language: this.languages[this.langIdx].lang,
              problemid: this.problemNumber
            }
          }
        );
        this.userAvgData.time = data.avg_time;
        this.userAvgData.memory = data.avg_memory;
        this.selectedLang = this.languages[this.langIdx].lang;
        this.selectedIco = this.languages[this.langIdx].ico;
      } catch (e) {
        console.error(e);
      }
      this.fetchSolvingData();
    },
    // 풀이 데이터 import
    async fetchSolvingData() {
      try {
        this.SolutionLoading = true;
        const { data } = await axios.get(
          `${this.$store.state.ServerURL}/newuser/search/detail_list`,
          {
            params: {
              direction: "ASC",
              page: this.SolutionPage,
              problemid: this.problemNumber,
              size: 12,
              language: this.languages[this.langIdx].lang
            }
          }
        );
        this.solvedLists = data.content;
        this.SolutionPageCount = Number(data.totalPages);
        this.SolutionLoading = false;
      } catch (e) {
        console.error(e);
      }
    },
    // 문제 이름 받아오기
    async getProblemName() {
      const { data } = await axios.get(
        `${this.$store.state.ServerURL}/newuser/search/find_problemname/`,
        {
          params: {
            problemid: this.problemNumber
          }
        }
      );
      this.problemTitle = data;
    },
    // 도넛 데이터 받아오기
    async fetchDoughnutData() {
      const res = await axios.get(`${this.$store.state.ServerURL}/newuser/search/detail_py`, {
        params: {
          problemid: this.$route.params.id
        }
      });
      // eslint-disable-next-line
      for (const [key, value] of Object.entries(res.data)) {
        this.problemData.labels.push(key);
        this.problemData.datasets[0].data.push(value);
      }
      this.doughnutLoaded = true;
    },
    getColor(exectime) {
      // 여기 avg 인자를 추가로 받아서
      // 각각 평균보다 낮으면 green / +- 20 퍼면 중간 / 더 높으면 red 이런식으로
      if (exectime > 400) return "red";
      if (exectime > 200) return "orange";
      return "green";
    }
  },
  mounted() {
    this.getProblemName();
    this.fetchSolutionNumb();
    this.fetchDoughnutData();
    this.fetchAvgData(true);
  },
  computed: mapState(["ServerURL"]),
  watch: {
    // eslint-disable-next-line
    SolutionPage: function () {
      this.fetchSolvingData();
    }
  }
};
</script>

<style lang="scss">
.chart-container {
  flex-grow: 1;
  min-height: 0;

  > div {
    position: relative;
    height: 100%;
  }
}
.corner {
  position: absolute;
  top: 0px;
  right: 0px;
  height: 7rem;
}
</style>
