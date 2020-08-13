<template>
  <v-container fluid>
    <v-row justify="center" no-gutters>
      <v-col cols="12">
        <v-card class="mx-5 mt-15 mp-2" height="75vh">
          <v-row>
            <v-col cols="8">
              <v-card class="mt-5 mx-2" flat height="70vh">
                <v-card-title>
                  나의 그룹
                  <v-spacer></v-spacer>
                  <v-btn
                    tile
                    outlined
                    class="ma-2"
                    color="primary"
                    @click.stop="createGroupDialog = true"
                  >
                    <v-icon>mdi-plus</v-icon>그룹 만들기
                  </v-btn>
                  <v-dialog v-model="createGroupDialog" max-width="700">
                    <v-card class="px-5">
                      <v-card-title class="headline">그룹 만들기</v-card-title>
                      <v-text-field class="px-5" label="그룹 이름" v-model="createGroupName">
                        <v-icon slot="prepend">mdi-format-title</v-icon>
                      </v-text-field>
                      <v-text-field class="px-5" label="그룹 한마디" v-model="createGroupIntro">
                        <v-icon slot="prepend">mdi-chat-processing</v-icon>
                      </v-text-field>
                      <v-card-actions>
                        <v-spacer></v-spacer>
                        <v-btn color="secondary" text @click="createGroupDialog = false">
                          취소
                        </v-btn>
                        <v-btn color="secondary" text @click="makeGroup">
                          그룹 만들기
                        </v-btn>
                      </v-card-actions>
                    </v-card>
                  </v-dialog>
                </v-card-title>
                <v-card-text class="pt-4">
                  <v-subheader>내가 가입된 그룹</v-subheader>
                  <v-divider></v-divider>
                  <v-virtual-scroll :items="myGroup" :item-height="50" height="500">
                    <template v-slot="{ item }">
                      <v-list-item>
                        <v-list-item-content>
                          <v-list-item-title>{{ item.GroupName }}</v-list-item-title>
                        </v-list-item-content>
                        <v-list-item-action>
                          <v-btn
                            depressed
                            small
                            :to="{ name: 'Group', params: { groupname: item.GroupName } }"
                          >
                            그룹 페이지 가기
                            <v-icon color="secondary" right>
                              mdi-open-in-new
                            </v-icon>
                          </v-btn>
                        </v-list-item-action>
                      </v-list-item>
                    </template>
                  </v-virtual-scroll>
                </v-card-text>
              </v-card>
            </v-col>
            <v-divider vertical></v-divider>
            <v-col cols="3.5">
              <v-card class="mt-5 mx-2" height="70vh">
                <v-card-title>그룹 가입하기</v-card-title>
                <v-card-text>
                  <v-text-field
                    label="그룹 검색하기"
                    append-icon="mdi-magnify"
                    v-model="searchedGroup"
                  ></v-text-field>
                  <v-btn text>지원하기</v-btn>
                </v-card-text>
              </v-card>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "GroupList",
  data() {
    return {
      // 초기 세팅
      ownerName: this.$route.params.nickname,
      ownerEmail: "",
      myGroup: [
        {
          GroupName: "으으잉"
        },
        {
          GroupName: "호다닥"
        }
      ],
      searchGroup: [],
      // 그룹 생성 관련 data
      createGroupDialog: false,
      createGroupName: "",
      createGroupIntro: "",
      // 그룹 신청 관련 data
      searchedGroup: ""
    };
  },
  methods: {
    async makeGroup() {
      try {
        await this.$http.post(`${this.ServerURL}/user/grouping/make_group`, {
          groupname: this.createGroupName,
          groupdesc: this.createGroupIntro
        });
        this.$store.commit("ShowAlert", {
          flag: true,
          msg: "그룹 생성이 완료 되었습니다",
          color: "info"
        });
        setTimeout(() => {
          this.$store.commit("ShowAlert", { flag: false, msg: "" });
        }, 2000);
        this.myGroup.push({ GroupName: this.createGroupName });
        this.createGroupName = "";
        this.createGroupIntro = "";
        this.createGroupDialog = false;
      } catch (err) {
        // 이미 그룹이 존재함으로써 받는 에러코드
        console.error(err);
      }
    },
    async applyGroup() {
      try {
        await this.$http.post(`${this.ServerURL}/user/grouping/apply_group`, null, {});
      } catch (err) {
        console.error(err);
      }
    },
    // mounted 에서 가장 첫번째로 실행.
    async getownerEmail() {
      try {
        const res = await this.$http.get(`${this.ServerURL}/newuser/search/find_username`, {
          params: {
            nickname: this.ownerName
          }
        });
        this.ownerEmail = res.data;
      } catch (e) {
        console.error(e);
      }
    }
    // async getGroups() {
    //   try {
    //     await this.$http.get(`${this.ServerURL}`);
    //     // 여기에 param 으로 ownerEmail 보내서 가입한 group 목록을 불러오고
    //     // mounted 에서 두번째로 실행
    //   }
    // }
  },
  computed: mapState(["ServerURL"])
};
</script>

<style scoped></style>
