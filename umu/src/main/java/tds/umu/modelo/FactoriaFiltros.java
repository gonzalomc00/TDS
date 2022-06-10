package tds.umu.modelo;


/*Usamos el patrón factoría para hacer uso de la creación de los objetos utilizados para filtrar los videos por un usuario.
 * A partir del nombre del filtro, esta clase nos devolverá el objeto adecuado en cada momento. 
 * 
 * Como solo puede haber una de estas factorías, además implementa el patrón Singleton. 
 * */
public class FactoriaFiltros {
	private static FactoriaFiltros unicaInstancia=null;
		

	/*Método para obtener la instancia de FactoríaFiltros*/
	public static FactoriaFiltros getUnicaInstancia() {
		if (unicaInstancia == null) 
				unicaInstancia=new FactoriaFiltros();
			
		return unicaInstancia;
	}
	
	/*Creamos el objeto filtro correspondiente dependiendo del nombre del filtro seleccionado*/
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