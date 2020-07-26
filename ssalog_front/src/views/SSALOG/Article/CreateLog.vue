<template>
  <v-container>
    <v-row>
      <v-col cols="12" md="6">
        <div class="editor">
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
        <div class="editor">
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
          <button class="button">Submit</button>
          <button class="button">Move</button>
        </div>
      </v-col>
    </v-row>
    <h3>HTML</h3>
    <pre><code>{{ html }}</code></pre>
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

export default {
  components: {
    EditorContent,
    EditorMenuBar,
    EditorMenuBubble
  },
  data() {
    return {
      html: "<h1>gps</h1>",
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
          this.html = getHTML();
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
    const saved = JSON.parse(sessionStorage.getItem("test"));
    console.dir(saved);

    const text = saved.code;
    const transaction = this.codearea.state.tr.insertText(text);
    this.codearea.view.dispatch(transaction);
    this.codearea.commands.code_block();
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
    }
  }
};
</script>

<style lang="scss" scope>
// eliminate vuetify settings
.v-application code {
  all: unset;
  color: #eee;
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

pre {
  &::before {
    content: attr(data-language);
    text-transform: uppercase;
    display: block;
    text-align: right;
    font-weight: bold;
    font-size: 1rem;
  }
  code {
    font-size: 1rem;
    .hljs-comment,
    .hljs-quote {
      color: #999999;
    }
    .hljs-variable,
    .hljs-template-variable,
    .hljs-attribute,
    .hljs-tag,
    .hljs-name,
    .hljs-regexp,
    .hljs-link,
    .hljs-name,
    .hljs-selector-id,
    .hljs-selector-class {
      color: #f2777a;
    }
    .hljs-number,
    .hljs-meta,
    .hljs-built_in,
    .hljs-builtin-name,
    .hljs-literal,
    .hljs-type,
    .hljs-params {
      color: #f99157;
    }
    .hljs-string,
    .hljs-symbol,
    .hljs-bullet {
      color: #99cc99;
    }
    .hljs-title,
    .hljs-section {
      color: #ffcc66;
    }
    .hljs-keyword,
    .hljs-selector-tag {
      color: #6699cc;
    }
    .hljs-emphasis {
      font-style: italic;
    }
    .hljs-strong {
      font-weight: 700;
    }
  }
}
//code 부분 style
.usercode {
  border: 3px solid;
  text-align: left;
  overflow-y: scroll;
  height: 70vh;
  background-color: black;
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
  color: #aaa;
  pointer-events: none;
  height: 0;
  font-style: italic;
}

.has-focus {
  border-radius: 3px;
  box-shadow: 0 0 0 3px #3ea4ffe6;
}
</style>
