<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col cols="2">
        <div id="relative_wrapper">
          <v-avatar size="240" class="mt-8">
            <img :src="writerThumbnail" />
          </v-avatar>
          <v-dialog v-model="thumbnailDialog" max-width="600px">
            <template
              v-slot:activator="{ on, attrs }"
              v-if="$store.state.username === this.writerName"
            >
              <v-btn
                v-bind="attrs"
                v-on="on"
                v-if="$store.state.username === this.writerName"
                class="mx-2"
                id="thumbnailplus"
                fab
                dark
                color="indigo"
              >
                <v-icon dark>mdi-camera</v-icon>
              </v-btn>
            </template>
            <v-card>
              <v-card-title>
                <span class="headline">프로필사진 관리</span>
              </v-card-title>
              <v-list rounded>
                <v-list-item-group v-model="ThumbnailSelect" color="primary">
                  <v-list-item v-for="(item, i) in items" :key="i" @click="changeThumbnail(i)">
                    <v-list-item-icon>
                      <v-icon v-text="item.icon"></v-icon>
                    </v-list-item-icon>
                    <v-list-item-content>
                      <v-list-item-title v-text="item.text"></v-list-item-title>
                    </v-list-item-content>
                  </v-list-item>
                </v-list-item-group>
              </v-list>
              <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn color="blue darken-1" text @click="thumbnailDialog = false">닫기</v-btn>
              </v-card-actions>
            </v-card>
          </v-dialog>
          <v-row justify="center" style="width:240px">
            <div class="ma-2 font-weight-bold"><v-icon>mdi-account</v-icon> {{ writerName }}님</div>
          </v-row>
          <v-tabs vertical class="my-15 pa-3">
            <v-tab
              style="justify-content:left;"
              v-for="tab in tabs"
              :key="tab.id"
              :to="tab.route"
              exact
            >
              <v-icon left>{{ tab.icon }}</v-icon>
              {{ tab.name }}
            </v-tab>
          </v-tabs>
        </div>
      </v-col>
      <v-col lg="6" cols="12">
        <v-toolbar flat class="ml-0 mr-3 mt-4">
          <h1 class="content-title">{{ problemNum }} {{ problemTitle }}</h1>
        </v-toolbar>

        <v-toolbar flat>
          <v-spacer></v-spacer>
          <v-toolbar-title class="ml-15"> </v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-title>
            <span class="ml-10 font-weight-light">{{ updatedDate }}</span>
          </v-toolbar-title>
        </v-toolbar>
        <v-row class="mx-auto mb-3">
          <v-col cols="3" style="border-left: 1px solid red; font-size: small ;">
            언어: {{ language }}
          </v-col>
          <v-col cols="3" style="border-left: 1px solid red; font-size: small ;">
            메모리: {{ memory }}KB
          </v-col>
          <v-col cols="3" style="border-left: 1px solid red; font-size: small ;">
            시간: {{ time }}MS
          </v-col>
          <v-col cols="3" style="border-left: 1px solid red; font-size: small ;">
            코드길이: {{ len }}B
          </v-col>
        </v-row>
        <v-toolbar flat v-if="$store.state.username === this.writerName">
          <v-spacer></v-spacer>
          <v-toolbar-title class="ml-15"> </v-toolbar-title>
          <v-spacer></v-spacer>
          <v-toolbar-title>
            <span class="ml-10 caption">
              <v-btn text @click="editSolution">수정</v-btn>
              <v-btn text @click="deleteSolution">삭제</v-btn></span
            >
          </v-toolbar-title>
        </v-toolbar>
        <v-toolbar-items class="mb-7">
          <v-chip class="mx-2" v-for="(key, i) in keyword" :key="i">
            {{ key }}
          </v-chip>
        </v-toolbar-items>
        <v-card min-height="70vh" flat class="main_content_wrapper">
          <editor-content class="main_content editor__content article" :editor="editor" />
        </v-card>
        <hr />
        <Comment />
      </v-col>
      <v-col lg="2">
        <div class="ml-8 mt-5 code_button">
          <v-tooltip bottom>
            <!-- eslint-disable-next-line -->
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                outlined
                fab
                icon
                class="mt-3 mb-2 mx-4"
                @click.stop="dialog = true"
                v-bind="attrs"
                v-on="on"
                ><v-icon>mdi-code-braces</v-icon></v-btn
              >
            </template>
            <span>제출 코드 보기</span>
          </v-tooltip>
          <v-tooltip bottom>
            <template v-slot:activator="{ on, attrs }">
              <v-btn outlined fab class="mt-3 mb-2" v-bind="attrs" v-on="on"
                ><v-icon>mdi-star</v-icon></v-btn
              >
            </template>
            <span>조아연</span>
          </v-tooltip>

          <!--          dialog 부분-->
          <v-dialog v-model="dialog" width="50vw" height="50vh">
            <v-card>
              <v-card-title class="headline"> {{ writerName }}님의 코드</v-card-title>
              <editor-content
                class="main_content code_area editor__content article"
                :editor="codeview"
              />
            </v-card>
          </v-dialog>
        </div>
        <v-card
          tile
          flat
          min-height="15vh"
          width="15vw"
          class="mx-8 table_of_contents"
          v-once
          v-if="tocLoaded"
        >
          <v-list dense>
            <v-subheader>목 차</v-subheader>
            <v-list-item-group v-model="TOC" color="primary">
              <v-list-item v-for="(item, i) in TOC" :key="i">
                <v-list-item-content>
                  <v-list-item-title @click="$vuetify.goTo(`#${item.id}`)" v-text="item.data">
                  </v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list-item-group>
          </v-list>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import { Editor, EditorContent } from "tiptap";
