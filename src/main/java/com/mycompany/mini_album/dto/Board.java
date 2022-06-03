package com.mycompany.mini_album.dto;

import java.util.Date;
import java.util.List;

import lombok.Data;

@Data
public class Board {
  private int bno;
  private String mid;
  private String cname;
  private String btitle;
  private String bcontent;
  private Date bdate;
  
  public List<Images> bimages;
}
