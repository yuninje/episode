<template>
    <v-container>
        <v-row>
            <v-col cols="12">
                <p class="no-result">Top 100</p>
            </v-col>
            <v-col cols="12" class="d-flex justify-center">
                <v-card
                    flat
                    max-width="1440"
                >
                <v-row>
            <v-col 
                cols="12" 
                v-for="(novel, index) in novels.slice(page*10 - 10,page*10)"
                :key="index"
                class="novel-content"
            >
                    <v-row>
                        <v-col cols="1" sm="1" class="d-flex justify-center align-center">
                            <p class="sub-title">{{novel.index}}</p>
                        </v-col>
                        <v-col cols="5" sm="3">
                            <v-img
                                :src="novel.novelImage"
                                aspect-ratio=0.7
                                @click="gotoNovelDetail(novel.novelPk)"
                                class="clickable"
                            >
                            </v-img>
                        </v-col>
                        <v-col cols="6" sm="8">
                            <p class="sub-title">{{novel.novelName}}</p>
                            <p class="hidden-xs-only">{{novel.novelIntro}}</p>
                        </v-col>
                    </v-row>
            </v-col>

                </v-row>
                </v-card>
            </v-col>
            <!-- <v-col cols="12">
                <v-data-table
                    :headers="headers"
                    :items="novels"
                    class="elevation-1"
                >
                    <template slot="items" slot-scope="props">
                        <td>{{props.item.index}}</td>
                        <td>
                            <v-img
                                :src="props.item.novelImage"
                                aspect-ratio=0.7
                            >
                            </v-img>
                        </td>
                        <td>{{props.item.novelName}}</td>
                    </template>
                </v-data-table>
            </v-col> -->
            <v-col cols="12">
                <v-pagination
                    v-model="page"
                    :length="pageLength"
                    color="rgba(255, 83, 83, 1)"
                ></v-pagination>
            </v-col>
        </v-row>
    </v-container>
</template>

<script>
import http from '../../http-common'

export default {
    data() {
        return {
            headers: [
                {
                    text: 'Rank',
                    align: 'start',
                    sortable: false,
                    value: 'index'
                },
                {
                    text: 'Image',
                    sortable: false,
                    value: 'novelImage'
                },
                {
                    text: 'Title',
                    sortable: false,
                    value: 'novelName'
                },
            ],
            page:1,
            pageLength: 0,
            startNum: 0,
            endNum: 0,
            novels: [],
            perNovels: null,
            loading: true,
            errored: false
        }
    },
    created() {
        this.getTop100();
    },
    mounted() {
        // this.getPerNovels();
    },
    computed: {

    },
    methods: {
        getTop100() {
            http
                .get('/novels/top100')
                .then(response => {
                    console.log(response.data.data)
                    this.novels = response.data.data;
                    if(this.novels.length % 10 === 0) {
                        this.pageLength = parseInt(this.novels.length / 10);
                    } else {
                        this.pageLength = parseInt(this.novels.length / 10) + 1;
                    }
                    for(var i = 0; i<this.novels.length; i++) {
                        this.novels[i].index = i+1;
                    }
                    console.log(this.novels);
                })
                .catch(() => {
                    this.errored = true;
                })
                .finally(() => {
                    this.loading = false;
                })
        },
        getPerNovels() {
            this.perNovels = new Array(this.pageLength);
            for(var i = 0; i<this.novels.length; i++) {
                this.perNovels[parseInt(i/10)].push(this.novels[i]);
            }
            for(var i = 0; i<3; i++) {
                console.log(perNovels[i]);
            }
        },
        gotoNovelDetail(novelPk) {
            this.$router.push(`/noveldetail/${novelPk}`);
        }
    },
}
</script>

<style lang="scss" scoped>
.no-result {
    color: rgba(255,83,83,1);
    font-size: 3.5rem;
    font-weight: bold;
    text-align: center;
}
.sub-title {
    font-size : 1.5rem;
    font-weight: bold;
}
.novel-content {
    border-bottom: 1px solid;
    border-bottom-color: rgba(230,230,230,1);
    border-top: 1px solid;
    border-top-color:rgba(230,230,230,1);
}
.novel-content:first-child {
    border-top: 2px solid;
    border-top-color:rgba(230,230,230,1);
}
.novel-content:last-child {
    border-bottom: 2px solid;
    border-bottom-color: rgba(230,230,230,1);
}
.novel-content:nth-child(even) {
    /* background-color: rgba(240,240,240,1); */
}
.novel-content:nth-child(odd) {
    /* background-color: rgba(200,200,200,1); */
}
.clickable {
    cursor: pointer;
}
</style>