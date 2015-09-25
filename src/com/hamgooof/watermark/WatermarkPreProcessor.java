package com.hamgooof.watermark;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.FileImageInputStream;
import javax.swing.SwingWorker;

/**
 * Pre-processes the location for the watermark on the images.
 *
 * @author Jake
 */
public class WatermarkPreProcessor extends
		SwingWorker<List<WatermarkedImage>, Integer> {

	private static List<WatermarkedImage> list;
	private final Dimension watermark;

	public WatermarkPreProcessor(List<WatermarkedImage> list, File watermark) {
		WatermarkPreProcessor.list = list;
		this.watermark = getDimension(new WatermarkedImage(watermark));
	}

	@Override
	protected List<WatermarkedImage> doInBackground() throws Exception {
		int counter = 1;
		for (Iterator<WatermarkedImage> iter = list.iterator(); iter.hasNext();) {
			double d = list.size();
			d = Math.min(100, 100d / d * counter++);
			processWatermark(iter.next());
			setProgress((int) d);
			System.out.println("Progress " + d + " counter " + counter);
		}
		System.out.println("Returning list");
		return list;
	}

	private final static double heightPerc = 0.2, widthPerc = 0.35;

	public synchronized static List<WatermarkedImage> getList() {
		return list;
	}

	private void processWatermark(final WatermarkedImage next) {
		Dimension d = getDimension(next);
		double newWidth, newHeight;
		System.out.println("Preprocessing: " + next.getName());
		if (d.getWidth() > d.getHeight()) {
			// Landscape
			newWidth = (d.getWidth() * widthPerc);
			newHeight = (newWidth / watermark.getWidth())
					* watermark.getHeight();
		} else {
			// Portrait
			newHeight = (d.getHeight() * heightPerc);
			newWidth = (newHeight / watermark.getHeight())
					* watermark.getWidth();
		}
		if (newWidth < watermark.getWidth()
				|| newHeight < watermark.getHeight()) {
			newWidth = watermark.getWidth();
			newHeight = watermark.getHeight();
		}
		next.setImageDimensions(d);
		next.setWatermarkPosition(10, (int) ((d.getHeight() - 10) - newHeight),
				(int) newWidth, (int) newHeight);
	}

	private Dimension getDimension(WatermarkedImage next) {
		Dimension dimension = null;
		Iterator<ImageReader> iter = ImageIO.getImageReadersBySuffix(next
				.getImageType());
		if (iter.hasNext()) {
			ImageReader imageReader = iter.next();
			try {
				imageReader
						.setInput(new FileImageInputStream(next.getImgFile()));
				dimension = new Dimension(imageReader.getWidth(imageReader
						.getMinIndex()), imageReader.getHeight(imageReader
						.getMinIndex()));
			} catch (IOException e) {
				e.printStackTrace(System.err);
			} finally {
				imageReader.dispose();
			}
		}
		return dimension;
	}
}
