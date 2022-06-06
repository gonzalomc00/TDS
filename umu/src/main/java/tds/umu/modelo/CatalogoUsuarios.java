package tds.umu.modelo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;
import tds.umu.persistencia.IAdaptadorVideoDAO;

public class CatalogoUsuarios {

	private Map<String,Usuario> Usuarios; 
	private static CatalogoUsuarios unicaInstancia = new CatalogoUsuarios();
	
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	/*Esta clase contiene el Catálogo de todos los usuarios almacenados en memoria*/
	private CatalogoUsuarios() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			Usuarios = new HashMap<String,Usuario>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	/*Devuelve la instancia del Catálogo */
	public static CatalogoUsuarios getUnicaInstancia(){
		return unicaInstancia;
	}
	
	/*Devuelve una lista con todos los usuarios almacenados en memoria*/
	public List<Usuario> getUsuarios(){
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario c:Usuarios.values()) 
			lista.add(c);
		return lista;
	}
	/*Devuelve un objeto de un Usuario en específico*/
	
	public Usuario getUsuario(String usuario) {
		return Usuarios.get(usuario);
	}
	

	/*Método que añade un usuario al Catálogo*/
	public void addUsuario(Usuario user) {
		System.out.println(user.getUsuario());
		Usuarios.put(user.getUsuario(),user);
	}
	/*Método que elimina a un usuario del Catálogo*/
	public void removeUsuario (Usuario user) {
		Usuarios.remove(user.getUsuario());
	}
	
	/*Método para recuperar todos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> UsuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario user: UsuariosBD) {
			     Usuarios.put(user.getUsuario(),user);
		 }
	}
}
