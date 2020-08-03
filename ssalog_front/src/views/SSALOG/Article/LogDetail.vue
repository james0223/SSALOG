<template>
  <v-container>
    <v-row justify="center" no-gutters>
      <v-col cols="2">
        <div class="code_button">
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
        <TOC v-bind:content="htmlData" class="toc" />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";
import { Editor, EditorContent } from "tiptap";
import { CodeBlockHighlight } from "tiptap-extensions";
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
import TOC from "@/components/TOC.vue";
import Comment from "@/components/Comment.vue";

export default {
  name: "LogDetail",
  components: {
    EditorContent,
    Comment,
    TOC
  },
  data() {
    return {
      routeId: null,
      codeData: null,
      htmlData: null,
      problemNum: null,
      problemTitle: null,
      contentTitle: "19541 역학 조사",
      writerName: null,
      updatedDate: "2020-07-27",
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
          })
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
  computed: mapState(["ServerURL"]),
  created() {
    this.getSSALOG(this.$route.params.id);
  },
  methods: {
    async getSSALOG(pageId) {
      try {
        console.log(this.ServerURL);
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
      } catch (e) {
        console.error(e);
      }
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
  position: fixed;
  top: 30vh;
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
  position: fixed;
  top: 30vh;
}
</style>
