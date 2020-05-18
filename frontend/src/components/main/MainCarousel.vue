<template>
    <v-carousel
        cycle
        height="400"
        delimiter-icon="mdi-circle-medium"
        hide-delimiter-background
        show-arrows-on-hover
    >
        <!-- 여기에서는 아마도 새 창보다는 그냥 링크로 연결해주는 것이 맞아보임. -->
        <v-carousel-item
            v-for="(item, i) in items"
            :key="i"
            :src="item.src"
            :href="item.link"
            target="_blank"
        ></v-carousel-item>
    </v-carousel>
</template>

<script>
export default {
    data() {
        return {
            items: [
                {
                    src: 'https://cdn.vuetifyjs.com/images/carousel/squirrel.jpg',
                    link: 'https://www.naver.com',
                },
                {
                    src: 'https://cdn.vuetifyjs.com/images/carousel/sky.jpg',
                    link: 'https://vuetifyjs.com/ko/',
                },
                {
                    src: 'https://cdn.vuetifyjs.com/images/carousel/bird.jpg',
                    link: 'https://www.google.com',
                },
                {
                    src: 'https://cdn.vuetifyjs.com/images/carousel/planet.jpg',
                    link: 'https://www.youtube.com',
                },
            ],
        }
    }
}
</script>

<style lang="scss" scoped>
.v-btn .v-btn__content .v-icon {
    color: black;
}
</style>