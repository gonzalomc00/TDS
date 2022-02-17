package tds.umu.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
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
	private JComboBox comboBox;
	private JButton bReproducir, bCancelar;
	private JPanel panel_3;
	private JScrollBar scrollBar;
	private JPanel panel_6;
	
	public PanelMisListas(VentanaPrincipal ventana) {

		ventana=ventana;
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
		
		etiquetaSeleccion = new JLabel("Seleccione la lista");
		etiquetaSeleccion.setAlignmentX(Component.CENTER_ALIGNMENT);
		etiquetaSeleccion.setForeground(Color.WHITE);
		panel_1.add(etiquetaSeleccion);
		
		comboBox = new JComboBox();
		panel_1.add(comboBox);
		
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
		
		panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3.setBackground(Color.GRAY);
		panel.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		scrollBar = new JScrollBar();
		scrollBar.setBackground(Color.GRAY);
		scrollBar.setForeground(Color.GRAY);
		panel.add(scrollBar, BorderLayout.EAST);
		
		panel_6 = new JPanel();
		panel_6.setBackground(Color.GRAY);
		panel.add(panel_6, BorderLayout.CENTER);
		
		panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		add(panel_5, BorderLayout.CENTER);
		
	}

}
