import Vue from "vue";
import VueRouter from "vue-router";
import SignUp from "@/views/Account/SignUp.vue";
import Login from "@/views/Account/Login.vue";

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
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

// // login required
// const requireAuth = () => (from, to, next) => {
//   const isAuthenticated = false;
//   if (isAuthenticated) return next();
//   next("");
// };
export default router;
