package tds.umu.persistencia;

import java.util.List;
import tds.umu.modelo.Usuario;

public interface IAdaptadorUsuarioDAO {

	public void registrarUsuario(Usuario usuario);
	public void borrarUsuario(Usuario usuario);
	public void modificarUsuario(Usuario usuario);
	public Usuario recuperarUsuario(int codigo);
	public List<Usuario> recuperarTodosUsuarios();
	public void setPremium (Usuario usuario, boolean premium);
	//TODO tendríamos que poder recuperar los usuarios que haya en la BBDD
	//TODO no sé si habría que añadir algo de las listas aquí
}
