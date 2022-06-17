package tds.umu.vista;

import java.awt.BorderLayout;
import javax.swing.Timer;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import tds.umu.modelo.*;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Component;
/*Generación del PDF*/
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

/*
 * Clase hija de PanelGenerico la cual utiliza distintos métodos para rellenar una ComboBox con las listas de video del usuario que esté actualmente conectado. 
 * Además, incorpora nuevos elementos a la estrucura base que proporciona PanelGenérico, como es el botón para reproducir una lista de videos completa durante un tiempo introducido.
 * Para hacer esto haremos uso de la clase Timer de la Swing. 
 */
public class PanelMisListas extends PanelGenerico {

	private List<ListaVideos> vlists_encontradas;
	private ListaVideos vlist_seleccionada;
	private JButton bPDF, bReproducirTodos;

	// Temporizador de Swing y el evento que realizará.
	private Timer timer;
	private ActionListener eventoTimer;

	/*
	 * Constructor de la clase. Inicia también el temporizador.
	 */
	public PanelMisListas() {
		super();
		timer = new Timer(0, null);
		rellenarPantalla();
	}

	public void rellenarPantalla() {
		setTextoEtiqueta("Mis Listas");
		comboBox.setVisible(true);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				String selected = (String) comboBox.getSelectedItem();
				if (selected != null) {
					vlist_seleccionada = vlists_encontradas.get(comboBox.getSelectedIndex());
					actualizarPanelLateral();
				}
			}
		});

		bReproducirTodos = new JButton("Reproducir todo");
		bReproducirTodos.setAlignmentX(Component.CENTER_ALIGNMENT);
		bReproducirTodos.addMouseListener(new MouseAdapter() {
			/*
			 * Cuando seleccionamos el botón ReproducirTodos el sistema nos pide que
			 * introduzcamos un tiempo de segundos que queremos que se reproduzcan todos los
			 * videos de la playlist actual.
			 */
			public void mouseClicked(MouseEvent event) {
				String tiempo = JOptionPane.showInputDialog(null, "Introduce los segundos que quieres ver los vídeos.");
				if (tiempo!=null && !tiempo.equals(""))
					try {
					reproducirTodos(Integer.valueOf(tiempo));
					} catch(NumberFormatException e) {
						JOptionPane.showMessageDialog(null,"Debes introducir un número","Introduce un número"
								,JOptionPane.ERROR_MESSAGE);
					
					}
			}
		});
		panel_4.add(bReproducirTodos, BorderLayout.CENTER);

		bPDF = new JButton("PDF");
		bPDF.addActionListener(ev -> {
			if (controlador.esUserPremium()) {
				crearPDF();
			} else {
				JOptionPane.showMessageDialog(this, "Para usar esta función debes ser un usuario premium",
						"No eres Premium", JOptionPane.ERROR_MESSAGE);
			}
		});
		bPDF.setForeground(Color.RED);
		panel_inferior.add(bPDF, BorderLayout.CENTER);

	}

	/*
	 * Método que introduce los nombres de las listas de video dentro de la
	 * comboBox.
	 */
	public void actualizarPlayLists() {
		vlists_encontradas = controlador.obtenerPlayListsUser();
		vlists_encontradas.stream().forEach(l -> comboBox.addItem(l.getNombre()));

	}

	/*
	 * Para rellenar el panel lateral utilizamos los vídeos de la lista de vídeo que
	 * esté seleccionada ahora mismo.
	 */
	@Override
	public List<Video> metodoRelleno() {
		return vlist_seleccionada.getVideos();
	}

	/*
	 * Método para la creación del PDF. Toma la información de esta ventana para
	 * poder generarlo. Para la generación del PDF se utiliza el componente ITextPDF
	 */
	private void crearPDF() {
		String raiz = "C:\\temp\\Lista.pdf";
		PdfWriter writer = null;
		try {
			writer = new PdfWriter(raiz);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		PdfDocument doc = new PdfDocument(writer);
		doc.addNewPage();

		Document document = new Document(doc);
		Paragraph titulo = new Paragraph(" RECOPILACIÓN DE MIS LISTAS DE REPRODUCCIÓN ");
		titulo.setItalic();
		titulo.setBold();

		document.add(titulo);
		Usuario user = controlador.getUsuarioActual();
		String usuario = "Nombre del usuario: " + user.getNombre() + "\n" + "Apellidos del usuario: "
				+ user.getApellidos() + "\n" + "Email del usuario: " + user.getEmail() + "\n" + "Fecha de nacimiento: "
				+ user.getFecha();

		document.add(new Paragraph(usuario));
		document.add(new Paragraph("\n"));

		document.add(new Paragraph("MIS LISTAS: " + "\n\n"));

		for (ListaVideos v : vlists_encontradas) {
			document.add(new Paragraph("Lista de reproduccion: " + v.getNombre()));
			for (Video vid : v.getVideos()) {
				document.add(new Paragraph("Video: " + vid.getTitulo() + "\n Enlace: " + vid.getUrl()
						+ "\n Numero de reproducciones: " + vid.getNumReproducciones()));
			}
			document.add(new Paragraph("\n\n"));
		}
		document.close();
		JOptionPane.showMessageDialog(null, "El PDF se ha generado correctamente en  C:\\temp\\Lista.pdf.",
				"PDF generado", JOptionPane.INFORMATION_MESSAGE);

	}

	/*
	 * Método al que se llamará cuando pulsamos el botón de ReproducirTodos. Añade
	 * el evento el cual permite reproducir un vídeo al Timer, el cual se programa
	 * para actuar cada X segundos, tantos como se hayan introducido como parámetro
	 * a la función-
	 */
	private void reproducirTodos(int tiempo) {

		bReproducir.setEnabled(false);
		bReproducirTodos.setEnabled(false);

		eventoTimer = new ActionListener() {
			int counter = 0;
			int max = videos_relleno.size();

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (counter < max) {
					Video v = videos_relleno.get(counter);
					controlador.actualizarVideoSeleccionado(v);
					cambiarPanelRep();
					counter++;

				} else {
					cancelarVideo();
				}

			}
		};
		timer.addActionListener(eventoTimer);

		timer.setRepeats(true);
		timer.setDelay(tiempo * 1000);
		timer.start();

	}

	/*
	 * Para evitar problemas con el Timer, el método cancelarVideo de esta clase
	 * extiene al de PanelGenerico. En este caso. se encarga de parar el
	 * temporizador por si acaso sigue reproduciendose y elimina el ActionListener
	 * del temporizador. De esta forma eliminamos problemas relacionados con pulsar
	 * varias veces seguidas el botón de MisListas
	 */
	protected void cancelarVideo() {
		super.cancelarVideo();
		bReproducir.setEnabled(true);
		bReproducirTodos.setEnabled(true);
		timer.stop();
		timer.removeActionListener(eventoTimer);
	}

}