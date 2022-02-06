package tds.umu.persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import tds.umu.modelo.Etiqueta;
import tds.umu.modelo.Video;

public class AdaptadorVideoTDS  implements IAdaptadorVideoDAO {

	private static ServicioPersistencia servPersistencia;
	private static AdaptadorVideoTDS unicaInstancia = null;
	
	public static AdaptadorVideoTDS getUnicaInstancia() { // patron singleton
		if (unicaInstancia == null)
			unicaInstancia=new AdaptadorVideoTDS();
	
		return unicaInstancia;
	}

	private AdaptadorVideoTDS() {
		servPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	
	
	@Override
	public void registrarVideo(Video video) {
		Entidad eVideo=null;
		try {
			eVideo=servPersistencia.recuperarEntidad(video.getCodigo());
		} catch(NullPointerException e) {}
		if(eVideo!=null) return;
		
	AdaptadorEtiquetaTDS adaptadorETQ= AdaptadorEtiquetaTDS.getUnicaInstancia();
	for (Etiqueta et : video.getEtiquetas()) {
		adaptadorETQ.registrarEtiqueta(et);
	}
		
	eVideo= new Entidad();
	eVideo.setNombre("video");
	eVideo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("url",video.getUrl()), 
								new Propiedad("titulo",video.getTitulo()),
								new Propiedad("numReproducciones",String.valueOf(video.getNumReproducciones())),
								new Propiedad("etiquetas",obtenerCodigosEtiquetas(video.getEtiquetas())))));
	
	
	eVideo= servPersistencia.registrarEntidad(eVideo);
	video.setCodigo(eVideo.getId());
	
	}

	@Override
	public void borrarVideo(Video video) {
		//PROBABLEMENTE ESTÉ MAL, SUPER REVISAR
		//Mirar el tema de las etiquetas
		Entidad eVideo;
		eVideo= servPersistencia.recuperarEntidad(video.getCodigo());
		servPersistencia.borrarEntidad(eVideo);
	}

	@Override
	public void modificarVideo(Video video) {
		Entidad eVideo= servPersistencia.recuperarEntidad(video.getCodigo());
		
		for(Propiedad prop: eVideo.getPropiedades()) {
			if(prop.getNombre().equals("url")) {
				prop.setValor(String.valueOf(video.getUrl()));
			}
			else if(prop.getNombre().equals("titulo")) {
				prop.setValor(String.valueOf(video.getTitulo()));
			}
			else if(prop.getNombre().equals("numReproducciones")) {
				prop.setValor(String.valueOf(video.getNumReproducciones()));
			}
			else if(prop.getNombre().equals("codigo")) {
				prop.setValor(String.valueOf(video.getCodigo()));
			}
			else if(prop.getNombre().equals("etiquetas")) {
				String lineas = obtenerCodigosEtiquetas(video.getEtiquetas());
				prop.setValor(lineas);
			}
			
			servPersistencia.modificarPropiedad(prop);	
		}
		
	}

	@Override
	public Video recuperarVideo(int codigo) {
		
		Entidad eVideo=null;
		String url;
		String titulo;
		int numReproducciones;
		List<Etiqueta> etiquetas;
		
		try {
			eVideo=servPersistencia.recuperarEntidad(codigo);
		} catch(NullPointerException e) {}
		
		if(eVideo == null) return null;
		
		url= servPersistencia.recuperarPropiedadEntidad(eVideo,"url");
		titulo=servPersistencia.recuperarPropiedadEntidad(eVideo, "titulo");
		numReproducciones=Integer.parseInt(servPersistencia.recuperarPropiedadEntidad(eVideo, "numReproducciones"));
		etiquetas= obtenerEtiquetasDesdeCodigos(servPersistencia.recuperarPropiedadEntidad(eVideo, "etiquetas"));
		
		
		Video video= new Video(url,titulo);
		video.setNumReproducciones(numReproducciones);
		video.setCodigo(codigo);
		
		for(Etiqueta etq: etiquetas)
			video.addEtiqueta(etq);
		
		
		return video;
	}

	@Override
	public List<Video> recuperarTodosVideos() {
		
		List<Entidad> eVideos= servPersistencia.recuperarEntidades("video");
		List<Video> videos= new LinkedList<Video>();
		
		for(Entidad eVideo: eVideos) 
			videos.add(recuperarVideo(eVideo.getId()));		
		
		
		return videos;
	}
	
	private String obtenerCodigosEtiquetas(List<Etiqueta> etiquetas) {
		String lineas="";
		for(Etiqueta etq: etiquetas)
				lineas+= etq.getCodigo();
		return lineas.trim();
		
	}
	
	private List<Etiqueta> obtenerEtiquetasDesdeCodigos(String lineas){
		List<Etiqueta> etiquetas= new LinkedList<Etiqueta>();
		StringTokenizer strTok= new StringTokenizer(lineas," ");
		AdaptadorEtiquetaTDS adaptadorETQ= AdaptadorEtiquetaTDS.getUnicaInstancia();
		while(strTok.hasMoreTokens()) {
			etiquetas.add(adaptadorETQ.recuperarEtiqueta(Integer.valueOf((String)strTok.nextElement())));
		}
		
		
		return etiquetas;
	}

}
