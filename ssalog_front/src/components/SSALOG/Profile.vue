<template>
  <v-container>
    <v-row>
      <v-col cols="12">
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
          <editor-content class="editor__content article" :editor="editor" />
          <div id="example-3">
            <br />
          </div>
          <v-btn color="info" tile block @click="write"> 작성하기</v-btn>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>
<script>
import { mapState } from "vuex";
import { Editor, EditorContent, EditorMenuBar } from "tiptap";
import {
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
// import axios from "axios";

export default {
  name: "Profile",
  components: {
    EditorContent,
    EditorMenuBar
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
      })
    };
  },
  computed: mapState(["ServerURL", "nickname", "username"])
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
