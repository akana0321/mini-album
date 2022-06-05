<template>
  <div v-if="image !== null">
    <img @click="goDetail(item['bno'])" :src="image" />
    <div class="card-footer text-center">
      {{ item["btitle"] }}
    </div>
  </div>  
  <div v-if="image === null">
    <img src="@/assets/logo.png" />
    <div class="card-footer text-center">
      {{ item["bno"] }}
    </div>
  </div>
</template>

<script setup>
import { ref, defineProps } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import apiBoardList from '@/apis/boardList';
import apiBoard from '@/apis/board';

const router = useRouter();

const item = defineProps({
  ino: String,
  bno: String,
  btitle: String,
  pageNo: String
});

const image = ref(null);

async function getImage(ino) {
  const blob = await apiBoardList.downloadBoardImages(ino);
  if(blob != null) {
    image.value = URL.createObjectURL(blob);
  }
}
getImage(item["ino"]);

async function goDetail(bno) {
  console.log("실행: ", bno);
  // router.push(`/board/${bno}`);
  router.push(`/detail?bno=${bno}&pageNo=${item['pageNo']}`);
}
</script>

<style scoped>
img {
  width: 100%;
  height: 20rem;
  cursor: pointer;
}
</style>