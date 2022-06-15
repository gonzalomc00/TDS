package tds.umu.modelo;

/*Clase que filtra los vídeos de manera que solo aparezcan aquellos cuya longitud del título sea menor a LONGITUD_MAX_TITULO */
public class FiltroLargos implements FiltroVideo {

	@Override
	public boolean esVideoOK(Video v) {
		if(v.getTitulo().length()<Constantes.LONGITUD_MAX_TITULO)
			return true;
		else
			return false;
	}

}
