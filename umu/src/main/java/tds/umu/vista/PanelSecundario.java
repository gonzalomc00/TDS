package tds.umu.vista;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;

public class PanelSecundario extends JPanel {

	/**
	 * Create the panel.
	 */
	public PanelSecundario() {
		setBackground(Color.PINK);
		
		JLabel lblNewLabel = new JLabel("Secundario");
		add(lblNewLabel);
		setPreferredSize(new Dimension(900, 500));
	    setMaximumSize(new Dimension(900, 500));

	}

}
