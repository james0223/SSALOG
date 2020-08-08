<template>
  <v-card flat height="60vh" class="pa-6 mt-8">
    <h3 class="font-weight-light mb-6">풀이목록</h3>
    <v-divider></v-divider>
    <hr />
    <v-data-table
      :headers="headers"
      :items="solutions"
      :single-expand="singleExpand"
      item-key="scoring"
      :items-per-page="100"
      class="elevation-1"
      show-expand
      :footer-props="{
        itemsPerPageOptions: [10],
        showFirstLastPage: true,
        firstIcon: 'mdi-arrow-collapse-left',
        lastIcon: 'mdi-arrow-collapse-right',
        prevIcon: 'mdi-minus',
        nextIcon: 'mdi-plus'
      }"
    >
      <template v-slot:expanded-item="{ item }">
        <td colspan="2"></td>
        <td colspan="6" style="text-align: center;">
          {{ item.keyword }}
        </td>
      </template></v-data-table
    >
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  data() {
    return {
      singleExpand: true,
      headers: [
        {
          text: "문제",
          align: "start",
          sortable: false,
          value: "problemname"
        },
        { text: "작성날짜", align: "end", width: 200, value: "regdate" },
        { text: "문제번호", align: "end", sortable: false, width: 100, value: "problemid" },
        { text: "키워드", align: "end", sortable: false, width: 100, value: "data-table-expand" },
        { text: "좋아요", align: "end", width: 100, value: "like" }
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
        const res = await axios.get(`${this.$store.state.ServerURL}/newuser/search/to_username`, {
          params: {
            to_username: this.$route.params.id,
            direction: "ASC",
            page: 1,
            size: 1000000,
            username: "jso"
          }
        });
        this.solutions = res.data.content;
        const iterable = this.solutions;
        iterable.forEach(element => {
          const temp = element;
          if (temp.keyword != null) {
            temp.keyword = temp.keyword.join(" / ");
          }
        });

        console.dir(this.solutions);
      } catch (e) {
        console.error(e);
      }
    },
    clicked(arg) {
      alert(arg);
    }
  }
};
</script>

<style scoped></style>
