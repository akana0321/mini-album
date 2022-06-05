package com.mycompany.mini_album.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.mini_album.dao.CategoryDao;
import com.mycompany.mini_album.dto.Category;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CategoryService {
  
  @Resource
  public CategoryDao categoryDao;
  
  public List<Category> selectAll(String mid) {
    return categoryDao.selectAllByMid(mid);
  }
  
  public int insertCategory(Category category) {
    return categoryDao.insert(category);
  }
  
  public int updateCategory(String cname, String oldCname) {
    return categoryDao.update(cname, oldCname);
  }
  
  public int deleteCategory(String cname) {
	  System.out.println("딜리트 실행2222222 : " + cname);
    return categoryDao.delete(cname);
  }
}
