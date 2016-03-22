package com.h.mall.myprod.model;

import java.util.Arrays;
import java.util.List;

/**
 * 字符
 * 由布尔矩阵组成
 * true表示是字符区域，false表示空白区域
 *
 */
public class Char {
  private int width;
  private int height;
  private List<Point> blackPoints;
  private boolean[][] values;

  public Char(List<Point> blackPoints) {
    super();
    this.blackPoints = blackPoints;

    // 将黑点序列中最大y坐标设为字符width，最大x坐标设为height
    this.width = 0;
    this.height = 0;
    for (Point p : blackPoints) {
      if (p.getY() + 1 > this.width) {
        this.width = p.getY() + 1;
      }
      if (p.getX() + 1 > this.height) {
        this.height = p.getX() + 1;
      }
    }
    // 设置每个点布尔值
    this.values = new boolean[this.width][this.height];
    for (Point p : blackPoints) {
      setPointValue(p, 0);
    }
  }

  /**
   * 设置指定坐标点像素值
   * 
   * @param p
   */
  public void setPointValue(Point p, int value) {
    if (p == null) return;
    values[p.getY()][p.getX()] = (value == 0);
  }

  public void setBlackPoints(List<Point> ps) {
    this.blackPoints = ps;
  }

  public List<Point> getBlackPoints() {
    return blackPoints;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }

  public int getHeight() {
    return height;
  }

  public void setHeight(int height) {
    this.height = height;
  }

  public boolean[][] getValues() {
    return values;
  }

  public void setValues(boolean[][] values) {
    this.values = values;
  }

  @Override
  public String toString() {
    // 打印图像
    for (int x = 0; x < height; x++) {
      for (int y = 0; y < width; y++) {
        if (values[y][x]) {
          System.out.print("*");
        } else {
          System.out.print(" ");
        }
      }
      System.out.println();
    }
    return "Char [width=" + width + ", height=" + height + ", blackPoints=" + blackPoints
        + ", values=" + Arrays.toString(values) + "]";
  }

}
