import Vue from "vue";
import VueRouter from "vue-router";
// account
import SignUp from "@/views/Account/SignUp.vue";
import Login from "@/views/Account/Login.vue";
import Account from "@/views/Account/Account.vue";
import FindId from "@/views/Account/FindId.vue";
import FindPass from "@/views/Account/FindPass.vue";
import AccountList from "@/views/Account/AccountList.vue";

// problem
import ProblemList from "@/views/Problem/ProblemList.vue";
import ProblemDetail from "@/views/Problem/ProblemDetail.vue";
// main
import Home from "@/views/Home.vue";
// editor
import WriteLog from "@/views/SSALOG/Article/WriteLog.vue";
import LogDetail from "@/views/SSALOG/Article/LogDetail.vue";
// ssalog
import SSALOG from "@/views/SSALOG/SSALOG.vue";
import SsalogMain from "@/components/SSALOG/Main.vue";
// search
import Search from "@/views/Search/Search.vue";
import store from "@/store";

Vue.use(VueRouter);

// saving former path
// const savingPath = () => (to, from, next) => {
//   console.log(from);
//   return next();
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
    component: Account,
    meta: { authRequired: true }
  },
  {
    path: "/FindId",
    name: "FindId",
    component: FindId
  },
  {
    path: "/FindPass",
    name: "FindPass",
    component: FindPass
  },
  {
    path: "/WriteLog/:id",
    name: "WriteLog",
    component: WriteLog,
    meta: { authRequired: true }
  },
  // 아래 Soultion 보다 위에 놓여야 해당 링크로 라우터가 접근 가능
  // children component 형식으로 동작하는 것이 아니기에, 이렇게 분리해주어야 함.
  {
    path: "/SSALOG/Solution/:id",
    name: "LogDetail",
    component: LogDetail
  },
  {
    path: "/SSALOG",
    name: "SSALOG",
    component: SSALOG,
    meta: { authRequired: true },
    children: [
      {
        path: "Main",
        component: SsalogMain
      }
      // {
      //   path: "Solution",
      //   name: "SolutionList",
      //   component: SolutionList
      // }
    ]
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes
});

// global guard (login required)
router.beforeEach(function(to, from, next) {
  if (
    to.matched.some(function(routeInfo) {
      return routeInfo.meta.authRequired;
    })
  ) {
    if (store.state.accessToken == null) {
      store.commit("FormerLink", to);
      next("/Login");
    } else next();
  } else {
    store.commit("FormerLink", to);
    next();
  }
});
export default router;
