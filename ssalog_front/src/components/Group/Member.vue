<template>
  <v-card flat height="70vh" class="mx-7 my-15">
    <v-toolbar-title>그룹 멤버</v-toolbar-title>
    <v-divider></v-divider>
    <v-virtual-scroll class="mt-5" :items="groupMember" :item-height="50" height="300">
      <template v-slot="{ item }">
        <v-list-item>
          <v-list-item-avatar size="48">
            <v-img :src="ImgURL + item.img"> </v-img>
          </v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title>{{ item.nickname }}</v-list-item-title>
          </v-list-item-content>
          <v-spacer></v-spacer>
          <v-list-item-action>
            <v-btn color="amber lighten-2" tile large icon>
              <v-icon>mdi-trophy-award</v-icon>
            </v-btn>
          </v-list-item-action>
        </v-list-item>
      </template>
    </v-virtual-scroll>
  </v-card>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "GroupList",
  data() {
    return {
      groupMember: []
    };
  },
  methods: {
    async getGroupMember() {
      try {
        const { data } = await this.$http.get(`${this.ServerURL}/user/grouping/group_member`, {
          params: {
            groupname: this.$route.params.groupname
          }
        });
        console.log(data);
        this.groupMember = [];
        // eslint-disable-next-line
        for (let i = 0; i < data.length; i++) {
          this.groupMember.push(data[i]);
        }
        console.log(this.groupMember);
      } catch (e) {
        console.log(e);
      }
    }
  },
  computed: mapState(["ServerURL", "ImgURL"]),
  mounted() {
    this.getGroupMember();
  }
};
</script>

<style scoped></style>
