package com.h.mall.myprod.model;

public class Point {
  // 竖直坐标
  private int x;
  // 水平坐标
  private int y;

  public Point() {
    super();
  }

  public Point(int x, int y) {
    this();
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  @Override
  public String toString() {
    return "Point [x=" + x + ", y=" + y + "]";
  }

}
