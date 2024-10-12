<script lang="ts" setup>
import {WishlistDTO} from "@/api";
import {defineProps, ref} from "vue";
import {BeckersUserProp} from "@/proptypes";
import {getAvatarUrl} from "@/helpers";
import {useRouter} from "vue-router";
import {VSpeedDial} from "vuetify/components";
import {useDialogStore, useListsStore, useMessagesStore} from "@/stores";

const router = useRouter();
const dialogStore = useDialogStore();
const listsStore = useListsStore();
const messageStore = useMessagesStore();

const props = defineProps({
  user: BeckersUserProp,
  lists: Array<WishlistDTO>,
  own: {
    type: Boolean,
    default: false
  }
})

function openList(target: WishlistDTO) {
  router.push({path: `/list/${target.id}`});
}

const isEditModeOpen = ref(false);

function changeListName(e: any, listId: number) {
  console.log('change list name', listId);
  router.push({path: `/list/${listId}/edit`});
}

function removeList(e: any, listId: number) {
  for (const listItr in props.lists) {
    //@ts-ignore
    const list = props.lists[listItr];
    if (list.id === listId) {
      if (list.items.length > 0) {
        dialogStore.showAlert('Let op, deze lijst niet leeg is!', 'Je kan alleen lege lijsten verwijderen.', () => {
        });
      } else {
        dialogStore.showConfirm(
          'Weet je zeker dat je deze lijst wilt verwijderen?', 'Dit kan niet ongedaan worden gemaakt.',
          [{
            title: 'Ja', color: 'success', handler: () => actuallyRemoveList(listId)
          },
            {title: 'Nee', color: 'error'}
          ]);
      }
      break;
    }
  }
}

function actuallyRemoveList(listId: number) {
  listsStore.deleteList(listId).then(() => {
    messageStore.showMessage('De lijst is verwijderd.', 'success');
    router.go(0);
  });
}

</script>

<template>
  <v-col cols="12" md="6">
    <v-card :prepend-avatar="getAvatarUrl(props.user)" :subtitle="props.own ? 'Je eigen lijsten' : ''"
            :title="props.user?.name" class="mx-auto">
      <v-card-text class="bg-surface-light pa-0 pl-2">
        <v-list>
          <v-list-item v-for="list in props.lists" :key="list.id" :prepend-icon="`mdi-${list.icon ?? 'view-list'}`"
                       @click.self="e => openList(list)">
            <v-list-item-title @click.self="e => openList(list)">{{ list.listName }}</v-list-item-title>
            <template v-slot:append>
              <v-scroll-x-transition>
                <v-list-item-action v-show="isEditModeOpen">
                  <v-btn-group>
                    <v-btn color="primary" icon size="small" variant="tonal"
                           @click.prevent="(e:any) => changeListName(e, list.id)">
                      <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                    <v-btn color="error" icon size="small" variant="tonal"
                           @click.prevent="(e:any) => removeList(e, list.id)">
                      <v-icon>mdi-delete</v-icon>
                    </v-btn>
                  </v-btn-group>
                </v-list-item-action>
              </v-scroll-x-transition>
            </template>
          </v-list-item>
        </v-list>
      </v-card-text>
      <v-card-actions v-if="own">
        <v-speed-dial v-model="isEditModeOpen"
                      location="right center"
                      transition="fade-transition"
                      persistent

        >
          <template v-slot:activator="{ props: activatorProps }">
            <v-btn color="warning" icon v-bind="activatorProps">
              <v-icon>mdi-playlist-edit</v-icon>
            </v-btn>

          </template>
          <v-btn key="1" color="success" icon="mdi-plus" @click="router.push('/list/new')"></v-btn>

        </v-speed-dial>

      </v-card-actions>
    </v-card>
  </v-col>
</template>

<style scoped>

</style>
