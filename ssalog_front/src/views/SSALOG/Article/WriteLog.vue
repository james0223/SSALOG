<template>
  <v-container mt-3>
    <template v-if="resData !== undefined">
      <h1>{{ resData.problemid }}번 : {{ resData.problemname }}</h1>
      <v-row class="px-3">
        <v-col cols="3" style="border-left: 1px solid red"> 언어: {{ resData.language }} </v-col>
        <v-col cols="3" style="border-left: 1px solid red"> 메모리: {{ resData.memory }}KB </v-col>
        <v-col cols="3" style="border-left: 1px solid red"> 시간: {{ resData.time }}MS </v-col>
        <v-col cols="3" style="border-left: 1px solid red"> 코드길이: {{ resData.len }}B </v-col>
      </v-row>
    </template>
    <br />
    <v-row>
      <v-col cols="12" md="6">
        <div class="editor" spellcheck="false">
          <editor-menu-bubble :editor="codearea" :keep-in-bounds="keepInBounds" v-slot="{ menu }">
            <div
              class="menububble"
              :class="{ 'is-active': menu.isActive }"
              :style="`left: ${menu.left + 50}px; top: ${menu.top}px;`"
            >
              <button class="menububble__button red" @click="menububbleClick">
                <!-- <img class="icon" src="@/assets/tiptap/icons/bold.svg" /> -->
                <v-icon color="white">mdi-content-cut</v-icon>
              </button>
            </div>
          </editor-menu-bubble>
          <editor-content
            class="editor__content usercode"
            :editor="codearea"
            style="height : 100vh;"
          />
        </div>
      </v-col>
      <v-col cols="12" md="6">
        <div class="editor" spellcheck="false">
          <v-text-field label="제목" v-model="title"></v-text-field>
          <v-select
            v-model="SelectedProblemCategory"
            :items="$store.state.ProblemCategory"
            :item-text="'kor'"
            :item-value="'kor'"
            attach
            chips
            label="알고리즘 분류"
            multiple
          ></v-select>
          <editor-menu-bar :editor="editor" v-slot="{ commands, isActive }">
            <div class="menubar">
              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.bold() }"
                @click="commands.bold"
              >
                <img class="icon" src="@/assets/tiptap/icons/bold.svg" />
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.italic() }"
                @click="commands.italic"
              >
                <img class="icon" src="@/assets/tiptap/icons/italic.svg" />
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.strike() }"
                @click="commands.strike"
              >
                <img class="icon" src="@/assets/tiptap/icons/strike.svg" />
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.underline() }"
                @click="commands.underline"
              >
                <img class="icon" src="@/assets/tiptap/icons/underline.svg" />
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.code() }"
                @click="commands.code"
              >
                <img class="icon" src="@/assets/tiptap/icons/code.svg" />
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.heading({ level: 1 }) }"
                @click="commands.heading({ level: 1 })"
              >
                H1
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.heading({ level: 2 }) }"
                @click="commands.heading({ level: 2 })"
              >
                H2
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.heading({ level: 3 }) }"
                @click="commands.heading({ level: 3 })"
              >
                H3
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.bullet_list() }"
                @click="commands.bullet_list"
              >
                <img class="icon" src="@/assets/tiptap/icons/ul.svg" />
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.ordered_list() }"
                @click="commands.ordered_list"
              >
                <img class="icon" src="@/assets/tiptap/icons/ol.svg" />
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.blockquote() }"
                @click="commands.blockquote"
              >
                <img class="icon" src="@/assets/tiptap/icons/quote.svg" />
              </button>

              <button
                class="menubar__button"
                :class="{ 'is-active': isActive.code_block() }"
                @click="commands.code_block"
              >
                <img class="icon" src="@/assets/tiptap/icons/code.svg" />
              </button>

              <button class="menubar__button" @click="commands.horizontal_rule">
                <img class="icon" src="@/assets/tiptap/icons/hr.svg" />
              </button>

              <button class="menubar__button" @click="commands.undo">
                <img class="icon" src="@/assets/tiptap/icons/undo.svg" />
              </button>

              <button class="menubar__button" @click="commands.redo">
                <img class="icon" src="@/assets/tiptap/icons/redo.svg" />
              </button>
            </div>
          </editor-menu-bar>
          <div v-on:click="select()">
            <editor-content class="editor__content article" :editor="editor" />
          </div>
          <br />
          <v-btn color="info" tile block @click="write">
            {{ isUpdating ? "수정하기" : "작성하기 " }}</v-btn
          >
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import { mapState } from "vuex";
import { Editor, EditorContent, EditorMenuBar, EditorMenuBubble } from "tiptap";
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
  History,
  Focus,
  TrailingNode
} from "tiptap-extensions";
import cpp from "highlight.js/lib/languages/cpp";
import css from "highlight.js/lib/languages/css";
import c from "highlight.js/lib/languages/c";
import clike from "highlight.js/lib/languages/c-like";
import python from "highlight.js/lib/languages/python";
import java from "highlight.js/lib/languages/java";
import javascript from "highlight.js/lib/languages/javascript";
import "highlight.js/styles/github.css";

