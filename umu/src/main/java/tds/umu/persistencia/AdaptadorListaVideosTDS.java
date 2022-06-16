package tds.umu.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Video;

public class AdaptadorListaVideosTDS implements IAdaptadorListaVideosDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorListaVideosTDS unicaInstancia = null;

	public static AdaptadorListaVideosTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorListaVideosTDS();

		return unicaInstancia;
	}

	private AdaptadorListaVideosTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* Registramos una Lista de Vídeos asignándole un código */
	@Override
	public void registrarListaVideos(ListaVideos listavideos) {
		Entidad eListaVideos = null;
		try {
			eListaVideos = servPersistencia.recuperarEntidad(listavideos.getCodigo());
		} catch (NullPointerException e) {
		}
		if (eListaVideos != null)
			return;

		AdaptadorVideoTDS adaptadorVideos = AdaptadorVideoTDS.getUnicaInstancia();
		for (Video v : listavideos.getVideos()) {
			adaptadorVideos.registrarVideo(v);
		}

		eListaVideos = new Entidad();
		eListaVideos.setNombre("listavideos");
		eListaVideos
				.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", listavideos.getNombre()),
						new Propiedad("videos", obtenerCodigosVideos(listavideos.getVideos())))));

		eListaVideos = servPersistencia.registrarEntidad(eListaVideos);
		listavideos.setCodigo(eListaVideos.getId());
	}

	/* Borramos una Lista de videos */
	@Override
	public void borrarListaVideos(ListaVideos listavideos) {
		Entidad eListaVideos;
		eListaVideos = servPersistencia.recuperarEntidad(listavideos.getCodigo());
		servPersistencia.borrarEntidad(eListaVideos);
	}

	/* Modificamos una lista de Vídeos */
	@Override
	public void modificarListaVideos(ListaVideos listavideos) {
		Entidad eListaVideos = servPersistencia.recuperarEntidad(listavideos.getCodigo());

		for (Propiedad prop : eListaVideos.getPropiedades()) {
			if (prop.getNombre().equals("nombre")) {
				prop.setValor(String.valueOf(listavideos.getNombre()));
				;
			} else if (prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(listavideos.getCodigo()));
			} else if (prop.getNombre().equals("videos")) {
				String lineas = obtenerCodigosVideos(listavideos.getVideos());
				prop.setValor(String.valueOf(lineas));
			}

			servPersistencia.modificarPropiedad(prop);
		}

	}

	/* Recuperamos una lista de videos mediante su código */
	@Override
	public ListaVideos recuperarListaVideos(int codigo) {

		Entidad eListaVideos = null;
		String nombre;
		List<Video> videos;

		try {
			eListaVideos = servPersistencia.recuperarEntidad(codigo);
		} catch (NullPointerException e) {
		}

		if (eListaVideos == null)
			return null;

		nombre = servPersistencia.recuperarPropiedadEntidad(eListaVideos, "nombre");
		videos = obtenerVideosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eListaVideos, "videos"));

		ListaVideos listavideos = new ListaVideos(nombre);
		listavideos.setCodigo(codigo);

		for (Video v : videos)
			listavideos.añadirVideo(v);

		return listavideos;
	}

	/* Recuperamos todas las listas de videos */
	@Override
	public List<ListaVideos> recuperarTodosListasVideos() {

		List<Entidad> eListasVideos = servPersistencia.recuperarEntidades("listavideos");
		List<ListaVideos> listas = new LinkedList<ListaVideos>();

		for (Entidad eListaVideos : eListasVideos)
			listas.add(recuperarListaVideos(eListaVideos.getId()));

		return listas;
	}

	/* FUNCIONES AUXILIARES */
	/* Obtenemos el código de los vídeos */
	private String obtenerCodigosVideos(List<Video> videos) {
		String lineas = "";
		for (Video v : videos)
			lineas += v.getCodigo() + " ";
		return lineas.trim();
	}

	/* Obtenemos los vídeos a partir de sus códigos */
	private List<Video> obtenerVideosDesdeCodigos(String lineas) {
		List<Video> videos = new LinkedList<Video>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			videos.add(adaptadorVideo.recuperarVideo(Integer.valueOf((String) strTok.nextElement())));
		}

		return videos;
	}

}
