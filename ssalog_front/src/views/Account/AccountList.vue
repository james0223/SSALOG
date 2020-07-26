<template>
  <div>
    사용자 조회 list
    <SearchBar SelectedCategoryIdx="3" />
    {{ searchData.nickname }}
    {{ users }}
  </div>
</template>

<script>
import axios from "axios";
import SearchBar from "@/components/SearchBar.vue";

export default {
  name: "AccountList",
  components: {
    SearchBar
  },
  data() {
    return {
      searchData: {
        direction: "ASC",
        nickname: this.$route.query.q,
        page: 0,
        size: 12
      },
      users: []
    };
  },
  methods: {
    async fetchUserData() {
      // const options = {
      //   params: {
      //     ...this.searchData
      //   }
      // }
      try {
        const res = await axios.get(
          // this.$serverURL
          "http://i3b101.p.ssafy.io:8080/newuser/search/to_nickname",
          {
            params: {
              ...this.searchData
            }
          }
        );
        this.users = res.data.content;
        console.log(res);
      } catch (e) {
        console.log(e);
      }
    }
  },
  mounted() {
    this.fetchUserData();
    // if (!this.q) {
    //   // 전체 user 조회
    //   this.q = "검색어가 없으므로 걍 전체리스트한다 ㅇㅋ?";
    // } else {
    //   // 닉 기반으로 조회
    // }
  }
};
</script>

<style></style>
