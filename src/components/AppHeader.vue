<template>
  <nav class="navbar shadow navbar-dark text-white font-weight-bold" v-if="$store.state.userId !== ''" >
      <router-link to="/listboard" class="navbar-brand">
        <img src="../assets/logo.png" alt="" width="80" height="60" class="align-top mr-2" />
      </router-link>
    <div>
      <!-- <router-link v-if="$store.state.userId === ''" to="/loginform" class="mr-3"  id="login">로그인</router-link> -->
      <span v-if="$store.state.userId !== ''"  @click="handleLogout" class="mr-3" id="logout">로그아웃</span>
    </div>
  </nav>
</template>

<script setup>
import apiAuth from '@/apis/auth';
import { useRouter } from 'vue-router';

const router = useRouter();

async function handleLogout() {
  await apiAuth.logout();
  router.push("/login");
}
</script>

<style scoped>
#login{
  color: black;
  font-weight: 700;
  text-decoration: none;
}

#logout{
  color: black;
  font-weight: 700;
  text-decoration: none;
}

#logout:hover{
  cursor: pointer;
}

nav{
	background-color: rgb(255, 245, 245);
}
</style>