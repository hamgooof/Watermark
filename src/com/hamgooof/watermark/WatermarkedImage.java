/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamgooof.watermark;

import java.awt.Dimension;
import java.io.File;

/**
 *
 * @author Jake
 */
public class WatermarkedImage {

    private final File imgFile;
    private int xPos = -1, yPos = -1, width = -1, height = -1;
    private Dimension imageDimensions;

    public WatermarkedImage(File imgFile) {
        this.imgFile = imgFile;
    }

    public File getImgFile() {
        return imgFile;
    }

    public void setImageDimensions(Dimension imageDimensions) {
        this.imageDimensions = imageDimensions;
    }

    public Dimension getImageDimensions() {
        return imageDimensions;
    }

    public void setWatermarkPosition(int x, int y, int width, int height) {
        this.xPos = x;
        this.yPos = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Gets the textual representation of the file name
     *
     * @return
     */
    @Override
    public String toString() {
        return imgFile.getName();
    }

    public File getSaveDirectory() {
        return new File(imgFile.getParentFile(), "Watermarked");
    }

    /**
     * Gets the image type without the separator.
     *
     * @return The image file-type i.e. jpg, png, gif
     */
    public String getImageType() {
        return imgFile.getName().substring(imgFile.getName().indexOf('.') + 1);
    }

    public int getWatermarkYPos() {
        return yPos;
    }

    public int getWatermarkXPos() {
        return xPos;
    }

    public int getWatermarkWidth() {
        return width;
    }

    public int getWatermarkHeight() {
        return height;
    }

    public String getName() {
        return imgFile.getName().substring(0, imgFile.getName().indexOf('.'));
    }

}
