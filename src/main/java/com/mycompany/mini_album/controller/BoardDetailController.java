package com.mycompany.mini_album.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.mycompany.mini_album.dto.Board;
import com.mycompany.mini_album.dto.Images;
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
  
  @GetMapping("/battach/{bno}/{ino}")
  public ResponseEntity<InputStreamResource> download(@PathVariable int bno, @PathVariable int ino) throws Exception {
    Board board = boardService.getBoard(bno);
    String battachoname = "";
    String battachsname = "";
    String battachtype = "";
    
    for(int i=0; i<board.getBimages().size(); i++) {
      if (ino == board.getBimages().get(i).getIno()) {
        battachoname = board.getBimages().get(i).getIoname();
        battachsname = board.getBimages().get(i).getIsname();
        battachtype = board.getBimages().get(i).getItype();
        
        if (battachoname == null) return null;
        
      }
    }
    
    //파일 이름이 한글일 경우에 설정
    battachoname = new String(battachoname.getBytes("UTF-8"),"ISO-8859-1");
    
    //파일 입력 스트림 생성
    FileInputStream fis = new FileInputStream("C:/Temp/album/" + battachsname);
    InputStreamResource resource = new InputStreamResource(fis);
    
    //응답 생성
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + battachoname + "\";")
        .header(HttpHeaders.CONTENT_TYPE, battachtype)
        .body(resource);
  }
  
  @DeleteMapping("/battach/{bno}/{ino}")
  public Map<String, String> deleteImage(@PathVariable int ino) {
    boardService.deleteImage(ino);
    Map<String, String> map = new HashMap<>();
    map.put("result", "success");
    return map;
  }
  
  @PutMapping("/{bno}")
  public Board update(Board board) {
    log.info("실행");
    log.info(board);
    
    boardService.updateBoard(board);
    Board dbBoard = boardService.getBoard(board.getBno());
    return dbBoard;
  }
  
  @PutMapping("/battach/{bno}")
  public Board updateImage(Images images) {
    log.info("실행");
    log.info(images);
    Board board = boardService.getBoard(26);

    if(images.getImgFile() != null && !images.getImgFile().isEmpty()) {
      for (Images image : board.getBimages()) {        
        MultipartFile mf = image.getImgFile();
        image.setIoname(mf.getOriginalFilename());
        image.setIsname(new Date().getTime() + "-" + mf.getOriginalFilename());
        image.setItype(mf.getContentType());
//    
//       for(int i = 1; i<3; i++) {
//         MultipartFile mf = images.getImgFile();
//         images.setIoname(mf.getOriginalFilename());
//         images.setIsname(new Date().getTime() + "-" + mf.getOriginalFilename());
//         images.setItype(mf.getContentType());
       
        try {
          File file = new File("C:/Temp/album/" + images.getIsname());
          mf.transferTo(file);
        } catch (Exception e) {
          log.error(e.getMessage());
        }
        
        boardService.writeImage(images);
      }
    }
    
    boardService.updateBoard(board);
    Board dbBoard = boardService.getBoard(board.getBno());
    return dbBoard;
  }
}