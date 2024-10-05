import {defineStore} from 'pinia';
import {listsApi, RequiredError, Wishlist} from "@/api";
import type {AxiosResponse} from 'axios';


export const useListsStore = defineStore({
  id: 'lists',
  state: (): {} => ({
  }),
  getters: {},
  actions: {
    async getAllLists(): Promise<Array<Wishlist>> {
      return await listsApi.getAllLists()
        .catch((response: RequiredError) => {
        })
        .then((response: void | AxiosResponse<Array<Wishlist>, any>) => {
          if (!response) {
            return []
          }
          return response.data
        });
    },
    async getAllListsGroupedByUser(): Promise<{ [user: number]: Wishlist[] }> {
      console.log("Getting all lists grouped by user")
      console.log(listsApi)
      return await listsApi.getAllLists()
        .catch((response: RequiredError) => {
          console.error(response);
        })
        .then((response: void | AxiosResponse<Array<Wishlist>, any>) => {
          if (!response) {
            return {}
          }
          let listsByUser: { [user: number]: Wishlist[] } = {}
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
      return await listsApi.getListById(id)
        .catch((response: RequiredError) => {
        })
        .then((response: void | AxiosResponse<Wishlist, any>) => {
          if (!response) {
            return {} as Wishlist
          }
          return response.data
        });
    }
  }
});
