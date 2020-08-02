import Vue from "vue";
import Vuex from "vuex";
import Axios from "axios";
// eslint-disable-next-line
import jwt_decode from "jwt-decode";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    accessToken: null,
    refreshToken: null,
    ImgURL: "https://ssalog.gq/upload/",
    ServerURL: "https://ssalog.gq/api",
    // user data
    username: null,
    userThumbnail: null
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
      state.userThumbnail = `${state.ImgURL}/default.png`;
    },
    TOKEN(state, payload) {
      const { accessToken, refreshToken } = payload;
      state.accessToken = accessToken;
      state.refreshToken = refreshToken;
      console.log(`Token${state.refreshToken}`);
      console.log(`Token${state.accessToken}`);
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
      console.log(accessToken);
      console.log(refreshToken);
      const { exp } = jwt_decode(accessToken, { header: true });
      const now = Date.now() / 1000;
      // eslint-disable-next-line
      let timeUntilRef = exp - now;
      timeUntilRef -= 5 * 60;
      const res = () => {
        // eslint-disable-next-line
        Axios.defaults.headers.common.Authorization = `Bearer ${accessToken}`;
        Axios.post(`${this.state.ServerURL}/user/refresh`, null, {
          headers: {
            refreshToken: `Bearer ${refreshToken}`
          }
        })
          .then(result => {
            console.log(result);
            commit("TOKEN", { accessToken: result.data.accessToken, refreshToken });
            dispatch("autoRefresh");
          })
          .catch(err => console.error(err));
      };
      setTimeout(res, 1000);
    },
    async LOGOUT({ commit }) {
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
      const res = await Axios.get(`${this.state.ServerURL}/user/get_profile_img`, {
        params: {
          username: payload
        }
      });
      commit("Thumbnail", res.data);
    }
  },
  modules: {}
});
