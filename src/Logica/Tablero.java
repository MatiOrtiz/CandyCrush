package Logica;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Stack;
import Entidades.Caramelo;
import Entidades.Entidad;
import Entidades.Envuelto;
import Entidades.MultiRayado;
import Entidades.RayadoHorizontal;
import Entidades.RayadoVertical;
import Entidades.EntidadVacia;

public class Tablero {
	//Atributos
	private static int cantFilas;
	private static int cantColumnas; 
	private Entidad[][] grilla;
	private int x;
	private int y;
	private Juego miJuego;
	private List<VerificadorDeMatch> estrategiasDeMatch;
	

	public Tablero(int cantF, int cantC, Juego j) {
		cantFilas = cantF;
		cantColumnas = cantC;
		grilla = new Entidad[cantFilas][cantColumnas];
		miJuego = j;
		x = 0;
		y = 0;
		estrategiasDeMatch = new LinkedList<VerificadorDeMatch>(); //TODO definir la estrategia de match dependiendo del nivel
	}




	//Operaciones
	public int getFilas() {
		return cantFilas;
	}

	public int getColumnas() {
		return cantColumnas;
	}

	public Entidad[][] getGrilla() {
		return grilla;
	}

	public Entidad getEntidad(int i, int j) {
		return grilla[i][j];
	}
	
	public Entidad getCursor() {
		return grilla[x][y];
	}
	
	
	public Juego getMiJuego() { return miJuego; }
	
	public List<VerificadorDeMatch> getEstrategiasDeMatch(){
		return estrategiasDeMatch;
	}

	public void setEntidad(int i, int j, Entidad e) {
		grilla[i][j] = e;
	}
	

	public boolean moverCursor(int direccion) {
		boolean movio = false;
		Entidad entidadVieja = grilla[x][y];
		switch(direccion) {
			case Juego.ARRIBA :
				if(x != 0) {
					x -= 1;
					movio = true;
				}
				break;
			case Juego.ABAJO :
				if(x != cantColumnas -1) {
					x += 1;
					movio = true;
				}
				break;
			case Juego.IZQUIERDA:
				if(y != 0) {
					y -= 1;
					movio = true;
				}
				break;
			case Juego.DERECHA :
				if(y != cantColumnas -1) {
					y+= 1;
					movio = true;
				}
				break;
		}
		if(movio) {
			entidadVieja.cambiarFoco();
			grilla[x][y].cambiarFoco();
		}
		return movio;
	}


	public boolean intercambiar(int direccion) {
		boolean huboMatch = false;
		grilla[x][y].cambiarFoco();
		Entidad posActual = grilla[x][y];
		Entidad adyacente;
		switch(direccion) {
		case Juego.ARRIBA :
			if(x != 0) {
				adyacente = grilla[x-1][y];
				huboMatch = posActual.intercambiar(grilla[x-1][y]);
				x--;
				if(!huboMatch) {
					grilla[x+1][y].intercambiar(grilla[x][y]);
					x++;
				}
			}
			break;
		case Juego.ABAJO :
			if(x != cantColumnas -1) {
				adyacente = grilla[x+1][y];
				huboMatch = posActual.intercambiar(grilla[x+1][y]);
				x++;
				if(!huboMatch) {
					grilla[x-1][y].intercambiar(grilla[x][y]);
					x--;
				}
			}
			break;
		case Juego.IZQUIERDA:
			if(y != 0) {
				adyacente = grilla[x][y-1];
				huboMatch = posActual.intercambiar(grilla[x][y-1]);
				y--;
				if(!huboMatch) {
					grilla[x][y+1].intercambiar(grilla[x][y]);
					y++;
				}
			}
			break;
		case Juego.DERECHA :
			if(y != cantColumnas -1) {
				adyacente = grilla[x][y+1];
				huboMatch = posActual.intercambiar(grilla[x][y+1]);
				y++;
				if(!huboMatch) {
					grilla[x][y-1].intercambiar(grilla[x][y]);
					y--;
				}
			}
			break;
		}
		if(!miJuego.getGui().hayAnimacionesEnCurso()) {
			grilla[x][y].cambiarFoco();
		}
		if(huboMatch) {
			caer();
			recorrerTablero();
		}
		return huboMatch;
	}
	
	
	/*Se encarga de llamar a los checkMatch auxiliares en las cuatro direcciones,
	 * controlando que no se caigan de los arreglos.
	 * return huboMatch por ahora no se usa.
	 */
	public boolean checkMatch3(int pos_x, int pos_y) {
		boolean huboMatch = false;
		for(VerificadorDeMatch v: estrategiasDeMatch) {
				huboMatch = v.checkMatch(pos_x, pos_y) || huboMatch;
		}
		return huboMatch;
	}


