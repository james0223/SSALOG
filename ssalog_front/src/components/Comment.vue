<template>
  <v-main>
    <v-card flat>
      <v-container>
        <v-row>0개의 댓글</v-row>
        <v-row>
          <v-textarea
            class="mt-4"
            solo
            auto-grow
            outlined
            label="댓글을 작성하세요."
            background-color="white"
          ></v-textarea>
        </v-row>
        <v-row justify="end">
          <v-btn class="white--text" color="rgb(32, 201, 151)">댓글 작성</v-btn>
        </v-row>
      </v-container>
    </v-card>
  </v-main>
</template>

<script>
export default {
  name: "Comment",
  methods: {
    createComment() {
      console.log("do create");
    }
  }
};
</script>

<style scoped></style>
