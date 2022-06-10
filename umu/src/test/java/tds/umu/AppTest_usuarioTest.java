package tds.umu;


import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.*;

import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.FiltroLargos;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Usuario;
import tds.umu.modelo.Video;


import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

import tds.umu.modelo.Usuario;

import tds.umu.modelo.Usuario;

public class AppTest_usuarioTest {

		private  Usuario u1,u2,u3;
		private  Video v1,v2;
		
	
	  
		@BeforeEach
		public void beforeClass() {
		u1=new Usuario("Gonzalo","Manzanares Carmona",LocalDate.of(2000, 05, 07),"gonzalo.manzanaresc@um.es","gonzalo","prueba1");
		u2= new Usuario("Trinidad","Quinto Ibáñez",LocalDate.of(2000,02,15),"trinidad.quintoi@um.es","trini","prueba2");
		u3= new Usuario("Gonzalo","Manzanares Carmona",LocalDate.of(2000, 05, 07),"gonzalo.manzanaresc@um.es","gonzalo","prueba1");
		v1= new Video("v1","url1");
		v2= new Video("v2","url2");
		}
		
		
	  	@Test
	  	 public void testUser1 () {
	  		 	assertEquals(u1.getNombre(),"Gonzalo");
	  		 	assertEquals(u1.getApellidos(),"Manzanares Carmona");
	  		 	assertEquals(u1.getFecha(),LocalDate.of(2000, 05, 07));
	  		 	assertEquals(u1.getEmail(),"gonzalo.manzanaresc@um.es");
	  		 	assertEquals(u1.getUsuario(),"gonzalo");
	  		 	assertEquals(u1.getContraseña(),"prueba1");
	  	 }
	  	
	
	  	public void testEqualsTrue() {
	  		assertTrue(u1.equals(u3),"Resultado prueba 2:");
	  	}
	  	
	  	@Test 
	  	public void testEqualsFalse() {
	  		assertFalse(u1.equals(u2),"Resultado prueba 3");
	  	}
	  	
	  	@Test 
	  	public void testRecientes() {
	  		//Lista con el resultado esperado
	  		List<Video> recientesPrueba= new LinkedList<Video>();
	  		recientesPrueba.add(v2);
	  		recientesPrueba.add(v1);
	  		
	  		//Tratamiento con el usuario
	  		u1.añadirReciente(v1);
	  		u1.añadirReciente(v2);
	  		
	  		assertTrue(recientesPrueba.equals(u1.getRecientes()),"Resultado prueba 4");
	  	}
	  	
	  	@Test
	  	public void testNuevaLista() {
	  		//Lista con el resultado esperado
	  		ListaVideos listaVideo= new ListaVideos("prueba");
	  		listaVideo.añadirVideo(v1);
	  		
	  		
	  		ListaVideos lv= u1.crearLista("prueba");
	  		lv.añadirVideo(v1);
	  		
	  		assertEquals(true,listaVideo.equals(u1.getLista("prueba")),"Resultado prueba 5");
	  		
	  	}
	  	
	  	@Test
	  	public void testCheckPass() {
	  		assertTrue(u1.checkLogin("prueba1"),"Resultado prueba 6");
	  	}
	  	
	  	@Test
	  	public void testEliminarLista() {
	  		ListaVideos lv=u1.crearLista("prueba1");
	  		u1.eliminarLista(lv);
	  		assertEquals(0,u1.getListas().size(),"Resultado prueba 7");
	  	}
	  	
	  	@Test
	  	public void testTieneVideo() {
	  		ListaVideos lv=u1.crearLista("prueba1");
	  		lv.añadirVideo(v1);
	  		assertTrue(u1.tieneVideo(v1),"Resultado prueba 8");
	  		assertFalse(u1.tieneVideo(v2),"Resultado prueba 8.2");
	  		
	  	}
	 
	  	@Test 
	  	public void testPremium() {
	  		assertFalse(u1.isPremium(),"Primer resultado prueba 9");
	  		u1.setPremium(true);
	  		assertTrue(u1.isPremium(),"Segundo resultado prueba 9");
	  	}
	  	
	  	
	  	
}