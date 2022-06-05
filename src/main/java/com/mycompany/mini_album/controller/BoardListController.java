package com.mycompany.mini_album.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.mini_album.dto.Board;
import com.mycompany.mini_album.dto.Category;
import com.mycompany.mini_album.dto.Images;
import com.mycompany.mini_album.dto.Pager;
import com.mycompany.mini_album.service.BoardService;
import com.mycompany.mini_album.service.CategoryService;
import com.mycompany.mini_album.service.ImagesService;

import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
@RequestMapping("/listboard")
public class BoardListController {
  
  @Resource
  private CategoryService categoryService;
  
  @Resource
  private BoardService boardService;
  
  @Resource
  private ImagesService imagesService;
    
  @GetMapping("/list")
  public Map<String, Object> list(@RequestParam(defaultValue="basic") String category,
                                  @RequestParam(defaultValue="1") int pageNo,
                                  @RequestParam String mid) {
    log.info("실행");
    int totalRows = boardService.getTotalBoardNum(mid);
    Pager pager = new Pager(9, 4, totalRows, pageNo);
    pager.setMid(mid);
    List<Board> list =  boardService.getBoards(pager);
    Map<String, Object> map = new HashMap<>();
    map.put("boards", list);
    map.put("pager", pager);  // Front에서도 페이징 처리를 하기 위해 제공
    return map;
  }
  
  @GetMapping("/list/image/{ino}")
  public ResponseEntity<InputStreamResource> download(@PathVariable int ino) throws Exception {
    Images image = imagesService.getImageOne(ino);
    String isname = image.getIsname();
    
    if(isname == null) return null;
    
    // 파일 이름이 한글일 경우
    isname = new String(isname.getBytes("UTF-8"),"ISO-8859-1");
    
    // 파일 입력 스트림
    FileInputStream fis = new FileInputStream("C:/Temp/album/" + isname);
    InputStreamResource resource = new InputStreamResource(fis);
    
    // 응답 생성
    return ResponseEntity.ok()
          .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""+isname+"\";")
          .header(HttpHeaders.CONTENT_TYPE, image.getItype())
          .body(resource);
  }
  
//  @PostMapping("/write")
//  public Board create(Board board) {
//    log.info("실행");
//    List<Images> imgList = new ArrayList<>();
//    if(board.getBimages() != null && !board.getBimages().isEmpty()) {
//      for(int i=0; i<board.getBimages().size(); i++) {
//        Images image = board.getBimages().get(i);
//        MultipartFile mf = image.getImgFile();
//        image.setIoname(mf.getOriginalFilename());
//        image.setIsname(new Date().getTime()+"-" +mf.getOriginalFilename());
//        image.setItype(mf.getContentType());
//        File file = new File("c:/Temp/uploadfiles/"+image.getIsname());
//        try {
//          mf.transferTo(file);
//        } catch (Exception e) {
//          log.error(e.getMessage());
//        }
//        imgList.add(image);
//      }
//    }
//    boardService.insertBoard(board);
//    imagesService.insertImages(imgList);
//    Board dbBoard = boardService.getBoard(board.getBno());
//    return dbBoard;
//  }

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
    log.info("create Category 실행");
    return result;
  }
  
  
  @DeleteMapping("/deleteCategory/{cname}") 
  public int deleteCategory(@PathVariable String cname) {
    System.out.println("딜리트 실행 : " + cname);
    
    int result = categoryService.deleteCategory(cname);
    return result;
  }
  
  @PutMapping("/updateCategory")
  public int updateCategory(@RequestBody String cname, @RequestBody String oldCname) {
    cname = "1주년 기념일";
    oldCname = "기념일";
    int result = categoryService.updateCategory(cname, oldCname);
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
  
  @GetMapping("/write")
  public List<Category> getCategory() {
    log.info("실행");
    List<Category> dbCategory = categoryService.selectAll("user01");
    log.info(dbCategory);
    return dbCategory;
  }
  
  @PostMapping("/write")
  public int create(Board board) {
    log.info("실행");
    log.info(board);
    
    int bno = boardService.insertBoard(board);
    return bno;
  }
  
  @PostMapping("/write/{bno}")
  public void updateImage(Images image) {
    log.info("실행");
    log.info(image);

    if(image.getImgFile() != null && !image.getImgFile().isEmpty()) {
      MultipartFile mf = image.getImgFile();
      image.setIoname(mf.getOriginalFilename());
      image.setIsname(new Date().getTime() + "-" + mf.getOriginalFilename());
      image.setItype(mf.getContentType());
  
        try {
          File file = new File("C:/Temp/album/" + image.getIsname());
          mf.transferTo(file);
        } catch (Exception e) {
          log.error(e.getMessage());
        }
        
        boardService.writeImage(image);
    }
  }
  
}
