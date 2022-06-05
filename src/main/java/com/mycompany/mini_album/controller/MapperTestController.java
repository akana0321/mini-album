package com.mycompany.mini_album.controller;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.mini_album.dto.Board;
import com.mycompany.mini_album.dto.Pager;
import com.mycompany.mini_album.service.BoardService;
import com.mycompany.mini_album.service.CategoryService;
import com.mycompany.mini_album.service.ImagesService;
import com.mycompany.mini_album.service.MemberService;

import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("/")
@Log4j2
public class MapperTestController {
  @Resource
  MemberService memberService;
  
  @Resource
  CategoryService categoryService;
  
  @Resource
  ImagesService imagesService;
  
  @Resource
  BoardService boardService;
  
  @RequestMapping("/home")
  @ResponseBody
  public String home() {
    /*Board board = new Board();
    board.setMid("user01");
    board.setCname("노을");
    board.setBtitle("테스트 제목4");
    board.setBcontent("테스트 내용4");
    int boardResult = boardService.insertBoard(board);
    log.info(boardResult);*/
    
    /*String saveFilename = new Date().getTime() + "-" + "image_0010_2550x1200";
    String originalFilename = "image_0010_2550x1200";
    String contentType = "image/png";
    Images image = new Images();
    image.setBno(27);
    image.setIoname(originalFilename);
    image.setIsname(saveFilename);
    image.setItype(contentType);
    List<Images> imgList = new ArrayList<>();
    imgList.add(image);
    File file = new File("C://Temp/uploadfiles/"+originalFilename);
    int imageResult = imagesService.insertImages(imgList);*/
    
    /*Category category = new Category();
    category.setCname("집으로");
    category.setMid("user01");*/
    
    List<Board> boardList = new ArrayList<>();
    int totalRows = boardService.getTotalBoardNum("user01");
    Pager pager = new Pager(5, 5, totalRows, 1);
    pager.setMid("user01");
    Board board = boardService.getBoard(26);
    for(int i=0; i<boardList.size(); i++) {
      log.info(boardList.get(i));
    }
    log.info(board);
    
    return "success?";
  }
}