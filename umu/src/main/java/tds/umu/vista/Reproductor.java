package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.Video;
import tds.video.VideoWeb;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Font;
import javax.swing.SwingConstants;

public class Reproductor extends JPanel {

	private JLabel nombreVideo;
	private JLabel reproducciones;
	
	private Controlador controlador= Controlador.getUnicaInstancia();
	private VideoWeb videoWeb= Controlador.getUnicaInstancia().getReproductor();
	private VentanaPrincipal ventana;
	private JPanel p;
	private JPanel panel;
	private JLabel tit;
	private JTextField textField;
	private JButton anadir;
	private Component verticalStrut;
	
	public Reproductor(VentanaPrincipal v) {
		ventana=v;
		setBackground(Color.LIGHT_GRAY);
	    setLayout(new BorderLayout());
		
		
		setBackground(Color.GRAY);
		
		nombreVideo= new JLabel();
		
		nombreVideo.setHorizontalAlignment(SwingConstants.CENTER);
		
		nombreVideo.setFont(new Font("Verdana", Font.BOLD, 15));
		nombreVideo.setText("Titulo de un video");
		reproducciones=new JLabel();
		reproducciones.setText("Visualizaciones");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(nombreVideo);
		add(reproducciones);
		add(videoWeb);
		
		panel = new JPanel();
		panel.setBackground(Color.GRAY);
		add(panel);
		
		verticalStrut = Box.createVerticalStrut(50);
		panel.add(verticalStrut);
		
		tit = new JLabel("Nueva etiqueta:");
		tit.setForeground(Color.WHITE);
		panel.add(tit);
		
		textField = new JTextField();
		textField.setColumns(40);
		panel.add(textField);
		
		anadir = new JButton("Añadir");
		panel.add(anadir);
		
		
	}
	
	public void reproducir() {
		Video v= controlador.getVideoActual();
		nombreVideo.setText(v.getTitulo());
		reproducciones.setText("Visto por: "+v.getNumReproducciones()+ " usuarios");
		videoWeb.playVideo(v.getUrl());
		
	}

	
}
