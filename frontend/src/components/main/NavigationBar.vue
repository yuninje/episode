<template>
    <v-toolbar sticky>
        <v-spacer></v-spacer>
        <v-toolbar-title class="toolbar-title" @click="gotoMain()">EPISODE</v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- class="pt-6" 는 padding-top:6 줌-->
        <v-text-field
            v-model="searchText"
            append-icon="mdi mdi-magnify"
            @keyup.enter="search()"
            @click:append="search()"
            placeholder="작품명이나 작가명으로 검색"
            filled
            rounded
            dense
            class="pt-6"    
        ></v-text-field>
        <v-spacer></v-spacer>
        <v-btn color="black" text>소설 전체보기</v-btn>
        <v-btn color="black" text>에디터</v-btn>
        <v-spacer></v-spacer>
        <v-btn color="black" text @click="gotoSignIn()">로그인</v-btn>
        <v-btn color="black" text @click="gotoSignUp()">회원가입</v-btn>
        <v-spacer></v-spacer>
    </v-toolbar>
</template>

<script>
import http from '../../http-common';

export default {
    name: 'NavigationBar',
    data() {
        return {
            searchText:'',
            loading: true,
            errored: false
        }
    },
    methods: {
        search() {
            alert(this.searchText + " 검색");
        },
        gotoMain() {
            this.$router.push('/');
        },
        gotoSignIn() {
            this.$router.push('/signin');
        },
        gotoSignUp() {
            this.$router.push('/signup');
        }
    }
}
</script>

<style lang="scss" scoped>
.toolbar-title{
    color: rgb(192,0,0);
    font-size: 1.8rem;
    font-weight: bold;
}
</style>