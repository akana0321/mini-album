import axios from "axios";
import apiAuth from "@/apis/auth";

//게시물 가져오기
async function readBoard(bno) {
  let board = null;
  try { 
    const response = await axios.get(`/board/${bno}`);
    board = response.data;
  } catch (error) {
    if (error.response) {
      if(error.response.status === 403) {
        console.log("네트워크 에러");
        if (await apiAuth.refreshToken()) {
          const response = await axios.get(`/board/${bno}`);
          board = response.data;
        }
      }
    }
  }
  return board;
}

//파일 다운로드
async function downloadBoardAttach(bno, ino) {
  let blob = null;
  try {
    const response = await axios.get(`/board/battach/${bno}/${ino}`, {responseType: "blob"});
    blob = response.data;
  } catch(error) {
    if(error.response) {
      if(error.response.status === 403) {
        if(await apiAuth.refreshToken()) {
          const response = await axios.get(`/board/battach/${bno}/${ino}`, {responseType: "blob"});
          blob = response.data;
        }
      }
    }
  }
  return blob;
}


//파일 삭제
async function deleteBoardAttach(bno, ino) {
  let result = null;
  try {
    const response = await axios.delete(`/board/battach/${bno}/${ino}`);
    result = response.data.result;
  } catch (error) {
    if (error.response) {
      if(error.reponse.status === 403) {
        if (await apiAuth.refreshToken()) {
          const response = await axios.delete(`/board/battach/${bno}/${ino}`);
          result = response.data.result;
        }
      }
    }
  }
  return result;
}

//게시물 삭제
async function deleteBoard(bno) {
  let result = null;
  try {
    const response = await axios.delete(`/board/${bno}`);
    result = response.data.result;
  } catch(error) {
    if (error.response) {
      if (error.response.status === 403) {
        if (await apiAuth.refreshToken()) {
          const response = await axios.delete(`/board/${bno}`);
          result = response.data.result;
        }
      }
    }
  }
  return result;
}

//게시물 수정
async function updateBoard(bno, multipartFormData) {
  let dbBoard = null;

  try {
    const response = await axios.put(`/board/${bno}`, multipartFormData);

  } catch(error) {
    if(error.response) {
      if(error.response.status === 403) {
        if(await apiAuth.refreshToken()) {
          const response = await axios.put(`/board`, multipartFormData);
          dbBoard = response.data;
        }
      }
    }
  }
  return dbBoard;
}

//파일 수정
async function updateImage(bno, multipartFormDataImage) {
  let dbImages = null;
  try {
    const response = await axios.put(`/board/battach/${bno}`, multipartFormDataImage);
    dbImages = response.data;

  } catch(error) {
    if(error.response) {
      if(error.response.status === 403) {
        if(await apiAuth.refreshToken()) {
          const response = await axios.put(`/board/battach/${bno}`, multipartFormDataImage);
          dbImages = response.data;
        }
      }
    }
  }
  return dbImages;
}

export default {
  readBoard, //게시물 가져오기
  deleteBoard, //게시물 삭제
  downloadBoardAttach, //파일 다운로드
  deleteBoardAttach, //파일 삭제
  updateBoard, //게시물 수정
  updateImage, //파일 수정

}