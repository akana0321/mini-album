<template>
  <div class="card">
    <router-link :to="`/listboard?pageNo=${pageNo}`" class="btn btn-light">목록</router-link>
    <div class="card-header">
      <h2>{{board.cname}}</h2>
    </div>
    <div class="card-body">
      <div class="content">
        <div class="d-flex justify-content-between align-items-center">
          <h4 class="d-inline" style="margin:0;">{{board.btitle}}</h4>
          <div class="button-wrapper">
            <router-link :to="`/detail/updateform?bno=${bno}&pageNo=${pageNo}`" 
            class="btn btn-light">수정</router-link>
            <button @click="handleRemove" class="btn btn-light">삭제</button>
          </div>
        </div>
        <p>{{new Date(board.bdate).toLocaleDateString()}}</p>
        <p>{{board.bcontent}}</p>
      </div>

      <div class="imgs d-flex flex-column">

        <div v-for="(image) in board.bimages" :key="image.ino" class="img-wrapper mb-2 d-flex align-items-end align-self-center">
          
          <Image :bno="bno" :ino="`${image.ino}`"/>

          <i @click="removeBattach(`${image.ino}`)" class="deleteImg bi bi-x-lg" style="font-size:20px"></i>
          <i @click="downloadBattach(`${image.ino}`, `${image.ioname}`)" class="download bi bi-download fs-5" style="font-size:20px;"></i>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import Image from "./Image.vue"
import { useRoute, useRouter } from "vue-router";
import apiBoard from "@/apis/board";
import { ref } from "vue";

import fileDownload from "js-file-download";

const route = useRoute();
const router = useRouter();
const bno = route.query.bno;
const pageNo = route.query.pageNo;
const ino = route.query.ino;
const board = ref(null);
const battach = ref(null);

//게시물 가져오기
async function getBoard() {
  apiBoard.readBoard(bno) 
  .then((data) => {
    board.value = data;
  });
}
getBoard();

//파일 다운로드
async function downloadBattach(ino, ioname) {
  const blob = await apiBoard.downloadBoardAttach(bno, ino);
  if(blob != null) {
    fileDownload(blob, ioname);
  }
}
//파일 삭제
async function removeBattach(ino) {
  const result = await apiBoard.deleteBoardAttach(bno, ino);
  if (result === "success") {
    router.push(`/detail?bno=${bno}&pageNo=${pageNo}`)
    getBoard();
  }
}
//게시물 삭제
async function handleRemove() {
  const result = await apiBoard.deleteBoard(bno);
  if (result === "success") {
    router.push(`/listboard?pageNo=${pageNo}`)
  }
}
</script>

<style scoped>
</style>