package tds.umu.modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

/*Clase que contiene la funcionalidad de un usuario de nuestra AppVideo.
 * Este se compone de unos atributos tales como sus datos, sus playlists, si es premium o no y
 * el filtro que aplica en tiempo de ejecución.*/
public class Usuario {
	/* Código único y asociado a un usuario */
	private Integer codigo;
	/* Nombre asociado al usuario */
	private String nombre;
	/* Apellidos asociados al usuario */
	private String apellidos;
	/* Fecha de nacimiento */
	private LocalDate fecha;
	/* Email asociado al usuario */
	private String email;
	/* Nombre de usuario */
	private String usuario;
	/* Contraseña del usuario */
	private String contraseña;
	/* Si tiene funcionalidad premium o no */
	private boolean isPremium;
	/* Playlists del usuario */
	private List<ListaVideos> listas;
	/* Vídeos más recientes reproducidos por el usuario */
	private LinkedList<Video> recientes;
	/* Filtro actual aplicado por el usuario */
	private FiltroVideo filtro;

	/* Constructor de la clase Usuario. Por defecto, un usuario tiene como filtro "NOFILTRO" */
	public Usuario(String nombre, String apellidos, LocalDate fecha, String email, String usuario, String contraseña) {
		this.nombre = nombre;
		this.apellidos = apellidos;
		this.fecha = fecha;
		this.email = email;
		this.usuario = usuario;
		this.contraseña = contraseña;
		this.isPremium = false;
		this.listas = new LinkedList<ListaVideos>();
		this.recientes = new LinkedList<Video>();
		this.setFiltro(FactoriaFiltros.getUnicaInstancia().getFiltro("NOFILTRO"));

	}

	/* Método para comprobar la contraseña del usuario*/
	public boolean checkLogin(String contraseña) {

		return this.contraseña.equals(contraseña);
	}

	/* Método para crear una playlist y asociarla con el usuario que la creó */
	public ListaVideos crearLista(String text) {
		ListaVideos lv = new ListaVideos(text);
		listas.add(lv);
		return lv;
	}

	/* Método para asociar una playlist ya creada con un usuario */
	public void añadirLista(ListaVideos lv) {
		this.listas.add(lv);
	}

	/* Método para eliminar una playlist del usuario */
	public void eliminarLista(ListaVideos lv) {
		this.listas.remove(lv);
	}

	/* Método para recuperar una lista del usuario mediante su nombre */
	public ListaVideos getLista(String lista) {
		for (ListaVideos lv : listas) {
			if (lv.getNombre().equals(lista))
				return lv;
		}
		return null;
	}

	/* Método para añadir al usuario un video en su lista de videos recientes. 
	 * Su funcionamiento es el siguiente: si un usuario visualiza su video más reciente más de una vez seguida, este no se volverá a introducir en la lista de recientes.
	 * Si un usuario ve sus videos recientes de manera intercalada, entonces si se añadirá de forma repetida. Sigue un funcionamiento similar al de páginas como Youtube. 
	 */
	public void añadirReciente(Video v) {

		//Comprobación inicial necesaria por si es el primer vídeo que ve un usuario. 
		if(recientes.size()!=0) {
		if (!v.equals(recientes.getFirst())) {
			recientes.addFirst(v);
			if (recientes.size() > Constantes.MAX_RECIENTES)
				recientes.removeLast();
		}
		} else {
			recientes.addFirst(v);
		}

	}

	/* Método para comprobar si una playlist tiene un vídeo */
	public boolean tieneVideo(Video v) {

		/*
		 * Usamos un stream que realiza un flatmap de todos los videos de un usuario. Entonces comprueba si alguno coincide con el que buscamos. 
		 */
		return listas.stream().flatMap(l -> l.getVideos().stream()).anyMatch(vid -> vid.equals(v));
	}

	// Métodos get/set de los diferentes atributos asociados a la clase Usuario
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getContraseña() {
		return contraseña;
	}

	public void setContraseña(String contraseña) {
		this.contraseña = contraseña;
	}

	public boolean isPremium() {
		return isPremium;
	}

	public void setPremium(boolean isPremium) {
		this.isPremium = isPremium;
	}

	public List<ListaVideos> getListas() {
		return listas;
	}

	public void setListas(List<ListaVideos> listas) {
		this.listas = listas;
	}

	/* Métodos para recuperar/establecer un filtro */
	public FiltroVideo getFiltro() {
		return filtro;
	}

	public void setFiltro(FiltroVideo filtro) {
		this.filtro = filtro;
	}

	/* Método para recuperar los vídeos recientes del usuario */
	public List<Video> getRecientes() {
		return recientes;
	}

	public void setRecientes(LinkedList<Video> v) {
		recientes = v;
	}

	/* Métodos para comprobar la igualdad de entidades */
	@Override
	public int hashCode() {
		return Objects.hash(codigo, nombre, apellidos, fecha, email, usuario, contraseña, isPremium, listas,recientes);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		if (obj.getClass() != this.getClass())
			return false;

		Usuario otro = (Usuario) obj;
		return codigo == otro.getCodigo() && nombre.equals(otro.getNombre()) && apellidos.equals(otro.getApellidos())
				&& fecha.isEqual(otro.getFecha()) && email.equals(otro.getEmail()) && usuario.equals(otro.getUsuario())
				&& contraseña.equals(otro.getContraseña()) && isPremium == otro.isPremium()
				&& listas.equals(otro.listas)
				&& recientes.equals(otro.recientes);

	}

}
