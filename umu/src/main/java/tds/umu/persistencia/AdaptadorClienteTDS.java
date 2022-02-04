package tds.umu.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import tds.umu.modelo.Cliente;
import beans.Entidad;
import beans.Propiedad;




//Usa un pool para evitar problemas doble referencia con ventas
public class AdaptadorClienteTDS implements IAdaptadorClienteDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorClienteTDS unicaInstancia = null;

	public static AdaptadorClienteTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			unicaInstancia=new AdaptadorClienteTDS();
	
		return unicaInstancia;
	}

	private AdaptadorClienteTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un cliente se le asigna un identificador �nico */
	public void registrarCliente(Cliente cliente) {
		Entidad eCliente = null;
		if(cliente.getCodigo()!=null) {
		// Si la entidad esta registrada no la registra de nuevo
		try {
			eCliente = servPersistencia.recuperarEntidad(cliente.getCodigo());
		} catch (NullPointerException e) {}
		}
		if (eCliente != null) return;

		
		// crear entidad Cliente
		eCliente = new Entidad();
		eCliente.setNombre("cliente");
		eCliente.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList( new Propiedad("nombre", cliente.getNombre()))));

		// registrar entidad cliente
		eCliente = servPersistencia.registrarEntidad(eCliente);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		cliente.setCodigo(eCliente.getId());
	}

	public void borrarCliente(Cliente cliente) {
		// No se comprueban restricciones de integridad con Venta
		Entidad eCliente = servPersistencia.recuperarEntidad(cliente.getCodigo());

		servPersistencia.borrarEntidad(eCliente);
	}

	public void modificarCliente(Cliente cliente) {

		Entidad eCliente = servPersistencia.recuperarEntidad(cliente.getCodigo());

		for (Propiedad prop : eCliente.getPropiedades()) {
			
			if (prop.getNombre().equals("nombre")) {
				prop.setValor(cliente.getNombre());
			} 
			servPersistencia.modificarPropiedad(prop);
		}

	}

	public Cliente recuperarCliente(int codigo) {

		// si no, la recupera de la base de datos
		Entidad eCliente;
		String nombre;

		// recuperar entidad
		eCliente = servPersistencia.recuperarEntidad(codigo);
		nombre = servPersistencia.recuperarPropiedadEntidad(eCliente, "nombre");

		Cliente cliente = new Cliente(nombre);
		cliente.setCodigo(codigo);
		return cliente;
	}

	public List<Cliente> recuperarTodosClientes() {

		List<Entidad> eClientes = servPersistencia.recuperarEntidades("cliente");
		List<Cliente> clientes = new LinkedList<Cliente>();

		for (Entidad eCliente : eClientes) {
			clientes.add(recuperarCliente(eCliente.getId()));
		}
		return clientes;
	}


}
