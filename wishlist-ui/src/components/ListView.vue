<script lang="ts" setup>

import {useRoute} from 'vue-router'
import {useAuthStore, useListsStore} from "@/stores";
import {onMounted, ref} from "vue";
import {Wishlist} from "@/api";
import {formatCurrency} from "@/helpers";

const route = useRoute()
const list = ref<Wishlist>(null);
const listsStore = useListsStore();
const authStore = useAuthStore();
const own = ref<Boolean>(false);

onMounted(() => {
  listsStore.getListById(route.params.id).then((listFromApi) => {
    console.log(listFromApi);
    list.value = listFromApi
    own.value = listFromApi.owner?.id == authStore.currentUserId;
  });
});

</script>

<template>
  <h1>
    Lijst van {{ list?.owner.name }}: {{ list?.listName }}
  </h1>
  <v-table>
    <thead>
    <tr>
      <th>Beschrijving</th>
      <th>Prijs</th>
      <th>Winkel</th>
      <th>Acties</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="item in list.items" v-if="list" :key="item.id">
      <td><span class="mr-3">{{ item.description }}</span>
        <v-bottom-sheet v-if="item.remarks">
          <template v-slot:activator="{props}">
            <v-chip v-if="item.remarks" color="primary" v-bind="props">
              <v-icon icon="mdi-message-alert-outline"/>
            </v-chip>
          </template>
          <v-card>
            <v-card-title>Opmerkingen</v-card-title>
            <v-card-text>{{ item.remarks }}</v-card-text>
          </v-card>
        </v-bottom-sheet>
      </td>
      <td>{{ formatCurrency(item.price) }}</td>
      <td><a v-if="item.hasValidUrl" :href="item.url" target="_blank">{{ item.store }}</a>
        <span v-else>{{ item.store }}</span></td>
      <td>
        <v-btn-group v-if="own">
          <v-btn color="primary" icon variant="tonal">
            <v-icon>mdi-pencil</v-icon>
          </v-btn>
          <v-btn color="error" icon variant="tonal">
            <v-icon>mdi-delete</v-icon>
          </v-btn>
        </v-btn-group>
        <v-btn-group v-else>
          <v-btn color="primary" icon variant="tonal">
            <v-icon>mdi-cart-plus</v-icon>
          </v-btn>
        </v-btn-group>
      </td>
    </tr>
    </tbody>
  </v-table>
</template>

<style scoped>

</style>
