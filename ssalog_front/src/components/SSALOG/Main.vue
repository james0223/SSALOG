<template>
  <v-card flat height="60vh" class="pa-6 mt-8">
    <h3 class="font-weight-light mb-1">쌀밭</h3>
    <v-divider></v-divider>
    <calendar-heatmap
      tooltip-unit="개 풀었습니다"
      class="mt-3"
      v-bind:values="heatmapData.dates"
      v-bind:range-color="heatmapData.rangeColor"
      v-bind:tooltip="true"
      v-bind:end-date="new Date()"
    ></calendar-heatmap>
    <v-row>
      <v-col cols="5"> <DoughNutChart v-bind:chart-data="chartData" /> </v-col>
      <v-col cols="7">
        <v-row no-gutters>
          <v-col cols="6"> <h4 class="font-weight-light mb-1">작성한 글</h4> </v-col>
          <v-col cols="3" class="d-flex justify-center"
            ><h4 class="font-weight-light mb-1">키워드</h4></v-col
          >
          <v-col cols="3" class="d-flex justify-center"
            ><h4 class="font-weight-light mb-1">작성날짜</h4></v-col
          >
        </v-row>
        <v-divider></v-divider>
        <v-list>
          <v-list-item-group>
            <v-list-item
              class="px-1"
              v-for="(solve, i) in solvedList"
              :key="i"
              @click="
                $router.push({
                  name: 'LogDetail',
                  params: { username: ownerName, id: solve.scoring }
                })
              "
            >
              <v-row no-gutters class="align-center">
                <v-col cols="6"> {{ solve.problemid }} - {{ solve.problemname }} </v-col>
                <v-col cols="3" class="d-flex justify-center">
                  {{ solve.keyword2 }}
                </v-col>
                <v-col cols="3" class="d-flex justify-center">
                  {{ solve.regdate }}
                </v-col>
              </v-row>
            </v-list-item>
          </v-list-item-group>
        </v-list>
        <v-divider></v-divider>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import DoughNutChart from "@/components/DoughnutChart.vue";
import { CalendarHeatmap } from "vue-calendar-heatmap";
import { mapState } from "vuex";

export default {
  components: {
    CalendarHeatmap,
    DoughNutChart
  },
  data() {
    return {
      ownerName: this.$route.params.nickname,
      ownerEmail: "",
      solvedList: [],
      isNoSolve: false,
      heatmapData: {
        dates: [
          { date: "2020-3-6", count: 6 },
          { date: "2020-3-4", count: 6 }
        ],
        rangeColor: ["#FFFDE7", "#FFF9C4", "#FFF59D", "#FFF176", "#FFEE58"]
      },
      chartData: {}
    };
  },
  computed: mapState(["ServerURL"]),
  methods: {
    async drawDoughnut() {
      try {
        const res = await this.$http.get(`${this.ServerURL}/newuser/search/find_pyto`, {
          params: {
            username: this.ownerEmail
          }
        });

        this.chartData = {
          labels: Object.keys(res.data),
          datasets: [
            {
              backgroundColor: ["#377DF0", "#30C7BE", "#40AD58", "#92C43F", "#BDAA3D"],
              data: Object.values(res.data)
            }
          ]
        };
      } catch (e) {
        console.error(e);
      }
    },
    async getownerEmail() {
      try {
        const res = await this.$http.get(`${this.ServerURL}/newuser/search/find_username`, {
          params: {
            nickname: this.ownerName
          }
        });
        this.ownerEmail = res.data;
      } catch (e) {
        console.error(e);
      }
    },
    async getSolvedList() {
      try {
        const { data } = await this.$http.get(`${this.ServerURL}/newuser/search/to_username`, {
          params: {
            direction: "DESC",
            page: 1,
            size: 5,
            username: this.ownerEmail
          }
        });
        if (data.totalElements === 0) {
          this.isNoSolve = true;
        } else {
          this.solvedList = data.content;
          const iterable = this.solvedList;
          iterable.forEach(element => {
            const temp = element;
            if (temp.keyword.length !== 0) {
              const [foo] = temp.keyword;
              const size = temp.keyword.length - 1;
              if (size !== 0) {
                temp.keyword2 = foo.concat(" 외 ", size, "개");
              } else {
                temp.keyword2 = foo;
              }
            }
          });
        }
      } catch (e) {
        console.error(e);
      }
    },
    // 태인아 이거 try 넣어
    async getGrassData() {
      const res = await this.$http.get(`${this.ServerURL}/newuser/search/get_jandi`, {
        params: {
          username: this.ownerEmail
        }
      });
      this.heatmapData.dates = res.data;
    },
    getFormatDate(date) {
      const year = date.getFullYear();
      let month = 1 + date.getMonth();
      month = month >= 10 ? month : `0${month}`;
      let day = date.getDate();
      day = day >= 10 ? day : `0${day}`;
      return `${year}-${month}-${day}`;
    }
  },
  async Create() {
    await this.getownerEmail();
  },
  async mounted() {
    await this.getownerEmail();
    this.getSolvedList();
    this.getGrassData();
    this.drawDoughnut();
  }
};
</script>

<style scoped></style>
