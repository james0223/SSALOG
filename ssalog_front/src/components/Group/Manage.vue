<template>
  <v-container class="px-7">
    <v-row>
      <v-col cols="7">
        <v-row>
          <v-col cols="7">
            <v-card height="25vh"
              >과제 제출현황
              <PieChart
                v-if="pieLoaded"
                v-bind:chart-data="examData"
                v-bind:chart-options="chartOptions"
                :width="200"
                :height="100"
              />
            </v-card>
          </v-col>
          <v-col cols="5">
            <v-card height="25vh"
              >과제 제출현황
              <br />
              제출 : {{ examData.datasets[0].data[0] }} 미제출 : {{ examData.datasets[0].data[1] }}
            </v-card>
          </v-col>
        </v-row>
        <v-row>
          <v-col cols="12">
            <v-card height="52.5vh">
              <v-toolbar-title>과제 출제</v-toolbar-title>
              <v-divider></v-divider>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="5">
        <v-card class="mt-3" height="80vh">
          <v-toolbar-title>회원 관리</v-toolbar-title>
          <v-divider></v-divider>
          <v-list class="mb-5">
            <v-list-item v-for="manager in groupMember" :key="manager.nickname">
              <v-list-item-avatar>
                <v-img :src="manager.avatar"></v-img>
              </v-list-item-avatar>
              <v-list-item-content>
                <v-list-item-title v-text="manager.nickname"></v-list-item-title>
              </v-list-item-content>
              <v-list-item-icon>
                <v-icon v-if="manager">mdi-trash</v-icon>
              </v-list-item-icon>
            </v-list-item>
          </v-list>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import PieChart from "@/components/PieChart.vue";

export default {
  name: "Manage",
  components: {
    PieChart
  },
  data() {
    return {
      // 회원 관리
      groupMember: [
        {
          nickname: "바스티온",
          avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg"
        }
      ],
      // pie chart data
      pieLoaded: true,
      examData: {
        labels: ["풀음", "안품"],
        datasets: [
          {
            label: "과제 현황",
            backgroundColor: "#d28b08",
            data: [1, 2]
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
        responsive: false,
        maintainAspectRatio: false
      }
    };
  }
};
</script>

<style scoped></style>
