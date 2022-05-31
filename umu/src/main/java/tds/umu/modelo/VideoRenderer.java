package tds.umu.modelo;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

public class VideoRenderer extends JLabel implements ListCellRenderer<VideoRepresent> {
	public VideoRenderer() {
	}

	@Override
	public Component getListCellRendererComponent(JList<? extends VideoRepresent> list, VideoRepresent vr, int ind,
			boolean isSelected, boolean cellHasFocus) {
		
		setBackground(Color.GRAY);
		setForeground(Color.WHITE);
		setOpaque(true);
		setPreferredSize(new Dimension(140,110));
		setVerticalAlignment(CENTER);
		setHorizontalAlignment(CENTER);
		setHorizontalTextPosition(JLabel.CENTER);
		setVerticalTextPosition(JLabel.BOTTOM);
		
		String nombre= vr.getNombre();
		ImageIcon imageIcon = vr.getImagen();
		
		setIcon(imageIcon);
		setText(nombre);
		return this;
	}

}
