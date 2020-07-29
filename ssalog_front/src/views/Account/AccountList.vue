<template>
  <div>
    사용자 조회 list
    <SearchBar SelectedCategoryIdx="3" />
    <h2 v-if="searchData.nickname">{{ searchData.nickname }} 에 대한 검색결과입니다.</h2>
    <v-container>
      <v-row>
        <v-col v-for="(user, i) in users" :key="i" cols="3">
          <v-card>
            <v-avatar>
              <img src="https://cdn.vuetifyjs.com/images/john.jpg" alt="John" />
            </v-avatar>
            <v-card-text>
              {{ user.nickname }}
            </v-card-text>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
    <div class="text-center">
      <v-pagination v-model="searchData.page" :length="4" circle></v-pagination>
    </div>
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
        page: 1,
        size: 6
      },
      users: []
    };
  },
  methods: {
    async fetchUserData() {
      try {
        const res = await axios.get(`${this.$store.state.ServerURL}/newuser/search/to_nickname`, {
          params: {
            ...this.searchData
          }
        });
        this.users = res.data.content;
      } catch (e) {
        console.log(e);
      }
    }
  },
  watch: {
    searchData: {
      deep: true,
      handler() {
        this.fetchUserData();
      }
    }
  },
  mounted() {
    this.fetchUserData();
  }
};
</script>

<style></style>
