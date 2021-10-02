package com.inovaproducao.utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

import org.springframework.web.multipart.MultipartFile;

public class ImageBuilder {
	private BufferedImage image;
	
	public ImageBuilder(BufferedImage bufferedImage) {
		this.image = bufferedImage;
	}
	
	public static ImageBuilder load(MultipartFile file) throws IOException {
		return new ImageBuilder(ImageUtil.toBufferedReader(file));
	}
	
	public ImageBuilder crop() {
		this.image = ImageUtil.cropSquare(image);
		return this;
	}
	
	public ImageBuilder resize(int size) {
		this.image = ImageUtil.resize(image, size);
		return this;
	}
	
	public ImageBuilder pngToJpg() {
		this.image = ImageUtil.pngToJpg(image);
		return this;
	}
	
	public InputStream toInputStream(String extension) {
		return ImageUtil.getInputStream(this.image, extension);
	}
}