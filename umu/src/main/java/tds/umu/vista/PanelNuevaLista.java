package tds.umu.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Video;
import tds.umu.modelo.VideoRenderer;
import tds.umu.modelo.VideoRepresent;
import tds.video.VideoWeb;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.SwingConstants;

public class PanelNuevaLista extends JPanel {

	private VentanaPrincipal ventana;
	private JPanel panel,panel_1,panel_2,panel_4,panel_5;
	private JLabel etiquetaSeleccion;
	private JButton bEliminar;
	private JPanel panel_3;
	private JPanel panel_6;
	private Controlador controlador= Controlador.getUnicaInstancia();
	private JList lista_videos,videos_lista;
	private VideoWeb videoWeb= controlador.getReproductor();
	private JPanel panel_7;
	private JButton bBuscar;
	private JTextField campoNombre;
	private JButton bAceptar;
	private JButton bAñadir;
	private JButton bQuitar;
	private JLabel barra_titulo;
	private JTextField textField_1;
	private JButton bBusqueda2;
	private JButton bNuevaBusqueda;

	private DefaultListModel<VideoRepresent> modeloVideosLista= new DefaultListModel<VideoRepresent>();
	private DefaultListModel<VideoRepresent> modeloListaVideos= new DefaultListModel<VideoRepresent>();
	
	private ListaVideos lv_creada;
	
	public PanelNuevaLista(VentanaPrincipal ventana) {
		ventana=ventana;
		crearPantalla();}
	
	public void crearPantalla()
	{
		setBackground(Color.GRAY);
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setBackground(Color.GRAY);
		add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		etiquetaSeleccion = new JLabel("Introducir nombre lista:");
		etiquetaSeleccion.setAlignmentX(Component.CENTER_ALIGNMENT);
		etiquetaSeleccion.setForeground(Color.WHITE);
		panel_1.add(etiquetaSeleccion);
		
		panel_7 = new JPanel();
		panel_7.setBackground(Color.GRAY);
		panel_1.add(panel_7);
		panel_7.setLayout(new BoxLayout(panel_7, BoxLayout.X_AXIS));
		
		campoNombre = new JTextField();
		panel_7.add(campoNombre);
		campoNombre.setColumns(10);
		
		bBuscar = new JButton("Buscar");
		bBuscar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actualizacionPlaylist();
			}
			
		});
		panel_7.add(bBuscar);
		
		panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel_1.add(panel_4);
		
		bEliminar = new JButton("Eliminar");
		panel_4.add(bEliminar);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		bAceptar = new JButton("Aceptar");
		bAceptar.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(bAceptar);
		
		bAñadir = new JButton("Añadir");
		bAñadir.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(bAñadir);
		
		bQuitar = new JButton("Quitar");
		bQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		bQuitar.setAlignmentX(Component.CENTER_ALIGNMENT);
		bQuitar.setHorizontalAlignment(SwingConstants.LEADING);
		panel_2.add(bQuitar);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3.setBackground(Color.GRAY);
		panel.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		panel_6 = new JPanel();
		panel_6.setBackground(Color.GRAY);
		
		panel.add(panel_6, BorderLayout.CENTER);
		
		lista_videos = new JList<VideoRepresent>();
	    lista_videos.setBackground(Color.GRAY);
	    lista_videos.setForeground(Color.WHITE);
	    lista_videos.setVisibleRowCount(-1);
	    lista_videos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    lista_videos.setModel(modeloListaVideos);
	    lista_videos.setCellRenderer(new VideoRenderer());
	   /* lista_videos.addMouseListener(
	    		new MouseAdapter() {
	    			public void mouseClicked(MouseEvent event) {
	    				if(event.getClickCount()==2) {
	    					JList<VideoRepresent> source=(JList<VideoRepresent>) event.getSource();
	    					VideoRepresent selected = source.getSelectedValue();
	    					if(selected!=null) {
	    						Video v= vlist_seleccionada.getVideoIndex(lista_videos.getSelectedIndex());
	    						controlador.actualizarVideoSeleccionado(v);
	    						reproductor.reproducir();
	    						cambiarPanelRep();
	    						
	    				}
	    				}
	    			}	
	    			}
	    		}
	    		);
	*/
	
		panel_6.add(lista_videos);
		panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		add(panel_5, BorderLayout.CENTER);	
		
		barra_titulo = new JLabel("Buscar por titulo:");
		barra_titulo.setForeground(Color.WHITE);
		panel_5.add(barra_titulo);
		
		textField_1 = new JTextField();
		panel_5.add(textField_1);
		textField_1.setColumns(10);
		
		bBusqueda2 = new JButton("Buscar");
		panel_5.add(bBusqueda2);
		
		bNuevaBusqueda = new JButton("Nueva búsqueda");
		panel_5.add(bNuevaBusqueda);
	}

	
	private void actualizacionPlaylist() {
		lv_creada=controlador.obtenerLista(campoNombre.getText());
		if(lv_creada==null) {
			lv_creada=controlador.crearLista(campoNombre.getText());
		}
			
		actualizarListaVideos();
	}

	private void actualizarListaVideos() {
		modeloListaVideos.removeAllElements();
	    lv_creada.getVideos().stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloListaVideos.addElement(nv));
	    validate();
		
		
	}
	
	

}
