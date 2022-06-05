package tds.umu.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.*;
import tds.video.VideoWeb;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.security.AccessController;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.CardLayout;
//import com.jgoodies.forms.layout.FormLayout;
//import com.jgoodies.forms.layout.ColumnSpec;
//import com.jgoodies.forms.layout.FormSpecs;
//import com.jgoodies.forms.layout.RowSpec;
import java.awt.GridLayout;
import javax.swing.UIManager;

import com.itextpdf.kernel.font.PdfFont;
//PARA GENERAR PDF (SE QUE AQUI NO VA PERO LO DEJO PARA COPIARLO A DONDE SEA)
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

public class PanelMisListas extends JPanel {

	private VentanaPrincipal ventana;
	private JPanel panel, panel_1, panel_2, panel_4, panel_5;
	private JLabel etiquetaSeleccion;
	private JComboBox<String> comboBox;
	private JButton bReproducir, bCancelar;
	private JPanel panel_3;
	private JPanel panel_6;
	private Controlador controlador = Controlador.getUnicaInstancia();
	private JList lista_videos;
	private JScrollPane barra_deslizadora;
	private DefaultListModel<VideoRepresent> modeloListaVideos = new DefaultListModel<VideoRepresent>();
	private VideoWeb videoWeb = controlador.getReproductor();
	private Reproductor reproductor;
	private List<ListaVideos> vlists_encontradas;
	private ListaVideos vlist_seleccionada;
	private Timer timer;
	private JButton bPDF;

	public PanelMisListas(VentanaPrincipal ventana) {

		ventana = ventana;
		crearPantalla();
		// No sé si esto se puede hacer
		reproductor = Reproductor.getUnicaInstancia();
	}

	public void crearPantalla() {
		setBackground(Color.GRAY);
		setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		panel.setBackground(Color.GRAY);
		add(panel, BorderLayout.WEST);
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel();
		panel_1.setBackground(Color.GRAY);
		panel_1.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_1, BorderLayout.NORTH);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		etiquetaSeleccion = new JLabel("Seleccione la lista");
		etiquetaSeleccion.setAlignmentX(Component.CENTER_ALIGNMENT);
		etiquetaSeleccion.setForeground(Color.WHITE);
		panel_1.add(etiquetaSeleccion);

