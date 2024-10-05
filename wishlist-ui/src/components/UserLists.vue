<script lang="ts" setup>
import {Wishlist} from "@/api";
import {computed, defineProps, onMounted} from "vue";
import {BeckersUserProp} from "@/proptypes";
import {getAvatarUrl} from "@/helpers";
import {useRouter} from "vue-router";

const router = useRouter();

const props = defineProps({
  user: BeckersUserProp,
  lists: Array<Wishlist>,
  own: {
    type: Boolean,
    default: false
  }
})
onMounted(() => {

});
const ownText = computed(() => {
  return props.own ? 'Je eigen lijsten' : '';
});

function openList(target: Wishlist) {
  console.log(target.id);
  router.push({path: `/list/${target.id}`});
}

</script>

<template>
  <v-col cols="12" md="6">
    <v-card :prepend-avatar="getAvatarUrl(props.user)" :subtitle="ownText" :title="props.user?.name" class="mx-auto"
            color="surface-variant">
      <v-card-text class="bg-surface-light pa-0 pl-2">
        <v-list>
          <v-list-item v-for="list in props.lists" :key="list.id" :prepend-icon="list.icon ?? 'mdi-view-list'"
                       @click="e => openList(list)">
            <v-list-item-title>{{ list.listName }}</v-list-item-title>
          </v-list-item>
        </v-list>
      </v-card-text>
      <v-card-actions v-if="own">
        <v-btn color="success" variant="tonal">
          Edit
          <!--          <v-icon color="success">mdi-pencil</v-icon> &lt;!&ndash; TODO: Edit mode &ndash;&gt;-->
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-col>
</template>

<style scoped>

</style>
