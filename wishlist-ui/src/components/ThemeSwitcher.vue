<script setup lang="ts">
import {useTheme} from "vuetify";
import {computed, onMounted, ref} from "vue";

const theme = useTheme();
const mode: ThemeMode = ref(localStorage.getItem('themeMode') ?? ThemeMode.AUTO)

enum ThemeMode {
  LIGHT = 'light',
  DARK = 'dark',
  AUTO = 'auto'
}

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
  localStorage.setItem('themeMode', mode.value)
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
    :prepend-icon="icon"
    slim
    @click="toggleTheme"
  ></v-btn>
</template>

<style scoped>

</style>
