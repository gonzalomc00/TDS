package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;

import tds.umu.controlador.Controlador;
import tds.video.VideoWeb;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Reproductor extends JPanel {

	JLabel nombreVideo;
	VideoWeb videoWeb= Controlador.getUnicaInstancia().getReproductor();
	
	public Reproductor() {
		add(videoWeb, BorderLayout.CENTER);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblNewLabel = new JLabel("New label");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 0);
		gbc_lblNewLabel.gridx = 5;
		gbc_lblNewLabel.gridy = 3;
		add(lblNewLabel, gbc_lblNewLabel);
		
		JPanel panel = new JPanel();
		GridBagConstraints gbc_panel = new GridBagConstraints();
		gbc_panel.fill = GridBagConstraints.BOTH;
		gbc_panel.gridx = 5;
		gbc_panel.gridy = 4;
		panel.add(videoWeb);
		add(panel, gbc_panel);
		
	    videoWeb.playVideo("https://www.youtube.com/watch?v=4mYBiIO0pfY");

	}

}
