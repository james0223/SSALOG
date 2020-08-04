<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col lg="2" cols="12">
        <div id="relative_wrapper">
          <v-avatar size="260" class="mt-8">
            <img :src="userThumbnail" />
          </v-avatar>
          <v-dialog v-model="thumbnailDialog" max-width="600px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                v-bind="attrs"
                v-on="on"
                class="mx-2"
                id="thumbnailplus"
                fab
                dark
                color="indigo"
              >
                <v-icon dark>mdi-camera</v-icon>
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="headline">프로필사진 관리</span>
              </v-card-title>
              <v-list rounded>
                <v-list-item-group v-model="ThumbnailSelect" color="primary">
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
          <v-tabs vertical class="my-15 pa-3" v-model="activeTab">
            <v-tab v-for="tab in tabs" :key="tab.id" :to="tab.route" exact>
              <v-icon left>{{ tab.icon }}</v-icon>
              {{ tab.name }}
            </v-tab>
          </v-tabs>
        </div>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col lg="7" cols="12"><router-view></router-view> </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  data() {
    return {
      // 왼쪽 thumbnail 관련
      thumbnailDialog: false,
      ThumbnailSelect: 0,
      items: [
        { text: "사진 업로드", icon: "mdi-camera-enhance" },
        { text: "기본이미지로 변경", icon: "mdi-camera-off" }
      ],
      userData: {
        id: this.$route.query.id,
        nickname: null // 지금 없지만 받아온다.
      },
      activeTab: `/SSALOG/main`,
      tabs: [
        { id: 0, name: "Main", route: `/SSALOG/Main`, icon: "mdi-clipboard-text-play-outline" },
        { id: 1, name: "Solution", route: `/SSALOG/Solution`, icon: "mdi-heart" },
        { id: 2, name: "Profile", route: `/SSALOG/Profile`, icon: "mdi-heart" },
        { id: 3, name: "Following", route: `/SSALOG/Following`, icon: "mdi-heart" },
        { id: 4, name: "Follower", route: `/SSALOG/Follower`, icon: "mdi-heart" },
        { id: 5, name: "Star", route: `/SSALOG/Star`, icon: "mdi-heart" }
      ]
    };
  },
  created() {
    if (!this.userData.id) {
      if (!this.$store.state.accessToken) {
        // 로그인 안했는데 내 살로그 가기 누른 경우
        this.$router.push({ name: "Login" });
      } else {
        // 자기 계정 로딩
      }
    }
  },
  computed: mapState(["userThumbnail"])
};
</script>
<style>
#relative_wrapper {
  position: relative;
}
#thumbnailplus {
  position: absolute;
  top: 222px;
  left: 190px;
}
</style>
