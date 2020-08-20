<template>
  <v-container min-height="80vh">
    <v-row>
      <v-col cols="12">
        <div class="editor" spellcheck="false">
          <div
            class="checkbox"
            style="float:right"
            v-if="$store.state.nickname === $route.params.nickname"
          >
            <v-btn @click="modeClick" :text="editable" :color="modeColor" class="white--text">{{
              mode
            }}</v-btn>
            <!-- <input type="checkbox" id="editable" v-model="editable" /> -->
            <!-- <label for="editable">수정하기</label> -->
          </div>

          <div class="checkbox" style="float:right" v-if="editable">
            <v-btn @click="write" text color="green" class="white--text">저장</v-btn>
            <!-- <input type="checkbox" id="editable" v-model="editable" /> -->
            <!-- <label for="editable">수정하기</label> -->
          </div>

          <editor-menu-bar :editor="editor" v-slot="{ commands, isActive }">
            <div
              class="is-hidden"
              :class="{ 'is-focused': editable, menubar_dark: isDark, menubar: !isDark }"
            >
              <button
                :class="{
                  'is-active': isActive.bold(),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.bold"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/bold.svg"
                />
              </button>

              <button
                :class="{
                  'is-active': isActive.italic(),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.italic"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/italic.svg"
                />
              </button>

              <button
                :class="{
                  'is-active': isActive.strike(),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.strike"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/strike.svg"
                />
              </button>

              <button
                :class="{
                  'is-active': isActive.underline(),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.underline"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/underline.svg"
                />
              </button>

              <button
                :class="{
                  'is-active': isActive.code(),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.code"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/code.svg"
                />
              </button>

              <button
                :class="{
                  'is-active': isActive.paragraph(),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.paragraph"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/paragraph.svg"
                />
              </button>

              <button
                :class="{
                  'is-active': isActive.heading({ level: 1 }),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.heading({ level: 1 })"
              >
                H1
              </button>

              <button
                :class="{
                  'is-active': isActive.heading({ level: 2 }),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.heading({ level: 2 })"
              >
                H2
              </button>

              <button
                :class="{
                  'is-active': isActive.heading({ level: 3 }),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.heading({ level: 3 })"
              >
                H3
              </button>

              <button
                :class="{
                  'is-active': isActive.bullet_list(),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.bullet_list"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/ul.svg"
                />
              </button>

              <button
                :class="{
                  'is-active': isActive.ordered_list(),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.ordered_list"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/ol.svg"
                />
              </button>

              <button
                :class="{
                  'is-active': isActive.blockquote(),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.blockquote"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/quote.svg"
                />
              </button>

              <button
                :class="{
                  'is-active': isActive.code_block(),
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.code_block"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/code.svg"
                />
              </button>

              <button
                :class="{
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.horizontal_rule"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/hr.svg"
                />
              </button>

              <button
                :class="{
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.undo"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/undo.svg"
                />
              </button>

              <button
                :class="{
                  menubar_dark__button: isDark,
                  menubar__button: !isDark
                }"
                @click="commands.redo"
              >
                <img
                  :class="{ 'image-to-dark': isDark }"
                  class="icon"
                  src="@/assets/tiptap/icons/redo.svg"
                />
              </button>
            </div>
          </editor-menu-bar>

          <editor-content class="editor__content article" :editor="editor" style="border :none" />
          <div id="example-3">
            <br />
          </div>
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
import axios from "axios";
import "@/assets/Main.css";

export default {
  name: "Profile",
  components: {
    EditorContent,
    EditorMenuBar
  },
  data() {
    return {
      modeColor: "primary",
      mode: "수정하기",
      editable: false,
      title: null,
      html: null,
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
        content: `<h1>안녕하세요 ${this.$route.params.nickname}입니다 😁<br></h1><h3>자기소개</h3><hr><p></p><h3>기술스택</h3><hr><p></p><h3>수상경력</h3><hr><p></p><h3>SNS</h3><hr>`,
        autoFocus: true,
        editable: false
      })
    };
  },
  computed: mapState(["ServerURL", "nickname", "username", "isDark"]),
  watch: {
    editable() {
      this.editor.setOptions({
        editable: this.editable
      });
      if (this.mode === "수정하기") {
        this.mode = "취소";
        this.modeColor = "red";
      } else {
        this.modeColor = "primary";
        this.$router.go();
        this.mode = "수정하기";
      }
    }
  },
  methods: {
    modeClick() {
      this.editable = !this.editable;
    },
    write() {
      this.html = this.editor.getHTML();
      axios
        .post(`${this.ServerURL}/user/write_introduce`, {}, { params: { introduce: this.html } })
        .then(() => {
          this.editable = false;
        })
        .catch(function(error) {
          console.log(error);
        });
    },
    async get() {
      try {
        const res = await axios.get(`${this.ServerURL}/newuser/get_introduce`, {
          params: {
            nickname: this.$route.params.nickname
          }
        });
        if (res.data !== "" && res.data !== "fail") {
          this.editor.setContent(res.data);
        }
      } catch (e) {
        console.error(e);
      }
    }
  },
  created() {
    this.get();
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

// article 부분 style
.article {
  padding: 15px 10px;
  border: 3px solid;
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
