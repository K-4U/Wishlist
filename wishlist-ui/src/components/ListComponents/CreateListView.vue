<script lang="ts" setup>

import {ref} from "vue";
import {useRoute, useRouter} from "vue-router";
import {WishlistDTO} from "@/api";
import {useDialogStore, useListsStore, useMessagesStore} from "@/stores";
import * as Yup from "yup";
import {useForm} from "vee-validate";
import Messages from "@/components/Messages.vue";

const route = useRoute()
const router = useRouter();
const list = ref<WishlistDTO | null>(null);
const listsStore = useListsStore();
const dialogStore = useDialogStore();


function returnToMain() {
  if (isFieldTouched('name') || isFieldTouched('icon')) {
    dialogStore.showConfirm('Weet je zeker dat je terug wilt gaan?', 'Je hebt wijzigingen gemaakt die nog niet zijn opgeslagen.',
      [{title: 'Oke!', color: 'success', handler: (e) => handleConfirm()}, {title: 'Woepsie', color: 'error'}]);
    return;
  }
  handleConfirm();
}

function handleConfirm() {
  router.push(`/`);
}

const schema = Yup.object().shape({
  name: Yup.string().required('Voer een naam in'),
  icon: Yup.string(),
});

const vuetifyConfig = (state: any) => ({
  props: {
    'error-messages': state.errors,
  },
});

const {defineField, handleSubmit, resetForm, setValues, isFieldTouched} = useForm({
  validationSchema: schema,
});

const [name, nameProps] = defineField('name', vuetifyConfig);
const [icon, iconProps] = defineField('icon', vuetifyConfig);


const onSubmitHandler = handleSubmit((values, actions) => {
  listsStore.createList({
    name: name.value,
    icon: icon.value,
  }).then((list: WishlistDTO) => {
    useMessagesStore().showMessage('Lijst aangemaakt', 'success');
    router.push(`/list/${list.id}`);
  });
});

</script>

<template>
  <Messages/>
  <v-card title="Maak nieuwe lijst aan">
    <v-form @submit="onSubmitHandler">
      <v-card-text>
        <v-text-field
          v-model="name"
          label="Naam"
          v-bind="nameProps"
        ></v-text-field>
        <v-text-field
          v-model="icon"
          :prepend-icon="`mdi-${icon || 'view-list'}`"
          label="Icoon"
          v-bind="iconProps"
        />
        Voor iconen zie <a href="https://pictogrammers.com/library/mdi/" target="_blank">Material Design Icons</a>
      </v-card-text>
      <v-card-actions>
        <v-btn color="success" prepend-icon="mdi-check" type="submit" variant="tonal">Opslaan</v-btn>
        <v-btn color="error" prepend-icon="mdi-close" @click="returnToMain">Annuleren</v-btn>
      </v-card-actions>
    </v-form>
  </v-card>
</template>

<style scoped>

</style>
