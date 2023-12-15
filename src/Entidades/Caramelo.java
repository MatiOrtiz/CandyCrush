package Entidades;

import java.util.Stack;

public class Caramelo extends Entidad{	
	protected final String[] COLOR = {"","Rojo","Azul","Verde","Purpura","Naranja"};
	protected final int[] PUNTAJE = {0,5,20,10,25,20};
	protected int numColor;
	
	
	//Constructores
	public Caramelo(int pos_x, int pos_y, int numColor) {
		//Posicion en el tablero
		posX = pos_x;
		posY = pos_y;
		color = COLOR[numColor];
		puntaje = PUNTAJE[numColor];
		this.numColor = numColor;
		numObjetivo = numColor;
	}
	
	public Caramelo(int pos_x, int pos_y, String color) {
		//Posicion en el tablero
		posX = pos_x;
		posY = pos_y;
		this.color = color;
		numObjetivo = numColor;
		for(int i = 0; i < COLOR.length; i++) {
			if(color.equals(COLOR[i])) {
				numColor = i;
				puntaje = PUNTAJE[i];
			}
		}
	}

	
	//operaciones
	public boolean puedeMoverse() {
		return true;
	}
	
	/*
	 * Comportamiento para chequear match.
	 */
	public boolean machea(Entidad e) {
		return e.puedeRomper(this);
	}
	
	
	public boolean puedeRomper(Caramelo c) {
		return color.equals(c.getColor());
	}
	
	
	public boolean puedeRomper(Gelatina g) {
		return puedeRomper(g.getCaramelo());
	}
	
	public boolean intercambiar(Entidad e){
		return e.intercambiarCon(this);
	}
	
	public boolean intercambiarCon(Gelatina g) {
		boolean huboMatch = false;
		int xAdyacente = g.getX();
		int yAdyacente = g.getY();
		Caramelo carameloAdyacente = g.getCaramelo();
		
		carameloAdyacente.setPos(posX, posY);
		this.setPos(xAdyacente, yAdyacente);
		miTablero.setEntidad(carameloAdyacente.getX(), carameloAdyacente.getY(), carameloAdyacente);
		g.setCaramelo(this);
		
		notificarIntercambio(carameloAdyacente);
		huboMatch = (miTablero.checkMatch3(carameloAdyacente.getX(), carameloAdyacente.getY()) | miTablero.checkMatch3(xAdyacente, yAdyacente)) ;
		return huboMatch;
	}
	
	public boolean intercambiarCon(Caramelo c) {
		boolean huboMatch = false;
		int xAdyacente = c.getX();
		int yAdyacente = c.getY();
		
		
		c.setPos(posX, posY);
		this.setPos(xAdyacente,yAdyacente);
		miTablero.setEntidad(c.getX(), c.getY(), c);
		miTablero.setEntidad(posX, posY, this);
		
		notificarIntercambio(c);
		huboMatch = (miTablero.checkMatch3(c.getX(),c.getY()) | miTablero.checkMatch3(xAdyacente, yAdyacente)) ;
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
	 * Comportamiento para romperse. Las entidades adyacentes que sufren la explosion tambien detonan
	 */
	public void romperse() {
		notificarDetonacion();
		miTablero.incrementarObj(numObjetivo);
        Stack<Entidad> p = miTablero.getAdyacentes(posX, posY);
        while(! p.isEmpty()) {
            Entidad e = p.pop();
            e.romperDetonable();
        }
    }
	
	
	public void romperDetonable() {
	}
	
	
	/*
	 * Comportamiento para la caida de los caramelos.
	 */
	public boolean bloqueaCaida() {
		return false;
	}
	
	
	public boolean puedeOcuparse() {
		return false;
	}

	
	public boolean puedeCaer() {
		return true;
	}

	@Override
	public void ocuparse(Entidad e) {
	}

	@Override
	public void ocupar(EntidadVacia v) {
		notificarCaida(this, v);
		int vX = v.getX();
		int vY = v.getY();
		miTablero.setEntidad(posX, posY, v);
		miTablero.setEntidad(vX, vY, this);
		v.setPos(posX, posY);
		posX = vX;
		posY = vY;
	}

	@Override
	public void ocupar(Gelatina g) {
		notificarCaida(this, g);
		EntidadVacia nuevoVacio = new EntidadVacia(posX, posY);
		miTablero.getMiJuego().agregarGrafica(nuevoVacio);
		miTablero.setEntidad(posX, posY, nuevoVacio);
		g.setCaramelo(this);
		posX = g.getX();
		posY = g.getY();
	}

	
}