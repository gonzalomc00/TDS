package tds.umu.modelo;


/*Usamos el patrón Factoría para poder generar varios filtros de la manera más eficiente posible.
 * Partimos de la factoría, y según el tipo de filtro derivamos a su respectiva clase.*/
public class FactoriaFiltros {
	private static FactoriaFiltros unicaInstancia=null;
		

	/*Método para obtener la instancia de FactoríaFiltros*/
	public static FactoriaFiltros getUnicaInstancia() {
		if (unicaInstancia == null) 
				unicaInstancia=new FactoriaFiltros();
			
		return unicaInstancia;
	}
	
	/*Creamos el objeto filtro correspondiente dependiendo del filtro seleccionado*/
	public FiltroVideo getFiltro(String filtro) {
		if(filtro.equalsIgnoreCase("NOFILTRO"))
			return new NoFiltro();
		else if (filtro.equalsIgnoreCase("LARGOS"))
			return new FiltroLargos();
		else if (filtro.equalsIgnoreCase("MISLISTAS"))
			return new FiltroMisListas();
		
		return null;
		
	}


}