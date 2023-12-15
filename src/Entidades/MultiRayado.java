package Entidades;

public class MultiRayado extends Caramelo {

    public MultiRayado(int posX, int posY, int numColor) {
        super(posX, posY, numColor);
        setPuntaje(100);
    }

    public MultiRayado(int posX, int posY, String color) {
        super(posX, posY, color);
        setPuntaje(100);
    }

    public void romperse() {
        miTablero.romperColumna(posX, posY);
        miTablero.romperFila(posX, posY);
	    notificarDetonacion();
        miTablero.incrementarPuntaje(puntaje);
    }

    public String getNombreImagen() {
		return color +"_multiRayado";
	}
    
}
