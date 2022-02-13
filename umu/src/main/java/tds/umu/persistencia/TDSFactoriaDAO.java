package tds.umu.persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	public TDSFactoriaDAO () {
	}
	
	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioTDS.getUnicaInstancia();
	}
	
	public IAdaptadorVideoDAO getVideoDAO() {
		return AdaptadorVideoTDS.getUnicaInstancia();
	}
	
	public IAdaptadorEtiquetaDAO getEtiquetaDAO() {
		return AdaptadorEtiquetaTDS.getUnicaInstancia();
	}
	
	public IAdaptadorListaVideosDAO getListaVideosDAO() {
		return AdaptadorListaVideosTDS.getUnicaInstancia();
	}
}
