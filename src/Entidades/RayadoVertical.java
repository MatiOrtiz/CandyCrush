package Entidades;

public class RayadoVertical extends Caramelo{

	public RayadoVertical(int pos_x, int pos_y, int numColor) {
		super(pos_x, pos_y, numColor);
		setPuntaje(35);
		numObjetivo = 20;
	}
	public RayadoVertical(int pos_x, int pos_y, String color) {
		super(pos_x, pos_y, color);
		setPuntaje(35);
		numObjetivo = 20;
	}

	public void romperse() {
		miTablero.romperColumna(posX, posY);
		notificarDetonacion();
		miTablero.incrementarObj(numColor);
		miTablero.incrementarObj(numObjetivo);
		miTablero.incrementarPuntaje(puntaje);
	}
	
	public String getNombreImagen() {
		return color +"_rayadoV";
	}
}
