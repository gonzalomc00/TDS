package tds.umu.modelo;

import tds.umu.controlador.Controlador;

/*Clase que filtra las listas viendo si el vídeo está en alguna de las listas del usuario actual.*/
public class FiltroMisListas implements FiltroVideo {

	private Controlador controlador = Controlador.getUnicaInstancia();

	@Override
	public boolean esVideoOK(Video v) {

		return !controlador.userTieneVideo(v);
	}

}
