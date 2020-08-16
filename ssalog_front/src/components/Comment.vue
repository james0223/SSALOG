<template>
  <v-card flat color="transparent">
    <v-divider></v-divider>
    <v-card-title>{{ comments.length }}개의 댓글</v-card-title>
    <v-card-actions v-if="nickname">
      <v-textarea
        solo
        auto-grow
        outlined
        v-model="myComment"
        label="건전한 댓글을 달아주세요 :)"
        background-color="white"
      ></v-textarea>
    </v-card-actions>
    <v-card-actions>
      <v-spacer></v-spacer>
      <v-btn dark color="rgb(32, 201, 151)" @click="createComment()">댓글 작성</v-btn>
    </v-card-actions>
    <v-card-text class="" v-for="(comment, idx) in comments" :key="idx">
      <v-list-item>
        <v-list-item-avatar color="grey">
          <img :src="`${$store.state.ImgURL}/${comment.imgpath}`" />
        </v-list-item-avatar>
        <v-list-item-content>
          <v-list-item-title
            style="cursor: pointer;"
            class="headline"
            @click="$router.push({ name: 'SSalogMain', params: { username: comment.userid } })"
            >{{ comment.nickname }}</v-list-item-title
          >
          <v-list-item-subtitle>{{ comment.time }}</v-list-item-subtitle>
        </v-list-item-content>
        <v-spacer></v-spacer>
        <v-btn icon @click="deleteComment(idx)" v-if="$store.state.username === comment.userid">
          <v-icon>mdi-window-close</v-icon>
        </v-btn>
      </v-list-item>
      <v-card-text class="mb-5">
        <div class="text--primary">{{ comment.message }}</div>
      </v-card-text>
      <v-divider v-if="idx < comments.length - 1"></v-divider>
    </v-card-text>
  </v-card>
</template>

<script>
import axios from "axios";
import { mapState } from "vuex";

export default {
  name: "Comment",
  data() {
    return {
      myComment: "",
      comments: []
    };
  },
  computed: mapState(["ServerURL", "nickname"]),
  mounted() {
    this.getComments();
  },
  methods: {
    async getComments() {
      const { ServerURL } = this;
      try {
        const res = await axios.get(`${ServerURL}/newuser/post/get_comment`, {
          params: {
            Scoring: this.$route.params.id
          }
        });
        this.comments = res.data;
      } catch (e) {
        console.error(e);
      }
    },
    async createComment() {
      // eslint-disable-next-line
      const { ServerURL } = this;
      console.log(this.myComment);
      try {
        if (this.myComment !== "") {
          await axios.post(
            `${ServerURL}/user/post/write_comment`,
            {},
            {
              params: {
                Scoring: this.$route.params.id,
                Comment: this.myComment
              }
            }
          );
          this.myComment = "";
          this.getComments();
        }
      } catch (e) {
        console.error(e);
      }
    },
    async deleteComment(index) {
      await this.$http.delete(`${this.ServerURL}/user/post/delete_comment`, {
        params: {
          id: this.comments[index].uniqueid
        }
      });
      this.comments.splice(index, 1);
    }
  }
};
</script>

<style scoped></style>
