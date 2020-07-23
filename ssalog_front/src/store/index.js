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
      const res = await Axios.post(`${ServerURL}/newuser/login`, loginData);
      commit("LOGIN", res.data);
    },
    async LOGOUT({ commit }) {
      commit("LOGOUT");
    },
    async SIGNUP({ commit }, signupData) {
      const SingupRes = await Axios.post(`${ServerURL}/newuser/add`, signupData);
      if (SingupRes.status === 200) {
        const loginData = { username: signupData.username, password: signupData.password };
        const loginRes = await Axios.post(`${ServerURL}/newuser/login`, loginData);
        commit("LOGIN", loginRes.data);
      }
    }
  },
  modules: {}
});
