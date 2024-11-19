import {defineStore} from 'pinia';
import {ref} from 'vue';

export interface Message {
  message: string;
  messageType: 'success' | 'error' | 'info' | 'warning';
}

const STORAGE_KEY = 'messages-store';

export const useMessagesStore = defineStore('messages', () => {
  const messages = ref<Message[]>(JSON.parse(localStorage.getItem(STORAGE_KEY) || '[]'));

  function showMessage(message: string, messageType: 'success' | 'error' | 'info' | 'warning' = 'info') {
    console.log('showMessage', message, messageType);
    messages.value.push({message, messageType});
    localStorage.setItem(STORAGE_KEY, JSON.stringify(messages.value));
  }

  function clearMessages() {
    messages.value = [];
    localStorage.setItem(STORAGE_KEY, JSON.stringify(messages.value));
  }

  function getAllMessages(): Message[] {
    const msgTemp: Message[] = messages.value;
    clearMessages();
    return msgTemp;
  }

  return {
    messages,
    showMessage,
    clearMessages,
    getAllMessages
  };
});
