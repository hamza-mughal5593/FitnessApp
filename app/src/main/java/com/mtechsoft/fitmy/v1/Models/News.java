package com.mtechsoft.fitmy.v1.Models;

public class News {
  int news_image;
  String news_name;

  public News(int news_image, String news_name) {
    this.news_image = news_image;
    this.news_name = news_name;
  }

  public int getNews_image() {
    return news_image;
  }

  public void setNews_image(int news_image) {
    this.news_image = news_image;
  }

  public String getNews_name() {
    return news_name;
  }

  public void setNews_name(String news_name) {
    this.news_name = news_name;
  }
}

