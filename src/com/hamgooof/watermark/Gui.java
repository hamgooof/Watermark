/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamgooof.watermark;

import java.awt.Desktop;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Jake
 */
public class Gui extends javax.swing.JFrame {

    /**
     * Creates new form Gui
     */
    private FileFilter imageFilter;
    
    public Gui() {
        initComponents();
        imageFilter = new ImageFilter();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtWatermarkLocation = new javax.swing.JTextField();
        btnBrowseWatermark = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txtImagesLocation = new javax.swing.JTextField();
        btnBrowseImages = new javax.swing.JButton();
        btnStartProcessing = new javax.swing.JButton();
        jProgressBar1 = new javax.swing.JProgressBar();
        checkBoxRecursive = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Watermark");

        jLabel1.setText("Watermark");

        btnBrowseWatermark.setText("Browse");
        btnBrowseWatermark.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseWatermarkActionPerformed(evt);
            }
        });

        jLabel2.setText("Image folder");

        btnBrowseImages.setText("Browse");
        btnBrowseImages.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBrowseImagesActionPerformed(evt);
            }
        });

        btnStartProcessing.setText("Process");
        btnStartProcessing.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnStartProcessingActionPerformed(evt);
            }
        });

        jProgressBar1.setString("Idle");
        jProgressBar1.setStringPainted(true);

        checkBoxRecursive.setText("Recursive");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(63, 63, 63)
                                .addComponent(jLabel1)
                                .addGap(101, 101, 101)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtImagesLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(38, 38, 38)
                                        .addComponent(jLabel2))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(btnBrowseWatermark)
                                        .addGap(123, 123, 123)
                                        .addComponent(btnBrowseImages))
                                    .addComponent(txtWatermarkLocation, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnStartProcessing, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(checkBoxRecursive))))
                .addGap(0, 24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(checkBoxRecursive)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnStartProcessing))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtWatermarkLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtImagesLocation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(4, 4, 4)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnBrowseWatermark)
                            .addComponent(btnBrowseImages))))
                .addGap(18, 18, 18)
                .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBrowseWatermarkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseWatermarkActionPerformed
        String file = openFileBrowser(imageFilter, false);
        if (file == null) {
            return;
        }
        txtWatermarkLocation.setText(file);
    }//GEN-LAST:event_btnBrowseWatermarkActionPerformed
    private void enableButtons(boolean enabled) {
        btnBrowseImages.setEnabled(enabled);
        btnBrowseWatermark.setEnabled(enabled);
        btnStartProcessing.setEnabled(enabled);
    }
    private void btnStartProcessingActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnStartProcessingActionPerformed
        if (!new File(txtWatermarkLocation.getText()).exists()) {
            JOptionPane.showMessageDialog(null, "Watermark image not found", "File not found", JOptionPane.ERROR_MESSAGE);
            return;
        }
        enableButtons(false);
        preprocess();
    }//GEN-LAST:event_btnStartProcessingActionPerformed

    private void btnBrowseImagesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBrowseImagesActionPerformed
        String file = openFileBrowser(true);
        if (file == null) {
            return;
        }
        txtImagesLocation.setText(file);
    }//GEN-LAST:event_btnBrowseImagesActionPerformed
    private List<WatermarkedImage> watermarkedList = new ArrayList<WatermarkedImage>();
    
    private String openFileBrowser(FileFilter filter, boolean acceptDirectories) {
        JFileChooser chooser = new JFileChooser();
        if (filter != null) {
            chooser.setFileFilter(filter);
        }
        if (acceptDirectories) {
            chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        }
        int val = chooser.showDialog(jLabel1, null);
        if (val != JFileChooser.APPROVE_OPTION || chooser.getSelectedFile() == null || (!acceptDirectories && chooser.getSelectedFile().isDirectory())) {
            return null;
        }
        return chooser.getSelectedFile().getAbsolutePath();
    }
    
    private String openFileBrowser(boolean acceptDirectories) {
        return openFileBrowser(null, acceptDirectories);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(final String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gui.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gui().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBrowseImages;
    private javax.swing.JButton btnBrowseWatermark;
    private javax.swing.JButton btnStartProcessing;
    private javax.swing.JCheckBox checkBoxRecursive;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JTextField txtImagesLocation;
    private javax.swing.JTextField txtWatermarkLocation;
    // End of variables declaration//GEN-END:variables

    private void preprocess() {
        jProgressBar1.setValue(0);
        jProgressBar1.setString("Pre-processing images");
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
                System.out.println(String.valueOf(evt.getNewValue()));
                if ("progress".equals(evt.getPropertyName())) {
                    jProgressBar1.setValue((Integer) evt.getNewValue());
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
                System.out.println(evt.getPropertyName());
                if ("progress".equals(evt.getPropertyName())) {
                    jProgressBar1.setValue((Integer) evt.getNewValue());
                    jProgressBar1.setString(processor.getCounter().get() + "/" + watermarkedList.size());
                } else if (evt.getPropertyName().equals("state") && String.valueOf(evt.getNewValue()).equalsIgnoreCase("done")) {
                    jProgressBar1.setString("Finished");
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
