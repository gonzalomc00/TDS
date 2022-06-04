package tds.umu.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.border.LineBorder;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Video;
import tds.video.VideoWeb;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.UIManager;

public class PanelRecientes extends JPanel {
	
	private VentanaPrincipal ventana;
	private JPanel panel,panel_1,panel_2,panel_3,panel_4,panel_5;
	private JLabel etiquetaSeleccion;
	private JComboBox comboBox;
	private JButton bReproducir, bCancelar;
	private JScrollBar scrollBar;
	private JList lista_videos;
	private Reproductor reproductor;
	private Controlador controlador= Controlador.getUnicaInstancia();
	private List<Video> recientes= new LinkedList<Video>();
	private DefaultListModel<VideoRepresent> modeloListaVideos= new DefaultListModel<VideoRepresent>();
	private VideoWeb videoWeb= controlador.getReproductor();
	
	public PanelRecientes(VentanaPrincipal ventana) {

		ventana=ventana;
		reproductor=ventana.getReproductor();
		crearPantalla();
	}
	
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
		
		etiquetaSeleccion = new JLabel("Videos recientes");
		etiquetaSeleccion.setAlignmentX(Component.CENTER_ALIGNMENT);
		etiquetaSeleccion.setForeground(Color.WHITE);
		panel_1.add(etiquetaSeleccion);
		
		panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel_1.add(panel_4);
		
		bReproducir = new JButton("Reproducir");
		panel_4.add(bReproducir);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_2, BorderLayout.SOUTH);
		
		bCancelar = new JButton("Cancelar");
		panel_2.add(bCancelar);
		
		
		
		lista_videos = new JList<VideoRepresent>();
	    lista_videos.setBackground(Color.GRAY);
	    lista_videos.setForeground(Color.WHITE);
	    lista_videos.setVisibleRowCount(-1);
	    lista_videos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
	    lista_videos.setModel(modeloListaVideos);
	    lista_videos.setCellRenderer(new VideoRenderer());
	    lista_videos.addMouseListener(
	    		new MouseAdapter() {
	    			public void mouseClicked(MouseEvent event) {
	    				
	    				/*
	    				 * No se si aquí debería buscar en la base de datos apartir del nombre o algo, o si deberia
	    				 * incluir la clase que representa a los videos el video en sí. No se si rompe algun patron
	    				 */
	    				if(event.getClickCount()==2) {
	    					JList<VideoRepresent> source=(JList<VideoRepresent>) event.getSource();
	    					VideoRepresent selected = source.getSelectedValue();
	    					if(selected!=null) {
	    						Video v= recientes.get(lista_videos.getSelectedIndex());
	    						controlador.actualizarVideoSeleccionado(v);
	    						reproductor.reproducir();
	    						cambiarPanelRep();
	    						
	    				}
	    			}	
	    			}
	    		}
	    		);
	    panel.add(new JScrollPane(lista_videos),BorderLayout.CENTER);
		
	}
	
	public void actualizarPanelRecientes() {
		modeloListaVideos.removeAllElements();
		recientes= controlador.obtenerRecientesUser();
	    recientes.stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloListaVideos.addElement(nv));
	    validate();
		
	}
	
	private void cambiarPanelRep() {
		add(reproductor,BorderLayout.CENTER);
		revalidate();
		repaint();
		validate();
	}
	
	

}
