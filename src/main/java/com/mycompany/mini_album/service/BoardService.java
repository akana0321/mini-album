package com.mycompany.mini_album.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.mini_album.dao.BoardDao;
import com.mycompany.mini_album.dto.Board;
import com.mycompany.mini_album.dto.Images;
import com.mycompany.mini_album.dto.Pager;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class BoardService {
	
	@Resource
	private BoardDao boardDao;
	
	public List<Board> getBoards(Pager pager) {
		log.info("실행");
		return boardDao.selectByPage(pager);
	}
	
		public Board getBoard(int bno) {
		log.info("실행");
		return boardDao.selectByBno(bno);
	}
	
	public int getTotalBoardNum(String mid) {
		log.info("실행");
		return boardDao.count(mid);
	}
	
	public int insertBoard(Board board) {
		log.info("실행");
		boardDao.insert(board);
		return board.getBno();
	}
	
	public int updateBoard(Board board) {
		log.info("실행");
		return boardDao.update(board);
	}
	
	public int deleteBoard(int bno) {
	  System.out.println("딜리트 실행222");
		return boardDao.deleteByBno(bno);
	}
	
	 public int deleteImage(int ino) {
	    log.info("실행");
	    return boardDao.deleteByIno(ino);
	  }

  public int writeImage(Images images) {
    log.info("실행");
    return boardDao.insertImage(images);
    
  }
}








