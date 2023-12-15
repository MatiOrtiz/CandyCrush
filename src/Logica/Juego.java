package Logica;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Scanner;

import Entidades.*;
import Grafica.Gui;

public class Juego {
	// Atributos
	public static final int DERECHA = 400;
	public static final int IZQUIERDA = 300;
	public static final int ARRIBA = 100;
	public static final int ABAJO = 200;

	private Tablero tablero;
	private int vidas;
	private Nivel nivelActual;
	private Jugador jugador;
	private Gui gui;
	private int numNivel;
	private boolean controlesBloqueados;
	private ManejadorSolicitudesFinalAnimaciones accionAlFinalizarAnimaciones;
	private Reloj reloj;
	//Constructor
	public Juego(Gui g) {
		gui = g;
		accionAlFinalizarAnimaciones = new EnEspera(this);
		vidas = 3;
		jugador = new Jugador("Jugador",0);
		Path dir = Paths.get(System.getProperty("java.class.path"));
		numNivel = 1;
		controlesBloqueados = false;
		generarNivel(dir.toString() + "\\Niveles\\"+numNivel+".txt");
	}


	//Operaciones
	public void setJugador(Jugador j) {
		jugador = j;
	}
	
	public int getVidas() {
		return vidas;
	}

	
	public int getNumNivel() {
		return numNivel;
	}
	
	public Tablero getTablero() {
		return tablero;
	}
	
	public List<VerificadorDeMatch> getEstraregiasDeMatch(){
		return tablero.getEstrategiasDeMatch();
	}

	public Nivel getNivelActual() {
		return nivelActual;
	}
	
	public Gui getGui() {
		return gui;
	}

	public Jugador getJugador() { return jugador; }

	public void guardarJugador(String nombre) {
		jugador.setNombre(nombre);
		gui.getRankingJugadores().addJugador(jugador);
		guardarDatos();
	}

	public void moverCursor(int direccion) {
		if(!controlesBloqueados) {
			tablero.moverCursor(direccion);
		}
	}

	public boolean intercambiar(int direccion) {
		boolean intercambioValido = false;
		if(!controlesBloqueados) {
			intercambioValido = tablero.intercambiar(direccion);
			if (intercambioValido)
				incrementarMov();
		}
		return intercambioValido;
	}
	

	public void generarNivel(String lvl) {
		try {	
			File f = new File(lvl);
			Scanner scanner = new Scanner(f);
			int i = 0;
			Entidad entidad = null;
			tablero = new Tablero(scanner.nextInt(), scanner.nextInt(),this);

			Queue<Integer> stackEstrategias = new LinkedList<Integer>();
			int codigoEstrategias = scanner.nextInt();
			while(codigoEstrategias > 0) {
				int aux = codigoEstrategias % 10;
				stackEstrategias.add(aux);
				codigoEstrategias = codigoEstrategias/10;
			}
			while(!stackEstrategias.isEmpty()) {
				switch(stackEstrategias.remove()) {
				case 1: tablero.agregarEstrategiaDeMatch(new Match3(tablero));
				break;
				case 2: tablero.agregarEstrategiaDeMatch(new Match4(tablero));
				break;
				case 3: tablero.agregarEstrategiaDeMatch(new MatchTyL(tablero));
				}
			}
			reloj = new Reloj(0 ,90 , this);
			nivelActual = new Nivel(scanner.nextInt(), scanner.nextInt(), scanner.nextInt());
			for(int x = 0; x < tablero.getFilas(); x++)
				for (int j = 0; j < tablero.getColumnas(); j++) {
					i = scanner.nextInt();
					if(i < 6) {
						entidad = new Caramelo(x,j,i);
						agregarGrafica(entidad);
					}
					if(i == 7) {
						entidad = new Glaseado(x,j);
						agregarGrafica(entidad);
					}
					if(i > 10 && i < 16) {
						Caramelo c = new Caramelo(x,j,i-10);
						agregarGrafica(c);
						entidad = new Gelatina(x,j,c);
						agregarGrafica(entidad);
						c.setTablero(tablero);
					}
					if(i == 16) {
						entidad = new Explosivo(x,j,60,this);
						agregarGrafica(entidad);
						((Explosivo) entidad).iniciar();
					}
					tablero.setEntidad(x, j, entidad);
					if (entidad != null) {
						entidad.setTablero(tablero);
					}
				}
			scanner.close();
		} catch (NullPointerException | FileNotFoundException | NoSuchElementException e) {
			e.printStackTrace();
		}
		tablero.getEntidad(0, 0).cambiarFoco();
	}
	

