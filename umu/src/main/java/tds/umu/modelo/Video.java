package tds.umu.modelo; 
 
import java.util.LinkedList; 
import java.util.List; 
import java.util.Objects; 
 
import javax.swing.DefaultListModel; 
import javax.swing.JList; 
 
public class Video { 
	private String url; 
	private String titulo; 
	private int numReproducciones; 
	private List<Etiqueta> etiquetas; 
	private int codigo; 
	 
 
	private DefaultListModel<String> modelo= new DefaultListModel<String>();; 
	private JList<String> listaetiquetas; 
	 
	 
	public Video(String url, String titulo,List<Etiqueta> etiquetas) { 
		this.url=url; 
		this.titulo=titulo; 
		this.etiquetas= etiquetas; 
		this.numReproducciones=0; 
	} 
	 
	public String getUrl() { 
		return url; 
	} 
	public void setUrl(String url) { 
		this.url = url; 
	} 
	public String getTitulo() { 
		return titulo; 
	} 
	public void setTitulo(String titulo) { 
		this.titulo = titulo; 
	} 
	public int getNumReproducciones() { 
		return numReproducciones; 
	} 
	public void setNumReproducciones(int numReproducciones) { 
		this.numReproducciones = numReproducciones; 
	} 
	 
	public void aumentarReproduccion() { 
		this.numReproducciones++; 
	} 
	public List<Etiqueta> getEtiquetas() { 
		return etiquetas; 
	} 
	public void setEtiquetas(List<Etiqueta> etiquetas) { 
		this.etiquetas = etiquetas; 
	} 
	public int getCodigo() { 
		return codigo; 
	} 
	public void setCodigo(int codigo) { 
		this.codigo = codigo; 
	} 
 
	public void addEtiqueta(Etiqueta etq) { 
		etiquetas.add(etq); 
		 
	} 
	 
	@Override 
	public int hashCode() { 
		return Objects.hash(codigo,url,titulo,etiquetas,numReproducciones); 
	} 
 
	@Override 
	public boolean equals(Object obj) 
	{ 
		if(obj == null)  
			return false; 
		if (obj.getClass() != this.getClass())  
			return false; 
		 
		Video otro= (Video) obj; 
		return 	codigo==otro.getCodigo() && 
				url.equals(otro.getUrl()) && 
				titulo.equals(otro.getTitulo())&& 
				etiquetas.equals(otro.getEtiquetas()) && 
				numReproducciones==otro.getNumReproducciones(); 
		 
	} 
 
	public boolean containsEtiqueta(List<Etiqueta> etiquetas) { 
		 
		for(Etiqueta et: etiquetas) { 
			if(this.etiquetas.contains(et)) 
				return true; 
		} 
		 
		return false; 
	} 
 
	public JList<String> obtenerEtiquetas() { 
		listaetiquetas= new JList<String>(); 
	    listaetiquetas.setModel(modelo); 
	    
		for (Etiqueta e: etiquetas) { 
			modelo.addElement(e.toString()); 
		} 
		return listaetiquetas; 
	} 
	 
} 
