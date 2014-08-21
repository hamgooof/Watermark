package com.hamgooof.watermark;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.SwingWorker;

/**
 * Pre-processes the location for the watermark on the images.
 *
 * @author Jake
 */
public class WatermarkProcessor extends SwingWorker<Void, Integer> {

    private final List<WatermarkedImage> list;
    private Image watermark = null;
    private Component[] reEnable;
    private AtomicInteger counter = new AtomicInteger(1);

    public WatermarkProcessor(List<WatermarkedImage> list, File watermark, Component... reenable) {
        this.list = list;
        this.reEnable = reenable;
        try {
            this.watermark = ImageIO.read(watermark);
        } catch (IOException ex) {
            Logger.getLogger(WatermarkProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    protected Void doInBackground() throws Exception {
        if (watermark == null) {
            throw new Exception("Watermark not loaded");
        }
        for (Iterator<WatermarkedImage> iter = list.iterator(); iter.hasNext();) {
            double d = list.size();
            d = Math.min(100, 100d / d * counter.getAndAdd(1));
            processWatermark(iter.next());
            //    System.out.println("Setting progress.");
            setProgress((int) d);
        }
        for (Component c : reEnable) {
            c.setEnabled(true);
        }
        return null;
    }

    public AtomicInteger getCounter() {
        return counter;
    }

    @Override
    protected void process(List<Integer> chunks) {
        super.process(chunks); //To change body of generated methods, choose Tools | Templates.
    }

    private void processWatermark(final WatermarkedImage next) {
        BufferedImage bi = new BufferedImage(next.getImageDimensions().width, next.getImageDimensions().height, BufferedImage.TYPE_INT_RGB);
        try {
            Image img = ImageIO.read(next.getImgFile());
            Graphics2D g2d = (Graphics2D) bi.getGraphics();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                    RenderingHints.VALUE_INTERPOLATION_BICUBIC);
            g2d.drawImage(img, 0, 0, null);
            g2d.drawImage(watermark, next.getWatermarkXPos(), next.getWatermarkYPos(), next.getWatermarkWidth(), next.getWatermarkHeight(), null);
            if (!next.getSaveDirectory().exists()) {
                next.getSaveDirectory().mkdirs();
            }
            ImageIO.write(bi, next.getImageType(), new File(next.getSaveDirectory(), next.getImgFile().getName()));
        } catch (IOException ex) {
            Logger.getLogger(WatermarkProcessor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
