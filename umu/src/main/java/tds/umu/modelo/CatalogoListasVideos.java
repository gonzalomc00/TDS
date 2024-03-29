package tds.umu.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorListaVideosDAO;

public class CatalogoListasVideos {

	private Map<Integer, ListaVideos> listasvideos;
	private static CatalogoListasVideos unicaInstancia = new CatalogoListasVideos();

	private FactoriaDAO dao;
	private IAdaptadorListaVideosDAO adaptadorListaVideos;

	/*
	 * Esta clase mantiene en memoria todas las listas de video de todos los
	 * usuarios. De esta forma no hace falta entrar todo el rato a la base de datos
	 * a obtener los objetos.
	 * 
	 * Como pueden haber varias listas de video con el mismo nombre, pertenecientes
	 * a usuarios distintos, entonces el mapa de las listas estará indexado mediante
	 * el código de las listas y no por su nombre.
	 */
	private CatalogoListasVideos() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorListaVideos = dao.getListaVideosDAO();
			listasvideos = new HashMap<Integer, ListaVideos>();
			this.cargarCatalogo();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}

	}

	/* Devuelve la instancia del Catálogo */
	public static CatalogoListasVideos getUnicaInstancia() {
		return unicaInstancia;
	}

	/* Devuelve una lista con todos las playlists existentes */
	public List<ListaVideos> getListasVideos() {
		ArrayList<ListaVideos> lista = new ArrayList<ListaVideos>();
		for (ListaVideos lv : listasvideos.values())
			lista.add(lv);
		return lista;
	}

	/* Devuelve una playlist específica pasando por parámetro su código */
	public ListaVideos getListaVideos(int codigo) {
		return listasvideos.get(codigo);
	}

	/* Método para añadir una playlist al catálogo */
	public void addListaVideos(ListaVideos lv) {
		listasvideos.put(lv.getCodigo(), lv);
	}

	/* Método para eliminar una playlist del Catálogo */
	public void removeListaVideos(ListaVideos lv) {
		listasvideos.remove(lv.getCodigo());
	}

	/* Método para cargar el catálogo en memoria utilizando el adaptador */
	private void cargarCatalogo() throws DAOException {
		List<ListaVideos> lvideosBD = adaptadorListaVideos.recuperarTodosListasVideos();
		for (ListaVideos lv : lvideosBD)
			listasvideos.put(lv.getCodigo(), lv);

	}
}
