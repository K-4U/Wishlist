<script lang="ts" setup>
import * as Yup from 'yup';
import {useAuthStore} from '@/stores';
import {definePage} from "unplugin-vue-router/runtime";
import {useForm} from "vee-validate";
import {ref} from "vue";
import {LoginError} from "@/exceptions/LoginError";

definePage({
  meta: {
    layout: 'login'
  }
})


const schema = Yup.object().shape({
  username: Yup.string().email('Voer geldig e-mail adres in').required('Voer een e-mail adres in'),
  password: Yup.string().required('Voer een wachtwoord in')
});

const vuetifyConfig = (state: any) => ({
  props: {
    'error-messages': state.errors,
  },
});

const {defineField, handleSubmit, resetForm} = useForm({
  validationSchema: schema,
});

const [username, usernameProps] = defineField('username', vuetifyConfig);
const [password, passwordProps] = defineField('password', vuetifyConfig);

const isSubmitting = ref(false);
const visible = ref(false);
const onSubmitHandler = handleSubmit((values, actions) => {
  isSubmitting.value = true;
  const authStore = useAuthStore();
  const {username, password} = values;

  authStore.login(username, password).catch((error: LoginError) => {
    isSubmitting.value = false;
    if (error.code === 401) {
      actions.setFieldError("username", 'Ongeldige gebruikersnaam of wachtwoord');
      actions.resetField('password');
    }
  });
});


</script>

<template>
  <v-container class="fill-height" fluid>
    <v-row justify="center">
      <v-col class="v-col-md-10 v-col-lg-2 v-col-sm-10" cols="10">
        <v-card
          class="mx-auto"
          elevation="8"
          rounded="lg">
          <v-card-text>
            <v-img
              class="mx-auto my-6 rounded-circle elevation-10"
              max-height="200"
              max-width="200"
              src="@/assets/logo.jpg"
            ></v-img>
            <v-form @submit="onSubmitHandler">
              <v-text-field
                v-model="username"
                density="compact"
                placeholder="Email"
                prepend-inner-icon="mdi-email-outline"
                v-bind="usernameProps"
                variant="outlined"
              ></v-text-field>

              <div class="text-subtitle-1 text-medium-emphasis d-flex align-center justify-space-between">
                <a
                  class="text-caption text-decoration-none text-blue"
                  href="#"
                  rel="noopener noreferrer"
                  target="_blank"
                >
                  Wachtwoord vergeten?</a>
              </div>

              <v-text-field
                v-model="password"
                :append-inner-icon="visible ? 'mdi-eye-off' : 'mdi-eye'"
                :type="visible ? 'text' : 'password'"
                density="compact"
                placeholder="Wachtwoord"
                prepend-inner-icon="mdi-lock-outline"
                v-bind="passwordProps"
                variant="outlined"
                @click:append-inner="visible = !visible"
              ></v-text-field>

              <v-btn
                :disabled="isSubmitting"
                block
                class="mb-8"
                color="blue"
                size="large"
                type="submit"
                variant="tonal"
              >
                Log In
              </v-btn>
            </v-form>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<style scoped>

</style>
