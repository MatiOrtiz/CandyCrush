package Entidades;

/*
 * Modela la entidad Gelatina.
 */
public class Gelatina extends Entidad{

	//Atributos
	private Entidad miCaramelo;
	
	
	//Constructor
	public Gelatina(int pos_x, int pos_y, Entidad c) {
		//Posicion en el tablero
		posX = pos_x;
		posY = pos_y;
		color = "Gelatina";
		miCaramelo = c;
		setPuntaje(miCaramelo.getPuntaje()+10);
		numObjetivo = 10;
	}
	
	
	//Operaciones
	public void setCaramelo(Entidad e) {
		miCaramelo = e;
		e.setPos(posX, posY);
	}
	
	public boolean puedeMoverse() {
		return miCaramelo.puedeMoverse();
	}

	public Caramelo getCaramelo() {
		return (Caramelo) miCaramelo;
	}
	
	public String getColor() {
		return miCaramelo.getColor();
	}

	
	/*
	 *Comportamiento para verificar el match 
	 */
	public boolean machea(Entidad e) {
		return e.puedeRomper(this);
	}
	
	public boolean puedeRomper(Gelatina g) {
		return miCaramelo.puedeRomper(g.getCaramelo());
	}
	

	public boolean puedeRomper(Caramelo c) {
		return miCaramelo.puedeRomper(c);
	}
	
	public boolean intercambiar(Entidad e) {
		return e.intercambiarCon(this);
	}
	
	
	
	public boolean intercambiarCon(Gelatina g) {
		boolean huboMatch = false;
		int posXAdyacente = g.getX();
		int posYAdyacente = g.getY();
		Caramelo carameloAdyacente = g.getCaramelo();
		
		miCaramelo.setPos(posXAdyacente, posYAdyacente);
		carameloAdyacente.setPos(posX, posY);
		g.setCaramelo(miCaramelo);
		miCaramelo = carameloAdyacente;

		miGrafica.notificarIntercambio(miCaramelo, g.getCaramelo());
		huboMatch = (miTablero.checkMatch3(carameloAdyacente.getX(), carameloAdyacente.getY()) | miTablero.checkMatch3(posXAdyacente,posYAdyacente)) ;
		return huboMatch;
	}
	

	public boolean intercambiarCon(Caramelo c) {
		boolean huboMatch = false;
		int posXAdyacente = c.getX();
		int posYAdyacente = c.getY();
		Caramelo nuevoInterno = c;
		Entidad internoAnterior = miCaramelo;
		
		
		nuevoInterno.setPos(posX, posY);
		internoAnterior.setPos(posXAdyacente, posYAdyacente);
		miCaramelo = nuevoInterno;
		miTablero.setEntidad(internoAnterior.getX(), internoAnterior.getY(), internoAnterior);
		
		miGrafica.notificarIntercambio(internoAnterior,nuevoInterno);
		huboMatch = (miTablero.checkMatch3(c.getX(), c.getY()) | miTablero.checkMatch3(posXAdyacente,posYAdyacente)) ;
		return huboMatch; 
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
	
	
	/*
	 * Comportamiento para romperse.
	 */
	public void romperse() {
		miCaramelo.romperse();
		notificarDetonacion();
		miTablero.incrementarObj(numObjetivo);
		miTablero.incrementarPuntaje(puntaje);
	}
	
	
	/*
	 * Comportamiento para la caida de caramelos.
	 */
	public void romperDetonable() {
	}
	
	public boolean bloqueaCaida() {
		return false;
	}

	public boolean puedeOcuparse() {
		return miCaramelo == null;
	}

	public boolean puedeCaer() {
		return (miCaramelo!= null && miCaramelo.puedeCaer());
	}


	@Override
	public void ocuparse(Entidad e) {
		e.ocupar(this);
	}


	@Override
	public void ocupar(EntidadVacia v) {
		notificarCaida(miCaramelo,v);
		int vX = v.getX();
		int vY = v.getY();
		miTablero.setEntidad(vX, vY, miCaramelo);
		miCaramelo.setPos(vX, vY);
		miCaramelo = null;
		v.romperse();
	}


	@Override
	public void ocupar(Gelatina g) {
		notificarCaida(miCaramelo, g);
		g.setCaramelo(miCaramelo);
		miCaramelo.setPos(g.getX(), g.getY());
		miCaramelo = null;
	}
	
}