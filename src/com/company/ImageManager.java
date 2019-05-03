package com.company;

import javax.imageio.ImageIO;
import java.awt.Graphics;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Scanner;
import java.io.File;
import java.util.ArrayList;
import java.awt.image.BufferedImage;
import java.util.TreeMap;
import java.util.Set;

public class ImageManager
{
	TreeMap<String, BufferedImage> images = null;

	public ImageManager()
	{
		images =new TreeMap<String,BufferedImage>();
	}

	/* Pre: Receives a name of file
	 * Post: Loads all this images in the file to map of images using the provided load type
	 */
	public boolean loadImages(String fileName)
	{
		try {
			Scanner scanner = new Scanner(new File(fileName));
			while (scanner.hasNextLine()) {
				String s = scanner.nextLine();

				Scanner lScanner = new Scanner(s);
				lScanner.useDelimiter(",");

				switch (lScanner.next()) {
					case "single":
						String k = lScanner.next();
						BufferedImage b = ImageIO.read(new File(lScanner.next()));

						images.put(k, b);
						break;
					case "SNbL":
						int n = lScanner.nextInt();

						String l = lScanner.next();
						BufferedImage c = ImageIO.read(new File(lScanner.next()));

						for(int i = 0; i < n; i++) {
							/*
							BufferedImage temp = new BufferedImage(c.getWidth()/n, c.getHeight(), BufferedImage.TYPE_INT_ARGB);
							temp.getGraphics().drawImage(c, 0, 0, temp.getWidth(), temp.getHeight(),
									i * temp.getWidth(), 0, (i + 1) * temp.getWidth(), temp.getHeight(), null);*/
							int w = c.getWidth()/n;
							BufferedImage temp = c.getSubimage(i * w, 0, w, c.getHeight());

							images.put(l + i, temp);
						}
						break;
					case "SSN":
						int o = lScanner.nextInt();
						int foo = 0;
						String[] names = new String[o];

						while(foo < o) {
							names[foo] = lScanner.next();
							foo++;
						}

						BufferedImage d = ImageIO.read(new File(lScanner.next()));

						for(int j = 0; j < o; j++) {
							/*
							BufferedImage temp = new BufferedImage(d.getWidth()/o, d.getHeight(), BufferedImage.TYPE_INT_ARGB);
							temp.getGraphics().drawImage(d, 0, 0, temp.getWidth(), temp.getHeight(),
									j * temp.getWidth(), 0, (j + 1) * temp.getWidth(), temp.getHeight(), null);*/
							int w = d.getWidth()/o;
							BufferedImage temp = d.getSubimage(j * w, 0, w, d.getHeight());
							images.put(names[j], temp);
						}
						break;
					case "GNbL":
						int col = lScanner.nextInt();
						int row = lScanner.nextInt();

						String name = lScanner.next();

						BufferedImage e = ImageIO.read(new File(lScanner.next()));

						for(int y = 0; y < row; y++) {
							for(int x = 0; x < col; x++) {
								/*
								BufferedImage bar = new BufferedImage(e.getWidth()/col, e.getHeight()/row, BufferedImage.TYPE_INT_ARGB);
								bar.getGraphics().drawImage(e, 0, 0, bar.getWidth(), bar.getHeight(),
										frame * bar.getWidth(), y * bar.getHeight(), (frame + 1) * bar.getWidth(), (y + 1) * bar.getHeight(), null);*/
								int w = e.getWidth()/col;
								int h = e.getHeight()/row;

								BufferedImage bar = e.getSubimage(x * w, y * h, w, h);

								images.put(name + "r" + y + "c" + x, bar);
							}
						}
						break;
					case "GSN":
						int co = lScanner.nextInt();
						int ro = lScanner.nextInt();

						String[][] nameT = new String[ro][co];

						for(int m = 0; m < ro; m++) {
							for(int p = 0; p < co; p++) {
								nameT[m][p] = lScanner.next();
							}
						}

						BufferedImage img = ImageIO.read(new File(lScanner.next()));

						for(int t = 0; t < ro; t++) {
							for(int u = 0; u < co; u++) {
								/*
								BufferedImage por = new BufferedImage(img.getWidth()/co, img.getHeight()/ro, BufferedImage.TYPE_INT_ARGB);
								por.getGraphics().drawImage(img, 0, 0, por.getWidth(), por.getHeight(),
										u * por.getWidth(), t * por.getHeight(), (u + 1) * por.getWidth(), (t + 1) * por.getHeight(), null);*/
								int w = img.getWidth()/co;
								int h = img.getHeight()/ro;

								BufferedImage por = img.getSubimage(u * w, t * h, w, h);
								images.put(nameT[t][u], por);
							}
						}
				}
			}
			return true;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/* Pre: Receives a key
	 * Post: returns the image that corrisponds to the given key, null if the key is not found
	 */
	public BufferedImage getImage(String key)
	{
		if(key == null)
			return null;
		return images.get(key);
	}

	/* Pre: Receives a key and a file name
	 * Post: loads the image in the given file to the map with the provided key
	 * Note: null is returned if the file can not be opened
	 */
	public BufferedImage loadImage(String key, String file)
	{
		return null;
	}

	/* Pre: Receives a key a
	 * Post: removes the key and its image from the map, the removed image is returned.
	 * null is returned if the map does not cantain the given key
	 */
	public BufferedImage removeImage(String key)
	{
		return null;
	}

	/* Pre: 
	 * Post: empties the map
	 */
	public void clear()
	{
	
	}

	/* Pre: 
	 * Post: returns a set of all the keys in the map
	 */
	public Set<String> getKeys()
	{
		return images.keySet();
	}
}