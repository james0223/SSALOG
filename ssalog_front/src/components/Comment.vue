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
            v-model="myComment"
            label="댓글을 작성하세요."
            background-color="white"
          ></v-textarea>
        </v-row>
        <v-row justify="end">
          <v-btn class="white--text mb-8" color="rgb(32, 201, 151)" @click="createComment(this)"
            >댓글 작성</v-btn
          >
        </v-row>
      </v-container>
    </v-card>
    <v-flex v-for="comment in comments" :key="comment">
      <v-card flat>
        <v-list-item>
          <v-list-item-avatar color="grey"></v-list-item-avatar>
          <v-list-item-content>
            <v-list-item-title class="headline">abcde</v-list-item-title>
            <v-list-item-subtitle>2020년 7월 6일</v-list-item-subtitle>
          </v-list-item-content>
        </v-list-item>
        <v-card-text>
          <div class="text--primary">
            dalsfdkjasldkfjlasdkjflkasjdflasdjflaskdfjlaskdfjlas;kfjdlkasjdflk'aasdfklasjdlkfjasdklfjklsdjfklsjdfaklsjdfkljasdklfjklasdfjklasdjfklsad
          </div>
        </v-card-text>
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
      comments: ["1", "2"]
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
        const res = await axios.get(`${ServerURL}/newuser/post/get_cooment`, {
          params: {
            Scoring: this.$route.params.id
          }
        });
        console.log(res);
      } catch (e) {
        console.error(e);
      }
    },
    async createComment() {
      // eslint-disable-next-line
      const {ServerURL} = this;
      try {
        if (this.myComment !== "") {
          await axios.post(
            `${ServerURL}/user/post/write_comment`,
            {
              comment: this.myComment
            },
            {
              params: {
                Scoring: this.$route.params.id
              }
            }
          );
          this.myComment = "";
        }
      } catch (e) {
        console.error(e);
      }
    }
  }
};
</script>

<style scoped></style>
