package com.h.mall.myprod.Image;

import com.h.mall.myprod.model.Char;
import com.h.mall.myprod.randCode.ImageFilter;
import com.h.mall.myprod.util.ImgUtil;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;


public class s {

    public static void main(String[] args) throws IOException {

        File file = new File("/Users/lihui/Desktop/captcha.jpg");
        BufferedImage image = ImageIO.read(file);
        ImageFilter imageFilter = new ImageFilter(image);
        BufferedImage image1 = imageFilter.grayFilter();// 转换为黑白灰度图
        File newfile1 = new File("/Users/lihui/Desktop/captcha1.jpg");
        ImageIO.write(image1, "png", newfile1);
        image1 = imageFilter.changeGrey(image1);
        File newfile = new File("/Users/lihui/Desktop/captcha2.jpg");
        ImageIO.write(image1, "png", newfile);

        // Integer[] values = new Integer[]{1,1,1,1,1,1};
        // Point point = new Point(0,1);

        BufferedImage image2 = ImageIO.read(newfile);
        Integer iw = image2.getWidth();
        Integer ih = image2.getHeight();

        // 逐行遍历图片
        int[][] values = new int[iw][ih];
        for (int x = 0; x < ih; x++) {
            for (int y = 0; y < iw; y++) {
                if (image2.getRGB(y, x) != -1) {
//	          System.out.print("*");
                    values[y][x] = 0;
                } else {
//	          System.out.print(" ");
                    values[y][x] = 1;
                }
            }
            System.out.println();
        }


        List<Char> chars = ImgUtil.getChars(values, 4, 5, 50);
        for (Char c : chars) {
            System.out.println(c);
        }

    }


}
