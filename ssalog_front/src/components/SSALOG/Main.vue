<template>
  <v-card flat height="60vh" class="pa-6 mt-8">
    <h2 class="font-weight-light mb-1">{{ ownerName }}의 쌀밭</h2>
    <v-divider></v-divider>
    <calendar-heatmap
      class="mt-3"
      v-bind:values="heatmapData.dates"
      v-bind:range-color="heatmapData.rangeColor"
      v-bind:tooltip="true"
      v-bind:end-date="heatmapData.endDate"
    ></calendar-heatmap>
    <v-row>
      <v-col cols="5"> <DoughNutChart v-bind:chart-data="chartData" /> </v-col>
      <v-col cols="7">
        <v-row no-gutters>
          <v-col cols="6"> <h4 class="font-weight-light mb-1">작성한 글</h4> </v-col>
          <v-col cols="3" class="d-flex justify-center"
            ><h4 class="font-weight-light mb-1">추천수</h4></v-col
          >
          <v-col cols="3" class="d-flex justify-center"
            ><h4 class="font-weight-light mb-1">댓글</h4></v-col
          >
        </v-row>
        <v-divider></v-divider>
        <v-list>
          <v-list-item-group>
            <v-list-item
              class="px-1"
              v-for="(solve, i) in solvedList"
              :key="i"
              @click="$router.push({ name: 'LogDetail', params: { id: solve.scoring } })"
            >
              <v-row no-gutters class="align-center">
                <v-col cols="6"> {{ solve.problemid }} - {{ solve.problemname }} </v-col>
                <v-col cols="3" class="d-flex justify-center">
                  {{ solve.like }}
                </v-col>
                <v-col cols="3" class="d-flex justify-center">
                  {{ solve.comment === null ? 0 : solve.comment.length }}
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
      ownerName: this.$route.params.username,
      solvedList: [],
      isNoSolve: false,
      heatmapData: {
        dates: [
          { date: "2020-3-6", count: 6 },
          { date: "2020-3-4", count: 6 }
        ],
        rangeColor: ["#FFFDE7", "#FFF9C4", "#FFF59D", "#FFF176", "#FFEE58"],
        endDate: "2020-08-04"
      },
      chartData: {
        // 해당 내용은 보기용
        // https://vue-chartjs.org/guide/#chart-with-api-data 나중에 이런 식으로 변경해야 함.
        labels: ["element1", "ele2", "ele3"],
        datasets: [
          {
            label: "Data one",
            backgroundColor: "#f87979",
            data: [1, 2, 3]
          }
        ]
      }
    };
  },
  computed: mapState(["ServerURL"]),
  methods: {
    async getSolvedList() {
      try {
        const { data } = await this.$http.get(`${this.ServerURL}/newuser/search/to_username`, {
          params: {
            direction: "DESC",
            page: 1,
            size: 5,
            username: this.ownerName
          }
        });
        if (data.totalElements === 0) {
          this.isNoSolve = true;
        } else {
          this.solvedList = data.content;
        }
      } catch (e) {
        console.error(e);
      }
    },
    // 태인아 이거 try 넣어
    async getGrassData() {
      const res = await this.$http.get(`${this.ServerURL}/newuser/search/get_jandi`, {
        params: {
          username: this.$store.state.username
        }
      });
      let date = new Date();
      date = this.getFormatDate(date);
      this.endDate = date;
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
  mounted() {
    this.getGrassData();
    this.getSolvedList();
  }
};
</script>

<style scoped></style>
