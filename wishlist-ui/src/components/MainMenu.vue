<script lang="ts" setup>
import {useAuthStore} from "@/stores";
import ThemeSwitcher from "@/components/ThemeSwitcher.vue";
import {getAvatarUrl} from "@/helpers";
import {useRouter} from "vue-router";
import {computed} from "vue";


const auth = useAuthStore();
const avatar = getAvatarUrl(auth.user);

const router = useRouter();

const showHomeButton = computed(() => {
  return router.currentRoute.value.name == '/';
});
const showBackButton = computed(() => {
  return !showHomeButton.value && !router.currentRoute.value.meta.previousPage;
});
const showPreviousButton = computed(() => {
  return !showHomeButton.value && router.currentRoute.value.meta.previousPage;
});

</script>

<template>
  <v-app-bar scroll-behavior="collapse">
    <v-toolbar>
      <span class="ml-4"></span>
      <v-btn v-if="showHomeButton" icon>
        <v-icon>mdi-home</v-icon>
      </v-btn>
      <v-btn v-if="showBackButton" icon @click="$router.push('/')">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>
      <v-btn v-else v-if="showPreviousButton" icon
             @click="$router.push(router.currentRoute.value.meta.previousPage ?? '/')">
        <v-icon>mdi-arrow-left</v-icon>
      </v-btn>

      <v-toolbar-title>Wishlist</v-toolbar-title>
      <v-spacer></v-spacer>

      <ThemeSwitcher/>
      <span class="mr-2">{{ auth.user?.name }}</span>

      <v-menu class="d-none d-md-flex">
        <template v-slot:activator="{ props }">
          <v-btn class="d-none mr-10 d-md-flex" icon v-bind="props">
            <v-avatar :image="avatar" class="elevation-5" size="large"/>
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
            <v-avatar :image="avatar" class="elevation-5" size="large"/>
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
