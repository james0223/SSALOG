<template>
  <v-card flat height="60vh">
    <v-row justify="center" bottom>
      <div id="nothing">
        <v-avatar size="240" class="mt-8">
          <img :src="userThumbnail" />
        </v-avatar>

        <v-dialog v-model="thumbnailDialog" max-width="600px">
          <template v-slot:activator="{ on, attrs }">
            <v-btn v-bind="attrs" v-on="on" class="mx-2" id="thumbnailplus" fab dark color="indigo">
              <v-icon dark>mdi-camera</v-icon>
            </v-btn>
          </template>
          <v-card>
            <v-card-title>
              <span class="headline">프로필사진 관리</span>
            </v-card-title>
            <v-list rounded>
              <v-list-item-group v-model="item" color="primary">
                <v-list-item v-for="(item, i) in items" :key="i" @click="changeThumbnail(i)">
                  <v-list-item-icon>
                    <v-icon v-text="item.icon"></v-icon>
                  </v-list-item-icon>
                  <v-list-item-content>
                    <v-list-item-title v-text="item.text"></v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
              </v-list-item-group>
            </v-list>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="blue darken-1" text @click="thumbnailDialog = false">닫기</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>
    </v-row>
    <v-row justify="center">
      <h3>{{ this.$store.state.nickname }}</h3>
    </v-row>
    <v-row justify="center">
      <v-btn outlined large class="mt-6 mb-5" @click="dialog = true">Edit Profile</v-btn>
    </v-row>
    <Profile :dialog.sync="dialog" />
    <v-row justify="center">
      <v-btn class="ma-2" text> <v-icon left>mdi-account-heart</v-icon>following </v-btn>
      <v-btn class="ma-2" text> <v-icon left>mdi-account-arrow-left</v-icon>follower </v-btn>
    </v-row>
    <v-row justify="center">
      <v-btn class="ma-2" text> <v-icon left>mdi-star</v-icon>star </v-btn>
    </v-row>
  </v-card>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";
import Profile from "./Profile.vue";

export default {
  components: {
    Profile
  },
  data() {
    return {
      dialog: false,
      thumbnailDialog: false,
      item: 0,
      items: [
        { text: "사진 업로드", icon: "mdi-camera-enhance" },
        { text: "기본이미지로 변경", icon: "mdi-camera-off" }
      ]
    };
  },
  methods: {
    async changeThumbnail(i) {
      if (i === 0) {
        try {
          // 여기서 이미지 업로드
        } catch (e) {
          console.error(e);
        }
      } else {
        try {
          await axios.delete(`${this.$store.state.ServerURL}/user/delete_profile_img`);
          this.$store.commit("Thumbnail", "default.png");
        } catch (e) {
          console.error(e);
        }
      }
    }
  },
  computed: mapState(["userThumbnail"])
};
</script>

<style scoped>
#nothing {
  position: relative;
}
#thumbnailplus {
  position: absolute;
  top: 222px;
  left: 190px;
}
</style>
