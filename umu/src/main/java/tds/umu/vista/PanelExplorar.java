package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import javax.swing.JList;
import javax.swing.JTextPane;

public class PanelExplorar extends JPanel {
	private JTextPane textPane,textPane_1;
	private JPanel panel,panel_1,panel_2,panel_3,panel_4,panel_5,panel_6,panel_7,panel_8,panel_9,panel_10,panel_11;
	private JButton n_busqueda,boton_buscar;
	private JLabel etiq_titulo,etiquetas_disp,b_etiquetas;
	private JTextField barra_busqueda;
	private VentanaPrincipal ventana;

	/**
	 * Create the panel.
	 */
	public PanelExplorar(VentanaPrincipal ventana) {
		ventana=ventana;
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
	    
	    textPane = new JTextPane();
	    panel_9.add(textPane);
	    
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
	    
	    textPane_1 = new JTextPane();
	    panel_11.add(textPane_1);
	    
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
	}
}
