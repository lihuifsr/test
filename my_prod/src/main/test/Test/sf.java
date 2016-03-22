package Test;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 * @author lihui
 *
 */
public class sf {
	
	private BufferedImage image;

	private int iw, ih;

	private int[] pixels;
	
	public sf(BufferedImage image) {
		this.image = image;
		iw = image.getWidth();
		ih = image.getHeight();
		pixels = new int[iw * ih];
	}
	
	
	public static void main(String[] args) throws IOException {
		
		File file = new File("D:\\image\\randcode.png");
		BufferedImage image = ImageIO.read(file);
		new sf(image);
		
		for (int i = 0; i < args.length; i++) {
			
		}
	}
	
	
	
	

}
