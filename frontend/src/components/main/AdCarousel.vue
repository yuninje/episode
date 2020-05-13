<template>
    <v-carousel
        cycle
        height="440"
        hide-delimiter-background
        show-arrows-on-hover
    >
        <v-carousel-item
            v-for="(item, i) in items"
            :key="i"
            :src="item.src"
            
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
                },
                {
                    src: 'https://yomistore.com/wp-content/uploads/2019/08/main02.jpg',
                },
                {
                    src: 'https://i.pinimg.com/originals/91/a3/2d/91a32d22a0f1f2981fc166753d323a61.png',
                },
                {
                    src: 'https://gi.esmplus.com/jumondomio/U5package/U5P_main.jpg',
                },
            ],
        }
    }
}
</script>

<style lang="scss" scoped>

</style>