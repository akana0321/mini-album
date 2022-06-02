package com.mycompany.mini_album.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.mini_album.dao.CategoryDao;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CategoryService {
  
  @Resource
  public CategoryDao categoryDao;
  
  public void insertCategory(String cname) {
    categoryDao.insert(cname);
  }
  
  public void updateCategory(String cname) {
    categoryDao.update(cname);
  }
  
  public void deleteCategory(String cname) {
    categoryDao.delete(cname);
  }
}
