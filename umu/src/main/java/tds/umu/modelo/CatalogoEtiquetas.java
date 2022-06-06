package tds.umu.modelo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorEtiquetaDAO;



//TODO DEBERIA METERSE EN EL CATALOGO POR NOMBRE O POR CODIGO??
public class CatalogoEtiquetas {
	
	
	private Map<String,Etiqueta> etiquetas;
	private static CatalogoEtiquetas unicaInstancia= new CatalogoEtiquetas();
	/*Usamos el patrón FactoríaDAO*/
	private FactoriaDAO dao;
	/*Usamos diferentes adaptadores para cumplir con el patrón Adaptador y FactoríaDAO*/
	private IAdaptadorEtiquetaDAO adaptadorEtiqueta;
	
	
	
	
	
	/*Esta clase mantiene todas las etiquetas usadas en memoria*/
	/**/
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
	
	//TODO revisar tema del singleton
	
	/*Devuelve el Catálogo*/
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

	/*Devuelve una etiqueta específica llamada "nombre"*/
	public Etiqueta getEtiqueta(String nombre) {
		return etiquetas.get(nombre);
	}
	
	
	/*Añade una etiqueta pasada por parámetro*/
	public void addEtiqueta(Etiqueta etq) {
		etiquetas.put(etq.getNombre(), etq);
	}
	
	/*Elimina la etiqueta pasada por parámetro*/
	public void removeEtiqueta(Etiqueta etq) {
		etiquetas.remove(etq.getNombre());
	}
	
	
	/*Carga el catálogo de etiquetas en memoria*/
	private void cargarCatalogo() throws DAOException{
		List<Etiqueta> etiquetasBD= adaptadorEtiqueta.recuperarTodosEtiquetas();
		for(Etiqueta etq: etiquetasBD)
			etiquetas.put(etq.getNombre(), etq);
		
	}
	
}
