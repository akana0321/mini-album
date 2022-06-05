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
            <input type="file" class="form-control-file" ref="newBattach" @change="previewImg" multiple/>
          </div>
        </div>

        <div class="row">
          <div v-for="(image) in board.bimages" :key="image.ino">
            <div class="col-4 mb-2">
              <div class="d-flex align-items-end">
                <Preview :bno="bno" :ino="`${image.ino}`"/>
              </div>
            </div>
          </div>

          <div v-for="(item, index) in battach" :key="index">
            <div class="col-4 mb-2">
              <div class="d-flex align-items-end">
                <img :src="item" width="180" />
              </div>
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
import Preview from "./Preview.vue"
import { ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import apiBoard from "@/apis/board";

const route = useRoute();
const router = useRouter();

const board = ref(null);
const bno = route.query.bno;
const pageNo = route.query.pageNo;
const newBattach = ref(null);
const battach = ref([]);
const images = [];

async function getBoard() {
  const dbBaord = await apiBoard.readBoard(bno);
  board.value = dbBaord;
} 
getBoard();

async function handleUpdate() {
  const multipartFormData = new FormData();
  multipartFormData.append("btitle", board.value.btitle);
  multipartFormData.append("bcontent", board.value.bcontent);
  multipartFormData.append("cname", board.value.cname);
  multipartFormData.append("bno", board.value.bno);
  
  if(newBattach.value.files.length != 0) {
    for(var i=0; i<newBattach.value.files.length; i++) {
      const multipartFormDataImage = new FormData();
      multipartFormDataImage.append("imgFile", newBattach.value.files[i]);
      await apiBoard.updateImage(bno, multipartFormDataImage);
    }
  }

  await apiBoard.updateBoard(bno, multipartFormData);
  router.push(`/detail?bno=${bno}&pageNo=${pageNo}`);
}

function handleCancel() {
  router.push(`/detail?bno=${bno}&pageNo=${pageNo}`);
}

function previewImg() {
  if(newBattach.value.files.length != 0) {
    for(var i=0; i<newBattach.value.files.length; i++) {
      battach.value.push(URL.createObjectURL(newBattach.value.files[i]));
    }
  } else {
    battach.value = null;
  }
}

async function removeBattach(ino) {
  const result = await apiBoard.deleteBoardAttach(bno, ino);
  if (result === "success") {
    router.push(`/detail/updateform?bno=${bno}&pageNo=${pageNo}`)
    getBoard();
  }
}
</script>

<style scoped></style>