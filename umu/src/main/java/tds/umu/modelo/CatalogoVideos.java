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
	
	/*Clase que almacena todos los vídeos en memoria*/
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
	
	//TODO revisar tema del singleton
	
	/*Método para recuperar el catálogo*/
	public static CatalogoVideos getUnicaInstancia() {
		return unicaInstancia;
	}
	
	/*Método que recupera una lista de vídeos almacenados en memoria*/
	public List<Video> getVideos(){
		ArrayList<Video> lista= new ArrayList<Video>();
		for(Video v: videos.values())
			lista.add(v);
		
		
		return lista;
	}

	/*Método que recupera un vídeo en específico a través de su código*/
	public Video getVideo(String codigo) {
		return videos.get(codigo);
	}	
	
	
	/*Método que recupera una lista con los vídeos más vistos*/
	public List<Video> getMasVistos() {
		/*Usamos un stream para ir comparando el número de reproducciones*/
		List<Video> resultado=videos.values().stream()
		.sorted(Comparator.comparingInt(Video::getNumReproducciones).reversed())
		.limit(Constantes.TOP)
		.collect(Collectors.toList());
		
		return resultado;  
	}
	
	
	/*Método para recuperar una lista de vídeos que corresponden a una búsqueda en específico*/
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
	
	//TODO deberia meterlos con codigo o con titulo?
	
	
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
