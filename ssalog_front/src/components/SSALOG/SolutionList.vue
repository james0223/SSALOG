<template>
  <v-card color="transparent" flat height="60vh" class="pa-6 mt-8">
    <v-card-title>
      풀이목록
      <v-spacer></v-spacer>
      <v-text-field
        append-icon="mdi-magnify"
        label="검색"
        single-line
        hide-details
        v-model="search"
      ></v-text-field>
    </v-card-title>
    <v-divider></v-divider>
    <hr />
    <v-data-table
      v-model="selected"
      :headers="headers"
      :items="solutions"
      :search="search"
      no-data-text="😢 데이터가 없습니다 😢"
      item-key="scoring"
      :items-per-page="10"
      class="elevation-1"
      :footer-props="{
        itemsPerPageOptions: [10],
        showFirstLastPage: true,
        firstIcon: 'mdi-arrow-collapse-left',
        lastIcon: 'mdi-arrow-collapse-right',
        prevIcon: 'mdi-minus',
        nextIcon: 'mdi-plus'
      }"
    >
      <template v-slot:[`item.title`]="{ item }">
        <span
          style="cursor:pointer"
          @click="$router.push({ name: 'LogDetail', params: { id: item.scoring } })"
          >{{ item.title }}</span
        >
      </template>
      <template v-slot:[`item.problemid`]="{ item }">
        <span
          style="cursor:pointer"
          @click="$router.push({ name: 'ProblemDetail', params: { id: item.problemid } })"
          >{{ item.problemid }}</span
        >
      </template>
      <template v-slot:[`item.problemname`]="{ item }">
        <span
          style="cursor:pointer"
          @click="$router.push({ name: 'ProblemDetail', params: { id: item.problemid } })"
          >{{ item.problemname }}</span
        >
      </template>
      <template v-slot:[`item.keyword`]="{ item }">
        <span :title="item.keyword">{{ item.keyword2 }}</span>
      </template>
      <v-alert slot="no-results" :value="true">
        검색어 : "{{ search }}" 에 관한 글이 없습니다.
      </v-alert>
    </v-data-table>
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      selected: [],
      search: "",
      singleExpand: true,
      headers: [
        {
          text: "문제번호",
          sortable: false,
          value: "problemid"
        },
        { text: "문제이름", sortable: false, value: "problemname" },
        { text: "제목", sortable: false, value: "title" },
        { text: "좋아요", value: "like" },
        { text: "키워드", sortable: false, value: "keyword" },
        { text: "작성날짜", width: 120, value: "regdate" }
      ],
      solutions: []
    };
  },

  mounted() {
    this.getSolutions();
  },
  methods: {
    async getSolutions() {
      try {
        const owneremail = await this.$http.get(
          `${this.$store.state.ServerURL}/newuser/search/find_username`,
          {
            params: {
              nickname: this.$route.params.nickname // 바꿔야함
            }
          }
        );
        const res = await axios.get(`${this.$store.state.ServerURL}/newuser/search/to_username`, {
          params: {
            direction: "ASC",
            page: 1,
            size: 5000,
            username: owneremail.data // 수정필요
          }
        });
        this.solutions = res.data.content.reverse();
        const iterable = this.solutions;
        iterable.forEach(element => {
          const temp = element;
          if (temp.keyword && temp.keyword.length !== 0) {
            // temp.keyword2 = temp.keyword.join(" / ");
            const [foo] = temp.keyword;
            const size = temp.keyword.length - 1;
            if (size !== 0) {
              temp.keyword2 = foo.concat(" 외 ", size, "개");
            } else {
              temp.keyword2 = foo;
            }
          }
        });
      } catch (e) {
        console.error(e);
      }
    }
  }
};
</script>

<style scoped></style>
