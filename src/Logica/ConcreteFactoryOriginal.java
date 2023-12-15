package Logica;

import javax.swing.ImageIcon;

import Entidades.Entidad;
import Grafica.EntidadGrafica;

public class ConcreteFactoryOriginal extends AbstractFactory {
	public ConcreteFactoryOriginal() {
	}
	
	
    public EntidadGrafica crearGrafica(Entidad e) {
    	return new EntidadGrafica(e,"Original");
    }


	public ImageIcon getImagen(String s) {
		return new ImageIcon(this.getClass().getResource("/ImagenesOriginal/"+s+".png"));
	}

}