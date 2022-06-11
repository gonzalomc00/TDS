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

/*
 * Panel que va a permitir que el usuario inicie sesión mediante su nickname y contraseña. 
 */
public class PanelLogin extends JPanel {
	private JTextField textoUser;
	private JPanel panel_relleno_izq,panel_relleno_sup,panel_relleno_der,panel_relleno_inf,panel_campos,panel_nombre,panel_contraseña,panel_boton;
	private JLabel etiquetaUsuario,etiquetaContrasena;
	private JButton aceptar;
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
		
		panel_relleno_izq = new JPanel();
		panel_relleno_izq.setBackground(Color.GRAY);
		panel_relleno_izq.add(Box.createRigidArea(new Dimension(220, 70)));
		add(panel_relleno_izq, BorderLayout.WEST);
		
		panel_relleno_sup = new JPanel();
		panel_relleno_sup.setBackground(Color.GRAY);
		panel_relleno_sup.add(Box.createRigidArea(new Dimension(105, 150)));
		add(panel_relleno_sup, BorderLayout.NORTH);
		
		panel_relleno_der = new JPanel();
		panel_relleno_der.setBackground(Color.GRAY);
		panel_relleno_der.add(Box.createRigidArea(new Dimension(220, 70)));
		add(panel_relleno_der, BorderLayout.EAST);
		
		panel_relleno_inf = new JPanel();
		panel_relleno_inf.setBackground(Color.GRAY);
		panel_relleno_inf.add(Box.createRigidArea(new Dimension(105, 190)));
		add(panel_relleno_inf, BorderLayout.SOUTH);
		
		panel_campos = new JPanel();
		panel_campos.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(panel_campos, BorderLayout.CENTER);
		panel_campos.setLayout(new BorderLayout(0, 0));
		
		panel_nombre = new JPanel();
		panel_nombre.setPreferredSize(new Dimension(900, 50));
		panel_campos.add(panel_nombre, BorderLayout.NORTH);
		
		etiquetaUsuario = new JLabel("Usuario");
		panel_nombre.add(etiquetaUsuario);
		
		textoUser = new JTextField();
		panel_nombre.add(textoUser);
		textoUser.setColumns(14);
		
		panel_contraseña = new JPanel();
		panel_campos.add(panel_contraseña, BorderLayout.CENTER);
		
		etiquetaContrasena = new JLabel("Contraseña");
		panel_contraseña.add(etiquetaContrasena);
		
		textoContrasena = new JPasswordField();
		textoContrasena.setColumns(12);
		panel_contraseña.add(textoContrasena);
		
		panel_boton = new JPanel();
		panel_campos.add(panel_boton, BorderLayout.SOUTH);
		
		aceptar = new JButton("Aceptar");
		
		/*
		 * Al hacer click sobre el botón Aceptar, se pasan los datos de inicio de sesión al controlador quien comprobará si estos son correctos. Si lo son, 
		 * entonces se procede a establecer al usuario como usuario_actual en el controlador y se pasa al panel de Recientes (método actualizarLogin())
		 */
		aceptar.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(Controlador.getUnicaInstancia().loginUsuario(textoUser.getText(), textoContrasena.getText())) {
					ventana.actualizarLogin(textoUser.getText());
				}
				else {
					JOptionPane.showMessageDialog(PanelLogin.this, "Nombre de usuario o contraseña no valido",
							"Error", JOptionPane.ERROR_MESSAGE);
				

				}
			}
		});
		panel_boton.add(aceptar);
	}

	/*
	 * Método para limpiar los campos de datos
	 */
	public void clean() {
		textoUser.setText("");
		textoContrasena.setText("");
		
	}

}
