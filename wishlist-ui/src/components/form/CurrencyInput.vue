<script lang="ts" setup>
import {CurrencyDisplay, useCurrencyInput} from 'vue-currency-input';
import {watch} from 'vue';

const props = defineProps({modelValue: Number, label: String, prependInnerIcon: String});

const {inputRef, formattedValue, numberValue, setValue} = useCurrencyInput({
  currency: 'EUR',
  locale: 'nl-NL',
  hideCurrencySymbolOnFocus: false,
  hideNegligibleDecimalDigitsOnFocus: false,
  useGrouping: false,
  currencyDisplay: CurrencyDisplay.hidden,
  precision: 2,
  valueRange: {min: 0},
});

watch(
  () => props.modelValue,
  (value) => {
    setValue(value ?? 0);
  }
);
</script>

<template>
  <VTextField
    ref="inputRef"
    v-model="formattedValue"
    :label="label"
    :prepend-inner-icon="prependInnerIcon"
  >
  </VTextField>
</template>
