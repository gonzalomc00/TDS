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
import tds.umu.modelo.CatalogoVideos;
import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Usuario;
import tds.umu.modelo.Video;
import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorEtiquetaDAO;
import tds.umu.persistencia.IAdaptadorListaVideosDAO;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;
import tds.umu.persistencia.IAdaptadorVideoDAO;
import tds.umu.persistencia.TDSFactoriaDAO;
import tds.umu.vista.VentanaPrincipal;
import tds.video.VideoWeb;

import umu.tds.componente.Videos;
import umu.tds.componente.VideosEvent;
import umu.tds.componente.VideosListener;
import tds.umu.modelo.CatalogoUsuarios;

public final class Controlador implements VideosListener, IEncendidoListener {

	private Usuario usuarioActual;
	private Video videoActual;
	private static Controlador unicaInstancia=null;
	private FactoriaDAO factoria;
	private static VideoWeb videoWeb;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorVideoDAO adaptadorVideo;
	private IAdaptadorEtiquetaDAO adaptadorEtiqueta;
	private IAdaptadorListaVideosDAO adaptadorListaVideos;
	
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiqueta;
	private CatalogoListasVideos catalogoListaVideos;
	
	private ComponenteBuscadorVideos buscadorVideos;

	private Controlador(){
		inicializarAdaptadores();
		inicializarCatalogos();
		usuarioActual = null;
		videoWeb= new VideoWeb();
		buscadorVideos= new ComponenteBuscadorVideos();
		buscadorVideos.addVideosListener(this);
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e){
			e.printStackTrace();
		}
	}

	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria= FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch(DAOException e) {
			e.printStackTrace();
		}
		adaptadorUsuario=factoria.getUsuarioDAO();
		adaptadorVideo=factoria.getVideoDAO();
		adaptadorEtiqueta=factoria.getEtiquetaDAO();
		adaptadorListaVideos=factoria.getListaVideosDAO();
	}

	private void inicializarCatalogos() {
		catalogoUsuarios=CatalogoUsuarios.getUnicaInstancia();
		catalogoVideos=CatalogoVideos.getUnicaInstancia();
		catalogoEtiqueta=CatalogoEtiquetas.getUnicaInstancia();
		catalogoListaVideos=CatalogoListasVideos.getUnicaInstancia();
		
	}

	

	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}
	
	public Video getVideoActual() {
		return videoActual;
	}
	private void setVideoActual(Video v) {
		videoActual=v;
	}

	
	public VideoWeb getReproductor() {
		return videoWeb;
	}

	
	
	public boolean esUsuarioRegistrado(String login) {
		// la he cambiado un poco porque creo que habria que comprobar el correo tb
		//ya veremos lo del correo 
		return catalogoUsuarios.getUsuario(login)!= null;

	}
	public boolean esVideoRegistrado(String vtext) {
		return catalogoVideos.getVideo(vtext)!=null;
	}
	public boolean esEtiqueRegistrado(String etext) {
		return catalogoEtiqueta.getEtiqueta(etext)!=null;
	}
	public boolean esListaVideoRegistrado(String lvtext) {
		return catalogoListaVideos.getListaVideos(lvtext)!=null;
	}

	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = catalogoUsuarios.getUsuario(nombre);
		if (usuario != null && usuario.getContraseña().equals(password)) {
			this.usuarioActual = usuario;
			return true;
		}
		return false;
	}

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

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getUsuario()))
			return false;


		adaptadorUsuario.borrarUsuario(usuario);

		CatalogoUsuarios.getUnicaInstancia().removeUsuario(usuario);
		return true;
	}

	public void setUsuario(Usuario u) {
		this.usuarioActual= u;
		
	}

	public void cargarVideos(File xml) {
		buscadorVideos.setFichero(xml);
		
	}

	@Override
	public void enteradoSubidaVideos(EventObject arg0) {
		VideosEvent ve = (VideosEvent) arg0;
		Videos videos= ve.getVideos();
		for(umu.tds.componente.Video v: videos.getVideo()) {
			
			//Obtenemos los objetos etiqueta vinculado a los videos
			List<Etiqueta> etiquetas= new LinkedList<Etiqueta>();
			for(String et: v.getEtiqueta()) {
				if(!esEtiqueRegistrado(et)) {
					Etiqueta e= new Etiqueta(et);
					etiquetas.add(e);
					catalogoEtiqueta.addEtiqueta(e);
				}
				else {
					etiquetas.add(catalogoEtiqueta.getEtiqueta(et));
				}
			}
			
			Video vid= new Video(v.getURL(),v.getTitulo(),etiquetas);
			adaptadorVideo.registrarVideo(vid);
			//Volvemos a reiniciar los catalogos para tener los nuevos videos y las nuevas etiquetas que hayan podido surgir
			catalogoVideos.addVideo(vid);
		}
	}

	@Override
	public void enteradoCambioEncendido(EventObject arg0) {
		JFileChooser chooser= new JFileChooser();
		int returnVal= chooser.showOpenDialog(null);
		if(returnVal == JFileChooser.APPROVE_OPTION) {
			File currentFile= chooser.getSelectedFile();
			cargarVideos(currentFile);
		}
		
		
	}
	
	public List<Video> getListaVideos(){
		return catalogoVideos.getVideos();
		
	}

	public List<Etiqueta> getEtiquetas() {
		return catalogoEtiqueta.getEtiquetas();
	}

	public void logout() {
		usuarioActual=null;
		
	}

	public Video getVideo(String titulo) {
		return catalogoVideos.getVideo(titulo);
	}

	public List<Video> obtenerBusqueda(String text, LinkedList<String> etiquetas_Sel_Lista) {
		List<Etiqueta> etiquetas= getEtiqFromText(etiquetas_Sel_Lista);
		List<Video> videosMatch= catalogoVideos.getBusqueda(text,etiquetas);
		return videosMatch;
	}

	private List<Etiqueta> getEtiqFromText(LinkedList<String> etiquetas_Sel_Lista) {
		return etiquetas_Sel_Lista.stream()
				.map(text->catalogoEtiqueta.getEtiqueta(text))
				.collect(Collectors.toList());
			
	}
	
	public void actualizarVideoSeleccionado(Video v) {
		v.aumentarReproduccion();
		adaptadorVideo.modificarVideo(v);
		//Añadimos el video seleccionado a la lista de recientes del usuario
		usuarioActual.añadirReciente(v);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		setVideoActual(v);
	}

	public List<ListaVideos> obtenerPlayListsUser() {
		return usuarioActual.getListas();
	}

	public ListaVideos obtenerLista(String lista) {
		return usuarioActual.getLista(lista);
	}

	public ListaVideos crearLista(String text) {
		//No se si deberia añadir las cosas al catalogo. Creo que deberia dejarlo y asi y que se cargue al catalogo cuando se vuelva
		//a abrir.
		ListaVideos lv=  usuarioActual.crearLista(text);
		adaptadorListaVideos.registrarListaVideos(lv);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		catalogoListaVideos.addListaVideos(lv);
		return lv;
	}

	public void añadirVideoPlaylist(ListaVideos lv_creada, Video v_sel) {
		lv_creada.añadirVideo(v_sel);
		catalogoListaVideos.addListaVideos(lv_creada);
		adaptadorListaVideos.modificarListaVideos(lv_creada);

		
	}

	public void eliminarVideoLista(ListaVideos lv_creada, Video v_sel) {
		lv_creada.eliminarVideo(v_sel);
		adaptadorListaVideos.modificarListaVideos(lv_creada);
		
	}

	public void borrarLista(ListaVideos lv_creada) {
		
		adaptadorListaVideos.borrarListaVideos(lv_creada);
		catalogoListaVideos.removeListaVideos(lv_creada);
		usuarioActual.eliminarLista(lv_creada);
		adaptadorUsuario.modificarUsuario(usuarioActual);
		
		
	}

	public List<Video> obtenerRecientesUser() {
		return usuarioActual.getRecientes();
	}
	


	
}
