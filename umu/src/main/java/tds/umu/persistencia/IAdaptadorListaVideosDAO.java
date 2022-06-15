package tds.umu.persistencia;

import java.util.List;

import tds.umu.modelo.ListaVideos;

public interface IAdaptadorListaVideosDAO {

	public void registrarListaVideos(ListaVideos listavideos);

	public void borrarListaVideos(ListaVideos listavideos);

	public void modificarListaVideos(ListaVideos listavideos);

	public ListaVideos recuperarListaVideos(int codigo);

	public List<ListaVideos> recuperarTodosListasVideos();
}
