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
import javax.swing.UIManager;
import javax.swing.border.BevelBorder;
import java.awt.FlowLayout;
import java.awt.SystemColor;

/*
 * Panel que va a permitir que el usuario inicie sesión mediante su nickname y contraseña. 
 */
public class PanelLogin extends JPanel {
	private JTextField textoUser;
	private JPanel panel_campos,panel_nombre,panel_contraseña,panel_boton;
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
		FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER, 0, Constantes.LOGIN_VERTICAL_GAP);
		flowLayout.setAlignOnBaseline(true);
		setLayout(flowLayout);
		
		panel_campos = new JPanel();
		panel_campos.setBackground(Color.LIGHT_GRAY);
		panel_campos.setBorder(UIManager.getBorder("Spinner.border"));
		add(panel_campos);
		panel_campos.setLayout(new BoxLayout(panel_campos, BoxLayout.Y_AXIS));
		
		panel_nombre = new JPanel();
		panel_nombre.setPreferredSize(new Dimension(900, 50));
		panel_campos.add(panel_nombre);
		
		etiquetaUsuario = new JLabel("Usuario");
		panel_nombre.add(etiquetaUsuario);
		
		textoUser = new JTextField();
		panel_nombre.add(textoUser);
		textoUser.setColumns(14);
		
		panel_contraseña = new JPanel();
		panel_campos.add(panel_contraseña);
		
		etiquetaContrasena = new JLabel("Contraseña");
		panel_contraseña.add(etiquetaContrasena);
		
		textoContrasena = new JPasswordField();
		textoContrasena.setColumns(12);
		panel_contraseña.add(textoContrasena);
		
		panel_boton = new JPanel();
		panel_campos.add(panel_boton);
		
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
		panel_boton.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 20));
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
