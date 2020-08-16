<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col cols="2">
        <v-card flat height="70vh">
          <v-tabs vertical class="my-15 pa-3">
            <v-tab
              style="justify-content:left;"
              v-for="menu in GroupMenu"
              :key="menu.name"
              :to="menu.route"
              exact
              :class="{ 'd-none': menu.flag }"
            >
              <v-icon left>{{ menu.icon }}</v-icon>
              {{ menu.name }}
            </v-tab>
          </v-tabs>
        </v-card>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col>
        <router-view></router-view>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "Group",
  data() {
    return {
      GroupMenu: [
        {
          name: "그룹원",
          route: `/Group/${this.$route.params.groupname}/`
        },
        {
          name: "과제",
          route: `/Group/${this.$route.params.groupname}/Homework`
        },
        {
          name: "그룹관리",
          route: `/Group/${this.$route.params.groupname}/Manage`,
          flag: true
        }
      ]
    };
  },
  methods: {
    async checkRole() {
      try {
        const { data } = await this.$http.get(`${this.ServerURL}/user/grouping/check_role`, {
          params: {
            groupname: this.$route.params.groupname,
            nickname: this.nickname
          }
        });
        if (data === "owner") {
          this.GroupMenu[2].flag = false;
        }
      } catch (e) {
        console.log("에러", e);
        // 현재 이부분 작동 안함. 왜 안하는지 모르겠음.
        // this.$store.commit("ShowAlert", {
        //   flag: true,
        //   msg: "허가받지 않은 사용자입니다. 메인으로 이동합니다",
        //   color: "error"
        // });
        // setTimeout(() => {
        //   this.$store.commit("ShowAlert", { flag: false, msg: "" });
        //   this.$router.push({ name: "Home" });
        // }, 1000);
      }
    }
  },
  computed: mapState(["ServerURL", "nickname"]),
  created() {
    this.checkRole();
  }
};
</script>

<style scoped></style>
