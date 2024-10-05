<template>
  <v-app>
    <v-main>
      <v-container>
        <MainMenu/>
        <router-view/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script lang="ts" setup>
//
import MainMenu from "@/components/MainMenu.vue";
</script>
