package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.AbstractListModel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.AbstractTableModel;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.CatalogoEtiquetas;
import tds.umu.modelo.CatalogoVideos;
import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.Video;
import tds.umu.modelo.VideoRenderer;
import tds.umu.modelo.VideoRepresent;

import javax.swing.JList;
import javax.swing.JTextPane;
import tds.video.VideoWeb;
import javax.swing.JTable;

public class PanelExplorar extends JPanel {
	private JPanel panel,panel_1,panel_2,panel_3,panel_4,panel_5,panel_6,panel_7,panel_8,panel_9,panel_10,panel_11;
	private JButton n_busqueda,boton_buscar;
	private JLabel etiq_titulo,etiquetas_disp,b_etiquetas;
	private JTextField barra_busqueda;
	private VentanaPrincipal ventana;
	private JList lista_etiquetas,lista_etiquetas_sel,lista_videos;
	private VideoWeb videoWeb=Controlador.getUnicaInstancia().getReproductor();
	private DefaultListModel<String> modeloEtiqDisponibles = new DefaultListModel<String>();
	private DefaultListModel<String> modeloEtiqueSeleccionadas= new DefaultListModel<String>();
	private DefaultListModel<VideoRepresent> modeloTablaVideos= new DefaultListModel<VideoRepresent>();
	
	private Controlador controlador= Controlador.getUnicaInstancia();
	
	private Video videoSeleccionado;

	/**
	 * Create the panel.
	 */
	public PanelExplorar(VentanaPrincipal ventana) {
		this.ventana=ventana;
		crearPantalla();
	}

