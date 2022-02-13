package tds.umu.modelo;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;

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

//funcionalidad

public Usuario(String nombre, String apellidos, LocalDate fecha, String email, String usuario, String contraseña){
	this.nombre = nombre;
	this.apellidos= apellidos;
	this.fecha=fecha;
	this.email=email;
	this.usuario=usuario;
	this.contraseña= contraseña;
	this.isPremium=false;
	this.setListas(new LinkedList<ListaVideos>());
	
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

public void añadirLista(ListaVideos lv) {
	this.listas.add(lv);
}

public void eliminarLista(ListaVideos lv) {
	this.listas.remove(lv);
}





}
