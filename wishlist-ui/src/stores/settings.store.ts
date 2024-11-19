import {defineStore} from 'pinia';
import {ref} from 'vue';

interface Setting {
  key: string,
  value: string | number | boolean
}

export const useSettingsStore = defineStore('settings', () => {
  const settings = ref<Setting[]>(JSON.parse(localStorage.getItem("settings") || '[]'));

  function setSetting(key: string, value: string | number | boolean) {
    const setting = settings.value.find(setting => setting.key === key);
    if (setting) {
      setting.value = value;
    } else {
      settings.value.push({key, value});
    }
    localStorage.setItem("settings", JSON.stringify(settings.value));
  }

  function getSetting(key: string) {
    const setting = settings.value.find(setting => setting.key === key);
    return setting ? setting.value : undefined;
  }

  return {
    settings,
    setSetting,
    getSetting
  };
});
