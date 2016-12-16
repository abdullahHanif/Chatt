package com.ubits.chatt.model;

import android.graphics.Bitmap;

public class Conversation
{
  private String date;
  private boolean isSent;
  private boolean isSuccess;
  private String msg;
  private Bitmap bitmap;
  
  public Conversation(String message, Bitmap bmp, String date, boolean sent, boolean delivered) {
    this.msg = message;
    this.isSent = sent;
    this.date = date;
    this.isSuccess = delivered;
    this.bitmap= bmp;
  }

  public String getDate()
{
  return this.date;
}
  
  public String getMsg()
  {
    return this.msg;
  }

  public Bitmap getImage()
  {
    return this.bitmap;
  }
  
  public boolean isSent()
  {
    return this.isSent;
  }
  
  public boolean isSuccess()
  {
    return this.isSuccess;
  }
  
  public void setDate(String paramString)
  {
    this.date = paramString;
  }
  
  public void setMsg(String paramString)
  {
    this.msg = paramString;
  }

  public void setImage(Bitmap paramString)
  {
    this.bitmap = paramString;
  }
  
  public void setSent(boolean paramBoolean)
  {
    this.isSent = paramBoolean;
  }
  
  public void setSuccess(boolean paramBoolean)
  {
    this.isSuccess = paramBoolean;
  }
}

