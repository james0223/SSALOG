<template>
  <v-container>
    <h2 class="text-center mt-16" v-if="isNoResult">검색결과가 없습니다 :P</h2>
    <h2 v-if="!isNoResult">
      {{ searchData.nickname ? `${searchData.nickname}님에 대한 검색결과입니다.` : "전체 사용자" }}
    </h2>
    <v-row>
      <v-col v-for="(user, i) in users" :key="i" cols="3">
        <v-hover style="cursor:pointer" v-slot:default="{ hover }">
          <v-card
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
      v-if="limit >= searchData.page"
      @infinite="infiniteHandler"
      spinner="waveDots"
    ></infinite-loading>
  </v-container>
</template>

<script>
import axios from "axios";
import InfiniteLoading from "vue-infinite-loading";

export default {
  name: "AccountList",
  components: {
    InfiniteLoading
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
      isNoResult: false
    };
  },
  methods: {
    infiniteHandler($state) {
      setTimeout(() => {
        this.fetchUserData();
        $state.loaded();
      }, 2000);
    },
    visitUserDetail(nickname) {
      this.$router.push({ name: "SSalogMain", params: { nickname } });
    },
    async fetchUserData() {
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
      }
    }
  },
  // 이거 왜 만들었는지 기억이 안남
  // watch: {
  //   searchData: {
  //     deep: true,
  //     handler() {
  //       this.fetchUserData();
  //     }
  //   }
  // },
  mounted() {
    // this.fetchUserData();
  }
};
</script>

<style></style>
