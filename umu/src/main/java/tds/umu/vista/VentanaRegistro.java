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

import com.toedter.calendar.JCalendar;

import tds.umu.controlador.Controlador;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.border.TitledBorder;

/*
 * Panel que se encarga de realizar el registro de un usuario y le permite introducir todos sus datos. 
 */
public class VentanaRegistro extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTextField campoNombre, campoApellidos, campoEmail, campoUsuario;
	private JPasswordField campoContra;
	private JPasswordField repetirContra;
	private final JCalendar calendar;

	private Controlador controlador = Controlador.getUnicaInstancia();

	/**
	 * Create the panel.
	 */
	public VentanaRegistro(VentanaPrincipal prin) {
		setBorder(null);
		FlowLayout flowLayout = (FlowLayout) getLayout();
		flowLayout.setVgap(80);

		JPanel panel_datos = new JPanel();
		panel_datos.setToolTipText("");
		panel_datos.setBackground(SystemColor.inactiveCaptionBorder);
		panel_datos.setBorder(null);
		add(panel_datos);
		panel_datos.setLayout(new BoxLayout(panel_datos, BoxLayout.Y_AXIS));

		JPanel panel_na = new JPanel();
		panel_na.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_datos.add(panel_na);
		panel_na.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel etiqueta_nombre = new JLabel("*Nombre  ");
		etiqueta_nombre.setHorizontalAlignment(SwingConstants.CENTER);
		panel_na.add(etiqueta_nombre);

		campoNombre = new JTextField();
		campoNombre.setAlignmentX(Component.LEFT_ALIGNMENT);
		campoNombre.setHorizontalAlignment(SwingConstants.LEFT);
		campoNombre.setColumns(10);
		panel_na.add(campoNombre);

		JLabel lblNewLabel_1 = new JLabel("*Apellidos");
		panel_na.add(lblNewLabel_1);
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);

		campoApellidos = new JTextField();
		panel_na.add(campoApellidos);
		campoApellidos.setText("");
		campoApellidos.setColumns(10);

		JPanel panel_fecha = new JPanel();
		panel_fecha.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_datos.add(panel_fecha);
		panel_fecha.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel etiqueta_fecha = new JLabel("Fecha de nacimiento");
		panel_fecha.add(etiqueta_fecha);

		calendar = new JCalendar();
		panel_fecha.add(calendar);

		JPanel panel_email = new JPanel();
		panel_email.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_datos.add(panel_email);
		panel_email.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel etiqueta_email = new JLabel("*Email");
		panel_email.add(etiqueta_email);

		campoEmail = new JTextField();
		campoEmail.setForeground(SystemColor.textInactiveText);
		campoEmail.setText("pepico@dominio.algo");
		campoEmail.setHorizontalAlignment(SwingConstants.CENTER);
		panel_email.add(campoEmail);
		campoEmail.setColumns(15);

		JPanel panel_user_contra = new JPanel();
		panel_user_contra.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_datos.add(panel_user_contra);
		panel_user_contra.setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));

		JLabel etiqueta_usuario = new JLabel("*Usuario");
		panel_user_contra.add(etiqueta_usuario);

		campoUsuario = new JTextField();
		campoUsuario.setForeground(SystemColor.textInactiveText);
		campoUsuario.setText("pepico45");
		panel_user_contra.add(campoUsuario);
		campoUsuario.setColumns(10);

		JLabel etiqueta_contra = new JLabel("*Contraseña");
		panel_user_contra.add(etiqueta_contra);

		campoContra = new JPasswordField();
		panel_user_contra.add(campoContra);
		campoContra.setColumns(10);

		JPanel panel_contra2 = new JPanel();
		panel_contra2.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		panel_datos.add(panel_contra2);
		panel_contra2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		Component rigidArea = Box.createRigidArea(new Dimension(100, 20));
		panel_contra2.add(rigidArea);

		JLabel etiqueta_contra2 = new JLabel("Repetir Contraseña");
		panel_contra2.add(etiqueta_contra2);
		etiqueta_contra2.setHorizontalAlignment(SwingConstants.RIGHT);

		repetirContra = new JPasswordField();
		panel_contra2.add(repetirContra);
		repetirContra.setColumns(10);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		panel_datos.add(rigidArea_1);

		JPanel panel_botones = new JPanel();
		panel_botones.setBorder(new TitledBorder(null, "", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		FlowLayout fl_panel_botones = (FlowLayout) panel_botones.getLayout();
		fl_panel_botones.setAlignOnBaseline(true);
		fl_panel_botones.setAlignment(FlowLayout.RIGHT);
		panel_datos.add(panel_botones);

		JButton botonRegistrarse = new JButton("Registrarse");
		botonRegistrarse.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

			}
		});
		panel_botones.add(botonRegistrarse);

		JButton botonCancelar = new JButton("Cancelar");
		panel_botones.add(botonCancelar);
		botonCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				campoNombre.setText("");
				campoApellidos.setText("");
				campoEmail.setText("");
				campoUsuario.setText("");
				campoContra.setText("");
				repetirContra.setText("");
				prin.cambioPanel(Paneles.LOGIN);
			}
		});

		/*
		 * Al hacer click en el botón de "Registrar" obtenemos todos los datos de todos
		 * los campos en los que se han introducido. y procedemos a comprobar que todos
		 * estén correctos. Si lo están, se pasan los datos al controlador para que este
		 * sea quien cree al Usuario e introduzca todos sus datos en la base de datos.
		 * Si los datos no están correctos, o no pudo ser posible el registro, entonces
		 * no se llevará a cabo el registro.
		 */
		botonRegistrarse.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				String nombre = campoNombre.getText();
				String apellidos = campoApellidos.getText();
				Date fecha = calendar.getDate();
				// https://www.javadevjournal.com/java/convert-date-to-localdate/#:~:text=%20How%20to%20Convert%20Date%20to%20LocalDate%20,Java.%20This...%203%20DateUtils%20for%20Conversion%20More%20
				LocalDate fechaNacimiento = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
				String email = campoEmail.getText();
				String usuario = campoUsuario.getText();
				String contra = new String(campoContra.getPassword());

				boolean OK = false;
				OK = checkFields();
				if (OK) {
					boolean registrado = false;
					registrado = controlador.registrarUsuario(nombre, apellidos, fechaNacimiento, email, usuario,
							contra);
					if (registrado) {
						JOptionPane.showMessageDialog(VentanaRegistro.this, "Usuario registrado correctamente.",
								"Registro", JOptionPane.INFORMATION_MESSAGE);
						prin.cambioPanel(Paneles.LOGIN);

					} else {
						JOptionPane.showMessageDialog(VentanaRegistro.this,
								"No se ha podido llevar a cabo el registro.\n", "Registro", JOptionPane.ERROR_MESSAGE);
					}
				}

			}

		});

	}

	/*
	 * Función que se utiliza para comprobar que todos los datos introducidos por el
	 * usuario cumplen con todas las características necesarias.
	 */
	private boolean checkFields() {
		boolean salida = true;
		String falta = "";

		if (campoNombre.getText().trim().isEmpty()) {
			falta = falta.concat("Debes introducir un nombre\n");
			salida = false;
		}
		if (campoApellidos.getText().trim().isEmpty()) {
			falta = falta.concat("Debes introducir apellidos\n");
			salida = false;
		}
		if (campoEmail.getText().trim().isEmpty()) {
			falta = falta.concat("Debes introducir un email\n");
			salida = false;
		}
		// Comprobamos que el formato del correo electrónico sea el correcto.
		if (!(campoEmail.getText().endsWith(".com") || campoEmail.getText().endsWith(".es")
				|| (campoEmail.getText().endsWith(".orgs"))) && (campoEmail.getText().contains("@"))) {
			falta = falta.concat("El correo electrónico introducido no es válido\n");
			salida = false;
		}
		if (campoUsuario.getText().trim().isEmpty()) {
			falta = falta.concat("Debes introducir un nombre de usuario\n");
			salida = false;
		}

		// Obtenemos el string de la contraseña y comprobamos que coincidan.
		String password = new String(campoContra.getPassword());
		String password2 = new String(repetirContra.getPassword());
		if (password.isEmpty()) {
			falta = falta.concat("Debes introducir una contraseña\n");
			salida = false;
		}
		if (password2.isEmpty()) {
			falta = falta.concat("Debes repetir la contraseña\n");
			salida = false;
		}
		if (!password.equals(password2)) {
			falta = falta.concat("Ambas contraseñas no coinciden\n");
			salida = false;
		}

		/*
		 * Comprobar que no exista otro usuario con el mismo nickname ya en la base de
		 * datos.
		 */
		if (!campoNombre.getText().trim().isEmpty() && controlador.esUsuarioRegistrado(campoUsuario.getText())) {
			falta = falta.concat("Ya existe ese usuario\n");
			salida = false;
		}

		// Obtenemos la fecha de nacimiento y comprobamos que no esté vacía y sea una
		// fecha posterior a la actual.
		Date fecha = calendar.getDate();
		LocalDate fechaNacimiento = fecha.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

		if (fechaNacimiento == null) {
			falta = falta.concat("Introduce una fecha de nacimiento\n");
			salida = false;
		}
		if (fechaNacimiento.isAfter(LocalDate.now()) && fechaNacimiento != null) {
			falta = falta.concat("La fecha de nacimiento es incorrecta\n");
			salida = false;
		}

		// Mostramos una ventana con todos los datos que faltan.
		if (!salida) {
			JOptionPane.showMessageDialog(VentanaRegistro.this, falta, "Error", JOptionPane.ERROR_MESSAGE);
		}
		return salida;
	}

}
