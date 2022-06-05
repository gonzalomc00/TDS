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
	
	//revisar tema del singleton
	public static CatalogoVideos getUnicaInstancia() {
		return unicaInstancia;
	}
	
	
	public List<Video> getVideos(){
		ArrayList<Video> lista= new ArrayList<Video>();
		for(Video v: videos.values())
			lista.add(v);
		
		
		return lista;
	}

	public Video getVideo(String codigo) {
		return videos.get(codigo);
	}	
	
	
	public List<Video> getMasVistos() {
		List<Video> resultado=videos.values().stream()
		.sorted(Comparator.comparingInt(Video::getNumReproducciones).reversed())
		.limit(Constantes.TOP)
		.collect(Collectors.toList());
		
		return resultado;  
	}
	
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
	
	//deberia meterlos con codigo o con titulo?
	public void addVideo(Video v) {
		videos.put(v.getTitulo(), v);
	}
	
	public void removeVideo(Video v) {
		videos.remove(v.getTitulo());
	}
	
	
	private void cargarCatalogo() throws DAOException{
		List<Video> videosBD= adaptadorVideo.recuperarTodosVideos();
		for(Video v: videosBD)
			videos.put(v.getTitulo(), v);
		
	}




	
}
