package com.mycompany.mini_album.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.mini_album.dto.Board;
import com.mycompany.mini_album.dto.Pager;

@Mapper
public interface BoardDao {
  public List<Board> selectByPage(Pager pager);
  public int count();
  public Board selectByBno(int bno);
  public int insert(Board board);
  public int deleteByBno(int bno);
  public int update(Board board);
}
