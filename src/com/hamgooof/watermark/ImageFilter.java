/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hamgooof.watermark;

import java.io.File;
import javax.swing.filechooser.FileFilter;

/**
 *
 * @author Jake
 */
public class ImageFilter extends FileFilter {

    @Override
    public boolean accept(File f) {
        return f.getName().matches(("(?i).*(jpe?g|png)$")) || f.isDirectory();
    }

    @Override
    public String getDescription() {
        return "Accepts jpg, png, gif";
    }

}