import { CodeBlockHighlight, Heading } from "tiptap-extensions";
import cpp from "highlight.js/lib/languages/cpp";
import css from "highlight.js/lib/languages/css";
import c from "highlight.js/lib/languages/c";
import clike from "highlight.js/lib/languages/c-like";
import python from "highlight.js/lib/languages/python";
import java from "highlight.js/lib/languages/java";
import javascript from "highlight.js/lib/languages/javascript";
import "highlight.js/styles/github.css";
import { mapState } from "vuex";
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
      thumbnailDialog: false,
      ThumbnailSelect: 0,
      writerThumbnail: null,
      items: [
        { text: "사진 업로드", icon: "mdi-camera-enhance" },
        { text: "기본이미지로 변경", icon: "mdi-camera-off" }
      ],
      tabs: [
        { id: 0, name: "Main", route: `/SSALOG/Main`, icon: "mdi-clipboard-text-play-outline" },
        { id: 1, name: "Solution", route: `/SSALOG/Solution`, icon: "mdi-heart" },
        { id: 2, name: "Profile", route: `/SSALOG/Profile`, icon: "mdi-heart" },
        { id: 3, name: "Following", route: `/SSALOG/Following`, icon: "mdi-heart" },
        { id: 4, name: "Follower", route: `/SSALOG/Follower`, icon: "mdi-heart" },
        { id: 5, name: "Star", route: `/SSALOG/Star`, icon: "mdi-heart" }
      ],
      // tocData
      tocLoaded: false,
      TOC: null,
      routeId: null,
      codeData: null,
      htmlData: null,
      problemNum: null,
      problemTitle: null,
      contentTitle: null,
      writerName: null,
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
  computed: mapState(["ServerURL", "ImgURL"]),
  created() {
    this.getSSALOG(this.$route.params.id);
  },
  mounted() {},
  methods: {
    editSolution() {
      this.$router.push({ name: "WriteLog", params: { id: this.$route.params.id } });
    },
    async deleteSolution() {
      this.$store.commit("ShowAlert", {
        flag: true,
        msg: "게시물을 삭제하였습니다. 메인으로 이동합니다."
      });
      setTimeout(() => {
        this.$store.commit("ShowAlert", { flag: false, msg: "" });
        this.$router.push({ name: "Home" });
      }, 2000);
    },
    async getSSALOG(pageId) {
      try {
        resetTocData();
        const res = await axios.get(`${this.ServerURL}/newuser/post/get_detail`, {
          params: {
            Scoring: pageId
          }
        });
        this.keyword = res.data.keyword;
        this.problemNum = res.data.problemid;
        this.problemTitle = res.data.problemname;
        this.writerName = res.data.username;
        this.htmlData = res.data.html;
        this.updatedDate = res.data.regdate;
        this.language = res.data.language;
        this.memory = res.data.memory;
        this.time = res.data.time;
        this.len = res.data.len;
        this.codeData = `<pre><code>${res.data.code}</code></pre>`;
        this.editor.setContent(this.htmlData);
        this.codeview.setContent(this.codeData);
        this.setTOC(tocData);
      } catch (e) {
        console.error(e);
      }
      this.getThumbnail();
    },
    async getThumbnail() {
      const res = await axios.get(`${this.ServerURL}/newuser/get_profile_img`, {
        params: {
          username: this.writerName
        }
      });
      this.writerThumbnail = `${this.ImgURL}${res.data}`;
    },
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
  font-size: 1rem;
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
  position: fixed;
  top: 35vh;
  border-left: 2px solid rgb(233, 236, 239) !important;
}
</style>
