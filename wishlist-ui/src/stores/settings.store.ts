import {defineStore} from 'pinia';

interface Setting {
  key: string,
  value: string | number | boolean
}

export const useSettingsStore = defineStore({
  id: 'settings',
  state: (): { settings: Setting[] } => ({
    settings: JSON.parse(localStorage.getItem("settings") || '[]')
  }),
  getters: {},
  actions: {
    setSetting(key: string, value: string | number | boolean) {
      const setting = this.settings.find(setting => setting.key === key);
      if (setting) {
        setting.value = value;
      } else {
        this.settings.push({key, value});
      }
      localStorage.setItem("settings", JSON.stringify(this.settings));
    },
    getSetting(key: string) {
      const setting = this.settings.find(setting => setting.key === key);
      return setting ? setting.value : undefined;
    }
  }
});
