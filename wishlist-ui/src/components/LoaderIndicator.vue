<script lang="ts" setup>

import axios from "axios";
import {computed, ref} from "vue";

const depth = ref(0);

axios.interceptors.request.use((config) => {
  console.log("Requesting: " + config.url);
  depth.value++;
  return config;
});

axios.interceptors.response.use((response) => {
  depth.value--;
  return response;
}, error => {
  depth.value--;
  return error;
});

const loading = computed(() => depth.value > 0);

</script>

<template>
  <v-progress-linear
    :active="loading"
    :indeterminate="loading"
    absolute
    bottom
    color="deep-purple-accent-4"
  ></v-progress-linear>
</template>

<style scoped>

</style>
