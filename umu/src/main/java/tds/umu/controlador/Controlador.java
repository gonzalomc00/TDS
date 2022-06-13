package tds.umu.controlador;


import java.io.File;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFileChooser;
import pulsador.IEncendidoListener;
import umu.tds.componente.*;


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

import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorEtiquetaDAO;
import tds.umu.persistencia.IAdaptadorListaVideosDAO;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;
import tds.umu.persistencia.IAdaptadorVideoDAO;

import tds.video.VideoWeb;

import umu.tds.componente.Videos;
import umu.tds.componente.VideosEvent;
import umu.tds.componente.VideosListener;

public final class Controlador implements VideosListener, IEncendidoListener {
	/* Atributos asociados con los catálogos de objetos de las diferentes clases */

	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiqueta;
	private CatalogoListasVideos catalogoListaVideos;

	
	/*Atributo Singleton*/
	private static Controlador unicaInstancia = null;

	/*
	 * Atributos asociados con los adaptadores asociados a las clases de nuestro
	 * VideoApp
	 */

	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorVideoDAO adaptadorVideo;
	private IAdaptadorListaVideosDAO adaptadorListaVideos;
	private IAdaptadorEtiquetaDAO adaptadorEtiquetas;

	/* El buscador de vídeos perteneciente al componente */

	private ComponenteBuscadorVideos buscadorVideos;

	/* Atributo que contiene la clase Factoría asociada a los filtros */
	private FactoriaFiltros factoriaFiltros = FactoriaFiltros.getUnicaInstancia();
	
	/* Atributos asociados con la funcionalidad a manejar */
	private Usuario usuarioActual;
	private Video videoActual;
	private static VideoWeb videoWeb;

	private Controlador() {
		/* Inicializamos todos los catalogos,adaptadores y elementos necesarios*/
		inicializarAdaptadores();
		inicializarCatalogos();
		usuarioActual = null;
		videoActual=null;
		videoWeb = new VideoWeb();
		buscadorVideos = new ComponenteBuscadorVideos();
		buscadorVideos.addVideosListener(this);
		
	}

