<template>
    <v-toolbar fixed>
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
        <template v-if="true">
            <v-flex>
            <v-btn color="black" text>소설 전체보기</v-btn>
            <v-btn color="black" text>에디터</v-btn>
            </v-flex>
            <v-spacer></v-spacer>
            <v-flex>
            <v-btn color="black" text @click="gotoSignIn()">로그인</v-btn>
            <v-btn color="black" text @click="gotoSignUp()">회원가입</v-btn>
            </v-flex>
        </template>
        <template v-else>
            
        </template>
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
            errored: false,
            desktopSize: false
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
        },
        getDesktopSize() {
            if(window.innerWidth > 600) {
                console.log("함수 트루");
                this.desktopSize = true;
                return true;
            } else {
                console.log("함수 폴스");
                this.desktopSize = false;
                return false;
            }
        }
    }
}
</script>

<style lang="scss" scoped>
.toolbar-title{
    color: rgb(192,0,0);
    font-size: 1.8rem;
    font-weight: bold;
    cursor: pointer;
}

</style>