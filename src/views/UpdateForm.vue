<template>
  <div class="card">
    <div class="card-header">
      글 수정하기
    </div>
    <div class="card-body">
      <form v-if="board" v-on:submit.prevent="handleUpdate">
        <div class="form-group row">
          <label class="col-sm-2 col-form-label">제목</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="board.btitle" />
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-2 col-form-label">내용</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="board.bcontent" />
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-2 col-form-label">첨부그림</label>
          <div class="col-sm-10">
            <input type="file" class="form-control-file" ref="newBattach" @change="previewImg"/>
          </div>
        </div>

        <div class="row">
          <div v-for="(image) in board.bimages" :key="image.ino">
            <div class="col-3 mb-2">
              <Preview :bno="bno" :ino="`${image.ino}`"/>
            </div>
          </div>
        </div>

        <div class="form-group row mt-3">
          <div class="col-sm-12 d-flex justify-content-center">
            <input type="submit" class="btn btn-light mr-2" value="수정" />
            <input type="button" class="btn btn-light" value="취소" v-on:click="handleCancel" />
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import Preview from "./UpdateBoard/Preview.vue"
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import apiBoard from "@/apis/board";
const route = useRoute();
const router = useRouter();
const board = ref(null);
const bno = route.query.bno;
const pageNo = route.query.pageNo;
const newBattach = ref(null);
const battach = ref(null);
async function getBoard() {
  const dbBaord = await apiBoard.readBoard(bno);
  board.value = dbBaord;
  // const blob = await apiBoard.downloadBoardAttach(bno);
  // if(blob != null) {
  //   battach.value = URL.createObjectURL(blob);
  // }
} 
getBoard();
async function handleUpdate() {
  const multipartFormData = new FormData();
  multipartFormData.append("btitle", board.value.btitle);
  multipartFormData.append("bcontent", board.value.bcontent);
  multipartFormData.append("bno", board.value.bno);
  // if(newBattach.value.files.length != 0) {
  //   multipartFormData.append("battach", newBattach.value.files[0]);
  // }
  await apiBoard.updateBoard(multipartFormData);
  router.push(`/detail?bno=${bno}&pageNo=${pageNo}`);
}
function handleCancel() {
  router.push(`/detail?bno=${bno}&pageNo=${pageNo}`);
}
function previewImg() {
  if(newBattach.value.files.length != 0) {
    battach.value = URL.createObjectURL(newBattach.value.files[0]);
  } else {
    battach.value = null;
  }
}
</script>

<style scoped></style>