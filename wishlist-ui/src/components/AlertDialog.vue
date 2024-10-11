<script lang="ts" setup>
import {useDialogStore} from "@/stores/dialog.store";

const store = useDialogStore();

</script>


<template>
  <v-dialog v-model="store.alert.visible" max-width="800">
    <v-card :color="store.alert.color" :prepend-icon="store.alert.icon" :title="store.alert.title">
      <v-card-text>{{ store.alert.message }}</v-card-text>
      <v-card-actions>
        <v-btn color="success" @click="store.alertCallback">Ok</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>
