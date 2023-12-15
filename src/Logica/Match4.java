package Logica;

import Entidades.Entidad;

public class Match4 implements VerificadorDeMatch {
	private Tablero tablero;
	
	public Match4(Tablero t) {
		tablero = t;
	}
	@Override
	public boolean checkMatch(int pos_x, int pos_y) {
		boolean huboMatch = false;
		int cantFilas = tablero.getFilas();
		int cantColumnas = tablero.getColumnas();
		//buscar hacia arriba
		int px = pos_x-1;
		Entidad entidad = tablero.getEntidad(pos_x, pos_y);
		int contX = 1;
		while(px >=0 && entidad.machea(tablero.getEntidad(px, pos_y))) {
			contX++;
			px--;
		}
		px = pos_x+1;
		while(px < cantFilas && entidad.machea(tablero.getEntidad(px, pos_y))) {
			contX++;
			px++;
		}
		
		int py = pos_y-1;
		int contY = 1;
		while(py >=0 && entidad.machea(tablero.getEntidad(pos_x, py))) {
			contY++;
			py--;
		}
		
		py = pos_y+1;
		while(py < cantColumnas && entidad.machea(tablero.getEntidad(pos_x, py))) {
			contY++;
			py++;
		}
		huboMatch = (contX >= 3 || contY >= 3); 
		
		
		if(contX == 4) {
			tablero.romperHaciaArriba(px-1, pos_y, contX);
			tablero.generarRayadoVertical(pos_x, pos_y, entidad.getColor());
		}
		if(contY == 4) {
			tablero.romperHaciaIzquierda(pos_x, py-1, contY);
			tablero.generarRayadoHorizontal(pos_x, pos_y, entidad.getColor());
		}
		return huboMatch;
	}

	public String toString() {
		return "match 4";
	}
}