	public void aumentarNivel() {
		numNivel++;
		reloj.pausar();
		guardarDatos();
		if(numNivel < 6) {
			gui.notificacionQuitarTablero("Objetivo cumplido. Avanzando de nivel...");
			Path rutaTxtNivel = Paths.get(System.getProperty("java.class.path"));
			generarNivel(rutaTxtNivel.toString() + "\\Niveles\\"+numNivel+".txt");
			gui.actualizarGraficaAumentarNivel();
		}else {
			gui.notificacionQuitarTablero("Felicitaciones, completaste el juego!");
			gui.finalizarJuego();
		}
	}
	
	public void resetearNivel() {
		gui.notificacionQuitarTablero("No cumpliste los objetivos. Reiniciando nivel...");
		Path rutaTxtNivel = Paths.get(System.getProperty("java.class.path"));
		generarNivel(rutaTxtNivel.toString() + "\\Niveles\\"+numNivel+".txt");
		gui.actualizarGraficaResetearNivel();
		guardarDatos();
	}

	public void incrementarObj(int numObjetivo) {
		boolean seguir = nivelActual.incrementarObj(numObjetivo);
		gui.actualizarContObj();
		if(!seguir) {
			accionAlFinalizarAnimaciones.solicitarSiguienteNivel();
		}
		if(!gui.hayAnimacionesEnCurso()) {
			accionAlFinalizarAnimaciones.ejecutarSolicitud();
		}
	}
	
	public void incrementarPuntaje(int p) {
		int puntajeNuevo = jugador.getScore()+p;
		jugador.setScore(puntajeNuevo);
		gui.actualizarPuntaje(puntajeNuevo);
		guardarDatos();
	}

	public void incrementarMov() {
		boolean seguir = nivelActual.incrementarMov();
		gui.actualizarMov();
		if(!seguir) {
			restarVida();
		}
	}
	
	public void restarVida() {
		vidas--;
		reloj.pausar();
		//caso de game over, hasta ahora hecho super basico
		if(vidas == 0) {
			accionAlFinalizarAnimaciones.solicitarFinalizarJuego();
		}
		else {
			accionAlFinalizarAnimaciones.solicitarReiniciarTablero();
		}
		if(!gui.hayAnimacionesEnCurso()) {
			accionAlFinalizarAnimaciones.ejecutarSolicitud();
		}
	}

	public void guardarDatos() {
		RankingJugadores ranking= gui.getRankingJugadores();
		ranking.actualizarRanking();
		try {
			FileOutputStream fileOutputStream = new FileOutputStream(Gui.configuration.getProperty("file"));
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
			objectOutputStream.writeObject(ranking);
			objectOutputStream.flush();
			objectOutputStream.close();
		}catch (IOException exception){
			exception.printStackTrace();
		}
	}

	public void finalizarJuego() {
		guardarDatos();
		gui.actualizarVida();
		gui.mostrarMensaje("GAME OVER :(");
		gui.finalizarJuego();
	}

	public void notificarTiempo(String strTiempo) {
		gui.actualizarTiempo(strTiempo);
	}


	public void bloquarControles() {
		controlesBloqueados = true;
	}


	public void desbloquearControles() {
		controlesBloqueados = false;
	}

	public void agregarGrafica(Entidad e) {
		gui.agregarGrafica(e);
	}
	
	public void setManejadorSolicitudes(ManejadorSolicitudesFinalAnimaciones solicitud) {
		accionAlFinalizarAnimaciones = solicitud;
	}


	public void ejecutarAccionesPendientes() {
		accionAlFinalizarAnimaciones.ejecutarSolicitud();		
	}


	public ManejadorSolicitudesFinalAnimaciones getManejadorSolicitudes() {
		return accionAlFinalizarAnimaciones;
	}


	public void explotarBomba() {
		accionAlFinalizarAnimaciones.solicitarFinalizarJuego();
		if(!gui.hayAnimacionesEnCurso()) {
			accionAlFinalizarAnimaciones.ejecutarSolicitud();
		}
	}
	
	public void iniciarReloj() {
		reloj.iniciar();
	}
	
	public void resetearReloj() {
		reloj.reset();
		reloj.iniciar();
	}
}
