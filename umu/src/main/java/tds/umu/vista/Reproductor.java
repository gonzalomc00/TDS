package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.Video;
import tds.video.VideoWeb;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.List;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

//SOLO PUEDE EXISTIR UNA INSTANCIA DE REPRODUCTOR. SI NO, NO SE VE BIEN. 
public class Reproductor extends JPanel {

	private static Reproductor unicaInstancia= null;
	private JLabel reproducciones;
	
	private Controlador controlador= Controlador.getUnicaInstancia();
	private VideoWeb videoWeb= Controlador.getUnicaInstancia().getReproductor();
	private JPanel p;
	private JPanel panel;

	private JPanel panelEtiq;

	private JLabel tit;
	private JTextField textField;
	private JButton anadir;
	private Component verticalStrut;
	private JList<String> listaetiquetas;
	private DefaultListModel<String> modeloListaEtiquetas= new DefaultListModel<String>();
	private Component verticalStrut_1;
	private JLabel nombreVideo;
	private Component verticalStrut_2;

	
	
	public Reproductor() {
		setBackground(Color.LIGHT_GRAY);
	    setLayout(new BorderLayout());
		
		
		setBackground(Color.GRAY);
		
		reproducciones=new JLabel();
		reproducciones.setText("Visualizaciones");
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		verticalStrut_1 = Box.createVerticalStrut(20);
		add(verticalStrut_1, BorderLayout.NORTH);
		
		nombreVideo = new JLabel();
		nombreVideo.setText("Titulo de un video");
		nombreVideo.setFont(new Font("Verdana", Font.BOLD, 15));
		nombreVideo.setAlignmentX(0.5f);
		add(nombreVideo, BorderLayout.NORTH);
		
		verticalStrut_2 = Box.createVerticalStrut(20);
		add(verticalStrut_2, BorderLayout.NORTH);
		add(reproducciones);
		
		p= new JPanel();
		p.setBackground(Color.GRAY);
		p.add(videoWeb);
		add(p);
		panel = new JPanel();
		panel.setBackground(Color.GRAY);
	
		
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
		panelEtiq = new JPanel();
		panelEtiq.setBackground(Color.GRAY);
		listaetiquetas = new JList<String>();
		listaetiquetas.setBorder(new SoftBevelBorder(BevelBorder.RAISED, Color.CYAN, null, Color.DARK_GRAY, null));
		listaetiquetas.setBackground(Color.GRAY);
		listaetiquetas.setForeground(Color.BLACK);
		listaetiquetas.setVisibleRowCount(1);
		listaetiquetas.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listaetiquetas.setModel(modeloListaEtiquetas);
		panelEtiq.add(listaetiquetas);
		
		add(panel);
		add(panelEtiq);
	
		
	}
	
	public static Reproductor getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Reproductor();
		return unicaInstancia;
	}
	
	public void reproducir() {
		modeloListaEtiquetas.removeAllElements();
		Video v= controlador.getVideoActual();
		nombreVideo.setText(v.getTitulo());
		reproducciones.setText("Reproducciones: "+v.getNumReproducciones());
		List<String> listaetiquetas= v.obtenerEtiquetas();
		listaetiquetas.stream().forEach(str->modeloListaEtiquetas.addElement(str));
		videoWeb.playVideo(v.getUrl());
		
	}

	
}

