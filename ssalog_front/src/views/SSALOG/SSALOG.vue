<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col lg="3" cols="12">
        <UserNav></UserNav>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col lg="6" cols="12">
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
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import UserNav from "@/components/UserNav.vue";
import DoughNutChart from "@/components/DoughnutChart.vue";
import { CalendarHeatmap } from "vue-calendar-heatmap";

export default {
  components: {
    UserNav,
    CalendarHeatmap,
    DoughNutChart
  },
  data() {
    return {
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
  },
  created() {
    if (!this.userData.id) {
      if (!this.$store.state.accessToken) {
        // 로그인 안했는데 내 살로그 가기 누른 경우
        this.$router.push({ name: "Login" });
      } else {
        // 자기 계정 로딩
      }
    }
  }
};
</script>
<style></style>