	/*
	 * Rompe la cantidad indicadad de entidades hacia arriba de la posicion 
	 */
	public void romperHaciaArriba(int posX, int posY, int cantidadARomper) {
		for (int i=0; i<cantidadARomper; i++) 
			if(posX-i >= 0) 
				romperEntidad(posX-i, posY);
	}


	/*
	 * Rompe la cantidad indicada de entidades hacia la izquierda de la posicion.
	 */
	public void romperHaciaIzquierda(int posX, int posY, int cantidadARomper) {
		for (int i=0; i<cantidadARomper; i++) 
			if(posY-i >= 0) 
				romperEntidad(posX, posY-i);
	}

	public void romperFila(int f, int c) {
		for(int i = 0; i <= cantColumnas -1; i++)
			romperEntidad(f,i);
	}

	public void romperColumna(int f,int c) {
		for(int i = 0; i <= cantFilas -1; i++)
			romperEntidad(i,c);
	}

	/*
	 * Rompe los caramelos circundantes de la entidad en la fila f columna c.
	 */
	public void romperCircundantes(int f,int c) {
		Stack<Entidad> p = getAdyacentes(f,c);
		if(f + 1 <= cantFilas -1 && c+1 <=cantColumnas -1)
			p.push(grilla[f+1][c+1]);
		if(f+1 <= cantFilas -1 && c -1 >= 0)
			p.push(grilla[f+1][c-1]);
		if(f-1 >= 0 && c-1 >= 0)
			p.push(grilla[f-1][c-1]);
		if(f-1 >= 0 && c+1 <= cantColumnas -1)
			p.push(grilla[f-1][c+1]);

		while(! p.isEmpty()) {
			Entidad aRomper = p.pop();
			romperEntidad(aRomper.getX(), aRomper.getY());
		}
	}


	/**
	 * Rompe la entidad en la posicion y la reemplaza por un vacio
	 * @param posX, posY posicion a romper en el tablero.
	 */
	public void romperEntidad(int posX,int posY) {
		Entidad vacio = new EntidadVacia(posX, posY);
		agregarGrafica(vacio);
		Entidad aux = grilla[posX][posY];
		grilla[posX][posY] = vacio;
		aux.romperse();
	}

	
	/*
	 * Deja caer las entidades al espacio ocupable correspondiente.
	 */
	public void caer() {
        for(int j = 0 ; j <= cantColumnas -1 ; j ++) {
        for(int i = cantFilas - 1 ; i >= 0; i--) {
                if(grilla[0][j].puedeOcuparse()) {
                    generarCaramelo(j);
                }
                Entidad aOcupar = grilla[i][j];
                int k = 1;
                boolean seguir = true;
                if(aOcupar.puedeOcuparse()){
                    while(i-k >= 0 && seguir) {
                        Entidad aCaer = grilla[i-k][j];
                        if(aCaer.bloqueaCaida())
                            seguir = false;
                        else
                            if(aCaer.puedeCaer()) {
                            	aOcupar.ocuparse(aCaer);;
                            	seguir = false;
							}
							k++;
					}
				}
			}
		}
	}

