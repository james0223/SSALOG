<template>
  <v-card color="transparent" flat height="70vh" class="mx-7 my-15">
    <v-card color="transparent" flat class="mb-5">
      <v-toolbar-title>이전 과제 목록</v-toolbar-title>
      <v-simple-table height="20vh">
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">문제</th>
              <th class="text-left">문제 설명</th>
              <th class="text-left">과제 완료율</th>
              <th class="text-left">문제 링크</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(hw, idx) in Homeworks" :key="idx" @click="sortMemberByHW(idx)">
              <td>
                {{ hw.problemname }}
              </td>
              <td>{{ hw.mention }}</td>
              <td>{{ solvedRate }}%</td>
              <th>
                <a target="_blank" :href="`https://www.acmicpc.net/problem/${hw.problemid}`">
                  문제 링크
                </a>
              </th>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-card>
    <v-card color="transparent" flat height="45vh">
      <v-toolbar-title class="mb-4">제출 목록</v-toolbar-title>
      <v-card min-width="40%" height="35vh" class="d-inline-block mx-5">
        <v-subheader>제출자</v-subheader>
        <v-divider></v-divider>
        <v-virtual-scroll :items="solvedMembers" :item-height="50" height="500">
          <template v-slot="{ item }">
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title> {{ item.name }}</v-list-item-title>
              </v-list-item-content>
              <v-list-item-action>
                <v-btn depressed small v-bind:href="`${item.route}`">
                  풀이 보러 가기
                  <v-icon color="secondary" right>
                    mdi-open-in-new
                  </v-icon>
                </v-btn>
              </v-list-item-action>
            </v-list-item>
          </template>
        </v-virtual-scroll>
      </v-card>
      <v-card min-width="40%" height="35vh" class="d-inline-block">
        <v-subheader>미제출자</v-subheader>
        <v-divider></v-divider>
        <v-virtual-scroll :items="unsolvedMembers" :item-height="50" height="500">
          <template v-slot="{ item }">
            <v-list-item>
              <v-list-item-content>
                <v-list-item-title> {{ item.name }}</v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </template>
        </v-virtual-scroll>
      </v-card>
    </v-card>
  </v-card>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Homework",
  data() {
    return {
      Homeworks: [],
      taskSubmissionStatus: [],
      solvedMembers: [],
      unsolvedMembers: [],
      solvedNum: 0,
      unsolvedNum: 0,
      solvedRate: 0
    };
  },
  methods: {
    getCount(date) {
      const due = new Date(date);
      const now = new Date();
      return due - now;
    },
    async getDeadHW() {
      const res = await this.$http.get(`${this.ServerURL}/user/grouping/post_goal_list`, {
        params: {
          direction: "ASC",
          groupname: this.$route.params.groupname,
          page: 1,
          size: 5000
        }
      });
      this.Homeworks = res.data;
      return res.data;
    },
    /* eslint-disable */
    async totalFormerHWProgress() {
      const HWs = await this.getDeadHW();

      const fetchHWprogress = async problemnum => {
        const HwInfo = await this.$http.get(`${this.ServerURL}/newuser/grouping/check_goal`, {
          params: {
            groupname: this.$route.params.groupname,
            problemid: problemnum
          }
        });
        return HwInfo.data;
      };
      const fetchingHWs = async HW => {
        const req = HW.map(hw => {
          return fetchHWprogress(hw.problemid).then(res => {
            return res;
          });
        });
        return Promise.all(req);
      };
      fetchingHWs(HWs).then(finalres => {
        this.taskSubmissionStatus = finalres;
      });
    },
    sortMemberByHW(idx) {
      this.solvedMembers = [];
      this.unsolvedMembers = [];
      Object.keys(this.taskSubmissionStatus[idx]).map(key => {
        if (this.taskSubmissionStatus[idx][key] === "false") {
          this.unsolvedMembers.push({ name: key });
        } else {
          this.solvedMembers.push({ name: key, route: this.taskSubmissionStatus[idx][key] });
        }
      });
      this.solvedNum = this.solvedMembers.length;
      this.unsolvedNum = this.unsolvedMembers.length;
      this.solvedRate = this.solvedNum / (this.solvedNum + this.unsolvedNum);
    }
    /* eslint-enable */
  },
  computed: mapState(["ServerURL"]),
  mounted() {
    this.totalFormerHWProgress();
  }
};
</script>

<style scoped></style>
