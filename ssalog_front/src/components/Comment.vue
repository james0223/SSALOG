<template>
  <v-main>
    <v-card flat>
      <v-container>
        <v-row>{{ comments.length }}개의 댓글</v-row>
        <v-row>
          <v-textarea
            class="mt-4"
            solo
            auto-grow
            outlined
            v-model="myComment"
            label="댓글을 작성하세요."
            background-color="white"
          ></v-textarea>
        </v-row>
        <v-row justify="end">
          <v-btn class="white--text mb-8" color="rgb(32, 201, 151)" @click="createComment()"
            >댓글 작성</v-btn
          >
        </v-row>
      </v-container>
    </v-card>
    <v-flex v-for="(comment, idx) in comments" :key="idx">
      <v-card flat class="mt-3">
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
        <v-divider></v-divider>
      </v-card>
    </v-flex>
  </v-main>
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
  computed: mapState(["ServerURL"]),
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
