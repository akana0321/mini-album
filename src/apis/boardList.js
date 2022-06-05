import axios from "axios";
import apiAuth from "./auth";

// 게시물 목록 요청
async function getBoardList(category='basic', pageNo=1, mid) {
  let response = null;
  try {
    response = await axios.get("/listboard/list", {params: {category, pageNo, mid}});
    return {result: "success", data: response.data};
  } catch(error) {
    if(error.response) {
      // 서버 응답이 존재
      if(error.response.status == 403) {  // 자원에 접근할 수 없음, AccessToken 만료
        if(await apiAuth.refreshToken()) {
          response = await axios.get("/listboard/list", {params: {category, pageNo, mid}});
          return {result: "success", data: response.data};
        }
      }
    } else {
      // 서버 응답이 없음(네트워크 오류)
      return {result: "fail-network"};
    }
  }
}

// 파일 다운로드
async function downloadBoardImages(ino) {
  let blob = null;
  try {
    const response = await axios.get(`/listboard/list/image/${ ino }`, {responseType: 'blob'});
    blob = response.data;
  } catch(error) {
    if(error.response) {
      if(error.response.status === 403) {
        if(await apiAuth.refreshToken()) {
          const response = await axios.get(`/listboard/list/image/${ ino }`, {responseType: 'blob'});
          blob = response.data;
        }
      }
    }
  }
  return blob;
}

//게시물 추가
async function createBoard(multipartFormData) {
  let dbBoard = null;

  try {
    const response = await axios.post(`/listboard/write`, multipartFormData);
    dbBoard = response.data;
    console.log(response);

  } catch(error) {
    if(error.response) {
      if(error.response.status === 403) {
        if(await apiAuth.refreshToken()) {
          const response = await axios.post(`/listboard/write`, multipartFormData);
          dbBoard = response.data;
        }
      }
    }
  }
  console.log(dbBoard);
  return dbBoard;
}

//파일 추가
async function createImage(dbBno, multipartFormDataImage) {
  let dbImages = null;
  try {
    const response = await axios.post(`/listboard/write/${dbBno}`, multipartFormDataImage);
    dbImages = response.data;

  } catch(error) {
    if(error.response) {
      if(error.response.status === 403) {
        if(await apiAuth.refreshToken()) {
          const response = await axios.post(`/listboard/write/${dbBno}`, multipartFormDataImage);
          dbImages = response.data;
        }
      }
    }
  }
  return dbImages;
}

//카테고리 가져오기
async function readCategory() {
  let category = null;
  try { 
    const response = await axios.get(`/listboard/write`);
    category = response.data;
  } catch (error) {
    if (error.response) {
      if(error.response.status === 403) {
        if (await apiAuth.refreshToken()) {
          const response = await axios.get(`/listboard/write`);
          category = response.data;
        }
      }
    }
  }
  return category;
}

export default {
  getBoardList,
  downloadBoardImages,
  createBoard, //게시물 추가
  createImage, //파일 추가
  readCategory, //카테고리 가져오기
}