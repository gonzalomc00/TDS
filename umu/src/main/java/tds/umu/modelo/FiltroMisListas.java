package tds.umu.modelo;

import tds.umu.controlador.Controlador;


//-REVISAR TEMA DEL FILTRO- NO SE SI EL FILTRO PUEDE TENER DENTRO UN CONTROLADOR
public class FiltroMisListas implements FiltroVideo{

	private Controlador controlador=Controlador.getUnicaInstancia();

	
	@Override
	public boolean esVideoOK(Video v) {
		//Por como está hecho el stream del usuario hay que negar el return 
		return !controlador.userTieneVideo(v);
	}

}
