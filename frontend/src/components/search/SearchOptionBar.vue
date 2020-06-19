<template>
    <v-tabs
        v-model="tab"
        background-color="white"
        color="rgba(255,83,83,1)"
        class="tabs"
        show-arrows
    >
        <v-tabs-slider color="transparent"></v-tabs-slider>
        <!-- v-tab을 페이지 이동을 위한 방법으로 사용 가능 -->
        <v-tab
            v-for="searchOption in searchOptions"
            :key="searchOption.searchPk"
            class="tab"
            @click="gotoSearch(searchOption.searchPk)"
        >
            {{searchOption.searchName}}
        </v-tab>
    </v-tabs>
</template>

<script>
import http from '../../http-common'

export default {
    name: 'SearchOptionBar',
    props: {
        
    },
    data() {
        return {
            searchOptions: [
                {searchPk:0, searchName: "전체"},
                {searchPk:1, searchName: "작가명"},
                {searchPk:2, searchName: "작품명"},
                {searchPk:3, searchName: "해시태그"},
            ],
            tab: null,
            searchKeyword: '',
            errored: false,
            loading: true
        }
    },
    created() {

    },
    mounted() {
        // this.tab = Number(this.selectedTab);
        this.tab = Number(this.$route.params.searchPk);
        this.searchKeyword = this.$route.params.searchKeyword;
    },
    methods: {
        gotoSearch(searchPk) {
            this.$router.push(`/search/${this.searchKeyword}/${searchPk}/1`);
        }
    },
}
</script>

<style lang="scss" scoped>
.tabs {
    border-bottom: 2px solid;
    border-bottom-color: rgba(255,83,83,1);
}
.tab:first-child {
    border-left-width: 2px;
}
.tab:last-child {
    border-right-width: 2px;
}
.tab {
    border-top: 2px solid;
    border-left: 2px solid;
    border-right: 1px solid;
    border-color:rgba(255,83,83,1);
    color: rgba(255,83,83,1) !important;
    font-weight: bold;
    margin-right: -1px;
}
.v-tab--active {
    background-color: rgba(255,83,83,1) !important;
    color: white !important;
}
</style>