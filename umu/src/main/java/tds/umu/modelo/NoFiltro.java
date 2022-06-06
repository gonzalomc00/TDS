package tds.umu.modelo;
/*Clase que implica que no se aplica filtro, por tanto, no hace nada más que devolver siempre true*/
public class NoFiltro implements FiltroVideo {

	@Override
	public boolean esVideoOK(Video v) {
		return true;
	}

}
