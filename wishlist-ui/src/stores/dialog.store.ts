import {defineStore} from 'pinia';

interface Button {
  title: string;
  color: string;
  handler?: (e: any) => void;
}

export const useDialogStore = defineStore({
  id: 'alert',
  state: (): {} => ({
    alert: {
      visible: false,
      message: '',
      title: '',
      callback: () => {
      },
      icon: 'mdi-information',
      color: null
    },
    confirm: {
      visible: false,
      message: '',
      title: '',
      icon: 'mdi-information',
      color: null,
      buttons: [] as Button[]
    }
  }),
  getters: {},
  actions: {
    showAlert(title: string, message: string, callback: () => void, icon: string | null = 'mdi-information', color: string | null = null) {
      this.alert.title = title;
      this.alert.message = message;
      this.alert.callback = callback;
      this.alert.icon = icon ?? 'mdi-information';
      this.alert.color = color;
      this.alert.visible = true;
    },
    showConfirm(title: string, message: string, buttons: Button[], icon: string | null = 'mdi-information', color: string | null = null) {
      this.confirm.title = title;
      this.confirm.message = message;
      this.confirm.buttons = buttons;
      this.confirm.icon = icon ?? 'mdi-information';
      this.confirm.color = color;
      this.confirm.visible = true;
    },
    alertCallback() {
      this.alert.visible = false
      this.alert.callback();
    },
    confirmCallback(button: Button) {
      this.confirm.visible = false
      if (button.handler) {
        button.handler(null);
      }
    }
  }
});
