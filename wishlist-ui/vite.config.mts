// Plugins
import Components from 'unplugin-vue-components/vite'
import Vue from '@vitejs/plugin-vue'
import Vuetify, {transformAssetUrls} from 'vite-plugin-vuetify'
import ViteFonts from 'unplugin-fonts/vite'
import VueRouter from 'unplugin-vue-router/vite'
import Layouts from 'vite-plugin-vue-layouts'
import vueDevTools from 'vite-plugin-vue-devtools'

// Utilities
import {defineConfig, loadEnv} from 'vite'
import {fileURLToPath, URL} from 'node:url'
import {VitePWA} from "vite-plugin-pwa";

// https://vitejs.dev/config/
export default defineConfig(({command, mode}) => {
    const env = loadEnv(mode, process.cwd());
    return {
        plugins: [
            VueRouter({
                dts: 'src/typed-router.d.ts',
            }),
            Layouts(),
            Vue({
                template: {transformAssetUrls},
            }),
            // https://github.com/vuetifyjs/vuetify-loader/tree/master/packages/vite-plugin#readme
            Vuetify({
                autoImport: true,
                styles: {
                    configFile: 'src/styles/settings.scss',
                },
            }),
            Components(),
            ViteFonts({
                google: {
                    families: [{
                        name: 'Roboto',
                        styles: 'wght@100;300;400;500;700;900',
                    }],
                },
            }),
            vueDevTools(),
            VitePWA({
                registerType: 'autoUpdate',
                includeAssets: ['favicon.ico', 'icon.png'],
                manifest: {
                    name: 'Wishlist',
                    short_name: 'wishlist',
                    description: 'Beckers Wishlist',
                    theme_color: '#4DBA87',
                    icons: [
                        {
                            src: '/icon.png',
                            sizes: '512x512',
                            type: 'image/png'
                        },
                        {
                            src: '/icon-192.png',
                            sizes: '192x192',
                            type: 'image/png'
                        }
                    ]
                }
            })
        ],
        /*build: {
            minify: false,
        },*/
        define: {'process.env': {...env, ...{mode: mode}}},
        resolve: {
            alias: {
                '@': fileURLToPath(new URL('./src', import.meta.url)),
            },
            extensions: [
                '.js',
                '.json',
                '.jsx',
                '.mjs',
                '.ts',
                '.tsx',
                '.vue',
            ],
        },
        server: {
            host: true,
            port: 3000,
        },
    }
})
