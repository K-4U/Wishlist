<script lang="ts" setup>
import {ref} from "vue";
import axios from "axios";

axios.interceptors.response.use((response) => {
  return response;
}, (error) => {
  if (error.response.data.code === 500) {
    subtitle.value = "Het ligt aan mij, niet aan jou.";
  }
  details.value = [`Bericht: ${error.response.data.message}`,
    `Code: ${error.response.data.code}`,
    `Id: ${error.response.data.id}`,
    `Exceptions: ${error.response.data.exceptions.map(e => e.message).join(', ')}`];

  visible.value = true;

  return error;
});

const visible = ref(false);
const subtitle = ref('');
const details = ref('');

</script>

<template>
  <v-dialog v-model="visible" max-width="500">
    <v-card :subtitle="subtitle" prepend-icon="mdi-alert-circle" title="Dat ging niet helemaal lekker!">
      <v-card-text>
        Er is iets misgegaan. Probeer het later nog eens.

        <v-expansion-panels class="ma-auto pt-10">
          <v-expansion-panel v-if="details" title="Klik hier voor meer info om aan Koen te laten zien">
            <v-expansion-panel-text>
              <p v-for="detail in details">{{ detail }}</p>
            </v-expansion-panel-text>
          </v-expansion-panel>
        </v-expansion-panels>
      </v-card-text>
      <v-card-actions>
        <v-spacer></v-spacer>

        <v-btn
          text="Ok"
          @click="visible = false"
        ></v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>

<style scoped>

</style>
