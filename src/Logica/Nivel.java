package Logica;

public class Nivel {

	private final int objetivo, cantidadMov, cantidadObjetivo;
	private int contadorObj, contadorMov;

	public Nivel(int tipoObjetivo, int cantidadMovimientos, int cantidadARomper) {
		objetivo= tipoObjetivo;
		cantidadMov= cantidadMovimientos;
		cantidadObjetivo= cantidadARomper;
		contadorMov= 0;
		contadorObj= 0;
	}

	public int getMovimientos() {
		return cantidadMov-contadorMov;
	}
	
	public String getStringObjetivo() {
		String toReturn = "";
		switch (objetivo) {
		case 1:
			toReturn = "Rojo";
			break;
		case 2:
			toReturn = "Azul";
			break;
		case 3:
			toReturn = "Verde";
			break;
		case 4:
			toReturn = "Purpura";
			break;
		case 5:
			toReturn = "Naranja";
			break;
		case 7:
			toReturn = "Glaseado";
			break;
		case 10:
			toReturn = "Gelatina";
			break;
		case 20:
			toReturn = "Rayados";
			break;
		case 30:
			toReturn = "Envueltos";
			break;
		} 
		return toReturn;
		
	}
	
	public int getCantidadObjetivos() {
		return cantidadObjetivo;
	}
	public int getContadorObjetivo() {
		return contadorObj;
	}
	public int getObjetivo() {
		return objetivo;
	}

	public boolean incrementarMov() {
		contadorMov++;
		return cantidadMov - contadorMov > 0;
	}

	//Cuando retorna falso no se podra incrementar el contadorObjetivo
	//Significa que el objetivo se cumplió
	public boolean incrementarObj(int numObjetivo) {
		if(objetivo == numObjetivo) {
			contadorObj++;
		}
		return contadorObj<cantidadObjetivo;
	}

}
