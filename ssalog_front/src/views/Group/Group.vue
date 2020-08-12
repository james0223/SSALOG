<template>
  <v-container>
    <v-row>
      <v-col lg="2">
        <v-card flat height="70vh">
          <v-tabs vertical class="my-15 pa-3">
            <v-tab
              style="justify-content:left;"
              v-for="menu in GroupMenu"
              :key="menu.name"
              :to="menu.route"
              exact
            >
              <v-icon left>{{ menu.icon }}</v-icon>
              {{ menu.name }}
            </v-tab>
          </v-tabs>
        </v-card>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col lg="8">
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
          name: "메인"
        },
        {
          name: "그룹원",
          route: `/Group/${this.$route.params.nickname}/Member`
        },
        {
          name: "과제",
          route: `/Group/${this.$route.params.nickname}/Homework`
        },
        {
          name: "그룹관리",
          route: `/Group/${this.$route.params.nickname}/Manage`
        }
      ]
    };
  },
  computed: mapState(["ServerURL"])
};
</script>

<style scoped></style>
