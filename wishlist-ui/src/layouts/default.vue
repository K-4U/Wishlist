<template>
  <v-app>
    <v-main>
      <v-container>
        <MainMenu/>
        <router-view/>
        <ApiErrorHandler/>
      </v-container>
    </v-main>
  </v-app>
</template>

<script lang="ts" setup>
//
import MainMenu from "@/components/MainMenu.vue";
import ApiErrorHandler from "@/components/ApiErrorHandler.vue";
</script>
