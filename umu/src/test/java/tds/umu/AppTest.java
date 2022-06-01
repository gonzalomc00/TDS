package tds.umu;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import tds.umu.modelo.Usuario;
import tds.umu.persistencia.IAdaptadorEtiquetaDAO;
import tds.umu.persistencia.IAdaptadorListaVideosDAO;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;
import tds.umu.modelo.CatalogoEtiquetas;
import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.ListaVideos;
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
	private IAdaptadorListaVideosDAO lvdao;
	private IAdaptadorEtiquetaDAO edao;
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    /*
    @Test
   /public void persistencia()
    {
    	idao = factoria.getUsuarioDAO();
    
    	idao.registrarUsuario(a);
    	List<Usuario> clientes = idao.recuperarTodosUsuarios();
    	for (Usuario cliente : clientes) {
			System.out.println("mi nombre es " + cliente.getNombre() + "\n");
		}
    	assertTrue(clientes.size()!=0);
    	
    }
    */
    
    /*
     @Test
    public void persistenciaVideo()
    {
    	vdao=factoria.getVideoDAO();
    	Video v= new Video("aaaaa","prueba");
    	v.addEtiqueta(new Etiqueta("pruebaETQ"));
    	vdao.registrarVideo(v);
    	List<Video> videos= vdao.recuperarTodosVideos();
    	//for(Video video: videos) {
    		//System.out.println("url:"+video.getUrl()+" nombre:"+video.getTitulo()+" numRepro:"+video.getNumReproducciones());
    	//}
    	assertTrue(videos.size()!=0);
    }
    
    */
    /*
    @Test
    public void persistenciaListaVideos()
    {
    	lvdao=factoria.getListaVideosDAO();
    	Video v1= new Video("video1","prueba1");
    	Video v2= new Video("video2","prueba2");
    	
    	ListaVideos lprueba= new ListaVideos("ListaPrueba");
    	lprueba.añadirVideo(v1);
    	lprueba.añadirVideo(v2);
    	lvdao.registrarListaVideos(lprueba);
    	List<ListaVideos> listas= lvdao.recuperarTodosListasVideos();
    	
    	for(ListaVideos lv: listas) {
    		System.out.println("nombre:"+lv.getNombre());
    		for(Video video: lv.getVideos()) {
    			System.out.println("nombre de video: "+video.getUrl());
    		}
    	}
    	
    	assertTrue(listas.size()!=0);
    	
    }
    */
   
    public void pruebaEtqs() {
    	edao= factoria.getEtiquetaDAO();
    	Etiqueta e1= new Etiqueta("musica");
    	Etiqueta e2= new Etiqueta("documental");
    	edao.registrarEtiqueta(e1);
    	edao.registrarEtiqueta(e2);
    	CatalogoEtiquetas.getUnicaInstancia().addEtiqueta(e1);
    	CatalogoEtiquetas.getUnicaInstancia().addEtiqueta(e2);
  
    	for(Etiqueta etq: edao.recuperarTodosEtiquetas()) {
    		System.out.println("Nombre etq: "+etq.getNombre());
    	}
    	
    	assertTrue(true);
    	
    }
    
    @Test
    public void pruebaEquals() {
    	
    	Usuario u1 = new Usuario("a","a", LocalDate.now(),"a" ,"a", "a");
    	Usuario u2 = new Usuario("b","a", LocalDate.now(),"a" ,"a", "a");
    
    	assertFalse(u1.equals(u2));
    }
    
    
    
    
}
