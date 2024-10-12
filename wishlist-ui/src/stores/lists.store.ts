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
import {ref} from 'vue';

async function handleApiCall<T>(apiCall: Promise<AxiosResponse<T>>, errorHandler: (error: RequiredError) => void = () => {
}): Promise<T | null> {
  try {
    const response = await apiCall;
    return response.data;
  } catch (error) {
    errorHandler(error as RequiredError);
    return null;
  }
}

export const useListsStore = defineStore('lists', () => {
  const lists = ref<WishlistDTO[]>([]);
  const listById = ref<WishlistDTO | null>(null);
  const itemById = ref<WishlistItemDTO | null>(null);

  async function getAllLists(): Promise<Array<WishlistDTO>> {
    return await handleApiCall(listsApi.getAllLists()) ?? [];
  }

  async function getOwnLists(): Promise<Array<WishlistDTO>> {
    return await handleApiCall(listsApi.ownLists()) ?? [];
  }

  async function getAllListsGroupedByUser(): Promise<{ [user: number]: WishlistDTO[] }> {
    const lists = await handleApiCall(listsApi.getAllLists()) ?? [];
    const listsByUser: { [user: number]: WishlistDTO[] } = {};
    lists.forEach((wishlist) => {
      if (!listsByUser[wishlist.owner?.id]) {
        listsByUser[wishlist.owner?.id] = [];
      }
      listsByUser[wishlist.owner?.id].push(wishlist);
    });
    return listsByUser;
  }

  async function getListById(id: number): Promise<WishlistDTO> {
    return await handleApiCall(listsApi.getListById(id)) ?? {} as WishlistDTO;
  }

  async function getItemFromList(listId: number, itemId: number): Promise<WishlistItemDTO> {
    return await handleApiCall(listsApi.getItem(listId, itemId)) ?? {} as WishlistItemDTO;
  }

  async function updateItem(listId: number, itemId: number, item: WishlistItemUpdate) {
    return await handleApiCall(listsApi.saveItem(listId, itemId, item)) ?? {} as WishlistItemDTO;
  }

  async function deleteItem(listId: number, itemId: number) {
    return await handleApiCall(listsApi.deleteItem(listId, itemId)) ?? {} as WishlistItemDTO;
  }

  async function createItem(listId: number, item: WishlistItemCreate) {
    return await handleApiCall(listsApi.addItem(listId, item)) ?? {} as WishlistItemDTO;
  }

  async function buyItem(listId: number, itemId: number) {
    return await handleApiCall(listsApi.buyItem(listId, itemId)) ?? {} as WishlistItemDTO;
  }

  async function unbuyItem(listId: number, itemId: number) {
    return await handleApiCall(listsApi.unbuyItem(listId, itemId)) ?? {} as WishlistItemDTO;
  }

  async function moveItem(listId: number, itemId: number, targetListId: number) {
    return await handleApiCall(listsApi.moveItem(listId, itemId, {value: targetListId})) ?? {} as WishlistItemDTO;
  }

  async function updateList(listId: number, list: WishlistUpdate) {
    return await handleApiCall(listsApi.updateList(listId, list)) ?? {} as WishlistDTO;
  }

  async function createList(list: WishlistCreate) {
    return await handleApiCall(listsApi.createList(list)) ?? {} as WishlistDTO;
  }

  async function deleteList(listId: number) {
    return await handleApiCall(listsApi.deleteList(listId))
  }

  return {
    lists,
    listById,
    itemById,
    getAllLists,
    getOwnLists,
    getAllListsGroupedByUser,
    getListById,
    getItemFromList,
    updateItem,
    deleteItem,
    createItem,
    buyItem,
    unbuyItem,
    moveItem,
    updateList,
    createList,
    deleteList
  };
});
