<template>
  <ModalDialogTemplateVue style="width:400px">
    <template v-slot:header>
      수정 혹은 삭제
    </template>

    <template v-slot:body>
      <p>카테고리를 수정하거나 삭제하세요</p>
      <input type="text" value="원래 카테고리 이름">
    </template>

    <template v-slot:footer>
      <button class="btn btn-info btn-sm" @click="$emit('confirm')">삭제</button>
      <button class="btn btn-info btn-sm"  @click="$emit('close')">수정</button>
    </template>
  </ModalDialogTemplateVue>
</template>

<script setup>
import ModalDialogTemplateVue from '@/components/popup/ModalDialogTemplate.vue';
</script>

<style scoped>
</style>
