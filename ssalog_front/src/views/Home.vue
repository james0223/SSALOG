<template>
  <div id="app">
    <v-app>
      <v-content>
        <section id="hero">
          <v-row no-gutters>
            <v-img src="@/assets/images/banner3.jpg" height="850px" alt="메인화면 배경이미지">
              <v-theme-provider>
                <v-container fill-height mt-15 pl-10>
                  <v-row></v-row>
                  <v-row class="white--text mx-auto" justify="center">
                    <v-col class="text-center" cols="12" tag="h1">
                      <span
                        :class="[$vuetify.breakpoint.smAndDown ? 'display-1' : 'display-2']"
                        class="font-weight-medium black--text text-end"
                      >
                        알고리즘 풀이 정리를 간편하게
                      </span>

                      <br />

                      <span
                        :class="[$vuetify.breakpoint.smAndDown ? 'display-1' : 'display-2']"
                        class="font-weight-black black--text"
                        justify="right"
                      >
                        SSALOG
                      </span>
                    </v-col>

                    <v-btn large fab outlined @click="$vuetify.goTo('#about-me')">
                      <v-icon>mdi-chevron-double-down</v-icon>
                    </v-btn>
                  </v-row>
                </v-container>
              </v-theme-provider>
            </v-img>
          </v-row>
        </section>

        <section id="about-me">
          <div class="py-12"></div>

          <v-container class="text-center">
            <v-avatar class="elevation-12 mb-12" size="200">
              <v-img src="@/assets/images/logo.jpg"></v-img>
            </v-avatar>

            <v-responsive class="mx-auto mb-8" width="100">
              <v-divider class="mb-3"></v-divider>
              <v-divider></v-divider>
            </v-responsive>

            <v-responsive class="mx-auto title font-weight-light mb-4" max-width="720">
              쌀로그는 알고리즘 풀이 정리 블로그 입니다.
            </v-responsive>

            <v-responsive class="mx-auto title font-weight-light mb-4" max-width="720">
              구글 확장프로그램을 통해 빠르게 코드와 결과를 가져올 수 있습니다.
            </v-responsive>

            <v-responsive class="mx-auto title font-weight-light mb-4" max-width="720">
              다른 사람들의 풀이를 함께 리뷰하며 실력을 키워보세요.
            </v-responsive>
            <v-responsive class="mx-auto title font-weight-light mb-4" max-width="720"
              >쌀은 우리 몸에 영양분을 공급하고 주요한 열량원이 되어주기 때문에 사람이 살아가는데
              있어 가장 중요한 곡물이듯,
            </v-responsive>
            <v-responsive class="mx-auto title font-weight-light mb-4" max-width="720">
              알고리즘은 훌륭한 프로그래머로 성장하기위해 필요한 영양이 됩니다.
            </v-responsive>
            <v-responsive class="mx-auto title font-weight-light mb-4" max-width="720">
              이제 쌀로그에서 쌀을 모아보세요
            </v-responsive>
            <div></div>
          </v-container>

          <div class="py-12"></div>
        </section>

        <section id="features" class="lighten-4">
          <div class="py-12"></div>

          <v-container class="text-center">
            <h2 class="display-2 font-weight-bold mb-3">서비스</h2>

            <v-responsive class="mx-auto mb-1" width="56">
              <v-divider class="mb-1"></v-divider>
              <v-divider></v-divider>
            </v-responsive>

            <v-row>
              <v-col v-for="({ icon, title, text }, i) in features" :key="i" cols="12" md="4">
                <v-card class="py-12 px-4" flat>
                  <v-theme-provider dark>
                    <div>
                      <v-avatar color="black lighten-4" size="88">
                        <v-icon large v-text="icon"></v-icon>
                      </v-avatar>
                    </div>
                  </v-theme-provider>

                  <v-card-title
                    class="justify-center font-weight-black text-uppercase"
                    v-text="title"
                  ></v-card-title>

                  <v-card-text class="subtitle-1" v-text="text"> </v-card-text>
                </v-card>
              </v-col>
            </v-row>
          </v-container>

          <div class="py-12"></div>
        </section>

        <section id="stats">
          <v-parallax
            :height="$vuetify.breakpoint.smAndDown ? 700 : 500"
            src="https://images.unsplash.com/photo-1510915228340-29c85a43dcfe?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80"
          >
            <v-container fill-height>
              <v-row class="mx-auto">
                <v-col v-for="[value, title] of stats" :key="title" cols="12" md="6">
                  <div class="text-center">
                    <div class="display-3 font-weight-black mb-4" v-text="value"></div>

                    <div class="title font-weight-regular text-uppercase" v-text="title"></div>
                  </div>
                </v-col>
              </v-row>
            </v-container>
          </v-parallax>
        </section>

        <section id="blog">
          <div class="py-12"></div>

          <v-container>
            <h2 class="display-2 font-weight-bold mb-3 text-uppercase text-center">
              사용법
            </h2>

            <v-responsive class="mx-auto mb-12" width="56">
              <v-divider class="mb-1"></v-divider>

              <v-divider></v-divider>
            </v-responsive>

            <v-carousel cycle height="400" hide-delimiter-background show-arrows-on-hover>
              <v-carousel-item v-for="(slide, i) in slides" :key="i">
                <v-sheet :color="colors[i]" height="100%">
                  <v-row class="fill-height" align="center" justify="center">
                    <div class="display-3">{{ slide }} Slide</div>
                  </v-row>
                </v-sheet>
              </v-carousel-item>
            </v-carousel>
          </v-container>

          <div class="py-12"></div>
        </section>
      </v-content>

      <v-footer class="justify-center" color="#292929" height="100">
        <div class="title font-weight-light grey--text text--lighten-1 text-center">
          &copy; none.vat
        </div>
      </v-footer>
    </v-app>
  </div>
