<template>
  <v-container class="px-7">
    <v-row>
      <v-col cols="8">
        <v-row>
          <v-col cols="6">
            <v-card v-if="HWList.length" height="25vh" class="chart-container"
              >과제 제출현황
              <PieChart class="pie" v-if="pieLoaded" v-bind:chart-data="examData" />
            </v-card>
            <v-card v-else height="25vh">
              <v-img :src="require('@/assets/images/html_programming1.png')"></v-img>
            </v-card>
          </v-col>
          <v-col cols="6">
            <v-card v-if="HWList.length" height="25vh" class="chart-container">
              <BarChart class="bar" v-if="barLoaded" v-bind:chart-data="barData" />
            </v-card>
            <v-card v-else height="25vh">
              <v-img :src="require('@/assets/images/html_programming2.png')"></v-img>
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
                    <v-card flat class="ml-10" width="19vw" height="46vh">
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
                        <ValidationProvider name="문제 제목" rules="required|max:20">
                          <v-text-field
                            slot-scope="{ errors, valid }"
                            v-model="HW.mention"
                            :error-messages="errors"
                            :success="valid"
                            :counter="20"
                            label="문제 설명"
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
                        <v-btn class="button_margin" type="reset">초기화</v-btn>
                        <v-btn class="ml-5" type="submit">과제 출제</v-btn>
                      </form>
                    </v-card>
                  </ValidationObserver>
                </v-col>
                <v-col cols="6">
                  <v-card
                    flat
                    height="44vh"
                    class="px-3"
                    min-width="18vw"
                    style="overflow-y: scroll;"
                  >
                    <v-timeline v-if="HWList.length" dense>
                      <v-timeline-item v-for="(task, idx) in HWList" :key="task.id" small>
                        <v-card class="elevation-2">
                          <v-card-title class="headline"
                            >{{ task.problemname }} <v-spacer></v-spacer>
                            <v-btn
                              color="error"
                              icon
                              @click="
                                openDeleteHW = true;
                                taskBeErased = task.id;
                                taskBeErasedOrder = idx;
                              "
                            >
                              <v-icon>mdi-close-circle</v-icon>
                            </v-btn>
                          </v-card-title>
                          <v-card-subtitle>{{ task.mention }}</v-card-subtitle>
                          <v-card-text> 마감일 : {{ task.limit.substr(0, 10) }} </v-card-text>
                        </v-card>
                      </v-timeline-item>
                    </v-timeline>
                    <p class="mt-15 display-2 text--primary" style="line-height: 4rem" v-else>
                      📚과제를 <br />
                      출제해주세요
                    </p>
                  </v-card>
                </v-col>
              </v-row>
            </v-card>
          </v-col>
        </v-row>
      </v-col>
      <v-col cols="4">
        <v-card class="mt-3" height="80vh">
          <v-card flat class="mb-3">
            <v-toolbar-title>회원 관리</v-toolbar-title>
            <v-virtual-scroll :items="groupMember" :item-height="45" height="328">
              <template v-slot="{ item }">
                <v-list-item>
                  <v-list-item-avatar size="42">
                    <v-img :src="ImgURL + item.img"> </v-img>
                  </v-list-item-avatar>
                  <v-list-item-content>
                    <v-list-item-title>{{ item.nickname }}</v-list-item-title>
                  </v-list-item-content>
                  <v-spacer></v-spacer>
                  <v-list-item-action>
                    <v-btn v-if="item.role === 'owner'" color="amber lighten-2" tile large icon>
                      <v-icon>mdi-trophy-award</v-icon>
                    </v-btn>
                    <v-btn v-else color="error" tile large icon @click="kick_member(item.nickname)">
                      <v-icon>mdi-close-circle</v-icon>
                    </v-btn>
                  </v-list-item-action>
                </v-list-item>
              </template>
            </v-virtual-scroll>
          </v-card>
          <v-divider></v-divider>
          <v-card flat>
            <v-toolbar-title>가입 신청 현황</v-toolbar-title>
            <v-virtual-scroll
              v-if="applicants.length"
              :items="applicants"
              :item-height="35"
              height="300"
            >
              <template v-slot="{ item }">
                <v-list-item>
                  <v-list-item-avatar size="32">
                    <v-img :src="ImgURL + item.img"> </v-img>
                  </v-list-item-avatar>
                  <v-list-item-content>
                    <v-list-item-title>{{ item.nickname }}</v-list-item-title>
                  </v-list-item-content>
                  <v-list-item-action>
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
                  </v-list-item-action>
                  <v-list-item-action>
                    <v-btn color="error" tile small icon @click.prevent="declineApplicant(item)">
                      <v-icon>mdi-close-circle</v-icon>
                    </v-btn>
                  </v-list-item-action>
                </v-list-item>
              </template>
            </v-virtual-scroll>
            <h4 class="mt-5 ml-5" v-else>가입신청한 회원이 없습니다😭</h4>
          </v-card>
        </v-card>
      </v-col>
    </v-row>
    <v-dialog v-model="openDeleteHW" persistent width="30vw">
      <v-card>
        <v-card-title class="headline">정말 과제를 삭제하시겠습니까?</v-card-title>
        <v-card-text>{{ taskBeErased }}</v-card-text>
        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn color="green darken-1" text @click="openDeleteHW = false">과제 삭제 취소</v-btn>
          <v-btn
            color="green darken-1"
            text
            @click="
              openDeleteHW = false;
              deleteHW(taskBeErased);
            "
            >과제 삭제</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>
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
        mention: "",
        deadline: new Date()
      },
      openDeleteHW: false,
      taskBeErased: null,
      taskBeErasedOrder: null,
      HWList: [],
      // 회원 관리
      groupMember: [],
      // 회원가입 관리
      applicants: [],
      // pie chart data
      pieLoaded: false,
      examData: {
        labels: ["풀음", "안품"],
        datasets: [
          {
            label: "과제 현황",
            backgroundColor: ["#9AD0F5", "#FFB1C1"],
            data: []
          }
        ]
      },
      // bar chart data
      barLoaded: false,
      barData: {
        labels: [],
        datasets: [
          {
            label: "제출자",
            backgroundColor: "#9AD0F5",
            data: []
          },
          {
            label: "미제출자",
            backgroundColor: "#FFB1C1",
            data: []
          }
        ]
      },
      // HW progress data
      hwData: []
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
        this.$router.go();
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
    // 그룹 인원 강퇴
    async kick_member(member) {
      await this.$http.delete(`${this.ServerURL}/user/grouping/kick_member`, {
        params: {
          groupname: this.$route.params.groupname,
          wantkick: member
        }
      });
      this.$router.go();
    },
    // 그래프 그리기
    async getHWProgress() {
      /* eslint-disable */
      const HWs = await this.getLiveHW();
      this.HWList = HWs;
      let solved_total = 0;
      let unsolved_total = 0;
      const fetchHWprogress = async problemnum => {
        const HwInfo = await this.$http.get(`${this.ServerURL}/newuser/grouping/check_goal`, {
          params: {
            groupname: this.$route.params.groupname,
            problemid: problemnum
          }
        });
        return HwInfo.data;
      };

      const MultiFetchingData = async HW => {
        const req = HW.map(hw => {
          return fetchHWprogress(hw.problemid).then(res => {
            const values = Object.values(res);
            let solved = 0;
            let unsolved = 0;
            for (let i = 0; i < values.length; i++) {
              if (values[i] === "false") {
                unsolved++;
                unsolved_total++;
              } else {
                if (isNaN(values[i])) {
                  solved++;
                  solved_total++;
                }
              }
            }
            this.hwData.push({
              problemname: hw.problemname,
              problemnum: hw.problemid,
              solved,
              unsolved
            });
            return res;
          });
        });
        return Promise.all(req);
      };
      MultiFetchingData(HWs).then(() => {
        this.examData.datasets[0].data.push(solved_total);
        this.examData.datasets[0].data.push(unsolved_total);
        this.pieLoaded = true;
        this.drawBarChart();
      });
      /* eslint-enable */
    },
    drawBarChart() {
      // destruct 해서 BarChart 에 데이터 삽입
      // eslint-disable-next-line
      for (const data of this.hwData) {
        this.barData.labels.push(data.problemname);
        this.barData.datasets[0].data.push(data.solved);
        this.barData.datasets[1].data.push(data.unsolved);
      }
      this.barLoaded = true;
    },
    async getLiveHW() {
      const res = await this.$http.get(`${this.ServerURL}/user/grouping/pre_goal_list`, {
        params: {
          direction: "ASC",
          groupname: this.$route.params.groupname,
          page: 1,
          size: 5000
        }
      });
      return res.data;
    },
    // 과제 출제
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
            mention: this.HW.mention,
            problemid: this.HW.number,
            problemname: this.HW.name
          }
        });
        const HCopy = _.cloneDeep(this.HW);
        this.HWList.push(HCopy);
      } catch (err) {
        console.dir(err);
      } finally {
        this.$router.go();
      }
    },
    // 과제 삭제
    async deleteHW(hwNum) {
      try {
        await this.$http.delete(`${this.ServerURL}/user/grouping/delete_goal`, {
          params: {
            id: hwNum
          }
        });
        console.log(this.HWList);
        this.HWList.splice(this.HWList.indexOf(this.taskBeErasedOrder), 1);
        console.log(this.HWList);
      } catch (e) {
        console.error(e);
      }
    },
    async getGroupMember() {
      try {
        const { data } = await this.$http.get(`${this.ServerURL}/user/grouping/group_member`, {
          params: {
            groupname: this.$route.params.groupname
          }
        });
        this.groupMember = data;
        /* eslint-disable */
        for (let idx = 0; idx < this.groupMember.length; idx++) {
          if (this.groupMember[idx].role === "owner") {
            this.array_move(this.groupMember, idx, 0);
            break;
          }
        }
      } catch (e) {
        console.log(e);
      }
    },
    array_move(arr, old_idx, new_idx) {
      if (new_idx >= arr.length) {
        let k = (new_idx = arr.length + 1);
        while (k--) {
          arr.push(undefined);
        }
      }
      arr.splice(new_idx, 0, arr.splice(old_idx, 1)[0]);
      return arr;
    }
  },
  computed: mapState(["ServerURL", "ImgURL"]),
  mounted() {
    this.getGroupMember();
    this.getApplicantList();
    this.getHWProgress();
  }
};
</script>

<style lang="scss">
.button_margin {
  margin-left: 4.5vw;
}
.chart-container {
  flex-grow: 1;
  min-height: 0;

  > .pie {
    position: relative;
    height: 80%;
  }
  > .bar {
    position: relative;
    height: 100%;
  }
}
</style>
