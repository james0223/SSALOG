<template>
  <v-card flat min-height="80vh" class="pa-1 mt-8">
    <!-- <h2 class="text-center mt-16" v-if="isNoResult">팔로우가없습니다</h2> -->
    <v-tabs background-color="white" color="blue accent-4" grow>
      <v-tab @click="getFollowing()">Following</v-tab>
      <v-tab @click="getFollower()">Follower</v-tab>
    </v-tabs>

    <v-row>
      <template v-if="Object.keys(this.users).length !== 0">
        <v-col v-for="(user, i) in users" :key="i" cols="3">
          <v-hover style="cursor:pointer" v-slot:default="{ hover }">
            <v-card
              @click="visitUserDetail(i)"
              align="center"
              :elevation="hover ? 4 : 1"
              style=" border-radius:10%; margin:30px;"
            >
              <v-img
                :src="`${$store.state.ImgURL}/${user ? user : 'default.png'}`"
                alt="유저 썸네일"
                height="150px"
              ></v-img>

              <v-card-subtitle> {{ i }} </v-card-subtitle>
              <v-btn
                v-if="flag && $store.state.nickname === $route.params.nickname"
                block
                color="red"
                text
                @click.stop="deleteFollow(i)"
              >
                <v-icon>mdi-delete-empty</v-icon>
              </v-btn>
            </v-card>
          </v-hover>
        </v-col>
      </template>
      <template v-else-if="flag">
        <v-row class="col-12" align="center" justify="center">
          <v-icon size="400">mdi-heart</v-icon>
        </v-row>
        <v-row align="center" justify="center">
          <h2>팔로우를 통해 교류를 시작하세요 👨‍👩‍👧‍👦</h2>
        </v-row>
      </template>
      <template v-else>
        <v-row class="col-12" align="center" justify="center">
          <v-icon size="400">mdi-heart</v-icon>
        </v-row>
        <v-row align="center" justify="center">
          <h2>팔로워가 없습니다 😢</h2>
        </v-row>
      </template>
    </v-row>
  </v-card>
</template>

<script>
import axios from "axios";

export default {
  name: "AccountList",
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
      isNoResult: false,
      flag: true
    };
  },
  methods: {
    async getFollowing() {
      this.flag = true;
      try {
        const res = await axios.get(
          `${this.$store.state.ServerURL}
/newuser/follow/myfollow`,
          {
            params: {
              nickname: this.$route.params.nickname
            }
          }
        );
        this.users = res.data;
        // console.dir(Object.keys(this.users).length);
      } catch (e) {
        console.error(e);
      }
    },
    async getFollower() {
      this.flag = false;
      try {
        const res = await axios.get(
          `${this.$store.state.ServerURL}
/newuser/follow/myfollowing`,
          {
            params: {
              nickname: this.$route.params.nickname
            }
          }
        );
        this.users = res.data;
        // console.dir(Object.keys(this.users).length);
      } catch (e) {
        console.error(e);
      }
    },
    async deleteFollow(target) {
      try {
        const res = await axios.delete(
          `${this.$store.state.ServerURL}
/user/follow/cancel_follow`,
          {
            params: {
              following: target
            }
          }
        );
        console.dir(res);
        this.getFollowing();
        this.$emit("decrement");
      } catch (e) {
        console.error(e);
      }
    },
    visitUserDetail(nickname) {
      this.$router.push({ name: "SSalogMain", params: { nickname } });
    }
  },
  mounted() {
    this.getFollowing();
  }
};
</script>

<style></style>
