<template>
  <div id="app" class="d-flex flex-column vh-100">
    <AppHeader />
    <div class="flex-grow-1 container-fluid">
      <div class="d-flex justify-content-center h-100">
        <div class="col-md-8 col-lg-8 p-3">
          <div class="h-100 d-flex flex-column">
            <div class="flex-grow-1  pr-3" style="height: 0px">
              <router-view />
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useStore } from "vuex";

import AppHeader from "./components/AppHeader.vue";

const store = useStore();

store.dispatch("loadAuth");
</script>
