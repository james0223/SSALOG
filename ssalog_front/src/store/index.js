import Vue from "vue";
import Vuex from "vuex";
import axios from "axios";
// eslint-disable-next-line
import jwt_decode from "jwt-decode";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);

const getDefaultState = () => {
  return {
    isDark: false,
    ColorSet: {
      Prime: "#2556f8", // 파랑
      Mid: "#8a50db", // 보라
      Sub: "#8dffb3" // 초록
    },
    // 유저 정보 관리용 변수
    accessToken: null,
    refreshToken: null,
    ImgURL: "https://ssalog.gq/upload/",
    // ServerURL: "http://127.0.0.1:8080",
    ServerURL: "https://ssalog.gq/api",
    // user data
    nickname: null,
    username: null, // 이메일
    userThumbnail: null,
    ProblemCategory: [
      { kor: "수학", eng: "math" },
      { kor: "DP", eng: "dp" },
      { kor: "그래프", eng: "graph" },
      { kor: "자료구조", eng: "structure" },
      { kor: "그리디", eng: "greedy" },
      { kor: "문자열", eng: "string" },
      { kor: "브루트포스", eng: "bruteforce" },
      { kor: "이진탐색", eng: "binarysearch" },
      { kor: "트리", eng: "tree" },
      { kor: "정렬", eng: "sort" },
      { kor: "DFS", eng: "dfs" },
      { kor: "조합", eng: "comb" },
      { kor: "다익스트라", eng: "dijkstra" },
      { kor: "BFS", eng: "bfs" },
      { kor: "시뮬레이션", eng: "simulation" },
      { kor: "비트마스킹", eng: "bit" },
      { kor: "누적합", eng: "sum" },
      { kor: "집합", eng: "set" },
      { kor: "메모이제이션", eng: "memoization" },
      { kor: "분할정복", eng: "divide" },
      { kor: "백트래킹", eng: "backtrack" },
      { kor: "우선순위큐", eng: "priority" },
      { kor: "MST", eng: "mst" },
      { kor: "플로이드-와샬", eng: "floyd" },
      { kor: "위상정렬", eng: "topological" },
      { kor: "재귀", eng: "recursion" },
      { kor: "KMP", eng: "kmp" },
      { kor: "벨만포드", eng: "bellman" },
      { kor: "LIS", eng: "lis" },
      { kor: "순열", eng: "perm" },
      { kor: "기타", eng: "etc" }
    ],
    // snackbar 관련 옵션
    alertColor: null,
    AlertMessage: "",
    showAlert: false,

    // 라우팅용 변수
    formerLink: null
  };
};

// initial state
// const state = getDefaultState();

export default new Vuex.Store({
  state: getDefaultState(),
  plugins: [createPersistedState()],
  mutations: {
    NicknameChange(state, payload) {
      state.nickname = payload;
    },
    ChangeDark(state) {
      state.isDark = !state.isDark;
    },
    LOGIN(state, payload) {
      state.nickname = payload.nickname;
      state.username = payload.username;
    },
    LOGOUT(state) {
      const bef = state.isDark;
      Object.assign(state, getDefaultState());
      if (bef) {
        state.isDark = true;
      }
    },
    TOKEN(state, payload) {
      const { accessToken, refreshToken } = payload;
      state.accessToken = accessToken;
      state.refreshToken = refreshToken;
      // 앞으로의 모든 HTTP 요청 헤더에 Auth 추가
      axios.defaults.headers.common.Authorization = `Bearer ${state.accessToken}`;
    },
    Thumbnail(state, payload) {
      state.userThumbnail = `${state.ImgURL}${payload}`;
    },
    FormerLink(state, payload) {
      state.formerLink = payload;
    },
    ShowAlert(state, payload) {
      state.AlertMessage = payload.msg;
      state.alertColor = payload.color;
      state.showAlert = payload.flag;
    }
  },
  actions: {
    async LOGIN({ commit, dispatch }, loginData) {
      axios.defaults.headers.common.Authorization = ``;

      const res = await axios.post(`${this.state.ServerURL}/newuser/login`, null, {
        params: {
          ...loginData
        }
      });
      if (res.data === "") {
        // eslint-disable-next-line
        await Promise.reject("비밀번호가 다릅니다");
      } else {
        commit("LOGIN", {
          nickname: res.data.nickname,
          username: loginData.username
        });
        commit("TOKEN", {
          accessToken: res.data.accessToken,
          refreshToken: res.data.refreshToken
        });
        dispatch("Thumbnail", loginData.username);
      }
    },
    async LOGOUT({ commit }) {
      await axios.post(`${this.state.ServerURL}/user/out`);
      commit("LOGOUT");
      commit("ShowAlert", {
        flag: true,
        msg: "로그아웃 되었습니다.",
        color: "info"
      });
      setTimeout(() => {
        commit("ShowAlert", {
          flag: false,
          msg: ""
        });
        window.location.reload();
      }, 1000);
    },
    async SIGNUP({ dispatch }, signupData) {
      const { data } = await axios.post(`${this.state.ServerURL}/newuser/add`, signupData);
      if (data.success === true) {
        const loginData = { username: signupData.username, password: signupData.password };
        dispatch("LOGIN", loginData);
      } else {
        console.log("자동로그인중 오류 발생");
      }
    },
    async Thumbnail({ commit }, payload) {
      const res = await axios.get(`${this.state.ServerURL}/newuser/get_profile_img`, {
        params: {
          username: payload
        }
      });
      commit("Thumbnail", res.data);
      window.location.reload();
    }
  },
  modules: {}
});
