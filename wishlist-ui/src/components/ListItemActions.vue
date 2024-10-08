<script lang="ts" setup>

import {defineProps, ref} from "vue";
import {WishlistItemProp, WishlistProp} from "@/proptypes";
import {useRouter} from "vue-router";
import ConfirmDialog from "@/components/ConfirmDialog.vue";
import {useListsStore} from "@/stores";
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

function openEditPage() {
  router.push(`/list/${list?.id}/item/${item?.id}`);
}

function removeItem() {
  confirmDialogRef.value?.open('Weet je zeker dat je dit item wilt verwijderen?', 'Dit zorgt ervoor dat het item niet meer zichtbaar is om te kopen, maar zal geen bericht sturen naar iemand die dit misschien al heeft gekocht.');
}

function dialogCallback(arg: string) {
  if (arg === 'oke!') {
    //@ts-ignore if list or item are null here, it should throw an error
    listsStore.deleteItem(list.id, item.id).then(() => {
      useMessagesStore().showMessage('Het item is verwijderd van de lijst.', 'success');
      window.location.reload();
    });
  }
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
  <v-btn-group v-else>
    <v-btn color="primary" icon size="small" variant="tonal">
      <v-icon>mdi-cart-plus</v-icon>
    </v-btn>
  </v-btn-group>

  <ConfirmDialog
    ref="confirmDialogRef"
    :buttons="[{title: 'Oke!', color: 'success'}, {title: 'Misschien niet', color: 'error'}]"
    @button-pressed="e => dialogCallback(e)"/>
</template>

<style scoped>

</style>
