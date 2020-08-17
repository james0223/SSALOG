<template>
  <v-container class="mt-16">
    <div class="d-flex justify-space-between">
      <h1>쌀로그</h1>
      <v-btn
        @click="$router.push({ name: 'SSalogMain', params: { nickname } })"
        tile
        v-if="nickname"
        color="#2E6FF2"
        dark
        class="animate__animated animate__bounce "
        >내 쌀로그</v-btn
      >
    </div>
    <SearchBar :SelectedCategoryIdx="3" :Q="$route.query.q" :Keywords="$route.query.keywords" />
    <h2 class="text-center mt-10" v-if="isNoResult">검색결과가 없습니다 :P</h2>
    <h2 v-if="!isNoResult">
      {{ searchData.nickname ? `${searchData.nickname}님에 대한 검색결과입니다.` : null }}
    </h2>
    <v-row>
      <v-col v-if="!nickname && showCard && !isNoResult" cols="12" sm="3">
        <v-hover v-slot:default="{ hover }">
          <v-card class="toNewbee" height="100%" dark elevation="8">
            <v-card-title>
              <h4 class="ml-3 mt-5">
                쌀로그의 회원이<br />
                되어주세요!😃
              </h4>
            </v-card-title>
            <v-card
              v-if="hover"
              class="d-flex flex-column black v-card--reveal white--text pa-4"
              elevation="12"
            >
              <v-card-actions
                ><v-btn color="black" large rounded @click="$router.push({ name: 'Login' })"
                  ><v-icon>mdi-login</v-icon>로그인</v-btn
                ></v-card-actions
              >
              <v-card-actions>
                <v-btn color="black" large rounded @click="$router.push({ name: 'SignUp' })">
                  <v-icon>mdi-checkbox-marked-circle-outline</v-icon>회원가입</v-btn
                ></v-card-actions
              >
            </v-card>
          </v-card>
        </v-hover>
      </v-col>
      <v-col v-for="(user, i) in users" :key="i" cols="12" sm="3">
        <v-hover style="cursor:pointer" v-slot:default="{ hover }">
          <v-card
            tile
            color="transparent"
            @click="visitUserDetail(user.nickname)"
            align="center"
            :elevation="hover ? 12 : 2"
          >
            <v-card-text>
              <v-avatar size="62">
                <img
                  :src="`${$store.state.ImgURL}/${user.imgpath ? user.imgpath : 'default.png'}`"
                  alt="유저 썸네일"
                />
              </v-avatar>
            </v-card-text>
            <v-card-text>
              {{ user.nickname }}
            </v-card-text>
            <v-card-text>
              제출한 리뷰 : n개
            </v-card-text>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
    <infinite-loading
      v-if="limit >= searchData.page && !isError && !isNoResult"
      @infinite="infiniteHandler"
      spinner="waveDots"
    ></infinite-loading>
  </v-container>
</template>

<script>
import axios from "axios";
import InfiniteLoading from "vue-infinite-loading";
import SearchBar from "@/components/SearchBar.vue";
import { mapState } from "vuex";
import "animate.css";

export default {
  name: "AccountList",
  components: {
    InfiniteLoading,
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
      limit: 9999,
      users: [],
      showCard: false,
      is_fetching: false,
      isNoResult: false,
      isError: false
    };
  },
  computed: mapState(["nickname"]),

  methods: {
    infiniteHandler($state) {
      setTimeout(() => {
        if (!this.is_fetching && !this.isNoResult && !this.isError) {
          this.fetchUserData();
        }
        $state.loaded();
      }, 1000);
    },
    visitUserDetail(nickname) {
      this.$router.push({ name: "SSalogMain", params: { nickname } });
    },
    async fetchUserData() {
      this.is_fetching = true;
      try {
        const { data } = await axios.get(
          `${this.$store.state.ServerURL}/newuser/search/to_nickname`,
          {
            params: {
              ...this.searchData
            }
          }
        );
        if (data.totalElements === 0) {
          this.isNoResult = true;
        }
        this.searchData.page += 1; // lint :(
        this.users = [...this.users, ...data.content];
        this.limit = Number(data.totalPages);
      } catch (e) {
        console.error(e);
        this.isError = true;
      }
      this.is_fetching = false;
      this.showCard = true;
    }
  }
};
</script>

<style scoped>
.toNewbee {
  background: rgb(122, 6, 118);
  background: linear-gradient(145deg, rgba(122, 6, 118, 1) 16%, rgba(0, 220, 255, 1) 92%);
}
.v-card--reveal {
  bottom: 0;
  opacity: 0.85;
  justify-content: center;
  position: absolute;
  width: 100%;
  height: 100%;
}
</style>
