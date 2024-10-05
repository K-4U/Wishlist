<script lang="ts" setup>

import {defineProps} from "vue";
import {WishlistItemProp} from "@/proptypes";
import {useRouter} from "vue-router";

const router = useRouter();

const props = defineProps({
  item: WishlistItemProp,
  own: {
    type: Boolean,
    default: false
  }
})

</script>

<template>
  <v-btn-group v-if="own">
    <v-btn color="primary" icon size="small" variant="tonal">
      <v-icon>mdi-pencil</v-icon>
    </v-btn>
    <v-btn color="error" icon size="small" variant="tonal">
      <v-icon>mdi-delete</v-icon>
    </v-btn>
  </v-btn-group>
  <v-btn-group v-else>
    <v-btn color="primary" icon size="small" variant="tonal">
      <v-icon>mdi-cart-plus</v-icon>
    </v-btn>
  </v-btn-group>
</template>

<style scoped>

</style>