import lodash from "lodash";
import axios from "axios";
import getSSALOG from "../../../utils/SSALOG";

export default {
  components: {
    EditorContent,
    EditorMenuBar,
    EditorMenuBubble
  },
  data() {
    return {
      keepInBounds: false,
      title: null,
      resData: undefined,
      isUpdating: false,
      SelectedProblemCategory: [],
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
          new Focus({
            className: "has-focus",
            nested: true
          }),
          new TrailingNode({
            node: "paragraph",
            notAfter: ["paragraph"]
          })
        ],
        autoFocus: true,
        content: "",
        onUpdate: ({ getHTML }) => {
          this.resData.html = getHTML();
        },
        // onTransaction: ({ state }) => {
        //   console.log(state);
        // },
        editorProps: {
          handleDOMEvents: {
            drop: (view, e) => {
              // console.log(view);
              // console.log(e);
              e.preventDefault();
            }
          }
        },
        // hide the drop position indicator
        dropCursor: { width: 0, color: "transparent" }
        // onDrop: (view, event, slice, moved) => {
        //   console.log(`ondrop`);
        //   console.log(view, event, slice, moved);
        // }
      }),
      codearea: new Editor({
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
          new Code(),
          new CodeBlock(),
          new History(),
          new HardBreak(),
          new Heading({ levels: [1, 2, 3] }),
          new Bold(),
          new Italic()
        ],
        editable: true,
        content: ``
      })
    };
  },
  computed: mapState(["ServerURL", "nickname", "username"]),
  beforeDestroy() {
    this.editor.destroy();
  },
  methods: {
    menububbleClick() {
      //  alert("클릭됨");
      const { selection, state } = this.codearea;
      const { from, to } = selection;
      const text = state.doc.textBetween(from, to);
      // console.log(text);
      // this.editor.setContent("1");
      const transaction = this.editor.state.tr.insertText(text);
      // console.log(transaction);
      this.editor.view.dispatch(transaction);
      this.editor.commands.code_block();
      this.editor.commands.horizontal_rule();
      // console.log(this.editor);
    },
    addUsername() {
      axios
        .get(`${this.ServerURL}/user/post/get_username?Scoring=${this.$route.params.id}`)
        .catch(function(error) {
          // handle error
          console.log(error);
        });
    },
    write() {
      if (!this.title) {
        this.$store.commit("ShowAlert", {
          flag: true,
          msg: "제목을 입력해주세요",
          color: "error"
        });
        setTimeout(() => {
          this.$store.commit("ShowAlert", { flag: false, msg: "" });
        }, 2000);
        return;
      }
      this.resData.username = this.username;
      this.resData.keyword = this.SelectedProblemCategory;
      this.resData.nickname = this.nickname; // 작성자 닉
      this.resData.title = this.title; // 글 제목
      this.resData.html = this.editor.getHTML();
      axios
        .put(`${this.ServerURL}/user/post/update_post`, this.resData)
        .then(() => {
          this.$router.push({
            name: "LogDetail",
            // eslint-disable-next-line
            params: { nickname: this.nickname } // id 안넣어도 지금 url 에서 자동으로 담아서 날려준다..
          });
        })
        .catch(function(error) {
          // handle error
          console.log(error);
        });
    },
    /*eslint-disable */
    async getData(that) {
      const iswrite = await axios.get(`${this.ServerURL}/newuser/post/is_write`, {
        params: {
          Scoring: that.$route.params.id
        }
      });

      if (iswrite.data) {
        that.resData = await axios.get(`${this.ServerURL}/newuser/post/get_detail`, {
          params: {
            Scoring: that.$route.params.id
          }
        });

        that.resData = that.resData.data;
      } else {
        that.resData = await getSSALOG(that.$route.params.id);
      }
      that.isUpdating = that.resData.html == undefined ? false : true;

      if (!that.isUpdating) {
        // 속성이 없다면.
        // 속성 추가해주기
        let res = null;
        try {
           res = await this.$http.get(`${this.$store.state.ServerURL}/newuser/get_form`, {
            params: {
              nickname: this.nickname
            }
          });
          // console.log(res);
          if(res.data==="null"){
            res.data = "<h2>접근방법</h2><hr><p></p><h2>코드분석</h2><hr>";
          }
        } catch (e) {
          console.error(e);
        }

        const transCode = that.codearea.state.tr.insertText(that.resData.code);
        that.codearea.view.dispatch(transCode);
        that.codearea.commands.code_block();
        that.title = lodash.sample([
          `${this.nickname}님의 친절한 ${that.resData.problemname} 풀이`,
          `${that.resData.problemname} 3초컷`,
          `${that.resData.problemname} 은(는) 진짜 전설이다...`,
          `가슴이 웅장해지는 ${that.resData.problemname} 풀이`,
          `${that.resData.problemname} 왜이렇게 어렵냐...`,
          `${that.resData.problemname} 빛의 속도로 풀었음`
        ]);
        that.resData.SelectedProblemCategory = [];
        that.editor.setContent(res.data);
        // that.resData.html = res.data; // <- 여기
      } else {
        const transCode = that.codearea.state.tr.insertText(that.resData.code);
        that.codearea.view.dispatch(transCode);
        that.codearea.commands.code_block();
        that.title = that.resData.title;
        that.SelectedProblemCategory = that.resData.keyword;

        // const userHTML = that.editor.state.tr.insertText(that.resData.html);
        // that.editor.view.dispatch(userHTML);
        // console.log(that.resData.html);
        // if (that.resData.html == "<p></p>") {
        //   that.resData.html = "<p></p><p></p><p></p><p></p><p></p>";
        // }
        that.editor.setContent(that.resData.html);
        // that.editor.commands.code_block();
      }
    },
    select: function() {
      // console.log("클릭"); // returns 'foo'
      this.editor.view.dom.focus();
    }
    /* eslint-enable */
  },
  created() {
    this.getData(this);
    this.addUsername();
  }
};
</script>

