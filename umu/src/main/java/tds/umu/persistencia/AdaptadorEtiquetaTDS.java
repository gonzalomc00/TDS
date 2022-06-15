package tds.umu.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import tds.umu.modelo.Etiqueta;

public class AdaptadorEtiquetaTDS implements IAdaptadorEtiquetaDAO {


	private static ServicioPersistencia servPersistencia;
	private static AdaptadorEtiquetaTDS unicaInstancia = null;
	
	public static AdaptadorEtiquetaTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			unicaInstancia=new AdaptadorEtiquetaTDS();
	
		return unicaInstancia;
	}

	private AdaptadorEtiquetaTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	/*Registramos una etiqueta asignándole un código*/
	@Override
	public void registrarEtiqueta(Etiqueta etiqueta) {
		
		Entidad eEtiqueta=null;
		//Si está registrada no se hace nada
		try {
			eEtiqueta=servPersistencia.recuperarEntidad(etiqueta.getCodigo());
		} catch(NullPointerException e) {}
		if(eEtiqueta!=null) return;
		
		//Si no, se crea
		eEtiqueta= new Entidad();
		eEtiqueta.setNombre("etiqueta");
		eEtiqueta.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre",etiqueta.getNombre()))));
	
	
		eEtiqueta= servPersistencia.registrarEntidad(eEtiqueta);
		etiqueta.setCodigo(eEtiqueta.getId());
	}

	/*Borramos una etiqueta de la BBDD*/
	@Override
	public void borrarEtiqueta(Etiqueta etiqueta) {
		Entidad eEtiqueta;
		eEtiqueta= servPersistencia.recuperarEntidad(etiqueta.getCodigo());
		servPersistencia.borrarEntidad(eEtiqueta);
		
	}

	/*Modificamos a una etiqueta ya guardada en la BBDD*/
	@Override
	public void modificarEtiqueta(Etiqueta etiqueta) {
		Entidad eEtiqueta= servPersistencia.recuperarEntidad(etiqueta.getCodigo());
		
		for(Propiedad prop: eEtiqueta.getPropiedades()) {
			if(prop.getNombre().equals("nombre")) {
				prop.setValor(String.valueOf(etiqueta.getNombre()));
			}
			else if(prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(etiqueta.getCodigo()));
			}
			servPersistencia.modificarPropiedad(prop);	
		}
		
	}

	/*Recuperamos una etiqueta mediante su código*/
	@Override
	public Etiqueta recuperarEtiqueta(int codigo) {
		
		Entidad eEtiqueta=null;
		String nombre;
		
		try {
			eEtiqueta=servPersistencia.recuperarEntidad(codigo);
		} catch(NullPointerException e) {}
		
		if(eEtiqueta == null) return null;
		
		nombre= servPersistencia.recuperarPropiedadEntidad(eEtiqueta,"nombre");
		Etiqueta etiqueta= new Etiqueta(nombre);
		etiqueta.setCodigo(codigo);
		return etiqueta;
		
	}

	/*Recuperamos todas las etiquetas de la BBDD*/
	@Override
	public List<Etiqueta> recuperarTodosEtiquetas() {
		List<Entidad> eEtiquetas= servPersistencia.recuperarEntidades("etiqueta");
		List<Etiqueta> etiquetas= new LinkedList<Etiqueta>();
		
		for(Entidad eEtiqueta: eEtiquetas) 
			etiquetas.add(recuperarEtiqueta(eEtiqueta.getId()));		
		
		
		return etiquetas;
	}

}
