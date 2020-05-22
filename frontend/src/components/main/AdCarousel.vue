<template>
    <v-carousel
        cycle
        height="440"
        hide-delimiter-background
        show-arrows-on-hover
    >
        <!-- 광고 클릭을 통해 새 창 or 새 탭으로 링크를 띄우는 것이 적합해보임. -->
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
                    src: 'https://yomistore.com/wp-content/uploads/2019/09/main01-56.jpg',
                    link: 'https://www.naver.com',
                },
                {
                    src: 'https://yomistore.com/wp-content/uploads/2019/08/main02.jpg',
                    link: 'https://vuetifyjs.com/ko/',
                },
                {
                    src: 'https://i.pinimg.com/originals/91/a3/2d/91a32d22a0f1f2981fc166753d323a61.png',
                    link: 'https://www.google.com',
                },
                {
                    src: 'https://gi.esmplus.com/jumondomio/U5package/U5P_main.jpg',
                    link: 'https://www.youtube.com',
                },
            ],
        }
    }
}
</script>

<style lang="scss" scoped>

</style>