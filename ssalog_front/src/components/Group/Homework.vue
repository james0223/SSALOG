<template>
  <v-card flat height="70vh" class="mx-7 my-15">
    <v-card flat class="mb-5">
      <v-toolbar-title>과제 목록</v-toolbar-title>
      <v-simple-table height="20vh">
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">문제</th>
              <th class="text-left">출제자</th>
              <th class="text-left">제출인원</th>
              <th class="text-left">미 제출인원</th>
              <th class="text-left">남은 시간</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="hw in Homeworks" :key="hw.id">
              <td>{{ hw.title }}</td>
              <td>{{ hw.manager }}</td>
              <td>{{ hw.submit }}</td>
              <td>{{ hw.pending }}</td>
              <countdown :time="getCount(hw.due)" :interval="1000" tag="td">
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
      <v-toolbar-title>제출 목록</v-toolbar-title>
      <v-data-table :headers="headers" :items="submissions"></v-data-table>
    </v-card>
  </v-card>
</template>

<script>
export default {
  name: "Homework",
  data() {
    return {
      Homeworks: [
        {
          id: 21223225,
          title: "백양로 브레이크",
          manager: "gofeel",
          submit: 4,
          pending: 3,
          due: "2020-08-12"
        },
        {
          id: 212323,
          title: "골목대장 맥크리",
          submit: 4,
          pending: 3,
          manager: "McLee",
          due: "2020-08-20"
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
    }
  }
};
</script>

<style scoped></style>
