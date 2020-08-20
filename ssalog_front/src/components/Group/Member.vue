<template>
  <v-container>
    <v-row>
      <v-col cols="6">
        <v-card color="transparent" flat width="30vw" height="70vh" class="mx-7 my-15">
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
      </v-col>
      <v-col cols="6">
        <div class="group_anime_label">
          <h1>총 {{ groupMember.length }}명의</h1>
          <h2>인원이 참여하고 있습니다</h2>
        </div>

        <lottie :options="defaultOptions"></lottie>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import Lottie from "@/components/lottie.vue";
import { mapState } from "vuex";
// eslint-disable-next-line
import * as group_member_anim from "@/assets/images/group/group_main.json";

export default {
  name: "GroupList",
  components: {
    lottie: Lottie
  },
  data() {
    return {
      groupMember: [],
      defaultOptions: { animationData: group_member_anim }
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

<style scoped>
.group_anime_label {
  position: fixed;
  top: 250px;
  margin-left: 40px;
}
</style>
