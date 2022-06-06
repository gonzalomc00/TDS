package tds.umu.vista;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.imageio.ImageIO;
import javax.swing.AbstractListModel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import java.awt.Color;
import javax.swing.border.EtchedBorder;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.CatalogoEtiquetas;
import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.Usuario;
import tds.video.VideoWeb;
import pulsador.Luz;

public class VentanaPrincipal extends JFrame implements ActionListener {

	private static VideoWeb videoWeb=Controlador.getUnicaInstancia().getReproductor();
	private JPanel contentPane,panel_superior,panel_botones,panel_central;
	private JButton explorar,mlistas,recientes,nlistas,logout,login,registro,premium, masvisto;
	private JComboBox<String> filtros_selecter;
	private JLabel etiqueta;
	private PanelExplorar PExplora;
	private PanelMisListas panel_mis_listas;
	private PanelRecientes PReciente;
	private PanelLogin Plogin;
	private Reproductor reproductor;
	private VentanaRegistro PRegistro;
	private PanelNuevaLista PNLista;
	private PanelMasVisto PMVisto;
	private Luz luz;
	private Controlador controlador= Controlador.getUnicaInstancia();
	private EstadoLogin estado;

	
	public VentanaPrincipal() {
		setResizable(false);
		reproductor= Reproductor.getUnicaInstancia();
		panel_mis_listas= new PanelMisListas(this);
		Plogin= new PanelLogin(this);
		PRegistro = new VentanaRegistro(this);
		PNLista= new PanelNuevaLista(this);
		PExplora= new PanelExplorar(this);
		PReciente= new PanelRecientes(this);
		PMVisto= new PanelMasVisto(this);

		
		setBounds(0,0,900,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		crearPanelSuperior();
		crearPanelBotones();
		
		panel_central.add(Plogin,BorderLayout.CENTER);
		contentPane.add(panel_central,BorderLayout.CENTER);
		cambiarEstado(EstadoLogin.LOGOUT);
	}



private void crearPanelSuperior()
{
	panel_superior = new JPanel();
	panel_superior.setMaximumSize(new Dimension(900,300));
	panel_superior.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.LIGHT_GRAY, null, Color.LIGHT_GRAY, null));
	panel_superior.setLayout(new BoxLayout(panel_superior,BoxLayout.X_AXIS));
	
	etiqueta = new JLabel("Inicia sesión o crea una nueva cuenta");
	etiqueta.setIcon(new ImageIcon("C:\\Users\\gonzi\\tds\\Prueba_mejor\\img\\dis.png"));
	panel_superior.add(etiqueta);
	repaint();
	
	panel_superior.add(Box.createRigidArea(new Dimension(105,40)));
	registro = new JButton("Registro");
	registro.addActionListener(this);

	panel_superior.add(registro);
	
	
	login = new JButton("login");
	login.addActionListener(this);
	
	panel_superior.add(login);
	panel_superior.add(Box.createRigidArea(new Dimension(30,40)));
	logout = new JButton("logout");
	logout.addActionListener(this);
	panel_superior.add(logout);
	
	panel_superior.add(Box.createRigidArea(new Dimension(30,40)));

	
	premium = new JButton("Premium");
	premium.addActionListener(this);
	premium.setForeground(Color.RED);
	panel_superior.add(premium);
	
	contentPane.add(panel_superior,BorderLayout.NORTH);
	
}

