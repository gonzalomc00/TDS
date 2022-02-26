package tds.umu.modelo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorEtiquetaDAO;



//DEBERIA METERSE EN EL CATALOGO POR NOMBRE O POR CODIGO??
public class CatalogoEtiquetas {
	
	private Map<Integer,Etiqueta> etiquetas;
	private static CatalogoEtiquetas unicaInstancia= new CatalogoEtiquetas();
	
	private FactoriaDAO dao;
	private IAdaptadorEtiquetaDAO adaptadorEtiqueta;
	
	private CatalogoEtiquetas() {
		try {
			dao= FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorEtiqueta = dao.getEtiquetaDAO();
			etiquetas= new HashMap<Integer,Etiqueta>();
			this.cargarCatalogo();
		} catch(DAOException eDAO) {
			eDAO.printStackTrace();
		}
		
		
	}
	
	//revisar tema del singleton
	public static CatalogoEtiquetas getUnicaInstancia() {
		return unicaInstancia;
	}
	
	
	public List<Etiqueta> getEtiquetas(){
		ArrayList<Etiqueta> lista= new ArrayList<Etiqueta>();
		for(Etiqueta et: etiquetas.values())
			lista.add(et);
		
		
		return lista;
	}

	public Etiqueta getEtiqueta(int codigo) {
		return etiquetas.get(codigo);
	}
	
	//metodo para obtener video por titulo? no creo
	
	
	//deberia meterlos con codigo o con titulo?
	public void addEtiqueta(Etiqueta etq) {
		etiquetas.put(etq.getCodigo(), etq);
	}
	
	public void removeEtiqueta(Etiqueta etq) {
		etiquetas.remove(etq.getCodigo());
	}
	
	
	private void cargarCatalogo() throws DAOException{
		List<Etiqueta> etiquetasBD= adaptadorEtiqueta.recuperarTodosEtiquetas();
		for(Etiqueta etq: etiquetasBD)
			etiquetas.put(etq.getCodigo(), etq);
		
	}
	
}
