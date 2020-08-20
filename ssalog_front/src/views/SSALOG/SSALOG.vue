<template>
  <v-card
    :dark="isDark"
    :outlined="isDark"
    height="100%"
    elevation="8"
    :width="imageHeight ? '95vw' : '76vw'"
    class="mx-auto mt-16 px-3 card_mother"
  >
    <v-row>
      <v-col class="d-none d-sm-flex" cols="2">
        <v-card height="105vh"></v-card>
        <div class="absolute_card">
          <v-card
            shaped
            :dark="!isDark"
            elevation="12"
            class="flex-column align-center pt-3 profile_card"
          >
            <v-btn
              @click="$router.push({ name: 'SSalogMain' })"
              style="text-transform: none !important;"
              text
              block
              >{{ ownerName }}</v-btn
            >
            <v-card-title class="px-0" id="relative_wrapper">
              <v-avatar size="9vw" class="mt-2 mx-auto">
                <v-img :src="writerThumbnail"></v-img>
              </v-avatar>
              <v-btn
                v-if="nickname && nickname !== ownerName"
                class="thumbnailplus d-none d-lg-flex"
                height="40"
                width="40"
                fab
                @click="followclick"
                title="follow"
              >
                <v-icon :disabled="!isfollow" color="red">mdi-heart</v-icon>
              </v-btn>
              <v-dialog
                v-if="nickname && nickname === ownerName"
                v-model="thumbnailDialog"
                max-width="400px"
              >
                <template v-slot:activator="{ on, attrs }">
                  <v-btn
                    fab
                    v-bind="attrs"
                    v-on="on"
                    @click="imageUrl = writerThumbnail"
                    class="thumbnailplus d-none d-lg-flex"
                    small
                    dark
                    color="dark"
                  >
                    <v-icon small dark>mdi-camera</v-icon>
                  </v-btn>
                </template>
                <v-card :outlined="isDark" :dark="isDark" style="padding:16px;">
                  <v-card-title>
                    <span class="headline">프로필사진 관리</span>
                  </v-card-title>
                  <v-row>
                    <v-col cols="12" sm="12" md="12">
                      <v-file-input
                        prepend-icon="mdi-camera"
                        style="display: inline"
                        placeholder="사진선택"
                        accept="image/png, image/jpeg, image/jpg"
                        @change="onChangeImages"
                        outlined
                        clear-icon
                      ></v-file-input>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col cols="2"></v-col>
                    <v-avatar size="240">
                      <v-img :src="imageUrl" />
                    </v-avatar>
                  </v-row>
                  <v-row>
                    <v-col cols="6">
                      <v-list rounded>
                        <v-list-item-group v-model="ThumbnailSelect" :color="ColorSet.Prime">
                          <v-list-item @click="uploadImage()">
                            <v-list-item-content>
                              <v-list-item-title class="d-flex justify-center"
                                >저장</v-list-item-title
                              >
                            </v-list-item-content>
                          </v-list-item>
                        </v-list-item-group>
                      </v-list>
                    </v-col>
                    <v-col cols="6">
                      <v-list rounded>
                        <v-list-item-group v-model="ThumbnailSelect" color="red">
                          <v-list-item @click="thumbnailDialog = false">
                            <v-list-item-content>
                              <v-list-item-title class="d-flex justify-center"
                                >취소</v-list-item-title
                              >
                            </v-list-item-content>
                          </v-list-item>
                        </v-list-item-group>
                      </v-list>
                    </v-col>

                    <v-col cols="3"></v-col>
                    <v-col cols="6">
                      <v-list rounded>
                        <v-list-item-group v-model="ThumbnailSelect" color="green">
                          <v-list-item @click="deleteImage()">
                            <v-list-item-content>
                              <v-list-item-title class="d-flex justify-center"
                                >기본이미지</v-list-item-title
                              >
                            </v-list-item-content>
                          </v-list-item>
                        </v-list-item-group>
                      </v-list>
                    </v-col>
                  </v-row>
                </v-card>
              </v-dialog>
            </v-card-title>
            <v-divider></v-divider>
            <div class="d-none d-lg-flex" id="showLevel" title="레벨">
              <v-progress-circular
                :rotate="-90"
                :size="40"
                width="3"
                :value="exp"
                :color="levelColor"
              >
                <v-icon :color="levelColor" large>mdi-numeric-{{ level }}</v-icon>
              </v-progress-circular>
            </div>
            <v-row justify="center" no-gutters class="d-none d-lg-flex py-3">
              <v-col cols="4" title="following" class="text-center">
                <v-icon>mdi-account-heart</v-icon>
              </v-col>
              <v-col cols="4" title="follower" class="text-center">
                <v-icon>mdi-account-arrow-left</v-icon>
              </v-col>
              <v-col cols="4" title="scrap" class="text-center">
                <v-icon>mdi-star</v-icon>
              </v-col>
              <v-col cols="4" title="following" class="text-center">{{ following }}</v-col>
              <v-col cols="4" title="follower" class="text-center">{{ follower }}</v-col>
              <v-col cols="4" title="scrap" class="text-center">{{ scrapSu }}</v-col>
            </v-row>
          </v-card>
          <v-tabs vertical :color="ColorSet.Prime" background-color="transparent" class="my-1">
            <v-tab
              style="justify-content:left; height:6vh"
              v-for="tab in tabs"
              :key="tab.id"
              :to="tab.route"
              exact
              :class="{ 'd-none': tab.flag }"
            >
              <v-icon class="d-none d-lg-flex" left>{{ tab.icon }}</v-icon>
              {{ tab.name }}
            </v-tab>
          </v-tabs>
        </div>
      </v-col>
      <v-col cols="12" sm="10">
        <router-view v-on:decrement="decrementFollow" v-on:updateScrap="getInfo"></router-view>
      </v-col>
    </v-row>
  </v-card>
