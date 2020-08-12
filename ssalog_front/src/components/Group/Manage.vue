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
              <v-row>
                <v-col cols="6">
                  <ValidationObserver ref="hw">
                    <v-card flat class="mx-3" width="19vw" height="46vh">
                      <form>
                        <ValidationProvider name="문제 제목" rules="required|max:20">
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
                        <ValidationProvider name="문제 번호" rules="required|between:1,50000">
                          <v-text-field
                            slot-scope="{ errors, valid }"
                            v-model="HW.number"
                            :error-messages="errors"
                            :success="valid"
                            :counter="20"
                            label="문제 번호"
                            required
                          ></v-text-field>
                        </ValidationProvider>
                        <v-datetime-picker
                          required
                          ok-text="시간선택"
                          clear-text="초기화"
                          label="마감 시간"
                          v-model="HW.deadline"
                        >
                        </v-datetime-picker>
                        <v-btn type="reset">초기화</v-btn>
                        <v-btn type="submit" @click.prevent="makeHW">과제 출제</v-btn>
                      </form>
                    </v-card>
                  </ValidationObserver>
                </v-col>
                <v-col cols="6">
                  <v-card flat height="44vh" class="px-3" width="18vw" style="overflow-y: scroll;">
                    <v-timeline dense>
                      <v-timeline-item v-for="n in 5" :key="n" small>
                        <v-card class="elevation-2">
                          <v-card-title class="headline">Lorem ipsum</v-card-title>
                          <v-card-text>
                            Lorem ipsum
                          </v-card-text>
                        </v-card>
                      </v-timeline-item>
                    </v-timeline>
                  </v-card>
                </v-col>
              </v-row>
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
import { mapState } from "vuex";

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
        number: "",
        deadline: new Date()
      },
      HWList: [],
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
  },
  methods: {
    async makeHW() {
      try {
        const res = await this.$http.post(`${this.ServerURL}/user/grouping/make_goal`, null, {
          params: {
            groupname: this.$route.params.groupname,
            limitday: this.HW.deadline,
            problemid: this.HW.number,
            problemname: this.HW.name
          }
        });
        console.log(res);
      } catch (err) {
        console.dir(err);
        // 문제 발생시 초기화
        this.HW.name = "";
        this.HW.deadline = new Date();
        this.HW.number = "";
      }
    }
  },
  computed: mapState(["ServerURL"])
};
</script>

<style scoped></style>
