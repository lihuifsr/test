package com.h.mall.myprod.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.Test;

public class ImageTest {


	@Test
	public void formatImage() throws IOException{
		
		for (int i = 0; i < 100; i++) {
			
			File file = new File( "D:\\image2\\"+i+".png");
			file.canRead(); 
			if(file.exists()){
				BufferedImage image = ImageIO.read(file);
				File newfile = new File( "D:\\image4\\"+i+".tiff");
				ImageIO.write(image, "tiff", newfile);
			}
		}
	}
	
	@Test
	public void deleteImage(){
		for (int i = 0; i < 100; i++) {
			File file = new File( "D:\\image2\\"+i+".tif");
			if(file.exists()){
				file.delete();
			}
		}
	}
}
