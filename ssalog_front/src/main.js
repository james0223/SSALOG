import VueHighlightJS from "vue-highlightjs";
import Vue from "vue";
import axios from "axios";
import App from "./App.vue";
import router from "./router";
import store from "./store";
import vuetify from "./plugins/vuetify";
// import tiptap-style
import "./assets/tiptap/sass/main.scss";
// Tell Vue.js to use vue-highlightjs
Vue.use(VueHighlightJS);

Vue.config.productionTip = false;
Vue.prototype.$http = axios;
Vue.prototype.$serverURL = "http://i3b101.p.ssafy.io:8080";
new Vue({
  router,
  store,
  vuetify,
  render: h => h(App)
}).$mount("#app");
