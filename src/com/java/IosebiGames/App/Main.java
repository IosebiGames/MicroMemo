package com.java.IosebiGames.App;

import com.java.IosebiGames.backend.VoiceSaving;
import com.jgoodies.forms.factories.DefaultComponentFactory;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.awt.*;

public class Main {
   public JFrame window = new JFrame("MicroMemo");
   private int counter = 0;
   private JLabel titleText, authorLabel;
   public JButton Start, Stop, Listen, Delete;
   private JTextField timeField;
   private VoiceSaving vs;
   
   public Timer recTimer = new Timer(1000, new ActionListener() {
      public void actionPerformed(ActionEvent e) {
         counter++;
         timeField.setText("        " + counter + " seconds");
      }
   });
   public Main() {
	  this.vs = new VoiceSaving(this);
	  this.Start = new JButton("Start");
	  this.Stop = new JButton("Stop");
	  this.Listen = new JButton("Listen");
	  this.Delete = new JButton("Delete"); 
	  this.timeField = new JTextField("        0 seconds");
	  this.authorLabel = new JLabel("Made by IosebiGames | Click to see Github repository");
	  window.setResizable(false);
	  window.getContentPane().setLayout(null);
      window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      window.setPreferredSize(new Dimension(522, 229));
      window.pack();
      window.setLocationRelativeTo(null);
      window.getContentPane().setBackground(Color.white);
      window.setIconImage(new ImageIcon(getClass().getResource("/com/java/IosebiGames/res/logo.png")).getImage());

      titleText = DefaultComponentFactory.getInstance().createLabel("MicroMemo");
      titleText.setFont(new Font("Tahoma", 1, 23));
      titleText.setBounds(177, -19, 287, 75);
      
      Start.setBounds(63, 47, 89, 42);
      Start.setFocusable(false);
      Start.setBackground(Color.white);
      Start.setForeground(Color.black);
      Start.setFont(new Font("Tahoma", Font.BOLD, 15));
      Start.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == Start) {
        		vs.startSaving();
        		Start.setEnabled(false);
        		Listen.setEnabled(false);
        		Delete.setEnabled(false);
        		Stop.setEnabled(true);
        		recTimer.start();
        	}
         }
      });
      Stop.setBounds(165, 47, 89, 42);
      Stop.setEnabled(false);
      Stop.setFocusable(false);
      Stop.setBackground(Color.white);
      Stop.setForeground(Color.black);
      Stop.setEnabled(false);
      Stop.setFont(new Font("Tahoma", Font.BOLD, 15));
      Stop.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
        	if(e.getSource() == Stop) {
        		Start.setEnabled(true);
        		Listen.setEnabled(true);
        		Stop.setEnabled(false);  
        		vs.stopSaving();
        		recTimer.stop();
        		timeField.setText("        0 seconds");
        		counter = 0;
        	    window.dispose();
        	    new Main();
        	}
         }
      });
      if(new File("sample.wav").exists()) {
    	  Listen.setEnabled(true);
      }else {
    	  Listen.setEnabled(false);
      }
      Listen.setFocusable(false);
      Listen.setBounds(266, 47, 89, 42);
      Listen.setBackground(Color.white);
      Listen.setForeground(Color.black);
      Listen.setFont(new Font("Tahoma", Font.BOLD, 15));
      Listen.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    if(e.getSource() == Listen) {
                Listen.setEnabled(false);
                Delete.setEnabled(false);
		    	vs.listen("sample.wav");
		    }
		  }
      });
      Delete.setFocusable(false);
      Delete.setBounds(366, 47, 89, 42);
      Delete.setBackground(Color.white);
      Delete.setForeground(Color.black);
      Delete.setFont(new Font("Tahoma", Font.BOLD, 15));
      if(new File("sample.wav").exists()) {
    	  Delete.setEnabled(true);
      }else {
    	  Delete.setEnabled(false);
      }
      Delete.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		    if(e.getSource() == Delete) {
		    	new File("sample.wav").delete();
		    	Delete.setEnabled(false);
		    	window.dispose();
		    	new Main();
		    }
		  }
      });
      timeField.setFocusable(false);
      timeField.setEditable(false);
      timeField.setFont(new Font("Times New Roman", Font.BOLD, 22));
      timeField.setBackground(new Color(0, 100, 0));
      timeField.setForeground(Color.white);
      timeField.setBounds(141, 100, 200, 42);
      
      authorLabel.setFocusable(false);
      authorLabel.setFont(new Font("Verdana", Font.BOLD, 15));
      authorLabel.setBackground(Color.white);
      authorLabel.setForeground(Color.black);
      authorLabel.setBounds(35, 150, 500, 42);
      authorLabel.addMouseListener(new MouseAdapter() {
    	  @Override
    	  public void mouseClicked(MouseEvent e) {
    		 if(e.getSource() == authorLabel) { 
    			 try {
					Desktop.getDesktop().browse(new URI("https://github.com/IosebiGames/MicroMemo"));
				 }catch(IOException | URISyntaxException ex) {
				     System.out.println("Can't show Repository: " + ex.getMessage());
				}
    		 }
    	  }
      });
      window.getContentPane().add(titleText);
      window.getContentPane().add(Start);
      window.getContentPane().add(Stop);
      window.getContentPane().add(Listen);
      window.getContentPane().add(Delete);
      window.getContentPane().add(timeField);
      window.getContentPane().add(authorLabel);
      window.setVisible(true);
   }
}