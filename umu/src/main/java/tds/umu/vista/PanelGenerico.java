package tds.umu.vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;


import javax.swing.*;
import javax.swing.border.BevelBorder;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.Video;
import tds.video.VideoWeb;

public abstract class PanelGenerico extends JPanel{
	/*Este tipo de panel nos sirve para tener una instancia que luego irá convirtiéndose en el tipo de panel que necesitemos. De esta forma, 
	 * no repetiremos código de manera innecesaria*/
	protected JPanel panel_lateral,panel_superior,panel_inferior,panel_3,panel_4,panel_5;
	private JList<VideoRepresent> lista_videos;
	protected JComboBox<String> comboBox;
	private Reproductor reproductor;
	private JButton bCancelar;
	protected JButton bReproducir;
	protected Controlador controlador= Controlador.getUnicaInstancia();
	protected List<Video> videos_relleno= new LinkedList<Video>();
	private DefaultListModel<VideoRepresent> modeloListaVideos= new DefaultListModel<VideoRepresent>();
	private VideoWeb videoWeb= controlador.getReproductor();
	

	
	private JLabel etiquetaSeleccion;
	
	public PanelGenerico() {

		
		reproductor=Reproductor.getUnicaInstancia();
		crearPantalla();
	}
	
	public void crearPantalla()
	{
		//Disposición del panel genérico
		setBackground(Color.GRAY);
		setLayout(new BorderLayout(0, 0));
		
		panel_lateral = new JPanel();
		panel_lateral.setPreferredSize(new Dimension(Constantes.TAM_PANEL_LATERAL_GENRICO_ANCHO,0));
		panel_lateral.setBackground(Color.GRAY);
		add(panel_lateral, BorderLayout.WEST);
		panel_lateral.setLayout(new BorderLayout(0, 0));
		
		panel_superior = new JPanel();
		panel_superior.setBackground(Color.GRAY);
		panel_superior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_lateral.add(panel_superior, BorderLayout.NORTH);
		panel_superior.setLayout(new BoxLayout(panel_superior, BoxLayout.Y_AXIS));
		
		etiquetaSeleccion = new JLabel();
		etiquetaSeleccion.setAlignmentX(Component.CENTER_ALIGNMENT);
		etiquetaSeleccion.setForeground(Color.WHITE);
		panel_superior.add(etiquetaSeleccion);
		
		comboBox = new JComboBox<String>();
		panel_superior.add(comboBox);
		comboBox.setVisible(false);
		
		
		
		panel_4=new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel_4.setLayout(new BorderLayout(0, 0));
		bReproducir = new JButton("Reproducir");
		bReproducir.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		//Manejador que selecciona un vídeo de una lista de vídeos
		bReproducir.addMouseListener(
	    		new MouseAdapter() {
	    			public void mouseClicked(MouseEvent event) {
	    				VideoRepresent selected= (VideoRepresent) lista_videos.getSelectedValue();
	    				if(selected!=null) {
	    						Video v= selected.getVideo();
	    						controlador.actualizarVideoSeleccionado(v);
	    						cambiarPanelRep();
	    						actualizarPanelLateral();
	    			}	
	    			}
	    		});
	
		
		
		panel_4.add(bReproducir, BorderLayout.NORTH);
		panel_superior.add(panel_4);
		panel_inferior = new JPanel();
		panel_inferior.setBackground(Color.GRAY);
		panel_inferior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_inferior.setLayout(new BorderLayout());
		
		bCancelar = new JButton("Cancelar");
		
		//Manejador que gestiona el hecho de clicar para cancelar una selección
		bCancelar.addMouseListener(
	    		new MouseAdapter() {
	    			public void mouseClicked(MouseEvent event) {
	    					cancelarVideo();
	    			
	    			}
	    		});
		
		panel_inferior.add(bCancelar,BorderLayout.NORTH);
		panel_lateral.add(panel_inferior, BorderLayout.SOUTH);
		
		lista_videos = new JList<VideoRepresent>();
	    lista_videos.setBackground(Color.GRAY);
	    lista_videos.setForeground(Color.WHITE);
	    lista_videos.setVisibleRowCount(-1);
	    lista_videos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    lista_videos.setModel(modeloListaVideos);
	    lista_videos.setCellRenderer(new VideoRenderer());
	    panel_lateral.add(new JScrollPane(lista_videos));
	    
	}
	/*Funciones auxiliares que actualizan los diferentes paneles, y gestionan sus elementos */
	public void actualizarPanelLateral() {
		modeloListaVideos.removeAllElements();
		videos_relleno= metodoRelleno();
	    videos_relleno.stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloListaVideos.addElement(nv));
	    validate();
		
	}
	
	protected void cambiarPanelRep() {
			add(reproductor,BorderLayout.CENTER);
			reproductor.reproducir();
			revalidate();
			repaint();
			validate();

	}
	
	
	public void clean() {
		modeloListaVideos.removeAllElements();
		comboBox.removeAllItems();
		cancelarVideo();
	}
	
	
	protected void setTextoEtiqueta(String texto) {
		etiquetaSeleccion.setText(texto);
	}
	
	protected void cancelarVideo() {
		reproductor.cancelarVideo();
		remove(reproductor);	
		revalidate();
		repaint();
		validate();

		
	}
	
//funciones abstractas que dependiendo del tipo de panel al que se derive, harán una función u otra
	public abstract void rellenarPantalla();
	public abstract List<Video> metodoRelleno();
	
	
}