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
                          <v-list-item-title>
                            <v-icon v-if="item.groupGrade === 'owner'" color="orange"
                              >mdi-star-circle-outline</v-icon
                            >
                            <v-icon v-else>mdi-square-small</v-icon>
                            {{ item.GroupName }}</v-list-item-title
                          >
                        </v-list-item-content>
                        <v-list-item-action>
                          <v-btn
                            depressed
                            small
                            :to="{ name: 'Member', params: { groupname: item.GroupName } }"
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
              <v-card flat class="mt-5 mx-2" height="70vh">
                <v-card-title>그룹 가입하기</v-card-title>
                <v-card-text>
                  <v-autocomplete
                    v-model="selectedGroup"
                    :items="searchGroup"
                    :loading="GroupListLoading"
                    :search-input.sync="GroupSearch"
                    hide-no-data
                    hide-selected
                    item-text="groupname"
                    prepend-icon="mdi-account-search"
                    placeholder="그룹을 검색해보세요"
                    label="그룹 검색"
                    return-object
                  >
                  </v-autocomplete>
                </v-card-text>
                <v-expand-transition>
                  <v-card v-if="selectedGroup">
                    <v-card-text>
                      <h5>그룹명</h5>
                      <p class="display-2 text--primary">{{ selectedGroup.groupname }}</p>
                      <h5>그룹설명</h5>
                      <h3>{{ selectedGroup.groupdes }}</h3>
                      <h5>그룹장</h5>
                      <p>{{ selectedGroup.groupowner }}</p>
                    </v-card-text>
                    <v-card-actions>
                      <v-btn cl text @click="applyGroup(selectedGroup.groupname)"
                        >그룹 신청하기</v-btn
                      >
                    </v-card-actions>
                  </v-card>
                </v-expand-transition>
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
      myGroup: [],
      // 그룹 생성 관련 data
      createGroupDialog: false,
      createGroupName: "",
      createGroupIntro: "",
      // 그룹 신청 관련 data
      GroupListLoading: false,
      searchGroup: [],
      selectedGroup: null,
      GroupSearch: null
      // https://github.com/vuetifyjs/vuetify/blob/master/packages/docs/src/examples/autocompletes/simple/api.vue
      // 저기 보면서 완성시킬 것
    };
  },
  methods: {
    async makeGroup() {
      try {
        const res = await this.$http.post(`${this.ServerURL}/user/grouping/make_group`, {
          groupname: this.createGroupName,
          groupdesc: this.createGroupIntro
        });
        // 그룹이 이미 존재할 때
        if (res.data === "is exist") {
          this.$store.commit("ShowAlert", {
            flag: true,
            msg: "그룹 이름이 중복됩니다",
            color: "error"
          });
        } else {
          this.$store.commit("ShowAlert", {
            flag: true,
            msg: "그룹 생성이 완료 되었습니다",
            color: "info"
          });
        }

        setTimeout(() => {
          this.$store.commit("ShowAlert", { flag: false, msg: "" });
        }, 2000);
        this.myGroup.push({ GroupName: this.createGroupName, groupGrade: "owner" });
        this.createGroupName = "";
        this.createGroupIntro = "";
        this.createGroupDialog = false;
      } catch (err) {
        // 이미 그룹이 존재함으로써 받는 에러코드
        console.error(err);
      }
    },
    async applyGroup(selectedGroup) {
      try {
        await this.$http.post(`${this.ServerURL}/user/grouping/apply_group`, null, {
          params: {
            groupname: selectedGroup
          }
        });
        this.$store.commit("ShowAlert", {
          flag: true,
          msg: "가입신청이 완료되었습니다",
          color: "info"
        });
        setTimeout(() => {
          this.$store.commit("ShowAlert", {
            flag: false,
            msg: ""
          });
        }, 700);
      } catch (err) {
        console.error(err);
      }
    },
    async getGroups() {
      try {
        const { data } = await this.$http.get(`${this.ServerURL}/user/grouping/myGrouplist`, {});
        const keys = Object.keys(data);
        // eslint-disable-next-line
        for (let i = 0; i < keys.length; i++) {
          const key = keys[i];
          const grade = data[key];
          this.myGroup.push({
            GroupName: key,
            groupGrade: grade
          });
        }
      } catch (e) {
        console.log(e);
      }
    }
  },
  computed: {
    ...mapState(["ServerURL"])
  },
  mounted() {
    this.getGroups();
  },
  watch: {
    // eslint-disable-next-line
    selectedGroup(val) {
      if (val != null) console.log(val);
    },
    // eslint-disable-next-line
    async GroupSearch(val) {
      console.log(val);
      if (this.searchGroup.length > 0) return;
      this.GroupListLoading = true;

      const res = await this.$http.get(`${this.ServerURL}/newuser/grouping/search_group`, {
        params: {
          direction: "ASC",
          page: 1,
          size: 999
        }
      });
      this.searchGroup = res.data;
      this.GroupListLoading = false;
    }
  }
};
</script>

<style scoped></style>
