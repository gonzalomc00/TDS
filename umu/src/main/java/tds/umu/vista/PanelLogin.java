package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;

import tds.umu.controlador.Controlador;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPasswordField;

public class PanelLogin extends JPanel {
	private JTextField textoUser;
	private JPanel panel,panel_1,panel_2,panel_3,panel_4,panel_5,panel_6,panel_7;
	private JLabel etiquetaUsuario,etiquetaContrasena;
	private JButton aceptar,cancelar;
	private VentanaPrincipal ventana;
	private JPasswordField textoContrasena;

	/**
	 * Create the panel.
	 */
	public PanelLogin(VentanaPrincipal v) {
		ventana=v;
		crearPantalla();
		
	}
	
	private void crearPantalla()
	{
		setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setBackground(Color.GRAY);
		panel.add(Box.createRigidArea(new Dimension(105,70)));
		add(panel, BorderLayout.WEST);
		
		panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.add(Box.createRigidArea(new Dimension(105,70)));
		add(panel_1, BorderLayout.NORTH);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel_2.add(Box.createRigidArea(new Dimension(105,70)));
		add(panel_2, BorderLayout.EAST);
		
		panel_3 = new JPanel();
		panel_3.setBackground(Color.GRAY);
		panel_3.add(Box.createRigidArea(new Dimension(105,100)));
		add(panel_3, BorderLayout.SOUTH);
		
		panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BoxLayout(panel_4, BoxLayout.Y_AXIS));
		
		panel_5 = new JPanel();
		panel_5.setPreferredSize(new Dimension(900, 50));
	    panel_5.setMaximumSize(new Dimension(900, 50));
		panel_4.add(panel_5);
		
		etiquetaUsuario = new JLabel("Usuario");
		panel_5.add(etiquetaUsuario);
		
		textoUser = new JTextField();
		panel_5.add(textoUser);
		textoUser.setColumns(14);
		
		panel_6 = new JPanel();
		panel_6.setPreferredSize(new Dimension(900, 50));
	    panel_6.setMaximumSize(new Dimension(900, 50));
		panel_4.add(panel_6);
		
		etiquetaContrasena = new JLabel("Contrase\u00F1a");
		panel_6.add(etiquetaContrasena);
		
		textoContrasena = new JPasswordField();
		textoContrasena.setColumns(12);
		panel_6.add(textoContrasena);
		
		panel_7 = new JPanel();
		panel_7.setPreferredSize(new Dimension(900, 50));
	    panel_7.setMaximumSize(new Dimension(900, 50));
		panel_4.add(panel_7);
		
		aceptar = new JButton("Aceptar");
		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Controlador.getUnicaInstancia().loginUsuario(textoUser.getText(), textoContrasena.getText())) {
					ventana.actualizarLogin(textoUser.getText());
				}
				else {
					JOptionPane.showMessageDialog(PanelLogin.this, "Nombre de usuario o contrase�a no valido",
							"Error", JOptionPane.ERROR_MESSAGE);
				

				}
			}
		});
		panel_7.add(aceptar);
		
		cancelar = new JButton("Cancelar");
		panel_7.add(cancelar);
	}

	public void clean() {
		textoUser.setText("");
		textoContrasena.setText("");
		
	}

}
