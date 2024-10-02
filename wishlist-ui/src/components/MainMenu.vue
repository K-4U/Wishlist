<script lang="ts" setup>
import {useAuthStore} from "@/stores";
import {computed} from "vue";

const auth = useAuthStore();
const avatar = computed(() => new URL("/src/assets/avatar/" + auth.user.avatarName, import.meta.url).href);
</script>

<template>
  <v-app-bar scroll-behavior="collapse">
    <v-toolbar>
      <v-app-bar-nav-icon></v-app-bar-nav-icon>
      <v-toolbar-title>Wishlist</v-toolbar-title>
      <v-spacer></v-spacer>
      <span class="mr-2">{{ auth.user.name }}</span>

      <v-menu class="d-none d-md-flex">
        <template v-slot:activator="{ props }">
          <v-btn class="d-none mr-10 d-md-flex" icon v-bind="props">
            <v-avatar :image="avatar" size="large"/>
          </v-btn>
        </template>

        <v-list>
          <v-list-item prepend-icon="mdi-logout" @click="auth.logout">
            Logout
          </v-list-item>
        </v-list>
      </v-menu>

      <v-bottom-sheet class="d-flex d-md-none">
        <template v-slot:activator="{ props }">
          <v-btn class="mr-10 d-flex d-md-none" icon v-bind="props">
            <v-avatar :image="avatar" size="large"/>
          </v-btn>
        </template>

        <v-list>
          <v-list-item prepend-icon="mdi-logout" @click="auth.logout">
            Logout
          </v-list-item>
        </v-list>
      </v-bottom-sheet>

    </v-toolbar>

  </v-app-bar>
</template>

<style scoped>

</style>
