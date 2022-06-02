package com.mycompany.mini_album.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.mini_album.dto.Images;

@Mapper
public interface ImagesDao {
  public List<Images> selectByBno(int bno); // 게시물 번호로 이미지(들) 가져오기
  public Images selectOneByIno(int ino);    // 상세보기 화면인 상태에서 이미지 번호로 가져오기
  public int countByBno(int bno);           // 게시물의 총 이미지 개수
  public int insert(List<Images> images);   // 이미지 추가
  public int deleteAllbyBno(int bno);       // 게시물 삭제시 모든 이미지 삭제
  public int deleteOneByIno(int ino);       // 상세보기 화면인 상태에서 특정 이미지 삭제
}
