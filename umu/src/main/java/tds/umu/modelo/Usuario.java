package tds.umu.modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class Usuario {
private Integer codigo;
private String nombre;
private String apellidos;
private LocalDate fecha;
private String email;
private String usuario;
private String contraseña;
private boolean isPremium;
private List<ListaVideos> listas;
private LinkedList<Video> recientes;
private FiltroVideo filtro;

//funcionalidad

public Usuario(String nombre, String apellidos, LocalDate fecha, String email, String usuario, String contraseña){
	this.nombre = nombre;
	this.apellidos= apellidos;
	this.fecha=fecha;
	this.email=email;
	this.usuario=usuario;
	this.contraseña= contraseña;
	this.isPremium=false;
	this.listas=new LinkedList<ListaVideos>();
	this.recientes= new LinkedList<Video>();
	this.setFiltro(FactoriaFiltros.getUnicaInstancia().getFiltro("NOFILTRO"));
	

}


public boolean login(String contraseña) {
	return this.contraseña.equals(contraseña);
}

//TODO faltaría meterle todo lo relacionado con las listas.


//getters/setters

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

public ListaVideos crearLista(String text) {
	ListaVideos lv= new ListaVideos(text);
	listas.add(lv);
	return lv;
}

public void añadirLista(ListaVideos lv) {
	this.listas.add(lv);
}

public void eliminarLista(ListaVideos lv) {
	this.listas.remove(lv);
}


//Desde Java 7 se puede empezar a usar este metodo
@Override
public int hashCode() {
	return Objects.hash(codigo,nombre,apellidos,fecha,email,usuario,contraseña,isPremium,listas);
}

@Override
public boolean equals(Object obj)
{
	if(obj == null) 
		return false;
	if (obj.getClass() != this.getClass()) 
		return false;
	
	Usuario otro= (Usuario) obj;
	return	codigo==otro.getCodigo() &&
			nombre.equals(otro.getNombre()) &&
			apellidos.equals(otro.getApellidos())&&
		    fecha.isEqual(otro.getFecha()) &&
		    email.equals(otro.getEmail()) &&
		    usuario.equals(otro.getUsuario()) &&
		    contraseña.equals(otro.getContraseña()) &&
		    isPremium==otro.isPremium() &&
		    listas.equals(otro.listas);
	
}


public ListaVideos getLista(String lista) {
	for(ListaVideos lv: listas) {
		if(lv.getNombre().equals(lista))
			return lv;
	}
	return null;
}


public List<Video> getRecientes() {
	return recientes;
}

public void setRecientes(LinkedList<Video> v) {
	recientes=v;
}
public void añadirReciente(Video v) {
	//Si el video que vamos a ver ahora coincide con el último que hemos visto, no se repite en la lista de recientes.
	if(!v.equals(recientes.getFirst())) {
	 recientes.addFirst(v);
	 if(recientes.size()>Constantes.MAX_RECIENTES)
		 recientes.removeLast();
	}
	
}

//COMPROBAR 
public boolean tieneVideo(Video v) {
	
	return listas.stream()
			.flatMap(l-> l.getVideos().stream())
			.anyMatch(vid-> vid.equals(v));
}


public FiltroVideo getFiltro() {
	return filtro;
}


public void setFiltro(FiltroVideo filtro) {
	this.filtro = filtro;
}




}
