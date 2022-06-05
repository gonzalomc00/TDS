package tds.umu.modelo;



public class FactoriaFiltros {
	private static FactoriaFiltros unicaInstancia;
		

	public static FactoriaFiltros getUnicaInstancia() {
		if (unicaInstancia == null) 
				unicaInstancia=new FactoriaFiltros();
			
		return unicaInstancia;
	}
	
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