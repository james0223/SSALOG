<template>
  <v-card flat height="70vh" class="mx-7 my-15">
    <v-card flat class="mb-5">
      <v-toolbar-title>과제 목록</v-toolbar-title>
      <v-simple-table height="20vh">
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">문제</th>
              <th class="text-left">남은 시간</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="hw in Homeworks" :key="hw.id">
              <td>{{ hw.problemname }}</td>
              <countdown :time="getCount(hw.limit)" :interval="1000" tag="td">
                <template slot-scope="props"
                  >{{ props.days }}일 {{ props.hours }}시간 {{ props.minutes }}분
                  {{ props.seconds }}초</template
                >
              </countdown>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </v-card>
    <v-card flat height="45vh">
      <v-toolbar-title class="mb-4">제출 목록</v-toolbar-title>
      <v-card width="27vw" height="40vh" class="d-inline-block mr-5">
        <v-subheader>제출자</v-subheader>
        <v-divider></v-divider>
      </v-card>
      <v-card width="27vw" height="40vh" class="d-inline-block">
        <v-subheader>미제출자</v-subheader>
        <v-divider></v-divider>
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
      Homeworks: [
        {
          title: "아직 출제된 과제가 없습니다.",
          manager: "",
          submit: "",
          pending: "",
          due: ""
        }
      ],
      // 아래 card
      searchKeyword: "",
      headers: [
        {
          text: "제출자",
          align: "start",
          sortable: false,
          value: "nickname"
        },
        { text: "추천 수", value: "stars" },
        { text: "시간", value: "time" },
        { text: "메모리", value: "memory" }
      ],
      submissions: [
        {
          nickname: "앙기모리",
          stars: 3,
          time: 20,
          memory: 2020
        },
        {
          nickname: "한조",
          stars: 3,
          time: 20,
          memory: 2020
        },
        {
          nickname: "두둥탁",
          stars: 3,
          time: 20,
          memory: 2020
        }
      ]
    };
  },
  methods: {
    getCount(date) {
      const due = new Date(date);
      const now = new Date();
      return due - now;
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
      this.Homeworks = res.data;
      return res.data;
    },
    /* eslint-disable */
    async totalHWProgress() {
      const HWs = await this.getLiveHW();

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
        console.log(finalres);
      });
    }
    /* eslint-enable */
  },
  computed: mapState(["ServerURL"]),
  mounted() {
    this.totalHWProgress();
  }
};
</script>

<style scoped></style>
