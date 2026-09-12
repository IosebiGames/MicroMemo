package com.java.IosebiGames.App;

import javax.swing.SwingUtilities;
import com.formdev.flatlaf.FlatLightLaf;

public class Executor {
	 public static void main(String[] args) {
		 FlatLightLaf.setup();
         SwingUtilities.invokeLater(Main :: new);
     }
}