	private void crearPantalla() {
		setBackground(Color.LIGHT_GRAY);
		setPreferredSize(new Dimension(900, 500));
	    setMaximumSize(new Dimension(900, 500));
	    setLayout(new BorderLayout(0, 0));
	    
	    panel = new JPanel();
	    panel.setBackground(Color.GRAY);
	    panel.setBorder(new LineBorder(new Color(0, 0, 0)));
	    add(panel, BorderLayout.EAST);
	    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	    
	    panel_6 = new JPanel();
	    panel.add(panel_6);
	    panel_6.setLayout(new BoxLayout(panel_6, BoxLayout.Y_AXIS));
	    
	    panel_8 = new JPanel();
	    panel_8.setBackground(Color.GRAY);
	    panel_6.add(panel_8);
	    
	    etiquetas_disp = new JLabel("Etiquetas disponibles");
	    etiquetas_disp.setForeground(Color.WHITE);
	    panel_8.add(etiquetas_disp);
	    
	    panel_9 = new JPanel();
	    panel_9.setBackground(Color.GRAY);
	    panel_6.add(panel_9);
	    
	    lista_etiquetas = new JList();
	    lista_etiquetas.setModel(modeloEtiqDisponibles);
	    
	    lista_etiquetas.addListSelectionListener(
	    		new ListSelectionListener() {
	    			public void valueChanged(ListSelectionEvent event) {
	    				
	    				if(!event.getValueIsAdjusting()) {
	    					JList<String> source=(JList<String>) event.getSource();
	    					String selected = source.getSelectedValue();
	    					if(selected!=null && !modeloEtiqueSeleccionadas.contains(selected)) {
	    					modeloEtiqueSeleccionadas.addElement(selected);
	    					}
	    				}
	    			}	
	    		}	
	    		);
	    panel_9.add(lista_etiquetas);
	    
	    
	    panel_7 = new JPanel();
	    panel.add(panel_7);
	    panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.Y_AXIS));
	    
	    panel_10 = new JPanel();
	    panel_10.setBackground(Color.GRAY);
	    panel_7.add(panel_10);
	    
	    b_etiquetas = new JLabel("Buscar etiquetas");
	    b_etiquetas.setForeground(Color.WHITE);
	    panel_10.add(b_etiquetas);
	    
	    panel_11 = new JPanel();
	    panel_11.setBackground(Color.GRAY);
	    panel_7.add(panel_11);
	    
	    lista_etiquetas_sel = new JList();
	    lista_etiquetas_sel.setModel(modeloEtiqueSeleccionadas);
	    lista_etiquetas_sel.addListSelectionListener(
	    		new ListSelectionListener() {
	    			public void valueChanged(ListSelectionEvent event) {
	    				
	    				if(!event.getValueIsAdjusting()) {
	    					//No usar los indices cuando tratemos con listas. Solo traen problemas. 
	    					JList<String> source=(JList<String>) event.getSource();
	    					String selected = source.getSelectedValue();
	    					if(selected!=null) {
	    						modeloEtiqueSeleccionadas.removeElement(selected);
	    					}
	    					
	    				}
	    			}	
	    		}	
	    		);
	    
	    
	    panel_11.add(lista_etiquetas_sel);
	    
	    panel_1 = new JPanel();
	    panel_1.setBackground(Color.GRAY);
	    add(panel_1, BorderLayout.CENTER);
	    panel_1.setLayout(new BorderLayout(0, 0));
	    
	    panel_2 = new JPanel();
	    panel_2.setBackground(Color.GRAY);
	    panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
	    panel_1.add(panel_2, BorderLayout.NORTH);
	    panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
	    
	    panel_3 = new JPanel();
	    panel_3.setBackground(Color.GRAY);
	    panel_2.add(panel_3);
	    
	    etiq_titulo = new JLabel("Buscar t�tulo");
	    panel_3.add(etiq_titulo);
	    
	    barra_busqueda = new JTextField();
	    panel_3.add(barra_busqueda);
	    barra_busqueda.setColumns(40);
	    
	    boton_buscar = new JButton("Buscar");
	    boton_buscar.addMouseListener(new MouseAdapter(){
	    	
	    	public void mouseClicked(MouseEvent e) {
	    		actualizarListaVideos();
	    	}
	    	
	    });
	    panel_3.add(boton_buscar);
	    
	    panel_4 = new JPanel();
	    panel_4.setBackground(Color.GRAY);
	    panel_2.add(panel_4);
	    
	    n_busqueda = new JButton("Nueva búsqueda");
	    n_busqueda.addMouseListener(new MouseAdapter() {
	    	
	    	public void mouseClicked(MouseEvent e) {
	    		modeloTablaVideos.removeAllElements();
	    	}
	    });
	    panel_4.add(n_busqueda);
	    
	    panel_5 = new JPanel();
	    panel_5.setLayout(new BorderLayout());
	    panel_5.setBackground(Color.GRAY);
	    panel_1.add(panel_5, BorderLayout.CENTER);
	    
	    
	    //Tratamiento de la lista de videos
	    lista_videos = new JList();
	    lista_videos.setBackground(Color.GRAY);
	    lista_videos.setVisibleRowCount(-1);
	    lista_videos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    lista_videos.setModel(modeloTablaVideos);
	    lista_videos.setCellRenderer(new VideoRenderer());
	    lista_videos.addListSelectionListener(
	    		new ListSelectionListener() {
	    			public void valueChanged(ListSelectionEvent event) {
	    				
	    				/*
	    				 * No se si aquí debería buscar en la base de datos apartir del nombre o algo, o si deberia
	    				 * incluir la clase que representa a los videos el video en sí. No se si rompe algun patron
	    				 */
	    				if(!event.getValueIsAdjusting()) {
	    					JList<VideoRepresent> source=(JList<VideoRepresent>) event.getSource();
	    					VideoRepresent selected = source.getSelectedValue();
	    					if(selected!=null) {
	    					videoSeleccionado=selected.getVideo();
	    					ventana.cambioPanel(Paneles.REPRODUCTOR);
	    				}
	    			}	
	    			}
	    		}
	    		);
	
	 
	    
	    panel_5.add(new JScrollPane(lista_videos),BorderLayout.CENTER);

	    
	    
	}
		
	public void actualizarListaVideos() {
		modeloTablaVideos.removeAllElements();
	    List<Video> videos= controlador.getListaVideos();
	    videos.stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloTablaVideos.addElement(nv));
	    validate();
	    
	}
	
	public void actualizarEtiquetasExplora() {
		List<Etiqueta> lista_etiquetas_cat;
		lista_etiquetas_cat= Controlador.getUnicaInstancia().getEtiquetas();
		for(Etiqueta et: lista_etiquetas_cat) {
			System.out.println(et.getNombre());
		}
		lista_etiquetas_cat.stream()
					   .map(etq->etq.getNombre())
					   .forEach(nom->modeloEtiqDisponibles.addElement(nom));
		
		
	}
	
	public Video getVideoSeleccionado() {
		return videoSeleccionado;
	}


	public void renovar() {
		modeloEtiqDisponibles.removeAllElements();
		modeloEtiqueSeleccionadas.removeAllElements();
		modeloTablaVideos.removeAllElements();
		
	}
}
