package com.austin.javautils.Photo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
/**
 * @ClassName: PhotoUtil
 * @description: 证件背景图片背景色替换工具
 * @author: zqz
 * @date: 2024/11/12 15:44
 */
public class PhotoUtil {

    // todo 这三种颜色是网上说是标准的白蓝红的颜色，具体也是可以替换的
    // todo 这三种颜色是网上说是标准的白蓝红的颜色，具体也是可以替换的
    public static void main(String[] args) {
        //白色
        changeBgColor("/Users/zqz/Downloads/output/test1.png", new Color(255,255,255), 30);
        //蓝色
        changeBgColor("/Users/zqz/Downloads/output/test1.png", new Color(2,167,240), 30);
        //红色
        changeBgColor("/Users/zqz/Downloads/output/test1.png", new Color(217,0,27), 30);
    }

    /**
     * 方法描述: 改变背景色
     *
     * @param path  图片路径
     * @param color 要变成的颜色
     * @param range 颜色误差范围（取50左右最佳）
     */
    private static void changeBgColor(String path, Color color, int range) {
        try {
            BufferedImage image = ImageIO.read(new File(path));
            int RGB = image.getRGB(image.getMinX(), image.getMinY());
            // 遍历Y轴的像素
            for (int y = image.getMinY(); y < image.getHeight(); y++) {
                // 遍历X轴的像素
                for (int x = image.getMinX(); x < image.getWidth(); x++) {
                    int rgb = image.getRGB(x, y);
                    int r = (rgb & 0xff0000) >> 16;
                    int g = (rgb & 0xff00) >> 8;
                    int b = (rgb & 0xff);
                    int R = (RGB & 0xff0000) >> 16;
                    int G = (RGB & 0xff00) >> 8;
                    int B = (RGB & 0xff);
                    //颜色误差范围
                    if (Math.abs(R - r) < range && Math.abs(G - g) < range && Math.abs(B - b) < range) {
                        image.setRGB(x, y, color.getRGB());
                    }
                }
            }
            String suffix = path.substring(path.lastIndexOf("."));
            String filePath = path.replace(suffix, "_" + color.getRGB() + suffix);
            ImageIO.write(image, "png", new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 方法描述: argb 转rgb
     * @param color
     * @return {@link String}
     */
    public static String toRGB(int color) {
        int red = (color & 0xff0000) >> 16;// 获取color(RGB)中R位
        int green = (color & 0x00ff00) >> 8;// 获取color(RGB)中G位
        int blue = (color & 0x0000ff);// 获取color(RGB)中B位
        return red + "," + green + "," + blue;
    }

    /**
     * 方法描述: rgb 转argb
     *
     * @param color
     * @return {@link String}
     */
    private static int toARGB(String color) {
        String[] rgb = color.split(",");
        int r = Integer.parseInt(rgb[0]);
        int g = Integer.parseInt(rgb[0]);
        int b = Integer.parseInt(rgb[0]);
        int argb = 255 << 24;//设置A(透明度)的值 左移24位是放到最前面
        argb += (r << 16);
        argb += (g << 8);
        argb += b;
        return argb;
    }
}


