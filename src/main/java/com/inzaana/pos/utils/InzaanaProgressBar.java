package com.inzaana.pos.utils;

import java.awt.BorderLayout;
import java.awt.Container;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JProgressBar;
import javax.swing.border.Border;

public class InzaanaProgressBar {
  public static void main(String args[]) {
    JFrame f = new JFrame("JProgressBar Sample");
    f.setLocationRelativeTo(null);
    f.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
    f.setAlwaysOnTop(true);
    Container content = f.getContentPane();
    JProgressBar progressBar = new JProgressBar();
    progressBar.setValue(25);
    progressBar.setStringPainted(true);
    Border border = BorderFactory.createTitledBorder("Reading...");
    progressBar.setBorder(border);
    content.add(progressBar, BorderLayout.NORTH);
    f.setSize(500, 100);
    f.setVisible(true);
  }
}