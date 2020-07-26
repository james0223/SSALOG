import Vue from "vue";
import Vuex from "vuex";
import Axios from "axios";
import createPersistedState from "vuex-persistedstate";

Vue.use(Vuex);
const ServerURL = "http://i3b101.p.ssafy.io:8080";

export default new Vuex.Store({
  state: {
    accessToken: null
  },
  plugins: [createPersistedState()],
  getters: {},
  mutations: {
    LOGIN(state, { accessToken }) {
      state.accessToken = accessToken;
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
      const res = await Axios.post(`${ServerURL}/newuser/login`, null, {
        params: {
          ...loginData
        }
      });
      commit("LOGIN", res.data);
    },
    async LOGOUT({ commit }) {
      commit("LOGOUT");
    },
    async SIGNUP({ dispatch }, signupData) {
      const SingupRes = await Axios.post(`${ServerURL}/newuser/add`, signupData);
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
