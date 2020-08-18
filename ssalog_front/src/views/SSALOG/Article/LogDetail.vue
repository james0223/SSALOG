<template>
  <v-row no-gutters>
    <v-col lg="10" cols="12">
      <v-card class="mt-2 pa-3 mb-10" color="transparent" tile>
        <h3 v-show="!scrapped" title="스크랩한 사용자수" class="ma-2 frontcorner">
          <v-icon @click="doScrap" style="cursor:pointer;" color="#f8f8f8">mdi-star</v-icon>
          {{ like }}
        </h3>
        <h3
          v-show="scrapped"
          title="스크랩한 사용자수"
          class="ma-2 frontcorner"
          style="color:black"
        >
          <v-icon @click="doScrap" style="cursor:pointer;" color="black">mdi-star</v-icon>
          {{ like }}
        </h3>
        <img
          v-if="!scrapped"
          class="corner"
          src="@/assets/images/corner/corner_normal.png"
          title="스크랩한 사용자수"
          alt="scrap number"
        />
        <img
          v-if="scrapped"
          class="corner"
          src="@/assets/images/corner/corner_scrapped.png"
          title="스크랩한 사용자수"
          alt="scrap number"
        />
        <v-card-title>
          <h2>{{ title }}</h2>
        </v-card-title>
        <v-card-text>
          <v-row>
            <v-col cols="12" sm="6">
              <h3>
                문제 :
                <span
                  class="innerLink blue--text"
                  @click="$router.push({ name: 'ProblemDetail', params: { id: problemNum } })"
                  outlined
                  >{{ problemNum }} {{ problemTitle }}</span
                >
                <v-btn
                  height="1rem"
                  class="ml-1 mb-1 pb-1 no-background-hover"
                  text
                  @click="goSite(`https://www.acmicpc.net/problem/${problemNum}`)"
                >
                  <img style="height:1.5rem;" src="@/assets/images/boj.png" />백준
                </v-btn>
              </h3>
            </v-col>
            <v-col cols="12" sm="6">
              <h3>작성일 : {{ updatedDate }}</h3>
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-text>
          <v-row>
            <v-col cols="12" sm="3">
              <v-icon>mdi-lead-pencil</v-icon>
              언어: {{ language }}
            </v-col>
            <v-col cols="12" sm="3">
              <v-icon>mdi-memory</v-icon>
              메모리: {{ memory }}KB
            </v-col>
            <v-col cols="12" sm="3">
              <v-icon>mdi-timer</v-icon>
              시간: {{ time }}MS
            </v-col>
            <v-col cols="12" sm="3">
              <v-icon>mdi-sort-variant</v-icon>
              코드길이: {{ len }}B
            </v-col>
          </v-row>
        </v-card-text>
        <v-card-text class="pb-0" v-if="!!keyword">
          사용한 알고리즘
          <v-chip-group>
            <v-chip outlined v-for="(key, i) in keyword" :key="i">{{ key }}</v-chip>
          </v-chip-group>
        </v-card-text>
        <v-card-title class="ma-0 pa-0" v-if="username === writerUsername">
          <v-spacer></v-spacer>
          <v-btn text @click="editSolution">수정</v-btn>
          <v-btn text color="red" @click="isDelete = true">삭제</v-btn>
          <v-dialog v-model="isDelete" max-width="60vh">
            <v-card>
              <v-card-title>
                <h5>글 삭제</h5>
              </v-card-title>
              <v-card-text>정말 삭제하시겠어요?</v-card-text>
              <v-card-text>삭제 이후에는 복구할 수 없습니다.</v-card-text>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="red" text @click="deleteSolution">삭제</v-btn>
                <v-btn color="grey" text @click="isDelete = false">취소</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
        </v-card-title>
      </v-card>
      <v-card flat color="transparent" min-height="40vh" class="pa-0 pa-sm-3 mb-10">
        <v-card-text class="pa-0 pa-sm-3">
          <editor-content
            class="main_content editor__content article text-sm-caption text-md-subtitle-1"
            :editor="editor"
          />
        </v-card-text>
      </v-card>
      <Comment />
      <!-- </v-card> -->
    </v-col>
    <v-col class="d-none d-lg-flex" cols="2">
      <div class="ml-8 mt-5 code_button">
        <v-tooltip bottom>
          <!-- eslint-disable-next-line -->
            <template v-slot:activator="{ on, attrs }">
            <v-btn
              outlined
              x-large
              icon
              class="mt-3 mb-2 mx-4"
              @click.stop="dialog = true"
              v-bind="attrs"
              v-on="on"
            >
              <v-icon :color="ColorSet.Mid">mdi-code-braces</v-icon>
            </v-btn>
          </template>
          <span>제출 코드 보기</span>
        </v-tooltip>
        <v-tooltip v-if="username && username !== writerUsername" bottom>
          <template v-slot:activator="{ on }">
            <v-btn outlined icon x-large class="mt-3 mb-2" v-on="on" @click="doScrap">
              <v-icon :disabled="!scrapped" :color="ColorSet.Sub">mdi-star</v-icon>
            </v-btn>
          </template>
          <span>스크랩</span>
        </v-tooltip>

        <!--          dialog 부분-->
        <v-dialog v-model="dialog" width="50vw" height="50vh">
          <v-card>
            <v-card-title class="headline">
              {{ writerNickname }}님의 코드
              <v-spacer></v-spacer>
              <v-btn color="info" small text v-clipboard:copy="code" @click="showCopyMsg"
                >복사하기</v-btn
              >
            </v-card-title>
            <editor-content
              class="main_content code_area editor__content article"
              :editor="codeview"
            />
          </v-card>
        </v-dialog>
      </div>
      <v-card
        elevation="8"
        min-height="15vh"
        width="15vw"
        class="mx-8 table_of_contents"
        v-once
        v-if="tocLoaded"
      >
        <v-card-title class="index_title">
          <h5>목 차</h5>
        </v-card-title>
        <v-list color="transparent" dense>
          <v-list-item-group v-model="TOC" color="primary">
            <v-list-item v-for="(item, i) in TOC" :key="i">
              <v-list-item-content>
                <v-list-item-title
                  @click="$vuetify.goTo(`[id='${item.id}']`)"
                  v-text="item.data"
                ></v-list-item-title>
              </v-list-item-content>
            </v-list-item>
          </v-list-item-group>
        </v-list>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>
