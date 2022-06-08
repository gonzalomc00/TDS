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

import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import tds.video.VideoWeb;
import javax.swing.JTable;

public class PanelExplorar_NLista extends JPanel {
	private JPanel panel_etiquetas,panelBusqueda,panelControl,panel_control_superior,panel_control_inferior,panel_etiquetas_disponibles,panel_lista_etiquetas_disp,panel_etiquetas_selec,panel_lista_etiquetas_selec,panel_resultados;
	private JPanel panel_listas,panel_lista_supeior,panel_listas_inferior,panel_eliminar_lista,panel_listas_busqueda,panel_3;
	private JButton n_busqueda,boton_buscar,bEliminar,bBuscar_lista,bAñadir,bQuitar;
	private JLabel etiq_titulo,etiquetas_disp,b_etiquetas_selec,etiquetaSeleccion,etiquetaNomLista;
	private JTextField barra_busqueda,campoNombre;
	private VentanaPrincipal ventana;
	private JList lista_etiquetas,lista_etiquetas_sel,lista_videos,videos_lista;
	private VideoWeb videoWeb=Controlador.getUnicaInstancia().getReproductor();
	private List<Video> videos_encontrados;



	//VARIABLE PROPIA DE EXPLORAR
	private LinkedList<String> etiquetas_Sel_Lista= new LinkedList<String>();
	private DefaultListModel<String> modeloEtiqDisponibles = new DefaultListModel<String>();
	private DefaultListModel<String> modeloEtiqueSeleccionadas= new DefaultListModel<String>();
	
	//VARIABLE PROPIAS DE UNA BUSQUEDA
	private DefaultListModel<VideoRepresent> modeloTablaVideos= new DefaultListModel<VideoRepresent>();
	
	//VARIABLES PROPIAS DE LA VENTANA NUEVAS LISTAS
	private ListaVideos lv_creada;
	private DefaultListModel<VideoRepresent> modeloVideosLista= new DefaultListModel<VideoRepresent>();

	
	
	private MouseListener event;
	private boolean activo;
	private Controlador controlador= Controlador.getUnicaInstancia();
	private JPanel panel_nombre;
	

	/**
	 * Create the panel.
	 */
	
	
	/*
	 * Para enviar los videos al controlador podemos hacerlo de manera directa pues previamente los recuperamos para poder
	 * representarlos en las listas. Como estas listas no varian entre busqueda y busquedas, los indices de las listas
	 * (la que contiene las representaciones de los videos y la que contiene los videos en si) coinciden. Sin embargo, con las
	 * etiquetas no se puede hacer esto para enviarlas al controlador. Esto se debe a que los indices de las etiquetas seleccionadas
	 * no coinciden con el indice de las etiquetas encontradas, pues son un solo conjunto cambiante a lo largo de toda la ejecución
	 * 
	 * Luego en el controlador si que podemos hacer el paso de nombre de etiqueta a etiqueta
	 */
	public PanelExplorar_NLista(VentanaPrincipal ventana) {
		this.ventana=ventana;
		this.activo=false;
		crearPantalla();
		
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

	private void crearPantalla() {
		setBackground(Color.LIGHT_GRAY);
	    setLayout(new BorderLayout(0, 0));
	    
	    construirPanelListaVideos();
	    construirPanelBusqueda();
	    construirPanelEtiquetas();  
	    
	}
	
	private void construirPanelListaVideos() {
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
		bBuscar_lista.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actualizacionPlaylist();
			}
			
		});
		
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

	public void construirPanelEtiquetas() {
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
		    
		    
		    lista_etiquetas_sel.addListSelectionListener(
		    		new ListSelectionListener() {
		    			public void valueChanged(ListSelectionEvent event) {
		    				
		    				if(!event.getValueIsAdjusting()) {
		    					//No usar los indices cuando tratemos con listas. Solo traen problemas. 
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
	
	
	public void construirPanelBusqueda() {
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
	    boton_buscar.addMouseListener(new MouseAdapter(){
	    	
	    	public void mouseClicked(MouseEvent e) {
	    		actualizarListaVideos();
	    	}
	    	
	    });
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
	    
	    
	    //Tratamiento de la lista de videos
	    lista_videos = new JList();
	    lista_videos.setBackground(Color.GRAY);
	    lista_videos.setForeground(Color.WHITE);
	    lista_videos.setVisibleRowCount(-1);
	    lista_videos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    lista_videos.setModel(modeloTablaVideos);
	    lista_videos.setCellRenderer(new VideoRenderer());
	    
	    
	    panel_resultados.add(new JScrollPane(lista_videos),BorderLayout.CENTER);
	    
	    
	    //MANEJADORES
	    n_busqueda.addMouseListener(new MouseAdapter() {
	    	
	    	public void mouseClicked(MouseEvent e) {
	    		modeloTablaVideos.removeAllElements();
	    	}
	    });
	
	    

	}
		
	//METODOS DE EXPLORAR Y BUSQUEDA
	public void actualizarListaVideos() {
		modeloTablaVideos.removeAllElements();
	    videos_encontrados= controlador.obtenerBusqueda(barra_busqueda.getText(),etiquetas_Sel_Lista);
	    videos_encontrados.stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloTablaVideos.addElement(nv));
	    validate();
	    
	}
	public void actualizarEtiquetasExplora() {
		List<Etiqueta> lista_etiquetas_cat;
		lista_etiquetas_cat= Controlador.getUnicaInstancia().getEtiquetas();
		lista_etiquetas_cat.stream()
					   .map(etq->etq.getNombre())
					   .forEach(nom->modeloEtiqDisponibles.addElement(nom));
		
		
	}
	
	//METODOS DE NUEVA LISTA

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
	
	private void actualizarVideosLista() {
		modeloVideosLista.removeAllElements();
	    lv_creada.getVideos().stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloVideosLista.addElement(nv));
	    validate();
		
		
	}
	
	private void eliminarLista() {
		controlador.borrarLista(lv_creada);
		modeloVideosLista.removeAllElements();
		lv_creada=null;
		etiquetaNomLista.setText("");
	}
	
//FUNCIONES DE MANTENIMIENTO 
	public void modoExplorar() {
		panel_listas.setVisible(false);
		panel_etiquetas.setVisible(true);
		if(activo==false)
			lista_videos.addMouseListener(event);
			activo=true;
	}
	
	public void modoNuevaLista() {
		panel_listas.setVisible(true);
		panel_etiquetas.setVisible(false);
		lista_videos.removeMouseListener(event);
		activo=false;
	}

	public void renovar() {
		modeloEtiqDisponibles.removeAllElements();
		modeloEtiqueSeleccionadas.removeAllElements();
		modeloTablaVideos.removeAllElements();
		etiquetas_Sel_Lista.clear();
		
		modeloVideosLista.removeAllElements();
		campoNombre.setText("");
		etiquetaNomLista.setText("");
		
	}
	
	

	
	public LinkedList<String> getEtiquetaSelLista(){
		return etiquetas_Sel_Lista;
	}
}
