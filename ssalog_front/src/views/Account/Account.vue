<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col lg="3" cols="12">
        <UserNav></UserNav>
      </v-col>
      <v-divider vertical></v-divider>
      <v-col lg="6" cols="12">
        <v-card flat height="60vh" class="pa-8">
          <h2 class="font-weight-light mb-3">비밀번호 변경</h2>
          <v-divider></v-divider>
          <v-row>
            <body-1 class="mt-3 ml-3">비밀번호를 변경하기 전에 다시 한번 확인합니다.</body-1>
          </v-row>
          <v-row>
            <v-btn class="mt-5 ml-3 mb-12">비밀번호 변경</v-btn>
          </v-row>
          <h2 class="font-weight-light mb-3 warn">계정 삭제</h2>
          <v-divider></v-divider>
          <v-row>
            <body-1 class="mt-3 ml-3"
              >계정을 삭제하시면 <b>복구하실 수 없습니다</b>. 신중하게 결정해 주세요</body-1
            >
          </v-row>
          <v-row>
            <v-btn class="mt-5 ml-3"><body-1 class="warn">계정 삭제</body-1></v-btn>
          </v-row>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import UserNav from "@/components/UserNav.vue";

export default {
  components: {
    UserNav
  }
};
</script>

<style scoped>
.warn {
  color: red;
}
</style>
