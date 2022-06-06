package tds.umu.modelo;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/*Clase que contiene la funcionalidad asociada a un vídeo , además de sus atributos básicos como
 * su url, su titulo, el número de reproducciones, las etiquetas asociadas y su código*/
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

	/* Método para inicializar un objeto Vídeo */
	public Video(String url, String titulo, List<Etiqueta> etiquetas) {
		this.url = url;
		this.titulo = titulo;
		this.etiquetas = etiquetas;
		this.numReproducciones = 0;
	}

	/* Métodos get/set para los atributos asociados al vídeo */

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

	/* Método para incrementas las reproducciones si reproducimos el vídeo */
	public void aumentarReproduccion() {
		this.numReproducciones++;
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

	/* Método para asociar una etiqueta a un vídeo */
	public void addEtiqueta(Etiqueta etq) {
		etiquetas.add(etq);

	}

	/* Método para comprobar si un vídeo contiene una etiqueta */
	public boolean containsEtiqueta(List<Etiqueta> etiquetas) {

		for (Etiqueta et : etiquetas) {
			if (this.etiquetas.contains(et))
				return true;
		}

		return false;
	}

	/* Método para obtener las etiquetas asociadas a un vídeo */
	public List<String> obtenerEtiquetas() {
		return etiquetas.stream().map(etq -> etq.getNombre()).collect(Collectors.toList());
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