<style lang="scss" scoped>
// eliminate vuetify settings
.v-application code {
  all: unset;
}

.icon {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  width: 0.8rem;
  height: 0.8rem;
  margin: 0 0.3rem;
  top: -0.05rem;
  fill: currentColor;

  // &.has-align-fix {
  // top: -.1rem;
  // }
  &__svg {
    display: inline-block;
    vertical-align: top;
    width: 100%;
    height: 100%;
  }
  &:first-child {
    margin-left: 0;
  }

  &:last-child {
    margin-right: 0;
  }
}
// svg sprite
body > svg,
.icon use > svg,
symbol {
  path,
  rect,
  circle,
  g {
    fill: currentColor;
    stroke: none;
  }

  *[d="M0 0h24v24H0z"] {
    display: none;
  }
}

//code 부분 style
.usercode {
  text-align: left;
  overflow-y: scroll;
  height: 80vh;
}
// article 부분 style
.article {
  padding: 15px 10px;
  border: 3px solid;
  overflow-y: scroll;
  height: 60vh;
}
.editor *.is-empty:nth-child(1)::before {
  content: attr(data-empty-text);
  float: left;
  pointer-events: none;
  height: 0;
  font-style: italic;
}

.has-focus {
  border-radius: 3px;
  box-shadow: 0 0 0 3px #3ea4ffe6;
}
</style>
