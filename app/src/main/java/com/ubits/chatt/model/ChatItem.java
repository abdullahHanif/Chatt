package com.ubits.chatt.model;

public class ChatItem
{
  private String date;
  private int icon;
  private boolean isGroup;
  private String msg;
  private String name;
  private boolean online;
  private String title;
  
  public ChatItem(String paramString1, String paramString2, String paramString3, String paramString4, int paramInt, boolean paramBoolean1, boolean paramBoolean2)
  {
    this.name = paramString1;
    this.date = paramString4;
    this.icon = paramInt;
    this.isGroup = paramBoolean2;
    this.msg = paramString3;
    this.online = paramBoolean1;
    this.title = paramString2;
  }
  
  public String getDate()
  {
    return this.date;
  }
  
  public int getIcon()
  {
    return this.icon;
  }
  
  public String getMsg()
  {
    return this.msg;
  }
  
  public String getName()
  {
    return this.name;
  }
  
  public String getTitle()
  {
    return this.title;
  }
  
  public boolean isGroup()
  {
    return this.isGroup;
  }
  
  public boolean isOnline()
  {
    return this.online;
  }
  
  public void setDate(String paramString)
  {
    this.date = paramString;
  }
  
  public void setGroup(boolean paramBoolean)
  {
    this.isGroup = paramBoolean;
  }
  
  public void setIcon(int paramInt)
  {
    this.icon = paramInt;
  }
  
  public void setMsg(String paramString)
  {
    this.msg = paramString;
  }
  
  public void setName(String paramString)
  {
    this.name = paramString;
  }
  
  public void setOnline(boolean paramBoolean)
  {
    this.online = paramBoolean;
  }
  
  public void setTitle(String paramString)
  {
    this.title = paramString;
  }
}


