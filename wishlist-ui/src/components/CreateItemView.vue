<script lang="ts" setup>

import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {Wishlist, WishlistItemCreate} from "@/api";
import {useAuthStore, useListsStore} from "@/stores";
import * as Yup from "yup";
import {useForm} from "vee-validate";
import CurrencyInput from "@/components/form/CurrencyInput.vue";
import ConfirmDialog, {openDialog} from "@/components/ConfirmDialog.vue";

const route = useRoute()
const router = useRouter();
const list = ref<Wishlist | null>(null);
const createItem = ref<WishlistItemCreate>({});
const listsStore = useListsStore();
const authStore = useAuthStore();
const own = ref<Boolean>(false);

onMounted(() => {
  //@ts-ignore
  listsStore.getListById(route.params.listId).then((listFromApi) => {
    list.value = listFromApi;
  });
});

function returnToList() {
  if (isFieldTouched('description') || isFieldTouched('price') || isFieldTouched('url') || isFieldTouched('remarks')) {
    openDialog('Weet je zeker dat je terug wilt gaan?', 'Je hebt wijzigingen gemaakt die nog niet zijn opgeslagen.')
    return;
  }
  handleConfirm();
}

function doTheThing(arg) {
  if (arg === 'oke!') {
    handleConfirm();
  }
}

function handleConfirm() {
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
  if (createItem.value) {
    listsStore.createItem(list.value.id, createItem.value.id, {
      description: description.value,
      price: price.value,
      url: url.value,
      remarks: remarks.value,
    }).then(() => {
      router.push(`/list/${route.params.listId}`);
    });
  }
});

</script>

<template>
  <v-card :title="`Nieuw item in lijst ${list?.listName}`">
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

  <ConfirmDialog :buttons="[{title: 'Oke!', color: 'success'}, {title: 'Woepsie', color: 'error'}]"
                 @button-pressed="doTheThing"/>
</template>

<style scoped>

</style>
