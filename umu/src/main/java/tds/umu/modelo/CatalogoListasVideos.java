package tds.umu.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorListaVideosDAO;

public class CatalogoListasVideos {

	private Map<Integer,ListaVideos> listasvideos;
	private static CatalogoListasVideos unicaInstancia= new CatalogoListasVideos();
	
	private FactoriaDAO dao;
	private IAdaptadorListaVideosDAO adaptadorListaVideos;
	
	private CatalogoListasVideos() {
		try {
			dao= FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorListaVideos = dao.getListaVideosDAO();
			listasvideos= new HashMap<Integer,ListaVideos>();
			this.cargarCatalogo();
		} catch(DAOException eDAO) {
			eDAO.printStackTrace();
		}
		
		
	}
	
	//revisar tema del singleton
	public static CatalogoListasVideos getUnicaInstancia() {
		return unicaInstancia;
	}
	
	
	public List<ListaVideos> getListasVideos(){
		ArrayList<ListaVideos> lista= new ArrayList<ListaVideos>();
		for(ListaVideos lv: listasvideos.values())
			lista.add(lv);
		return lista;
	}

	public ListaVideos getListaVideos(String codigo) {
		return listasvideos.get(codigo);
	}
	
	//deberia meterlos con codigo o con titulo?
	public void addListaVideos(ListaVideos lv) {
		listasvideos.put(lv.getCodigo(), lv);
	}
	
	public void removeListaVideos(ListaVideos lv) {
		listasvideos.remove(lv.getNombre());
	}
	
	
	private void cargarCatalogo() throws DAOException{
		List<ListaVideos> lvideosBD= adaptadorListaVideos.recuperarTodosListasVideos();
		for(ListaVideos lv: lvideosBD)
			listasvideos.put(lv.getCodigo(), lv);
		
	}
}
 