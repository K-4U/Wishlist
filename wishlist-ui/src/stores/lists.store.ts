import {defineStore} from 'pinia';
import {Configuration, ListsApi, Wishlist} from "@/api";
import type {AxiosPromise} from 'axios';
import {RequiredError} from "@/api/base";
import {useAuthStore} from "@/stores/auth.store";


export const useListsStore = defineStore({
  id: 'lists',
  state: (): { api: ListsApi, authStore } => ({
    api: new ListsApi(new Configuration({
      basePath: (window.location.protocol === 'https' ? 'https' : 'http') + "://" + window.location.hostname + ':8081',
      accessToken: () => localStorage.getItem('token')
    })),
    authStore: useAuthStore()
  }),
  getters: {},
  actions: {
    async getAllLists(): Promise<Array<Wishlist>> {
      return await this.api.getAllLists()
        .catch((response: RequiredError) => {
        })
        .then((response: AxiosPromise<Array<Wishlist>>) => {
          return response.data
        });
    },
    async getAllListsGroupedByUser(): Promise<{ [user: number]: Wishlist[] }> {
      return await this.api.getAllLists()
        .catch((response: RequiredError) => {
        })
        .then((response: AxiosPromise<Array<Wishlist>>) => {
          let listsByUser = {}
          response.data.map((wishlist: Wishlist) => {
            if (!listsByUser[wishlist.owner?.id]) {
              listsByUser[wishlist.owner?.id] = []
            }
            listsByUser[wishlist.owner?.id].push(wishlist);
          })

          return listsByUser;
        })
    },
    async getListById(id: number): Promise<Wishlist> {
      return await this.api.getListById(id)
        .catch((response: RequiredError) => {
        })
        .then((response: AxiosPromise<Wishlist>) => {
          return response.data
        });
    }
  }
});
