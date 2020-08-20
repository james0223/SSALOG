import Vue from "vue";
import axios from "axios";
// eslint-disable-next-line
import App from "./App.vue";

import VueClipboard from "vue-clipboard2";

// import tiptap-style
import "./assets/tiptap/sass/main.scss";
// eslint-disable-next-line
// import Highlight from "vue-markdown-highlight";
import "highlight.js";
// countdown;
// eslint-disable-next-line
import VueCountdown from "@chenfengyuan/vue-countdown";
// vue-particles
import VueParticles from "vue-particles";

import { ValidationObserver, ValidationProvider, extend, localize } from "vee-validate";
import ko from "vee-validate/dist/locale/ko.json";
import * as rules from "vee-validate/dist/rules";

import DatetimePicker from "vuetify-datetime-picker";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
import "material-design-icons-iconfont/dist/material-design-icons.css";

Object.keys(rules).forEach(rule => {
  extend(rule, rules[rule]);
});
localize("ko", ko);

/* eslint-enable */
// Install components globally
Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);
// Register it globally
Vue.component(VueCountdown.name, VueCountdown);
// Vue.use(Highlight);
Vue.use(VueClipboard);
VueClipboard.config.autoSetContainer = true;
Vue.use(DatetimePicker);
Vue.config.productionTip = false;
Vue.prototype.$http = axios;
Vue.use(VueParticles);
new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount("#app");

// auto refresh
/* eslint-disable */
axios.interceptors.response.use(
  function(response) {
    return response;
  },
  async function(error) {
    // 9999 에러나 401 에러가 발생하고, 리프레시 토큰이 있을때

    if (
      (error.response.status === 9999 || error.response.status === 401) &&
      store.state.refreshToken
    ) {
      try {
        const refreshToken = store.state.refreshToken;
        axios.defaults.headers.common.accessToken = null;
        const res = await axios.post(`${store.state.ServerURL}/newuser/refresh`, null, {
          headers: {
            refreshToken: `Bearer ${refreshToken}`
          }
        });
        console.log("리프레시 토큰", refreshToken);
        console.log("재요청 결과", res);
        if (res.success === true) {
          store.commit("TOKEN", { accessToken: res.data.accessToken, refreshToken });
          axios.defaults.headers.common.accessToken = res.data.accessToken;
          error.config.headers["Authorization"] = `Bearer ${res.data.accessToken}`;
          return axios(error.config);
        } else {
          store.commit("LOGOUT");
          store.commit("ShowAlert", {
            flag: true,
            msg: "리프레시 토큰이 만료되었습니다. 다시 로그인하세요",
            color: "error"
          });
          setTimeout(() => {
            store.commit("ShowAlert", {
              flag: false,
              msg: ""
            });
            router.push("/Login");
          }, 1000);
        }
      } catch (e) {
        // 리프레시 토큰이 만료되었거나 뭔가 정상이 아닐때
        store.commit("LOGOUT");
        store.commit("ShowAlert", {
          flag: true,
          msg: "리프레시 토큰이 만료되었습니다. 다시 로그인하세요",
          color: "error"
        });
        setTimeout(() => {
          store.commit("ShowAlert", {
            flag: false,
            msg: ""
          });
          router.push("/Login");
        }, 1000);
      }
      // window.location.reload();
    }
  }
);
