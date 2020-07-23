import Vue from "vue";
import VueRouter from "vue-router";
import SignUp from "@/views/Account/SignUp.vue";
import Login from "@/views/Account/Login.vue";
import FindPass from "@/views/Account/FindPass.vue";

Vue.use(VueRouter);

const routes = [
  {
    path: "/SignUp",
    name: "SignUp",
    component: SignUp
  },
  {
    path: "/Login",
    name: "Login",
    component: Login
  },
  {
    path: "/FindPass",
    name: "FindPass",
    component: FindPass
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
