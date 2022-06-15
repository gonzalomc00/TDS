package tds.umu.vista;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;


import javax.swing.*;
import tds.umu.controlador.Controlador;
import tds.umu.modelo.Video;
import tds.umu.modelo.VideoRepresent;
import tds.video.VideoWeb;


/*
 * Clase genérica que permite muestra un panel lateral y permite utilizar el reproductor para visualizar videos. Esta clase contiene la mayoría de elementos que tendrán todas las clases hijas,
 * incluyendo componentes y métodos comunes. Algunas clases (como PanelMisListas) pueden introducir elementos únicos al panel, mientras que otros cambiarán el método para rellenar el panel lateral.
 * 
 * En un principìo teniamos pensado establecer el método para rellenar el panel lateral mediante un patrón Estrategia, pero viendo la complejidad de la clases PanelMisListas decidimos optar por realizar
 * esta clase abstracta.
 */
public abstract class PanelGenerico extends JPanel{
	protected JPanel panel_lateral,panel_superior,panel_inferior,panel_4;
	private JList<VideoRepresent> lista_videos;
	protected JComboBox<String> comboBox;
	private JLabel etiquetaSeleccion;
	private JButton bCancelar;
	protected JButton bReproducir;
	
	private Reproductor reproductor;
	protected Controlador controlador= Controlador.getUnicaInstancia();
	protected List<Video> videos_relleno= new LinkedList<Video>();

	private DefaultListModel<VideoRepresent> modeloListaVideos= new DefaultListModel<VideoRepresent>();
	private VideoWeb videoWeb= controlador.getReproductor();
	
	public PanelGenerico() {

		
		reproductor=Reproductor.getUnicaInstancia();
		crearPantalla();
	}
	
	public void crearPantalla()
	{
		//Disposición del panel genérico
		setLayout(new BorderLayout(0, 0));
		
		panel_lateral = new JPanel();
		panel_lateral.setBorder(UIManager.getBorder("Spinner.border"));
		panel_lateral.setPreferredSize(new Dimension(Constantes.TAM_PANEL_LATERAL_GENRICO_ANCHO,0));
		add(panel_lateral, BorderLayout.WEST);
		panel_lateral.setLayout(new BorderLayout(0, 0));
		
		panel_superior = new JPanel();
		panel_lateral.add(panel_superior, BorderLayout.NORTH);
		panel_superior.setLayout(new BoxLayout(panel_superior, BoxLayout.Y_AXIS));
		
		etiquetaSeleccion = new JLabel();
		etiquetaSeleccion.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_superior.add(etiquetaSeleccion);
		
		comboBox = new JComboBox<String>();
		panel_superior.add(comboBox);
		comboBox.setVisible(false);
		
		
		
		panel_4=new JPanel();
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
	    lista_videos.setVisibleRowCount(-1);
	    lista_videos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    lista_videos.setModel(modeloListaVideos);
	    lista_videos.setCellRenderer(new VideoRenderer());
	    panel_lateral.add(new JScrollPane(lista_videos));
	    
	}
	
	/*
	 * Método que actualiza el contenido de la barra lateral. Dependiendo de la clase en la que nos encontremos (MisListas,Recientes o MasVistos) el método variará. Obtiene
	 * todos los vídeos que se necesiten y entonces se construirán los objetos VideoRepresent que sean necesarios.
	 */
	public void actualizarPanelLateral() {
		modeloListaVideos.removeAllElements();
		videos_relleno= metodoRelleno();
	    videos_relleno.stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloListaVideos.addElement(nv));
	    validate();
		
	}
	
	/*
	 * Método para mostrar el reproductor cuando seleccionemos un vídeo.
	 */
	protected void cambiarPanelRep() {
			add(reproductor,BorderLayout.CENTER);
			reproductor.reproducir();
			revalidate();
			repaint();
			validate();

	}
	
	/*
	 * Método para limpiar el contenido de la barra lateral y el reproductor. Las clases hijas podrán redefinirlo según sus necesidades. 
	 */
	public void clean() {
		modeloListaVideos.removeAllElements();
		comboBox.removeAllItems();
		cancelarVideo();
	}
	
	/*
	 * Método para cambiar el texto de la etiqueta superior.
	 */
	protected void setTextoEtiqueta(String texto) {
		etiquetaSeleccion.setText(texto);
	}
	
	/*
	 * Método para quitar el reproductor cuando cancelemos un vídeo. 
	 */
	protected void cancelarVideo() {
		reproductor.cancelarVideo();
		remove(reproductor);	
		revalidate();
		repaint();
		validate();

		
	}
	
	/*
	 * Métodos que implementarán las subclases. 
	 */

	public abstract void rellenarPantalla();
	public abstract List<Video> metodoRelleno();
	
	
}