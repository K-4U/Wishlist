import {defineStore} from 'pinia';
import {authenticationApi, BeckersUser, JwtResponse} from "@/api";
import type {AxiosResponse} from 'axios';
import router from "@/router";
import {jwtDecode, JwtPayload} from "jwt-decode";
import {LoginError} from "@/exceptions/LoginError";


export const useAuthStore = defineStore({
  id: 'auth',
  state: (): { user: BeckersUser | null, returnUrl: string | null, token: string | null } => ({
    // initialize state from local storage to enable user to stay logged in
    user: JSON.parse(localStorage.getItem('user') ?? "{}"),
    token: localStorage.getItem('token'),
    returnUrl: null,
  }),
  getters: {
    decodedToken(): JwtPayload | null {
      return this.token !== null ? jwtDecode(this.token) : null;
    },
    isLoggedIn(): boolean {
      //TODO: If we WERE logged in, but the token has expired, show a message to the user
      console.log(this.user !== null, this.token != null, this.decodedToken !== null, (this.decodedToken?.exp ?? 0) > Date.now() / 1000);
      return (this.user !== null) &&
        (this.token != null) &&
        (this.decodedToken !== null) &&
        ((this.decodedToken.exp ?? 0) > Date.now() / 1000);
    },
    currentUserId(): number | undefined {
      return this.user?.id;
      // return 3;
    }
  },
  actions: {
    async login(username: string, password: string) {
      const response: JwtResponse = await authenticationApi.doLogin({
        username,
        password
      }).then((value: AxiosResponse<JwtResponse, any>) => {
        if (value.data === undefined && value.status === 401) {
          throw new LoginError("Invalid username or password", 401);
        }
        return value.data as JwtResponse;
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
      this.token = null;
      localStorage.removeItem('user');
      localStorage.removeItem('token');
      router.push('/login').then(() => {
        window.location.reload();
      });
    }
  }
});
