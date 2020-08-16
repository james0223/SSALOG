<template>
  <div id="app">
    <v-app>
      <v-main>
        <section id="hero">
          <v-row no-gutters>
            <v-img src="@/assets/images/banner3.jpg" height="92vh" alt="메인화면 배경이미지">
              <v-theme-provider>
                <v-row class=" mx-auto" justify="center">
                  <v-col class="text-center" cols="12">
                    <vue-particles
                      color="#dedede"
                      background-color="black"
                      :particleOpacity="0.9"
                      :particlesNumber="100"
                      shapeType="circle"
                      :particleSize="3"
                      linesColor="#dedede"
                      :linesWidth="2"
                      :lineLinked="true"
                      :lineOpacity="0.4"
                      :linesDistance="200"
                      :moveSpeed="5"
                      :hoverEffect="true"
                      hoverMode="grab"
                      :clickEffect="true"
                      clickMode="push"
                      style="height: 60vh;"
                    >
                    </vue-particles>
                    <span class="font-weight-medium black--text text-end">
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
              </v-theme-provider>
              <section id="hero"></section>
            </v-img>
          </v-row>
        </section>

        <section id="about-me">
          <v-container class="text-center">
            <v-avatar class="elevation-12 mb-12 mt-10" size="150">
              <v-img src="@/assets/images/logo.jpg"></v-img>
            </v-avatar>

            <v-responsive class="mx-auto mb-12" width="100">
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
            <v-responsive class="mx-auto title font-weight-light mb-10" max-width="720">
              이제 쌀로그에서 쌀을 모아보세요
            </v-responsive>
            <div></div>
            <v-btn large fab outlined @click="$vuetify.goTo('#features')">
              <v-icon>mdi-chevron-double-down</v-icon>
            </v-btn>
          </v-container>

          <div class="py-12"></div>
        </section>

        <section id="features" class="lighten-4" v-intersect="onIntersect">
          <div class="py-10"></div>

          <v-container
            class="text-center"
            v-bind:class="[{ serviceIcon: isIntersecting }, { hidden: !isIntersecting }]"
          >
            <h2 class="display-2 font-weight-bold ma-3">서비스</h2>

            <v-responsive class="mx-auto mb-1" width="56">
              <v-divider class="mb-1"></v-divider>
              <v-divider></v-divider>
            </v-responsive>

            <v-row>
              <v-col
                v-for="({ icon, title, text, color }, i) in features"
                :key="i"
                cols="12"
                md="4"
              >
                <v-card class="py-12 px-4" flat>
                  <v-theme-provider dark>
                    <div>
                      <v-avatar :color="color" size="88">
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

          <div class="py-12"></div>
        </section>

        <section id="stats">
          <v-parallax
            :height="$vuetify.breakpoint.smAndDown ? 700 : 500"
            src="@/assets/images/home_middle.jpg"
          >
            <v-lazy
              :options="{
                threshold: 0.5
              }"
              min-height="200"
              transition="fade-transition"
            >
              <v-container fill-height>
                <v-row class="mx-auto">
                  <v-col v-for="[value, title] of stats" :key="title" cols="12" md="6">
                    <div class="text-center">
                      <ICountUp
                        class="display-4 font-weight-black mb-4"
                        :delay="delay"
                        :endVal="value"
                        :options="options"
                      />
                      <div class="title font-weight-regular " v-text="title"></div>
                    </div>
                  </v-col>
                </v-row>
              </v-container>
            </v-lazy>
          </v-parallax>
        </section>
        <section id="blog">
          <div class="py-12"></div>
          <div class="iCountUp"></div>
          <v-container>
            <h2 class="display-2 font-weight-bold mb-3 text-uppercase text-center">
              사용법
            </h2>

            <v-responsive class="mx-auto mb-12" width="56">
              <v-divider class="mb-1"></v-divider>

              <v-divider></v-divider>
            </v-responsive>

            <v-carousel
              cycle
              hide-delimiters
              height="800"
              hide-delimiter-background
              show-arrows-on-hover
            >
              <v-carousel-item v-for="slide in slides" :key="slide.id">
                <v-sheet color="black" height="100%">
                  <v-row class="fill-height" align="center" justify="center">
                    <v-img :src="slide.image" height="90%" width="100%" contain />
                    <div class="display-3">
                      <h6 black>{{ slide.desc }}</h6>
                    </div>
                  </v-row>
                </v-sheet>
              </v-carousel-item>
            </v-carousel>
          </v-container>

          <div class="py-12"></div>
        </section>
      </v-main>
      <v-btn
        v-scroll="onScroll"
        v-show="fab"
        fab
        dark
        fixed
        bottom
        right
        color="blue-grey darken-2"
        @click="toTop"
      >
        <v-icon>keyboard_arrow_up</v-icon>
      </v-btn>
      <v-footer class="font-weight-medium" height="110" color="#26272b">
        <v-col class="text-center grey--text" cols="12 ">
          2020-&copy;SSALOG
        </v-col>
        <v-col class="text-center grey--text" cols="12">
          <v-icon color="grey">mdi-email-outline</v-icon>ssalogofficial@gmail.com
        </v-col>
      </v-footer>
    </v-app>
  </div>
