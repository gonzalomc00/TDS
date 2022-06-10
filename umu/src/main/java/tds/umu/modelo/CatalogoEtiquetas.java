package tds.umu.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorEtiquetaDAO;

public class CatalogoEtiquetas {
	private Map<String,Etiqueta> etiquetas;
	private static CatalogoEtiquetas unicaInstancia= new CatalogoEtiquetas();
	private FactoriaDAO dao;
	private IAdaptadorEtiquetaDAO adaptadorEtiqueta;

	/*
	 * Esta clase mantiene en memoria todas las etiquetas usadas. De esta forma no hace falta entrar todo el rato a la base
	 * de datos a obtener  los objetos. 
	 */
	private CatalogoEtiquetas() {
		try {
			dao= FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorEtiqueta = dao.getEtiquetaDAO();
			etiquetas= new HashMap<String,Etiqueta>();
			this.cargarCatalogo();
		} catch(DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	/*Devuelve el Catálogo - Patrón Singleton*/
	public static CatalogoEtiquetas getUnicaInstancia() {
		return unicaInstancia;
	}
	
	
	/*Devuelve todas las etiquetas disponibles*/
	public List<Etiqueta> getEtiquetas(){
		ArrayList<Etiqueta> lista= new ArrayList<Etiqueta>();
		for(Etiqueta et: etiquetas.values()) {
			lista.add(et);

		}
		return lista;
	}

	/*Devuelve una etiqueta según su nombre*/
	public Etiqueta getEtiqueta(String nombre) {
		return etiquetas.get(nombre);
	}
	
	
	/*Añade al catálogo una etiqueta pasada por parámetro*/
	public void addEtiqueta(Etiqueta etq) {
		etiquetas.put(etq.getNombre(), etq);
	}
	
	/*Elimina la etiqueta pasada por parámetro*/
	public void removeEtiqueta(Etiqueta etq) {
		etiquetas.remove(etq.getNombre());
	}
	
	
	/*Carga el catálogo de etiquetas en memoria utilizando el adaptador*/
	private void cargarCatalogo() throws DAOException{
		List<Etiqueta> etiquetasBD= adaptadorEtiqueta.recuperarTodosEtiquetas();
		for(Etiqueta etq: etiquetasBD)
			etiquetas.put(etq.getNombre(), etq);
		
	}
	
}
