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
          <label class="col-sm-2 col-form-label">카테고리</label>
          <div class="col-sm-10">
            <select class="form-control" v-model="board.cname" >
              <option v-for="(item, index) in category" :key="index">{{item.cname}}</option>
            </select>
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
            <input type="file" class="form-control-file" ref="newBattach" @change="previewImg" multiple/>
          </div>
        </div>
      
        <div class="row">
          <div v-for="(item, index) in battach" :key="index">
            <div class="col-4 mb-2">
              <div class="d-flex align-items-end">
                <img :src="item" width="180" />
              </div>
            </div>
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

import apiBoardList from '@/apis/boardList';
import { useStore } from "vuex";

const store = useStore(); 
const router = useRouter();

const newBattach = ref(null);
const board = reactive({
  btitle: "",
  bcontent: "",
  cname: ""
});

const battach = ref([]);
const category = ref([]);

async function getCategory() {
  apiBoardList.readCategory()
  .then((data) =>{
    category.value = data;
  });
}
getCategory();

async function handleAdd() {
  
  const multipartFormData = new FormData();
  multipartFormData.append("btitle", board.btitle);
  multipartFormData.append("bcontent", board.bcontent);
  multipartFormData.append("mid", store.state.userId);
  multipartFormData.append("cname", board.cname);
  let dbBno = null;
  
  dbBno = await apiBoardList.createBoard(multipartFormData);

  if(newBattach.value.files.length != 0) {
    for (var i=0; i<newBattach.value.files.length; i++) {
      const multipartFormDataImage = new FormData();
      multipartFormDataImage.append("imgFile", newBattach.value.files[i]);
      await apiBoardList.createImage(dbBno, multipartFormDataImage);
    }
  }

  router.push("/listboard")
}

function handleCancel() {
  router.push("/listboard")
}

function previewImg(){
  if(newBattach.value.files.length != 0) {
    for(var i=0; i<newBattach.value.files.length; i++) {
      console.log(newBattach.value.files[i]);
      battach.value.push(URL.createObjectURL(newBattach.value.files[i]));
    }
  } else {
    battach.value = null;
  }
}

</script>

<style scoped>
textarea{
    resize: none;
}
</style>