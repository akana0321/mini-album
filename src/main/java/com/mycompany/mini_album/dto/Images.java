package com.mycompany.mini_album.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class Images {
  private MultipartFile imgFile;
  
  private int ino;
  private int bno;
  private String ioname;
  private String isname;
  private String itype;
}
