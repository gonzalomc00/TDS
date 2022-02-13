package tds.umu.controlador;

import java.time.LocalDate;

import tds.umu.modelo.Usuario;
import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;
import umu.tds.dominio.CatalogoUsuarios;

public final class Controlador {

	private Usuario usuarioActual;
	private static Controlador unicaInstancia;
	private FactoriaDAO factoria;

	private Controlador() {
		usuarioActual = null;
		try {
			factoria = FactoriaDAO.getInstancia();
		} catch (DAOException e) {
			e.printStackTrace();
		}
	}

	public static Controlador getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new Controlador();
		return unicaInstancia;
	}

	public Usuario getUsuarioActual() {
		return usuarioActual;
	}

	public boolean esUsuarioRegistrado(String login, String email) {
		// la he cambiado un poco porque creo que habria que comprobar el correo tb
		if ((CatalogoUsuarios.getUnicaInstancia().isUsuarioRegistrado(login))
				&& (CatalogoUsuarios.getUnicaInstancia().isCorreoRegistrado(email))) {
			return false;
		} else
			return true;

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

		if (esUsuarioRegistrado(login, email)) {

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
		if (!esUsuarioRegistrado(usuario.getUsuario(), usuario.getEmail()))
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
