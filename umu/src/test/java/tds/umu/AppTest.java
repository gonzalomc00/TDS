package tds.umu;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import tds.umu.modelo.Cliente;
import tds.umu.persistencia.IAdaptadorClienteDAO;
import tds.umu.persistencia.TDSFactoriaDAO;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	private TDSFactoriaDAO factoria = new TDSFactoriaDAO();
	private IAdaptadorClienteDAO idao;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void persistencia()
    {
    	idao = factoria.getClienteDAO();
    	Cliente a= new Cliente("trini");
    	idao.registrarCliente(a);
    	List<Cliente> clientes = idao.recuperarTodosClientes();
    	for (Cliente cliente : clientes) {
			System.out.println("mi nombre es " + cliente.getNombre() + "\n");
		}
    	assertTrue(clientes.size()!=0);
    	
    }
}
