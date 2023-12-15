package Entidades;

import Grafica.EntidadGrafica;
import Logica.Tablero;



public abstract class Entidad implements Intercambiable{
	//Atributos
	protected int posX;
	protected int posY;
	protected int puntaje;
	protected int numObjetivo;
	protected EntidadGrafica miGrafica;
	protected Tablero miTablero;
	protected String color;
	
	
	//Operaciones
	public abstract void ocuparse(Entidad e);

	public abstract void ocupar(EntidadVacia v);

	public abstract void ocupar(Gelatina g);

	public abstract void romperse();

	public abstract void romperDetonable();

	public abstract boolean puedeMoverse();
	
	public abstract boolean machea(Entidad e);
	
	public abstract boolean puedeRomper(Caramelo c);
	
	public abstract boolean puedeRomper(Gelatina g);

	public abstract boolean bloqueaCaida();
	
	public abstract boolean puedeOcuparse();
	
	public abstract boolean puedeCaer();
	
	public abstract boolean intercambiar(Entidad e);
 
	// Setters y getters
	public void setEntidadGrafica(EntidadGrafica e) {
		miGrafica = e;
	}
	public void setTablero(Tablero t) {
		miTablero = t;
	}

	public void setPuntaje(int p) {
		puntaje = p;
	}
	
	public void setPos(int x, int y) {
		posX = x;
		posY = y;
	}
	
	public int getX() {
		return posX;
	}
	
	public int getY() {
		return posY;
	}
	
	public String getNombreImagen() {
		return color;
	}

	public EntidadGrafica getEntidadGrafica() {
		return miGrafica;
	}

	public String getColor() {
		return color;
	}

	public int getPuntaje() {
		return puntaje;
	}

	/*
	 * Comportamiento para mover el cursor
	 */
	public void cambiarFoco() {
		miGrafica.cambiarFoco();
	}	

	/*
	 * Comportamiento para chequear match
	 */
	public boolean puedeRomper(Glaseado g) {
		return false;
	}
	
	public boolean puedeRomper(EntidadVacia v) {
		return false;
	}

	public void incrementarPuntaje() {
		miTablero.incrementarPuntaje(puntaje);
	}


	//NOTIFICACIONES A OTRAS CLASES
	public void notificarCaida(Entidad arriba, Entidad aOcupar) {
		miGrafica.notificarCaida(arriba, aOcupar);
	}
	
	public void notificarIntercambio(Entidad adyacente) {
		miGrafica.notificarIntercambio(adyacente,this);
	}
	
	public void notificarDetonacion() {
		miGrafica.notificarDetonacion(this);
	}
}
