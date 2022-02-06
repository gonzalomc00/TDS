package tds.umu.persistencia;

import java.util.List;

import tds.umu.modelo.Etiqueta;

public interface IAdaptadorEtiquetaDAO {

	public void registrarEtiqueta(Etiqueta etiqueta);
	public void borrarEtiqueta(Etiqueta etiqueta);
	public void modificarEtiqueta(Etiqueta etiqueta);
	public Etiqueta recuperarEtiqueta(int codigo);
	public List<Etiqueta> recuperarTodosEtiquetas();
}
