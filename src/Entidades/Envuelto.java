package Entidades;

public class Envuelto extends Caramelo {

	public Envuelto(int pos_x, int pos_y, int numColor) {
		super(pos_x, pos_y, numColor);
		setPuntaje(50);
		numObjetivo = 30;
	}
	
	
	public Envuelto(int pos_x, int pos_y, String color) {
		super(pos_x, pos_y, color);
		setPuntaje(50);
		numObjetivo = 30;
	}


	//Redefinir
	public void romperse() {
		miTablero.romperCircundantes(posX, posY);
		notificarDetonacion(); 
		miTablero.incrementarObj(numColor);
		miTablero.incrementarObj(numObjetivo);
		miTablero.incrementarPuntaje(puntaje);
	}
	
	public String getNombreImagen() {
		return color +"_envuelto";
	}
	
}