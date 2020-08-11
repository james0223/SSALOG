import Vue from "vue";
import axios from "axios";
// eslint-disable-next-line
import App from "./App.vue";
// import tiptap-style
import "./assets/tiptap/sass/main.scss";
// eslint-disable-next-line
import Highlight from "vue-markdown-highlight";
import "highlight.js/styles/github.css";
// countdown;
// eslint-disable-next-line
import VueCountdown from "@chenfengyuan/vue-countdown";

import { ValidationObserver, ValidationProvider, extend, localize } from "vee-validate";
import ko from "vee-validate/dist/locale/ko.json";
import * as rules from "vee-validate/dist/rules";
import vuetify from "./plugins/vuetify";
import store from "./store";
import router from "./router";

Object.keys(rules).forEach(rule => {
  extend(rule, rules[rule]);
});
localize("ko", ko);
// Install components globally
Vue.component("ValidationObserver", ValidationObserver);
Vue.component("ValidationProvider", ValidationProvider);
// Register it globally
Vue.component(VueCountdown.name, VueCountdown);
Vue.use(Highlight);

Vue.config.productionTip = false;
Vue.prototype.$http = axios;
new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount("#app");
