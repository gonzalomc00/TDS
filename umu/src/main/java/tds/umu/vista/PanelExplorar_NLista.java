package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.CatalogoEtiquetas;
import tds.umu.modelo.CatalogoVideos;
import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Video;
import tds.umu.modelo.VideoRepresent;

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import tds.video.VideoWeb;
import javax.swing.JTable;

/*
 * Debido a que el panel Explorar y el panel Nuevas Listas tienen una estructura común y utilizan muchos métodos similares, o incluso iguales, hemos decidido unificar ambos paneles en uno solo.
 * Para cambiar de un "modo" a otro, ocultamos uno de los 3 paneles que contiene este panel. Cuando lo utilizamos el panel en modo Explorar, ocultamos el panel izquierdo mientras que en el modo
 * Nueva Lista ocultamos el panel derecho.  
 * 
 * Para visualizar de forma correcta el contenido de esta ventana en el modo Explorar, deberemos recargarla justo después de cargar los vídeos, en el caso de que no lo estuvieran previamente. 
 */
public class PanelExplorar_NLista extends JPanel {
	private JPanel panel_etiquetas,panelBusqueda,panelControl,panel_control_superior,panel_control_inferior,panel_etiquetas_disponibles,panel_lista_etiquetas_disp,panel_etiquetas_selec,panel_lista_etiquetas_selec,panel_resultados;
	private JPanel panel_listas,panel_lista_supeior,panel_listas_inferior,panel_eliminar_lista,panel_listas_busqueda,panel_3;
	private JButton n_busqueda,boton_buscar,bEliminar,bBuscar_lista,bAñadir,bQuitar;
	private JLabel etiq_titulo,etiquetas_disp,b_etiquetas_selec,etiquetaSeleccion,etiquetaNomLista;
	private JTextField barra_busqueda,campoNombre;
	private JList lista_etiquetas,lista_etiquetas_sel,lista_videos,videos_lista;
	private VideoWeb videoWeb=Controlador.getUnicaInstancia().getReproductor();
	private List<Video> videos_encontrados;



	//VARIABLES PROPIAS DE EXPLORAR
	private LinkedList<String> etiquetas_Sel_Lista= new LinkedList<String>();
	private DefaultListModel<String> modeloEtiqDisponibles = new DefaultListModel<String>();
	private DefaultListModel<String> modeloEtiqueSeleccionadas= new DefaultListModel<String>();
	
	//VARIABLES PROPIAS DE UNA BUSQUEDA
	private DefaultListModel<VideoRepresent> modeloTablaVideos= new DefaultListModel<VideoRepresent>();
	
	//VARIABLES PROPIAS DE LA VENTANA NUEVAS LISTAS
	private ListaVideos lv_creada;
	private DefaultListModel<VideoRepresent> modeloVideosLista= new DefaultListModel<VideoRepresent>();

	//Ya que no queremos que en el modo Nueva Lista se reproduzcan vídeos cuando hacemos doble click en los resultados de la búsqueda, añadiremos el MouseListener únicamente cuando 
	//utilicemos el panel en modo Explorar. 
	private MouseListener event;
	private boolean activo;
	private Controlador controlador= Controlador.getUnicaInstancia();
	private JPanel panel_nombre;
	

