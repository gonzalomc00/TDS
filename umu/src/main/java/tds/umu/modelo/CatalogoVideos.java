package tds.umu.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorVideoDAO;

public class CatalogoVideos {
	
	private Map<Integer,Video> videos;
	private static CatalogoVideos unicaInstancia= new CatalogoVideos();
	
	private FactoriaDAO dao;
	private IAdaptadorVideoDAO adaptadorVideo;
	
	private CatalogoVideos() {
		try {
			dao= FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorVideo = dao.getVideoDAO();
			videos= new HashMap<Integer,Video>();
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

	public Video getVideo(int codigo) {
		return videos.get(codigo);
	}
	
	//metodo para obtener video por titulo? no creo
	
	
	//deberia meterlos con codigo o con titulo?
	public void addVideo(Video v) {
		videos.put(v.getCodigo(), v);
	}
	
	public void removeVideo(Video v) {
		videos.remove(v.getCodigo());
	}
	
	
	private void cargarCatalogo() throws DAOException{
		List<Video> videosBD= adaptadorVideo.recuperarTodosVideos();
		for(Video v: videosBD)
			videos.put(v.getCodigo(), v);
		
	}
	
}