	public void incrementarObj(int numObjetivo) {
		miJuego.incrementarObj(numObjetivo);
	}

	public void incrementarPuntaje(int p) {
		miJuego.incrementarPuntaje(p);
	}

	public void generarRayadoHorizontal(int pos_x, int pos_y, String color) {
		grilla[pos_x][pos_y].romperse(); //Rompe la Entidad(Vacio) que queda después de haber llamado a romper.
		Entidad nuevoRayado = new RayadoHorizontal(pos_x, pos_y, color);
		agregarGrafica(nuevoRayado);
		grilla[pos_x][pos_y] = nuevoRayado;
		nuevoRayado.setTablero(this);
	}

	public void generarRayadoVertical(int pos_x, int pos_y,String color) {
		grilla[pos_x][pos_y].romperse(); //Rompe la Entidad(Vacio) que queda después de haber llamado a romper.
		Entidad nuevoRayado = new RayadoVertical(pos_x, pos_y, color);
		agregarGrafica(nuevoRayado);
		grilla[pos_x][pos_y] = nuevoRayado;
		nuevoRayado.setTablero(this);
	}

	public void generarEnvuelto(int pos_x, int pos_y, String color) {
		grilla[pos_x][pos_y].romperse();  //Rompe la Entidad(Vacio) que queda después de haber llamado a romper.
		Entidad nuevoEnvuelto = new Envuelto(pos_x, pos_y, color);
		agregarGrafica(nuevoEnvuelto);
		grilla[pos_x][pos_y] = nuevoEnvuelto;
		nuevoEnvuelto.setTablero(this);
	}
	

	/*
	 * Genera un nuevo caramelo aleatorio en la fila 0, columna posY.
	 */
	private void generarCaramelo(int posY) {
        Random rand = new Random();
        int value = rand.nextInt(100) + 1;
        Entidad entidadNueva;
        if(value == 1) {
        	entidadNueva = new MultiRayado(0,posY,rand.nextInt(5)+1);
        }
        else {
        	entidadNueva = new Caramelo(0,posY,rand.nextInt(5)+1);
        }
        agregarGrafica(entidadNueva);
        entidadNueva.setTablero(this);
        Entidad entidadVieja = grilla[0][posY];
        grilla[0][posY] = entidadNueva;
        entidadVieja.romperse();
    }
	

	/**
	 * Devuelve una pila con las entidades adyacentes a la entidad de la posicion indicada.
	 * @param posX posY Posicion de la entidad que invoca.
	 * @return Stack<Entidad> pila con los adyacentes.
	 */
	public Stack<Entidad> getAdyacentes(int posX, int posY){
		Stack<Entidad> pila = new Stack<Entidad>();
		if(posX >= 1)
			pila.add(grilla[posX -1][posY]);
		if(posX < cantFilas - 1)
			pila.add(grilla[posX +1][posY]);
		if(posY >= 1)
			pila.add(grilla[posX][posY - 1]);
		if(posY < cantColumnas - 1)
			pila.add(grilla[posX][posY + 1]);
		return pila;
	}
	

	/*
	 * Chequea Exhaustivamente el tablero y verifica si se formo un match nuevo.
	 */
	private void recorrerTablero() {
		boolean seguir = true;
		boolean huboMatch = false;
		while(seguir) {
			huboMatch = false;
			for(int i = 0; i <= cantFilas-1; i++) {
				for(int j = 0; j <= cantColumnas -1; j++) {
					if(checkMatch3(i,j))
						huboMatch = true;
				}
			}
			if(huboMatch) 
				caer();
			else 
				seguir = false;
		}
	}
	
	
	private void agregarGrafica(Entidad e) {
		miJuego.agregarGrafica(e);
	}




	public void agregarEstrategiaDeMatch(VerificadorDeMatch verificadorDeMatch) {
		estrategiasDeMatch.add(verificadorDeMatch);		
	}
	
}