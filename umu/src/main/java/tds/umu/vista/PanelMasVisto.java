package tds.umu.vista;

import tds.umu.modelo.Video;
import java.util.List;

/* Panel similar a panel genérico, pero su metodo para rellenar la columna lateral es obteniendo los video más vistos por todos los usuarios.*/
public class PanelMasVisto extends PanelGenerico {

	public PanelMasVisto() {
		super();
		rellenarPantalla();
	}
	
	public void rellenarPantalla()
	{
		setTextoEtiqueta("Videos más vistos");
	}

	
	@Override
	public List<Video> metodoRelleno() {
		return controlador.obtenerMasVisto();
	}
	

}
	