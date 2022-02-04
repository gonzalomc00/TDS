package tds.umu.persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	public TDSFactoriaDAO () {
	}
	
	@Override
	public IAdaptadorClienteDAO getClienteDAO() {
		return AdaptadorClienteTDS.getUnicaInstancia();
	}

}
