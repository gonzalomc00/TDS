package tds.umu.persistencia;


/* Factoría abstracta que se encarga de devolver objetos factoria, los cuales a su vez nos proporcionará los adaptadores adecuados */
public abstract class FactoriaDAO {
	private static FactoriaDAO unicaInstancia;
	
	public static final String DAO_TDS = "tds.umu.persistencia.TDSFactoriaDAO";
		
	/** 
	 * Crea un tipo de factoria DAO.
	 * Solo existe el tipo TDSFactoriaDAO
	 */
	public static FactoriaDAO getInstancia(String tipo) throws DAOException{
		if (unicaInstancia == null) {
			try {
				unicaInstancia=(FactoriaDAO) Class.forName(tipo).newInstance();
			} catch (Exception e) {	
				throw new DAOException(e.getMessage());
			} 
		}
		return unicaInstancia;
	}


	/* Método que devuelve la única instancia de la factoría */
	public static FactoriaDAO getInstancia() throws DAOException{
			if (unicaInstancia == null) return getInstancia (FactoriaDAO.DAO_TDS);
					else return unicaInstancia;
		}

	/* Constructor */
	protected FactoriaDAO (){}
		
		
	// Métodos que deberán implementar las factorias que sean creadas. Cada objeto devuelvo debe implementar una interfaz distinta, interfaz usada por todos los adaptadores de la misma clase
	// pero que pueden ser creados por factorías distintas. 

	public abstract IAdaptadorUsuarioDAO getUsuarioDAO();
	public abstract IAdaptadorVideoDAO getVideoDAO();
	public abstract IAdaptadorEtiquetaDAO getEtiquetaDAO();
	public abstract IAdaptadorListaVideosDAO getListaVideosDAO();

}
