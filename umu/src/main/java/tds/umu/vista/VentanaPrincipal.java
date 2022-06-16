package tds.umu.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Color;
import tds.umu.controlador.Controlador;
import tds.video.VideoWeb;
import pulsador.Luz;

/*
 * Ventana principal de nuestra aplicación. Se encargará de inicializar todos los paneles necesarios para la aplicación y de mostrarlos correctamente en pantalla
 * Además mantiene el resto de elementos de la pantalla: los botones para el intercambio y la barra superior. 
 * 
 * El estado de esta ventana restringirá los botones que se podrán pulsar, estando algunos bloqueados, por ejemplo, si el usuario todavía no ha iniciado sesión o si no es premium. 
 */
public class VentanaPrincipal extends JFrame implements ActionListener {

	private static VideoWeb videoWeb = Controlador.getUnicaInstancia().getReproductor();
	private JPanel contentPane, panel_superior, panel_botones, panel_central;
	private JButton explorar, mlistas, recientes, nlistas, logout, login, registro, premium, masvisto;
	private JComboBox<String> filtros_selecter;
	private JLabel etiqueta;
	private PanelExplorar_NLista PExplora_NLista;
	private PanelMisListas panel_mis_listas;
	private PanelRecientes PReciente;
	private PanelLogin Plogin;
	private Reproductor reproductor;
	private VentanaRegistro PRegistro;
	private PanelMasVisto PMVisto;
	private Luz luz;
	private Controlador controlador = Controlador.getUnicaInstancia();

	/*
	 * Constructor de la ventana principal, inicializa todos los paneles, crea la
	 * propia ventana y llama a los distintos métodos para construir todos sus
	 * elementos
	 */
	public VentanaPrincipal() {

		// Creamos todos los paneles necesarios. El panel Reproductor es creado mediante
		// el patrón Singleton ya que, si tenemos varias instancias del mismo, su
		// contenido no se ve correctamente
		// Por eso, hemos optado tener una única instancia que sea compartida por todos
		// los paneles que lo necesiten.
		reproductor = Reproductor.getUnicaInstancia();
		Plogin = new PanelLogin(this);
		PRegistro = new VentanaRegistro(this);
		PExplora_NLista = new PanelExplorar_NLista(this);
		panel_mis_listas = new PanelMisListas();
		PReciente = new PanelRecientes();
		PMVisto = new PanelMasVisto();

		// Por defecto, el tamaño de la ventana no es modificable en tiempo de
		// ejecución.
		setResizable(false);
		setBounds(0, 0, 1270, 720);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBackground(Color.LIGHT_GRAY);
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		crearPanelSuperior();
		crearPanelBotones();

		panel_central.add(Plogin, BorderLayout.CENTER);
		contentPane.add(panel_central, BorderLayout.CENTER);
		cambiarEstado(EstadoLogin.LOGOUT);
	}

	/*
	 * Función que crea todos los elementos del panel superior de la ventana
	 * principal
	 */
	private void crearPanelSuperior() {
		panel_superior = new JPanel();
		panel_superior.setForeground(Color.WHITE);
		panel_superior.setBorder(UIManager.getBorder("Spinner.border"));
		panel_superior.setPreferredSize(new Dimension(900, 40));
		panel_superior.setLayout(new BoxLayout(panel_superior, BoxLayout.X_AXIS));

		etiqueta = new JLabel("Inicia sesión o crea una nueva cuenta");
		ImageIcon imagen = new ImageIcon("resources/AppVideo_logo.png");
		Image image = imagen.getImage();
		Image newimg = image.getScaledInstance(120, 40, java.awt.Image.SCALE_SMOOTH);
		imagen = new ImageIcon(newimg);
		etiqueta.setIcon(imagen);
		panel_superior.add(etiqueta);
		repaint();

		panel_superior.add(Box.createRigidArea(new Dimension(105, 40)));
		registro = new JButton("Registro");
		registro.addActionListener(this);

		panel_superior.add(registro);

		login = new JButton("login");
		login.addActionListener(this);

		panel_superior.add(login);
		panel_superior.add(Box.createRigidArea(new Dimension(30, 40)));
		logout = new JButton("logout");
		logout.addActionListener(this);
		panel_superior.add(logout);

		panel_superior.add(Box.createRigidArea(new Dimension(30, 40)));

		premium = new JButton("Premium");
		premium.addActionListener(this);
		premium.setForeground(Color.RED);
		panel_superior.add(premium);

		contentPane.add(panel_superior, BorderLayout.NORTH);

	}

