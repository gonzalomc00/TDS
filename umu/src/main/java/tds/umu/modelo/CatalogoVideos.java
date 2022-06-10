package tds.umu.modelo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorVideoDAO;

public class CatalogoVideos {
	
	private Map<String,Video> videos;
	private static CatalogoVideos unicaInstancia= new CatalogoVideos();
	
	private FactoriaDAO dao;
	private IAdaptadorVideoDAO adaptadorVideo;
	
	/*
	 * Esta clase mantiene en memoria todos los videos almacenados. De esta forma no hace falta entrar todo el rato a la base
	 * de datos a obtener  los objetos. 
	 */
	private CatalogoVideos() {
		try {
			dao= FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorVideo = dao.getVideoDAO();
			videos= new HashMap<String,Video>();
			this.cargarCatalogo();
		} catch(DAOException eDAO) {
			eDAO.printStackTrace();
		}
		
		
	}
	
	/*Método para recuperar el catálogo - Patrón Singleton*/
	public static CatalogoVideos getUnicaInstancia() {
		return unicaInstancia;
	}
	
	/*Método que recupera todos los vídeos almacenados en memoria*/
	public List<Video> getVideos(){
		ArrayList<Video> lista= new ArrayList<Video>();
		for(Video v: videos.values())
			lista.add(v);
		
		
		return lista;
	}

	/*Método que recupera un vídeo en específico a través de su titulo*/
	public Video getVideo(String titulo) {
		return videos.get(titulo);
	}	
	
	
	/*Método que recupera una lista con los vídeos más vistos*/
	public List<Video> getMasVistos() {
		/*
		 * Usamos un stream para ir comparando el número de reproducciones de todos los videos del catálogo. 
		 */
		List<Video> resultado=videos.values().stream()
		.sorted(Comparator.comparingInt(Video::getNumReproducciones).reversed())
		.limit(Constantes.TOP)
		.collect(Collectors.toList());
		
		return resultado;  
	}
	
	
	/*
	 *  Método para recuperar una lista de vídeos que corresponden a una búsqueda en específico. Para realizar la búsqueda se utiliza
	 * una cadena de texto que filtra el título del vídeo, una lista de etiquetas que deben contener(en su totalidad o parcial) los vídeos encontrados
	 * y, además, se le aplica el filtro que tenga actualmente seleccionado el usuario.  
	 */
	public List<Video> getBusqueda(String text, List<Etiqueta> etiquetas, FiltroVideo filtroVideo) {
		
		List<Video> resultado= new LinkedList<Video>();
		for(String vt: videos.keySet()) {
			if(vt.contains(text)) {
				Video v= videos.get(vt);
				if(filtroVideo.esVideoOK(v)) {
					if(etiquetas.size()>0 ) {
						if(v.containsEtiqueta(etiquetas) )
							resultado.add(v);
					}
					else{
						resultado.add(v);
						}
					}
			}
		}
		
		return resultado;
	}
	
	/*Método para añadir un vídeo al catálogo*/
	public void addVideo(Video v) {
		videos.put(v.getTitulo(), v);
	}
	
	/*Método para eliminar un vídeo del catálogo*/
	public void removeVideo(Video v) {
		videos.remove(v.getTitulo());
	}
	
	/*Método para cargar el catálogo en memoria*/
	private void cargarCatalogo() throws DAOException{
		List<Video> videosBD= adaptadorVideo.recuperarTodosVideos();
		for(Video v: videosBD)
			videos.put(v.getTitulo(), v);
		
	}




	
}
