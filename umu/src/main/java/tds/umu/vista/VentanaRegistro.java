package tds.umu.vista;



import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.FlowLayout;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;
import java.awt.BorderLayout;
import com.toedter.calendar.JCalendar;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.Usuario;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class VentanaRegistro extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField campoNombre;
	private JTextField campoApellidos;
	private JTextField campoEmail;
	private JTextField campoUsuario;
	private JPasswordField campoContra;
	private VentanaPrincipal prin;
	private JPasswordField repetirContra;
	private final JCalendar calendar;

	/**
	 * Create the panel.
	 */
	public VentanaRegistro(VentanaPrincipal prin) {
		this.prin=prin; //TODO mirar donde se usa en el otro  fichero
		setBorder(new SoftBevelBorder(BevelBorder.LOWERED, null, null, null, null));
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(80);
		
		JPanel panel = new JPanel();
		panel.setToolTipText("");
		panel.setBackground(SystemColor.inactiveCaptionBorder);
		panel.setBorder(new SoftBevelBorder(BevelBorder.RAISED, SystemColor.infoText, null, null, null));
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Nombre  ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel_1.add(lblNewLabel);
		
		campoNombre = new JTextField();
		campoNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
		campoNombre.setHorizontalAlignment(SwingConstants.LEFT);
		campoNombre.setColumns(10);
		panel_1.add(campoNombre);
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("Apellidos");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(lblNewLabel_1);
		
		campoApellidos = new JTextField();
		campoApellidos.setText("");
		panel_2.add(campoApellidos);
		campoApellidos.setColumns(10);
		
		JPanel panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel_2 = new JLabel("Fecha de nacimiento");
		panel_3.add(lblNewLabel_2);
		
		calendar = new JCalendar();
		panel_3.add(calendar);
		
		JPanel panel_4 = new JPanel();
		panel.add(panel_4);
		panel_4.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel_3 = new JLabel("Email");
		panel_4.add(lblNewLabel_3);
		
		campoEmail = new JTextField();
		campoEmail.setForeground(SystemColor.textInactiveText);
		campoEmail.setText("pepico@dominio.algo");
		campoEmail.setHorizontalAlignment(SwingConstants.CENTER);
		panel_4.add(campoEmail);
		campoEmail.setColumns(15);
		
		JPanel panel_5 = new JPanel();
		panel.add(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel_4 = new JLabel("Usuario");
		panel_5.add(lblNewLabel_4);
		
		campoUsuario = new JTextField();
		campoUsuario.setForeground(SystemColor.textInactiveText);
		campoUsuario.setText("pepico45");
		panel_5.add(campoUsuario);
		campoUsuario.setColumns(10);
		
		JPanel panel_6 = new JPanel();
		panel_5.add(panel_6);
		panel_6.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
		
		JLabel lblNewLabel_5 = new JLabel("Contrase\u00F1a");
		panel_6.add(lblNewLabel_5);
		
		campoContra = new JPasswordField();
		campoContra.setColumns(10);
		panel_6.add(campoContra);
		
		JPanel panel_8 = new JPanel();
		panel.add(panel_8);
		panel_8.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		Component rigidArea = Box.createRigidArea(new Dimension(100, 20));
		panel_8.add(rigidArea);
		
		JPanel panel_9 = new JPanel();
		panel_8.add(panel_9);
		
		JLabel lblNewLabel_6 = new JLabel("Repetir Contraseña");
		lblNewLabel_6.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_9.add(lblNewLabel_6);
		
		repetirContra = new JPasswordField();
		repetirContra.setColumns(10);
		repetirContra.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_9.add(repetirContra);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel.add(rigidArea_1);
		
		JPanel panel_7 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) panel_7.getLayout();
		flowLayout_1.setAlignOnBaseline(true);
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		panel.add(panel_7);
		
		JButton botonRegistrarse = new JButton("Registrarse");
		botonRegistrarse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
				
			}
		});
		panel_7.add(botonRegistrarse);
		
		JButton botonCancelar = new JButton("Cancelar");
		panel_7.add(botonCancelar);
		botonCancelar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				campoNombre.setText("");
				campoApellidos.setText("");
				campoEmail.setText("");
				campoUsuario.setText("");
				campoContra.setText("");
				repetirContra.setText("");
			}
		});
		
		
		
		botonRegistrarse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre= campoNombre.getText();
				String apellidos= campoApellidos.getText();
				Date fecha= calendar.getDate();
				//https://www.javadevjournal.com/java/convert-date-to-localdate/#:~:text=%20How%20to%20Convert%20Date%20to%20LocalDate%20,Java.%20This...%203%20DateUtils%20for%20Conversion%20More%20
				LocalDate fechaNacimiento= fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String email= campoEmail.getText();
				String usuario= campoUsuario.getText();
				String contra= new String(campoContra.getPassword());
				String contraRepe= new String(repetirContra.getPassword());
				
				boolean OK = false;
				OK = checkFields();
				if (OK) {
					boolean registrado = false;
					registrado = Controlador.getUnicaInstancia().registrarUsuario(nombre, apellidos,fechaNacimiento,email, usuario, contra);
						if (registrado) {
							JOptionPane.showMessageDialog(VentanaRegistro.this, "Usuario registrado correctamente.", "Registro",
									JOptionPane.INFORMATION_MESSAGE);
	
						} else {
							JOptionPane.showMessageDialog(VentanaRegistro.this, "No se ha podido llevar a cabo el registro.\n",
									"Registro", JOptionPane.ERROR_MESSAGE);
						}
					}
				
	
						
				}
			
		});
		
		
	}
	private boolean checkFields() {
		boolean salida = true;
		String falta="";
		
		if (campoNombre.getText().trim().isEmpty()) {
			falta=falta.concat("Debes introducir un nombre\n");
			salida = false;
		}
		if (campoApellidos.getText().trim().isEmpty()) {
			falta=falta.concat("Debes introducir apellidos\n");
			salida = false;
		}
		if (campoEmail.getText().trim().isEmpty()) {
			falta=falta.concat("Debes introducir un email\n");
			salida = false;
		}
		if(!(campoEmail.getText().endsWith(".com")||campoEmail.getText().endsWith(".es")||(campoEmail.getText().endsWith(".orgs")))&&(campoEmail.getText().contains("@"))) {		
			falta=falta.concat("El correo electrónico introducido no es válido\n");
			salida =false;
		}
		if (campoUsuario.getText().trim().isEmpty()) {
			falta=falta.concat("Debes introducir un nombre de usuario\n");
			salida = false;
		}
		String password = new String(campoContra.getPassword());
		String password2 = new String(repetirContra.getPassword());
		if (password.isEmpty()) {
			falta=falta.concat("Debes introducir una contraseña\n");
			salida = false;
		} 
		if (password2.isEmpty()) {
			falta=falta.concat("Debes repetir la contraseña\n");
			salida = false;
		} 
		if (!password.equals(password2)) {
			falta=falta.concat("Ambas contraseñas no coinciden\n");
			salida = false;
		}
		/* Comprobar que no exista otro usuario con igual login */
		if (!campoNombre.getText().trim().isEmpty() && Controlador.getUnicaInstancia().esUsuarioRegistrado(campoUsuario.getText())) {
			falta=falta.concat("Ya existe ese usuario\n");
			salida = false;
		}
		Date fecha= calendar.getDate();
		LocalDate fechaNacimiento= fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (fechaNacimiento==null) {
			falta=falta.concat("Introduce una fecha de nacimiento\n");
			salida = false;
		}
		if(fechaNacimiento.isAfter(LocalDate.now()) && fechaNacimiento!=null) {
			falta=falta.concat("La fecha de nacimiento es incorrecta\n");
			salida=false;
		}

		if(!salida) {
		JOptionPane.showMessageDialog(VentanaRegistro.this, falta,
				"Error", JOptionPane.ERROR_MESSAGE);
		}
		return salida;
	}
	

}
