package com.mycompany.mini_album.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mycompany.mini_album.dto.Board;
import com.mycompany.mini_album.service.BoardService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/board")
public class BoardDetailController {
  @Resource
  private BoardService boardService;

  @GetMapping("/{bno}")
  public Board detail(@PathVariable int bno) {
    log.info("실행");
    log.info(bno);
    Board dbBoard = boardService.getBoard(bno);
    log.info(dbBoard);
    return dbBoard;
  }
  
  @DeleteMapping("/{bno}")
  public Map<String, String> delete(@PathVariable int bno) {
    boardService.deleteBoard(bno);
    Map<String, String> map = new HashMap<>();
    map.put("result", "success");
    return map;
  }
}