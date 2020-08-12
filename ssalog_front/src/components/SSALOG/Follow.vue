<template>
  <v-card flat height="60vh" class="pa-6 mt-8">
    <!-- <h2 class="text-center mt-16" v-if="isNoResult">팔로우가없습니다</h2> -->
    <v-btn class="mr-1 pa-10  text-center   rounded-t-xl primary">
      Following
    </v-btn>
    <v-btn class="pa-10   text-center   rounded-t-xl primary">
      Follower
    </v-btn>
    <hr />

    <v-row>
      <v-col v-for="(user, i) in users" :key="i" cols="3">
        <v-hover style="cursor:pointer" v-slot:default="{ hover }">
          <v-card
            @click="visitUserDetail(user.nickname)"
            align="center"
            :elevation="hover ? 4 : 1"
            style=" border-radius:10%; margin:30px;"
          >
            <v-img
              :src="`${$store.state.ImgURL}/${user.imgpath ? user.imgpath : 'default.png'}`"
              alt="유저 썸네일"
              height="150px"
            ></v-img>

            <v-card-subtitle> {{ user.nickname }} </v-card-subtitle>
            <v-btn block color="red" text @click.stop="showAlert">
              삭제
            </v-btn>
          </v-card>
        </v-hover>
      </v-col>
    </v-row>
    <infinite-loading
      v-if="limit >= searchData.page"
      @infinite="infiniteHandler"
      spinner="waveDots"
    ></infinite-loading>
  </v-card>
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
    showAlert() {
      alert("클릭");
    },
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
