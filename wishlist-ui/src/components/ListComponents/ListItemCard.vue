<script lang="ts" setup>

import {defineProps, onMounted, ref} from "vue";
import {WishlistItemProp, WishlistProp} from "@/proptypes";
import {useRouter} from "vue-router";
import ListItemActions from "@/components/ListComponents/ListItemActions.vue";
import {formatCurrency} from "@/helpers";
import {WishlistDTO, WishlistItemDTO} from "@/api";

const router = useRouter();
const item = ref<WishlistItemDTO>();
const list = ref<WishlistDTO>()

const props = defineProps({
  item: WishlistItemProp,
  list: WishlistProp,
  own: {
    type: Boolean,
    default: false
  }
})
onMounted(() => {
  item.value = props.item;
  list.value = props.list;
});

</script>

<template>
  <v-col cols="12">
    <v-card :title="item?.description" class="mx-auto v-col-12">
      <v-card-text>
        <v-table>
          <tr>
            <td class="leftHeader ma-auto pr-2">Prijs:</td>
            <td class="valueColumn">{{ formatCurrency(item?.price) }}</td>
            <td class="leftHeader ma-auto pr-2">Winkel:</td>
            <td class="valueColumn"><a v-if="item?.hasValidUrl" :href="item?.url" target="_blank">{{ item?.store }}</a>
              <span v-else>{{ item?.store }}</span></td>
          </tr>
        </v-table>
        {{ item?.remarks }}
      </v-card-text>
      <v-card-actions>
        <ListItemActions :item="item" :list="list" :own="own"/>
      </v-card-actions>
    </v-card>
  </v-col>
</template>

<style scoped>
.leftHeader {
  font-weight: bold;
  width: 5%;
}

.valueColumn {
  width: 40%
}
</style>
