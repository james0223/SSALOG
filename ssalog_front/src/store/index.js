import Vue from "vue";
import Vuex from "vuex";
import Axios from "axios";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    accessToken: null,
    ImgURL: "https://ssalog.gq/upload/",
    ServerURL: "http://i3b101.p.ssafy.io:8080",
    // user data
    username: null,
    userThumbnail: null
  },
  plugins: [createPersistedState()],
  getters: {},
  mutations: {
    LOGIN(state, payload) {
      state.accessToken = payload.accessToken;
      state.username = payload.username;
      Axios.defaults.headers.common.Authorization = `Bearer ${state.accessToken}`;
    },
    LOGOUT(state) {
      state.accessToken = null;
    },
    Thumbnail(state, payload) {
      // jso 하드타이핑 나중에 수정 필요.
      console.log(payload);
      state.userThumbnail = `${state.ImgURL}${payload}`;
    }
  },
  actions: {
    async LOGIN({ commit, dispatch }, loginData) {
      const res = await Axios.post(`${this.state.ServerURL}/newuser/login`, null, {
        params: {
          ...loginData
        }
      });
      commit("LOGIN", {
        accessToken: res.data.accessToken,
        username: loginData.username
      });
      await dispatch("Thumbnail", loginData.username);
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
