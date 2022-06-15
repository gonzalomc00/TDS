package tds.umu.persistencia;

import java.util.List;

import tds.umu.modelo.Video;

public interface IAdaptadorVideoDAO {

	public void registrarVideo(Video video);
	public void borrarVideo(Video video);
	public void modificarVideo(Video video);
	public Video recuperarVideo(int codigo);
	public List<Video> recuperarTodosVideos();

}
