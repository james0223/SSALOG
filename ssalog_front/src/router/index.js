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
import SolutionList from "@/components/SSALOG/SolutionList.vue";
// group
import Group from "@/views/Group/Group.vue";
import GroupMain from "@/components/Group/GroupMain.vue";
import GroupList from "@/components/Group/GroupList.vue";
import GroupHW from "@/components/Group/Homework.vue";
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
    path: "/Problem/:id",
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
    path: "/Group/:nickname/",
    name: "Group",
    component: Group,
    // meta: { authRequired: true },
    children: [
      {
        path: "",
        name: "Main",
        component: GroupMain
      },
      {
        path: "GroupList",
        name: "GroupList",
        component: GroupList
      },
      {
        path: "Homework",
        name: "GroupHW",
        component: GroupHW
      },
      {
        path: "GroupManage"
      }
    ]
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
    path: "/SSALOG/:username/Solution/:id",
    name: "LogDetail",
    component: LogDetail
  },
  {
    path: "/SSALOG/:username",
    name: "SSALOG",
    component: SSALOG,
    // meta: { authRequired: true },
    children: [
      {
        path: "Main",
        name: "SSalogMain",
        component: SsalogMain
      },
      {
        path: "SolutionList",
        name: "SolutionList",
        component: SolutionList
      }
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
  // 같은 이름으로 라우팅이 안되는 문제 해결
  console.log(to, "to");
  console.log(from, "from");
  if (from.name === to.name) {
    next();
  }
  if (
    to.matched.some(function(routeInfo) {
      return routeInfo.meta.authRequired;
    })
  ) {
    if (store.state.accessToken == null) {
      store.commit("FormerLink", to.path);
      next("/Login");
    } else next();
  } else {
    if (to.name !== "Login") {
      store.commit("FormerLink", to.path);
    }
    next();
  }
});
export default router;
