package tds.umu.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Video;
import tds.video.VideoWeb;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;

public class PanelNuevaLista extends JPanel {

	private VentanaPrincipal ventana;
	private JPanel panel,panel_1,panel_2,panel_4,panel_5;
	private JLabel etiquetaSeleccion;
	private JButton bEliminar;
	private JPanel panel_3;
	private JPanel panel_6;
	private Controlador controlador= Controlador.getUnicaInstancia();
	private JList videos_lista,lista_videos;
	private VideoWeb videoWeb= controlador.getReproductor();
	private JPanel panel_7;
	private JButton bBuscar;
	private JTextField campoNombre;
	private JButton bAceptar;
	private JButton bAñadir;
	private JButton bQuitar;
	private JLabel barra_titulo;
	private JTextField barra_busqueda;
	private JButton bBusqueda2;
	private JButton bNuevaBusqueda;
	private JPanel panel_9;


	

	private DefaultListModel<VideoRepresent> modeloVideosLista= new DefaultListModel<VideoRepresent>();
	private DefaultListModel<VideoRepresent> modeloListaVideos= new DefaultListModel<VideoRepresent>();
	private ListaVideos lv_creada;
	private List<Video> videos_encontrados;
	
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
		bEliminar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lv_creada==null) {
					JOptionPane.showMessageDialog(PanelNuevaLista.this, "Debes tener una lista de videos seleccionada",
							"Error", JOptionPane.ERROR_MESSAGE);
				}
				else {
						controlador.borrarLista(lv_creada);
						modeloVideosLista.removeAllElements();
						lv_creada=null;
				}
			}
		});
		panel_4.add(bEliminar);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		

		bAñadir = new JButton("Añadir");
		bAñadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lv_creada!=null && lista_videos.getSelectedValue()!=null) {
					Video v_sel=videos_encontrados.get(lista_videos.getSelectedIndex());
					controlador.añadirVideoPlaylist(lv_creada,v_sel);
					actualizarVideosLista();
				}
			}
		});
		bAñadir.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(bAñadir);
		
		bQuitar = new JButton("Quitar");
		bQuitar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(lv_creada!=null && videos_lista.getSelectedValue()!=null) {
					Video v_sel=lv_creada.obtenerVideoIndex(videos_lista.getSelectedIndex());
					controlador.eliminarVideoLista(lv_creada,v_sel);
					actualizarVideosLista();
				}
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
		

		
		
		videos_lista = new JList<VideoRepresent>();
	    videos_lista.setBackground(Color.GRAY);
	    videos_lista.setForeground(Color.WHITE);
	    videos_lista.setVisibleRowCount(-1);
	    videos_lista.setModel(modeloVideosLista);
	    videos_lista.setCellRenderer(new VideoRenderer());
	    videos_lista.addMouseListener(
	    		new MouseAdapter() {
	    			public void mouseClicked(MouseEvent event) {
	    				if(event.getClickCount()==2) {
	    					JList<VideoRepresent> source=(JList<VideoRepresent>) event.getSource();
	    					VideoRepresent selected = source.getSelectedValue();
	    					if(selected!=null) {
	    						
	    				}
	    			}	
	    			}
	    		}
	    		);
	    		
	    		
		panel.add(new JScrollPane(videos_lista));
		panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		add(panel_5, BorderLayout.CENTER);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		panel_9 = new JPanel();
		panel_9.setBackground(Color.GRAY);
		panel_5.add(panel_9, BorderLayout.NORTH);
		
		barra_titulo = new JLabel("Buscar por titulo:");
		panel_9.add(barra_titulo);
		barra_titulo.setForeground(Color.WHITE);
		
		barra_busqueda = new JTextField();
		panel_9.add(barra_busqueda);
		barra_busqueda.setColumns(10);
		
		bBusqueda2 = new JButton("Buscar");
		bBusqueda2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				actualizarListaVideos();
			}
			
		});
	
		panel_9.add(bBusqueda2);
		
		bNuevaBusqueda = new JButton("Nueva búsqueda");
		bNuevaBusqueda.addMouseListener(new MouseAdapter() {
		    	
		    	public void mouseClicked(MouseEvent e) {
		    		modeloListaVideos.removeAllElements();
		    	}
		    });
		panel_9.add(bNuevaBusqueda);
		

		//LISTA DE VIDEOS QUE APARECEN EN LA BUSQUEDA
		lista_videos = new JList<VideoRepresent>();
	    lista_videos.setBackground(Color.GRAY);
	    lista_videos.setForeground(Color.WHITE);
	    lista_videos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    lista_videos.setVisibleRowCount(-1);
	    lista_videos.setModel(modeloListaVideos);
	    lista_videos.setCellRenderer(new VideoRenderer());
		panel_5.add(new JScrollPane(lista_videos),BorderLayout.CENTER);
		
	
	}
	/*
	 * REVISAR:
	 * 		Al aparecer el panel de seleccion SI o NO tambien aparece un boton Cancelar.
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
	
	//OJO!! CAMBIAR TEMA DE LAS ETIQUETAS. NO SE SI ESTO ES LO MAS OPORTUNO
	public void actualizarListaVideos() {
		modeloListaVideos.removeAllElements();
	    videos_encontrados= controlador.obtenerBusqueda(barra_busqueda.getText(),new LinkedList<String>());  
	    videos_encontrados.stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloListaVideos.addElement(nv));
	    validate();
	    
	}
	

	public void clear() {
		modeloListaVideos.removeAllElements();
		modeloVideosLista.removeAllElements();
		campoNombre.setText("");
	}
	
	
	

}
