package tds.umu.modelo;

import java.util.Objects;

/*La clase Etiqueta se caracteriza por tener un nombre y un código. Estas se usan para
 * clasificar a los vídeos en diferentes temas.
 * */
public class Etiqueta {
	/*Nombre de la etiqueta*/
	private String nombre;
	/*Código de la etiqueta*/
	private int codigo;

	public Etiqueta(String nombre)
	{
		this.nombre=nombre;
	}
	/*Métodos de get/set para las variables*/
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	/*Métodos para saber si dos objetos son iguales*/
	@Override
	public int hashCode() {
		return Objects.hash(codigo,nombre);
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj == null) 
			return false;
		if (obj.getClass() != this.getClass()) 
			return false;
		
		Etiqueta otro= (Etiqueta) obj;
		return codigo==otro.getCodigo() &&
				nombre.equals(otro.getNombre());
	}
	
	
	
}
