<template>
  <ModalDialogTemplateVue style="width:400px">
    <template v-slot:header>
      <div style="font-color:red">확인</div>
    </template>

    <template v-slot:body>
      <p>정말로 삭제하시겠습니까?</p>
    </template>

    <template v-slot:footer>
      <button class="btn btn-info btn-sm" @click="$emit('confirm')">네, 삭제합니다.</button>
      <button class="btn btn-info btn-sm"  @click="$emit('close')">아니요. 삭제하지 않습니다.</button>
    </template>
  </ModalDialogTemplateVue>
 
</template>

<script setup>
import ModalDialogTemplateVue from '@/components/popup/ModalDialogTemplate.vue';

</script>

<style scoped></style>