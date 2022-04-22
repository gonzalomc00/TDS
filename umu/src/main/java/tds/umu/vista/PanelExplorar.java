package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
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
import tds.umu.modelo.Etiqueta;

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
	private DefaultListModel<String> modeloTablaVideos= new DefaultListModel<String>();

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
	    actualizarEtiquetasExplora();
	    lista_etiquetas.setModel(modeloEtiqDisponibles);
	    
	    lista_etiquetas.addListSelectionListener(
	    		new ListSelectionListener() {
	    			public void valueChanged(ListSelectionEvent event) {
	    				
	    				if(!event.getValueIsAdjusting()) {
	    					JList source=(JList) event.getSource();
	    					String selected = source.getSelectedValue().toString();
	    					if(!modeloEtiqueSeleccionadas.contains(selected)) {
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
	    					JList source=(JList) event.getSource();
	    					int selected = source.getSelectedIndex();
	    					if(selected>=0) {
	    						modeloEtiqueSeleccionadas.remove(selected);
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
	    panel_3.add(boton_buscar);
	    
	    panel_4 = new JPanel();
	    panel_4.setBackground(Color.GRAY);
	    panel_2.add(panel_4);
	    
	    n_busqueda = new JButton("Nueva b�squeda");
	    panel_4.add(n_busqueda);
	    
	    panel_5 = new JPanel();
	    panel_5.setBackground(Color.GRAY);
	    panel_1.add(panel_5, BorderLayout.CENTER);
	    
	    lista_videos = new JList();
	    lista_videos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    lista_videos.setVisibleRowCount(2);
	    modeloTablaVideos.addElement("a");
	    modeloTablaVideos.addElement("a");
	    modeloTablaVideos.addElement("a");
	    modeloTablaVideos.addElement("a");
	    modeloTablaVideos.addElement("eeeeeeeeeeeeeeee");
	    modeloTablaVideos.addElement("eeeeeeeeeeeeeeee");
	    modeloTablaVideos.addElement("eeeeeeeeeeeeeeee");
	    lista_videos.setModel(modeloTablaVideos);
	 
	    
	    panel_5.add(lista_videos);

	    
	    
	}
	

	
	public void actualizarEtiquetasExplora() {
		List<Etiqueta> lista_etiquetas_cat;
		lista_etiquetas_cat= CatalogoEtiquetas.getUnicaInstancia().getEtiquetas();
		lista_etiquetas_cat.stream()
					   .map(etq->etq.getNombre())
					   .forEach(nom->modeloEtiqDisponibles.addElement(nom));
		
		
	}
}
