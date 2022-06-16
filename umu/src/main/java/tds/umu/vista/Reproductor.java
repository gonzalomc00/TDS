package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.BorderLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.Video;
import tds.video.VideoWeb;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.Font;

/* 
 * Panel que muestra el reproductor del componente VideoWeb. Este panel será mostrado por pantalla cuando haya que reproducir algún vídeo, ya sea ocupando la ventana completa cuando es llamado
 * desde la ventana principal u ocupando solo un trozo de la ventana cuando es llamado desde otra ventana. Se le aplica el patrón Singleton de forma que solo exista una única instancia de este 
 * panel ya que, cuando existen varias, el contenido no se visualiza de manera correcta. Además, esto no interfiere con el funcionamiento de la aplicación, pues solo se puede visualizar un único
 * video a la vez. 
 */
public class Reproductor extends JPanel {

	/* Atributo del patrón Singleton */
	private static Reproductor unicaInstancia = null;
	private Controlador controlador = Controlador.getUnicaInstancia();
	private VideoWeb videoWeb = controlador.getReproductor();
	private Video v;

	private JPanel p;
	private JPanel panel;
	private JPanel panelEtiq;
	private JLabel tit, reproducciones, nombreVideo;
	private JTextField textField;
	private JButton anadir;
	private Component verticalStrut, verticalStrut_1, verticalStrut_2;
	private JList<String> listaetiquetas;
	private DefaultListModel<String> modeloListaEtiquetas = new DefaultListModel<String>();

	/* Constructor de la clase Reproductor: establece todos los componentes */
	public Reproductor() {
		v = null;
		setLayout(new BorderLayout());

		reproducciones = new JLabel();
		reproducciones.setFont(new Font("Tahoma", Font.PLAIN, 14));
		reproducciones.setAlignmentX(Component.CENTER_ALIGNMENT);
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

		verticalStrut_1 = Box.createVerticalStrut(20);
		add(verticalStrut_1, BorderLayout.NORTH);

		nombreVideo = new JLabel();
		nombreVideo.setFont(new Font("Verdana", Font.BOLD, 18));
		nombreVideo.setAlignmentX(0.5f);
		add(nombreVideo, BorderLayout.NORTH);

		verticalStrut_2 = Box.createVerticalStrut(20);
		add(verticalStrut_2, BorderLayout.NORTH);
		add(reproducciones);

		p = new JPanel();
		p.add(videoWeb);
		add(p);
		panel = new JPanel();

		verticalStrut = Box.createVerticalStrut(50);
		panel.add(verticalStrut);

		tit = new JLabel("Nueva etiqueta:");
		panel.add(tit);

		textField = new JTextField();
		textField.setColumns(40);
		panel.add(textField);

		anadir = new JButton("Añadir");
		anadir.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!textField.getText().isEmpty()) {
					añadirEtiqueta();
				}
			}

		});

		panel.add(anadir);
		panelEtiq = new JPanel();
		listaetiquetas = new JList<String>();
		listaetiquetas.setVisibleRowCount(1);
		listaetiquetas.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		listaetiquetas.setModel(modeloListaEtiquetas);
		panelEtiq.add(listaetiquetas);

		add(panel);
		add(panelEtiq);

	}

	/* Método del patrón Singleton para obtener la única instancia de la clase */
	public static Reproductor getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Reproductor();
		return unicaInstancia;
	}

	/*
	 * Cuando vayamos a reproducir algún vídeo, este estará en el controlador
	 * seleccionado como video actual. Una vez tengamos el vídeo entonces podremos
	 * obtener todos sus datos para mostrarlos por pantalla y reproducirlo.
	 */
	public void reproducir() {
		modeloListaEtiquetas.removeAllElements();
		v = controlador.getVideoActual();
		nombreVideo.setText(v.getTitulo());
		reproducciones.setText("Reproducciones: " + v.getNumReproducciones());
		actualizarEtiquetas();
		videoWeb.playVideo(v.getUrl());

	}

	/* Método que cancela la reproducción de un vídeo */
	public void cancelarVideo() {
		videoWeb.cancel();

	}

	/*
	 * Método utilizado para añadir una etiqueta al vídeo que está actualmente
	 * reproduciendose (recordemos que el vídeo se encuentra en el controlador como
	 * VídeoSeleccionado). Una vez se llegue al controlador este realizará todo el
	 * proceso necesario para hacer los cambios oportunos en la base de datos.
	 */
	public void añadirEtiqueta() {
		controlador.añadirEtiqueta(textField.getText());
		actualizarEtiquetas();

	}

	/*
	 * Método para mostrar todas las etiquetas que tiene un vídeo por pantalla.
	 * Cuando se añadan etiquetas a un vídeo se llamará para que muestre también las
	 * nuevas etiquetas.
	 */
	private void actualizarEtiquetas() {
		modeloListaEtiquetas.removeAllElements();
		List<String> listaetiquetas = v.obtenerEtiquetas();
		listaetiquetas.stream().forEach(str -> modeloListaEtiquetas.addElement(str));
		textField.setText("");
	}

}
