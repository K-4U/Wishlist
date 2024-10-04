<template>
  <v-app>
    <MainMenu/>
    <v-main>
      <v-container>
        <router-view/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script lang="ts" setup>
//
import MainMenu from "@/components/MainMenu.vue";
</script>
