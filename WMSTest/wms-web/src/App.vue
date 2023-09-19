<template>
    <div id="app">
        <!--    <index></index>-->
        <router-view/>
    </div>
</template>

<script>

    // import Index from "@/components/index";
    export default {
        name: 'App',
        components: {
            // Index
        },
        data() {
            return {
                user: JSON.parse(sessionStorage.getItem('CurUser')),
            }
        },
        watch: {
            '$store.state.menu': {
                handler(val, old) {
                    if (!old && this.user && this.user.no) {
                        this.$store.commit("setRouter",val)
                    }
                },
                immediate: true
            }
        }
    }
</script>

<style>
    #app {
        height: 100%;
    }
</style>
