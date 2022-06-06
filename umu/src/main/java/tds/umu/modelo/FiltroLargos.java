package tds.umu.modelo;

import tds.umu.controlador.Controlador;

/*Clase que filtra los vídeos de manera que solo aparezcan aquellos que tengan una longitud maxima 
 * en el titulo inferior a la de la constante */
public class FiltroLargos implements FiltroVideo {

	/*Devuelve un booleano aplicando el filtro*/
	@Override
	public boolean esVideoOK(Video v) {
		if(v.getTitulo().length()<Constantes.LONGITUD_MAX_TITULO)
			return true;
		else
			return false;
	}

}
