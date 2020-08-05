<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col cols="2">
        <div id="relative_wrapper">
          <v-avatar size="260" class="mt-8">
            <img :src="writerThumbnail" />
          </v-avatar>
          <v-dialog v-model="thumbnailDialog" max-width="600px">
            <template v-slot:activator="{ on, attrs }">
              <v-btn
                v-bind="attrs"
                v-on="on"
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
          <v-tabs vertical class="my-15 pa-3" v-model="activeTab">
            <v-tab v-for="tab in tabs" :key="tab.id" :to="tab.route" exact>
              <v-icon left>{{ tab.icon }}</v-icon>
              {{ tab.name }}
            </v-tab>
          </v-tabs>
        </div>
      </v-col>
      <v-col lg="6" cols="12">
        <v-toolbar flat class="mx-auto mt-5 mb-3">
          <h1 class="content-title">{{ problemNum }} {{ problemTitle }}</h1>
        </v-toolbar>
        <v-toolbar flat class="mb-5">
          <v-toolbar-title>
            <span class="mr-2">{{ writerName }}</span>
            <span class="mr-2">·</span>
            <span class="mr-2">{{ updatedDate }}</span>
            <v-spacer></v-spacer>
          </v-toolbar-title>

          <v-spacer></v-spacer>
          <v-toolbar-title>
            <span class="mr-4">수정</span>
            <span>삭제</span>
          </v-toolbar-title>
        </v-toolbar>
        <v-card flat class="main_content_wrapper">
          <editor-content class="main_content editor__content article" :editor="editor" />
        </v-card>
        <Comment />
      </v-col>
      <v-col lg="2">
        <div class="ml-8 mt-5 code_button">
          <v-btn outlined fab icon class="mt-3 mb-2" @click.stop="dialog = true"
            ><v-icon>mdi-code-braces</v-icon></v-btn
          >
          <v-btn outlined fab class="mt-2 mb-3"><v-icon>mdi-star</v-icon></v-btn>
          <!--          dialog 부분-->
          <v-dialog v-model="dialog" width="50vw" height="50vh">
            <v-card>
              <v-card-title class="headline"> {{ writerName }}님의 코드</v-card-title>
              <editor-content class="main_content editor__content article" :editor="codeview" />
            </v-card>
          </v-dialog>
        </div>
        <v-card height="35vh" width="15vw" class="mx-8 table_of_contents" v-once v-if="tocLoaded">
          <v-list dense>
            <v-subheader>목 차</v-subheader>
            <v-list-item-group v-model="TOC" color="primary">
              <v-list-item v-for="(item, i) in TOC" :key="i">
                <a @click="$vuetify.goTo(`#${item.id}`)">
                  <v-list-item-content>
                    <v-list-item-title v-text="item.data"> </v-list-item-title>
                  </v-list-item-content>
                </a>
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
import { TocHeading, tocData } from "@/plugins/tiptap/TocHeading";

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
      // code dialog
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
    this.getThumbnail();
  },
  mounted() {},
  methods: {
    async getSSALOG(pageId) {
      try {
        const res = await axios.get(`${this.ServerURL}/newuser/post/get_detail`, {
          params: {
            Scoring: pageId
          }
        });
        this.problemNum = res.data.problemid;
        this.problemTitle = res.data.problemname;
        this.writerName = res.data.username;
        this.htmlData = res.data.html;
        this.codeData = `<pre><code>${res.data.code}</code></pre>`;
        this.editor.setContent(this.htmlData);
        this.codeview.setContent(this.codeData);
        this.setTOC(tocData);
      } catch (e) {
        console.error(e);
      }
    },
    async getThumbnail() {
      console.log("hihih");
      const res = await axios.get(`${this.ServerURL}/newuser/get_profile_img`, {
        params: {
          username: this.writerName
        }
      });
      console.log(this.writerThumbnail);
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
  min-height: 200vh;
}
.code_button {
  top: 10vh;
  width: 4rem;
  display: flex;
  flex-direction: column;
  -webkit-box-align: center;
  align-items: center;
  background: rgb(248, 249, 250);
  border-width: 1px;
  border-style: solid;
  border-color: rgb(241, 243, 245);
  border-image: initial;
  border-radius: 2rem;
  padding: 0.5rem;
}

.table_of_contents {
  top: 10vh;
}
</style>
