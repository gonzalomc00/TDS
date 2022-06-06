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

				@Override
				public void actionPerformed(ActionEvent arg0) {
					controlador.actualizarVideoSeleccionado(vid);
					reproductor.reproducir();
				}
				
			};
			timer=new Timer(10000,taskPerformer);
			timer.setRepeats(false);
			timer.start();
		}
		timer.stop();
		
		
	}
	*/
	
	
}