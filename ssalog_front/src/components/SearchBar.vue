<template>
  <v-container>
    <v-row align="center">
      <v-col class="d-flex" cols="3">
        <v-select v-model="selectedCatagory" :items="category" label="검색유형"></v-select>
      </v-col>
      <v-col class="d-flex" cols="9">
        <v-text-field v-model="search" label="검색어를 입력해주세요" append-icon="mdi-magnify">
        </v-text-field>
      </v-col>
    </v-row>
    <v-btn color="primary" @click="goSearch">
      검색
    </v-btn>
  </v-container>
</template>

<script>
export default {
  name: "SearchBar",
  data() {
    return {
      category: ["문제번호", "문제제목", "문제유형", "회원"],
      selectedCatagory: "",
      search: "",
    };
  }
}
</script>

<style></style>
