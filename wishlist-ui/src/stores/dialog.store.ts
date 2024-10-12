import {defineStore} from 'pinia';
import {ref} from 'vue';

interface Button {
  title: string;
  color: string;
  handler?: (e: any) => void;
}

export const useDialogStore = defineStore('alert', () => {
  const alert = ref({
    visible: false,
    message: '',
    title: '',
    callback: () => {
    },
    icon: 'mdi-information',
    color: ''
  });

  const confirm = ref({
    visible: false,
    message: '',
    title: '',
    icon: 'mdi-information',
    color: '',
    buttons: [] as Button[]
  });

  function showAlert(title: string, message: string, callback: () => void, icon: string | null = 'mdi-information', color: string | null = null) {
    alert.value.title = title;
    alert.value.message = message;
    alert.value.callback = callback;
    alert.value.icon = icon ?? 'mdi-information';
    alert.value.color = color ?? '';
    alert.value.visible = true;
  }

  function showConfirm(title: string, message: string, buttons: Button[], icon: string | null = 'mdi-information', color: string | null = null) {
    confirm.value.title = title;
    confirm.value.message = message;
    confirm.value.buttons = buttons;
    confirm.value.icon = icon ?? 'mdi-information';
    confirm.value.color = color ?? '';
    confirm.value.visible = true;
  }

  function alertCallback() {
    alert.value.visible = false;
    if (alert.value.callback) {
      alert.value.callback();
    }
  }

  function confirmCallback(button: Button) {
    confirm.value.visible = false;
    if (button.handler) {
      button.handler(null);
    }
  }

  return {
    alert,
    confirm,
    showAlert,
    showConfirm,
    alertCallback,
    confirmCallback
  };
});
