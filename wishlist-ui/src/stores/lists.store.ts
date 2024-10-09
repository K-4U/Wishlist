import {defineStore} from 'pinia';
import {listsApi, RequiredError, Wishlist, WishlistItem, WishlistItemCreate, WishlistItemUpdate} from "@/api";
import type {AxiosResponse} from 'axios';


export const useListsStore = defineStore({
  id: 'lists',
  state: (): {} => ({}),
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
    async getOwnLists(): Promise<Array<Wishlist>> {
      return await listsApi.ownLists()
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
    },
    async getItemFromList(listId: number, itemId: number): Promise<WishlistItem> {
      return await listsApi.getItem(listId, itemId)
        .catch((response: RequiredError) => {
          console.error(response);
        })
        .then((response: AxiosResponse<WishlistItem, any> | void) => {
          console.log(response);
          if (!response) {
            return {} as WishlistItem
          }
          return response.data
        });
    },
    async updateItem(listId: number, itemId: number, item: WishlistItemUpdate) {
      return await listsApi.saveItem(listId, itemId, item).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<WishlistItem, any> | void) => {
        if (!response) {
          return {} as WishlistItem
        }
        return response.data;
      });
    },
    async deleteItem(listId: number, itemId: number) {
      return await listsApi.deleteItem(listId, itemId).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<void, any> | void) => {
        if (!response) {
          return {} as WishlistItem
        }
        return response.data;
      });
    },
    async createItem(listId: number, item: WishlistItemCreate) {
      return await listsApi.addItem(listId, item).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<WishlistItem, any> | void) => {
        if (!response) {
          return {} as WishlistItem
        }
        return response.data;
      });
    },
    async buyItem(listId: number, itemId: number) {
      return await listsApi.buyItem(listId, itemId).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<void, any> | void) => {
        if (!response) {
          return {} as WishlistItem
        }
        return response.data;
      });
    },
    async unbuyItem(listId: number, itemId: number) {
      return await listsApi.unbuyItem(listId, itemId).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<void, any> | void) => {
        if (!response) {
          return {} as WishlistItem
        }
        return response.data;
      });
    },
    async moveItem(listId: number, itemId: number, targetListId: number) {
      return await listsApi.moveItem(listId, itemId, {value: targetListId}).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<void, any> | void) => {
        if (!response) {
          return {} as WishlistItem
        }
        return response.data;
      });
    },
  }
});
