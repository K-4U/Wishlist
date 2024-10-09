<script setup lang="ts">
import {useTheme} from "vuetify";
import {computed, onMounted, Ref, ref} from "vue";
import {useSettingsStore} from "@/stores/settings.store";

const theme = useTheme();
const settings = useSettingsStore();

enum ThemeMode {
  LIGHT = 'light',
  DARK = 'dark',
  AUTO = 'auto'
}

const mode: Ref<ThemeMode> = ref(settings.getSetting('themeMode') as ThemeMode ?? ThemeMode.AUTO)

const icon = computed(() => {
  switch (mode.value) {
    case ThemeMode.AUTO:
      return 'mdi-brightness-auto'
    case ThemeMode.LIGHT:
      return 'mdi-weather-sunny'
    case ThemeMode.DARK:
      return 'mdi-weather-night'
  }
})

function toggleTheme() {
  switch (mode.value) {
    case ThemeMode.AUTO:
      mode.value = ThemeMode.LIGHT
      break
    case ThemeMode.LIGHT:
      mode.value = ThemeMode.DARK
      break
    case ThemeMode.DARK:
      mode.value = ThemeMode.AUTO
      break
  }
  console.log(mode.value);
  settings.setSetting('themeMode', mode.value);
  setThemeInBrowser();
}

function setThemeInBrowser() {
  if (mode.value === ThemeMode.AUTO) {
    const prefersDarkMode = window.matchMedia && window.matchMedia('(prefers-color-scheme: dark)').matches;
    theme.global.name.value = prefersDarkMode ? 'dark' : 'light'
  } else {
    theme.global.name.value = mode.value
  }
}

if (window.matchMedia) {
  window.matchMedia('(prefers-color-scheme: dark)').addEventListener('change', e => {
    if (mode.value === ThemeMode.AUTO) {
      theme.global.name.value = e.matches ? 'dark' : 'light';
    }
  });
}

onMounted(() => {
  setThemeInBrowser()
})

</script>

<template>
  <v-btn
    slim
    icon
    @click="toggleTheme"
  >
    <v-icon :icon="icon"/>
  </v-btn>
</template>

<style scoped>

</style>
