import Vue from "vue";
import Vuex from "vuex";
import Axios from "axios";
// eslint-disable-next-line
import jwt_decode from "jwt-decode";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    // 라우팅용 변수
    formerLink: null,
    // 유저 정보 관리용 변수
    accessToken: null,
    refreshToken: null,
    ImgURL: "https://ssalog.gq/upload/",
    // ServerURL: "http://127.0.0.1:8080",
    ServerURL: "https://ssalog.gq/api",
    // user data
    username: null,
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
    ]
  },
  plugins: [createPersistedState()],
  getters: {},
  mutations: {
    LOGIN(state, payload) {
      state.username = payload.username;
    },
    LOGOUT(state) {
      state.accessToken = null;
      state.refreshToken = null;
      state.username = null;
      state.userThumbnail = null;
    },
    TOKEN(state, payload) {
      const { accessToken, refreshToken } = payload;
      state.accessToken = accessToken;
      state.refreshToken = refreshToken;
      // 앞으로의 모든 HTTP 요청 헤더에 Auth 추가
      Axios.defaults.headers.common.Authorization = `Bearer ${state.accessToken}`;
    },
    Thumbnail(state, payload) {
      // jso 하드타이핑 나중에 수정 필요.
      state.userThumbnail = `${state.ImgURL}${payload}`;
    },
    FormerLink(state, payload) {
      state.formerLink = payload;
    }
  },
  actions: {
    async LOGIN({ commit, dispatch }, loginData) {
      // 이해안될까봐 남겨놓는다.
      // const options = {
      //   params: {
      //     이게 아래 선언한거랑 같은 기능을 한다.
      //     ...loginData

      //     // username: loginData.username,
      //     // password: loginData.password
      //   }
      // };
      const res = await Axios.post(`${this.state.ServerURL}/newuser/login`, null, {
        params: {
          ...loginData
        }
      });
      commit("LOGIN", {
        username: loginData.username
      });
      commit("TOKEN", {
        accessToken: res.data.accessToken,
        refreshToken: res.data.refreshToken
      });
      await dispatch("Thumbnail", loginData.username);
      dispatch("autoRefresh");
    },
    // eslint-disable-next-line
    autoRefresh({ state, dispatch, commit }) {
      const { accessToken, refreshToken } = state;
      const { exp } = jwt_decode(accessToken);
      const now = Date.now() / 1000;
      // eslint-disable-next-line
      let timeUntilRef = exp - now;
      timeUntilRef -= 5 * 60;
      timeUntilRef *= 1000;
      const res = () => {
        // eslint-disable-next-line
        Axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
        Axios.post(`${this.state.ServerURL}/user/refresh`, null, {
          headers: {
            refreshToken: `Bearer ${refreshToken}`
          }
        })
          .then(result => {
            commit("TOKEN", { accessToken: result.data.accessToken, refreshToken });
            dispatch("autoRefresh");
          })
          .catch(() => {
            // 토큰 갱신 실패 (9999) 처리
            this.state.username = null;
            this.state.userThumbnail = null;
            this.accessToken = null;
            this.refreshToken = null;
          });
      };
      setTimeout(res, timeUntilRef);
    },
    async LOGOUT({ commit }) {
      await Axios.post(`${this.state.ServerURL}/user/out`);
      commit("LOGOUT");
    },
    async SIGNUP({ dispatch }, signupData) {
      const SingupRes = await Axios.post(`${this.state.ServerURL}/newuser/add`, signupData);
      if (SingupRes.status === 200) {
        const loginData = { username: signupData.username, password: signupData.password };
        await dispatch("LOGIN", loginData);
      }
    },
    async Thumbnail({ commit }, payload) {
      const res = await Axios.get(`${this.state.ServerURL}/newuser/get_profile_img`, {
        params: {
          username: payload
        }
      });
      commit("Thumbnail", res.data);
    }
  },
  modules: {}
});
