<template>
<section class="main-content">
		<div class="container">
			<div class="login-card rounded-lg overflow-hidden bg-white mx-auto">
				<div class="login-head text-center pt-4">
					<img src="../assets/logo.png" alt="" width="250" height="200"/>
				</div>
				<div class="text-center">
					<span v-if="alert" id="alert">아이디 또는 패스워드가 틀렸습니다.</span>
					<span v-if="networkErr" id="alert">네트워크 에러</span>
				</div>
				<div class="login-body mt-5">
					<div class="login-body-wrapper mx-auto">
						<div class="form-group">
							<input type="text" class="form-control form-control-lg"  id="userId" v-model="user.id" placeholder="아이디">
						</div>
						<div class="form-group">
							<input type="password" class="form-control form-control-lg"  id="password" aria-describedby="helpId"  v-model="user.password" placeholder="비밀번호">
						</div>
						<button class="btn btn-primary btn-block btn-lg mt-5 mb-5" v-on:click="handleLogin" :disabled="!regEnabled">
                            <span v-if="!loading">로그인</span>
                            <span v-if="loading" class="spinner-border" role="status"></span>
                        </button>
					</div>
                    
				</div>
			</div>
			
		</div>
	</section>
</template>

<script setup>
import {reactive, ref, computed} from "vue";
import {useStore} from "vuex";
import apiAuth from "@/apis/auth";
import {useRouter} from "vue-router"

const alertDialog = ref(false);
const alertDialogMessage = ref("");
const loading = ref(false);
const alert = ref(false);
const router = useRouter();
const networkErr = ref(false);

const user = reactive({
  id: "",
  password: ""
})

async function handleLogin(){

  loading.value = true;
 setTimeout(async ()=>{
  const result = await apiAuth.login(user);
  if(result === "success"){
    alert.value = false;
    router.push("/listboard");
  }else if(result === "fail-401"){
    alert.value = true;
    networkErr.value = false;
  }else{
    alert.value = false;
    networkErr.value = true;
  }
    loading.value = false;
  },1000) 

}

//아이디와 비밀번호가 공백이면 로그인 버튼 비활성화
const regEnabled = computed(() => {
  var result = user.id !== "" && user.password !== "" ? true : false;
  return result;
});

</script>

<style scoped>
@import url("https://fonts.googleapis.com/css?family=Roboto:300,400,500,700&display=swap");
body {
	background-color: #f3ebeb;
	font-family: "Roboto", sans-serif;
}


.main-content {
	padding-top: 100px;
	padding-bottom: 100px;
}

.shadow {
	box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1) !important;
}

.login-card {
	max-width: 500px;
	box-shadow: 0 2px 30px rgba(0, 0, 0, 0.1);
	border:solid 0.4px;
}
.login-card .login-body .login-body-wrapper {
	max-width: 400px;
}

.or-divider {
	height: 40px;
	width: 40px;
}
.spinner-border{
    width: 20px;
    height: 20px;
}

#alert{
    color:red;
    font-size: 0.5em;
}

input{
	font-size: 1em;
}


</style>