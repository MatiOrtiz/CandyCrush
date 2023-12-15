package Entidades;

/*
 * Modela la entidad Vacio.
 */
public class EntidadVacia extends Entidad{
	
	//Constructor
	public EntidadVacia(int pos_x, int pos_y) {
		posX = pos_x;
		posY = pos_y;
		color = "Vacio";
	}


	/*
	 * 
	 */
	public void romperse() {
		miGrafica.romperse();
	}

	/*
	 * No posee este comportamiento.
	 */
	public void mover(int direccion) {
	}

	
	/*
	 * Comprotamiento para la caida de caramelos.
	 */
	public void romperDetonable() {
	}

	public void ocuparse(Entidad e) {
		e.ocupar(this);

	}

	public void ocupar(EntidadVacia v) {
	}

	public void ocupar(Gelatina g) {
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

	public boolean puedeRomper(Gelatina g) {
		return false;
	}

	public boolean bloqueaCaida() {
		return false;
	}
	
	public boolean puedeOcuparse() {
		return true;
	}

	public boolean puedeCaer() {
		return false;
	}

	public boolean intercambiarCon(Gelatina g) {
		return false;
	}

	public boolean intercambiarCon(Caramelo c) {
		return false;
	}

	public boolean intercambiarCon(EntidadVacia v) {
		return false;
	}

	public boolean intercambiarCon(Glaseado g) {
		return false;
	}

	public boolean intercambiarCon(Explosivo t) {
		return false;
	}

	public boolean intercambiar(Entidad e) {
		return false;
	}


}
