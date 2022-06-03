package com.mycompany.mini_album.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.mini_album.dto.Category;


@Mapper
public interface CategoryDao {
  public List<Category> selectAll();
  public int insert(String cname);
  public int delete(String cname);
  public int update(Category category);
  
}
