package com.mycompany.mini_album.controller;

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
public class MemberController {
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
    int totalRows = boardService.getTotalBoardNum();
    Pager pager = new Pager(5, 5, totalRows, 1);
    pager.setMid("userid01");
    List<Board> boards = boardService.getBoards(pager);
    System.out.println(boards.size());
    for(int i=0; i<boards.size(); i++) {
      log.info(boards.get(i));
    }
    return "real??";
  }
}
