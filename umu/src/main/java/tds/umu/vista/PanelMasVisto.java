package tds.umu.vista;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JList;
import javax.swing.border.LineBorder;

import tds.umu.controlador.Controlador;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Video;
import tds.video.VideoWeb;

import java.awt.Color;
import javax.swing.border.BevelBorder;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Component;
import javax.swing.UIManager;

/* Panel similar a panel genérico, pero su metodo para rellenar la columna lateral es obteniendo los video más vistos por todos los usuarios.*/
public class PanelMasVisto extends PanelGenerico {

	private JLabel etiquetaSeleccion;
	private JButton bReproducir;
	
	public PanelMasVisto() {
		super();
		rellenarPantalla();
	}
	
	public void rellenarPantalla()
	{
		setTextoEtiqueta("Videos más vistos");
	}

	
	@Override
	public List<Video> metodoRelleno() {
		return controlador.obtenerMasVisto();
	}
	

}
	