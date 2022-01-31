package tds.umu.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollBar;
import javax.swing.border.BevelBorder;
import java.awt.Color;

public class PanelNuevaLista extends JPanel {

	private VentanaPrincipal ventana;
	private JTextField textField;
	private JTextField textField_1;
	
	public PanelNuevaLista(VentanaPrincipal ventana) {
		setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		panel.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(panel, BorderLayout.NORTH);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));
		
		JPanel panel_5 = new JPanel();
		panel_5.setBackground(Color.GRAY);
		panel_1.add(panel_5);
		
		JLabel lblNewLabel_1 = new JLabel("New label");
		lblNewLabel_1.setForeground(Color.WHITE);
		panel_5.add(lblNewLabel_1);
		
		JPanel panel_6 = new JPanel();
		panel_6.setBackground(Color.GRAY);
		panel_1.add(panel_6);
		
		textField_1 = new JTextField();
		panel_6.add(textField_1);
		textField_1.setColumns(10);
		
		JButton btnNewButton_2 = new JButton("New button");
		panel_6.add(btnNewButton_2);
		
		JPanel panel_7 = new JPanel();
		panel_7.setBackground(Color.GRAY);
		panel_1.add(panel_7);
		
		JButton btnNewButton_3 = new JButton("New button");
		panel_7.add(btnNewButton_3);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_2.add(panel_3);
		
		JLabel lblNewLabel = new JLabel("New label");
		panel_3.add(lblNewLabel);
		
		textField = new JTextField();
		panel_3.add(textField);
		textField.setColumns(30);
		
		JButton btnNewButton = new JButton("New button");
		panel_3.add(btnNewButton);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel_2.add(panel_4);
		
		JButton btnNewButton_1 = new JButton("New button");
		panel_4.add(btnNewButton_1);
		
		JPanel panel_8 = new JPanel();
		panel_8.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		add(panel_8, BorderLayout.WEST);
		panel_8.setLayout(new BoxLayout(panel_8, BoxLayout.Y_AXIS));
		
		JPanel panel_9 = new JPanel();
		panel_9.setBackground(Color.GRAY);
		panel_8.add(panel_9);
		
		JScrollBar scrollBar = new JScrollBar();
		panel_9.add(scrollBar);
		
		JPanel panel_10 = new JPanel();
		panel_10.setBackground(Color.GRAY);
		panel_10.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_8.add(panel_10);
		panel_10.setLayout(new BoxLayout(panel_10, BoxLayout.Y_AXIS));
		
		JPanel panel_11 = new JPanel();
		panel_11.setBackground(Color.GRAY);
		panel_10.add(panel_11);
		
		JButton btnNewButton_4 = new JButton("New button");
		panel_11.add(btnNewButton_4);
		
		JButton btnNewButton_5 = new JButton("New button");
		panel_11.add(btnNewButton_5);
		
		JPanel panel_12 = new JPanel();
		panel_12.setBackground(Color.GRAY);
		panel_10.add(panel_12);
		
		JButton btnNewButton_6 = new JButton("New button");
		panel_12.add(btnNewButton_6);
		
		JPanel panel_13 = new JPanel();
		panel_13.setBackground(Color.GRAY);
		add(panel_13, BorderLayout.CENTER);
		ventana=ventana;
		
	
	}
	
	public void crearPantalla()
	{
		
	}

}
