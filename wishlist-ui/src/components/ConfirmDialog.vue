<script lang="ts">
import {ref} from 'vue';

const visible = ref(false);
const title = ref('');
const message = ref('');

function openDialog(dialogTitle: string, dialogMessage: string) {
  title.value = dialogTitle;
  message.value = dialogMessage;
  visible.value = true;
}

interface Button {
  title: string;
  color: string;
}

export {openDialog, Button};

export default {
  name: 'CustomConfirmDialog',
  props: {
    yesNo: {
      type: Boolean,
      default: true,
    },
    buttons: {
      type: Array<Button>,
      default: [{title: 'Ja', color: 'success'}, {title: 'Nee', color: 'error'}],
    },
  },
  emits: ['confirm', 'cancel', 'button-pressed'],
  setup(props, {emit}) {

    function confirm() {
      visible.value = false;
      emit('confirm');
    }

    function cancel() {
      visible.value = false;
      emit('cancel');
    }

    function eventHandler(button: Button) {
      visible.value = false;
      emit('button-pressed', button.title.toLowerCase());
    }

    return {
      visible,
      title,
      message,
      confirm,
      cancel,
      eventHandler
    };
  },
};
</script>

<!-- CustomConfirmDialog.vue -->
<template>
  <v-dialog v-model="visible" max-width="400">
    <v-card>
      <v-card-title class="headline">{{ title }}</v-card-title>
      <v-card-text>{{ message }}</v-card-text>
      <v-card-actions v-if="!buttons">
        <v-btn color="success" @click="confirm">Ja</v-btn>
        <v-btn color="error" @click="cancel">Nee</v-btn>
      </v-card-actions>
      <v-card-actions v-else>
        <v-btn v-for="button in buttons" :color="button.color" @click="e => eventHandler(button)">{{
            button.title
          }}
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>
