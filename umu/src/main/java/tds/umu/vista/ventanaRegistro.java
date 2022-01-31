package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

public class ventanaRegistro extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField txtDdmmaaaa;
	private JTextField txtPepicodominioalgo;
	private JTextField txtPepico;
	private JPasswordField passwordField;
	private JPanel panel,panel_1,panel_2,panel_3,panel_4,panel_5,panel_6,panel_7;
	private JLabel lblNewLabel, lblNewLabel_1, lblNewLabel_2, lblNewLabel_3, lblNewLabel_4, lblNewLabel_5;
	private JButton btnNewButton, btnNewButton_1;
	private VentanaPrincipal ventana;

	/**
	 * Create the panel.
	 */

	public ventanaRegistro(VentanaPrincipal ventana) {
		ventana=ventana;
		crearPantalla();

	}
	
	public void crearPantalla()
	{
	setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
	FlowLayout flowLayout = (FlowLayout) getLayout();
	flowLayout.setVgap(80);
	
	panel = new JPanel();
	panel.setToolTipText("");
	panel.setBackground(SystemColor.inactiveCaptionBorder);
	panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, SystemColor.infoText, null, null, null));
	add(panel);
	panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
	
	panel_1 = new JPanel();
	panel.add(panel_1);
	panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	
	lblNewLabel = new JLabel("Nombre  ");
	lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
	panel_1.add(lblNewLabel);
	
	textField = new JTextField();
	textField.setAlignmentX(Component.LEFT_ALIGNMENT);
	textField.setHorizontalAlignment(SwingConstants.LEFT);
	textField.setColumns(10);
	panel_1.add(textField);
	
	panel_2 = new JPanel();
	panel_1.add(panel_2);
	panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	
	JLabel lblNewLabel_1 = new JLabel("Apellidos");
	lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
	panel_2.add(lblNewLabel_1);
	
	textField_1 = new JTextField();
	textField_1.setText("");
	panel_2.add(textField_1);
	textField_1.setColumns(10);
	
	panel_3 = new JPanel();
	panel.add(panel_3);
	panel_3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	
	lblNewLabel_2 = new JLabel("Fecha de nacimiento");
	panel_3.add(lblNewLabel_2);
	
	txtDdmmaaaa = new JTextField();
	txtDdmmaaaa.setForeground(SystemColor.textInactiveText);
	txtDdmmaaaa.setText(" dd/mm/aaaa");
	panel_3.add(txtDdmmaaaa);
	txtDdmmaaaa.setColumns(10);
	
	panel_4 = new JPanel();
	panel.add(panel_4);
	panel_4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	
	lblNewLabel_3 = new JLabel("Email");
	panel_4.add(lblNewLabel_3);
	
	txtPepicodominioalgo = new JTextField();
	txtPepicodominioalgo.setForeground(SystemColor.textInactiveText);
	txtPepicodominioalgo.setText("pepico@dominio.algo");
	txtPepicodominioalgo.setHorizontalAlignment(SwingConstants.CENTER);
	panel_4.add(txtPepicodominioalgo);
	txtPepicodominioalgo.setColumns(15);
	
	panel_5 = new JPanel();
	panel.add(panel_5);
	panel_5.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	
	lblNewLabel_4 = new JLabel("Usuario");
	panel_5.add(lblNewLabel_4);
	
	txtPepico = new JTextField();
	txtPepico.setForeground(SystemColor.textInactiveText);
	txtPepico.setText("pepico45");
	panel_5.add(txtPepico);
	txtPepico.setColumns(10);
	
	panel_6 = new JPanel();
	panel_5.add(panel_6);
	panel_6.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
	
	lblNewLabel_5 = new JLabel("Contrase\u00F1a");
	panel_6.add(lblNewLabel_5);
	
	passwordField = new JPasswordField();
	passwordField.setColumns(10);
	panel_6.add(passwordField);
	
	Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
	panel.add(rigidArea_1);
	
	panel_7 = new JPanel();
	FlowLayout flowLayout_1 = (FlowLayout) panel_7.getLayout();
	flowLayout_1.setAlignOnBaseline(true);
	flowLayout_1.setAlignment(FlowLayout.RIGHT);
	panel.add(panel_7);
	
	btnNewButton = new JButton("Registrarse");
	panel_7.add(btnNewButton);
	
	btnNewButton_1 = new JButton("Cancelar");
	panel_7.add(btnNewButton_1);
		
	}

}
