package tds.umu.persistencia;

import java.util.List;
import tds.umu.modelo.Cliente;

public interface IAdaptadorClienteDAO {

	public void registrarCliente(Cliente cliente);
	public void borrarCliente(Cliente cliente);
	public void modificarCliente(Cliente cliente);
	public Cliente recuperarCliente(int codigo);
	public List<Cliente> recuperarTodosClientes();
}
