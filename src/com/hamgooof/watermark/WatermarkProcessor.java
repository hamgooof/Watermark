package com.hamgooof.watermark;

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
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;

/**
 * Pre-processes the location for the watermark on the images.
 *
 * @author Jake
 */
public class WatermarkProcessor extends SwingWorker<Void, Integer> {

	private final AtomicInteger counter = new AtomicInteger(1);
	private final File watermarkFile;
	private final WatermarkPreProcessor preProcessor;

	public WatermarkProcessor(WatermarkPreProcessor preProcessor,
			File watermarkFile) {
		this.preProcessor = preProcessor;
		this.watermarkFile = watermarkFile;
	}

	@Override
	protected Void doInBackground() throws Exception {
		Image watermark = null;
		try {
			watermark = ImageIO.read(watermarkFile);
			System.out.println("Got watermark");
		} catch (IOException ex) {
			Logger.getLogger(WatermarkProcessor.class.getName()).log(
					Level.SEVERE, null, ex);
		}
		if (watermark == null) {
			throw new Exception("Watermark not loaded");
		}
		try {
			while (!preProcessor.isDone()) {
				Thread.sleep(1000);
			}
			List<WatermarkedImage> list = WatermarkPreProcessor.getList();
			for (Iterator<WatermarkedImage> iter = list.iterator(); iter
					.hasNext();) {
				double d = list.size();
				d = Math.min(100, 100d / d * counter.getAndAdd(1));
				processWatermark(iter.next(), watermark, false);
				setProgress((int) d);
			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
		}
		return null;
	}

	public AtomicInteger getCounter() {
		return counter;
	}

	@Override
	protected void process(List<Integer> chunks) {
		super.process(chunks);
	}

	private void processWatermark(final WatermarkedImage next, Image watermark,
			boolean recheck) {
		System.out.println("Processing: " + next.getName());
		BufferedImage bi = new BufferedImage(next.getImageDimensions().width,
				next.getImageDimensions().height, BufferedImage.TYPE_INT_RGB);
		try {
			Image img = ImageIO.read(next.getImgFile());
			Graphics2D g2d = (Graphics2D) bi.getGraphics();
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
					RenderingHints.VALUE_ANTIALIAS_ON);
			g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
					RenderingHints.VALUE_INTERPOLATION_BICUBIC);
			g2d.drawImage(img, 0, 0, null);
			g2d.drawImage(watermark, next.getWatermarkXPos(),
					next.getWatermarkYPos(), next.getWatermarkWidth(),
					next.getWatermarkHeight(), null);
			if (!next.getSaveDirectory().exists()) {
				next.getSaveDirectory().mkdirs();
			}
			ImageIO.write(bi, next.getImageType(),
					new File(next.getSaveDirectory(), next.getImgFile()
							.getName()));
		} catch (Exception ex) {
			if (!recheck) {
				processWatermark(next, watermark, true);
			} else {
				System.out.println(ex.getMessage());
				JOptionPane.showMessageDialog(null, next.getName()
						+ " failed saving");
			}
		}
	}

}