private void crearPanelBotones()
{
	panel_botones = new JPanel();
	panel_botones.setMaximumSize(new Dimension(900,300));
	panel_botones.setForeground(Color.WHITE);
	panel_botones.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
	
	panel_central = new JPanel();
	panel_central.setLayout(new BorderLayout(0, 0));
	
	panel_botones.setLayout(new BoxLayout(panel_botones, BoxLayout.X_AXIS));

	
	explorar = new JButton("Explorar");
	explorar.setForeground(Color.WHITE);
	explorar.setBackground(Color.LIGHT_GRAY);
	explorar.addActionListener(this);
	panel_botones.add(explorar);
	
	mlistas = new JButton("Mis Listas");
	mlistas.addActionListener(this);
	mlistas.setForeground(Color.WHITE);
	mlistas.setBackground(Color.LIGHT_GRAY);
	panel_botones.add(mlistas);
	
	recientes = new JButton("Recientes");
	recientes.addActionListener(this);
	recientes.setForeground(Color.WHITE);
	recientes.setBackground(Color.LIGHT_GRAY);
	panel_botones.add(recientes);
	
	nlistas = new JButton("Nueva Lista");
	nlistas.addActionListener(this);
	nlistas.setForeground(Color.WHITE);
	nlistas.setBackground(Color.LIGHT_GRAY);
	panel_botones.add(nlistas);
	

	masvisto= new JButton("Más Visto");
	masvisto.addActionListener(this);
	masvisto.setForeground(Color.WHITE);
	masvisto.setBackground(Color.LIGHT_GRAY);
	panel_botones.add(masvisto);
	
	filtros_selecter=new JComboBox<String>();
	//Añado los tipos de filtros que hay. Seguro que hay alguna forma mejor de hacerlo 
	filtros_selecter.addItem("NoFiltro");
	filtros_selecter.addItem("MisListas");
	filtros_selecter.addItem("Largos");
	filtros_selecter.addActionListener(this);
	panel_botones.add(filtros_selecter);
	
	luz = new Luz();
	luz.addEncendidoListener(controlador);
	panel_botones.add(luz);
	contentPane.add(panel_botones,BorderLayout.SOUTH);
	
	
}

@Override
public void actionPerformed(ActionEvent e) {
	
	if(e.getSource()==registro) {
		cambioPanel(Paneles.REGISTRO);
	}
	if(e.getSource()==login) {
		cambioPanel(Paneles.LOGIN);
	}
	if(e.getSource()==premium) {
		controlador.actualizarPremium();
		cambiarEstado(EstadoLogin.PREMIUM);
	}
	if(e.getSource()==explorar) {
		cambioPanel(Paneles.EXPLORAR);
	}
	if(e.getSource()==mlistas) {
		cambioPanel(Paneles.MISLISTAS);
	}
	if(e.getSource()==recientes)  {
		cambioPanel(Paneles.RECIENTE);
	}
	if(e.getSource()==nlistas) {
		cambioPanel(Paneles.NUEVALISTA);
	}
	if(e.getSource()==logout) {
		cierreSesion();
		cambioPanel(Paneles.LOGIN);
	}
	if(e.getSource()==masvisto) {
		cambioPanel(Paneles.MASVISTO);
	}
	if(e.getSource()==filtros_selecter) {
		controlador.cambiarFiltro((String)filtros_selecter.getSelectedItem());
	}
}

//Falta el apartado de recientes

//Función que cambia todo el panel central. Permite pasar de una ventana "general" hacia otra. Las ventanas generales son las que ocupan toda la ventana. 
public void cambioPanel(Paneles panel) {
	videoWeb.cancel();
	switch (panel) {
		case REPRODUCTOR:
		{
		reproductor.reproducir();
		cambio_panel_vista(reproductor);
		break;
		}
		case EXPLORAR:
			PExplora.renovar();
			PExplora.actualizarEtiquetasExplora();
			cambio_panel_vista(PExplora);
			break;
		case MISLISTAS:
			panel_mis_listas.clean();
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
			PNLista.clear();
			cambio_panel_vista(PNLista);
			break;
		case RECIENTE:
			PReciente.actualizarPanelLateral();
			cambio_panel_vista(PReciente);
			break;
		case MASVISTO:
			//MODIFICAR
			PMVisto.actualizarPanelLateral();
			cambio_panel_vista(PMVisto);
			break;
			
	default:
		break;
	}
}

public void cambio_panel_vista(JPanel panel) {
	panel_central.removeAll();
	panel_central.add(panel,BorderLayout.CENTER);
	panel_central.revalidate();
	panel_central.repaint();
	validate();
	return;
}


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
		
		
		this.estado=estado;
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
		
		this.estado=estado;
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
		this.estado=estado;
		break;
	}
	
}

public void actualizarLogin(String text) {
	etiqueta.setText("Hola "+text+"!");
	if(controlador.esUserPremium()) {
		cambiarEstado(EstadoLogin.PREMIUM);
	}
	else {
		cambiarEstado(EstadoLogin.LOGIN);
	}
	cambioPanel(Paneles.RECIENTE);

	
}

private void cierreSesion() {
	cambiarEstado(EstadoLogin.LOGOUT);
	controlador.logout();
	etiqueta.setText("Inicia sesión o crea una cuenta nueva");
}




}
