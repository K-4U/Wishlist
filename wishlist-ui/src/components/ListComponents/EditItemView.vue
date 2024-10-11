<script lang="ts" setup>

import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {WishlistDTO, WishlistItemDTO, WishlistItemUpdate} from "@/api";
import {useAuthStore, useListsStore} from "@/stores";
import * as Yup from "yup";
import {useForm} from "vee-validate";
import CurrencyInput from "@/components/form/CurrencyInput.vue";
import ConfirmDialog from "@/components/ConfirmDialog.vue";
import Messages from "@/components/Messages.vue";
import {useMessagesStore} from "@/stores/messages.store";

const route = useRoute()
const router = useRouter();
const list = ref<WishlistDTO | null>(null);
const editItem = ref<WishlistItemUpdate | null>(null);
const listsStore = useListsStore();
const authStore = useAuthStore();
const own = ref<Boolean>(false);
const confirmDialogRef = ref<InstanceType<typeof ConfirmDialog> | null>(null);

onMounted(() => {
  //@ts-ignore
  listsStore.getItemFromList(route.params.listId, route.params.itemId).then((itemFromApi: WishlistItemDTO) => {
    editItem.value = {
      itemId: itemFromApi.id,
      description: itemFromApi.description,
      price: itemFromApi.price,
      url: itemFromApi.url,
      remarks: itemFromApi.remarks,
      wishlistId: itemFromApi.wishlist.id,
    };
    setValues({
      description: editItem.value?.description,
      price: editItem.value?.price,
      url: editItem.value?.url,
      remarks: editItem.value?.remarks ?? '',
    });
  });
  //@ts-ignore the fact that params doesn't contain listId
  listsStore.getListById(route.params.listId).then((listFromApi) => {
    own.value = listFromApi.owner?.id == authStore.currentUserId;
    list.value = listFromApi;
  });
});

function returnToList() {
  if (isFieldTouched('description') || isFieldTouched('price') || isFieldTouched('url') || isFieldTouched('remarks')) {
    confirmDialogRef.value?.open('Weet je zeker dat je terug wilt gaan?', 'Je hebt wijzigingen gemaakt die nog niet zijn opgeslagen.',
      [{title: 'Oke!', color: 'success', handler: (e) => handleConfirm()}, {title: 'Woepsie', color: 'error'}]);
    return;
  }
  handleConfirm();
}

function handleConfirm() {
  //@ts-ignore
  router.push(`/list/${route.params.listId}`);
}

const schema = Yup.object().shape({
  description: Yup.string().required('Voer een beschrijving in'),
  price: Yup.string().required('Voer een prijs in'),
  url: Yup.string().required('Voer een website in, of een winkel waar het item te koop is'),
  remarks: Yup.string(),
});

const vuetifyConfig = (state: any) => ({
  props: {
    'error-messages': state.errors,
  },
});

const {defineField, handleSubmit, resetForm, setValues, isFieldTouched} = useForm({
  validationSchema: schema,
});

const [description, descriptionProps] = defineField('description', vuetifyConfig);
const [price, priceProps] = defineField('price', vuetifyConfig);
const [url, urlProps] = defineField('url', vuetifyConfig);
const [remarks, remarksProps] = defineField('remarks', vuetifyConfig);

const test: Number = 20;

const onSubmitHandler = handleSubmit((values, actions) => {
  if (editItem.value) {
    //@ts-ignore the fact that list can be null. If it is, it's in error.
    listsStore.updateItem(list.value.id, editItem.value.itemId, {
      description: description.value,
      price: price.value,
      url: url.value,
      remarks: remarks.value,
      wishlistId: editItem.value.wishlistId,
      itemId: editItem.value.itemId,
    }).then(() => {
      useMessagesStore().showMessage('Item bijgewerkt', 'success');
      //@ts-ignore the fact that params doesn't have listId
      router.push(`/list/${route.params.listId}`);
    });
  }
});

</script>

<template>
  <Messages/>
  <v-card :title="`Bewerk item in lijst ${list?.listName}`">
    <v-form @submit="onSubmitHandler">
      <v-text-field
        v-model="description"
        label="Beschrijving"
        prepend-inner-icon="mdi-email-outline"
        v-bind="descriptionProps"
      ></v-text-field>
      <CurrencyInput v-model="price" label="Prijs" prepend-inner-icon="mdi-currency-eur"
                     v-bind="priceProps"></CurrencyInput>
      <v-text-field
        v-model="url"
        label="Website of winkel"
        prepend-inner-icon="mdi-web"
        v-bind="urlProps"/>
      <v-textarea
        v-model="remarks"
        density="compact"
        label="Opmerkingen"
        prepend-inner-icon="mdi-comment"
        v-bind="remarksProps"/>
      <v-card-actions>
        <v-btn color="success" prepend-icon="mdi-check" type="submit" variant="tonal">Opslaan</v-btn>
        <v-btn color="error" prepend-icon="mdi-close" @click="returnToList">Annuleren</v-btn>
      </v-card-actions>
    </v-form>
  </v-card>

  <ConfirmDialog ref="confirmDialogRef"/>
</template>

<style scoped>

</style>
