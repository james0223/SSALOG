<template>
  <v-card flat height="60vh" class="pa-6 mt-8">
    <h2 class="font-weight-light mb-1">{{ this.$store.state.username }}의 쌀밭</h2>
    <v-divider></v-divider>
    <calendar-heatmap
      class="mt-3"
      v-bind:values="HeatmapData.dates"
      v-bind:range-color="HeatmapData.rangeColor"
      v-bind:tooltip="false"
      v-bind:end-date="HeatmapData.endDate"
    ></calendar-heatmap>
    <v-row>
      <v-col cols="5"> <DoughNutChart v-bind:chart-data="chartData" /> </v-col>
      <v-col cols="7">
        <h4 class="font-weight-light mb-1">내가 쓴 글</h4>
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
      HeatmapData: {
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
  mounted() {
    this.getGrassData();
  },
  computed: mapState(["ServerURL"]),
  methods: {
    async getGrassData() {
      const res = await this.$http.get(`${this.ServerURL}/newuser/search/get_jandi`, {
        params: {
          username: this.$store.state.username
        }
      });
      let date = new Date();
      date = this.getFormatDate(date);
      this.endDate = date;
      this.HeatmapData.dates = res.data;
    },
    getFormatDate(date) {
      const year = date.getFullYear();
      let month = 1 + date.getMonth();
      month = month >= 10 ? month : `0${month}`;
      let day = date.getDate();
      day = day >= 10 ? day : `0${day}`;
      return `${year}-${month}-${day}`;
    }
  }
};
</script>

<style scoped></style>
