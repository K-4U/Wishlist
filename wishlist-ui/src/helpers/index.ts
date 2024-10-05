import {BeckersUser} from "@/api";

export function getAvatarUrl(user: BeckersUser | undefined | null): string {
  if (!user || !user.avatarName) {
    return new URL("/avatar/unknown.png", import.meta.url).href
  }
  return new URL("/avatar/" + user.avatarName, import.meta.url).href
}


export function formatCurrency(value: number | undefined, locale: string = 'nl-NL', currency: string = 'EUR'): string {
  return new Intl.NumberFormat(locale, {style: 'currency', currency}).format(value || 0);
}
