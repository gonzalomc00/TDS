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
import tds.umu.modelo.*;
import tds.video.VideoWeb;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.CardLayout;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.FormSpecs;
//import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;
import javax.swing.UIManager;

public class PanelMisListas extends JPanel {
	
	private VentanaPrincipal ventana;
	private JPanel panel,panel_1,panel_2,panel_4,panel_5;
	private JLabel etiquetaSeleccion;
	private JComboBox<String> comboBox;
	private JButton bReproducir, bCancelar;
	private JPanel panel_3;
	private JPanel panel_6;
	private Controlador controlador= Controlador.getUnicaInstancia();
	private JList lista_videos;
	private JScrollPane barra_deslizadora;
	private DefaultListModel<VideoRepresent> modeloListaVideos= new DefaultListModel<VideoRepresent>();
	private VideoWeb videoWeb= controlador.getReproductor();
	private Reproductor reproductor;
	private List<ListaVideos> vlists_encontradas;
	private ListaVideos vlist_seleccionada;
	
	public PanelMisListas(VentanaPrincipal ventana) {

		ventana=ventana;
		crearPantalla();
		//No sé si esto se puede hacer
		reproductor=ventana.getReproductor();
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
		
		etiquetaSeleccion = new JLabel("Seleccione la lista");
		etiquetaSeleccion.setAlignmentX(Component.CENTER_ALIGNMENT);
		etiquetaSeleccion.setForeground(Color.WHITE);
		panel_1.add(etiquetaSeleccion);
		
		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected= (String)comboBox.getSelectedItem();
				if(selected!=null) {
				actualizarPanelLateral(vlists_encontradas.get(comboBox.getSelectedIndex()));
			}
			}
		});
		panel_1.add(comboBox);
		
		panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel_1.add(panel_4);
		
		bReproducir = new JButton("Reproducir");
		bReproducir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cambiarPanelRep();
				//reproducirPlayListCompleta();
				
			}
			
		});
		panel_4.add(bReproducir);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_2, BorderLayout.SOUTH);
		
		bCancelar = new JButton("Cancelar");
		panel_2.add(bCancelar);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3.setBackground(Color.GRAY);
		panel.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		
		
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
	    						Video v= vlist_seleccionada.getVideoIndex(lista_videos.getSelectedIndex());
	    						controlador.actualizarVideoSeleccionado(v);
	    						reproductor.reproducir(0);
	    						cambiarPanelRep();
	    						
	    				}
	    			}	
	    			}
	    		}
	    		);
	    
	    barra_deslizadora = new JScrollPane(lista_videos);
		barra_deslizadora.setBackground(Color.GRAY);
		panel.add(barra_deslizadora, BorderLayout.CENTER);
		
		
	}
	

	public void actualizarPlayLists() {
		vlists_encontradas= controlador.obtenerPlayListsUser();
		vlists_encontradas.stream().
			forEach(l->comboBox.addItem(l.getNombre()));
		
		
	}
	
	public void actualizarPanelLateral(ListaVideos list) {
		modeloListaVideos.removeAllElements();
	    list.getVideos().stream()
	    	   .map(v -> new VideoRepresent(v,videoWeb.getSmallThumb(v.getUrl())))
	    	   .forEach(nv-> modeloListaVideos.addElement(nv));
	    validate();
	    vlist_seleccionada=list;
		
	}
	
	
	public void reproducirPlayListCompleta() {
		//Patron experto?? Yo creo que no 
		for(Video vid: vlist_seleccionada.getVideos()) {
			
		}
		
		
	}
	
	
	
	private void cambiarPanelRep() {
		PanelMisListas.this.add(reproductor);
		revalidate();
		repaint();
		validate();
	}
	
	


}
