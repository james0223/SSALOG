<template>
  <v-card flat height="70vh" class="mx-7 my-15">
    <v-toolbar-title>그룹 관리자</v-toolbar-title>
    <v-divider></v-divider>
    <v-list class="mb-5">
      <v-list-item v-for="manager in groupManager" :key="manager.nickname">
        <v-list-item-avatar>
          <v-img :src="manager.avatar"></v-img>
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title v-text="manager.nickname"></v-list-item-title>
        </v-list-item-content>
      </v-list-item>
    </v-list>
    <v-toolbar-title>그룹 멤버</v-toolbar-title>
    <v-divider></v-divider>
    <v-list class="mb-5">
      <v-list-item v-for="manager in groupManager" :key="manager.nickname">
        <v-list-item-avatar>
          <v-img :src="manager.avatar"></v-img>
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title v-text="manager.nickname"></v-list-item-title>
        </v-list-item-content>
        <v-list-item-icon>
          <v-icon v-if="manager">mdi-trash</v-icon>
        </v-list-item-icon>
      </v-list-item>
    </v-list>
  </v-card>
</template>

<script>
export default {
  name: "GroupList",
  data() {
    return {
      groupManager: [
        {
          nickname: "티모",
          avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg"
        },
        {
          nickname: "Gofeel8",
          avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg"
        },
        {
          nickname: "Gofeel8",
          avatar: "https://cdn.vuetifyjs.com/images/lists/1.jpg"
        }
      ]
    };
  }
};
</script>

<style scoped></style>
