<template>
  <v-container>
    <v-row>
      <v-col cols="2 pr-6" style="border-right:1px solid gray">
        <div id="relative_wrapper" align="center">
          <v-avatar size="100" class="mt-8">
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
                id="thumbnailplus"
                outlined
                icon
                dark
                color="indigo"
              >
                <v-icon small dark>mdi-camera</v-icon>
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
          <v-row justify="center" class="mt-2" style="border-bottom : 1px dashed grey">
            <div id="showLevel" title="level">
              <v-progress-circular
                class=" mr-6"
                :rotate="-90"
                :size="40"
                :width="9"
                :value="exp"
                color="primary"
              >
                <v-icon color="blue" medium>mdi-numeric-{{ level }}-circle-outline</v-icon>
              </v-progress-circular>
            </div>
            <div class="mt-4 font-weight-medium ">{{ ownerName }}</div>
            <template v-if="$store.state.nickname !== $route.params.nickname">
              <v-btn
                id="thumbnailplus"
                height="40"
                width="40"
                icon
                @click="followclick"
                title="follow"
              >
                <v-icon :disabled="!isfollow" color="red" size="50">mdi-heart</v-icon>
              </v-btn>
            </template>
          </v-row>
          <v-row justify="center" style="border-bottom : 1px dashed grey ">
            <v-col title="following"><v-icon>mdi-account-heart</v-icon>{{ following }}</v-col>
            <v-col title="follower"><v-icon>mdi-account-arrow-left</v-icon>{{ follower }}</v-col>
            <v-col title="scrap"><v-icon>mdi-star</v-icon>{{ scrapSu }}</v-col>
          </v-row>

          <v-tabs vertical class="my-5">
            <v-tab
              style="justify-content:left;"
              v-for="tab in tabs"
              :key="tab.id"
              :to="tab.route"
              exact
              :class="{ 'd-none': tab.flag }"
            >
              <v-icon left>{{ tab.icon }}</v-icon>
              {{ tab.name }}
            </v-tab>
          </v-tabs>
        </div>
      </v-col>
      <v-col cols="undefined"
        ><router-view v-on:decrement="decrementFollow" v-on:updateScrap="getScrapNum"></router-view>
      </v-col>
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
          icon: "mdi-account-group"
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
  computed: mapState(["ServerURL", "ImgURL", "userThumbnail"]),
  watch: {
    total() {
      this.level = Math.floor(this.total / 20);
      this.exp = ((this.total % 20) / 20) * 100;
    }
  },
  methods: {
    decrementFollow() {
      this.following = this.following - 1;
    },
    async followclick() {
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
            alert("로그인이 필요합니다.");
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
    },
    async getFollowNum() {
      try {
        const res = await axios.get(
          `${this.$store.state.ServerURL}
/newuser/follow/myfollow`,
          {
            params: {
              nickname: this.$route.params.nickname
            }
          }
        );
        // this.users = res.data;
        // console.dir(Object.keys(res.data).length);
        this.following = Object.keys(res.data).length;
        // console.log(res);
      } catch (e) {
        console.error(e);
      }

      try {
        const res2 = await axios.get(
          `${this.$store.state.ServerURL}
/newuser/follow/myfollowing`,
          {
            params: {
              nickname: this.$route.params.nickname
            }
          }
        );
        // this.users = res2.data;
        // console.dir(Object.keys(res2.data).length);
        this.follower = Object.keys(res2.data).length;
        // console.log(res2);
      } catch (e) {
        console.error(e);
      }
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
    async getLevel() {
      try {
        const owneremail = await this.$http.get(
          `${this.$store.state.ServerURL}/newuser/search/find_username`,
          {
            params: {
              nickname: this.$route.params.nickname // 바꿔야함
            }
          }
        );
        const res = await axios.get(`${this.$store.state.ServerURL}/newuser/search/to_username`, {
          params: {
            direction: "ASC",
            page: 1,
            size: 5000,
            username: owneremail.data // 수정필요
          }
        });
        // console.log(Object.keys(res.data.content).length);
        this.total = Object.keys(res.data.content).length;
      } catch (e) {
        console.error(e);
      }
    }
  },
  created() {
    this.getThumbnail();
    this.getFollowNum();
    this.getScrapNum();
    if (this.$store.state.nickname != null) {
      this.getFollowFlag();
    }
    this.getLevel();
  }
};
</script>
<style>
#relative_wrapper {
  position: relative;
}
#thumbnailplus {
  position: absolute;
  top: 100px;
  left: 70%;
}
#showLevel {
  position: absolute;
  top: 140px;
  left: -5px;
}
</style>
