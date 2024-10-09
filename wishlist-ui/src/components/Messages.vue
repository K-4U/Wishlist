<script lang="ts" setup>
import {Message, useMessagesStore} from "@/stores/messages.store";
import {onMounted, ref} from "vue";

const messagesStore = useMessagesStore();
const messageList = ref<Message[]>([]);
onMounted(() => {
  //We do this, so that it only fetches the messages once, when the component is mounted.
  messageList.value = messagesStore.getAllMessages();
});

</script>

<template>
  <v-expand-transition>
    <v-alert
      v-for="(message, index) in messageList"
      :key="index"
      :text="message.message"
      :type="message.messageType"
      border="start"
      closable
      elevation="2"
      transition="scale-transition"
    />
  </v-expand-transition>
</template>

<style scoped>

</style>
