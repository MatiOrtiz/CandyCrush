package Logica;

import javax.swing.ImageIcon;

import Entidades.Entidad;
import Grafica.EntidadGrafica;

public class ConcreteFactoryMinimalista extends AbstractFactory {
	public ConcreteFactoryMinimalista() {
	}
	
    public EntidadGrafica crearGrafica(Entidad e) {
        return new EntidadGrafica(e,"Minimalista");
    }

	public ImageIcon getImagen(String s) {
		return new ImageIcon(this.getClass().getResource("/ImagenesMinimalista/"+s+".png"));
	}
    
}
