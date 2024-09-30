import {defineStore} from 'pinia';
import {AuthenticationApi, BeckersUser, JwtResponse} from "@/api";
import type {AxiosPromise} from 'axios';
import router from "@/router";
import {jwtDecode} from "jwt-decode";


export const useAuthStore = defineStore({
  id: 'auth',
  state: (): { api: AuthenticationApi, user: BeckersUser, returnUrl: string, token: string } => ({
    // initialize state from local storage to enable user to stay logged in
    user: JSON.parse(localStorage.getItem('user')),
    token: localStorage.getItem('token'),
    returnUrl: null,
    api: new AuthenticationApi()
  }),
  getters: {
    decodedToken() {
      return jwtDecode(this.token);
    },
    isLoggedIn() {
      return this.user !== null && this.token != null && this.decodedToken.exp > Date.now() / 1000;
    },
  },
  actions: {
    async login(username, password) {
      const response: JwtResponse = await this.api.doLogin({
        username,
        password
      }).then((response: AxiosPromise<JwtResponse>) => {
        return response.data;
      });

      console.log(response)
      // update pinia state
      this.user = response.delegate;
      this.token = response.token;

      // store user details and jwt in local storage to keep user logged in between page refreshes
      localStorage.setItem('user', JSON.stringify(response.delegate));
      localStorage.setItem('token', response.token);

      // redirect to previous url or default to home page
      router.push(this.returnUrl || '/');
    },
    logout() {
      this.user = null;
      localStorage.removeItem('user');
      router.push('/login');
    }
  }
});
