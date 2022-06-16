package tds.umu;

import static org.junit.Assert.assertTrue;
import java.time.LocalDate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Usuario;
import tds.umu.modelo.Video;
import tds.umu.persistencia.DAOException;
import tds.umu.persistencia.FactoriaDAO;
import tds.umu.persistencia.IAdaptadorEtiquetaDAO;
import tds.umu.persistencia.IAdaptadorListaVideosDAO;
import tds.umu.persistencia.IAdaptadorUsuarioDAO;
import tds.umu.persistencia.IAdaptadorVideoDAO;
import tds.umu.persistencia.TDSFactoriaDAO;

//FICHERO DE TESTS CREADOS PARA AUTOMATIZAR CIERTOS PROCESOS Y PROBAR FUNCIONES DE LA PERSISTENCIA, EJECUTAR CON LA BD RECIEN CREADA 
/*Pruebas para comprobar que toda la funcionalidad de la persistencia funcione correctamente*/
class AppTest_persistenciaTest {

	private FactoriaDAO factoria = new TDSFactoriaDAO();
	private IAdaptadorUsuarioDAO idao;
	private IAdaptadorVideoDAO vdao;
	private IAdaptadorListaVideosDAO lvdao;
	private IAdaptadorEtiquetaDAO edao;
	private Video v1, v2;

	@BeforeEach
	public void beforeClass() {
		try {
			factoria = FactoriaDAO.getInstancia(FactoriaDAO.DAO_TDS);
		} catch (DAOException e) {
			e.printStackTrace();
		}

		idao = factoria.getUsuarioDAO();
		vdao = factoria.getVideoDAO();
		lvdao = factoria.getListaVideosDAO();
		edao = factoria.getEtiquetaDAO();

		v1 = new Video("video1", "prueba1");
		v2 = new Video("video2", "prueba2");

	}

	@Test
	public void testPersistenciaUsuario() {

		Usuario u1 = new Usuario("a", "a", LocalDate.now(), "a", "a", "a");
		idao.registrarUsuario(u1);
		Usuario u2 = idao.recuperarUsuario(u1.getCodigo());

		assertTrue(u1.equals(u2));
	}

	@Test
	public void testPersistenciaVideo() {
		v1.addEtiqueta(new Etiqueta("pruebaETQ"));
		vdao.registrarVideo(v1);
		Video v3 = vdao.recuperarVideo(v1.getCodigo());
		assertTrue(v1.equals(v3));
	}

	@Test
	public void testPersistenciaListaVideo() {

		ListaVideos lprueba = new ListaVideos("ListaPrueba");
		lprueba.añadirVideo(v1);
		lprueba.añadirVideo(v2);
		lvdao.registrarListaVideos(lprueba);
		ListaVideos lprueba2 = lvdao.recuperarListaVideos(lprueba.getCodigo());

		assertTrue(lprueba.equals(lprueba2));
	}

	@Test
	public void testEtiquetas() {
		edao = factoria.getEtiquetaDAO();
		Etiqueta e1 = new Etiqueta("musica");
		Etiqueta e2 = new Etiqueta("documental");
		edao.registrarEtiqueta(e1);
		edao.registrarEtiqueta(e2);

		Etiqueta e3 = edao.recuperarEtiqueta(e1.getCodigo());
		Etiqueta e4 = edao.recuperarEtiqueta(e2.getCodigo());

		assertTrue(e1.equals(e3));
		assertTrue(e2.equals(e4));

	}

}
