<template>
  <v-app>
    <MainMenu/>
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script lang="ts" setup>
//
import MainMenu from "@/components/MainMenu.vue";
</script>
