<template>
  <v-app class="login-app">
    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<script lang="ts" setup>
//
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
