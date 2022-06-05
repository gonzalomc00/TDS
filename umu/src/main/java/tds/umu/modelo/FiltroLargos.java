package tds.umu.modelo;

import tds.umu.controlador.Controlador;

public class FiltroLargos implements FiltroVideo {

	@Override
	public boolean esVideoOK(Video v) {
		if(v.getTitulo().length()<Constantes.LONGITUD_MAX_TITULO)
			return true;
		else
			return false;
	}

}
