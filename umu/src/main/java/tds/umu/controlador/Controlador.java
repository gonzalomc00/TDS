package tds.umu.controlador;

/*Bibliotecas Java para la gestión de archivos, eventos y colleciones*/
import java.io.File;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
/*Biblioteca para seleccionar el archivo con los vídeos, pulsar el boton, y los disntintos componentes*/
import javax.swing.JFileChooser;
import pulsador.IEncendidoListener;
import umu.tds.componente.*;

/*Importación de clases del modelo*/
import tds.umu.modelo.CatalogoEtiquetas;
import tds.umu.modelo.CatalogoListasVideos;
import tds.umu.modelo.CatalogoUsuarios;
import tds.umu.modelo.CatalogoVideos;
import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.FactoriaFiltros;
import tds.umu.modelo.FiltroVideo;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Usuario;
import tds.umu.modelo.Video;
/*Importación de clases de la persistencia*/
import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorEtiquetaDAO;
import tds.umu.persistencia.IAdaptadorListaVideosDAO;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;
import tds.umu.persistencia.IAdaptadorVideoDAO;
/*Importación de la clase que reproduce el vídeo asociado a una URL*/
import tds.video.VideoWeb;
/*Importación de los componentes de los vídeos*/
import umu.tds.componente.Videos;
import umu.tds.componente.VideosEvent;
import umu.tds.componente.VideosListener;

public final class Controlador implements VideosListener, IEncendidoListener {
	/* Atributos asociados con los catálogos de objetos de las diferentes clases */

	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiqueta;
	private CatalogoListasVideos catalogoListaVideos;

	/* Atributos asociados con la funcionalidad a manejar */
	private Usuario usuarioActual;
	private Video videoActual;
	private static Controlador unicaInstancia = null;
	private static VideoWeb videoWeb;

	/*
	 * Atributos asociados con los adaptadores asociados a las clases de nuestro
	 * VideoApp
	 */

	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorVideoDAO adaptadorVideo;
	private IAdaptadorListaVideosDAO adaptadorListaVideos;
	private IAdaptadorEtiquetaDAO adaptadorEtiquetas;

	/* El buscador de vídeos que usamos en la funcionalidad */

	private ComponenteBuscadorVideos buscadorVideos;

	/* Atributo que contiene la clase Factoría asociada a los filtros */
	private FactoriaFiltros factoriaFiltros = FactoriaFiltros.getUnicaInstancia();

	private Controlador() {
		/* Inicializamos todos los componentes de AppVideo */
		inicializarAdaptadores();
		inicializarCatalogos();
		usuarioActual = null;
		videoWeb = new VideoWeb();
		buscadorVideos = new ComponenteBuscadorVideos();
		buscadorVideos.addVideosListener(this);
		try {
			FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	/* Método para inicializar los adaptadores de la factoria asociada */
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario = factoria.getUsuarioDAO();
		adaptadorVideo = factoria.getVideoDAO();
		adaptadorListaVideos = factoria.getListaVideosDAO();
		adaptadorEtiquetas = factoria.getEtiquetaDAO();
		factoria.getEtiquetaDAO();

	}

	/*---------------- C A T A L O G O S --------------------------*/

	/* Método para inicializar los catálogos */

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoVideos = CatalogoVideos.getUnicaInstancia();
		catalogoEtiqueta = CatalogoEtiquetas.getUnicaInstancia();
		catalogoListaVideos = CatalogoListasVideos.getUnicaInstancia();

	}

	/* Métodos para comprobar si los atributos están registrados en los catálogos */
	public boolean esUsuarioRegistrado(String login) {
		// TODO la he cambiado un poco porque creo que habria que comprobar el correo tb
		// ya veremos lo del correo
		return catalogoUsuarios.getUsuario(login) != null;

	}

	public boolean esVideoRegistrado(String vtext) {
		return catalogoVideos.getVideo(vtext) != null;
	}

	public boolean esEtiqueRegistrado(String etext) {
		return catalogoEtiqueta.getEtiqueta(etext) != null;
	}

	public boolean esListaVideoRegistrado(String lvtext) {
		return catalogoListaVideos.getListaVideos(lvtext) != null;
	}

