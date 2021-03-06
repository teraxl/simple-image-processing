/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tubespcd;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import static tubespcd.Dilasi.colorToRGB;

/**
 *
 * @author sofyan
 */
public class Erosi {

    public BufferedImage image = null;

    public Erosi(BufferedImage srcImage) throws IOException {
        BufferedImage dstImage = new BufferedImage(srcImage.getWidth(), srcImage.getHeight(), BufferedImage.TYPE_INT_RGB);
        srcImage = biner(srcImage);
        for (int i = 0; i < dstImage.getHeight(); i++) {
            for (int j = 0; j < dstImage.getWidth(); j++) {
                dstImage.setRGB(j, i, colorToRGB(0, 255, 255, 255));
                //System.out.println(dstImage.getRGB(j, i));
            }

        }
        ImageIO.write(srcImage, "jpg", new File("/media/sofyan/Data/filePCD/HasilBiner.jpg"));
        for (int i = 1; i < srcImage.getHeight() - 1; i++) {
            for (int j = 1; j < srcImage.getWidth() - 1; j++) {
                if (srcImage.getRGB(j, i) == -16777216) {
                    if (srcImage.getRGB(j + 1, i) == -16777216 && srcImage.getRGB(j, i - 1) == -16777216) {
                        dstImage.setRGB(j, i, colorToRGB(0, 0, 0, 0));
                        System.out.println("masuk2");
                    }
                }

                //System.out.println(srcImage.getRGB(j,i));
            }

        }
        image=dstImage;
        ImageIO.write(dstImage, "jpg", new File("/media/sofyan/Data/filePCD/HasilErosi.jpg"));

    }

    public BufferedImage biner(BufferedImage src) {
        BufferedImage dest = new BufferedImage(src.getWidth(), src.getHeight(), BufferedImage.TYPE_INT_RGB);
        int gray = 0;

        for (int y = 0; y < src.getHeight(); y++) {
            for (int x = 0; x < src.getWidth(); x++) {
                int rgb = src.getRGB(x, y);

                // Merubah Warna Ke RGB
                int red = rgb & 0x000000FF;
                int green = (rgb & 0x0000FF00) >> 8;
                int blue = (rgb & 0x00FF0000) >> 16;
                // END OF Merubah Warna Ke RGB

                int avg = (red + green + blue) / 3;
                // Nilai 128 di bawah ini dapat Anda ubah sesuai dengan kebutuhan
                if (avg < 128) {
                    gray = 0;
                } else if (avg > 128) {
                    gray = 255;
                }

                // Merubah RGB Ke Warna
                int biner = gray + (gray << 8) + (gray << 16);
                // END OF Merubah RGB Ke Warna

                dest.setRGB(x, y, biner);
                //System.out.println(biner);
            }
        }
        return dest;
    }

    public static int colorToRGB(int alpha, int red, int green, int blue) {
        int newPixel = 0;
        newPixel += alpha;
        newPixel = newPixel << 8;
        newPixel += red;
        newPixel = newPixel << 8;
        newPixel += green;
        newPixel = newPixel << 8;
        newPixel += blue;
        //System.out.println(newPixel);
        return newPixel;
    }

}
