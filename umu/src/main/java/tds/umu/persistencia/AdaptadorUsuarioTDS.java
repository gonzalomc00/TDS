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

public class AdaptadorUsuarioTDS implements IAdaptadorUsuarioDAO {
	private static ServicioPersistencia servPersistencia;
	private static AdaptadorUsuarioTDS unicaInstancia = null;

	/* Aplicación del patrón Singleton */
	public static AdaptadorUsuarioTDS getUnicaInstancia() {
		if (unicaInstancia == null)
			unicaInstancia = new AdaptadorUsuarioTDS();

		return unicaInstancia;
	}

	private AdaptadorUsuarioTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	/* Registramos un usuario en la base de datos y le damos un código único. */
	public void registrarUsuario(Usuario usuario) {
		Entidad eUsuario = null;

		// Si la entidad ya esta registrada no la registra de nuevo
		if (usuario.getCodigo() != null) {
			try {
				eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
			} catch (NullPointerException e) {
			}
		}
		if (eUsuario != null)
			return;

		// Creamos una entidad Usuario
		eUsuario = new Entidad();
		eUsuario.setNombre("usuario");

		DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("dd-MMM-yy");
		String fecha = usuario.getFecha().format(formattedDate);

		// Añadimos las propiedades del Usuario.
		eUsuario.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", usuario.getNombre()),
				new Propiedad("apellidos", usuario.getApellidos()), new Propiedad("fechaNacimiento", fecha),
				new Propiedad("email", usuario.getEmail()), new Propiedad("usuario", usuario.getUsuario()),
				new Propiedad("contraseña", usuario.getContraseña()),
				new Propiedad("listasDeVideo", obtenerCodigosListasVideos(usuario.getListas())),
				new Propiedad("isPremium", String.valueOf(usuario.isPremium())),
				new Propiedad("recientes", obtenerCodigosRecientes(usuario.getRecientes())))));

		// Registramos la entidad en la base de datos y le asignamos un código único.
		eUsuario = servPersistencia.registrarEntidad(eUsuario);
		usuario.setCodigo(eUsuario.getId());
	}

	/*
	 * Al borrar una entidad Usuario de la base de datos también deberemos borrar
	 * todas sus listas de vídeo.
	 */
	public void borrarUsuario(Usuario usuario) {
		AdaptadorListaVideosTDS adaptadorListaVideos = AdaptadorListaVideosTDS.getUnicaInstancia();
		for (ListaVideos lv : usuario.getListas()) {
			adaptadorListaVideos.borrarListaVideos(lv);
		}
		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		servPersistencia.borrarEntidad(eUsuario);
	}

	/* Modificamos a un usuario en la base de datos */
	public void modificarUsuario(Usuario usuario) {

		Entidad eUsuario = servPersistencia.recuperarEntidad(usuario.getCodigo());
		DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("dd-MMM-yy");

		for (Propiedad prop : eUsuario.getPropiedades()) {
			switch (prop.getNombre()) {
			case ("nombre"):
				prop.setValor(usuario.getNombre());
				break;

			case ("apellidos"):
				prop.setValor(usuario.getApellidos());
				break;

			case ("fechaNacimiento"):
				prop.setValor(usuario.getFecha().format(formattedDate));
				break;

			case ("email"):
				prop.setValor(usuario.getEmail());
				break;

			case ("usuario"):
				prop.setValor(usuario.getUsuario());
				break;

			case ("contraseña"):
				prop.setValor(usuario.getContraseña());
				break;

			case ("isPremium"):
				prop.setValor(String.valueOf(usuario.isPremium()));
				break;
			case ("listasDeVideo"):
				String lineas = obtenerCodigosListasVideos(usuario.getListas());
				prop.setValor(String.valueOf(lineas));
				break;
			case ("recientes"):
				String lineas_recientes = obtenerCodigosRecientes(usuario.getRecientes());
				prop.setValor(String.valueOf(lineas_recientes));

			}

			servPersistencia.modificarPropiedad(prop);
		}

	}

	/*
	 * Recuperamos la entidad de la base de datos utilizando su código. Construimos
	 * el objeto Usuario desde la base de datos
	 */
	public Usuario recuperarUsuario(int codigo) {
		Entidad eUsuario = null;
		String nombre;
		String apellidos;
		String fecha;
		LocalDate fechaFormateada;
		String email;
		String nombreUsuario;
		String contraseña;
		String isPremium;
		List<ListaVideos> lv;
		List<Video> recientes;

		try {
			eUsuario = servPersistencia.recuperarEntidad(codigo);
		} catch (NullPointerException e) {
		}
		if (eUsuario == null)
			return null;

		nombre = servPersistencia.recuperarPropiedadEntidad(eUsuario, "nombre");
		apellidos = servPersistencia.recuperarPropiedadEntidad(eUsuario, "apellidos");
		fecha = servPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento");
		email = servPersistencia.recuperarPropiedadEntidad(eUsuario, "email");
		nombreUsuario = servPersistencia.recuperarPropiedadEntidad(eUsuario, "usuario");
		contraseña = servPersistencia.recuperarPropiedadEntidad(eUsuario, "contraseña");
		isPremium = servPersistencia.recuperarPropiedadEntidad(eUsuario, "isPremium");
		lv = obtenerListaVideosDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "listasDeVideo"));
		recientes = obtenerRecientesDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eUsuario, "recientes"));

		DateTimeFormatter formattedDate = DateTimeFormatter.ofPattern("dd-MMM-yy");
		fechaFormateada = LocalDate.parse(fecha, formattedDate);

		Usuario usuario = new Usuario(nombre, apellidos, fechaFormateada, email, nombreUsuario, contraseña);

		for (ListaVideos lista : lv) {
			usuario.añadirLista(lista);
		}
		if (isPremium.equals("false")) {
			usuario.setPremium(false);
		} else {
			usuario.setPremium(true);
		}

		usuario.setRecientes((LinkedList<Video>) recientes);
		usuario.setCodigo(codigo);
		return usuario;
	}

	/* Recuperar todos los usuarios de la base de datos */
	public List<Usuario> recuperarTodosUsuarios() {

		List<Entidad> eUsuarios = servPersistencia.recuperarEntidades("usuario");
		List<Usuario> usuarios = new LinkedList<Usuario>();

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}

	/*-------------------FUNCIONES AUXILIARES------------------*/

	/* Obtenemos los codigos de todas las listas de códigos */
	private String obtenerCodigosListasVideos(List<ListaVideos> lvideos) {
		String lineas = "";
		for (ListaVideos lv : lvideos)
			lineas += lv.getCodigo() + " ";
		return lineas.trim();
	}

	/* Obtenemos las listas de videos teniendo sus códigos. */
	private List<ListaVideos> obtenerListaVideosDesdeCodigos(String lineas) {
		List<ListaVideos> listasvideos = new LinkedList<ListaVideos>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorListaVideosTDS adaptadorListaVideo = AdaptadorListaVideosTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			listasvideos.add(adaptadorListaVideo.recuperarListaVideos(Integer.valueOf((String) strTok.nextElement())));
		}

		return listasvideos;
	}

	/* Obtenemos los codigos de los videos recientes */
	private String obtenerCodigosRecientes(List<Video> recientes) {
		String lineas = "";
		for (Video v : recientes)
			lineas += v.getCodigo() + " ";
		return lineas.trim();
	}

	/* Obtenemos los videos recientes a partir de sus codigos */
	private List<Video> obtenerRecientesDesdeCodigos(String lineas) {
		List<Video> recientes = new LinkedList<Video>();
		StringTokenizer strTok = new StringTokenizer(lineas, " ");
		AdaptadorVideoTDS adaptadorVideo = AdaptadorVideoTDS.getUnicaInstancia();
		while (strTok.hasMoreTokens()) {
			recientes.add(adaptadorVideo.recuperarVideo(Integer.valueOf((String) strTok.nextElement())));
		}

		return recientes;
	}

}
