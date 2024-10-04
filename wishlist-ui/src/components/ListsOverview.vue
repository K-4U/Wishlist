<script lang="ts" setup>

import {useListsStore} from "@/stores/lists.store";
import {onMounted, ref} from "vue";
import {BeckersUser, Wishlist} from "@/api";
import {useAuthStore} from "@/stores";
import UserLists from "@/components/UserLists.vue";

const authStore = useAuthStore();

const listsStore = useListsStore();
const listsRef = ref<{ [user: number]: Wishlist[] }>([])
const currentUserListRef = ref<Wishlist[]>(null)
const usersRef = ref<{ [user: number]: BeckersUser }>([])

onMounted(() => {
  listsStore.getAllListsGroupedByUser().then((lists) => {
    listsRef.value = {};
    usersRef.value = [];
    console.log(lists);
    for (let key: number in lists) {
      //Check if the key for userId is in the usersRef. If not, add the current list owner to it.
      if (!usersRef.value[key]) {
        usersRef.value[key] = lists[key][0].owner;
      }
      if (authStore.currentUserId == key) {
        currentUserListRef.value = lists[key];
      } else {
        if (!listsRef.value[key]) {
          listsRef.value[key] = lists[key];
        }
      }
    }
  });
});

</script>

<template>
  <v-row>
    <h1>Lists Overview</h1>
    <UserLists v-if="currentUserListRef" :lists="currentUserListRef" :user="usersRef[authStore.currentUserId]" own/>
    <UserLists v-for="(lists, userId) in listsRef" :key="userId" :lists="lists" :user="usersRef[userId]"/>
  </v-row>
</template>

<style scoped>

</style>
