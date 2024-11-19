<script lang="ts" setup>

import {useListsStore} from "@/stores/lists.store";
import {onMounted, ref} from "vue";
import {UserDTO, WishlistDTO} from "@/api";
import {useAuthStore} from "@/stores";
import UserLists from "@/components/ListComponents/UserLists.vue";
import Messages from "@/components/Messages.vue";

const authStore = useAuthStore();

console.log(authStore.currentUserId);
const listsStore = useListsStore();
const listsRef = ref<{ [user: number]: WishlistDTO[] }>([])
const currentUserListsRef = ref<WishlistDTO[]>([])
const usersRef = ref<{ [user: number]: UserDTO }>([])

onMounted(() => {
  listsStore.getAllListsGroupedByUser().then((lists) => {
    listsRef.value = {};
    usersRef.value = [];
    Object.keys(lists).map(Number).forEach((key: number) => {
      //Check if the key for userId is in the usersRef. If not, add the current list owner to it.
      if (!usersRef.value[key]) {
        usersRef.value[key] = lists[key][0].owner ?? {};
      }
      if (authStore.currentUserId == key) {
        currentUserListsRef.value = lists[key];
      } else {
        if (!listsRef.value[key]) {
          listsRef.value[key] = lists[key];
        }
      }
    })
  });
});

</script>

<template>
  <h1>Alle wenslijsten</h1>
  <Messages/>
  <v-row>
    <UserLists v-if="currentUserListsRef" :lists="currentUserListsRef" :user="usersRef[authStore.currentUserId ?? 0]"
               own/>
    <UserLists v-for="(lists, userId) in listsRef" :key="userId" :lists="lists" :user="usersRef[userId]"/>
  </v-row>
</template>

<style scoped>

</style>
