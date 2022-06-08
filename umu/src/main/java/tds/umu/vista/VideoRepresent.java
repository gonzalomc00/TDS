package tds.umu.vista;

import java.awt.Image;

import javax.swing.ImageIcon;

import tds.umu.modelo.Video;

//TOCAR AL FINAL: CREO QUE NO HARÍA FALTA UTILIZAR ESTA CLASE

public class VideoRepresent {
	private String nombre;
	private ImageIcon imagen;
	private Video video;
	
	
	public VideoRepresent(Video v,ImageIcon imagen) {
		nombre=v.getTitulo();
		this.imagen= imagen;
		this.video=v;
	}
	
	

	public String getNombre() {
		return nombre;
	}
	public ImageIcon getImagen() {
		return imagen;
	}
	public Video getVideo() {
		return video;
	}
	
	
	
	
	
	
	
	
}

	

