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
    <v-card-actions v-if="nickname">
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
            @click="$router.push({ name: 'SSalogMain', params: { nickname: comment.nickname } })"
            ><h3>{{ comment.nickname }}</h3></v-list-item-title
          >
          <v-list-item-subtitle>{{ comment.time }}</v-list-item-subtitle>
        </v-list-item-content>
        <v-spacer></v-spacer>
        <v-btn icon @click="putchange(idx)" v-if="username === comment.username">
          <v-icon>mdi-lead-pencil</v-icon>
        </v-btn>
        <v-btn color="red" icon @click="isDelete = true" v-if="username === comment.username">
          <v-icon>mdi-window-close</v-icon>
        </v-btn>
        <v-dialog v-model="isDelete" max-width="60vh">
          <v-card>
            <v-card-title>
              <h5>댓글 삭제</h5>
            </v-card-title>
            <v-card-text>삭제하시겠어요?</v-card-text>
            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="red" text @click="deleteComment(idx)">삭제</v-btn>
              <v-btn color="grey" text @click="isDelete = false">취소</v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </v-list-item>
      <v-card-text v-show="isPut === idx">
        <v-textarea outlined solo name="input-7-4" v-model="aftComment"></v-textarea>
        <v-card-actions
          ><v-spacer></v-spacer><v-btn @click="putComment(idx)" color="info" text>수정</v-btn
          ><v-btn @click="isPut = null" text>닫기</v-btn></v-card-actions
        >
      </v-card-text>
      <v-card-text v-show="isPut !== idx" class="mb-5">
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
      isPut: null,
      defComment: null,
      aftComment: null,
      isDelete: false,
      myComment: "",
      comments: []
    };
  },
  computed: mapState(["ServerURL", "nickname", "username"]),
  mounted() {
    this.getComments();
  },
  methods: {
    putchange(idx) {
      this.befComment = this.comments[idx].message;
      this.aftComment = this.befComment;
      this.isPut = idx;
    },
    async putComment(idx) {
      const { ServerURL } = this;
      try {
        await axios.put(`${ServerURL}/user/post/put_comment`, null, {
          params: {
            id: this.comments[idx].uniqueid,
            message: this.aftComment
          }
        });
        this.isPut = null;
        this.comments[idx].message = this.aftComment;
      } catch (e) {
        console.error(e);
      }
    },
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
      this.isDelete = false;
    }
  }
};
</script>

<style scoped></style>
