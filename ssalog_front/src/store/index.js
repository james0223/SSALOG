import Vue from "vue";
import Vuex from "vuex";
import Axios from "axios";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
export default new Vuex.Store({
  state: {
    accessToken: null,
    ServerImgURL: "https://ssalog.gq/upload/resources/",
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
    },
    LOGOUT(state) {
      state.accessToken = null;
    },
    Thumbnail(state, payload) {
      state.userThumbnail = state.ServerImgURL + payload;
      console.log(state.userThumbnail);
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
      await dispatch("Thumbnail", loginData.username);
      commit("LOGIN", {
        accessToken: res.data.accessToken,
        username: loginData.username
      });
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
