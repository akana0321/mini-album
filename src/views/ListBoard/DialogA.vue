<template>
  <ModalDialogTemplateVue style="width:400px">
    <template v-slot:header>
      알림
    </template>

    <template v-slot:body>
      <p>추가하고 싶은 카테고리 이름을 입력하세요</p>
      <input type="text" placeholder="카테고리 이름(예: 똥)">
    </template>

    <template v-slot:footer>
      <button class="btn btn-info btn-sm" @click="$emit('confirm')">확인</button>
      <button class="btn btn-info btn-sm"  @click="$emit('cancel')">닫기</button>
    </template>
  </ModalDialogTemplateVue>
</template>

<script setup>
import ModalDialogTemplateVue from '@/components/popup/ModalDialogTemplate.vue';
</script>

<style scoped>
</style>
