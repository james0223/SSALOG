import Vue from "vue";
import VueRouter from "vue-router";
// account
import SignUp from "@/views/Account/SignUp.vue";
import Login from "@/views/Account/Login.vue";
import FindId from "@/views/Account/FindId.vue";
import FindPass from "@/views/Account/FindPass.vue";
import AccountList from "@/views/Account/AccountList.vue";
// problem
import Problem from "@/views/Problem/Problem.vue";
import ProblemList from "@/components/Problem/ProblemList.vue";
import ProblemDetail from "@/components/Problem/ProblemDetail.vue";
// main
import Home from "@/views/Home.vue";
import Page404 from "@/views/Page404.vue";
import Naruto from "@/views/narutoHome.vue";
import Index from "@/views/Main.vue";
// editor
import WriteLog from "@/views/SSALOG/Article/WriteLog.vue";
import LogDetail from "@/views/SSALOG/Article/LogDetail.vue";
// ssalog
import SSALOG from "@/views/SSALOG/SSALOG.vue";
import Profile from "@/components/SSALOG/Profile.vue";
import Scrap from "@/components/SSALOG/Scrap.vue";
import Follow from "@/components/SSALOG/Follow.vue";
import SsalogMain from "@/components/SSALOG/Main.vue";
import SolutionList from "@/components/SSALOG/SolutionList.vue";
import UserSetting from "@/components/SSALOG/Setting.vue";
// group
import Group from "@/views/Group/Group.vue";
import Member from "@/components/Group/Member.vue";
import GroupHW from "@/components/Group/Homework.vue";
import FormerHW from "@/components/Group/FormerHW.vue";
import GroupManage from "@/components/Group/Manage.vue";
import GroupList from "@/components/SSALOG/GroupList.vue";
// search
import Search from "@/views/Search/Search.vue";
import store from "@/store";

Vue.use(VueRouter);

const routes = [
  {
    path: "*",
    redirect: "/404"
  },
  {
    path: "/Naruto",
    name: "Naruto",
    component: Naruto
  },
  {
    path: "/Index",
    name: "Index",
    component: Index
  },
  {
    path: "/Problem",
    name: "Problem",
    component: Problem,
    children: [
      {
        path: "List/",
        name: "ProblemList",
        component: ProblemList
      },
      {
        path: ":id/detail",
        name: "ProblemDetail",
        component: ProblemDetail
      }
    ]
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
    path: "/404",
    name: "404",
    component: Page404
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
    path: "/Group/:groupname/",
    name: "Group",
    component: Group,
    meta: { authRequired: true },
    children: [
      {
        path: "",
        name: "Member",
        component: Member
      },
      {
        path: "ExpiredHomeWork",
        name: "ExpiredHW",
        component: FormerHW
      },
      {
        path: "Homework",
        name: "GroupHW",
        component: GroupHW
      },
      {
        path: "Manage",
        name: "Manage",
        component: GroupManage
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
    path: "/SSALOG/:nickname",
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
      },
      {
        path: "UserSetting",
        name: "UserSetting",
        component: UserSetting,
        meta: { authRequired: true }
      },
      {
        path: "Solution/:id",
        name: "LogDetail",
        component: LogDetail
      },
      {
        path: "GroupList",
        name: "GroupList",
        component: GroupList,
        meta: { authRequired: true }
      },
      {
        path: "Profile",
        name: "Profile",
        component: Profile
      },
      {
        path: "Scrap",
        name: "Scrap",
        component: Scrap
      },
      {
        path: "Follow",
        name: "Follow",
        component: Follow
      }
    ]
  }
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes,
  scrollBehavior() {
    return { x: 0, y: 0 };
  }
});

// global guard (login required)
router.beforeEach(function(to, from, next) {
  // 같은 이름으로 라우팅이 안되는 문제 해결
  // console.log(to);
  // console.log(from);
  // console.log(next);
  if (from.name === to.name) {
    // console.log("음..");
    next();
  } else if (
    to.matched.some(function(routeInfo) {
      return routeInfo.meta.authRequired;
    })
  ) {
    if (store.state.accessToken == null && store.state.refreshToken == null) {
      // eslint-disable-next-line
      !(to.path === "/Login") && store.commit("FormerLink", to.path);
      store.commit("ShowAlert", {
        flag: true,
        msg: "로그인이 필요합니다",
        color: "error"
      });
      setTimeout(() => {
        store.commit("ShowAlert", {
          flag: false,
          msg: "",
          color: ""
        });
        next("/Login");
      }, 700);
    } else next();
  } else {
    if (to.name !== "Login") {
      // eslint-disable-next-line
      !(to.path === "/Login") && store.commit("FormerLink", to.path);
    }
    next();
  }
});

export default router;