	/*
	 * Función que crea el panel inferior, en el que se encuentran los botones para
	 * el cambio de panel
	 */
	private void crearPanelBotones() {
		panel_botones = new JPanel();
		panel_botones.setForeground(Color.RED);
		panel_botones.setBorder(UIManager.getBorder("Spinner.border"));
		panel_botones.setMaximumSize(new Dimension(900, 300));

		panel_central = new JPanel();
		panel_central.setLayout(new BorderLayout(0, 0));

		panel_botones.setLayout(new BoxLayout(panel_botones, BoxLayout.X_AXIS));

		explorar = new JButton("Explorar");

		explorar.addActionListener(this);
		panel_botones.add(explorar);

		mlistas = new JButton("Mis Listas");
		mlistas.addActionListener(this);

		panel_botones.add(mlistas);

		recientes = new JButton("Recientes");
		recientes.addActionListener(this);

		panel_botones.add(recientes);

		nlistas = new JButton("Nueva Lista");
		nlistas.addActionListener(this);

		panel_botones.add(nlistas);

		masvisto = new JButton("Más Visto");
		masvisto.addActionListener(this);

		panel_botones.add(masvisto);

		// Añadimos todos los filtros que sean necesarios, según los que estén
		// disponibles
		filtros_selecter = new JComboBox<String>();
		filtros_selecter.addItem("NoFiltro");
		filtros_selecter.addItem("MisListas");
		filtros_selecter.addItem("Largos");
		filtros_selecter.addActionListener(this);
		panel_botones.add(filtros_selecter);

		// Componente Luz, proporcionado por los profesores de la asignatura. Cuando sea
		// pulsado se notificará al controlador, el cual se encargará de cargar los
		// vídeos.
		luz = new Luz();
		luz.addEncendidoListener(controlador);
		panel_botones.add(luz);
		contentPane.add(panel_botones, BorderLayout.SOUTH);

	}

	/*
	 * Dependiendo de la fuente del evento que acaba de surgir (p.e. pulsar un botón
	 * de la pantalla), se llevará a cabo unas acciones u otras. La mayoria llaman a
	 * la función para cambiar el panel central
	 */
	@Override
	public void actionPerformed(ActionEvent e) {

		if (e.getSource() == registro) {
			cambioPanel(Paneles.REGISTRO);
		}
		if (e.getSource() == login) {
			cambioPanel(Paneles.LOGIN);
		}
		if (e.getSource() == premium) {
			int input = JOptionPane.showConfirmDialog(null,
					"¿Quieres actualizar tu cuenta y pasar a ser un usuario premium? Podrás: \n -Saber cuáles son los video más vistos \n -Aplicar filtros a tus búsquedas \n -Imprimir un PDF con tus listas",
					"Actualizar a premium", JOptionPane.YES_NO_OPTION);
			if (input == 0) {
				controlador.actualizarPremium();
				cambiarEstado(EstadoLogin.PREMIUM);
			}

		}
		if (e.getSource() == explorar) {
			cambioPanel(Paneles.EXPLORAR);
		}
		if (e.getSource() == mlistas) {
			cambioPanel(Paneles.MISLISTAS);
		}
		if (e.getSource() == recientes) {
			cambioPanel(Paneles.RECIENTE);
		}
		if (e.getSource() == nlistas) {
			cambioPanel(Paneles.NUEVALISTA);
		}
		if (e.getSource() == logout) {
			cierreSesion();
			cambioPanel(Paneles.LOGIN);
		}
		if (e.getSource() == masvisto) {
			cambioPanel(Paneles.MASVISTO);
		}
		if (e.getSource() == filtros_selecter) {
			controlador.cambiarFiltro((String) filtros_selecter.getSelectedItem());
		}
	}

