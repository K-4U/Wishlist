<script lang="ts" setup>

import {useRoute, useRouter} from 'vue-router'
import {useAuthStore, useListsStore} from "@/stores";
import {computed, onMounted, ref} from "vue";
import {WishlistDTO, WishlistItemDTO} from "@/api";
import {formatCurrency} from "@/helpers";
import ListItemCard from "@/components/ListComponents/ListItemCard.vue";
import ListItemActions from "@/components/ListComponents/ListItemActions.vue";
import Messages from "@/components/Messages.vue";
import {useSettingsStore} from "@/stores/settings.store";

const route = useRoute()
const router = useRouter();
const list = ref<WishlistDTO | null>(null);
const listsStore = useListsStore();
const authStore = useAuthStore();
const settingsStore = useSettingsStore();
const own = ref<Boolean>(false);
const tableView = ref<Boolean>((settingsStore.getSetting('tableView') ?? false) == true);

onMounted(() => {
  //@ts-ignore
  listsStore.getListById(route.params.id).then((listFromApi) => {
    console.log(listFromApi);
    list.value = listFromApi
    own.value = listFromApi.owner?.id == authStore.currentUserId;
  });
});

function toggleView() {
  tableView.value = !tableView.value;
  settingsStore.setSetting('tableView', tableView.value.valueOf());
}

const items = computed(() => {
  return list.value?.items?.filter((item: WishlistItemDTO) => !item.deleted && ((!own.value && item.purchasedBy == undefined) || own.value || item.purchasedBy?.id == authStore.currentUserId));
})

</script>

<template>

  <h1>
    Lijst van {{ list?.owner?.name }}: {{ list?.listName }}
  </h1>

  <Messages/>

  <v-row v-if="tableView == false">
    <ListItemCard v-for="item in items" v-if="list" :key="item.id"
                  :item="item" :list="list" :own="own == true"/>
  </v-row>

  <v-table v-if="tableView == true">
    <thead>
    <tr>
      <th>Beschrijving</th>
      <th>Prijs</th>
      <th>Winkel</th>
      <th>Acties</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="item in items" v-if="list" :key="item.id">
      <td><span class="mr-3">{{ item.description }}</span>
        <v-bottom-sheet v-if="item.remarks">
          <template v-slot:activator="{props}">
            <v-chip v-if="item.remarks" color="primary" v-bind="props">
              <v-icon icon="mdi-message-alert-outline"/>
            </v-chip>
          </template>
          <v-card>
            <v-card-title>Opmerkingen</v-card-title>
            <v-card-text style="white-space: pre-line;">{{ item.remarks }}</v-card-text>
          </v-card>
        </v-bottom-sheet>
      </td>
      <td>{{ formatCurrency(item.price) }}</td>
      <td>
        <a v-if="item.hasValidUrl" :href="item.url" target="_blank">{{ item.store }}</a>
        <span v-else>{{ item.store }}</span>
      </td>
      <td>
        <ListItemActions :item="item" :list="list" :own="own == true"/>
      </td>
    </tr>
    </tbody>
  </v-table>

  <v-speed-dial
    :close-on-content-click="false"
    location="top center"
    transition="slide-y-reverse-transition"
  >
    <template v-slot:activator="{ props: activatorProps }">
      <!-- TODO: Rotate on click -->
      <v-btn class="ma-16" color="primary"
             dark icon="mdi-cog"
             location="bottom end" position="fixed" size="large" v-bind="activatorProps">
      </v-btn>
    </template>

    <v-btn key="1"
           :icon="!tableView ? 'mdi-view-list-outline' : 'mdi-view-grid'" color="warning" size="large" @click="toggleView"></v-btn>

    <v-btn key="2"
           v-if="own" color="success" icon="mdi-plus" size="large"
           @click="router.push(`/list/${list?.id}/item/new`)"></v-btn>
  </v-speed-dial>


</template>

<style scoped>

</style>
