import Vue from "vue";
import VueRouter from "vue-router";
// import store from "@/store";
// account
import SignUp from "@/views/Account/SignUp.vue";
import Login from "@/views/Account/Login.vue";
import Account from "@/views/Account/Account.vue";
import FindPass from "@/views/Account/FindPass.vue";
import AccountList from "@/views/Account/AccountList.vue";

// problem
import ProblemList from "@/views/Problem/ProblemList.vue";
import ProblemDetail from "@/views/Problem/ProblemDetail.vue";
// main
import Home from "@/views/Home.vue";
// editor
import CreateLog from "@/views/SSALOG/Article/CreateLog.vue";
// ssalog
import SSALOG from "@/views/SSALOG/SSALOG.vue";
// search
import Search from "@/views/Search/Search.vue";

Vue.use(VueRouter);

// login required
// const requireAuth = () => (to, from, next) => {
//   if (store.state.accessToken !== null) {
//     return next();
//   }
//   return next("/Login");
// };
const routes = [
  {
    path: "/Problem/List/",
    name: "ProblemList",
    component: ProblemList
  },
  {
    path: "/Problem/detail/",
    name: "ProblemDetail",
    component: ProblemDetail
  },
  {
    path: "/Account/List/",
    name: "AccountList",
    component: AccountList
  },
  {
    path: "/Search",
    name: "Search",
    component: Search
  },
  {
    path: "/",
    name: "Home",
    component: Home
    // beforeEnter: requireAuth()
  },
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
    path: "/Account",
    name: "Account",
    component: Account
    // beforeEnter: requireAuth()
  },
  {
    path: "/FindPass",
    name: "FindPass",
    component: FindPass
  },
  {
    path: "/CreateLog",
    name: "CreateLog",
    component: CreateLog
    // beforeEnter: requireAuth()
  },
  {
    path: "/SSALOG",
    name: "SSALOG",
    component: SSALOG
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

export default router;
