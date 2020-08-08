<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col lg="2">
        <div id="relative_wrapper">
          <v-avatar size="240" class="mt-8">
            <img :src="userThumbnail" />
            <!--변경해줘야할듯-->
          </v-avatar>
          <v-dialog v-model="thumbnailDialog" max-width="600px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                v-bind="attrs"
                v-on="on"
                v-if="$store.state.username === $route.params.username"
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
          <v-row justify="center" style="width:240px">
            <div class="ma-2 font-weight-bold"><v-icon>mdi-account</v-icon> {{ ownerName }}님</div>
          </v-row>
          <v-tabs vertical class="my-15 pa-3">
            <v-tab
              style="justify-content:left;"
              v-for="tab in tabs"
              :key="tab.id"
              :to="tab.route"
              exact
            >
              <v-icon left>{{ tab.icon }}</v-icon>
              {{ tab.name }}
            </v-tab>
          </v-tabs>
        </div>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col lg="8" cols="12"><router-view></router-view> </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  data() {
    return {
      // 왼쪽 thumbnail 관련
      ownerName: this.$route.params.username,
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
        {
          id: 0,
          name: "Main",
          route: `/SSALOG/${this.$route.params.username}/Main`,
          icon: "mdi-clipboard-text-play-outline"
        },
        {
          id: 1,
          name: "Solution",
          route: `/SSALOG/${this.$route.params.username}/Solution`,
          icon: "mdi-ballot"
        },
        {
          id: 2,
          name: "Profile",
          route: `/SSALOG/${this.$route.params.username}/Profile`,
          icon: "mdi-account"
        },
        {
          id: 3,
          name: "Following",
          route: `/SSALOG/${this.$route.params.username}/Following`,
          icon: "mdi-account-arrow-right"
        },
        {
          id: 4,
          name: "Follower",
          route: `/SSALOG/${this.$route.params.username}/Follower`,
          icon: "mdi-account-arrow-left"
        },
        {
          id: 5,
          name: "Star",
          route: `/SSALOG/${this.$route.params.username}/Star`,
          icon: "mdi-star"
        }
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
