package Entidades;

public class RayadoHorizontal extends Caramelo{

	public RayadoHorizontal(int pos_x, int pos_y, int numColor) {
		super(pos_x, pos_y, numColor);
		setPuntaje(45);
		numObjetivo = 20;
	}
	public RayadoHorizontal(int pos_x, int pos_y, String color) {
		super(pos_x, pos_y, color);
		setPuntaje(45);
		numObjetivo = 20;
	}

	public void romperse() {
		miTablero.romperFila(posX, posY);
		notificarDetonacion();
		miTablero.incrementarObj(numColor);
		miTablero.incrementarObj(numObjetivo);
		miTablero.incrementarPuntaje(puntaje);
	}
	
	public String getNombreImagen() {
		return color +"_rayadoH";
	}

}
