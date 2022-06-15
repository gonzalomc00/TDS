package tds.umu.vista;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import tds.umu.modelo.VideoRepresent;


/*
 * Renderer utilizado por todas las listas que necesiten mostrar un vídeo junto a su miniatura. Consiste, básicamente en una etiqueta a la cual 
 * le asignamos como imágen la miniatura del vídeo y como texto su título. 
 */
public class VideoRenderer extends JLabel implements ListCellRenderer<VideoRepresent> {
	public VideoRenderer() {
		setOpaque(true);
		setPreferredSize(new Dimension(140,110));
		setVerticalAlignment(CENTER);
		setHorizontalAlignment(CENTER);
		setHorizontalTextPosition(JLabel.CENTER);
		setVerticalTextPosition(JLabel.BOTTOM);
		
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends VideoRepresent> list, VideoRepresent vr, int ind,
			boolean isSelected, boolean cellHasFocus) {
		
		
		if(isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}

		String nombre= vr.getNombre();
		ImageIcon imageIcon = vr.getImagen();
		
		setIcon(imageIcon);
		setText(nombre);
		return this;
	}

}
