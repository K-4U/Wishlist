import {BeckersUserProp} from "@/proptypes";

export function getAvatarUrl(user: BeckersUserProp) {
  return new URL("/src/assets/avatar/" + user.avatarName, import.meta.url).href
}
