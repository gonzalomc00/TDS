package tds.umu.modelo;

import java.util.LinkedList;
import java.util.List;

public class Video {
	private String url;
	private String titulo;
	private int numReproducciones;
	private List<Etiqueta> etiquetas;
	private int codigo;
	
	public Video(String url, String titulo,List<Etiqueta> etiquetas) {
		this.url=url;
		this.titulo=titulo;
		this.etiquetas= etiquetas;
		this.numReproducciones=0;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getNumReproducciones() {
		return numReproducciones;
	}
	public void setNumReproducciones(int numReproducciones) {
		this.numReproducciones = numReproducciones;
	}
	public List<Etiqueta> getEtiquetas() {
		return etiquetas;
	}
	public void setEtiquetas(List<Etiqueta> etiquetas) {
		this.etiquetas = etiquetas;
	}
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public void addEtiqueta(Etiqueta etq) {
		etiquetas.add(etq);
		
	}
	
}
