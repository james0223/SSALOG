<template>
  <v-app-bar flat height="100vh" color="white" class="mx-auto">
    <v-img :src="require('@/assets/images/header-logo.jpg')" max-width="5vw" max-height="10vh" />
    <v-btn text class="mx-10">SSALOG</v-btn>
    <v-btn text class="mx-10">GROUP</v-btn>
    <v-btn text class="mx-10">
      <b>검색</b>
    </v-btn>
    <v-btn text class="mx-10">Community</v-btn>
    <v-menu v-model="showMenu" offset-y>
      <template v-slot:activator="{ on, attrs }">
        <v-card v-bind="attrs" v-on="on" flat width="8vw">
          <v-row>
            <v-col cols="4">
              <v-avatar>
                <img src="https://cdn.vuetifyjs.com/images/john.jpg" alt="John" />
              </v-avatar>
            </v-col>
            <v-col cols="8">
              <div class="justify-space-between subtitle">{{ name }}</div>
              <div class="justify-space-between caption">님 환영합니다</div>
            </v-col>
          </v-row>
        </v-card>
      </template>
      <v-list>
        <v-list-item v-for="(item, index) in items" :key="index">
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
    <v-btn text>빼엑빼엑</v-btn>
    <v-divider vertical></v-divider>
    <v-btn text>ㅇㄴㅇㄴ</v-btn>
    <v-divider vertical></v-divider>
    <v-btn text>ㄴㅇㄴㅇ</v-btn>
  </v-app-bar>
</template>

<script>
export default {
  data: () => ({
    showMenu: false,
    items: [{ title: "회원정보" }, { title: "로그아웃" }],
    name: "utaein"
  })
};
</script>

<style scoped></style>