	/*
	 * Función que ejecuta una función u otra dependiendo de para qué panel haya
	 * sido llamada. Para los paneles que tienen listas, barras de búsqueda o algún
	 * texto se llama a la respectiva función que realizar el proceso de limpieza de
	 * los mismos, de forma que el contenido que había antes del cambio siga
	 * visionándose. Además si las listas tienen que mostrar algo automáticamente al
	 * ser mostradas por pantalla, también llama al método indicado para realizar
	 * esta actualización.
	 */
	public void cambioPanel(Paneles panel) {
		videoWeb.cancel();

		// Hacemos esto para evitar que una lista de reproducción a la que hemos
		// indicado reproducción automática continue reproduciendose incluso estando en
		// una ventana diferente.
		panel_mis_listas.clean();
		switch (panel) {
		case REPRODUCTOR: {
			reproductor.reproducir();
			cambio_panel_vista(reproductor);
			break;
		}
		case EXPLORAR:
			PExplora_NLista.modoExplorar();
			PExplora_NLista.renovar();
			PExplora_NLista.actualizarEtiquetasExplora();
			cambio_panel_vista(PExplora_NLista);
			break;
		case MISLISTAS:
			panel_mis_listas.actualizarPlayLists();
			cambio_panel_vista(panel_mis_listas);
			break;
		case LOGIN:
			cambio_panel_vista(Plogin);
			break;
		case REGISTRO:
			cambio_panel_vista(PRegistro);
			break;
		case NUEVALISTA:
			PExplora_NLista.renovar();
			PExplora_NLista.modoNuevaLista();
			cambio_panel_vista(PExplora_NLista);
			break;
		case RECIENTE:
			PReciente.clean();
			PReciente.actualizarPanelLateral();
			cambio_panel_vista(PReciente);
			break;
		case MASVISTO:
			PMVisto.clean();
			PMVisto.actualizarPanelLateral();
			cambio_panel_vista(PMVisto);
			break;

		default:
			break;
		}
	}

	/*
	 * Método que se encarga de realizar el cambio efectivo del panel central:
	 * cambia un panel por otro y vuelve a pintar el panel que lo contiene.
	 */
	private void cambio_panel_vista(JPanel panel) {
		panel_central.removeAll();
		panel_central.add(panel, BorderLayout.CENTER);
		panel_central.revalidate();
		panel_central.repaint();
		validate();
		return;
	}

	/*
	 * Función que se encarga de actualizar los botones que son interactuables
	 * dependiendo del estado en el que se encuentre el usuario
	 */
	private void cambiarEstado(EstadoLogin estado) {
		switch (estado) {

		case LOGOUT:
			explorar.setEnabled(false);
			mlistas.setEnabled(false);
			recientes.setEnabled(false);
			nlistas.setEnabled(false);
			logout.setEnabled(false);
			premium.setEnabled(false);
			masvisto.setEnabled(false);
			registro.setEnabled(true);
			login.setEnabled(true);
			filtros_selecter.setEnabled(false);

			break;

		case LOGIN:
			explorar.setEnabled(true);
			mlistas.setEnabled(true);
			recientes.setEnabled(true);
			nlistas.setEnabled(true);
			logout.setEnabled(true);
			premium.setEnabled(true);
			login.setEnabled(false);
			registro.setEnabled(false);
			masvisto.setEnabled(false);
			filtros_selecter.setEnabled(false);

			break;

		case PREMIUM:
			explorar.setEnabled(true);
			mlistas.setEnabled(true);
			recientes.setEnabled(true);
			nlistas.setEnabled(true);
			logout.setEnabled(true);
			premium.setEnabled(false);
			login.setEnabled(false);
			registro.setEnabled(false);
			masvisto.setEnabled(true);
			filtros_selecter.setEnabled(true);
			break;
		}

	}

	/*
	 * Función que se encarga de actualizar el estado de esta ventana después de
	 * hacer login. Llama a la función de cambiar el estado propio de la ventana y
	 * cambia la etiqueta superior
	 */
	public void actualizarLogin(String text) {
		etiqueta.setText("¡Hola " + text + "!");
		if (controlador.esUserPremium()) {
			cambiarEstado(EstadoLogin.PREMIUM);
		} else {
			cambiarEstado(EstadoLogin.LOGIN);
		}
		cambioPanel(Paneles.RECIENTE);

	}

	/* Método análogo al anterior, pero cuando se realizar logout */
	private void cierreSesion() {
		cambiarEstado(EstadoLogin.LOGOUT);
		Plogin.clean();
		controlador.logout();
		etiqueta.setText("Inicia sesión o crea una cuenta nueva");
	}

}
