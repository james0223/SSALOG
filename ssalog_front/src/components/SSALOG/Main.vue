<template>
  <v-card flat height="60vh" class="pa-3">
    <h2 class="font-weight-light mb-1">{{ userData.id }}의 쌀밭</h2>
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

export default {
  components: {
    CalendarHeatmap,
    DoughNutChart
  },
  data() {
    return {
      // 왼쪽 thumbnail 관련
      thumbnailDialog: false,
      ThumbnailSelect: 0,
      items: [
        { text: "사진 업로드", icon: "mdi-camera-enhance" },
        { text: "기본이미지로 변경", icon: "mdi-camera-off" }
      ],
      userData: {
        id: this.$route.query.id,
        nickname: null // 지금 없지만 받아온다.
      },
      HeatmapData: {
        dates: [
          { date: "2020-3-6", count: 6 },
          { date: "2020-3-4", count: 6 }
        ],
        rangeColor: ["ebedf0", "dae2ef", "#c0ddf9", "#73b3f3", "#3886e1", "#17459e"],
        endDate: "2020-07-25"
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
  }
};
</script>

<style scoped></style>
