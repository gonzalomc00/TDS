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

public class PanelMisListas extends PanelGenerico {
	
	private List<ListaVideos> vlists_encontradas;
	private ListaVideos vlist_seleccionada;
	private Timer timer;
	private JButton bPDF;
	
	public PanelMisListas() {
		super();
		rellenarPantalla();
	
	;
	}
	
	public void rellenarPantalla() {
		setTextoEtiqueta("Mis Listas");
		comboBox.setVisible(true);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
					String selected=(String)comboBox.getSelectedItem();
					if(selected!=null) {
						vlist_seleccionada=vlists_encontradas.get(comboBox.getSelectedIndex());
						actualizarPanelLateral();
					}	
			}
		});
		
		
		bPDF=new JButton("PDF");
		bPDF.addActionListener(ev -> {
			crearPDF();
		});
		bPDF.setForeground(Color.RED);
		panel_inferior.add(bPDF);
		
	}

	public void actualizarPlayLists() {
		vlists_encontradas = controlador.obtenerPlayListsUser();
		vlists_encontradas.stream().forEach(l -> comboBox.addItem(l.getNombre()));

	}
	@Override
	public List<Video> metodoRelleno() {
		return vlist_seleccionada.getVideos();
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
	
}