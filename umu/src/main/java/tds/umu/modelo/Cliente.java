package tds.umu.modelo;

public class Cliente {
private Integer codigo;
private String nombre;



public Cliente(String nombre){
	this.nombre = nombre;
}



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


}