	/* Constructor del panel.*/
	public PanelExplorar_NLista(VentanaPrincipal ventana) {
		this.activo=false;
		crearPantalla();
		
		//Inicializamos el MouseListener anteriormente descrito. Al hacer doble click sobre un vídeo se llamará a la Ventana Principal para que actualice el contenido del panel central de la 
		//aplicación para que muestre el reproductor con el vídeo que hemos seleccionado. 
		event=new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {
				if(event.getClickCount()==2) {
					JList<VideoRepresent> source=(JList<VideoRepresent>) event.getSource();
					VideoRepresent selected = source.getSelectedValue();
					if(selected!=null) {
						controlador.actualizarVideoSeleccionado(selected.getVideo());
						ventana.cambioPanel(Paneles.REPRODUCTOR);
				}
			}	
			}
		};
	}

	/*Metodo el cual llama a las distintas funciones para contruir el contenido de la ventana. . */
	private void crearPantalla() {
		setBackground(Color.LIGHT_GRAY);
	    setLayout(new BorderLayout(0, 0));
	    
	    construirPanelListaVideos();
	    construirPanelBusqueda();
	    construirPanelEtiquetas();  
	    
	}
	
	
	/*Método para construir la vista del panel que contiene las listas de vídeos. Este panel solo estará visible en el modo Nueva Lista.*/
	private void construirPanelListaVideos() {
		
		/*DISPOSICIÓN DE ELEMENTOS EN EL PANEL DE LA LISTA DE VÍDEOS*/
		panel_listas= new JPanel();
		panel_listas.setPreferredSize(new Dimension(Constantes.TAM_PANEL_LATERAL_ANCHO,200));
		panel_listas.setBackground(Color.GRAY);
		add(panel_listas, BorderLayout.WEST);
		panel_listas.setLayout(new BorderLayout(0, 0));
		
		panel_lista_supeior = new JPanel();
		panel_lista_supeior.setBackground(Color.GRAY);
		panel_lista_supeior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_listas.add(panel_lista_supeior, BorderLayout.NORTH);
		panel_lista_supeior.setLayout(new BoxLayout(panel_lista_supeior, BoxLayout.Y_AXIS));
		
		etiquetaSeleccion = new JLabel("Introducir nombre lista:");
		etiquetaSeleccion.setAlignmentX(Component.CENTER_ALIGNMENT);
		etiquetaSeleccion.setForeground(Color.WHITE);
		panel_lista_supeior.add(etiquetaSeleccion);
		
		panel_listas_busqueda = new JPanel();
		panel_listas_busqueda.setBackground(Color.GRAY);
		panel_lista_supeior.add(panel_listas_busqueda);
		panel_listas_busqueda.setLayout(new BoxLayout(panel_listas_busqueda, BoxLayout.X_AXIS));
		
		campoNombre = new JTextField();
		panel_listas_busqueda.add(campoNombre);
		
		bBuscar_lista = new JButton("Buscar");
	
		panel_listas_busqueda.add(bBuscar_lista);
		
		panel_eliminar_lista = new JPanel();
		panel_eliminar_lista.setBackground(Color.GRAY);
		panel_lista_supeior.add(panel_eliminar_lista);
		
		bEliminar = new JButton("Eliminar");
		panel_eliminar_lista.add(bEliminar);
		
		panel_nombre = new JPanel();
		panel_nombre.setBackground(Color.GRAY);
		panel_lista_supeior.add(panel_nombre);
		etiquetaNomLista= new JLabel("");
		etiquetaNomLista.setForeground(Color.WHITE);
		panel_nombre.add(etiquetaNomLista);
		
		panel_listas_inferior = new JPanel();
		panel_listas_inferior.setBackground(Color.GRAY);
		panel_listas_inferior.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_listas.add(panel_listas_inferior, BorderLayout.SOUTH);
		panel_listas_inferior.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		

		bAñadir = new JButton("Añadir");
		bAñadir.setHorizontalAlignment(SwingConstants.LEFT);
		panel_listas_inferior.add(bAñadir);
		bQuitar = new JButton("Quitar");
		
		bQuitar.setAlignmentX(Component.CENTER_ALIGNMENT);
		bQuitar.setHorizontalAlignment(SwingConstants.LEADING);
		panel_listas_inferior.add(bQuitar);
		
		
		videos_lista = new JList<VideoRepresent>();
	    videos_lista.setBackground(Color.GRAY);
	    videos_lista.setForeground(Color.WHITE);
	    videos_lista.setVisibleRowCount(-1);
	    videos_lista.setModel(modeloVideosLista);
	    videos_lista.setCellRenderer(new VideoRenderer());
		panel_listas.add(new JScrollPane(videos_lista));
	
		
		//MANEJADORES
		/*
		 * Al hacer click en este botón se buscará una lista de vídeo que pertenezca al usuario o se creará en su defecto.
		 */
		bBuscar_lista.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actualizacionPlaylist();
			}
			
		});
		/*
		 * Al hacer click en este botón se preguntará al usuario si quiere eliminar la lista que está actualmente seleccionada. 
		 */
		bEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lv_creada==null) {
					  JOptionPane.showMessageDialog(null, "Debes tener una lista de videos seleccionada",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
					int input= JOptionPane.showConfirmDialog(null,"¿Quieres eliminar la lista de videos "+ lista_videos.getName()+"?","Borrar lista",JOptionPane.YES_NO_OPTION);
					if(input==0)
						eliminarLista();
				}
			}
		});
		
		/*
		 * Al hacer click en este botón añadimos a la lista de video seleccionada el video seleccionado en la búsqueda. 
		 */
		bAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VideoRepresent selected = (VideoRepresent) lista_videos.getSelectedValue();
				if(lv_creada!=null && selected!=null) {
					Video v_sel= selected.getVideo();
					controlador.añadirVideoPlaylist(lv_creada,v_sel);
					actualizarVideosLista();
				}
			}
		});
		/*
		 *  Al hacer click en este botón eliminamos de la lista de video seleccionada el video seleccionado en la búsqueda. 
		 */
		bQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				VideoRepresent selected = (VideoRepresent) videos_lista.getSelectedValue();
				if(lv_creada!=null && selected!=null) {
					Video v_sel= selected.getVideo();
					controlador.eliminarVideoLista(lv_creada,v_sel);
					actualizarVideosLista();
				}
			}
		});
		
		
		
	}
