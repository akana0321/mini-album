package com.mycompany.mini_album.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.mycompany.mini_album.dao.ImagesDao;
import com.mycompany.mini_album.dto.Images;

@Service
public class ImagesService {
  @Resource
  ImagesDao imagesDao;
  
  // 게시물 번호의 모든 이미지 정보 가져오기
  public List<Images> getImagesList(int bno) {
    return imagesDao.selectByBno(bno);
  }
  
  // 상세보기 내의 특정 이미지의 번호를 통해 정보 가져오기
  public Images getImageOne(int ino) {
    return imagesDao.selectOneByIno(ino);
  }
  
  // 상세보기 내의 이미지 개수 얻기
  public int countByBno(int bno) {
    return imagesDao.countByBno(bno);
  }
  
  // 이미지(들) 추가하기
  public int insertImages(List<Images> images) {
    return imagesDao.insert(images);
  }
  
  // 게시물과 연관된 모든 이미지 지우기
  public int deleteImages(int bno) {
    return imagesDao.deleteAllbyBno(bno);
  }
  
  // 상세보기 내의 특정 이미지 지우기
  public int deleteOneImage(int ino) {
    return imagesDao.deleteOneByIno(ino);
  }
}
