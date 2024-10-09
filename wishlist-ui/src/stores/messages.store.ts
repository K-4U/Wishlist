import {defineStore} from 'pinia';

export interface Message {
  message: string;
  messageType: 'success' | 'error' | 'info' | 'warning';
}

const STORAGE_KEY = 'messages-store';

export const useMessagesStore = defineStore({
  id: 'messages',
  state: (): { messages: Message[] } => ({
    messages: JSON.parse(localStorage.getItem(STORAGE_KEY) || '[]')
  }),
  getters: {
  },
  actions: {
    showMessage(message: string, messageType?: 'success' | 'error' | 'info' | 'warning') {
      if (messageType === undefined) {
        messageType = 'info';
      }
      console.log('showMessage', message, messageType);
      this.messages.push({message, messageType});
      localStorage.setItem(STORAGE_KEY, JSON.stringify(this.messages));
    },
    clearMessages() {
      this.messages = [];
      localStorage.setItem(STORAGE_KEY, JSON.stringify(this.messages));
    },
    getAllMessages(): Message[] {
      const msgTemp: Message[] = this.messages;
      this.clearMessages();
      return msgTemp;
    }
  }
});
