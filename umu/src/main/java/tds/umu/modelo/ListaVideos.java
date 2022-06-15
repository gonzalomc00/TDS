package tds.umu.modelo;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/*Esta clase contiene la funcionalidad de una playlist. Se caracteriza por tener un nombre, un código y una 
 * lista de videos*/
public class ListaVideos {

	/* Nombre de la playlist */
	private String nombre;
	/* Código único de la playlist */
	private int codigo;
	/* Lista de vídeos de la playlist */
	private List<Video> videos;

	/* Constructor de una lista de videos */
	public ListaVideos(String nombre) {
		this.nombre = nombre;
		this.videos = new LinkedList<Video>();
	}

	/* Métodos get/set de los atributos de la clase */
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

	/* Método para añadir un vídeo a una playlist */
	public void añadirVideo(Video v) {
		videos.add(v);
	}

	/* Método para eliminar un vídeo de una playlist */
	public void eliminarVideo(Video v_sel) {
		videos.remove(v_sel);
	}

	/* Métodos para comparar playlist y comprobar si son iguales o no */
	@Override
	public int hashCode() {
		return Objects.hash(codigo, nombre, videos);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;

		ListaVideos otro = (ListaVideos) obj;
		return codigo == otro.getCodigo() && nombre.equals(otro.getNombre()) && videos.equals(otro.getVideos());
	}

}
