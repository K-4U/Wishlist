<script lang="ts" setup>

import {defineProps, onMounted} from "vue";
import {WishlistItemProp, WishlistProp} from "@/proptypes";
import {useRouter} from "vue-router";
import ListItemActions from "@/components/ListItemActions.vue";
import {formatCurrency} from "../helpers";

const router = useRouter();

const props = defineProps({
  item: WishlistItemProp,
  list: WishlistProp,
  own: {
    type: Boolean,
    default: false
  }
})
onMounted(() => {

});

</script>

<template>
  <v-col cols="12">
    <v-card :title="props.item?.description" class="mx-auto v-col-12">
      <v-card-text>
        <v-table>
          <tr>
            <td>Prijs:</td>
            <td>{{ formatCurrency(props.item?.price) }}</td>
          </tr>
          <tr>
            <td>Winkel:</td>
            <td><a v-if="props.item?.hasValidUrl" :href="props.item?.url" target="_blank">{{ props.item?.store }}</a>
              <span v-else>{{ props.item?.store }}</span></td>
          </tr>
        </v-table>
        {{ props.item?.remarks }}
      </v-card-text>
      <v-card-actions>
        <ListItemActions :item="props.item" :list="list" :own="own"/>
      </v-card-actions>
    </v-card>
  </v-col>
</template>

<style scoped>

</style>
