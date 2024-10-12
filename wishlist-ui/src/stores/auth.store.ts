import {defineStore} from 'pinia';
import {authenticationApi, JwtResponse, UserDTO} from "@/api";
import type {AxiosResponse} from 'axios';
import router from "@/router";
import {jwtDecode, JwtPayload} from "jwt-decode";
import {LoginError} from "@/exceptions/LoginError";
import {computed, ref} from 'vue';

export const useAuthStore = defineStore('auth', () => {
  const user = ref<UserDTO | null>(JSON.parse(localStorage.getItem('user') ?? "{}"));
  const token = ref<string | null>(localStorage.getItem('token'));
  const returnUrl = ref<string | null>(null);

  const decodedToken = computed<JwtPayload | null>(() => {
    return token.value !== null ? jwtDecode(token.value) : null;
  });

  const isLoggedIn = computed<boolean>(() => {
    return (user.value !== null) &&
      (token.value != null) &&
      (decodedToken.value !== null) &&
      ((decodedToken.value.exp ?? 0) > Date.now() / 1000);
  });

  const currentUserId = computed<number | undefined>(() => {
    return user.value?.id;
  });

  async function login(username: string, password: string) {
    const response: JwtResponse = await authenticationApi.doLogin({
      username,
      password
    }).then((value: AxiosResponse<JwtResponse, any>) => {
      if (value.data === undefined && value.status === 401) {
        throw new LoginError("Invalid username or password", 401);
      }
      return value.data as JwtResponse;
    });

    user.value = response.delegate;
    token.value = response.token;

    localStorage.setItem('user', JSON.stringify(response.delegate));
    localStorage.setItem('token', response.token);

    router.push(returnUrl.value || '/');
  }

  function logout() {
    user.value = null;
    token.value = null;
    localStorage.removeItem('user');
    localStorage.removeItem('token');
    router.push('/login').then(() => {
      window.location.reload();
    });
  }

  return {
    user,
    token,
    returnUrl,
    decodedToken,
    isLoggedIn,
    currentUserId,
    login,
    logout
  };
});
