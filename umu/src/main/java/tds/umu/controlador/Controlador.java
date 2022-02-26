package tds.umu.controlador;

import java.time.LocalDate;

import tds.umu.modelo.CatalogoEtiquetas;
import tds.umu.modelo.CatalogoListasVideos;
import tds.umu.modelo.CatalogoVideos;
import tds.umu.modelo.Usuario;
import tds.umu.modelo.Video;
import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorEtiquetaDAO;
import tds.umu.persistencia.IAdaptadorListaVideosDAO;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;
import tds.umu.persistencia.IAdaptadorVideoDAO;
import tds.umu.persistencia.TDSFactoriaDAO;
import tds.umu.modelo.CatalogoUsuarios;

public final class Controlador {

	private Usuario usuarioActual;
	private Video videoActual;
	private static Controlador unicaInstancia=null;
	private FactoriaDAO factoria;
	
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorVideoDAO adaptadorVideo;
	private IAdaptadorEtiquetaDAO adaptadorEtiqueta;
	private IAdaptadorListaVideosDAO adaptadorListaVideos;
	
	private CatalogoUsuarios catalogoUsuarios;
	private CatalogoVideos catalogoVideos;
	private CatalogoEtiquetas catalogoEtiqueta;
	private CatalogoListasVideos catalogoListaVideos;

	private Controlador(){
		inicializarAdaptadores();
		inicializarCatalogos();
		usuarioActual = null;
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
		IAdaptadorUsuarioDAO usuarioDAO = factoria
				.getUsuarioDAO(); /* Adaptador DAO para almacenar el nuevo Usuario en la BD */
		usuarioDAO.registrarUsuario(usuario);
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

}
