package com.mycompany.mini_album.dto;

import java.util.Date;

import lombok.Data;

@Data
public class Board {
  private int bno;
  private String mid;
  private String btitle;
  private String bcontent;
  private Date bdate;
}
