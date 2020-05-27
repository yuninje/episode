<template>
    <v-toolbar fixed>
        <v-spacer></v-spacer>
        <v-toolbar-title class="toolbar-title" @click="gotoMain()"><a><img class="logo" src="@/assets/images/logo1.png"></a></v-toolbar-title>
        <v-spacer></v-spacer>
        <!-- class="pt-6" 는 padding-top:6 줌-->
        <v-text-field
            v-model="searchText"
            append-icon="mdi mdi-magnify"
            @keyup.enter="gotoSearch()"
            @click:append="gotoSearch()"
            placeholder="작품명이나 작가명으로 검색"
            filled
            rounded
            dense
            class="pt-6"    
        ></v-text-field>
        <v-spacer></v-spacer>
        <template v-if="getDesktopSize()">
            <v-flex>
                <v-btn color="black" text>소설 전체보기</v-btn>
                <v-btn color="black" text @click="gotoMywork()">작품쓰기</v-btn>
            </v-flex>
            <v-spacer></v-spacer>
            <v-flex v-show="!getIsLogin">
                <v-btn color="black" text @click="gotoSignIn()">로그인</v-btn>
                <v-btn color="black" text @click="gotoSignUp()">회원가입</v-btn>
            </v-flex>
            <v-flex v-show="getIsLogin">
                <v-btn color="black" text @click="signout()">로그아웃</v-btn>
                <v-btn color="black" text @click="">마이페이지</v-btn>
            </v-flex>
        </template>
        <template v-else>
            <v-menu offset-y>
                <template v-slot:activator="{on}">
                    <v-btn 
                        icon
                        v-on="on"
                    >
                        <v-icon>mdi-dots-vertical</v-icon>
                    </v-btn>
                </template>
                <v-list>
                    <!-- v-for로 @click에 함수 연결이 어려워 직접 코딩 -->
                    <v-list-item @click=""><v-list-item-title>소설 전체 보기</v-list-item-title></v-list-item>
                    <v-list-item @click="gotoMywork()"><v-list-item-title>작품쓰기</v-list-item-title></v-list-item>
                    <v-list-item @click="gotoSignIn()" v-show="!getIsLogin"><v-list-item-title>로그인</v-list-item-title></v-list-item>
                    <v-list-item @click="gotoSignUp()" v-show="!getIsLogin"><v-list-item-title>회원가입</v-list-item-title></v-list-item>
                    <v-list-item @click="signout()" v-show="getIsLogin"><v-list-item-title>로그아웃</v-list-item-title></v-list-item>
                    <v-list-item @click="" v-show="getIsLogin"><v-list-item-title>마이페이지</v-list-item-title></v-list-item>
                    <!-- <v-list-item
                        v-for="(item, index) in items"
                        :key="index"
                        @click="item.link"
                    >
                        <v-list-item-title>{{item.title}}</v-list-item-title>
                    </v-list-item> -->
                </v-list>
            </v-menu>
        </template>
        <v-spacer></v-spacer>
    </v-toolbar>
</template>

<script>
import http from '../../http-common';
import { mapActions, mapMutations, mapGetters } from "vuex";


export default {
    name: 'NavigationBar',
    data() {
        return {
            searchText:'',
            loading: true,
            errored: false,
            desktopSize: false,
            items: [
                {
                    title:"소설 전체 보기",
                    link: ""
                },
                {
                    title:"작품쓰기",
                    link: "gotoMywork"
                },
                {
                    title:"로그인",
                    link: "gotoSignIn"
                },
                {
                    title:"회원가입",
                    link: "gotoSignUp"
                },
            ]
        }
    },
    computed: {
        ...mapGetters(["getIsLogin"]),
        ...mapGetters(["getSession"])
    },
    methods: {
        gotoSearch() {
            this.$router.push(`/search/${this.searchText}`)
        },
        gotoMain() {
            console.log(this.getSession.memPk)
            this.$router.push('/')
                        .catch(err => {});
        },
        gotoSignIn() {
            this.$router.push('/signin')
        },
        gotoSignUp() {
            this.$router.push('/signup')
                        .catch(err => {});
        },
        gotoMywork() {
            this.$router.push('/mywork')
                        .catch(err => {});
        },
        signout() {
            this.$store.dispatch("signout");
            this.$router.push("/signin")
        },
        getDesktopSize() {
            if(window.innerWidth > 600) {
                console.log("데스크탑 화면");
                this.desktopSize = true;
                return true;
            } else {
                console.log("모바일 화면");
                this.desktopSize = false;
                return false;
            }
        }
    }
}
</script>

<style lang="scss" scoped>
// .toolbar-title{
//     color: rgb(192,0,0);
//     font-size: 1.8rem;
//     font-weight: bold;
//     cursor: pointer;
// }

.logo {
    max-width: 150px;
    width: 15vw;
}
</style>