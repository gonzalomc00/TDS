package tds.umu.controlador;

import java.io.File;
import java.time.LocalDate;
import java.util.EventObject;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFileChooser;

import pulsador.IEncendidoListener;
import tds.umu.modelo.CatalogoEtiquetas;
import tds.umu.modelo.CatalogoListasVideos;
import tds.umu.modelo.CatalogoVideos;
import tds.umu.modelo.Etiqueta;
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
import umu.tds.componente.ComponenteBuscadorVideos;
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
	
	public VideoWeb getReproductor() {
		return videoWeb;
	}

	
	
	public boolean esUsuarioRegistrado(String login) {
		// la he cambiado un poco porque creo que habria que comprobar el correo tb
		//ya veremos lo del correo 
		return CatalogoUsuarios.getUnicaInstancia().getUsuario(login)!= null;

	}

	public boolean loginUsuario(String nombre, String password) {
		Usuario usuario = CatalogoUsuarios.getUnicaInstancia().getUsuario(nombre);
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

		} // tengo que ver como pasar la fecha
		Usuario usuario = new Usuario(nombre, apellidos, fecha, email, login, contra);
		 /* Adaptador DAO para almacenar el nuevo Usuario en la BD */
		adaptadorUsuario.registrarUsuario(usuario);
		CatalogoUsuarios.getUnicaInstancia().addUsuario(usuario);
		return true;
	}
//Esto no sería algo q hará el catalogo tambien?

	public boolean borrarUsuario(Usuario usuario) {
		if (!esUsuarioRegistrado(usuario.getUsuario()))
			return false;

		IAdaptadorUsuarioDAO usuarioDAO = factoria.getUsuarioDAO(); /* Adaptador DAO para borrar el Usuario de la BD */
		usuarioDAO.borrarUsuario(usuario);

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
				Etiqueta e= new Etiqueta(et);
				etiquetas.add(e);
				catalogoEtiqueta.addEtiqueta(e);
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

}
