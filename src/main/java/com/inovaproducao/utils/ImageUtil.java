package com.inovaproducao.utils;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.web.multipart.MultipartFile;

import com.inovaproducao.exceptions.FileException;

public class ImageUtil {

	public static String getExtension(MultipartFile file) {
		String type = file.getContentType();
		type = type.substring(type.lastIndexOf("/") + 1);
		return type;
	}
	
	public static BufferedImage toBufferedReader(MultipartFile file) throws IOException {
		return  ImageIO.read(file.getInputStream());
	}
	
	public static BufferedImage pngToJpg(MultipartFile file) {
		String ext = ImageUtil.getExtension(file);

		if (!"png".equals(ext) && !"jpg".equals(ext)) {
			throw new FileException("Image não suportada");
		}

		try {
			BufferedImage img = ImageIO.read(file.getInputStream());
			if ("png".equals(ext)) {
				img = ImageUtil.pngToJpg(img);
			}
			return img;
		} catch (Exception e) {
			throw new FileException("Image não suportada");

		}

	}

	public static BufferedImage pngToJpg(BufferedImage img) {
		BufferedImage jpgImage = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_RGB);
		jpgImage.createGraphics().drawImage(img, 0, 0, Color.WHITE, null);
		return jpgImage;
	}
	
	public static InputStream getInputStream(BufferedImage image, String extension) {
		try {
			ByteArrayOutputStream os = new ByteArrayOutputStream();
			ImageIO.write(image, extension, os);
			return new ByteArrayInputStream(os.toByteArray());
		}catch(IOException e) {
			throw new FileException("Erro ao ler arquivo");
		}
	}
	
	
	public static BufferedImage cropSquare(BufferedImage image) {
		int min = (image.getHeight() <= image.getWidth()) ? image.getHeight() : image.getWidth();
		return Scalr.crop(image,
				(image.getWidth()/2 - min/2),
				(image.getHeight()/2 - min/2),
				min, 
				min);
	}
	
	public static BufferedImage resize(BufferedImage image, int size) {
		return Scalr.resize(image, Scalr.Method.ULTRA_QUALITY, size);
	}

}