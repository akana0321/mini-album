package com.mycompany.mini_album.dao;

import org.apache.ibatis.annotations.Mapper;

import com.mycompany.mini_album.dto.Member;

@Mapper
public interface MemberDao {
  public int insert(Member member); 
  public Member selectByMid(String mid);
}
