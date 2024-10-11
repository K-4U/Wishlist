import {defineStore} from 'pinia';
import {
  listsApi,
  RequiredError,
  WishlistCreate,
  WishlistDTO,
  WishlistItemCreate,
  WishlistItemDTO,
  WishlistItemUpdate,
  WishlistUpdate
} from "@/api";
import type {AxiosResponse} from 'axios';


export const useListsStore = defineStore({
  id: 'lists',
  state: (): {} => ({}),
  getters: {},
  actions: {
    async getAllLists(): Promise<Array<WishlistDTO>> {
      return await listsApi.getAllLists()
        .catch((response: RequiredError) => {
        })
        .then((response: void | AxiosResponse<Array<WishlistDTO>, any>) => {
          if (!response) {
            return []
          }
          return response.data
        });
    },
    async getOwnLists(): Promise<Array<WishlistDTO>> {
      return await listsApi.ownLists()
        .catch((response: RequiredError) => {
        })
        .then((response: void | AxiosResponse<Array<WishlistDTO>, any>) => {
          if (!response) {
            return []
          }
          return response.data
        });
    },
    async getAllListsGroupedByUser(): Promise<{ [user: number]: WishlistDTO[] }> {
      console.log("Getting all lists grouped by user")
      console.log(listsApi)
      return await listsApi.getAllLists()
        .catch((response: RequiredError) => {
          console.error(response);
        })
        .then((response: void | AxiosResponse<Array<WishlistDTO>, any>) => {
          if (!response) {
            return {}
          }
          let listsByUser: { [user: number]: WishlistDTO[] } = {}
          response.data.map((wishlist: WishlistDTO) => {
            if (!listsByUser[wishlist.owner?.id]) {
              listsByUser[wishlist.owner?.id] = []
            }
            listsByUser[wishlist.owner?.id].push(wishlist);
          })

          return listsByUser;
        })
    },
    async getListById(id: number): Promise<WishlistDTO> {
      return await listsApi.getListById(id)
        .catch((response: RequiredError) => {
        })
        .then((response: void | AxiosResponse<WishlistDTO, any>) => {
          if (!response) {
            return {} as WishlistDTO
          }
          return response.data
        });
    },
    async getItemFromList(listId: number, itemId: number): Promise<WishlistItemDTO> {
      return await listsApi.getItem(listId, itemId)
        .catch((response: RequiredError) => {
          console.error(response);
        })
        .then((response: AxiosResponse<WishlistItemDTO, any> | void) => {
          console.log(response);
          if (!response) {
            return {} as WishlistItemDTO
          }
          return response.data
        });
    },
    async updateItem(listId: number, itemId: number, item: WishlistItemUpdate) {
      return await listsApi.saveItem(listId, itemId, item).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<WishlistItemDTO, any> | void) => {
        if (!response) {
          return {} as WishlistItemDTO
        }
        return response.data;
      });
    },
    async deleteItem(listId: number, itemId: number) {
      return await listsApi.deleteItem(listId, itemId).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<void, any> | void) => {
        if (!response) {
          return {} as WishlistItemDTO
        }
        return response.data;
      });
    },
    async createItem(listId: number, item: WishlistItemCreate) {
      return await listsApi.addItem(listId, item).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<WishlistItemDTO, any> | void) => {
        if (!response) {
          return {} as WishlistItemDTO
        }
        return response.data;
      });
    },
    async buyItem(listId: number, itemId: number) {
      return await listsApi.buyItem(listId, itemId).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<void, any> | void) => {
        if (!response) {
          return {} as WishlistItemDTO
        }
        return response.data;
      });
    },
    async unbuyItem(listId: number, itemId: number) {
      return await listsApi.unbuyItem(listId, itemId).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<void, any> | void) => {
        if (!response) {
          return {} as WishlistItemDTO
        }
        return response.data;
      });
    },
    async moveItem(listId: number, itemId: number, targetListId: number) {
      return await listsApi.moveItem(listId, itemId, {value: targetListId}).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<void, any> | void) => {
        if (!response) {
          return {} as WishlistItemDTO
        }
        return response.data;
      });
    },
    async updateList(listId: number, list: WishlistUpdate) {
      return await listsApi.updateList(listId, list).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<WishlistDTO, any> | void) => {
        if (!response) {
          return {} as WishlistDTO
        }
        return response.data;
      });
    },
    async createList(list: WishlistCreate) {
      return await listsApi.createList(list).catch((response: RequiredError) => {
        console.error(response)
      }).then((response: AxiosResponse<WishlistDTO, any> | void) => {
        if (!response) {
          return {} as WishlistDTO
        }
        return response.data;
      });
    }
  }
});
