<template>
  <div class="card">
    <div class="card-header">
        <div style="height : 130px; position:relative;">
            <div name="file_title" style="float:left;"><h2>고양이 사진</h2></div>
                <div style="position:relative;">
                    <img style="width:60px; height:60px; float:right;" :src="require(`C:/Temp/album/add_board.png`)" @click="handleAdd" />
                </div>
            <div name="file_list" style="float:left; position:absolute;">
                <div style="display:flex; margin-left : 100px;">
                    <div style="margin-top:50px; width : 100px;">
                        <img style="width:60px; height:60px; margin-left:20px;" :src="require(`C:/Temp/album/open_folder.png`)"/>
                        <p style="font-size:0.7rem; text-align:center;">고양이 사진 
                            <img style="width:16px; height:16px;" :src="require(`C:/Temp/album/setting.png`)"
                            @click="categorySetting=true"/></p>
                    </div>
                    <div style="margin-top:50px; width : 100px;">
                        <img style="width:60px; height:60px; margin-left:20px;" :src="require(`C:/Temp/album/close_folder.png`)"/>
                        <p style="font-size:0.7rem; text-align:center;">기념일
                            <img style="width:16px; height:16px;" :src="require(`C:/Temp/album/setting.png`)"
                            @click="categorySetting=true"/>
                            </p>
                    </div>
                    <div>
                        <img 
                        style="width:30px; height:30px; margin-top:60px; margin-left : 60px;" 
                        :src="require(`C:/Temp/album/add_category.png`)" @click="dialogA=true"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div v-if="page != null" class="card-body">
        <!-- 이미지 불러오기-->
        <div class="photos">
            <div v-for="board in page.boards" :key="board.bno" class="photo">
                <!-- <img :src="`board.bimages[0].ioname`" /> -->
                <!-- <p>{{ board.bimages[0].ioname }}</p> -->
                <imageFrame :ino=board.bimages[0].ino :bno=board.bno :btitle=board.btitle :pageNo=page.pager.pageNo></imageFrame>
            </div>
        </div>

        <!-- 페이지 이동 -->
        <div class="text-center" style="margin-top:30px;">
            <button @click="changePageNo(1)" class="btn btn-outline-primary btn-sm mr-1">처음</button>
            <button v-if="page.pager.groupNo > 1" @click="changePageNo(page.pager.startPageNo-1)" class="btn btn-outline-info btn-sm mr-1">이전</button>
            <button v-for="pageNo in range(page.pager.startPageNo, page.pager.endPageNo)" :key="pageNo" @click="changePageNo(pageNo)" class="btn btn-sm mr-1" :class="(page.pager.pageNo == pageNo) ? 'btn-danger' : 'btn-outline-success'">{{ pageNo }}</button>
            <button v-if="page.pager.groupNo < page.pager.totalGroupNo" @click="changePageNo(page.pager.endPageNo+1)" class="btn btn-outline-info btn-sm mr-1">다음</button>
            <button @click="changePageNo(page.pager.totalPageNo)" class="btn btn-outline-primary btn-sm mr-1">맨끝</button>
        </div>
        <!-- Modal -->
         <DialogA v-if="dialogA" v-on:confirm="handleConfirm" @close="dialogA = false" />
         <CategorySetting v-if="categorySetting" v-on:confirm="deleteConfirm" @close="updateCategory" />
         <chkDelete v-if="deleteCheck" v-on:confirm="deleteCategory" @close="deleteCheck = false;" />
    </div>
  </div>
</template>

<script setup>
import { ref, watch } from "vue";
import { useRouter, useRoute } from 'vue-router';
import { useStore } from 'vuex';
import apiBoardList from '@/apis/boardList';

import DialogA from './DialogA';
import CategorySetting from './CategorySetting';
import chkDelete from './chkDelete';
import imageFrame from "@/components/imageFrame.vue";

const router = useRouter();
const route = useRoute();
const store = useStore();

//반응형 선언
const dialogA = ref(false);
const categorySetting = ref(false);
const deleteCheck = ref(false);

const page = ref(null);

// url 형태: http://localhost:8090/listboard?category=XXX&pageNo=N&mid=YYY
// 라우팅 시에 전달된 QueryString에서 pageNo 읽기
let category = route.query.category;
let pageNo = route.query.pageNo;
let mid = store.state.userId;
if(category === "undefined") {
    category = 'basic';
}
if(pageNo === "undefined") {
    pageNo = 1;
}
if(mid === "undefined") {
    route.push("/login");
}

async function getBoardList(category, pageNo) {
    const result = await apiBoardList.getBoardList(category, pageNo, mid);
    if(result.result === "success") {
        page.value = result.data;
        console.log(page.value);
    } else {
        router.push("/login");
    }
}
console.log(mid);
getBoardList(category, pageNo, mid);

function range(start, end) {
    const numbers = [];
    for(var i=start; i<=end; i++) {
        numbers.push(i);
    }
    return numbers;
}

function changeCategory(category) {
    router.push(`/listboard/list?category=${ category }&pageNo=1`);
}

function changePageNo(pageNo) {
    router.push(`listboard/list?category=${ category }&pageNo=${ pageNo }`);
}

watch(route, (newUrl, oldUrl) => {
    if(newUrl.path === "/listboard/list") {
        if(newUrl.query.category && newUrl.query.pageNo) {
            getBoardList(newUrl.query.category, newUrl.query.pageNo);
        } else {
            getBoardList('basic', 1);
        }
    }
})

//메소드 선언
function handleConfirm(){
    dialogA.value = false;
}

function deleteConfirm(){
    deleteCheck.value = true;
    categorySetting.value=false;
}

function deleteCategory(){
    deleteCheck.value = false; 
    categorySetting.value = false;
}

function updateCategory(){
    categorySetting.value = false;
}

function handleAdd(){
    router.push("/WriteForm");
}
</script>

<style scoped>
.photos {
    width : 100%;
    display: flex;
    flex-wrap: wrap;
    flex-direction: row;
    justify-content: center;
    margin : auto;
}

.photo {
    width : 30%;
    height : 30%;
    border:1px solid #ccc;
    margin: 0.5rem 0.5rem;
}
</style>
