package com.mycompany.mini_album.dao;

import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface CategoryDao {

  public int insert(String cname);
  public int delete(String cname);
  public int update(String cname);
  
}
