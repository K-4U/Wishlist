import {BeckersUserProp} from "@/proptypes";

export function getAvatarUrl(user: BeckersUserProp) {
  return new URL("/src/assets/avatar/" + user.avatarName, import.meta.url).href
}


export function formatCurrency(value: number, locale: string = 'nl-NL', currency: string = 'EUR'): string {
  return new Intl.NumberFormat(locale, {style: 'currency', currency}).format(value);
}
