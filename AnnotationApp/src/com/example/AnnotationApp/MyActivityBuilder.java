package com.example.AnnotationApp;

import android.content.Intent;
import java.lang.Integer;
  
  public class MyActivityBuilder {
    private Integer dataOne;
  
    void setdataOne() {
      android.content.Intent intent  = new Intent();
      if ( dataOne != null) {
        intent.putExtra("myactivity:data",dataOne);
      }
    }
  }