<template>
  <v-card flat height="60vh" class="pa-6 mt-8">
    <v-card-title>
      스크랩목록
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
        <a
          style=" color: black; "
          target="_blank"
          :href="`/SSALOG/${item.nickname}/Solution/${item.scoring}`"
        >
          {{ item.title }}
        </a>
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
      // singleExpand: true,
      headers: [
        {
          text: "제목",
          align: "start",
          sortable: false,
          value: "title"
        },
        { text: "작성자", align: "center", width: 100, value: "nickname" },
        { text: "문제이름", width: 120, value: "problemname" },
        { text: "문제번호", sortable: false, width: 100, value: "problemid" },
        { text: "키워드", sortable: false, width: 160, value: "keyword" }
      ],
      solutions: []
    };
  },

  mounted() {
    this.getScrap();
  },
  methods: {
    async getScrap() {
      try {
        const res = await axios.post(
          `${this.$store.state.ServerURL}/newuser/scrap/scraplist`,
          {},
          {
            params: {
              nickname: this.$route.params.nickname
            }
          }
        );
        console.log(res.data);
        this.solutions = res.data.reverse();
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

<style lang="scss" scoped></style>