</template>

<script>
import axios from "axios";
import { mapState, mapMutations } from "vuex";
import "@/assets/Main.css";

export default {
  data() {
    return {
      // 왼쪽 thumbnail 관련
      total: 0,
      level: 0,
      exp: 0,
      scrapSu: 0,
      follower: 0,
      following: 0,
      isfollow: false,
      ownerName: this.$route.params.nickname,
      thumbnailDialog: false,
      ThumbnailSelect: 0,
      writerThumbnail: null,
      imageUrl: null,
      imageFile: null,
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
          name: "Home",
          route: `/SSALOG/${this.$route.params.nickname}/Main`,
          icon: "mdi-home"
        },
        {
          id: 1,
          name: "Profile",
          route: `/SSALOG/${this.$route.params.nickname}/Profile`,
          icon: "mdi-clipboard-account-outline"
        },
        {
          id: 2,
          name: "Solution",
          route: `/SSALOG/${this.$route.params.nickname}/SolutionList`,
          icon: "mdi-ballot"
        },
        {
          id: 3,
          name: "Scrap",
          route: `/SSALOG/${this.$route.params.nickname}/Scrap`,
          icon: "mdi-star"
        },
        {
          id: 4,
          name: "Follow",
          route: `/SSALOG/${this.$route.params.nickname}/Follow`,
          icon: "mdi-account-heart"
        },
        {
          id: 5,
          name: "Group",
          route: `/SSALOG/${this.$route.params.nickname}/GroupList`,
          icon: "mdi-account-group",
          flag: this.$store.state.nickname !== this.$route.params.nickname
        },
        {
          id: 6,
          name: "Setting",
          route: `/SSALOG/${this.$route.params.nickname}/UserSetting`,
          icon: "mdi-settings",
          flag: this.$store.state.nickname !== this.$route.params.nickname
        }
      ]
    };
  },
  computed: {
    ...mapState([
      "ServerURL",
      "ColorSet",
      "ImgURL",
      "userThumbnail",
      "username",
      "nickname",
      "isDark"
    ]),
    levelColor() {
      switch (this.level) {
        case 0:
          return "blue-grey darken-2";
        case 1:
          return "light-blue accent-4";
        case 2:
          return "purple lighten-2";
        default:
          return "teal accent-3";
      }
    },
    imageHeight() {
      switch (this.$vuetify.breakpoint.name) {
        case "xs":
          return true;
        case "sm":
          return true;
        case "md":
          return false;
        case "lg":
          return false;
        case "xl":
          return false;
        default:
          return false;
      }
    }
  },
  methods: {
    ...mapMutations(["ShowAlert"]),
    decrementFollow() {
      this.following = this.following - 1;
    },
    async followclick() {
      if (this.$store.state.nickname != null) {
        if (!this.isfollow) {
          await axios
            .post(
              `${this.$store.state.ServerURL}/user/follow/do_follow`,
              {},
              { params: { following: this.ownerName } }
            )
            .then(() => {
              this.isfollow = !this.isfollow;
              this.follower = this.follower + 1;
            })
            .catch(function(error) {
              console.log(error);
            });
        } else {
          await axios
            .delete(`${this.ServerURL}/user/follow/cancel_follow`, {
              params: { following: this.ownerName }
            })
            .then(() => {
              this.follower = this.follower - 1;
              this.isfollow = !this.isfollow;
            })
            .catch(function(error) {
              console.log(error);
            });
        }
      } else {
        // alert("로그인이 필요합니다.");
        this.ShowAlert({
          flag: true,
          msg: "로그인이 필요한 기능입니다.",
          color: "cyan darken-2"
        });
        setTimeout(() => {
          this.ShowAlert({ flag: false, msg: "" });
        }, 2000);
      }
    },
    onChangeImages(e) {
      const file = e;
      this.imageUrl = URL.createObjectURL(file);
      this.imageFile = file;
    },
    deleteImage() {
      try {
        axios.delete(`${this.$store.state.ServerURL}/user/delete_profile_img`);
        this.$store.dispatch("Thumbnail", this.$store.state.username);
        // window.location.reload();

        this.thumbnailDialog = false;
      } catch (e) {
        console.error(e);
      }
    },
    uploadImage() {
      const formData = new FormData();
      formData.append("filename", this.imageFile);
      axios
        .post(`${this.ServerURL}/user/upload_profile_img`, formData, {
          headers: {
            "Content-Type": "multipart/form-data",
            Authorization: `Bearer ${this.$store.state.accessToken}`
          }
        })
        .catch(function(error) {
          // handle error
          console.log(error);
        })
        .then(() => {
          this.$store.dispatch("Thumbnail", this.$store.state.username);
          // window.location.reload();
        });
    },
    getFollowFlag() {
      axios
        .get(`${this.$store.state.ServerURL}/user/follow/is_follow`, {
          params: {
            following: this.$route.params.nickname
          }
        })
        .then(res => {
          // console.log(res.data);
          this.isfollow = res.data;
        })
        .catch(function(error) {
          // handle error
          console.log(error);
          console.error(error);
        });
    },
    async getScrapNum() {
      try {
        const res = await axios.get(
          `${this.$store.state.ServerURL}
    /newuser/scrap/scraped_su`,
          {
            params: {
              nickname: this.$route.params.nickname
            }
          }
        );
        this.scrapSu = res.data;
      } catch (e) {
        console.error(e);
      }
    },
    async getInfo() {
      try {
        const info = await this.$http.get(`${this.$store.state.ServerURL}/newuser/user_info`, {
          params: {
            nickname: this.$route.params.nickname
          }
        });

        // console.log(info.data);
        this.level = info.data.level;
        this.exp = info.data.exp;
        this.scrapSu = info.data.scraped_num;
        this.following = info.data.follow_num;
        this.follower = info.data.following_num;
        this.writerThumbnail = `${this.ImgURL}${info.data.imgpath}`;
        this.imageUrl = this.writerThumbnail;
      } catch (e) {
        console.error(e);
      }
    }
  },
  async created() {
    await this.getInfo();
    if (this.$store.state.nickname != null) {
      await this.getFollowFlag();
    }
  }
};
</script>
<style></style>
