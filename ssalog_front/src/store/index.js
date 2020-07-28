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
      state.userThumbnail = payload.Thumbnail;
    },
    LOGOUT(state) {
      state.accessToken = null;
    }
  },
  actions: {
    async LOGIN({ commit }, loginData) {
      // 이해안될까봐 남겨놓는다.
      // const options = {
      //   params: {
      //     이게 아래 선언한거랑 같은 기능을 한다.
      //     ...loginData

      //     // username: loginData.username,
      //     // password: loginData.password
      //   }
      // };
      const res1 = await Axios.post(`${this.state.ServerURL}/newuser/login`, null, {
        params: {
          ...loginData
        }
      });
      const res2 = await Axios.get(`${this.state.ServerURL}/newuser/get_profile_img`, {
        params: {
          username: loginData.username
        }
      });
      commit("LOGIN", {
        accessToken: res1.data.accessToken,
        Thumbnail: res2.data,
        username: loginData.username
      });
    },
    async LOGOUT({ commit }) {
      commit("LOGOUT");
    },
    async SIGNUP({ dispatch }, signupData) {
      const SingupRes = await Axios.post(`${this.state.ServerURL}/newuser/add`, signupData);
      console.log(SingupRes.status);
      if (SingupRes.status === 200) {
        const loginData = { username: signupData.username, password: signupData.password };
        console.log(loginData);
        await dispatch("LOGIN", loginData);
      }
    }
  },
  modules: {}
});
