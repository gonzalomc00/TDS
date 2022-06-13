package tds.umu.modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*Clase que contiene la funcionalidad deun vídeo , además de sus atributos básicos como
 * su url, su titulo, el número de reproducciones, sus etiquetas y su código*/
public class Video {
	/* URL asociada al video */
	private String url;
	/* Titulo asociado al video */
	private String titulo;
	/* Numero de reproducciones asociadas */
	private int numReproducciones;
	/* Etiquetas asociadas a un vídeo */
	private List<Etiqueta> etiquetas;
	/* Código único para cada video */
	private int codigo;

	/* Constructor de la clase Vídeo */
	public Video(String url, String titulo) {
		this.url = url;
		this.titulo = titulo;
		this.etiquetas = new LinkedList<Etiqueta>();
		this.numReproducciones = 0;
	}

	/* Método para incrementar las reproducciones si reproducimos el vídeo */
	public void aumentarReproduccion() {
		this.numReproducciones++;
	}

    /* La clase vídeo será la encargada de crear las etiquetas en caso de que no existan, ya que es quien las contiene. */
	public Etiqueta crearEtiqueta(String text) {
		Etiqueta etq= new Etiqueta(text);
		addEtiqueta(etq);
		return etq;
	}
	/* Método para asociar una etiqueta ya existente a un vídeo */
	public void addEtiqueta(Etiqueta etq) {
		etiquetas.add(etq);

	}


	/* Método para obtener los nombres de las etiquetas del video */
	public List<String> obtenerEtiquetas() {
		return etiquetas.stream().map(etq -> etq.getNombre()).collect(Collectors.toList());
	}

	/* Método para comprobar si un vídeo contiene una etiqueta. */
	public boolean contieneEtiqueta(Etiqueta etq) {
		return etiquetas.contains(etq);
	}
	
	/* Método para comprobar si un video contiene una etiqueta de una lista */
	public boolean contieneEtiquetas(List<Etiqueta>etiquetas) {
		
		for(Etiqueta et:etiquetas) {
			if(contieneEtiqueta(et))
				return true;
		}
		
		return false;
	}

	
	/* 		MÉTODOS GET Y SET	*/
	
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

	
	/* Métodos para comprobar la igualdad de dos objetos */
	@Override
	public int hashCode() {
		return Objects.hash(codigo, url, titulo, etiquetas, numReproducciones);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;

		Video otro = (Video) obj;
		return codigo == otro.getCodigo() && url.equals(otro.getUrl()) && titulo.equals(otro.getTitulo())
				&& etiquetas.equals(otro.getEtiquetas()) && numReproducciones == otro.getNumReproducciones();

	}

}