	/* Método para obtener el controlador asociado */

	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}

	/* Métodos para obtener/establecer los atributos del controlador */

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public void setUsuario(Usuario u) {
		this.usuarioActual = u;

	}

	public Video getVideoActual() {
		return videoActual;
	}

	private void setVideoActual(Video v) {
		videoActual = v;
	}

	public VideoWeb getReproductor() {
		return videoWeb;
	}

	public List<Video> getListaVideos() {
		return catalogoVideos.getVideos();

	}

	public List<Etiqueta> getEtiquetas() {
		return catalogoEtiqueta.getEtiquetas();
	}

	public Video getVideo(String titulo) {
		return catalogoVideos.getVideo(titulo);
	}

	/*---------------- U S U A R I O  --------------------------*/

	/* Método para comprobar si el usuario ha hecho el login correctamente */
	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = catalogoUsuarios.getUsuario(nombre);
		if (usuario != null && usuario.getContraseña().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}

	/* Método para registrar a un usuario en el sistema */
	public boolean registrarUsuario(String nombre, String apellidos, LocalDate fecha, String email, String login,
			String contra) {

		if (esUsuarioRegistrado(login)) {

			return false;

		}
		Usuario usuario = new Usuario(nombre, apellidos, fecha, email, login, contra);
		/* Adaptador DAO para almacenar el nuevo Usuario en la BD */
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}

	/* Método para borrar a un usuario del sistema */
	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getUsuario()))
			return false;

		adaptadorUsuario.borrarUsuario(usuario);

		CatalogoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;
	}

	/* Método que cierra sesión en un usuario */
	public void logout() {
		usuarioActual = null;

	}
	// TODO REVISAR USO DE LOS SETS
	/* Método para actualizar a premium a un usuario */

	public void actualizarPremium() {
		usuarioActual.setPremium(true);
		adaptadorUsuario.modificarUsuario(usuarioActual);

	}

	/* Método para consultar si el usuario es premium */
	public boolean esUserPremium() {
		return usuarioActual.isPremium();
	}

	// TODO FILTROS -REVISAR-

	/*
	 * Método para comprobar si un usuario tiene un vídeo en alguna de sus playlists
	 */

	public boolean userTieneVideo(Video v) {
		return usuarioActual.tieneVideo(v);
	}

	/* Método para cambiarle el filtro a un usuario */
	public void cambiarFiltro(String filtro_selected) {
		FiltroVideo filtro = factoriaFiltros.getFiltro(filtro_selected);
		usuarioActual.setFiltro(filtro);
	}
	/*---------------- G E S T I O N  F I C H E R O  X M L --------------------------*/

	/* Método para cargar el fichero xml que contiene los videos en el sistema */
	public void cargarVideos(File xml) {
		buscadorVideos.setFichero(xml);

	}

	/*
	 * Método para comprobar si se ha cargado el archivo que contiene los vídeos de
	 * forma correcta
	 */

	@Override
	public void enteradoCambioEncendido(EventObject arg0) {
		JFileChooser chooser = new JFileChooser();
		int returnVal = chooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File currentFile = chooser.getSelectedFile();
			cargarVideos(currentFile);
		}

	}

	/* Método que comprueba si se han subido los vídeos correctamente */

	@Override
	public void enteradoSubidaVideos(EventObject arg0) {
		VideosEvent ve = (VideosEvent) arg0;
		Videos videos = ve.getVideos();
		for (umu.tds.componente.Video v : videos.getVideo()) {
			if (!esVideoRegistrado(v.getTitulo())) {
				// Obtenemos los objetos etiqueta vinculado a los videos
				List<Etiqueta> etiquetas = new LinkedList<Etiqueta>();
				for (String et : v.getEtiqueta()) {
					if (!esEtiqueRegistrado(et)) {
						Etiqueta e = new Etiqueta(et);
						etiquetas.add(e);
						catalogoEtiqueta.addEtiqueta(e);
					} else {
						etiquetas.add(catalogoEtiqueta.getEtiqueta(et));
					}
				}

				Video vid = new Video(v.getURL(), v.getTitulo(), etiquetas);
				adaptadorVideo.registrarVideo(vid);
				// Volvemos a reiniciar los catalogos para tener los nuevos videos y las nuevas
				// etiquetas que hayan podido surgir
				catalogoVideos.addVideo(vid);
			}
		}
	}

	/*---------------- V I D E O S--------------------------*/

	/* Método para obtener los resultados de una búsqueda */
	public List<Video> obtenerBusqueda(String text, LinkedList<String> etiquetas_Sel_Lista) {
		List<Etiqueta> etiquetas = getEtiqFromText(etiquetas_Sel_Lista);
		List<Video> videosMatch = catalogoVideos.getBusqueda(text, etiquetas, usuarioActual.getFiltro());
		return videosMatch;
	}

	/* Método para actualizar la información asociada a un vídeo */

	public void actualizarVideoSeleccionado(Video v) {
		v.aumentarReproduccion();
		adaptadorVideo.modificarVideo(v);
		// Añadimos el video seleccionado a la lista de recientes del usuario
		usuarioActual.añadirReciente(v);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		setVideoActual(v);
	}

	/* Método para obtener una lista con los vídeos más vistos */

	public List<Video> obtenerMasVisto() {
		return catalogoVideos.getMasVistos();
	}

	/*---------------- E T I Q U E T A S --------------------------*/

	/*
	 * Método para obtener las etiquetas asociadas a una lista de etiquetas
	 * seleccionadas
	 */
	private List<Etiqueta> getEtiqFromText(LinkedList<String> etiquetas_Sel_Lista) {
		return etiquetas_Sel_Lista.stream().map(text -> catalogoEtiqueta.getEtiqueta(text))
				.collect(Collectors.toList());

	}

	public void añadirEtiqueta(String text) {

		if (!esEtiqueRegistrado(text)) {
			Etiqueta etq = videoActual.crearEtiqueta(text);
			adaptadorEtiquetas.registrarEtiqueta(etq);
			catalogoEtiqueta.addEtiqueta(etq);
		} else {
			Etiqueta etq = catalogoEtiqueta.getEtiqueta(text);
			if (!videoActual.contieneEtiqueta(etq)) {
				videoActual.addEtiqueta(etq);
			}

		}

		adaptadorVideo.modificarVideo(videoActual);

	}

	/*---------------- L I S T A S  D E  V I D E O S--------------------------*/

	/* Método para obtener las playlists asociadas a un usuario */

	public List<ListaVideos> obtenerPlayListsUser() {
		return usuarioActual.getListas();
	}
	/* Método para obtener una playlist a través de su nombre */

	public ListaVideos obtenerLista(String lista) {
		return usuarioActual.getLista(lista);
	}

	/* Método para crear una playlist */

	public ListaVideos crearLista(String text) {
		// TODO No se si deberia añadir las cosas al catalogo. Creo que deberia dejarlo
		// y asi
		// y que se cargue al catalogo cuando se vuelva
		// a abrir.
		ListaVideos lv = usuarioActual.crearLista(text);
		adaptadorListaVideos.registrarListaVideos(lv);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		catalogoListaVideos.addListaVideos(lv);
		return lv;
	}

	/* Método para añadir videos a la playlist */

	public void añadirVideoPlaylist(ListaVideos lv_creada, Video v_sel) {
		lv_creada.añadirVideo(v_sel);
		catalogoListaVideos.addListaVideos(lv_creada);
		adaptadorListaVideos.modificarListaVideos(lv_creada);

	}
	/* Método para eliminar videos de la playlist */

	public void eliminarVideoLista(ListaVideos lv_creada, Video v_sel) {
		lv_creada.eliminarVideo(v_sel);
		adaptadorListaVideos.modificarListaVideos(lv_creada);

	}
	/* Método para eliminar la playlist */

	public void borrarLista(ListaVideos lv_creada) {

		adaptadorListaVideos.borrarListaVideos(lv_creada);
		catalogoListaVideos.removeListaVideos(lv_creada);
		usuarioActual.eliminarLista(lv_creada);
		adaptadorUsuario.modificarUsuario(usuarioActual);

	}

	/* Método para obtener una playlist de videos recientes del usuario */

	public List<Video> obtenerRecientesUser() {
		return usuarioActual.getRecientes();
	}

}
