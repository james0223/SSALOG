<template>
  <v-container>
    <SearchBar SelectedCategoryIdx="3" />
    <h2>
      {{ searchData.nickname ? `${searchData.nickname}님에 대한 검색결과입니다.` : "전체 사용자" }}
    </h2>
    <v-row>
      <v-col v-for="(user, i) in users" :key="i" cols="3">
        <v-hover style="cursor:pointer" v-slot:default="{ hover }">
          <v-card @click="visitSalog(user.id)" align="center" :elevation="hover ? 12 : 2">
            <v-card-text>
              <v-avatar size="62">
                <img
                  :src="`${$store.state.ImgURL}/${user.imgpath ? user.imgpath : 'default.png'}`"
                  alt="유저 썸네일"
                />
              </v-avatar>
            </v-card-text>
            <v-card-flex>
              {{ user.nickname }}
            </v-card-flex>
            <v-card-text>
              제출한 리뷰 : n개
            </v-card-text>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
    <div class="text-center">
      <v-pagination v-model="searchData.page" :length="4" circle></v-pagination>
    </div>
  </v-container>
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
        size: 12
      },
      users: []
    };
  },
  methods: {
    visitSalog(id) {
      this.$router.push({ name: "SSALOG", query: { id } });
    },
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