</template>

<script>
import axios from "axios";
import ICountUp from "vue-countup-v2";

const image1 = require("@/assets/images/howto/1.png");
const image2 = require("@/assets/images/howto/2.png");
const image3 = require("@/assets/images/howto/3.png");
const image4 = require("@/assets/images/howto/4.png");
const image5 = require("@/assets/images/howto/5.png");

export default {
  components: {
    ICountUp
  },
  data() {
    return {
      isIntersecting: false,
      fab: false,
      delay: 100,
      endVal: 30,
      options: {
        duration: 5.0,
        useEasing: true,
        useGrouping: true,
        separator: ",",
        decimal: ".",
        prefix: "",
        suffix: ""
      },
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
          title: "동기화",
          color: "light-green darken-1"
        },
        {
          icon: "mdi-account-multiple",
          title: "팔로우",
          color: "amber lighten-2"
        },
        {
          icon: "mdi-stairs",
          title: "레벨",
          color: "blue-grey lighten-1"
        },
        {
          icon: "mdi-markdown",
          title: "마크다운",
          color: "deep-purple lighten-1"
        },
        {
          icon: "mdi-eye-outline",
          title: "코드리뷰",
          color: "teal darken-3"
        },
        {
          icon: "mdi-chart-bar",
          title: "시각화",
          color: "red lighten-1"
        }
      ],
      stats: [],
      slides: [
        {
          id: "1",
          image: image1,
          desc: "1.SSALOG버튼클릭"
        },
        {
          id: "2",
          image: image2,
          desc: "2.로그인"
        },
        {
          id: "3",
          image: image3,
          desc: "3.자동 동기화"
        },
        {
          id: "4",
          image: image4,
          desc: "4.마크다운 문서 작성"
        },
        {
          id: "5",
          image: image5,
          desc: "5.작성완료"
        }
      ]
    };
  },
  methods: {
    onIntersect(entries) {
      // More information about these options
      // is located here: https://developer.mozilla.org/en-US/docs/Web/API/Intersection_Observer_API
      this.isIntersecting = entries[0].isIntersecting;
      // console.log(this.isIntersecting);
    },
    async fetchStatDatas() {
      try {
        const problemNum = await axios.get(
          `${this.$store.state.ServerURL}/newuser/search/find_allproblem`
        );
        const problem = [problemNum.data, "풀이 수"];
        const userNum = await axios.get(
          `${this.$store.state.ServerURL}/newuser/search/find_alluser`
        );
        const user = [userNum.data, "사용자 수"];
        this.stats.push(problem);
        this.stats.push(user);
        // console.log(this.stats);
      } catch (e) {
        console.error(e);
      }
    },
    onScroll(e) {
      if (typeof window === "undefined") return;
      const top = window.pageYOffset || e.target.scrollTop || 0;
      this.fab = top > 20;
    },
    toTop() {
      this.$vuetify.goTo(0);
    }
  },
  created() {
    this.fetchStatDatas();
  }
};
</script>

<style>
.hidden {
  transform: translate(9999px);
  opacity: 0;
}
.serviceIcon {
  transition: opacity 2s linear;
  transform: translate(0);
  opacity: 1;
}
</style>
