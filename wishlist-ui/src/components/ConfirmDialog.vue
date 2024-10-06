<script lang="ts" setup>
import {defineEmits, defineExpose, defineProps, ref} from 'vue';

interface Button {
  title: string;
  color: string;
}

const props = defineProps({
  icon: {
    type: String,
    default: 'mdi-alert-circle',
  },
  color: {
    type: String,
    default: '',
  },
  yesNo: {
    type: Boolean,
    default: true,
  },
  buttons: {
    type: Array as () => Button[],
    default: () => [],
  },
});

const emit = defineEmits(['confirm', 'cancel', 'button-pressed']);

const visible = ref(false);
const title = ref('');
const message = ref('');

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

defineExpose({
  open(_title: string, _message: string) {
    title.value = _title
    message.value = _message
    visible.value = true;
  },
});

</script>


<template>
  <v-dialog v-model="visible" max-width="800">
    <v-card :color="color" :prepend-icon="icon" :title="title">
      <v-card-text>{{ message }}</v-card-text>
      <v-card-actions v-if="!buttons">
        <v-btn color="success" @click="confirm">Ja</v-btn>
        <v-btn color="error" @click="cancel">Nee</v-btn>
      </v-card-actions>
      <v-card-actions v-else>
        <v-btn v-for="button in buttons" :color="button.color" :text="button.title"
               @click="(e:any) => eventHandler(button)"/>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>
