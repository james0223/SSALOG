<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col lg="2">
        <div id="relative_wrapper">
          <v-avatar size="240" class="mt-8">
            <img :src="writerThumbnail" />
            <!--변경해줘야할듯-->
          </v-avatar>
          <v-dialog
            v-if="$store.state.nickname === $route.params.nickname"
            v-model="thumbnailDialog"
            max-width="400px"
          >
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                v-bind="attrs"
                v-on="on"
                @click="imageUrl = writerThumbnail"
                class="mx-2"
                id="thumbnailplus"
                fab
                dark
                color="indigo"
              >
                <v-icon dark>mdi-camera</v-icon>
              </v-btn>
            </template>
            <v-card style="padding:16px;">
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
                    <v-list-item-group v-model="ThumbnailSelect" color="blue">
                      <v-list-item @click="uploadImage()">
                        <v-list-item-content>
                          <v-list-item-title class="d-flex justify-center">저장</v-list-item-title>
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
                          <v-list-item-title class="d-flex justify-center">취소</v-list-item-title>
                        </v-list-item-content>
                      </v-list-item>
                    </v-list-item-group>
                  </v-list>
                </v-col>

                <v-col cols="3"> </v-col>
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
          <v-row justify="center" class="mt-2" style="width:240px; border-bottom : 1px dashed grey">
            <div class="ma-2 font-weight-bold"><v-icon>mdi-account</v-icon> {{ ownerName }}</div>
            <template v-if="$store.state.nickname !== $route.params.nickname">
              <v-btn class="ml-3" large icon @click="followclick">
                <v-icon :disabled="!isfollow" color="red">mdi-heart</v-icon>
              </v-btn>
            </template>
          </v-row>
          <v-row>
            <v-col cols="5"><v-icon class="mr-3">mdi-account-multiple</v-icon>123/123</v-col>
            <v-col><v-icon class="mr-3">mdi-star</v-icon>232</v-col>
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
import axios from "axios";
import { mapState } from "vuex";

export default {
  data() {
    return {
      // 왼쪽 thumbnail 관련
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
          icon: "mdi-account-group"
        }
      ]
    };
  },
  computed: mapState(["ServerURL", "ImgURL", "userThumbnail"]),
  methods: {
    followclick() {
      // this.isfollow = !this.isfollow;
      if (this.isfollow) {
        // console.log("팔로우걸기");
        axios
          .post(
            `${this.$store.state.ServerURL}/user/follow/do_follow`,
            {},
            { params: { following: this.ownerName } }
          )
          .then(() => {
            // console.log("성공");
            this.isfollow = !this.isfollow;
          })
          .catch(function(error) {
            console.log(error);
            alert("로그인이 필요합니다.");
          });
      } else {
        // console.log("팔로우취소");
        axios
          .delete(`${this.ServerURL}/user/follow/cancel_follow`, {
            params: { following: this.ownerName }
          })
          .then(() => {
            console.log("취소성공");
            this.isfollow = !this.isfollow;
          })
          .catch(function(error) {
            console.log(error);
            alert("로그인이 필요합니다.");
          });
      }
    },
    onChangeImages(e) {
      const file = e;
      this.imageUrl = URL.createObjectURL(file);
      this.imageFile = file;
    },
    async getThumbnail() {
      const res = await axios.get(`${this.ServerURL}/newuser/get_profile_img_nick`, {
        params: {
          nickname: this.$route.params.nickname
        }
      });
      this.writerThumbnail = `${this.ImgURL}${res.data}`;
      this.imageUrl = this.writerThumbnail;
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
        .catch(function(error) {
          // handle error
          console.log(error);
        })
        .then(res => {
          // console.log(res.data);
          this.isfollow = res.data;
        });
    }
  },
  created() {
    this.getThumbnail();
    this.getFollowFlag();
  }
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
