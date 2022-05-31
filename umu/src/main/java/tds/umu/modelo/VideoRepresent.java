package tds.umu.modelo;

import java.awt.Image;

import javax.swing.ImageIcon;

import tds.umu.modelo.Video;

public class VideoRepresent {

	private Video video;
	private String nombre;
	private String url;
	private ImageIcon imagen;
	
	
	//No se si deberia cambiarlo y dejar unicamente los datos del video
	public VideoRepresent(Video v,ImageIcon imagen) {
		video=v;
		nombre=v.getTitulo();
		url=v.getUrl();
		this.imagen= imagen;
	}
	
	
	

	
	public Video getVideo() {
		return video;
	}
	public String getNombre() {
		return nombre;
	}
	public ImageIcon getImagen() {
		return imagen;
	}
	public String getUrl() {
		return url;
	}
	
	
	
	
	
	
	
}
