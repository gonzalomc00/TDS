package tds.umu.vista;


import tds.umu.modelo.Video;
import java.util.List;


/* Panel similar a panel genérico, pero su metodo para rellenar la columna lateral es obteniendo los videos recientes del usuario.*/
public class PanelRecientes extends PanelGenerico {

	
	
	public PanelRecientes() {
		super();
		rellenarPantalla();
	}
	
	public void rellenarPantalla()
	{
		setTextoEtiqueta("Videos recientes");	
	}
	
	@Override
	public List<Video> metodoRelleno() {
		return controlador.obtenerRecientesUser();
	}


}
