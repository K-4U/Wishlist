<script lang="ts" setup>
import {Wishlist} from "@/api";
import {defineProps, onMounted} from "vue";
import {BeckersUserProp} from "@/proptypes";
import {getAvatarUrl} from "@/helpers";


defineProps({
  user: BeckersUserProp,
  lists: Array<Wishlist>,
})
onMounted(() => {
  console.log('UserLists mounted')
});

</script>

<template>
  <v-col cols="12">
    <v-card :prepend-avatar="getAvatarUrl(user)" :title="user.name" class="mx-auto">
      <v-card-text class="bg-surface-light">

        <ul>
          <li v-for="list in lists" :key="list.id">
            {{ list.listName }}
          </li>
        </ul>
      </v-card-text>
    </v-card>
  </v-col>
</template>

<style scoped>

</style>
