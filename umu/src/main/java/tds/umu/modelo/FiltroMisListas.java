package tds.umu.modelo;

import tds.umu.controlador.Controlador;


//TODO -REVISAR TEMA DEL FILTRO- NO SE SI EL FILTRO PUEDE TENER DENTRO UN CONTROLADOR

/*Clase que filtra las listas viendo si el vídeo está en alguna de mis listas.*/
public class FiltroMisListas implements FiltroVideo{

	private Controlador controlador=Controlador.getUnicaInstancia();

	
	@Override
	public boolean esVideoOK(Video v) {
		
		/*Si el usuario tiene el vídeo, devolvemos true*/
		return !controlador.userTieneVideo(v);
	}

}
