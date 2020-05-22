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
            :src="require(`@/assets/images/${item.src}`)"
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
                    src: 'banner4.png',
                    link: 'https://www.naver.com',
                },
                {
                    src: 'banner2.png',
                    link: 'https://vuetifyjs.com/ko/',
                },
                {
                    src: 'banner3.png',
                    link: 'https://www.google.com',
                },
                {
                    src: 'banner1.png',
                    link: 'https://www.youtube.com',
                },
                {
                    src: 'banner0.png',
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