/*Método para construir la vista del panel que contiene las etiquetas, el cual solo será visible en el modo Explorar. */
	public void construirPanelEtiquetas() {

		/*DISPOSICIÓN DE ELEMENTOS EN EL PANEL DE ETIQUETAS*/
		    panel_etiquetas = new JPanel();
		    panel_etiquetas.setBackground(Color.GRAY);
		    panel_etiquetas.setBorder(new LineBorder(new Color(0, 0, 0)));
			panel_etiquetas.setPreferredSize(new Dimension(Constantes.TAM_PANEL_LATERAL_ANCHO,0));
		    add(panel_etiquetas, BorderLayout.EAST);
		    panel_etiquetas.setLayout(new BoxLayout(panel_etiquetas, BoxLayout.Y_AXIS));
		   
		    panel_etiquetas_disponibles = new JPanel();
		    panel_etiquetas_disponibles.setBackground(Color.GRAY);
		    panel_etiquetas.add(panel_etiquetas_disponibles);
		    
		    etiquetas_disp = new JLabel("Etiquetas disponibles");
		    etiquetas_disp.setForeground(Color.WHITE);
		    panel_etiquetas_disponibles.add(etiquetas_disp);
		    
		    panel_lista_etiquetas_disp = new JPanel();
		    panel_lista_etiquetas_disp.setBackground(Color.GRAY);
		    panel_etiquetas.add(panel_lista_etiquetas_disp);
		    
		    lista_etiquetas = new JList();
		    lista_etiquetas.setModel(modeloEtiqDisponibles);
		    lista_etiquetas.setFixedCellWidth(120);
		   

		
		    panel_lista_etiquetas_disp.add(new JScrollPane(lista_etiquetas));
		    
		    
		  
		    panel_etiquetas_selec = new JPanel();
		    panel_etiquetas_selec.setBackground(Color.GRAY);
		    panel_etiquetas.add(panel_etiquetas_selec);
		    
		    b_etiquetas_selec = new JLabel("Buscar etiquetas");
		    b_etiquetas_selec.setForeground(Color.WHITE);
		    panel_etiquetas_selec.add(b_etiquetas_selec);
		    
		    panel_lista_etiquetas_selec = new JPanel();
		    panel_lista_etiquetas_selec.setBackground(Color.GRAY);
		    panel_etiquetas.add(panel_lista_etiquetas_selec);
		    
		    lista_etiquetas_sel = new JList();
		    lista_etiquetas_sel.setModel(modeloEtiqueSeleccionadas);
		    lista_etiquetas_sel.setFixedCellWidth(120);
		    panel_lista_etiquetas_selec.add(new JScrollPane(lista_etiquetas_sel));
		    
		    
		    //MANEJADORES
		    
		    /*
		     * Si seleccionamos una etiqueta de la primera lista de etiquetas y esta no se encuentra ya seleccionada, entonces la añadimos a la segunda lista de etiquetas,
		     * la cual filtrará la búsqueda. 
		     */
		    lista_etiquetas.addListSelectionListener(
		    		new ListSelectionListener() {
		    			public void valueChanged(ListSelectionEvent event) {
		    				
		    				if(!event.getValueIsAdjusting()) {
		    					JList<String> source=(JList<String>) event.getSource();
		    					String selected = source.getSelectedValue();
		    					if(selected!=null && !modeloEtiqueSeleccionadas.contains(selected)) {
		    					modeloEtiqueSeleccionadas.addElement(selected);
		    					etiquetas_Sel_Lista.add(selected);
		    					}
		    				}
		    			}	
		    		}	
		    		);
		    
		    /*
		     * Si seleccionamos una etiqueta de la segunda lista de etiquetas la eliminamos de ella. 
		     */
		    lista_etiquetas_sel.addListSelectionListener(
		    		new ListSelectionListener() {
		    			public void valueChanged(ListSelectionEvent event) {
		    				
		    				if(!event.getValueIsAdjusting()) {
		    					JList<String> source=(JList<String>) event.getSource();
		    					String selected = source.getSelectedValue();
		    					if(selected!=null) {
		    						modeloEtiqueSeleccionadas.removeElement(selected);
		    						etiquetas_Sel_Lista.remove(selected);
		    					}
		    					
		    				}
		    			}	
		    		}	
		    		);
		    
		    
	}
	
	/*Método para construir el panel que contendrá los componentes para realizar la búsqueda, visible en ambos modos.*/
	public void construirPanelBusqueda() {

		/*DISPOSICIÓN DE ELEMENTOS EN EL PANEL DE BÚSQUEDA*/
		panelBusqueda = new JPanel();
	    panelBusqueda.setBackground(Color.GRAY);
	    add(panelBusqueda, BorderLayout.CENTER);
	    panelBusqueda.setLayout(new BorderLayout(0, 0));
	    
	    panelControl = new JPanel();
	    panelControl.setBackground(Color.GRAY);
	    panelControl.setBorder(new LineBorder(new Color(0, 0, 0)));
	    panelBusqueda.add(panelControl, BorderLayout.NORTH);
	    panelControl.setLayout(new BoxLayout(panelControl, BoxLayout.Y_AXIS));
	    
	    panel_control_superior = new JPanel();
	    panel_control_superior.setBackground(Color.GRAY);
	    panelControl.add(panel_control_superior);
	    
	    etiq_titulo = new JLabel("Buscar titulo");
	    etiq_titulo.setForeground(Color.WHITE);
	    panel_control_superior.add(etiq_titulo);
	    
	    barra_busqueda = new JTextField();
	    panel_control_superior.add(barra_busqueda);
	    barra_busqueda.setColumns(20);
	    
	    boton_buscar = new JButton("Buscar");
	   
	    panel_control_superior.add(boton_buscar);
	    
	    panel_control_inferior = new JPanel();
	    panel_control_inferior.setBackground(Color.GRAY);
	    panelControl.add(panel_control_inferior);
	    
	    n_busqueda = new JButton("Nueva búsqueda");
	    panel_control_inferior.add(n_busqueda);
	    
	    panel_resultados = new JPanel();
	    panel_resultados.setLayout(new BorderLayout());
	    panel_resultados.setBackground(Color.GRAY);
	    panelBusqueda.add(panel_resultados, BorderLayout.CENTER);
	    
	    
	 
	    lista_videos = new JList();
	    lista_videos.setBackground(Color.GRAY);
	    lista_videos.setForeground(Color.WHITE);
	    lista_videos.setVisibleRowCount(-1);
	    lista_videos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    lista_videos.setModel(modeloTablaVideos);
	    lista_videos.setCellRenderer(new VideoRenderer());
	    
	    
	    panel_resultados.add(new JScrollPane(lista_videos),BorderLayout.CENTER);
	    
	    
	    //MANEJADORES
	    /*
	     * Al hacer click en este botón eliminaremos todos los resultados de la búsqueda. 
	     */
	    n_busqueda.addMouseListener(new MouseAdapter() {
	    	
	    	public void mouseClicked(MouseEvent e) {
	    		modeloTablaVideos.removeAllElements();
	    	}
	    });
	    /*
	     * Al hacer click en este botón iniciaremos la búsqueda. 
	     */
	    boton_buscar.addMouseListener(new MouseAdapter(){
	    	
	    	public void mouseClicked(MouseEvent e) {
	    		actualizarListaVideos();
	    	}
	    	
	    });
	    

	}
		
	
	/*
	 * Métodos para actualizar el panel central de búsqueda con los resultados de la misma. Se construirá un objetos VideoRepresent por cada vídeo encontrado 
	 * y entonces se añadirá a la lista para que puedan ser representados. 
	 *
	 * La búsqueda estarña filtrada con las etiquetas seleccionadas en el panel derecho ( en el caso del modo Nueva Lista no habrá ninguna seleccionada por defecto, pues en este modo
	 * no queremos filtrar por etiquetas)
	 */
	public void actualizarListaVideos() {
		modeloTablaVideos.removeAllElements();
	    videos_encontrados= controlador.obtenerBusqueda(barra_busqueda.getText(),etiquetas_Sel_Lista);
	    videos_encontrados.stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloTablaVideos.addElement(nv));
	    validate();
	    
	}
	/*
	 * Método utilizado para cargar las listas del panel derecho con todas las etiquetas encontradas en la base de datos. Como de las etiquetas solo nos interesa su nombre,
	 * hacemos un stream para pasar del objeto etiqueta a un String con su nombre.  
	 */
	public void actualizarEtiquetasExplora() {
		List <Etiqueta>lista_etiquetas_cat= controlador.getEtiquetas();
		lista_etiquetas_cat.stream()
					   .map(etq->etq.getNombre())
					   .forEach(nom->modeloEtiqDisponibles.addElement(nom));
		
		
	}
	
	//METODOS DE PANEL NUEVA LISTA

	/*
	 * Método utilizado para actualizar el contenido del panel izquierdo. Si el usuario ha introducido el nombre de una playlist y esta no existe, se procederá a preguntar si quiere crearla
	 * y la deja seleccionada por si quiere introducir vídeos en ella. En caso de existir, se seleccionará la lista y se actualizará el panel para mostrar todos los vídeos que contiene. 
	 */
	private void actualizacionPlaylist() {
		if(!campoNombre.getText().equals("")) {
		lv_creada=controlador.obtenerLista(campoNombre.getText());
		//Si el nombre de la playList introducida no existe entonces se produce a preguntar si se quiere crear,
		//y hacerlo si corresponde. 
		if(lv_creada==null) {
			int res = JOptionPane.showConfirmDialog(this, "¿Quieres crear la lista de videos "+campoNombre.getText()+"?",
					"Crear lista de video",JOptionPane.YES_NO_CANCEL_OPTION);
			
			if(res==JOptionPane.YES_OPTION)
				lv_creada=controlador.crearLista(campoNombre.getText());
			else
				return;
		}
		etiquetaNomLista.setText("Lista: "+lv_creada.getNombre());
		actualizarVideosLista();
		}
		else {
			JOptionPane.showMessageDialog(this, "Debes introducir el nombre de una lista de video nueva o existente",
					"Error", JOptionPane.ERROR_MESSAGE);
		}
			
	}
	
	/*
	 * Método utilizado para actualizar el panel izquierdo con todos los vídeos que contiene una lista de vídeo. 
	 */
	private void actualizarVideosLista() {
		modeloVideosLista.removeAllElements();
	    lv_creada.getVideos().stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloVideosLista.addElement(nv));
	    validate();
		
		
	}
	
	/*Método para eliminar listas de vídeos*/
	private void eliminarLista() {
		controlador.borrarLista(lv_creada);
		modeloVideosLista.removeAllElements();
		lv_creada=null;
		etiquetaNomLista.setText("");
	}
	
