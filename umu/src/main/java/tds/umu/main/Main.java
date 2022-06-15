package tds.umu.main;

import java.awt.EventQueue;

import javax.swing.UIManager;

import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;

import tds.umu.vista.VentanaPrincipal;

public class Main {
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				
				try {
					
					FlatLightLaf.setup();
					try {
					    UIManager.setLookAndFeel( new FlatIntelliJLaf() );
					} catch( Exception ex ) {
					    System.err.println( "Failed to initialize LaF" );
					}
				
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
