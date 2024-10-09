<script lang="ts" setup>

import {defineProps, ref} from "vue";
import {WishlistItemProp, WishlistProp} from "@/proptypes";
import {useRouter} from "vue-router";
import ConfirmDialog from "@/components/ConfirmDialog.vue";
import {useAuthStore, useListsStore} from "@/stores";
import {useMessagesStore} from "@/stores/messages.store";

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

function openEditPage() {
  router.push(`/list/${list?.id}/item/${item?.id}`);
}

function removeItem() {
  confirmDialogRef.value?.open('Weet je zeker dat je dit item wilt verwijderen?',
    'Dit zorgt ervoor dat het item niet meer zichtbaar is om te kopen, maar zal geen bericht sturen naar iemand die dit misschien al heeft gekocht.',
    [{
      title: 'Oke!', color: 'success', handler: () => {
        listsStore.deleteItem(list.id, item.id).then(() => {
          useMessagesStore().showMessage('Het item is verwijderd van de lijst.', 'success');
          window.location.reload();
        });
      }
    }, {title: 'Misschien niet', color: 'error'}]);
}

function buy() {
  confirmDialogRef.value?.open('Weet je zeker dat je dit item wilt markeren als gekocht?',
    'Weet je zeker dat je dit gaat kopen? Je kan deze actie ongedaan maken, maar dat is natuurlijk niet netjes!',
    [{
      title: 'Ja', color: 'success', handler: () => {
        listsStore.buyItem(list.id, item.id).then(() => {
          useMessagesStore().showMessage('Je hebt dit item gekocht!', 'success');
          window.location.reload();
        });
      }
    }, {title: 'Nee', color: 'error'}]);
}

function unbuy() {
  confirmDialogRef.value?.open('Weet je zeker dat je dit item niet meer wilt markeren als gekocht?',
    'Het item zal weer beschikbaar zijn voor anderen om te kopen.',
    [{
      title: 'Ja', color: 'success', handler: () => {
        listsStore.unbuyItem(list.id, item.id).then(() => {
          useMessagesStore().showMessage('Je hebt dit item teruggelegd!', 'warning');
          window.location.reload();
        });
      }
    }, {title: 'Nee', color: 'error'}]);
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
</template>

<style scoped>

</style>