import axios from "axios";
import { Editor, EditorContent } from "tiptap";
import {
  CodeBlockHighlight,
  Placeholder,
  Blockquote,
  CodeBlock,
  HardBreak,
  Heading,
  HorizontalRule,
  OrderedList,
  BulletList,
  ListItem,
  Image,
  Bold,
  Code,
  Italic,
  Link,
  Strike,
  Underline,
  History
} from "tiptap-extensions";
import cpp from "highlight.js/lib/languages/cpp";
import css from "highlight.js/lib/languages/css";
import c from "highlight.js/lib/languages/c";
import clike from "highlight.js/lib/languages/c-like";
import python from "highlight.js/lib/languages/python";
import java from "highlight.js/lib/languages/java";
import javascript from "highlight.js/lib/languages/javascript";
import "highlight.js/styles/xcode.css";
import { mapState, mapMutations } from "vuex";
// component
import Comment from "@/components/Comment.vue";
// TOC
import { TocHeading, tocData, resetTocData } from "@/plugins/tiptap/TocHeading";

export default {
  name: "LogDetail",
  components: {
    EditorContent,
    Comment
  },
  data() {
    return {
      // 왼쪽 thumbnail 관련
      isDelete: false, // delete 모달
      scrapped: false,
      thumbnailDialog: false,
      ThumbnailSelect: 0,
      writerThumbnail: null,
      writerNickname: null,
      items: [
        { text: "사진 업로드", icon: "mdi-camera-enhance" },
        { text: "기본이미지로 변경", icon: "mdi-camera-off" }
      ],
      tabs: [
        {
          id: 0,
          name: "Main",
          route: `/SSALOG/${this.writerNickname}/Main`,
          icon: "mdi-clipboard-text-play-outline"
        },
        {
          id: 1,
          name: "Solution",
          route: `/SSALOG/${this.writerNickname}/SolutionList`,
          icon: "mdi-ballot"
        },
        {
          id: 2,
          name: "Profile",
          route: `/SSALOG/${this.writerNickname}/Profile`,
          icon: "mdi-account"
        },
        {
          id: 3,
          name: "Following",
          route: `/SSALOG/${this.writerNickname}/Following`,
          icon: "mdi-account-arrow-right"
        },
        {
          id: 4,
          name: "Follower",
          route: `/SSALOG/${this.writerNickname}/Follower`,
          icon: "mdi-account-arrow-left"
        },
        {
          id: 5,
          name: "Star",
          route: `/SSALOG/${this.writerNickname}/Star`,
          icon: "mdi-star"
        }
      ],
      // tocData
      tocLoaded: false,
      TOC: null,
      routeId: null,
      codeData: null,
      code: null,
      htmlData: null,
      problemNum: null,
      problemTitle: null,
      title: null,
      like: null,
      writerUsername: null,
      updatedDate: null,
      language: null,
      memory: null,
      time: null,
      len: null,
      // code dialog
      keyword: [],
      dialog: false,
      editor: new Editor({
        extensions: [
          new Blockquote(),
          new BulletList(),
          new CodeBlock(),
          new HardBreak(),
          new Heading({ levels: [1, 2, 3] }),
          new HorizontalRule(),
          new ListItem(),
          new OrderedList(),
          new Image(),
          new Link(),
          new Bold(),
          new Code(),
          new Italic(),
          new Strike(),
          new Underline(),
          new History(),
          new Placeholder({
            showOnlyCurrent: false
          }),
          new CodeBlockHighlight({
            languages: {
              cpp,
              css,
              c,
              clike,
              python,
              java,
              javascript
            }
          }),
          new Heading({ levels: [1, 2, 3] }),
          new TocHeading()
        ],
        editable: false,
        autoFocus: true,
        content: ""
      }),
      codeview: new Editor({
        extensions: [
          new CodeBlockHighlight({
            languages: {
              cpp,
              css,
              c,
              clike,
              python,
              java,
              javascript
            }
          })
        ],
        editable: false,
        autoFocus: true,
        content: ""
      })
    };
  },
  computed: mapState(["ServerURL", "ImgURL", "nickname", "username", "ColorSet"]),
  created() {
    this.getSSALOG(this.$route.params.id);
    if (this.$store.state.nickname != null) {
      this.getScrapped();
    }
  },
  methods: {
    ...mapMutations(["ShowAlert"]),
    goSite(site) {
      const win = window.open(site, "_blank");
      win.focus();
    },
    getScrapped() {
      axios
        .get(`${this.$store.state.ServerURL}/user/scrap/is_scrap`, {
          params: {
            Scoring: this.$route.params.id
          }
        })
        .catch(function(error) {
          // handle error
          console.log(error);
        })
        .then(res => {
          this.scrapped = res.data;
        });
    },
    doScrap() {
      // alert("스크랩");
      // console.log("스크랩");
      if (!this.nickname) {
        this.ShowAlert({
          flag: true,
          msg: "스크랩 기능은 로그인 후 가능해요.",
          color: "info"
        });
        setTimeout(() => {
          this.ShowAlert({ flag: false, msg: "" });
        }, 2000);
        return;
      }
      if (this.nickname === this.writerNickname) {
        this.ShowAlert({
          flag: true,
          msg: "본인 게시물은 스크랩할 수 없어요",
          color: "info"
        });
        setTimeout(() => {
          this.ShowAlert({ flag: false, msg: "" });
        }, 2000);
        return;
      }
      if (!this.scrapped) {
        this.like += 1;
        axios
          .post(
            `${this.ServerURL}/user/scrap/do_scrap`,
            {},
            { params: { Scoring: this.$route.params.id } }
          )
          .then(() => {
            this.scrapped = true;

            this.$emit("updateScrap");
          })
          .catch(e => {
            console.error(e);
          });
      } else {
        this.like -= 1;
        axios
          .delete(`${this.ServerURL}/user/scrap/stop_scrap`, {
            params: { Scoring: this.$route.params.id }
          })
          .then(() => {
            this.scrapped = false;
            this.$emit("updateScrap");
          })
          .catch(function(error) {
            console.log(error);
          });
      }
    },
    showCopyMsg() {
      this.ShowAlert({
        flag: true,
        msg: "클립보드에 복사되었습니다.",
        color: "info"
      });
      setTimeout(() => {
        this.ShowAlert({ flag: false, msg: "" });
      }, 2000);
    },
    editSolution() {
      this.$router.push({
        name: "WriteLog",
        params: { id: this.$route.params.id }
      });
    },
    async deleteSolution() {
      try {
        axios.delete(`${this.$store.state.ServerURL}/user/post/delete_post`, {
          params: {
            Scoring: this.$route.params.id
          }
        });
        this.ShowAlert({
          flag: true,
          msg: "게시물을 삭제하였습니다.",
          color: "red"
        });
        setTimeout(() => {
          this.ShowAlert({ flag: false, msg: "" });
          this.$router.push(`/SSALOG/${this.$route.params.nickname}/SolutionList`);
        }, 1000);
      } catch (e) {
        console.error(e);
      }
      this.isDelete = false;
    },
    async getSSALOG(pageId) {
      try {
        resetTocData();
        const res = await axios.get(`${this.ServerURL}/newuser/post/get_detail`, {
          params: {
            Scoring: pageId
          }
        });
        this.like = res.data.like;
        this.title = res.data.title;
        this.keyword = res.data.keyword;
        this.problemNum = res.data.problemid;
        this.problemTitle = res.data.problemname;
        this.writerUsername = res.data.username;
        this.writerNickname = res.data.nickname;
        this.htmlData = res.data.html;
        this.updatedDate = res.data.regdate;
        this.language = res.data.language;
        this.memory = res.data.memory;
        this.time = res.data.time;
        this.len = res.data.len;
        this.code = res.data.code;
        this.codeData = `<pre><code>${res.data.code}</code></pre>`;
        this.editor.setContent(this.htmlData);
        this.codeview.setContent(this.codeData);
        this.setTOC(tocData);
      } catch (e) {
        console.error(e);
      }
      // this.getThumbnail();
    },
    // async getThumbnail() {
    //   const res = await axios.get(`${this.ServerURL}/newuser/get_profile_img`, {
    //     params: {
    //       username: this.writerUsername
    //     }
    //   });
    //   this.writerThumbnail = `${this.ImgURL}${res.data}`;
    // },
    setTOC(toc) {
      this.TOC = toc;
      this.tocLoaded = true;
    }
  }
};
</script>

