<template>
  <v-card flat color="transparent" height="60vh" class="pa-8">
    <v-card-title>
      <h3 class="font-weight-light mb-3">비밀번호 변경</h3>
      <v-spacer></v-spacer>
      <v-btn fab color="secondary" dark @click="showPwChange = !showPwChange">
        <v-icon>{{ showPwChange ? "mdi-chevron-up" : "mdi-chevron-down" }}</v-icon>
      </v-btn>
    </v-card-title>
    <v-card-text>
      <v-expand-transition>
        <div v-show="showPwChange">
          <v-card-text>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="새 비밀번호"
                  type="password"
                  v-model="changePw.data"
                  required
                ></v-text-field>
              </v-col>
              <v-col cols="12">
                <v-text-field
                  label="비밀번호 확인"
                  type="password"
                  v-model="changePw.check"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <small class="warn">{{ changePw.error }}</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" text @click="changePassword">변경</v-btn>
          </v-card-actions>
        </div>
      </v-expand-transition>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-title>
      <h3 class="font-weight-light mb-3">닉네임 변경</h3>
      <v-spacer></v-spacer>
      <v-btn fab color="secondary" dark @click="showNickChange = !showNickChange">
        <v-icon>{{ showNickChange ? "mdi-chevron-up" : "mdi-chevron-down" }}</v-icon>
      </v-btn>
    </v-card-title>
    <v-card-text>
      <v-expand-transition>
        <div v-show="showNickChange">
          <v-card-text>
            <v-row>
              <v-col cols="12">
                <v-text-field
                  label="새 닉네임"
                  type="text"
                  v-model="changeNick.data"
                  required
                ></v-text-field>
              </v-col>
            </v-row>
            <small class="warn">{{ changeNick.error }}</small>
          </v-card-text>
          <v-card-actions>
            <v-spacer></v-spacer>
            <v-dialog v-model="changeNick.dialog" max-width="60vh">
              <v-card>
                <v-card-title>
                  <h5>{{ changeNick.data }} (은)는 사용가능한 닉네임입니다!</h5>
                </v-card-title>
                <v-card-text>닉네임을 변경하시겠어요?</v-card-text>
                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-btn color="green darken-1" text @click="changeNickname">변경</v-btn>
                  <v-btn color="grey" text @click="changeNick.dialog = false">취소</v-btn>
                </v-card-actions>
              </v-card>
            </v-dialog>
          </v-card-actions>
        </div>
      </v-expand-transition>
    </v-card-text>
    <v-divider></v-divider>
    <v-card-title>
      <h3 class="font-weight-light mb-3">양식 변경</h3>
      <v-spacer></v-spacer>

      <div class="text-center">
        <v-dialog v-model="changeForm.dialog" width="800">
          <template v-slot:activator="{ on, attrs }">
            <v-btn color="secondary" fab dark v-bind="attrs" v-on="on" @click="getForm">
              +
            </v-btn>
          </template>

          <v-card>
            <v-card-text>
              <editor-menu-bar
                :editor="editor"
                v-slot="{ commands, isActive }"
                style="border-bottom:1px solid black"
              >
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
              <editor-content
                class="editor__content article"
                :editor="editor"
                style="border :none"
              />
            </v-card-text>

            <v-divider></v-divider>

            <v-card-actions>
              <v-spacer></v-spacer>
              <v-btn color="primary" text @click="saveForm">
                저장
              </v-btn>
              <v-btn color="primary" text @click="changeForm.dialog = false">
                취소
              </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
      </div>
    </v-card-title>
    <v-divider></v-divider>
  </v-card>
</template>

<script>
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

export default {
  name: "Setting",
  components: {
    EditorContent,
    EditorMenuBar
  },
  data() {
    return {
      showPwChange: false,
      showNickChange: false,
      showFormChange: false,
      changePw: {
        data: null,
        check: null,
        error: null
      },
      changeNick: {
        data: null,
        error: null,
        dialog: false
      },
      changeForm: {
        data: null,
        error: null,
        dialog: false
      },
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
        content: `test`,
        autoFocus: true
      })
    };
  },
  methods: {
    async getForm() {
      this.changeForm.dialog = true;
      try {
        const res = await this.$http.get(`${this.$store.state.ServerURL}/newuser/get_form`, {
          params: {
            nickname: this.$route.params.nickname
          }
        });
        // console.log(res);
        this.editor.setContent(res.data);
      } catch (e) {
        console.error(e);
      }
    },
    async saveForm() {
      try {
        const html = this.editor.getHTML();
        await this.$http.post(`${this.$store.state.ServerURL}/user/write_form`, null, {
          params: {
            form: html
          }
        });
        this.changeForm.dialog = false;
      } catch (e) {
        console.error(e);
      }
    },
    async checkNickname() {
      this.changeNick.error = null;
      if (!this.changeNick.data) {
        this.changeNick.error = "변경할 닉네임을 입력해주세요";
      } else {
        try {
          const { data } = await this.$http.get(
            `${this.$store.state.ServerURL}/newuser/checkNickname`,
            {
              params: {
                nickname: this.changeNick.data
              }
            }
          );
          if (data) {
            this.changeNick.dialog = true;
          } else {
            this.changeNick.error = "해당 닉네임은 이미 사용중입니다.";
          }
        } catch (e) {
          console.error(e);
          this.changeNick.error = "Server error";
        }
      }
    },
    async changeNickname() {
      this.changeNick.error = null;
      try {
        const { data } = await this.$http.put(
          `${this.$store.state.ServerURL}/user/change_nickname`,
          null,
          {
            params: {
              nickname: this.changeNick.data
            }
          }
        );
        this.changeNick.dialog = false;
        if (data === "sucess") {
          this.$store.commit("NicknameChange", this.changeNick.data);
          this.$store.commit("ShowAlert", {
            flag: true,
            msg: "닉네임이 성공적으로 변경되었습니다!",
            color: "info"
          });
          this.$router.push({
            name: "UserSetting",
            params: { nickname: this.changeNick.data }
          });
          setTimeout(() => {
            this.$store.commit("ShowAlert", { flag: false, msg: "" });
          }, 2000);
        } else {
          this.changeNick.error = "해당 닉네임은 이미 사용중입니다.";
        }
      } catch (e) {
        console.error(e);
        this.changeNick.error = "Server error";
      }
    },
    async changePassword() {
      this.changePw.error = null;
      if (!this.changePw.data || !this.changePw.check) {
        this.changePw.error = "새 비밀번호, 확인문자를 입력해주세요";
      } else if (this.changePw.data !== this.changePw.check) {
        this.changePw.error = "새 비밀번호와 확인문자가 일치하지 않습니다.";
      } else {
        try {
          const { data } = await this.$http.put(
            `${this.$store.state.ServerURL}/user/change_pw`,
            null,
            {
              params: {
                password: this.changePw.data
              }
            }
          );
          if (data) {
            this.$store.commit("ShowAlert", {
              flag: true,
              msg: "비밀번호가 성공적으로 변경되었습니다!",
              color: "info"
            });
            setTimeout(() => {
              this.$store.commit("ShowAlert", { flag: false, msg: "" });
            }, 2000);
          }
        } catch (e) {
          this.changePw.error = "server error.";
          console.error(e);
        }
      }
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

.warn {
  color: red;
}
</style>
