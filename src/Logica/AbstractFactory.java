package Logica;

import javax.swing.ImageIcon;

import Entidades.Entidad;
import Grafica.EntidadGrafica;

public abstract class AbstractFactory {

    public abstract EntidadGrafica crearGrafica(Entidad e);

	public abstract ImageIcon getImagen(String s);

}