<style>
.v-application code {
  all: unset;
  border: 1px solid #ddd;
  border-left: 3px solid #f36d33;
  background: #f8f8f8;
  page-break-inside: avoid;
  font-family: monospace;
  line-height: 1.6;
  margin-bottom: 1.6em;
  max-width: 100%;
  overflow: auto;
  padding: 1em 1.5em;
  display: block;
  word-wrap: break-word;
  /* font-size: 1rem; */
  overflow-x: auto;
}
.content-title {
  font-size: 3rem;
  line-height: 1.5;
  letter-spacing: -0.004em;
  margin-top: 5vh;
  font-weight: 800;
  color: rgb(52, 58, 64);
  margin-bottom: 2rem;
  word-break: keep-all;
}
.main_content_wrapper {
  margin-bottom: 30px;
}
.code_button {
  position: fixed;
  top: 25vh;
  /*top: 10vh;*/
  /*width: 4rem;*/
  /*display: flex;*/
  /*flex-direction: column;*/
  /*-webkit-box-align: center;*/
  /*align-items: center;*/
  /*background: rgb(248, 249, 250);*/
  /*border-width: 1px;*/
  /*border-style: solid;*/
  /*border-color: rgb(241, 243, 245);*/
  /*border-image: initial;*/
  /*border-radius: 2rem;*/
  /*padding: 0.5rem;*/
}
.table_of_contents {
  opacity: 80%;
  position: fixed;
  top: 40vh;
  /* background: #8dffb3 !important; */
}
.innerLink {
  cursor: pointer;
}
.no-background-hover::before {
  background-color: transparent !important;
}
.frontcorner {
  color: #f8f8f8;
  z-index: 3;
  position: absolute;
  top: 0px;
  right: 0px;
  height: 5rem;
}
.corner {
  position: absolute;
  z-index: 2;
  top: 0px;
  right: 0px;
  height: 5rem;
}
.index_title {
  background: rgb(138, 80, 219);
  background: linear-gradient(124deg, rgba(138, 80, 219, 1) 16%, rgba(141, 255, 179, 1) 98%);
  color: #f0f0f0;
}
</style>
