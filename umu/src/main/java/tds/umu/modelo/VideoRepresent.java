package tds.umu.modelo;

import java.awt.Image;

import javax.swing.ImageIcon;

import tds.umu.modelo.Video;

public class VideoRepresent {
	private String nombre;
	private ImageIcon imagen;
	
	
	//No se si deberia cambiarlo y dejar unicamente los datos del video
	public VideoRepresent(Video v,ImageIcon imagen) {
		nombre=v.getTitulo();
		this.imagen= imagen;
	}
	
	

	public String getNombre() {
		return nombre;
	}
	public ImageIcon getImagen() {
		return imagen;
	}
	
	
	
	
	
	
	
	
}
