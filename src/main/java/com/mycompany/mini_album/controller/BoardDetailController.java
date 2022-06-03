package com.mycompany.mini_album.controller;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
  
  /*@GetMapping("/battach/{bno}")
  public ResponseEntity<InputStreamResource> download(@PathVariable int bno) throws Exception {
    Board board = boardService.getBoard(bno);
    String battachoname = board.getBattachoname();
    if (battachoname == null) return null;
    
    //파일 이름이 한글일 경우에 설정
    battachoname = new String(battachoname.getBytes("UTF-8"),"ISO-8859-1");
    
    //파일 입력 스트림 생성
    FileInputStream fis = new FileInputStream("C:/Temp/uploadfiles/" + board.getBattachsname());
    InputStreamResource resource = new InputStreamResource(fis);
    
    //응답 생성
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + battachoname + "\";")
        .body(resource);
  } */
}
