import {AuthenticationApi, Configuration, ListsApi} from "@/api/generated";

export * from "./generated";
export * from "./generated/base";

let basePath = process.env.VITE_BASE_PATH || 'INVALID_BASE_PATH';
console.dir(process.env);
//@ts-ignore
if (process.env.mode === 'development' || basePath === 'INVALID_BASE_PATH') {
  //Override the basepath:
  basePath = (window.location.protocol === 'https' ? 'https' : 'http') + "://" + window.location.hostname + ':8081'
}

const configurationWithoutAuth: Configuration = new Configuration({
  basePath: basePath
});
const configurationWithAuth: Configuration = new Configuration({
  basePath: basePath,
  //@ts-ignore
  accessToken: () => {
    return localStorage.getItem('token');
  }
});

console.log("Initializing API with basepath: " + basePath);
export const authenticationApi: AuthenticationApi = new AuthenticationApi(configurationWithoutAuth);
export const listsApi: ListsApi = new ListsApi(configurationWithAuth);
