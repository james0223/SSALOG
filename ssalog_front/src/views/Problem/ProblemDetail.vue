<template>
  <v-container class="mt-15">
    <v-row justify="center">
      <v-col cols="12">
        <v-card class="mb-4 pa-7" tile>
          <img
            v-if="isSolved"
            class="corner"
            src="@/assets/images/corner/corner_success.png"
            title="이 문제를 푸셨습니다!"
            alt="success"
          />
          <v-row>
            <v-col cols="8">
              <v-card-title>
                <h1>{{ problemNumber }} - {{ problemTitle }}</h1>
              </v-card-title>
              <v-card-text>
                <h3><v-icon>mdi-shovel</v-icon>개척자</h3>
                <h2>
                  <v-avatar>
                    <v-img src="https://cdn.vuetifyjs.com/images/john.png"></v-img> </v-avatar
                  >arduinho
                </h2>
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
            <v-col cols="4">
              <v-card flat class="chart-container">
                <v-card-title>
                  <h5>주요 풀이기법</h5>
                </v-card-title>
                <v-card-text>
                  <DoughNutChart
                    v-if="doughnutLoaded"
                    v-bind:chart-data="problemData"
                    v-bind:chart-options="chartOptions"
                  />
                </v-card-text>
              </v-card>
            </v-col>
            <v-col cols="3"></v-col>
            <v-col cols="5">
              <v-card flat>
                <v-menu offset-y>
                  <template v-slot:activator="{ on, attrs }">
                    <v-card-title>
                      <h5>언어별 평균스펙</h5>
                      <v-spacer></v-spacer>
                      <v-btn icon v-bind="attrs" v-on="on">
                        <v-icon>mdi-chevron-down</v-icon>
                      </v-btn>
                    </v-card-title>
                  </template>
                  <v-list>
                    <v-list-item
                      v-for="(language, i) in languages"
                      :key="i"
                      @click="fetchAvgData(language)"
                    >
                      <v-list-item-title>{{ language.lang }}</v-list-item-title>
                    </v-list-item>
                  </v-list>
                </v-menu>
                <v-card-title>
                  <v-icon>{{ selectedIco }}</v-icon>
                  {{ selectedLang }}
                </v-card-title>
                <v-card-title>
                  <v-icon>mdi-alarm</v-icon>
                  {{ userAvgData.time }} ms
                </v-card-title>
                <v-card-title>
                  <v-icon>mdi-memory</v-icon>
                  {{ userAvgData.memory }} kb
                </v-card-title>

                <!-- <v-list-item-group v-model="selectedList" color="primary">
                  <v-list-item v-for="(item, i) in userSolvingData" :key="i">
                    <v-list-item-icon>
                      <span v-if="item.avg">평균 시간</span>
                    </v-list-item-icon>
                    <v-list-item-content>
                      <v-list-item-title v-text="item.time"></v-list-item-title>
                    </v-list-item-content>
                    <v-list-item-icon>
                      <span v-if="item.avg">평균 메모리</span>
                    </v-list-item-icon>
                    <v-list-item-content>
                      <v-list-item-title v-text="item.memory" class="ml-4"></v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </v-list-item-group>-->
              </v-card>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
    <v-row>
      <v-col cols="12">
        <v-card tile>
          <v-subheader>
            <h4>풀이 목록</h4>
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
      // 유저가 문제를 풀었는지 아닌지 파악
      isSolved: true,
      problemTitle: null,
      problemNumber: this.$route.params.id,
      selectedList: 1,
      selectedLang: "PyPy3",
      selectedIco: "mdi-language-python",
      userAvgData: {
        time: null,
        memory: null
      },
      // doughnutchart 관련 옵션
      doughnutLoaded: false,
      problemData: {
        // 해당 내용은 보기용
        // https://vue-chartjs.org/guide/#chart-with-api-data 나중에 이런 식으로 변경해야 함.
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
          text: "사용자",
          align: "start",
          sortable: false,
          filterable: false,
          // 아마 이메일 튀어나올듯
          value: "username"
        },
        { text: "추천", value: "like" },
        { text: "시간", value: "time" },
        { text: "메모리", value: "memory" },
        { text: "언어", value: "language" }
      ],
      solvedLists: []
    };
  },
  methods: {
    async fetchAvgData(lang) {
      try {
        const { data } = await this.$http.get(
          `${this.$store.state.ServerURL}/newuser/search/detail_avg`,
          {
            params: {
              language: lang.lang,
              problemid: this.problemNumber
            }
          }
        );
        this.userAvgData.time = data.avg_time;
        this.userAvgData.memory = data.avg_memory;
        this.selectedLang = lang.lang;
        this.selectedIco = lang.ico;
      } catch (e) {
        console.error(e);
      }
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
              size: 10
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
    this.fetchSolvingData();
    this.fetchDoughnutData();
    this.fetchAvgData({ lang: "PyPy3" });
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