</template>

<script>
export default {
  data() {
    return {
      articles: [
        {
          src:
            "https://images.unsplash.com/photo-1423784346385-c1d4dac9893a?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80",
          title: "Mobile first & Responsive",
          text:
            "Phasellus lorem enim, luctus ut velit eget, convallis egestas eros. Sed ornare ligula eget tortor tempor, quis porta tellus dictum."
        },
        {
          src:
            "https://images.unsplash.com/photo-1475938476802-32a7e851dad1?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1950&q=80",
          title: "Think outside the box",
          text:
            "Nam ut leo ipsum. Maecenas pretium aliquam feugiat. Aenean vel tempor est, vitae tincidunt risus. Sed sodales vestibulum nibh."
        },
        {
          src:
            "https://images.unsplash.com/photo-1416339442236-8ceb164046f8?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=1892&q=80",
          title: "Small changes, big difference",
          text:
            "Vestibulum in dictum velit, in rhoncus nibh. Maecenas neque libero, interdum a dignissim in, aliquet vitae lectus. Phasellus lorem enim, luctus ut velit eget."
        }
      ],
      features: [
        {
          icon: "mdi-autorenew",
          title: "동기화"
        },
        {
          icon: "mdi-account-multiple",
          title: "팔로우"
        },
        {
          icon: "mdi-stairs",
          title: "레벨"
        },
        {
          icon: "mdi-markdown",
          title: "마크다운"
        },
        {
          icon: "mdi-eye-outline",
          title: "코드리뷰"
        },
        {
          icon: "mdi-chart-bar",
          title: "시각화"
        }
      ],
      stats: [
        ["24k", "풀이 수"],
        ["330+", "사용자 수"]
      ],
      colors: ["indigo", "warning", "pink darken-2", "red lighten-1", "deep-purple accent-4"],
      slides: ["First", "Second", "Third", "Fourth", "Fifth"]
    };
  },
  components: {}
};
</script>
