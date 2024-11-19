<script lang="ts" setup>

import {useDialogStore} from "@/stores/dialog.store";

const store = useDialogStore();

</script>


<template>
  <v-dialog v-model="store.confirm.visible" max-width="800">
    <v-card :color="store.confirm.color" :prepend-icon="store.confirm.icon" :title="store.confirm.title">
      <v-card-text>{{ store.confirm.message }}</v-card-text>
      <v-card-actions>
        <v-btn v-for="button in store.confirm.buttons" :color="button.color" :text="button.title"
               @click="() => store.confirmCallback(button)"/>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>
