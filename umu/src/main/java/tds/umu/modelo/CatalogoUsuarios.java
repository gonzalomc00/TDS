package tds.umu.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;

public class CatalogoUsuarios {

	private Map<String, Usuario> Usuarios;
	private static CatalogoUsuarios unicaInstancia = new CatalogoUsuarios();
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;

	/*
	 * Esta clase mantiene en memoria todos los usuarios registrados. De esta forma
	 * no hace falta entrar todo el rato a la base de datos a obtener los objetos.
	 */
	private CatalogoUsuarios() {
		try {
			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
			adaptadorUsuario = dao.getUsuarioDAO();
			Usuarios = new HashMap<String, Usuario>();
			this.cargarCatalogo();
		} catch (DAOException eDAO) {
			eDAO.printStackTrace();
		}
	}

	/* Devuelve la instancia del Catálogo - Patrón Singleton */
	public static CatalogoUsuarios getUnicaInstancia() {
		return unicaInstancia;
	}

	/* Devuelve una lista con todos los usuarios almacenados en memoria */
	public List<Usuario> getUsuarios() {
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario c : Usuarios.values())
			lista.add(c);
		return lista;
	}

	/* Devuelve un Usuario en específico mediante su nombre de usuario */
	public Usuario getUsuario(String usuario) {
		return Usuarios.get(usuario);
	}

	/* Método que añade un usuario al Catálogo */
	public void addUsuario(Usuario user) {
		Usuarios.put(user.getUsuario(), user);
	}

	/* Método que elimina a un usuario del Catálogo */
	public void removeUsuario(Usuario user) {
		Usuarios.remove(user.getUsuario());
	}

	/*
	 * Método para recuperar todos los usuarios en memoria utilizando el adaptador
	 */
	private void cargarCatalogo() throws DAOException {
		List<Usuario> UsuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		for (Usuario user : UsuariosBD) {
			Usuarios.put(user.getUsuario(), user);
		}
	}
}
