<template>
  <v-container>
    <h1>{{ obj.problemid }}번 : {{ obj.problemname }}</h1>
    <v-row>
      <v-col cols="12" md="6">
        <div class="editor" spellcheck="false">
          <editor-menu-bubble
            :editor="codearea"
            :keep-in-bounds="keepInBounds"
            v-slot="{ commands, isActive, menu }"
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
          <v-select
            v-model="SelectedProblemCategory"
            :items="ProblemCategory"
            :item-text="'kor'"
            :item-value="'kor'"
            attach
            chips
            label="알고리즘 분류"
            multiple
          ></v-select>
          <editor-content class="editor__content article" :editor="editor" />
          <div id="example-3">
            <template v-for="(item, i) in items">
              <input
                type="checkbox"
                v-bind:key="item.eng"
                v-bind:value="item.kor"
                v-model="obj.keyword"
              />
              <label v-bind:key="i" v-bind:for="item.eng">{{ item.kor }}</label>
            </template>
            <br />
            <span>체크한 키워드: {{ SelectedProblemCategory }}</span>
          </div>
          <button class="button" @click="write">write</button>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
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

const axios = require("axios");

export default {
  components: {
    EditorContent,
    EditorMenuBar,
    EditorMenuBubble
  },
  data() {
    return {
      obj: null,
      SelectedProblemCategory: [],
      ProblemCategory: [
        { kor: "수학", eng: "math" },
        { kor: "DP", eng: "dp" },
        { kor: "그래프", eng: "graph" },
        { kor: "자료구조", eng: "structure" },
        { kor: "그리디", eng: "greedy" },
        { kor: "문자열", eng: "string" },
        { kor: "브루트포스", eng: "bruteforce" },
        { kor: "이진탐색", eng: "binarysearch" },
        { kor: "트리", eng: "tree" },
        { kor: "정렬", eng: "sort" },
        { kor: "DFS", eng: "dfs" },
        { kor: "조합", eng: "comb" },
        { kor: "다익스트라", eng: "dijkstra" },
        { kor: "BFS", eng: "bfs" },
        { kor: "시뮬레이션", eng: "simulation" },
        { kor: "비트마스킹", eng: "bit" },
        { kor: "누적합", eng: "sum" },
        { kor: "집합", eng: "set" },
        { kor: "메모이제이션", eng: "memoization" },
        { kor: "분할정복", eng: "divide" },
        { kor: "백트래킹", eng: "backtrack" },
        { kor: "우선순위큐", eng: "priority" },
        { kor: "MST", eng: "mst" },
        { kor: "플로이드-와샬", eng: "floyd" },
        { kor: "위상정렬", eng: "topological" },
        { kor: "재귀", eng: "recursion" },
        { kor: "KMP", eng: "kmp" },
        { kor: "벨만포드", eng: "bellman" },
        { kor: "LIS", eng: "lis" },
        { kor: "순열", eng: "perm" },
        { kor: "기타", eng: "etc" }
      ],
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
          this.obj.html = getHTML();
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
  mounted() {
    // console.log(this.$route.query.score);
    const scoring = this.$route.query.score;
    // alert(scoring);
    axios
      .get("https://ssalog.gq/api/newuser/post/get_detail?Scoring=".concat(scoring))
      .then(response => {
        // handle success
        // console.log(response);
        this.obj = response.data;
        console.dir(this.obj);
        this.obj.SelectedProblemCategory = [];
        this.obj.html = "";
        const text = response.data.code;
        const transaction = this.codearea.state.tr.insertText(text);
        this.codearea.view.dispatch(transaction);
        this.codearea.commands.code_block();
      })
      .catch(function(error) {
        // handle error
        console.log(error);
      });
  },
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
    write() {
      // console.log(JSON.stringify(this.obj));
      this.obj.SelectedProblemCategory = this.SelectedProblemCategory;
      console.log(this.obj);
      axios
        .put("https://ssalog.gq/api/newuser/post/update_post", this.obj)
        .then(response => {
          console.log(response);
        })
        .catch(function(error) {
          // handle error
          console.log(error);
        });
    }
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
