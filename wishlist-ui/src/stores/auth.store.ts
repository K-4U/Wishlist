import {defineStore} from 'pinia';
import {AuthenticationApi, BeckersUser, JwtResponse} from "@/api";
import type {AxiosPromise} from 'axios';
import router from "@/router";
import {jwtDecode} from "jwt-decode";
import {RequiredError} from "@/api/base";
import {LoginError} from "@/exceptions/LoginError";


export const useAuthStore = defineStore({
  id: 'auth',
  state: (): { api: AuthenticationApi, user: BeckersUser, returnUrl: string, token: string } => ({
    // initialize state from local storage to enable user to stay logged in
    user: JSON.parse(localStorage.getItem('user')),
    token: localStorage.getItem('token'),
    returnUrl: null,
    api: new AuthenticationApi({basePath: (window.location.protocol === 'https' ? 'https' : 'http') + "://" + window.location.hostname + ':8081'})
  }),
  getters: {
    decodedToken() {
      return jwtDecode(this.token);
    },
    isLoggedIn() {
      //TODO: If we WERE logged in, but the token has expired, show a message to the user
      return this.user !== null && this.token != null && this.decodedToken.exp > Date.now() / 1000;
    },
  },
  actions: {
    async login(username, password) {
      const response: JwtResponse = await this.api.doLogin({
        username,
        password
      }).catch((response: RequiredError) => {
        throw new LoginError(response.response.data.message, response.response.data.status, response.response.data.error);
      })
        .then((response: AxiosPromise<JwtResponse>) => {
        return response.data;
      });

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
      localStorage.removeItem('token');
      router.push('/login').then(() => {
        window.location.reload();
      });
    }
  }
});
