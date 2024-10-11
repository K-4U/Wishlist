<script lang="ts" setup>
import {WishlistDTO} from "@/api";
import {defineProps, ref} from "vue";
import {BeckersUserProp} from "@/proptypes";
import {getAvatarUrl} from "@/helpers";
import {useRouter} from "vue-router";
import {VSpeedDial} from "vuetify/components";
import ConfirmDialog from "@/components/ConfirmDialog.vue";

const router = useRouter();
const confirmDialogRef = ref<InstanceType<typeof ConfirmDialog> | null>(null);

const props = defineProps({
  user: BeckersUserProp,
  lists: Array<WishlistDTO>,
  own: {
    type: Boolean,
    default: false
  }
})

function openList(target: WishlistDTO) {
  router.push({path: `/list/${target.id}`});
}

const isEditModeOpen = ref(false);

function changeListName(e: any, listId: number) {
  e.preventDefault()
  console.log('change list name', listId);
}

function removeList(e: any, listId: number) {
  props.lists.forEach((list) => {
    if (list.id === listId) {
      // if(list.items)
      //TODO: Continue here
    }
  });
  confirmDialogRef.value?.open(
    'Weet je zeker dat je deze lijst wilt verwijderen?', 'Dit kan niet ongedaan worden gemaakt.',
    [{
      title: 'Ja', color: 'success', handler: () => actuallyRemoveList(listId)
    },
      {title: 'Nee', color: 'error'}
    ]);
}

function actuallyRemoveList(listId: Number) {
  console.log('actually remove list');
}

</script>

<template>
  <v-col cols="12" md="6">
    <v-card :prepend-avatar="getAvatarUrl(props.user)" :subtitle="props.own ? 'Je eigen lijsten' : ''"
            :title="props.user?.name" class="mx-auto">
      <v-card-text class="bg-surface-light pa-0 pl-2">
        <v-list>
          <v-list-item v-for="list in props.lists" :key="list.id" :prepend-icon="list.icon ?? 'mdi-view-list'"
                       @click="e => openList(list)">
            <v-list-item-title>{{ list.listName }}</v-list-item-title>
            <template v-if="isEditModeOpen" v-slot:append>
              <v-btn-group>
                <v-btn color="primary" icon size="small" variant="tonal"
                       @click.stop="(e:any) => changeListName(e, list.id)">
                  <v-icon>mdi-pencil</v-icon>
                </v-btn>
                <v-btn color="error" icon size="small" variant="tonal" @click.stop="(e:any) => removeList(e, list.id)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </v-btn-group>
            </template>
          </v-list-item>
        </v-list>
      </v-card-text>
      <v-card-actions v-if="own">
        <v-speed-dial ref="editRef"
                      location="right center"
                      transition="fade-transition"
                      @update:modelValue="val => isEditModeOpen = val"
        >
          <template v-slot:activator="{ props: activatorProps }">
            <v-btn color="warning" icon v-bind="activatorProps">
              <v-icon>mdi-playlist-edit</v-icon>
            </v-btn>

          </template>
          <v-btn key="1" color="success" icon="mdi-plus"></v-btn>

        </v-speed-dial>

      </v-card-actions>
    </v-card>
  </v-col>
  <ConfirmDialog ref="confirmDialogRef"/>
</template>

<style scoped>

</style>
