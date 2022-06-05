package com.mycompany.mini_album.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.mini_album.dto.Category;


@Mapper
public interface CategoryDao {
  public List<Category> selectAllByMid(String mid);
  public int insert(Category category);
  public int delete(String cname);
  public int update(String cname, String oldCname);
  
}
