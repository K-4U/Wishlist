<script lang="ts" setup>

import {onMounted, ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {WishlistDTO} from "@/api";
import {useAuthStore, useListsStore} from "@/stores";
import * as Yup from "yup";
import {useForm} from "vee-validate";
import CurrencyInput from "@/components/form/CurrencyInput.vue";
import Messages from "@/components/Messages.vue";
import {useMessagesStore} from "@/stores/messages.store";
import {useDialogStore} from "@/stores/dialog.store";

const route = useRoute()
const router = useRouter();
const list = ref<WishlistDTO | null>(null);
const listsStore = useListsStore();
const authStore = useAuthStore();
const own = ref<Boolean>(false);

onMounted(() => {
  //@ts-ignore
  listsStore.getListById(route.params.listId).then((listFromApi) => {
    list.value = listFromApi;
  });
});

const dialogStore = useDialogStore();

function returnToList() {
  if (isFieldTouched('description') || isFieldTouched('price') || isFieldTouched('url') || isFieldTouched('remarks')) {
    dialogStore.showConfirm('Weet je zeker dat je terug wilt gaan?', 'Je hebt wijzigingen gemaakt die nog niet zijn opgeslagen.',
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

const onSubmitHandler = handleSubmit((values, actions) => {
  //@ts-ignore the fact that list can be null.
  listsStore.createItem(list.value.id, {
    description: description.value,
    price: price.value,
    url: url.value,
    remarks: remarks.value,
  }).then(() => {
    useMessagesStore().showMessage('Item toegevoegd', 'success');
    //@ts-ignore the fact that params doesn't have listId
    router.push(`/list/${route.params.listId}`);
  });
});

</script>

<template>
  <Messages/>
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
</template>

<style scoped>

</style>
