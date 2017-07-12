package ue4.preprocessing;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ImagingOpException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.imgscalr.Scalr.Method;

import xgeneral.modules.SystemMessage;

public class ImageScaler {

	/**
	 * Transforms an image containing RGB-Color to an image only containing the
	 * color gray.
	 * 
	 * @param image
	 *            The colored image.
	 * @return Same image in gray.
	 */
	public static BufferedImage toGray(BufferedImage image) {
		int width = image.getWidth();
		int height = image.getHeight();
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				Color c = new Color(image.getRGB(j, i));
				int red = (int) (c.getRed() * 0.21);
				int green = (int) (c.getGreen() * 0.72);
				int blue = (int) (c.getBlue() * 0.07);
				int sum = red + green + blue;
				Color newColor = new Color(sum, sum, sum);
				image.setRGB(j, i, newColor.getRGB());
			}
		}
		return image;
	}

	/**
	 * Loads an image.
	 * 
	 * @param path
	 *            Location of image.
	 * @return The image itself.
	 */
	public static BufferedImage loadImage(String path) {
		System.out.println("Loading <" + path + ">");
		File file = new File(path);
		BufferedImage loadedImage = null;
		try {
			loadedImage = ImageIO.read(file);
		} catch (IOException e) {
			SystemMessage.eMessage("Couldn't read this. <" + path + ">");
			e.printStackTrace();
		} catch (IllegalArgumentException iE) {
			SystemMessage.eMessage("Couldn't read this. <" + path + ">");
		} catch (Exception ES) {
			SystemMessage.eMessage("Couldn't read this. <" + path + ">");
		}
		return loadedImage;
	}

	/**
	 * Writes an image to the given location.
	 * 
	 * @param image
	 *            The image itself.
	 * @param file
	 *            Location where to save the image.
	 */
	public static void writeImage(BufferedImage image, File file) {

		try {
			ImageIO.write(image, "jpg", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Resizes an image.
	 * 
	 * @param image
	 *            The image itself.
	 * @param file
	 *            Location where to save the file.
	 * @param sizeOfNewImage
	 *            The new size of the image.
	 * @return The resized image.
	 */
	public static BufferedImage resize(BufferedImage image, File file, int sizeOfNewImage) {
		BufferedImage resize = null;
		try {
			System.out.println("Writing " + file.getAbsolutePath());
			resize = Scalr.resize(image, Method.ULTRA_QUALITY, sizeOfNewImage, Scalr.OP_ANTIALIAS);
		} catch (IllegalArgumentException | ImagingOpException e) {
			e.printStackTrace();
		}
		return resize;
	}

	/**
	 * Grays and resizes all images in a given dir where the file.size >=
	 * ThresHoldSize.
	 * 
	 * @param pathToDir
	 *            Dir of source-images.
	 * @param resultDir
	 *            Result-dir.
	 * @param resizeTo
	 *            Size of the output-image.
	 * @param ThresHoldSize
	 *            Threshold then to filter.
	 */
	public static void recolorAndResizeImageInDir(String pathToDir, String resultDir, Integer resizeTo) {
		int broken = 0;
		int allFileCount = 0;
		File dirOriSource = new File(pathToDir);

		if (dirOriSource.isDirectory()) {
			ArrayList<File> allFiles = listf(pathToDir, new ArrayList<>());
			allFileCount = allFiles.size();
			for (int i = 0; i < allFileCount; i++) {

				// To overview the process
				if (i % 10 == 0) {
					System.out.println();
					System.out.println(i + "/" + allFileCount + " DONE");
					System.out.println();
				}

				// The processing step
				File acFile = allFiles.get(i);
				if (Files.isRegularFile(acFile.toPath())) {
					File resultDirAsFile = new File(resultDir + "/" + acFile.getParentFile().getName());
					File desFile = new File(resultDirAsFile + "/" + acFile.getName());

					if (!resultDirAsFile.exists())
						resultDirAsFile.mkdirs();

					BufferedImage loadedImage = loadImage(acFile.toString());

					if (loadedImage == null) {
						broken++;
						System.out.println("KEY");
						continue;
					} else {

						BufferedImage resultImage = resize(toGray(loadedImage), desFile, resizeTo);
						writeImage(resultImage, desFile);
					}
				}
			}
		} else {
			System.out.println(pathToDir + " is not a directory");
		}

		System.out.println();
		System.out.println("Processing result ----");
		System.out.println("Total " + allFileCount);
		System.out.println("Successful " + (allFileCount - broken));
		System.out.println("Broken " + (broken));

	}

	/**
	 * Will transform all colored images into grey-images. *
	 * 
	 * @param pathToDir
	 * @param resultDir
	 */
	public static void grayColoredImageInDir(String pathToDir, String resultDir) {
		System.out.println("RIGHT CALL");
		File dirOriSource = new File(pathToDir);
		if (dirOriSource.isDirectory()) {

			ArrayList<File> allFiles = listf(pathToDir, new ArrayList<>());
			int allFileCount = allFiles.size();

			for (int i = 0; i < allFileCount; i++) {
				File acFile = allFiles.get(i);
				if (Files.isRegularFile(acFile.toPath())) {
					File resultDirAsFile = new File(resultDir + "/" + acFile.getParentFile().getName());
					File desFile = new File(resultDirAsFile + "/" + acFile.getName());

					if (!resultDirAsFile.exists())
						resultDirAsFile.mkdirs();

					BufferedImage loadedImage = loadImage(acFile.toString());
					BufferedImage resultImage = toGray(loadedImage);
					writeImage(resultImage, desFile);
					System.out.println("Writing image to: " + desFile.getAbsolutePath());

				}
			}
		} else {
			System.out.println(pathToDir + " is not a directory");
		}
	}

	/**
	 * Returns a list which contains all files from the given directory. The
	 * Source - >
	 * https://stackoverflow.com/questions/14676407/list-all-files-in-the-folder
	 * -and-also-sub-folders
	 * 
	 * Contribute from user: Nathan
	 * 
	 * @param directoryName
	 *            The dir.
	 * @param files
	 *            Initialize with an empty (Maybe-New) Array-List
	 * @return List of files.
	 */
	public static ArrayList<File> listf(String directoryName, ArrayList<File> files) {
		File directory = new File(directoryName);

		// get all the files from a directory
		File[] fList = directory.listFiles();
		for (File file : fList) {
			if (file.isFile()) {
				files.add(file);
			} else if (file.isDirectory()) {
				listf(file.getAbsolutePath(), files);
			}
		}
		return files;
	}
}