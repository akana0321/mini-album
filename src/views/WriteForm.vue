<template>
  <div class="card">
    <div class="card-header">
      글쓰기
    </div>
    <div class="card-body">
      <form v-on:submit.prevent="handleAdd">
        <div class="form-group row">
          <label class="col-sm-2 col-form-label">제목</label>
          <div class="col-sm-10">
            <input type="text" class="form-control" v-model="board.btitle" />
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-2 col-form-label">내용</label>
          <div class="col-sm-10">
            <textarea class="form-control" v-model="board.bcontent"></textarea>
          </div>
        </div>
        <div class="form-group row">
          <label class="col-sm-2 col-form-label">첨부 이미지</label>
          <div class="col-sm-10">
            <input type="file" class="form-control-file" ref="battach" multiple/>
          </div>
          <div>
            
          </div>
        </div>
        <div class="form-group row">
          <div class="col-sm-12 d-flex justify-content-center">
            <input type="submit" class="btn btn-primary btn-sm mr-2" value="추가" />
            <input type="button" class="btn btn-primary btn-sm" value="취소" v-on:click="handleCancel" />
          </div>
        </div>
      </form>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref } from "vue"
import { useRouter } from "vue-router";
import apiBoard from "@/apis/board";
const router = useRouter();
const newBattach = ref(null);
const board = reactive({
  btitle: "",
  bcontent: ""
});
const battach = ref(null);
async function handleAdd() {
  const multipartFormData = new FormData();
  multipartFormData.append("btitle", board.btitle);
  multipartFormData.append("bcontent", board.bcontent);
  multipartFormData.append("mid", "user");
  if(battach.value.files.length != 0) {
    multipartFormData.append("battach", battach.value.files[0]);
  }
  await apiBoard.createBoard(multipartFormData);
  router.push("/listboard")
}
function handleCancel() {
  router.push("/listboard")
}
function previewImg(){
    if(newBattach.value.files.length != 0){
        battach.value= URL.createObjectURL(newBattach.value.files[0]);
    }else{
        battach.value = null;
    }
}
</script>

<style scoped>
textarea{
    resize: none;
}
</style>