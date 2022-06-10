package tds.umu.modelo;

/*Filtro por defecto, por tanto, no hace nada más que devolver siempre true*/
public class NoFiltro implements FiltroVideo {

	@Override
	public boolean esVideoOK(Video v) {
		return true;
	}

}
