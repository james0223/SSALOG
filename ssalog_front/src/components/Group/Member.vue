<template>
  <v-card color="transparent" flat height="70vh" class="mx-7 my-15">
    <v-toolbar-title>그룹 멤버</v-toolbar-title>
    <v-divider></v-divider>
    <v-virtual-scroll class="mt-5" :items="groupMember" :item-height="50" height="600">
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
            <v-btn v-if="item.role === 'owner'" color="amber lighten-2" tile large icon>
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
        this.groupMember = [];
        // eslint-disable-next-line
        for (let idx = 0; idx < data.length; idx++) {
          if (data[idx].role === "owner") {
            this.array_move(data, idx, 0);
            break;
          }
        }
        this.groupMember = data;
      } catch (e) {
        console.log(e);
      }
    },
    /* eslint-disable */
    array_move(arr, old_idx, new_idx) {
      if (new_idx >= arr.length) {
        let k = (new_idx = arr.length + 1);
        while (k--) {
          arr.push(undefined);
        }
      }
      arr.splice(new_idx, 0, arr.splice(old_idx, 1)[0]);
      return arr;
    }
  },
  computed: mapState(["ServerURL", "ImgURL"]),
  mounted() {
    this.getGroupMember();
  }
};
</script>

<style scoped></style>
