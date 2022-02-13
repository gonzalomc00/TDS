package tds.umu.modelo;

import java.util.LinkedList;
import java.util.List;

public class ListaVideos {
	
	private String nombre;
	private int codigo;
	private List<Video> videos;
	
	
	public ListaVideos(String nombre) {
		this.nombre=nombre;
		this.videos= new LinkedList<Video>();
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public List<Video> getVideos() {
		return videos;
	}
	public void setVideos(List<Video> videos) {
		this.videos = videos;
	}
	
	public void añadirVideo(Video v) {
		videos.add(v);
	}
}
