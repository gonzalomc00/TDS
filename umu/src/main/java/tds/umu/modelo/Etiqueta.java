package tds.umu.modelo;

import java.util.Objects;

public class Etiqueta {
	private String nombre;
	private int codigo;

	public Etiqueta(String nombre)
	{
		this.nombre=nombre;
	}
	
	
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
