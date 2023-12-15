package Grafica;
import java.awt.Color;
import java.awt.Image;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import Entidades.Entidad;

public class EntidadGrafica {
	public static final int alto = 75;
	public static final int ancho = 75;


	private boolean enfocado;
	private String nombreImagen;
	private ImageIcon imagen;
	private Entidad entidad;
	private JLabel lbl;
	private Gui gui;

	//Constructor
	public EntidadGrafica(Entidad e,String dominio) {
		entidad = e;
		String s = e.getNombreImagen();
		nombreImagen = "/Imagenes"+dominio+"/"+s+".png";
		URL urlGrafica= this.getClass().getResource(nombreImagen);
		imagen = new ImageIcon(urlGrafica);
		enfocado = false;
		lbl = new JLabel(imagen);
	}


	//Operaciones
	public String getNombreImagen() {
		return nombreImagen;
	}

	public ImageIcon getImagen() {
		return imagen;
	}

	public JLabel getLabel() {
		return lbl;
	}
	public Gui getGui() {
		return gui;
	}

	public void setLabel(JLabel l){
		lbl = l;
	}

	public void setNombreImagen(String s) {
		nombreImagen = s;
	}
	public void setGui(Gui g) {
		gui = g;
	}

	public void cambiarFoco() {
		if(enfocado) {
			String imgColorEnfocadoSinPng= nombreImagen.substring(0, nombreImagen.length()-13);
			nombreImagen = imgColorEnfocadoSinPng + ".png";
		} else {
			String imgColorSinPng= nombreImagen.substring(0, nombreImagen.length() - 4);
			nombreImagen = imgColorSinPng + "_enfocado.png";
		}
		enfocado = !enfocado;
		actualizarImg();
	}

	public void actualizarImg() {
		URL urlGrafica= this.getClass().getResource(nombreImagen);
		imagen = new ImageIcon(urlGrafica);
		Image imgEscalada= imagen.getImage().getScaledInstance(alto, ancho, Image.SCALE_SMOOTH);
		ImageIcon escalada = new ImageIcon(imgEscalada);
		lbl.setIcon(escalada);
		lbl.setBounds(entidad.getY()*alto, entidad.getX()*ancho, ancho, alto);
	}


	public void romperse() {
		gui.eliminarGrafica(entidad);
	}

	public void actualizarTiempo(String strTiempo) {
		lbl.setForeground(Color.white);
		lbl.setText(strTiempo);
		lbl.setHorizontalTextPosition(JLabel.CENTER);
		lbl.setVerticalTextPosition(JLabel.CENTER);
	}


	public void notificarIntercambio(Entidad adyacente, Entidad e) {
		gui.animarIntercambio(adyacente,e);
	}

	public void notificarCaida(Entidad arriba, Entidad e) {
		gui.animarCaida(arriba, e);
	}

	public void notificarDetonacion(Entidad e) {
		gui.animarDetonacion(e);
	}
}
