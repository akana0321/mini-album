<template>
  <img :src="battach" width="300"/>
</template>

<script setup>
import { defineProps } from "vue";
import apiBoard from "@/apis/board";
import { ref } from "vue";
const props = defineProps(["bno", "ino"]); 
const battach = ref(null);
//파일 가져오기
async function getImage() {
  const blob = await apiBoard.downloadBoardAttach(props.bno, props.ino);
  if (blob != null) {
    battach.value = URL.createObjectURL(blob);
  }
}
getImage();
</script>