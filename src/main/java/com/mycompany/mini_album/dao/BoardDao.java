package com.mycompany.mini_album.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.mini_album.dto.Board;
import com.mycompany.mini_album.dto.Pager;

@Mapper
public interface BoardDao {
  public List<Board> selectByPage(Pager pager); // 사용자의 전체 게시물 가져오기
  public List<Board> selectByPageWithCategory(Pager pager); // 사용자의 특정 카테고리의 게시물 가져오기
  public Board selectByBno(int bno);   // 특정 게시물의 정보 가져오기
  public int count();
  public int insert(Board board);
  public int deleteByBno(int bno);
  public int update(Board board); 
}
