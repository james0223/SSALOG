<template>
  <v-container class="px-7">
    <v-row>
      <v-col cols="8">
        <v-row>
          <v-col cols="6">
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
          <v-col cols="6">
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
              <ValidationObserver ref="hw">
                <v-card class="mx-3" width="19vw" height="46vh">
                  <form>
                    <ValidationProvider name="Name" rules="required|max:20">
                      <v-text-field
                        slot-scope="{ errors, valid }"
                        v-model="HW.name"
                        :error-messages="errors"
                        :success="valid"
                        :counter="20"
                        label="문제 제목"
                        required
                      ></v-text-field>
                    </ValidationProvider>
                    <v-text-field v-model="HW.number" label="문제 번호" required></v-text-field>
                  </form>
                </v-card>
              </ValidationObserver>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="4">
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
      // 문제 제출
      HW: {
        name: "",
        number: ""
      },
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
