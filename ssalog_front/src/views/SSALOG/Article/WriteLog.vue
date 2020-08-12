<template>
  <v-container mt-3>
    <h1>{{ resData.problemid }}번 : {{ resData.problemname }}</h1>
    <v-row class="px-3">
      <v-col cols="3" style="border-left: 1px solid red"> 언어: {{ resData.language }} </v-col>
      <v-col cols="3" style="border-left: 1px solid red"> 메모리: {{ resData.memory }}KB </v-col>
      <v-col cols="3" style="border-left: 1px solid red"> 시간: {{ resData.time }}MS </v-col>
      <v-col cols="3" style="border-left: 1px solid red"> 코드길이: {{ resData.len }}B </v-col>
    </v-row>
    <br />
    <v-row>
      <v-col cols="12" md="6">
        <div class="editor" spellcheck="false">
          <editor-menu-bubble
            :editor="codearea"
            :keep-in-bounds="keepInBounds"
            v-slot="{ isActive, menu }"
          >
            <div
              class="menububble"
              :class="{ 'is-active': menu.isActive }"
              :style="`left: ${menu.left}px; bottom: ${menu.bottom}px;`"
            >
              <button
                class="menububble__button"
                :class="{ 'is-active': isActive.bold() }"
                @click="menububbleClick"
              >
                <!-- <img class="icon" src="@/assets/tiptap/icons/bold.svg" /> -->
                <h5>옮기기</h5>
              </button>
            </div>
          </editor-menu-bubble>
          <editor-content class="editor__content usercode" :editor="codearea" />
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
                :class="{ 'is-active': isActive.paragraph() }"
                @click="commands.paragraph"
              >
                <img class="icon" src="@/assets/tiptap/icons/paragraph.svg" />
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
          <editor-content class="editor__content article" :editor="editor" />
          <div id="example-3">
            <template v-for="(item, i) in items">
              <input
                type="checkbox"
                v-bind:key="item.eng"
                v-bind:value="item.kor"
                v-model="SelectedProblemCategory"
              />
              <label v-bind:key="i" v-bind:for="item.eng">{{ item.kor }}</label>
            </template>
            <br />
          </div>
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
  Focus
} from "tiptap-extensions";
import cpp from "highlight.js/lib/languages/cpp";
import css from "highlight.js/lib/languages/css";
import c from "highlight.js/lib/languages/c";
import clike from "highlight.js/lib/languages/c-like";
import python from "highlight.js/lib/languages/python";
import java from "highlight.js/lib/languages/java";
import javascript from "highlight.js/lib/languages/javascript";
import "highlight.js/styles/github.css";

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
          })
        ],
        autoFocus: true,
        content: "",
        onUpdate: ({ getHTML }) => {
          this.resData.html = getHTML();
        }
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
      // alert(text);
      // this.editor.setContent("1");
      const transaction = this.editor.state.tr.insertText(text);
      this.editor.view.dispatch(transaction);
      this.editor.commands.code_block();
    },
    addUsername() {
      axios
        .get(`${this.ServerURL}/user/post/get_username?Scoring=${this.$route.params.id}`)
        .then(response => {
          console.log(response);
        })
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
      axios
        .put(`${this.ServerURL}/user/post/update_post`, this.resData)
        .then(response => {
          console.log(response);
          this.$router.push({
            name: "LogDetail",
            // eslint-disable-next-line
            params: { nickname: this.nickname} // id 안넣어도 지금 url 에서 자동으로 담아서 날려준다..
          });
        })
        .catch(function(error) {
          // handle error
          console.log(error);
        });
    },
    /*eslint-disable */
    async getData(that) {
      that.resData = await getSSALOG(that.$route.params.id);
      // 이미 작성한 것을 수정할때
      that.isUpdating = that.resData.html == null ? false : true;
      console.log(that.isUpdating);
      console.log("isupdating=" + that.isUpdating);
      if (!that.isUpdating) {
        // 속성이 없다면.
        // 속성 추가해주기
        const transCode = that.codearea.state.tr.insertText(that.resData.code);
        that.codearea.view.dispatch(transCode);
        that.codearea.commands.code_block();
        that.resData.SelectedProblemCategory = [];
        that.resData.html = "";
      } else {
        const transCode = that.codearea.state.tr.insertText(that.resData.code);
        that.codearea.view.dispatch(transCode);
        that.codearea.commands.code_block();
        that.SelectedProblemCategory = that.resData.keyword;
        that.editor.setContent(that.resData.html);
      }
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
