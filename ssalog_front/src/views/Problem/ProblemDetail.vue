<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col lg="4" cols="12">
        <v-card flat height="80vh">
          <v-row justify="center" class="mt-8">
            <v-card class="mx-auto mb-4">
              <v-card-title>
                <span class="title mr-4">{{ problemTitle }}</span>
                <span class="subtitle-1 font-weight-light">{{ problemNumber }}</span>
              </v-card-title>
              <v-list-item>
                <v-icon class="mr-3">mdi-alarm</v-icon>
                <span class="body-1 mr-5">제한 시간</span>
                <span class="body-1">{{ timeLimit }}초</span>
              </v-list-item>
              <v-list-item>
                <v-icon class="mr-3">mdi-memory</v-icon>
                <span class="body-1 mr-5">메모리 제한</span>
                <span class="body-1">{{ memoryLimit }} MB</span>
              </v-list-item>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn text v-if="userSolved">문제 풀러 가기</v-btn>
              </v-card-actions>
            </v-card>
          </v-row>
          <v-row justify="center">
            <v-card flat width="20vw" height="40vh">
              <v-container class="chart-container">
                <DoughNutChart
                  v-if="doughnutLoaded"
                  v-bind:chart-data="problemData"
                  v-bind:chart-options="chartOptions"
                />
              </v-container>
            </v-card>
          </v-row>
        </v-card>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col lg="5" cols="12">
        <v-card flat height="80vh">
          <v-card class="mx-5 mt-5">
            <v-list disabled>
              <v-subheader>나의 풀이</v-subheader>
              <v-list-item-group v-model="selectedList" color="primary">
                <v-list-item v-for="(item, i) in userSolvingData" :key="i">
                  <v-list-item-icon>
                    <v-icon v-text="item.icon" class="mr-3">mdi-alarm</v-icon>
                    <span v-if="item.avg">평균 시간</span>
                    <span v-else>나의 시간</span>
                  </v-list-item-icon>
                  <v-list-item-content>
                    <v-list-item-title v-text="item.time"></v-list-item-title>
                  </v-list-item-content>
                  <v-list-item-icon>
                    <v-icon v-text="item.icon" class="mr-3">mdi-alarm</v-icon>
                    <span v-if="item.avg">평균 메모리</span>
                    <span v-else>나의 메모리</span>
                  </v-list-item-icon>
                  <v-list-item-content>
                    <v-list-item-title v-text="item.memory" class="ml-4"></v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
          </v-card>

          <v-card class="mx-5 mt-5">
            <v-data-table
              :lodaing="loading"
              :headers="headers"
              :items="solvedLists"
              :search="search"
            >
              <template v-slot:item.exectime="{ item }">
                <v-chip :color="getColor(item.exectime)">{{ item.exectime }}</v-chip>
              </template>
              <template v-slot:item.memory="{ item }">
                <v-chip :color="getColor(item.memory)">{{ item.memory }}</v-chip>
              </template>
            </v-data-table>
          </v-card>
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
      // 유저가 문제를 풀었는지 아닌지 파악
      userSolved: false,
      // 왼쪽 오른쪽 카드(문제) 관련 옵션
      problemTitle: "역학조사",
      problemNumber: "19541",
      timeLimit: 3,
      memoryLimit: 1024,
      // 오른쪽 위의 카드 (유저문제) 관련 옵션
      selectedList: 1,
      userSolvingData: [
        { avg: true, time: "20", memory: 1024 }, // 각 solve 의 평균값
        { time: "20", memory: 2014 } // 특정 유저의 값
      ],
      // doughnutchart 관련 옵션
      doughnutLoaded: false,
      problemData: {
        // 해당 내용은 보기용
        // https://vue-chartjs.org/guide/#chart-with-api-data 나중에 이런 식으로 변경해야 함.
        labels: [],
        datasets: [
          {
            label: "문제 키워드",
            backgroundColor: "#f87979",
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
      loading: true,
      search: "",
      headers: [
        {
          text: "사용자",
          align: "start",
          sortable: false,
          filterable: false,
          value: "name"
        },
        { text: "추천", value: "star" },
        { text: "시간", value: "exectime" },
        { text: "메모리", value: "memory" },
        { text: "언어", value: "lang" }
      ],
      solvedLists: [
        {
          name: "Gangbada",
          star: 3,
          exectime: 159,
          memory: 6.0,
          lang: "C++"
        },
        {
          name: "Ice cream sandwich",
          star: 2,
          exectime: 237,
          memory: 9.0,
          lang: "C++"
        },
        {
          name: "Eclair",
          star: 3,
          exectime: 262,
          memory: 16.0,
          lang: "C++"
        },
        {
          name: "Cupcake",
          star: 3,
          exectime: 305,
          memory: 3.7,
          lang: "Java"
        },
        {
          name: "Gingerbread",
          star: 3,
          exectime: 356,
          memory: 16.0,
          lang: "Java"
        },
        {
          name: "Jelly bean",
          star: 3,
          exectime: 375,
          memory: 0.0,
          lang: "Java"
        },
        {
          name: "Lollipop",
          star: 3,
          exectime: 392,
          memory: 0.2,
          lang: "Python"
        },
        {
          name: "Honeycomb",
          star: 3,
          exectime: 408,
          memory: 3.2,
          lang: "Python"
        },
        {
          name: "Donut",
          star: 3,
          exectime: 452,
          memory: 25.0,
          lang: "Python"
        },
        {
          name: "KitKat",
          star: 3,
          exectime: 518,
          memory: 26.0,
          lang: "Pyhthon"
        }
      ]
    };
  },
  methods: {
    // 데이터 import
    async fetchProblemData() {
      try {
        const { data } = await axios.get(`${this.$store.state.ServerURL}/none`, {
          params: {
            id: this.$route.params.id
          }
        });
        this.problemData = data;
      } catch (e) {
        console.error(e);
      }
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
        this.problemData.datasets.data.push(value);
        console.log(this.problemData.labels);
        console.log(this.problemData.datasets.data);
      }
      console.log(res);
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
    // this.fetchProblemData();
    this.fetchDoughnutData();
  },
  computed: mapState(["ServerURL"])
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
</style>
