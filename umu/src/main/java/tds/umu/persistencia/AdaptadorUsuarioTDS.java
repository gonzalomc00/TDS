package tds.umu.persistencia;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import tds.umu.modelo.ListaVideos;
import tds.umu.modelo.Usuario;
import tds.umu.modelo.Video;
import beans.Entidad;
import beans.Propiedad;




//Usa un pool para evitar problemas doble referencia con ventas
public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;

	public static AdaptadorUsuarioTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			unicaInstancia=new AdaptadorUsuarioTDS();
	
		return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* cuando se registra un usuario se le asigna un identificador �nico */
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario = null;
		
		if(usuario.getCodigo()!=null) {
		// Si la entidad esta registrada no la registra de nuevo
		try {
			eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		} catch (NullPointerException e) {}
		}
		if (eUsuario != null) return;

		
		// crear entidad Usuario
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");
		
		
		AdaptadorListaVideosTDS adaptadorListaVideos= AdaptadorListaVideosTDS.getUnicaInstancia();
		for(ListaVideos lv: usuario.getListas()) {
			adaptadorListaVideos.registrarListaVideos(lv);
		}
		
		DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("dd-MMM-yy");
		String fecha= usuario.getFecha().format(formattedDate);
		
		
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList( new Propiedad("nombre", usuario.getNombre()),
				//añado las nuevas propiedades
				//TODO supongo que si tiene listas de videos habrá que añadir algún campo a la BBDD para almacenarlos.
						new Propiedad("apellidos", usuario.getApellidos()),
						new Propiedad("fechaNacimiento", fecha),
						new Propiedad("email", usuario.getEmail()),
						new Propiedad("usuario", usuario.getUsuario()),
						new Propiedad("contraseña", usuario.getContraseña()),
					    new Propiedad("listasDeVideo", obtenerCodigosListasVideos(usuario.getListas())),
						new Propiedad("isPremium", String.valueOf(usuario.isPremium()))
						)));

		
		// registrar entidad usuario
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		usuario.setCodigo(eUsuario.getId());
	}

	public void borrarUsuario(Usuario usuario) {
		// Habrá que borrar tambien todas las playlists que tenga este usuario
		AdaptadorListaVideosTDS adaptadorListaVideos= AdaptadorListaVideosTDS.getUnicaInstancia();
		for(ListaVideos lv: usuario.getListas()) {
			adaptadorListaVideos.borrarListaVideos(lv);
		}
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		servPersistencia.borrarEntidad(eUsuario);
	}

	public void modificarUsuario(Usuario usuario) {

		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		//como en el arraylist de propiedades almacenamos strings, tengo que convertir lo del premium a un string
		//tengo que pasar lafecha a string también que la tengo en forma de LocalDate
		//he seguido los pasos de https://howtodoinjava.com/java/date-time/localdate-format-example/
		DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("dd-MMM-yy");
	
		for (Propiedad prop : eUsuario.getPropiedades()) {
			//TODO faltará uno para modificar las playlists de videos
			switch(prop.getNombre()) {
			case("nombre"):
				prop.setValor(usuario.getNombre());
				break;
				
			case("apellidos"):
				prop.setValor(usuario.getApellidos());
				break;
			
			case("fechaNacimiento"):
				prop.setValor(usuario.getFecha().format(formattedDate));
				break;
				
			case("email"):
				prop.setValor(usuario.getEmail());
				break;
				
			case("usuario"):	
				prop.setValor(usuario.getUsuario());
				break;
			
			case("contraseña"):	

				prop.setValor(usuario.getContraseña());
				break;
				
			case("isPremium"):
				prop.setValor(String.valueOf(usuario.isPremium()));
				break;
			case("listasDeVideo"):
				String lineas = obtenerCodigosListasVideos(usuario.getListas());
				prop.setValor(String.valueOf(lineas));
			
			}
			servPersistencia.modificarPropiedad(prop);
		}

	}

	public Usuario recuperarUsuario(int codigo) {

		// si no, la recupera de la base de datos
		//TODO same otra vez las lsitas de videos
		Entidad eUsuario=null;
		String nombre;
		String apellidos;
		String fecha;
		LocalDate fechaFormateada;
		String email;
		String nombreUsuario;
		String contraseña;
		String isPremium;
		List<ListaVideos> lv;
		
		try {
			eUsuario=servPersistencia.recuperarEntidad(codigo);
		} catch(NullPointerException e) {}
		if(eUsuario==null) return null;
		

		// recuperar entidad
		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, "apellidos");
		fecha = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		nombreUsuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		contraseña = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contraseña");
		isPremium = servPersistencia.recuperarPropiedadEntidad(eUsuario, "isPremium");
		lv= obtenerListaVideosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "listasDeVideo"));
		
		DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("dd-MMM-yy");
		fechaFormateada= LocalDate.parse(fecha,formattedDate);		
		
		Usuario usuario = new Usuario(nombre,apellidos, fechaFormateada,email,nombreUsuario,contraseña);

		for(ListaVideos lista: lv) {
			usuario.añadirLista(lista);
		}
		if(isPremium.equals("false")) {
			usuario.setPremium(false);
		}else {
			usuario.setPremium(true);
		}
		
		
		usuario.setCodigo(codigo);
		return usuario;
	}
	
	

	public List<Usuario> recuperarTodosUsuarios() {

		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}

	private String obtenerCodigosListasVideos(List<ListaVideos> lvideos) {
		String lineas="";
		for(ListaVideos lv: lvideos)
			lineas+= lv.getCodigo()+" ";
		return lineas.trim();
	}
	
	private List<ListaVideos> obtenerListaVideosDesdeCodigos(String lineas){
		List<ListaVideos> listasvideos= new LinkedList<ListaVideos>();
		StringTokenizer strTok= new StringTokenizer(lineas," ");
		AdaptadorListaVideosTDS adaptadorListaVideo=AdaptadorListaVideosTDS.getUnicaInstancia();
		while(strTok.hasMoreTokens()) {
			listasvideos.add(adaptadorListaVideo.recuperarListaVideos(Integer.valueOf((String)strTok.nextElement())));
		}
 		
		return listasvideos;
	}
	
}
