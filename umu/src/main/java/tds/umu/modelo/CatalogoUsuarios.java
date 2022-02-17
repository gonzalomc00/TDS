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

	private Map<Integer,Usuario> Usuarios; 
	private static CatalogoUsuarios unicaInstancia = new CatalogoUsuarios();
	
	private FactoriaDAO dao;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	
	private CatalogoUsuarios() {
		try {
  			dao = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
  			adaptadorUsuario = dao.getUsuarioDAO();
  			Usuarios = new HashMap<Integer,Usuario>();
  			this.cargarCatalogo();
  		} catch (DAOException eDAO) {
  			eDAO.printStackTrace();
  		}
	}
	
	public static CatalogoUsuarios getUnicaInstancia(){
		return unicaInstancia;
	}
	
	/*devuelve todos los Usuarios*/
	public List<Usuario> getUsuarios(){
		ArrayList<Usuario> lista = new ArrayList<Usuario>();
		for (Usuario c:Usuarios.values()) 
			lista.add(c);
		return lista;
	}
	
	public Usuario getUsuario(int codigo) {
		for (Usuario c:Usuarios.values()) {
			if (c.getCodigo()==codigo) return c;
		}
		return null;
	}

	
	public void addUsuario(Usuario user) {
		Usuarios.put(user.getCodigo(),user);
	}
	public void removeUsuario (Usuario user) {
		Usuarios.remove(user.getCodigo());
	}
	
	/*Recupera todos los Usuarios para trabajar con ellos en memoria*/
	private void cargarCatalogo() throws DAOException {
		 List<Usuario> UsuariosBD = adaptadorUsuario.recuperarTodosUsuarios();
		 for (Usuario user: UsuariosBD) 
			     Usuarios.put(user.getCodigo(),user);
	}
}
