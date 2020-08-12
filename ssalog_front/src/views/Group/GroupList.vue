<template>
  <v-container fluid>
    <v-row justify="center" no-gutters>
      <v-col cols="10">
        <v-card class="mx-auto mt-15 mp-2" height="75vh">
          <v-row>
            <v-col cols="8">
              <v-card class="mt-5 mx-2" flat height="70vh">
                <v-card-title>
                  나의 그룹
                  <v-spacer></v-spacer>
                  <v-btn tile outlined class="ma-2" color="primary">
                    <v-icon>mdi-plus</v-icon>그룹 만들기
                  </v-btn>
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
                          <v-btn depressed small>
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
                  <v-text-field label="그룹 검색하기" append-icon="mdi-magnify"></v-text-field>
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
export default {
  name: "GroupList",
  data() {
    return {
      myGroup: [
        {
          GroupName: "으으잉"
        },
        {
          GroupName: "호다닥"
        },
        {
          GroupName: "호다닥"
        },
        {
          GroupName: "호다닥"
        },
        {
          GroupName: "호다닥"
        },
        {
          GroupName: "호다닥"
        },
        {
          GroupName: "호다닥"
        },
        {
          GroupName: "호다닥"
        },
        {
          GroupName: "호다닥"
        },
        {
          GroupName: "호다닥"
        },
        {
          GroupName: "호다닥"
        }
      ],
      searchGroup: []
    };
  }
};
</script>

<style scoped></style>
