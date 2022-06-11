package tds.umu.modelo;

import java.awt.Image;

import javax.swing.ImageIcon;


/* Clase que usaremos para representar los vídeos en la interfaz. Esta almacena el título de un vídeo, su imagen y el propio vídeo
 * 
 *  La utilizamos para tener toda esta información almacenada y, en el caso de la imagen, no tener que estar recurriendo contínuamente al reproductor para descargarla. 
 *  Esta es la clase que utilizará VideoRenderer para renderizar las listas que utilicemos. Por tanto, cada video que "rescatemos" de la base de datos construirá uno de estos objetos.  
 *  
 */

public class VideoRepresent {
	private String nombre;
	private ImageIcon imagen;
	private Video video;
	
	
	public VideoRepresent(Video v,ImageIcon imagen) {
		nombre=v.getTitulo();
		this.imagen= imagen;
		this.video=v;
	}
	
	

	public String getNombre() {
		return nombre;
	}
	public ImageIcon getImagen() {
		return imagen;
	}
	public Video getVideo() {
		return video;
	}
	
	
	
	
	
	
	
	
}

	

