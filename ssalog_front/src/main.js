import Vue from "vue";
import axios from "axios";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
// import tiptap-style
import "./assets/tiptap/sass/main.scss";
// eslint-disable-next-line
import Highlight from "vue-markdown-highlight";
import "highlight.js/styles/github.css";

Vue.use(Highlight);

Vue.config.productionTip = false;
Vue.prototype.$http = axios;
Vue.prototype.$serverURL = "http://i3b101.p.ssafy.io:8080";
new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount("#app");