		comboBox = new JComboBox<String>();
		comboBox.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String selected = (String) comboBox.getSelectedItem();
				if (selected != null) {
					actualizarPanelLateral(vlists_encontradas.get(comboBox.getSelectedIndex()));
				}
			}
		});
		panel_1.add(comboBox);

		panel_4 = new JPanel();
		panel_4.setBackground(Color.GRAY);
		panel_1.add(panel_4);

		bReproducir = new JButton("Reproducir");
		bReproducir.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				cambiarPanelRep();
				// reproducirPlayListCompleta();

			}

		});
		panel_4.add(bReproducir);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.GRAY);
		panel_2.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel.add(panel_2, BorderLayout.SOUTH);
		panel_2.setLayout(new BorderLayout(0, 0));

		bCancelar = new JButton("Cancelar");
		panel_2.add(bCancelar, BorderLayout.NORTH);

		bPDF = new JButton("Generar PDF");
		bPDF.setForeground(Color.RED);
		panel_2.add(bPDF);

		bPDF.addActionListener(ev -> {
			crearPDF();
		});

		panel_3 = new JPanel();
		panel_3.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		panel_3.setBackground(Color.GRAY);
		panel.add(panel_3, BorderLayout.WEST);
		panel_3.setLayout(new BorderLayout(0, 0));

		lista_videos = new JList<VideoRepresent>();
		lista_videos.setBackground(Color.GRAY);
		lista_videos.setForeground(Color.WHITE);
		lista_videos.setVisibleRowCount(-1);
		lista_videos.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		lista_videos.setModel(modeloListaVideos);
		lista_videos.setCellRenderer(new VideoRenderer());

		lista_videos.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent event) {

				/*
				 * No se si aquí debería buscar en la base de datos apartir del nombre o algo, o
				 * si deberia incluir la clase que representa a los videos el video en sí. No se
				 * si rompe algun patron
				 */
				if (event.getClickCount() == 2) {
					JList<VideoRepresent> source = (JList<VideoRepresent>) event.getSource();
					VideoRepresent selected = source.getSelectedValue();
					if (selected != null) {
						Video v = vlist_seleccionada.getVideoIndex(lista_videos.getSelectedIndex());
						controlador.actualizarVideoSeleccionado(v);
						reproductor.reproducir();
						cambiarPanelRep();

					}
				}
			}
		});

		barra_deslizadora = new JScrollPane(lista_videos);
		barra_deslizadora.setBackground(Color.GRAY);
		panel.add(barra_deslizadora, BorderLayout.CENTER);

	}

	private void crearPDF() {
		//TODO Hay que mirar un directorio raiz que no sea violao por los permisos de momento si pones tu user aparece
		String raiz = "C:\\Users\\Trini\\Lista.pdf";
		PdfWriter writer = null;
		try {
			writer = new PdfWriter(raiz);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		PdfDocument doc = new PdfDocument(writer);
		doc.addNewPage();
		
		Document document = new Document(doc);
		Paragraph titulo= new Paragraph(" RECOPILACIÓN DE MIS LISTAS DE REPRODUCCIÓN ");
		titulo.setItalic();
		titulo.setBold();
		
		document.add(titulo);
		//TODO No sé si esto está bien así 
		String usuario = "Nombre del usuario: " + controlador.getUsuarioActual().getNombre() +

				"\n" + "Apellidos del usuario: " + controlador.getUsuarioActual().getApellidos() + "\n"
				+ "Email del usuario: " + controlador.getUsuarioActual().getEmail() + "\n" + "Fecha de nacimiento: "
				+ controlador.getUsuarioActual().getFecha();

		document.add(new Paragraph(usuario));
		document.add(new Paragraph("\n"));

		document.add(new Paragraph("MIS LISTAS: "+"\n\n"));

		List<ListaVideos> videos = controlador.obtenerPlayListsUser();

		//Tampoco sé si esto iría aquí o si violamos algun patrón
		for (ListaVideos v : videos) {
			document.add(new Paragraph("Lista de reproduccion: " + v.getNombre()));
			for (Video vid : v.getVideos()) {
				document.add(new Paragraph("Video: " + vid.getTitulo() + "\n Enlace: " + vid.getUrl()
						+ "\n Numero de reproducciones: " + vid.getNumReproducciones()));
			}
			document.add(new Paragraph("\n\n"));
		}
		document.close();
		JOptionPane.showMessageDialog(null, "El PDF se ha generado correctamente en  C:\\Usuarios\\TuNombre\\Lista.pdf :)).", "PDF generado :)",JOptionPane.INFORMATION_MESSAGE);

	}

	public void actualizarPlayLists() {
		vlists_encontradas = controlador.obtenerPlayListsUser();
		vlists_encontradas.stream().forEach(l -> comboBox.addItem(l.getNombre()));

	}

	private void actualizarPanelLateral(ListaVideos list) {
		modeloListaVideos.removeAllElements();
		list.getVideos().stream().map(v -> new VideoRepresent(v, videoWeb.getSmallThumb(v.getUrl())))
				.forEach(nv -> modeloListaVideos.addElement(nv));
		validate();
		vlist_seleccionada = list;

	}

	/*
	 * public void reproducirPlayListCompleta() {
	 * 
	 * for(Video vid: vlist_seleccionada.getVideos()) { ActionListener
	 * taskPerformer= new ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent arg0) {
	 * controlador.actualizarVideoSeleccionado(vid); reproductor.reproducir(); }
	 * 
	 * }; timer=new Timer(10000,taskPerformer); timer.setRepeats(false);
	 * timer.start(); } timer.stop();
	 * 
	 * 
	 * }
	 */

	private void cambiarPanelRep() {
		add(reproductor, BorderLayout.CENTER);
		revalidate();
		repaint();
		validate();
	}

	public void clean() {
		modeloListaVideos.removeAllElements();
		comboBox.removeAllItems();
	}
}