/*------------------funciones auxiliares de mantenimiento-------------*/
	
	/*
	 * En el modo Explorar ocultamos el panel izquierdo y añadimos el MouseListener que permite reproducir un vídeo si se hace doble click sobre él en la lista. Para evitar
	 * problemas por si se selecciona dos veces seguidas el panel Explorar, comprobamos que este no esté ya introducido.
	 */
	public void modoExplorar() {
		panel_listas.setVisible(false);
		panel_etiquetas.setVisible(true);
		if(activo==false)
			lista_videos.addMouseListener(event);
			activo=true;
	}
	
	/*
	 * En el modo Nueva Lista ocultamos el panel derecho y mostramos el izquierdo, a la vez que eliminamos el Mouse Listener que permite la visualización con doble click. 
	 */
	public void modoNuevaLista() {
		panel_listas.setVisible(true);
		panel_etiquetas.setVisible(false);
		lista_videos.removeMouseListener(event);
		activo=false;
	}

	/*
	 * Método utilizado para limpiar todos los elementos del panel: la lista de etiquetas y etiquetas seleccionada, la búsqueda, la lista seleccionada... Este método será llamado 
	 * siempre que accedamos al panel. 
	 */
	public void renovar() {
		modeloEtiqDisponibles.removeAllElements();
		modeloEtiqueSeleccionadas.removeAllElements();
		modeloTablaVideos.removeAllElements();
		etiquetas_Sel_Lista.clear();
		
		modeloVideosLista.removeAllElements();
		campoNombre.setText("");
		etiquetaNomLista.setText("");
		
	}
	

}
