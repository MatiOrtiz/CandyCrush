package Entidades;

/*
 * Modela la entidad Glaseado.
 */
public class Glaseado extends Entidad{

	public Glaseado(int pos_x, int pos_y) {
		posX = pos_x;
		posY = pos_y;
		color = "Glaseado";
		setPuntaje(25);
		numObjetivo = 7;
	}
	
	
	//Operaciones
	public boolean intercambiar(Entidad e) {
		return false;
	}

	public boolean puedeMoverse() {
		return false;
	}

	
	public boolean machea(Entidad e) {
		return false;
	}
	
	
	public boolean puedeRomper(Caramelo c) {
		return false;
	}
	
	/**
	 * 
	 */
	public boolean puedeRomper(Gelatina g) {
		return false;
	}
	
	
	public void romperse() {
		notificarDetonacion();
		miTablero.incrementarPuntaje(puntaje);
		miTablero.incrementarObj(numObjetivo);
	}


	public void romperDetonable() {
		miTablero.romperEntidad(posX, posY);
		
	}
	
	
	public boolean bloqueaCaida() {
		return true;
	}
	
	public boolean puedeOcuparse() {
		return false;
	}


	public boolean puedeCaer() {
		return false;
	}


	@Override
	public boolean intercambiarCon(Gelatina g) {
		return false;
	}


	@Override
	public boolean intercambiarCon(Caramelo c) {
		return false;
	}


	@Override
	public boolean intercambiarCon(EntidadVacia v) {
		return false;
	}


	@Override
	public boolean intercambiarCon(Glaseado g) {
		return false;
	}


	@Override
	public boolean intercambiarCon(Explosivo t) {
		return false;
	}


	@Override
	public void ocuparse(Entidad e) {
	}


	@Override
	public void ocupar(EntidadVacia v) {
	}


	@Override
	public void ocupar(Gelatina g) {
	}
	

}
