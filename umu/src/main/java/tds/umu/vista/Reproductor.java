package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.Video;
import tds.video.VideoWeb;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;

public class Reproductor extends JPanel {

	JLabel nombreVideo;
	JLabel reproducciones;
	Controlador controlador= Controlador.getUnicaInstancia();
	VideoWeb videoWeb= Controlador.getUnicaInstancia().getReproductor();
	
	public Reproductor() {
		
		setLayout(new GridBagLayout());
		GridBagConstraints gbc= new GridBagConstraints();
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.fill= GridBagConstraints.HORIZONTAL;
		
		nombreVideo= new JLabel();
		reproducciones=new JLabel();
		add(nombreVideo,gbc);
		add(Box.createRigidArea(new Dimension(10,20)),gbc);
		add(reproducciones,gbc);
		add(Box.createRigidArea(new Dimension(10,20)),gbc);
		add(videoWeb,gbc);		
		

	}
	
	public void reproducir() {
		Video v= controlador.getVideoActual();
		nombreVideo.setText(v.getTitulo());
		reproducciones.setText("Visualizaciones: "+v.getNumReproducciones());
		videoWeb.playVideo(v.getUrl());
	}

	
}
