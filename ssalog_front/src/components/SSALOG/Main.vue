<template>
  <v-card color="transparent" flat class="pa-1 mt-6">
    <h3 class="font-weight-light mb-1">1일1쌀밭</h3>
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
      <v-col cols="5">
        <h4 class="font-weight-light mb-1">
          키워드 빈도 그래프
          <v-tooltip right>
            <template v-slot:activator="{ on, attrs }"
              ><v-icon v-bind="attrs" v-on="on">mdi-help-circle-outline</v-icon>
            </template>
            <span>{{ ownerName }}님이 자주 사용한 알고리즘이에요!</span>
          </v-tooltip>
        </h4>
        <v-divider></v-divider>
        <DoughNutChart :width="450" :height="300" v-bind:chart-data="chartData" />
      </v-col>
      <v-col cols="7" class="font-weight-light">
        <v-row no-gutters>
          <v-col cols="6">
            <h4 class="font-weight-light mb-1">
              <span @click="$router.push({ name: 'SolutionList' })" class="to_pointer info--text"
                >작성한 글</span
              >
            </h4>
          </v-col>
          <v-col cols="3" class="d-flex justify-center"
            ><h4 class="font-weight-light mb-1">키워드</h4></v-col
          >
          <v-col cols="3" class="text-end"><h4 class="font-weight-light mb-1">작성일자</h4></v-col>
        </v-row>
        <v-divider></v-divider>
        <v-row v-if="isNoSolve">
          <v-col>
            <p class="p-2 text-center text--secondary">👨🏻‍🎓알고리즘 박사가 되는 그날까지👨‍🎓</p>
            <p class="p-3 text-center text--secondary">📙쌀로그에서 알고리즘을 정리하기🧾</p>
          </v-col>
        </v-row>
        <v-list color="transparent">
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
              <v-row no-gutters class="text-caption">
                <v-col cols="6">
                  <span> {{ solve.problemid }} - {{ solve.title }}</span>
                </v-col>
                <v-col cols="3" class=" text-center">
                  <span> {{ solve.keyword2 }}</span>
                </v-col>
                <v-col cols="3" class="text-end">
                  <span> {{ solve.regdate }}</span>
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
        rangeColor: ["#f4fffa", "#c1fff8", "#52a0da", "#5489d5", "#453ffe"]
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
            username: this.ownerEmail,
            count: 5
          }
        });

        if (Object.keys(res.data).length !== 0) {
          this.chartData = {
            labels: Object.keys(res.data),
            datasets: [
              {
                backgroundColor: ["#8dffb3", "#8087b5", "#7209b7", "#3a0ca3", "#4361ee"],
                data: Object.values(res.data)
              }
            ]
          };
        } else {
          this.chartData = {
            labels: ["자신있는", "알고리즘", "유형파악"],
            datasets: [
              {
                backgroundColor: ["#8dffb3", "#8087b5", "#7209b7", "#3a0ca3", "#4361ee"],
                data: [4, 2, 1]
              }
            ]
          };
        }
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
            const lengths = 17; // 표시할 글자수 기준
            const temp = element;
            if (temp.title.length > lengths) {
              temp.title = temp.title.substr(0, lengths - 2).concat("...");
            }

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
