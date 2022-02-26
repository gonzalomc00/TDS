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

public class VentanaPrincipal extends JFrame implements ActionListener {

	private JPanel contentPane,panel_superior,panel_botones,panel_central;
	private JButton explorar,mlistas,recientes,nlistas,logout,login,registro,premium;
	private JLabel etiqueta;
	private PanelMisListas panel_mis_listas;
	private PanelLogin Plogin;
	private VentanaRegistro PRegistro;
	private PanelNuevaLista PNLista;
	private Usuario user_actual;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaPrincipal frame = new VentanaPrincipal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @throws IOException 
	 */
	public VentanaPrincipal() throws IOException {
		setResizable(false);
		panel_mis_listas= new PanelMisListas(this);
		Plogin= new PanelLogin(this);
		PRegistro = new VentanaRegistro(this);
		PNLista= new PanelNuevaLista(this);
		
		setBounds(0,0,900,600);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		crearPanelSuperior();
		crearPanelBotones();
		
		panel_central.add(Plogin,BorderLayout.CENTER);
		contentPane.add(panel_central,BorderLayout.CENTER);
		
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
	registro.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			cambioPanel(PRegistro);
			return;
		}
	});
	panel_superior.add(registro);
	
	login = new JButton("login");
	panel_superior.add(login);
	login.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			cambioPanel(Plogin);
			return;
			
		}
	});
	
	panel_superior.add(Box.createRigidArea(new Dimension(30,40)));
	logout = new JButton("logout");
	panel_superior.add(logout);
	
	panel_superior.add(Box.createRigidArea(new Dimension(30,40)));

	
	premium = new JButton("Premium");
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
	
	explorar = new JButton("Explorar");
	explorar.setForeground(Color.WHITE);
	explorar.setBackground(Color.LIGHT_GRAY);
	explorar.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			cambioPanel(new PanelExplorar(VentanaPrincipal.this));
			return;
			
		}
	});
	panel_botones.setLayout(new BoxLayout(panel_botones, BoxLayout.X_AXIS));
	panel_botones.add(explorar);
	
	mlistas = new JButton("Mis Listas");
	mlistas.setForeground(Color.WHITE);
	mlistas.setBackground(Color.LIGHT_GRAY);
	mlistas.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			cambioPanel(panel_mis_listas);
			return;
		}
	});
	panel_botones.add(mlistas);
	
	recientes = new JButton("Recientes");
	recientes.setForeground(Color.WHITE);
	recientes.setBackground(Color.LIGHT_GRAY);
	panel_botones.add(recientes);
	
	nlistas = new JButton("Nueva Lista");
	nlistas.addMouseListener(new MouseAdapter() {
		@Override
		public void mouseClicked(MouseEvent e) {
			cambioPanel(PNLista);
		}
	});
	nlistas.setForeground(Color.WHITE);
	nlistas.setBackground(Color.LIGHT_GRAY);
	panel_botones.add(nlistas);
	contentPane.add(panel_botones,BorderLayout.SOUTH);
}
	
public void cambioPanel(JPanel panel) {
	panel_central.removeAll();
	panel_central.add(panel,BorderLayout.CENTER);
	panel_central.revalidate();
	panel_central.repaint();
	validate();
	return;
}

@Override
public void actionPerformed(ActionEvent e) {

}

public void actualizarLogin(String text) {
	etiqueta.setText("Hola "+text+"!");
	cambioPanel(new PanelExplorar(this));

	
}



}