	/* Método para inicializar la factoría DAO y todos los adaptadores para los distintos
	 * elementos del modelo
	 */
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

	

	}

	/*---------------- C A T A L O G O S --------------------------*/

	/* Método para inicializar los catálogos */

	private void inicializarCatalogos() {
		catalogoUsuarios = CatalogoUsuarios.getUnicaInstancia();
		catalogoVideos = CatalogoVideos.getUnicaInstancia();
		catalogoEtiqueta = CatalogoEtiquetas.getUnicaInstancia();
		catalogoListaVideos = CatalogoListasVideos.getUnicaInstancia();

	}


	public boolean esVideoRegistrado(String vtext) {
		return catalogoVideos.getVideo(vtext) != null;
	}

	public boolean esEtiqueRegistrado(String etext) {
		return catalogoEtiqueta.getEtiqueta(etext) != null;
	}

	public boolean esListaVideoRegistrado(int lvtext) {
		return catalogoListaVideos.getListaVideos(lvtext) != null;
	}

	/* Método para obtener el controlador - parte del patrón Singleton */

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


	/*---------------- U S U A R I O  --------------------------*/
	
	
	/*Metodo para comprobar si ya hay algún usuario registrado con el mismo nickname */
	public boolean esUsuarioRegistrado(String login) {
		return catalogoUsuarios.getUsuario(login) != null;

	}
	
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
		adaptadorUsuario.registrarUsuario(usuario);
		catalogoUsuarios.addUsuario(usuario);
		return true;
	}

	/* Método para borrar a un usuario del sistema */
	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getUsuario()))
			return false;

		adaptadorUsuario.borrarUsuario(usuario);
		catalogoUsuarios.removeUsuario(usuario);
		return true;
	}

	
	/* Método para que el usuario actual no siga siendo el mismo tras hacer logout */
	public void logout() {
		usuarioActual = null;

	}

	
	/* Método para actualizar a premium a un usuario */

	public void actualizarPremium() {
		usuarioActual.setPremium(true);
		adaptadorUsuario.modificarUsuario(usuarioActual);

	}

	/* Método para consultar si el usuario es premium */
	public boolean esUserPremium() {
		return usuarioActual.isPremium();
	}


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

	/*
	 * Método para cargar el fichero xml que contiene los videos en el sistema 
	 * Este método pertence al componene CargadorVideos propuesto en la asignatura
	 */
	public void cargarVideos(File xml) {
		buscadorVideos.setFichero(xml);

	}

	/*
	 * Método que se ejecutará cuando se interactúe con el Componente Luz proporcionado
	 * por los profesores de la asignatura. Permite seleccionar un fichero con JFileChooser
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

	/*
	 *  Método utilizado para cargar el contenido del fichero XML. Se ejecutará cuando el componente avise a todos sus listeners 
	 * 	Tenemos que hacer la transición entre la clase Video del componente a la clase Video de nuestro modelo. Para ello vamos cogiendo los campos y
	 *  revisamos que no se encuentren ya en la persistencia. Si lo están los recuperamos del catálogo y hacemos todas las modificaciones necesarias  
	 * */

	@Override
	public void enteradoSubidaVideos(EventObject arg0) {
		VideosEvent ve = (VideosEvent) arg0;
		Videos videos = ve.getVideos();
		for (umu.tds.componente.Video v : videos.getVideo()) {
			if (!esVideoRegistrado(v.getTitulo())) {
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

				Video vid = new Video(v.getURL(), v.getTitulo());
				for(Etiqueta etq: etiquetas) {
					vid.addEtiqueta(etq);
				}
				adaptadorVideo.registrarVideo(vid);
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

	/* 
	 * Método para actualizar el video que se va a reproducir en un instante
	 * Esto implica que se actualice su contador de visualizaciones y se añada a la lista de recientes del usuario actual.
	 */

	public void actualizarVideoSeleccionado(Video v) {
		v.aumentarReproduccion();
		adaptadorVideo.modificarVideo(v);
		usuarioActual.añadirReciente(v);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		setVideoActual(v);
	}

	/* Método para obtener una lista con los vídeos más vistos */

	public List<Video> obtenerMasVisto() {
		return catalogoVideos.getMasVistos();
	}
	
	/* Método para obtener la lista de videos recientes del usuario */

	public List<Video> obtenerRecientesUser() {
		return usuarioActual.getRecientes();
	}
	
	
	
	
	/* Método para obtener un vídeo del catálogo */
	public Video getVideo(String titulo) {
		return catalogoVideos.getVideo(titulo);
	}

	/*---------------- E T I Q U E T A S --------------------------*/

	/*
	 * Método para obtener una lista de etiquetas a partir de una lista de strings. Estos strings son los nombres de estas.
	 */
	private List<Etiqueta> getEtiqFromText(LinkedList<String> etiquetas_Sel_Lista) {
		return etiquetas_Sel_Lista.stream().map(text -> catalogoEtiqueta.getEtiqueta(text))
				.collect(Collectors.toList());

	}
	
	/*
	 * Método para añadir una etiqueta al video actual que se está reproduciendo. Solo se añade si el video no tiene ya la etiqueta. 
	 */

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
	
	/*
	 * Lista para obtener todas las etiquetas que se encuentran en la base de datos. 
	 */
	public List<Etiqueta> getEtiquetas() {
		return catalogoEtiqueta.getEtiquetas();
	}


	/*---------------- L I S T A S  D E  V I D E O S--------------------------*/

	/* Método para obtener las playlists asociadas al usuario actual */

	public List<ListaVideos> obtenerPlayListsUser() {
		return usuarioActual.getListas();
	}
	/* Método para obtener una playlist del usuario actual a través de su nombre */

	public ListaVideos obtenerLista(String lista) {
		return usuarioActual.getLista(lista);
	}

	/* Método para crear una playlist. El encargado de crear la lista en este caso será el propio usuario.  */

	public ListaVideos crearLista(String text) {
		ListaVideos lv = usuarioActual.crearLista(text);
		adaptadorListaVideos.registrarListaVideos(lv);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		catalogoListaVideos.addListaVideos(lv);
		return lv;
	}

	/* Método para añadir videos a una playlist */

	public void añadirVideoPlaylist(ListaVideos lv_creada, Video v_sel) {
		lv_creada.añadirVideo(v_sel);
		adaptadorListaVideos.modificarListaVideos(lv_creada);

	}
	/* Método para eliminar videos de una playlist */

	public void eliminarVideoLista(ListaVideos lv_creada, Video v_sel) {
		lv_creada.eliminarVideo(v_sel);
		adaptadorListaVideos.modificarListaVideos(lv_creada);

	}
	/* Método para eliminar una playlist */

	public void borrarLista(ListaVideos lv_creada) {

		adaptadorListaVideos.borrarListaVideos(lv_creada);
		catalogoListaVideos.removeListaVideos(lv_creada);
		usuarioActual.eliminarLista(lv_creada);
		adaptadorUsuario.modificarUsuario(usuarioActual);

	}


	/*
	 * Método para obtener todas las listas de videos 
	 */
	public List<Video> getListaVideos() {
		return catalogoVideos.getVideos();

	}


}
