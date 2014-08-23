/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamgooof.watermark;

import java.awt.Desktop;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Jake
 */
public class Gui extends JFrame {

    private FileFilter imageFilter;

    public Gui() {
        initComponents();
        imageFilter = new ImageFilter();
    }

    private void initComponents() {
        GridLayout layout = new GridLayout(0, 1);
        layout.setVgap(5);
        JPanel container = new JPanel(layout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        btnBrowseImages = new JButton("Browse");
        btnBrowseImages.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnBrowseImagesActionPerformed();
            }
        });
        btnBrowseWatermark = new JButton("Browse");
        btnBrowseWatermark.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnBrowseWatermarkActionPerformed();
            }
        });
        btnStartProcessing = new JButton("Process");
        btnStartProcessing.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                btnStartProcessingActionPerformed();
            }
        });
        checkBoxRecursive = new JCheckBox("Recursive");
        lblWatermark = new JLabel("Watermark Location", JLabel.CENTER);
        lblImages = new JLabel("Images location", JLabel.CENTER);
        txtImagesLocation = new JTextField();
        txtWatermarkLocation = new JTextField();
        progress = new JProgressBar();
        progress.setStringPainted(true);
        progress.setString("Idle");
        //watermark
        container.add(lblWatermark);
        container.add(txtWatermarkLocation);
        container.add(btnBrowseWatermark);
        //Images
        container.add(lblImages);
        container.add(txtImagesLocation);
        container.add(btnBrowseImages);
        //Misc
        container.add(checkBoxRecursive);
        container.add(btnStartProcessing);
        container.add(progress);
        add(container);
        pack();
    }
    private JButton btnBrowseImages, btnBrowseWatermark, btnStartProcessing;
    private JCheckBox checkBoxRecursive;
    private JLabel lblWatermark, lblImages;
    private JProgressBar progress;
    private JTextField txtImagesLocation, txtWatermarkLocation;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }

    private void btnBrowseWatermarkActionPerformed() {
        String file = openFileBrowser(imageFilter, false);
        if (file == null) {
            return;
        }
        txtWatermarkLocation.setText(file);
    }

    private void enableButtons(boolean enabled) {
        btnBrowseImages.setEnabled(enabled);
        btnBrowseWatermark.setEnabled(enabled);
        btnStartProcessing.setEnabled(enabled);
    }

    private void btnStartProcessingActionPerformed() {
        if (!new File(txtWatermarkLocation.getText()).exists()) {
            JOptionPane.showMessageDialog(null, "Watermark image not found", "File not found", JOptionPane.ERROR_MESSAGE);
            return;
        }
        enableButtons(false);
        preprocess();
    }

    private void btnBrowseImagesActionPerformed() {
        String file = openFileBrowser(true);
        if (file == null) {
            return;
        }
        txtImagesLocation.setText(file);
    }
    private List<WatermarkedImage> watermarkedList = new ArrayList<WatermarkedImage>();

    private String openFileBrowser(FileFilter filter, boolean acceptDirectories) {
        JFileChooser chooser = new JFileChooser();
        if (filter != null) {
            chooser.setFileFilter(filter);
        }
        if (acceptDirectories) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        int val = chooser.showDialog(null, null);
        if (val != JFileChooser.APPROVE_OPTION || chooser.getSelectedFile() == null || (!acceptDirectories && chooser.getSelectedFile().isDirectory())) {
            return null;
        }
        return chooser.getSelectedFile().getAbsolutePath();
    }

    private String openFileBrowser(boolean acceptDirectories) {
        return openFileBrowser(null, acceptDirectories);
    }

    private void preprocess() {
        progress.setValue(0);
        progress.setString("Pre-processing images");
        File file = new File(txtImagesLocation.getText());
        if (checkBoxRecursive.isSelected()) {
            getImages(file);
        } else {
            for (File f : file.listFiles(new java.io.FileFilter() {

                @Override
                public boolean accept(File pathname) {
                    return !pathname.isDirectory();
                }
            })) {
                watermarkedList.add(new WatermarkedImage(f));
            }
        }
        final WatermarkPreProcessor preprocessor = new WatermarkPreProcessor(watermarkedList, new File(txtWatermarkLocation.getText()));
        preprocessor.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress".equals(evt.getPropertyName())) {
                    progress.setValue((Integer) evt.getNewValue());
                } else if (evt.getPropertyName().equals("state") && String.valueOf(evt.getNewValue()).equalsIgnoreCase("DONE")) {
                    try {
                        watermarkedList = preprocessor.get();
                    } catch (InterruptedException | ExecutionException ex) {
                        Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    process();
                }
            }
        });
        preprocessor.execute();
    }

    private void process() {
        if (watermarkedList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No images found to watermark", "No images found", JOptionPane.ERROR_MESSAGE);
            enableButtons(true);
            return;
        }
        if (Desktop.isDesktopSupported()) {
            try {
                Desktop.getDesktop().browse(new File(txtImagesLocation.getText()).toURI());
            } catch (IOException ex) {
                Logger.getLogger(Gui.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        final WatermarkProcessor processor = new WatermarkProcessor(watermarkedList, new File(txtWatermarkLocation.getText()));
        processor.addPropertyChangeListener(new PropertyChangeListener() {

            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                if ("progress".equals(evt.getPropertyName())) {
                    progress.setValue((Integer) evt.getNewValue());
                    progress.setString(processor.getCounter().get() + "/" + watermarkedList.size());
                } else if (evt.getPropertyName().equals("state") && String.valueOf(evt.getNewValue()).equalsIgnoreCase("done")) {
                    progress.setString("Finished");
                    enableButtons(true);
                }
            }
        });
        processor.execute();
    }

    private void getImages(File file) {
        for (File f : file.listFiles()) {
            if (f.isDirectory()) {
                getImages(f);
            } else if (imageFilter.accept(f)) {
                watermarkedList.add(new WatermarkedImage(f));
            }
        }
    }
}
