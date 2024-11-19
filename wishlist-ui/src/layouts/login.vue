<template>
  <v-app class="login-app">
    <v-main>
      <router-view/>
      <ApiErrorHandler/>
    </v-main>
  </v-app>
</template>

<script lang="ts" setup>
//
import ApiErrorHandler from "@/components/ApiErrorHandler.vue";
</script>

<style scoped>
.login-app {
  background: url("@/assets/wall.jpg") no-repeat bottom center fixed;
  -webkit-background-size: cover;
  -moz-background-size: cover;
  -o-background-size: cover;
  background-size: cover;
}
</style>
