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
  
  public List<Category> selectAll() {
    return categoryDao.selectAll();
  }
  
  public int insertCategory(String cname) {
    return categoryDao.insert(cname);
  }
  
  public int updateCategory(Category category) {
    return categoryDao.update(category);
  }
  
  public int deleteCategory(String cname) {
    return categoryDao.delete(cname);
  }
}
