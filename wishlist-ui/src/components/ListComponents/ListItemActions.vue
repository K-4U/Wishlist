<script lang="ts" setup>

import {defineProps, ref} from "vue";
import {WishlistItemProp, WishlistProp} from "@/proptypes";
import {useRouter} from "vue-router";
import ConfirmDialog from "@/components/ConfirmDialog.vue";
import {useAuthStore, useListsStore} from "@/stores";
import {useMessagesStore} from "@/stores/messages.store";
import {WishlistDTO} from "@/api";

const router = useRouter();
const listsStore = useListsStore();

const {item, list, own} = defineProps({
  item: WishlistItemProp,
  list: WishlistProp,
  own: {
    type: Boolean,
    default: false
  }
})

const confirmDialogRef = ref<InstanceType<typeof ConfirmDialog> | null>(null);
const auth = useAuthStore();
const moveDialogVisible = ref(false);
// const selectedMoveList = ref();
const selectedMoveList = defineModel();

function openEditPage() {
  router.push(`/list/${list?.id}/item/${item?.id}`);
}

function removeItem() {
  confirmDialogRef.value?.open('Weet je zeker dat je dit item wilt verwijderen?',
    'Dit zorgt ervoor dat het item niet meer zichtbaar is om te kopen, maar zal geen bericht sturen naar iemand die dit misschien al heeft gekocht.',
    [{
      title: 'Oke!', color: 'success', handler: () => {
        //@ts-ignore the fact that list can be null
        listsStore.deleteItem(list?.id, item?.id).then(() => {
          useMessagesStore().showMessage('Het item is verwijderd van de lijst.', 'success');
          router.go(0);
        });
      }
    }, {title: 'Misschien niet', color: 'error'}]);
}

function buy() {
  confirmDialogRef.value?.open('Weet je zeker dat je dit item wilt markeren als gekocht?',
    'Weet je zeker dat je dit gaat kopen? Je kan deze actie ongedaan maken, maar dat is natuurlijk niet netjes!',
    [{
      title: 'Ja', color: 'success', handler: () => {
        //@ts-ignore the fact that list can be null
        listsStore.buyItem(list?.id, item?.id).then(() => {
          useMessagesStore().showMessage('Je hebt dit item gekocht!', 'success');
          router.go(0);
        });
      }
    }, {title: 'Nee', color: 'error'}]);
}

function unbuy() {
  confirmDialogRef.value?.open('Weet je zeker dat je dit item niet meer wilt markeren als gekocht?',
    'Het item zal weer beschikbaar zijn voor anderen om te kopen.',
    [{
      title: 'Ja', color: 'success', handler: () => {
        //@ts-ignore the fact that list can be null
        listsStore.unbuyItem(list?.id, item?.id).then(() => {
          useMessagesStore().showMessage('Je hebt dit item teruggelegd!', 'warning');
          router.go(0);
        });
      }
    }, {title: 'Nee', color: 'error'}]);
}

const ownLists = ref<WishlistDTO[]>([]);

function moveItem() {
  listsStore.getOwnLists().then((lists) => {
    //Filter out the current list
    //@ts-ignore the fact that list can be null
    ownLists.value = lists.filter((l: WishlistDTO) => l.id != list?.id);
    moveDialogVisible.value = true;
  });
}

function confirmMove() {
  const selectedList = selectedMoveList.value;
  const listName = ownLists.value.filter((l) => l.id == selectedList)[0].listName;
  console.log('Moving item', item?.id, 'to list', selectedList);
  //@ts-ignore the fact that list can be null
  listsStore.moveItem(list?.id, item?.id, selectedList).then(() => {
    useMessagesStore().showMessage(`Het item is verplaatst naar ${listName}.`, 'success');
    router.go(0);
  });
}

</script>

<template>
  <v-btn-group v-if="own">
    <v-btn color="primary" icon size="small" variant="tonal" @click="openEditPage">
      <v-icon>mdi-pencil</v-icon>
    </v-btn>
    <v-btn color="error" icon size="small" variant="tonal" @click="removeItem">
      <v-icon>mdi-delete</v-icon>
    </v-btn>
    <v-btn color="warning" icon size="small" variant="tonal" @click="moveItem">
      <v-icon>mdi-folder-move</v-icon>
    </v-btn>
  </v-btn-group>
  <v-btn-group v-else-if="!own && item?.purchasedBy?.id != auth.currentUserId">
    <v-btn color="primary" icon size="small" variant="tonal" @click="buy">
      <v-icon>mdi-cart-plus</v-icon>
    </v-btn>
  </v-btn-group>
  <v-btn-group v-else-if="item?.purchasedBy?.id == auth.currentUserId">
    <v-btn color="warning" icon size="small" variant="tonal" @click="unbuy">
      <v-icon>mdi-cart-minus</v-icon>
    </v-btn>
  </v-btn-group>

  <ConfirmDialog ref="confirmDialogRef"/>

  <v-dialog v-model="moveDialogVisible" max-width="800">
    <v-card :title="`Verplaats ${item?.description} van lijst`" prepend-icon="mdi-folder-move">
      <v-card-text>
        <v-select v-model="selectedMoveList" :items="ownLists" item-title="listName" item-value="id"
                  label="Naar lijst"/>
      </v-card-text>
      <v-card-actions>
        <v-btn color="success" @click="confirmMove">Verplaats</v-btn>
        <v-btn color="error" @click="moveDialogVisible = false">Annuleren</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>
