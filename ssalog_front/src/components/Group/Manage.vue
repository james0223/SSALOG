<template>
  <v-container class="px-7">
    <v-row>
      <v-col cols="8">
        <v-row>
          <v-col cols="6">
            <v-card height="25vh"
              >과제 제출현황
              <PieChart v-if="pieLoaded" v-bind:chart-data="examData" :width="350" :height="200" />
            </v-card>
          </v-col>
          <v-col cols="6">
            <v-card height="25vh">
              <BarChart v-if="barLoaded" v-bind:chart-data="barData" :width="330" :height="250" />
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
          <v-card class="mb-3">
            <v-toolbar-title>회원 관리</v-toolbar-title>
            <v-virtual-scroll class="mt-5" :items="groupMember" :item-height="20" height="300">
              <template v-slot="{ item }">
                <v-list-item>
                  <v-list-item-avatar size="56">
                    <v-img :src="item.avatar"> </v-img>
                  </v-list-item-avatar>
                  <v-list-item-content>
                    <v-list-item-title>{{ item.name }}</v-list-item-title>
                  </v-list-item-content>
                  <v-spacer></v-spacer>
                  <v-list-item-action>
                    <v-btn color="amber lighten-2" tile large icon>
                      <v-icon>mdi-trophy-award</v-icon>
                    </v-btn>
                  </v-list-item-action>
                  <v-list-item-action>
                    <v-btn color="error" tile large icon>
                      <v-icon>mdi-close-circle</v-icon>
                    </v-btn>
                  </v-list-item-action>
                </v-list-item>
              </template>
            </v-virtual-scroll>
          </v-card>
          <v-card>
            <v-toolbar-title>가입 신청 현황</v-toolbar-title>
            <v-virtual-scroll class="mt-5" :items="applicants" :item-height="20" height="300">
              <template v-slot="{ item }">
                <v-list-item>
                  <v-list-item-avatar size="56">
                    <v-img :src="item.avatar"> </v-img>
                  </v-list-item-avatar>
                  <v-list-item-content>
                    <v-list-item-title>{{ item.name }}</v-list-item-title>
                  </v-list-item-content>
                  <v-spacer></v-spacer>
                  <v-list-item-action>
                    <v-btn color="success" tile large icon>
                      <v-icon>mdi-check-bold</v-icon>
                    </v-btn>
                  </v-list-item-action>
                  <v-list-item-action>
                    <v-btn color="error" tile large icon>
                      <v-icon>mdi-close-circle</v-icon>
                    </v-btn>
                  </v-list-item-action>
                </v-list-item>
              </template>
            </v-virtual-scroll>
          </v-card>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import PieChart from "@/components/PieChart.vue";
import BarChart from "@/components/BarChart.vue";

import { mapState } from "vuex";

export default {
  name: "Manage",
  components: {
    BarChart,
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
          name: "바스티온",
          avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg"
        }
      ],
      // 회원가입 관리
      applicants: [
        {
          avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg",
          name: "오지네"
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
      // bar chart data
      barLoaded: true,
      barData: {
        labels: ["백양로", "유투브", "N과M", "기모띠"],
        datasets: [
          {
            label: "제출자",
            backgroundColor: "green",
            data: [20, 30, 20, 40]
          },
          {
            label: "미제출자",
            backgroundColor: "red",
            data: [20, 10, 40, 230]
          }
        ]
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
