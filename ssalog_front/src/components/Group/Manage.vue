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
                  <ValidationObserver ref="form">
                    <v-card flat class="mx-3" width="19vw" height="46vh">
                      <form @submit.prevent="makeHW">
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
                        <v-spacer></v-spacer>
                        <v-btn type="reset">초기화</v-btn>
                        <v-btn type="submit">과제 출제</v-btn>
                      </form>
                    </v-card>
                  </ValidationObserver>
                </v-col>
                <v-col cols="6">
                  <v-card flat height="44vh" class="px-3" width="18vw" style="overflow-y: scroll;">
                    <v-timeline dense>
                      <v-timeline-item v-for="task in HWList" :key="task.name" small>
                        <v-card class="elevation-2">
                          <v-card-title class="headline">{{ task.name }}</v-card-title>
                          <v-card-text>
                            {{ task.number }}
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
          <v-card flat>
            <v-toolbar-title>가입 신청 현황</v-toolbar-title>
            <v-virtual-scroll :items="applicants" :item-height="35" height="300">
              <template v-slot="{ item }">
                <v-expansion-panels>
                  <v-expansion-panel>
                    <v-expansion-panel-header>
                      <v-list-item>
                        <v-list-item-avatar size="32">
                          <v-img :src="ImgURL + item.img"> </v-img>
                        </v-list-item-avatar>
                        <v-list-item-content>
                          <v-list-item-title>{{ item.nickname }}</v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>
                    </v-expansion-panel-header>
                    <v-expansion-panel-content>
                      <v-app-bar flat dense color="white">
                        {{ item.introduce }}
                        <v-spacer></v-spacer>
                        <v-btn
                          color="success"
                          class="mr-3"
                          tile
                          small
                          icon
                          @click.prevent="acceptApplicant(item)"
                        >
                          <v-icon>mdi-check-bold</v-icon>
                        </v-btn>
                        <v-btn
                          color="error"
                          tile
                          small
                          icon
                          @click.prevent="declineApplicant(item)"
                        >
                          <v-icon>mdi-close-circle</v-icon>
                        </v-btn>
                      </v-app-bar>
                    </v-expansion-panel-content>
                  </v-expansion-panel>
                </v-expansion-panels>
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
import _ from "lodash";

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
      HWList: [
        {
          name: "골목한조",
          number: 2142,
          deadline: "2020-01-01"
        }
      ],
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
    // 그룹 지원자 확인
    async getApplicantList() {
      try {
        const res = await this.$http.get(`${this.ServerURL}/user/grouping/apply_list`, {
          params: {
            groupname: this.$route.params.groupname
          }
        });
        this.applicants = res.data;
      } catch (e) {
        console.log("그룹지원자 확인 문제생김");
      }
    },
    async acceptApplicant(item) {
      try {
        await this.$http.post(`${this.ServerURL}/user/grouping/apply_accept`, null, {
          params: {
            groupname: this.$route.params.groupname,
            regid: item.regid
          }
        });
        // 가입에 성공했다면 local 의 대기열에서도 pop
        this.applicants.splice(this.applicants.indexOf(item), 1);
      } catch (e) {
        console.log(e);
      }
    },
    async declineApplicant(item) {
      try {
        await this.$http.delete(`${this.ServerURL}/user/grouping/apply_reject`, {
          params: {
            groupname: this.$route.params.groupname,
            regid: item.regid
          }
        });
        this.applicants.splice(this.applicants.indexOf(item, 1));
      } catch (e) {
        console.error(e);
      }
    },
    // 과제 제출
    async makeHW() {
      try {
        const res = await this.$refs.form.validate();
        // eslint-disable-next-line
        if (!res) {
          // Wait until the models are updated in the UI
          this.$nextTick(() => {
            this.$refs.form.reset();
          });
          return;
        }
        await this.$http.post(`${this.ServerURL}/user/grouping/make_goal`, null, {
          params: {
            groupname: this.$route.params.groupname,
            limitday: this.HW.deadline,
            problemid: this.HW.number,
            problemname: this.HW.name
          }
        });
        const HCopy = _.cloneDeep(this.HW);
        this.HWList.push(HCopy);
        this.$nextTick(() => {
          this.$refs.form.reset();
        });
      } catch (err) {
        console.dir(err);
      } finally {
        this.HW.name = "";
        this.HW.number = "";
        this.HW.deadline = new Date();
      }
    }
  },
  computed: mapState(["ServerURL", "ImgURL"]),
  mounted() {
    this.getApplicantList();
  }
};
</script>

<style scoped></style>
