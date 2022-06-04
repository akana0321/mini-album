package com.mycompany.mini_album.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.mini_album.dto.Board;
import com.mycompany.mini_album.dto.Category;
import com.mycompany.mini_album.dto.Pager;
import com.mycompany.mini_album.service.BoardService;
import com.mycompany.mini_album.service.CategoryService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class BoardListController {
	
	@Resource
	private CategoryService categoryService;
	
	@Resource
	private BoardService boardService;

	@GetMapping("/readCategoryList")
	public List<Category> readCategoryList(String mid){
		log.info(mid);
		List<Category> categoryNames = categoryService.selectAll(mid);
		log.info(categoryNames);
		return categoryNames;
	}
	
	@PostMapping("/insertNewCategoryName")
	public int insertNewCategoryName(@RequestBody Category category) {
		int result = categoryService.insertCategory(category);
		
		return result;
	}
	
	
	@DeleteMapping("/deleteCategory") 
	public int deleteCategory(@RequestBody String cname) {
		System.out.println("딜리트 실행 : " + cname);
		
		int result = categoryService.deleteCategory(cname);
		return result;
	}
	
	@PutMapping("/updateCategory")
	public int updateCategory(@RequestBody Category category) {
		System.out.println("업데이트 실행 : " + category);
		int result = categoryService.updateCategory(category);
		return result;
		
	}
	
	//이미지 이름만 읽어온다면
	@GetMapping("/mainBoardImages1")
	public List<String> getImage(String mid) {
		//로그인 정보로 유저 아이디 읽어오는 거 어떻게 해야할 지 몰라서 일단 확인용으로 박음.
		mid = "user01";
	      
		//이미지 많으면 해당 파일 이름들을 리스트로 읽어와서 그걸 출력하면 될듯.......
		int totalBoardList = boardService.getTotalBoardNum(mid);
		Pager pager = new Pager(9, 4, totalBoardList, 1, mid);
		
		List<Board> list = boardService.getBoards(pager);
		List<String> imgList = new ArrayList<String>();
		for(int i = 0; i < list.size(); i++) {
			imgList.add(list.get(0).getBimages().get(0).getIoname());
		}
		
		return imgList;
	}
	
	
	//이미지 blob 파일 리스트로 읽어올거면
	@GetMapping("/mainBoardImages2")
	public List<ResponseEntity<byte[]>> getImageBlob(String mid) {
		//로그인 정보로 유저 아이디 읽어오는 거 어떻게 해야할 지 몰라서 일단 확인용으로 박음.
		mid = "user01";
	      
		//이미지 많으면 해당 파일 이름들을 리스트로 읽어와서 그걸 출력하면 될듯.......
		int totalBoardList = boardService.getTotalBoardNum(mid);
		Pager pager = new Pager(9, 4, totalBoardList, 1, mid);
		
		List<Board> list = boardService.getBoards(pager);
		System.out.println("list : " + list);
		System.out.println(list.get(0).getBimages().get(0).getIoname());
		System.out.println("list length : " + list.size());
		
		
		//이미지 리스트로 읽어오려고 하는 코드
		List<ResponseEntity<byte[]>> resultList = new  ArrayList<ResponseEntity<byte[]>>();
		for(int i = 0; i < list.size(); i++) {
			//메인 이미지 있을 경우 -> 메인 이미지 올림
			
			File file = new File("C:/Temp/userAlbum/" + list.get(i).getBimages().get(0).getIoname());
			System.out.println("names ... " + list.get(i).getBimages().get(0).getIoname());
			ResponseEntity<byte[]> result = null;
								
	      try {

	         HttpHeaders header = new HttpHeaders();
	         header.add("Content-type", Files.probeContentType(file.toPath()));
	         result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
	         

	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      resultList.add(result);
		}
		System.out.println("이걸로 이미지 띄우면 안되는 건가요? : " + resultList);
		return resultList;	
	}
		
	
	//이미지 출력
	/*
	@GetMapping("/showImage")
	   public ResponseEntity<byte[]> getImage(String fileName) {
	      
	    
	      File file = new File("C:/Temp/mypage/"+ fileName);
	      ResponseEntity<byte[]> result = null;

	      try {

	         HttpHeaders header = new HttpHeaders();
	         header.add("Content-type", Files.probeContentType(file.toPath()));
	         result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
	         

	      } catch (IOException e) {
	         e.printStackTrace();
	      }
	      return result;
	   }
	 
	 
	 * 
	 * 
	 */
}
