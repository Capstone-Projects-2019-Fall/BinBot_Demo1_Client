package edu.temple.cis.capstone.BinBotDemo1;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Arrays;

public class Main
{
	public static void main(String[] args) {
		System.out.println("Hello world");
	}
	
	private static BufferedImage stringToBufferedImage(String s) {
		BufferedImage retval = null;
		try {
			retval = ImageIO.read(new ByteArrayInputStream(s.getBytes()));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return retval;
	}

	private static String bufferedImageToString(BufferedImage bi) {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {
			ImageIO.write(bi, "jpg", out);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Arrays.toString(out.toByteArray());

	}

}
