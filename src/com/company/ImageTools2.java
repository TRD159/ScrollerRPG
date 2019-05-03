package com.company;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageTools2 {
    public static int HORIZONTAL_FLIP = 1, VERTICAL_FLIP = 2, DOUBLE_FLIP = 3;

    /**
     * Loads an image.
     *
     * @param fileName The name of a file with an image
     * @return Returns the loaded image. null is returned if the image cannot be loaded.
     */
    public static BufferedImage load(String fileName) {
        BufferedImage org = null;
        try {
            org = ImageIO.read(new File(fileName));
        } catch (IOException e) {
            return null;
        }

        return org;
    }

    /**
     * Copies and returns an image.
     *
     * @param img Receives a buffered image
     * @return Returns a copy of the received image. null is returned if the received image is null.
     */
    public static BufferedImage copy(BufferedImage img) {
        if(img == null)
            return null;
        BufferedImage b = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        b.getGraphics().drawImage(img, 0, 0, null);

        return b;
    }

    /**
     * Returns a new image with transparency enabled.
     *
     * @param img Receives a buffered image
     * @return returns a copy of the received image with a color mode that allows transparency.
     * null is returned if the received image is null.
     */
    public static BufferedImage copyWithTransparency(BufferedImage img) {
        if(img == null)
            return null;
        BufferedImage b = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        b.getGraphics().drawImage(img, 0, 0, null);

        return b;
    }

    /**
     * Checks if the provided image has transparency.
     *
     * @param img Receives a buffered image
     * @return returns true if the image has a transparent mode and false otherwise.
     */
    public static boolean hasTransparency(BufferedImage img) {
        return img.getType() == BufferedImage.TYPE_INT_ARGB;
    }

    /**
     * Scales an image.
     *
     * @param img Receives a buffered image and two positive double scale values (percentages)
     * @param horizontalScale Value to scale horizontal by.
     * @param verticalScale Value to scale vertical by.
     * @return creates and returns a scaled copy of the received image,
     * null is returned if the received image is null or if non-positive scales are provided
     */
    public static BufferedImage scale(BufferedImage img, double horizontalScale,
                                      double verticalScale) {
        BufferedImage b = new BufferedImage((int)(img.getWidth() * horizontalScale), (int)(img.getHeight() * verticalScale), BufferedImage.TYPE_INT_ARGB);
        b.getGraphics().drawImage(img, 0, 0, b.getWidth(), b.getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);

        return b;
    }

    /**
     * Scales an image.
     *
     * @param img Receives a buffered image
     * @param newWidth New width to scale to.
     * @param newHeight New height to scale to.
     * @return creates and returns a scaled copy of the received image,
     * null is returned if the received image is null or if non-positive dimensions are provided
     */
    public static BufferedImage scale(BufferedImage img, int newWidth,
                                      int newHeight) {
        BufferedImage b = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_ARGB);
        b.getGraphics().drawImage(img, 0, 0, newWidth, newHeight, 0, 0, img.getWidth(), img.getHeight(), null);

        return b;
    }

    /**
     * Rotates an image.
     *
     * @param img Receives a buffered image
     * @param angle The angle to rotate to.
     * @return The rotated image. null is returned if the received image is null.
     */
    public static BufferedImage rotate(BufferedImage img, double angle) {
        angle = angle % 360;

        AffineTransform r = new AffineTransform();
        r.setToTranslation(0, 0);
        r.rotate(Math.toRadians(angle), img.getWidth()/2, img.getHeight()/2);

        int tr = img.getColorModel().getTransparency();

        BufferedImage b = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        Graphics2D g = (Graphics2D)b.getGraphics();
        g.drawImage(img, r, null);

        return b;
    }

    /**
     * Flips an image.
     *
     * @param img Receives a buffered image
     * @param type Type of flip (int)
     * @return Creates and returns a flipped copy of the received image.
     * null is returned if the received image is null or if an invalid flipping value is provided
     */
    public static BufferedImage flip(BufferedImage img, int type) {
        BufferedImage b = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);

        switch (type) {
            case 1:
                b.getGraphics().drawImage(img, b.getWidth(), 0, 0, b.getHeight(), 0, 0, b.getWidth(), b.getHeight(), null);
                break;
            case 2:
                b.getGraphics().drawImage(img, 0, b.getHeight(), b.getWidth(), 0, 0, 0, b.getWidth(), b.getHeight(), null);
                break;
            case 3:
                b.getGraphics().drawImage(img, b.getWidth(), b.getHeight(), 0, 0, 0, 0, b.getWidth(), b.getHeight(), null);
                break;
            default:
                b.getGraphics().drawImage(img, 0,0, null);
                break;
        }

        return b;
    }

    /**
     * Blurs an image.
     *
     * @param img Receives a buffered image
     * @return creates and returns a blurred copy of the received image,
     * the blurring is created by blending each cell with its 8 neighbors.
     * Null is returned if the received image is null.
     */
    public static BufferedImage blur(BufferedImage img) {
        BufferedImage b = copy(img);

        if(b == null)
            return null;

        for(int x = 0; x < b.getWidth(); x+=1) {
            for(int y = 0; y < b.getHeight(); y+=1) {
                int red = 0;
                int green = 0;
                int blue = 0;
                int alpha = new Color(img.getRGB(x, y), true).getAlpha();

                if(alpha == 0)
                    continue;

                int rgb = 0;

                int pix = 0;

                for(int x1 = x - 1; x1 <= x + 1; x1++) {
                    for(int y1 = y - 1; y1 <= y + 1; y1++) {
                        if(x1 < 0 || x1 > b.getWidth() || y1 < 0 || y1 > b.getHeight()) {
                            System.out.println(x1 + " " + y1);
                            continue;
                        }

                        Color c = null;
                        try {
                            c = new Color(img.getRGB(x1, y1), true);
                        } catch (IndexOutOfBoundsException e) {
                            continue;
                        }

                        if(c.getAlpha() == 0)
                            continue;

                        rgb += c.getRGB();

                        red += c.getRed();
                        green += c.getGreen();
                        blue += c.getBlue();

                        pix++;
                    }
                }

                //System.out.print(pix + " ");

                //System.out.println(red + " " + green + " " + blue);

                red /= pix;
                green /= pix;
                blue /= pix;

                rgb /= pix;

                //b.setRGB(frame, y, rgb);
                //System.out.print(frame + " " + y + "-" + pix + " ");
                b.setRGB(x, y, new Color(red, green, blue,alpha).getRGB());
            }
            //System.out.println();
        }
        return b;
    }

    /**
     * Inverts an image's colors.
     *
     * @param img Receives a buffered image
     * @return Image with inverted colors. null is returned if the received image is null.
     */
    public static BufferedImage invertColor(BufferedImage img) {
        BufferedImage b = copy(img);
        for(int x = 0; x < b.getWidth(); x++) {
            for(int y = 0; y < b.getHeight(); y++) {
                if(b.getRGB(x, y) == 0) {
                    continue;
                }

                Color c = new Color(b.getRGB(x, y));

                Color d = new Color(255 - c.getRed(), 255 - c.getGreen(), 255 - c.getBlue());

                b.setRGB(x, y, d.getRGB());
            }
        }
        return b;
    }

    /**
     * Removes a certain percentage of an image's pixels.
     *
     * @param img Receives a buffered image
     * @param percentToRemove Percent to remove of the image.
     * @return creates and returns a copy of the received image with the given
     * percentage in decimal form of the images remaining non-fully transparent
     * pixels changed to be completely transparent. null is returned if the
     * received image is null or if non-positive percentage is provided.
     */
    public static BufferedImage removePixels(BufferedImage img, double percentToRemove) {
        if(img == null || percentToRemove < 0) {
            return null;
        }
        BufferedImage b = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        b.getGraphics().drawImage(img, 0, 0, null);

        double pRemoved = 0;

        double pFull = 0;
        for(int x = 0; x < b.getWidth(); x++) {
            for(int y = 0; y < b.getHeight(); y++) {
                if(b.getRGB(x, y) != 0) {
                    pFull++;
                }
            }
        }
        while(true) {

            //System.out.println(pRemoved/pFull);
            if((int)(pRemoved/pFull * 1000 - percentToRemove * 1000) == 0)
                break;

            boolean removed = false;

            while(!removed) {
                int x = (int)(Math.random() * b.getWidth());
                int y = (int)(Math.random() * b.getHeight());

                if(b.getRGB(x, y) != 0) {
                    b.setRGB(x, y, 0);
                    removed = true;
                }
            }

            pRemoved++;
        }
        return b;
    }

    /**
     * Removes a certain amount of pixels from an image.
     *
     * @param img Receives a buffered image
     * @param numToRemove number of pixels to remove
     * @return creates and returns a copy of the received image with the given
     * number of the images remaining pixels removed.
     * Non-fully transparent pixels are changed to be completely transparent.
     * null is returned if the received image is null or if non-positive number
     * is provided. Note: is there are not enough pixels in the image it will
     * remove as many as it can.
     */
    public static BufferedImage removePixels(BufferedImage img, int numToRemove) {
        int rem = 0;


        BufferedImage b = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        b.getGraphics().drawImage(img, 0, 0, null);

        int pFull = 0;
        for(int x = 0; x < b.getWidth(); x++) {
            for(int y = 0; y < b.getHeight(); y++) {
                if(b.getRGB(x, y) != 0) {
                    pFull++;
                }
            }
        }

        while(true) {

            //System.out.println(rem);
            if(rem == numToRemove || rem == pFull)
                break;

            boolean removed = false;

            while(!removed) {
                int x = (int)(Math.random() * b.getWidth());
                int y = (int)(Math.random() * b.getHeight());

                if(b.getRGB(x, y) != 0) {
                    b.setRGB(x, y, 0);
                    removed = true;
                }
            }

            rem++;
        }
        return b;
    }

    /**
     * Fades an image.
     *
     * @param img Receives a buffered image
     * @param fade Double percentage to fade
     * @return Creates and returns a copy of the received image that has been
     * faded the given percentage. Fading is done by multiply the alpha value by (1-fade)
     * Null is returned if the received image is null or if non-positive fade value is provided
     * Note: any fade greater than 1 will be reduced to 1
     */
    public static BufferedImage fade(BufferedImage img, double fade) {
        if(img == null || fade < 0) {
            return null;
        }
        BufferedImage b = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        b.getGraphics().drawImage(img, 0, 0, null);
        for(int x = 0; x < b.getWidth(); x++) {
            for(int y = 0; y < b.getHeight(); y++) {
                if(b.getRGB(x, y) == 0)
                    continue;
                Color c = new Color(b.getRGB(x, y));
                double f = (1 - fade > 1) ? 1 : 1 - fade;
                Color d = new Color(c.getRed(), c.getGreen(), c.getBlue(), (int)(c.getAlpha() * f));
                //System.out.println(c.getAlpha());
                b.setRGB(x, y, d.getRGB());
            }
        }

        return b;
    }

    /**
     * Lightens an image.
     *
     * @param img Receives a buffered image
     * @param lightenFactor double percentage to lighten
     * @return creates and returns a copy of the received image that has been
     * lightened by the given percentage + 1.
     * Null is returned if the received image is null or if non-positive lighten.
     * Factor value is provided.
     * Note: any colors that end up being larger than 255 will be changed to 255.
     */
    public static BufferedImage lighten(BufferedImage img, double lightenFactor) {
        if(img == null || lightenFactor < 0) {
            return null;
        }

        BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        bi.getGraphics().drawImage(img, 0, 0, null);

        double percent = lightenFactor + 1;

        for(int x = 0; x < bi.getWidth(); x++) {
            for(int y = 0; y < bi.getHeight(); y++) {
                if(bi.getRGB(x, y) == 0)
                    continue;
                Color c = new Color(bi.getRGB(x, y));

                double r = c.getRed() * percent;
                double g = c.getGreen() * percent;
                double b = c.getBlue() * percent;

                bi.setRGB(x, y, new Color((int)((r > 255) ? 255 : r), (int)((g > 255) ? 255 : g), (int)((b > 255) ? 255 : b)).getRGB());
            }
        }

        return bi;
    }

    /**
     * Darkens an image.
     *
     * @param img Receives a buffered image
     * @param darkenFactor double percentage to darken
     * @return creates and returns a copy of the received image that has been
     * darkened by 1 minus the given percentage.
     * null is returned if the received image is null or if non-positive.
     * Note: any colors that end up being larger than 255 will be
     * changed to 255.
     */
    public static BufferedImage darken(BufferedImage img, double darkenFactor) {
        if(img == null || darkenFactor < 0) {
            return null;
        }

        BufferedImage bi = new BufferedImage(img.getWidth(), img.getHeight(), BufferedImage.TYPE_INT_ARGB);
        bi.getGraphics().drawImage(img, 0, 0, null);

        double percent = 1 - darkenFactor;

        for(int x = 0; x < bi.getWidth(); x++) {
            for(int y = 0; y < bi.getHeight(); y++) {
                if(bi.getRGB(x, y) == 0)
                    continue;
                Color c = new Color(bi.getRGB(x, y));

                double r = c.getRed() * percent;
                double g = c.getGreen() * percent;
                double b = c.getBlue() * percent;

                bi.setRGB(x, y, new Color((int)((r > 255) ? 255 : r), (int)((g > 255) ? 255 : g), (int)((b > 255) ? 255 : b)).getRGB());
            }
        }

        return bi;
    }
}
