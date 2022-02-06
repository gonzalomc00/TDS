package tds.umu;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import tds.umu.modelo.Usuario;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;
import tds.umu.persistencia.TDSFactoriaDAO;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	private TDSFactoriaDAO factoria = new TDSFactoriaDAO();
	private IAdaptadorUsuarioDAO idao;
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
    	idao = factoria.getUsuarioDAO();
    	Usuario a= new Usuario("trini");
    	idao.registrarUsuario(a);
    	List<Usuario> clientes = idao.recuperarTodosUsuarios();
    	for (Usuario cliente : clientes) {
			System.out.println("mi nombre es " + cliente.getNombre() + "\n");
		}
    	assertTrue(clientes.size()!=0);
    	
    }
}
