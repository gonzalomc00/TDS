package tds.umu;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.Test;

import tds.umu.modelo.Usuario;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;
import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.Video;
import tds.umu.persistencia.IAdaptadorVideoDAO;
import tds.umu.persistencia.TDSFactoriaDAO;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
	private TDSFactoriaDAO factoria = new TDSFactoriaDAO();
	private IAdaptadorUsuarioDAO idao;
	private IAdaptadorVideoDAO vdao;
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
    	//Usuario a= new Usuario("trini");
    	//idao.registrarUsuario(a);
    	List<Usuario> clientes = idao.recuperarTodosUsuarios();
    	for (Usuario cliente : clientes) {
			System.out.println("mi nombre es " + cliente.getNombre() + "\n");
		}
    	assertTrue(clientes.size()!=0);
    	
    }
    @Test
    public void persistenciaVideo()
    {
    	vdao=factoria.getVideoDAO();
    	Video v= new Video("aaaaa","prueba");
    	v.addEtiqueta(new Etiqueta("pruebaETQ"));
    	vdao.registrarVideo(v);
    	List<Video> videos= vdao.recuperarTodosVideos();
    	for(Video video: videos) {
    		System.out.println("url:"+video.getUrl()+" nombre:"+video.getTitulo()+" numRepro:"+video.getNumReproducciones());
    	}
    	assertTrue(videos.size()!=0);
    }
}
